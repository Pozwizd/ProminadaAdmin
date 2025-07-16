$(document).ready(async function() {
    // =================================================================
    // 1. ОБЪЯВЛЕНИЕ ПЕРЕМЕННЫХ И ИНИЦИАЛИЗАЦИЯ ПЛАГИНОВ
    // =================================================================

    const fileInput = document.getElementById('fileUpload');
    const fileListDiv = document.getElementById('uploadedFilesList');
    let files = []; // для загрузки файлов отзывов
    let currentReviewButton = null;

    // Инициализация Select2 для городов
    $('#city-select').select2({
        ajax: {
            url: contextPath + 'city/getAllByName',
            dataType: 'json',
            delay: 250,
            data: function (params) { return { filter_city: params.term }; },
            processResults: function (data) {
                return {
                    results: $.map(data, city => ({ id: city.id, text: city.name })),
                    pagination: { more: false }
                };
            },
            cache: true
        },
        placeholder: 'Введите название города...',
        minimumInputLength: 1,
        language: "ru",
    });

    // =================================================================
    // 2. ОПРЕДЕЛЕНИЕ ВСЕХ ФУНКЦИЙ
    // =================================================================

    // --- Функции загрузки данных ---

    function loadBranchesSelect() {
        return axios.get(contextPath + 'branch/list')
            .then(response => {
                const branches = response.data;
                const select = document.getElementById('branchesSelect');
                if (!select) {
                    console.error('Branch select element not found!');
                    return;
                }
                select.innerHTML = ''; // Очищаем перед заполнением
                branches.forEach(branch => {
                    const option = document.createElement('option');
                    option.value = branch.id;
                    option.text = branch.name;
                    select.appendChild(option);
                });
            })
            .catch(error => console.error('Ошибка при загрузке филиалов:', error));
    }

    function loadRolesSelect() {
        const selectElement = document.getElementById('rolePersonal');
        if (!selectElement) {
            console.error('Role select element not found!');
            return Promise.reject('Role select not found');
        }
        selectElement.innerHTML = `<option value="" disabled selected>${i18next.t('loading')}</option>`;

        return axios.get(`${contextPath}personal/getRoles`)
            .then(response => {
                if (response.data && Array.isArray(response.data)) {
                    selectElement.innerHTML = `<option value="" data-i18n="selectRole" disabled selected>${i18next.t('selectRole')}</option>`;
                    response.data.forEach(role => {
                        const option = document.createElement('option');
                        option.setAttribute("data-i18n", role);
                        option.value = role;
                        option.textContent = i18next.t(role);
                        selectElement.appendChild(option);
                    });
                } else {
                    console.error('Неверный формат данных ролей', response.data);
                }
            })
            .catch(error => console.error('Ошибка при загрузке ролей:', error));
    }

    // --- Функция заполнения формы ---

    function fillFormData(data) {
        function setFieldValue(id, value) {
            const element = document.getElementById(id);
            if (element) {
                element.value = value || '';
            }
        }

        setFieldValue('userId', data.id);
        setFieldValue('surname', data.surname);
        setFieldValue('name', data.name);
        setFieldValue('lastName', data.lastName);
        setFieldValue('phoneNumber', data.phoneNumber);
        setFieldValue('email', data.email);

        const roleElement = document.getElementById('rolePersonal');
        if (roleElement && data.role && roleElement.querySelector(`option[value="${data.role}"]`)) {
            roleElement.value = data.role;
        }

        const branchesSelect = $('#branchesSelect');
        if (branchesSelect.length && data.branches && Array.isArray(data.branches)) {
            const selectedBranchIds = data.branches.map(b => String(b.id));
            branchesSelect.val(selectedBranchIds).trigger('change');
        }

        if (data.pathAvatar) {
            $('#uploadedAvatar').attr('src', window.contextPath + data.pathAvatar);
        }

        if (data.feedBacks && data.feedBacks.length > 0) {
            const container = document.getElementById('reviewSectionsContainer');
            container.innerHTML = '';
            data.feedBacks.forEach((feedback, index) => {
                const reviewSection = createReviewSection(index + 1, feedback);
                container.appendChild(reviewSection);
            });
        }

        if (data.documentFeedbacks && data.documentFeedbacks.length > 0) {
            files = data.documentFeedbacks.map(doc => ({
                id: doc.id,
                name: doc.name,
                path: doc.pathImage,
                file: null
            }));
            renderFiles();
        }
    }

    // --- Функции для работы с файлами и отзывами ---

    function getFileIcon(file) {
        if (file.file && file.file.type) {
            if (file.file.type.startsWith('image/')) return '<i class="ti tabler-photo me-2"></i>';
            if (file.file.type === 'application/pdf') return '<i class="ti tabler-file-text me-2"></i>';
            if (file.file.type.startsWith('video/')) return '<i class="ti tabler-video me-2"></i>';
        }
        return '<i class="ti tabler-file me-2"></i>';
    }

    function renderFiles() {
        if (!fileListDiv) return;
        fileListDiv.innerHTML = '';
        files.forEach((file, idx) => {
            let downloadUrl = '#';
            let fileIcon = '<i class="ti tabler-file me-2"></i>';

            if (file.file) {
                downloadUrl = URL.createObjectURL(file.file);
                fileIcon = getFileIcon(file);
            } else if (file.id) {
                downloadUrl = `${contextPath}personal/documents/${file.id}/download`;
                if (file.name.toLowerCase().endsWith('.pdf')) {
                    fileIcon = '<i class="ti tabler-file-text me-2"></i>';
                }
            }

            fileListDiv.innerHTML += `
                <div class="d-flex align-items-center justify-content-between p-2 border rounded mb-2">
                    <span class="d-flex align-items-center"> ${fileIcon} ${file.name} </span>
                    <div class="d-flex gap-2">
                        <a href="${downloadUrl}" download="${file.name}" class="btn btn-icon btn-sm btn-outline-primary" title="Скачать"><i class="ti tabler-download"></i></a>
                        <button class="btn btn-icon btn-sm btn-outline-danger" title="Удалить" onclick="deleteFile(${idx})"><i class="ti tabler-trash"></i></button>
                    </div>
                </div>`;
        });
    }

    window.deleteFile = function(idx) {
        files.splice(idx, 1);
        renderFiles();
    };

    function createReviewSection(index, feedback = {}) {
        const section = document.createElement('div');
        section.className = 'review-section mb-4 border p-3 rounded position-relative';
        section.innerHTML = `
            <input type="hidden" class="feedback-id" value="${feedback.id || ''}">
            <button type="button" class="btn btn-icon btn-sm btn-outline-danger position-absolute top-0 end-0 mt-2 me-2" title="Удалить отзыв" onclick="removeReviewSection(this)"><i class="ti tabler-trash"></i></button>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="reviewerName${index}" data-i18n="nameLabel" class="form-label">Name</label>
                    <input type="text" class="form-control" id="reviewerName${index}" value="${feedback.name || ''}">
                </div>
                <div class="col-md-6 mb-3">
                    <label for="reviewerContact${index}" data-i18n="contactLabel" class="form-label">Contact</label>
                    <input type="text" class="form-control" id="reviewerContact${index}" value="${feedback.phoneNumber || ''}">
                </div>
            </div>
            <div class="mb-3">
                <label for="reviewText${index}" data-i18n="reviewLabel" class="form-label">Feedback</label>
                <textarea class="form-control" id="reviewText${index}" rows="3">${feedback.text || ''}</textarea>
            </div>`;
        return section;
    }

    window.removeReviewSection = function(button) {
        currentReviewButton = button;
        const deleteModal = new bootstrap.Modal(document.getElementById('deleteReviewConfirmModal'));
        deleteModal.show();
    }

    // --- Функции для отправки формы и валидации ---

    function collectFormData() {
        const formData = new FormData();
        const userId = document.getElementById('userId').value;
        if (userId) formData.append('id', userId);

        formData.append('surname', document.getElementById('surname').value);
        formData.append('name', document.getElementById('name').value);
        formData.append('lastName', document.getElementById('lastName').value);
        formData.append('phoneNumber', document.getElementById('phoneNumber').value);
        formData.append('email', document.getElementById('email').value);
        formData.append('role', document.getElementById('rolePersonal').value);

        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        if (password) {
            formData.append('password', password);
            formData.append('confirmPassword', confirmPassword);
        }

        const selectedBranches = $('#branchesSelect').val();
        if (selectedBranches && selectedBranches.length > 0) {
            selectedBranches.forEach((branchId, index) => {
                formData.append(`branchIds[${index}]`, branchId);
            });
        }

        const avatarInput = document.getElementById('upload');
        if (avatarInput.files.length > 0) {
            formData.append('avatar', avatarInput.files[0]);
        }

        if (files && files.length > 0) {
            files.forEach((fileObj, index) => {
                if (fileObj.id) {
                    formData.append(`documents[${index}].id`, fileObj.id);
                    formData.append(`documents[${index}].name`, fileObj.name);
                } else if (fileObj.file) {
                    formData.append(`documents[${index}].name`, fileObj.name);
                    formData.append(`documents[${index}].file`, fileObj.file);
                }
            });
        }

        const reviewSections = document.querySelectorAll('.review-section');
        reviewSections.forEach((section, index) => {
            const feedbackId = section.querySelector('.feedback-id').value;
            if (feedbackId) {
                formData.append(`feedBacks[${index}].id`, feedbackId);
            }

            const nameInput = section.querySelector('input[id^="reviewerName"]');
            const contactInput = section.querySelector('input[id^="reviewerContact"]');
            const textArea = section.querySelector('textarea[id^="reviewText"]');

            if (nameInput && contactInput && textArea) {
                formData.append(`feedBacks[${index}].name`, nameInput.value);
                formData.append(`feedBacks[${index}].phoneNumber`, contactInput.value);
                formData.append(`feedBacks[${index}].description`, textArea.value);
            }
        });

        return formData;
    }

    function clearValidationErrors() {
        document.querySelectorAll('.error-message').forEach(el => el.remove());
        document.querySelectorAll('.is-invalid').forEach(el => {
            el.classList.remove('is-invalid');
        });
    }

    function displayValidationErrors(errors) {
        clearValidationErrors();

        let hasFeedbackErrors = false;
        const informationPersonalTabButton = document.getElementById('informationPersonal-tab');
        const testimonialsPersonalTabButton = document.getElementById('testimonialsPersonal-tab');

        for (const fieldPath in errors) {
            if (errors.hasOwnProperty(fieldPath)) {
                const errorMessage = errors[fieldPath];

                if (fieldPath.startsWith('feedBacks[')) {
                    hasFeedbackErrors = true;
                    if (testimonialsPersonalTabButton) {
                        new bootstrap.Tab(testimonialsPersonalTabButton).show();
                    }
                } else {
                    if (informationPersonalTabButton) {
                        new bootstrap.Tab(informationPersonalTabButton).show();
                    }
                }

                if (fieldPath.startsWith('feedBacks[')) {
                    // ... (обработка ошибок для отзывов)
                } else {
                    const fieldElement = document.getElementById(fieldPath);
                    if (fieldElement) {
                        fieldElement.classList.add('is-invalid');
                        const errorDiv = document.createElement('div');
                        errorDiv.className = 'error-message invalid-feedback';
                        const errorKey = `personal.validation.${errorMessage}`;
                        errorDiv.setAttribute('data-i18n', errorKey);
                        errorDiv.textContent = i18next.t(errorKey);
                        fieldElement.parentNode.appendChild(errorDiv);
                    } else {
                        console.warn('Не удалось найти поле для ошибки:', fieldPath);
                    }
                }
            }
        }
    }

    function formDataToObject(formData) {
        const object = {};
        formData.forEach((value, key) => {
            // Если есть несколько значений с одним ключом, создаем массив
            if (object[key]) {
                if (!Array.isArray(object[key])) {
                    object[key] = [object[key]];
                }
                object[key].push(value);
            } else {
                object[key] = value;
            }
        });
        return object;
    }


    function submitForm() {
        clearValidationErrors();
        const formData = collectFormData();
        const userId = document.getElementById('userId').value;
        const url = `${contextPath}personal${userId ? `/${userId}` : '/create'}`;
        const method = userId ? 'put' : 'post';
        const formDataObject = formDataToObject(formData);
        console.log('Form data:', JSON.stringify(formDataObject, null, 2));

        axios({
            method: method,
            url: url,
            data: formData,
            headers: { 'Content-Type': 'multipart/form-data' }
        })
            .then(response => {
                // Предполагается, что у вас есть функция для показа уведомлений (toast)
                showI18nToast('success', 'system.toast.title.success', 'personal.toast.update.successful');
                alert('Данные успешно сохранены!');
                setTimeout(() => {
                    window.location.href = `${contextPath}personal`;
                }, 1500);
            })
            .catch(error => {
                console.error('Ошибка при отправке данных:', error);
                if (error.response && error.response.data) {
                    if (typeof error.response.data === 'object') {
                        displayValidationErrors(error.response.data);
                    } else {
                        showI18nToast('error', 'system.toast.title.error', 'personal.toast.update.error');
                    }
                } else {
                    showI18nToast('error', 'system.toast.title.error', 'personal.toast.update.error');
                    // alert('Произошла неизвестная ошибка при сохранении данных.');
                }
            });
    }

    // =================================================================
    // 3. УСТАНОВКА ОБРАБОТЧИКОВ СОБЫТИЙ
    // =================================================================

    if (fileInput) {
        fileInput.addEventListener('change', (e) => {
            const invalidFiles = [];
            for (const file of e.target.files) {
                if (file.type === 'application/pdf' || file.name.toLowerCase().endsWith('.pdf')) {
                    files.push({ id: null, name: file.name, file: file, path: null });
                } else {
                    invalidFiles.push(file.name);
                }
            }
            if (invalidFiles.length > 0) {
                alert(`Следующие файлы не были загружены, так как они не в формате PDF: ${invalidFiles.join(', ')}`);
            }
            renderFiles();
        });
    }

    const submitButton = document.querySelector('button[type="submit"]');
    if (submitButton) {
        submitButton.addEventListener('click', function (e) {
            e.preventDefault();
            submitForm(); // Вызов отправки формы
        });
    }

    const confirmDeleteBtn = document.getElementById('confirm-delete-review-btn');
    if (confirmDeleteBtn) {
        confirmDeleteBtn.addEventListener('click', function () {
            if (currentReviewButton) {
                const section = currentReviewButton.closest('.review-section');
                section.remove();
                const deleteModal = bootstrap.Modal.getInstance(document.getElementById('deleteReviewConfirmModal'));
                deleteModal.hide();
                currentReviewButton = null;
            }
        });
    }

    const resetButton = document.querySelector('.account-image-reset');
    if(resetButton) {
        resetButton.onclick = () => {
            $('#uploadedAvatar').attr('src', window.defaultAvatarPath);
        };
    }

    document.querySelectorAll('input, select, textarea').forEach(input => {
        input.addEventListener('input', function () {
            this.classList.remove('is-invalid');
            const errorMessage = this.parentNode.querySelector('.error-message');
            if (errorMessage) errorMessage.remove();
        });
    });

    // =================================================================
    // 4. ОСНОВНАЯ ЛОГИКА ЗАПУСКА СКРИПТА
    // =================================================================

    const urlParams = new URLSearchParams(window.location.search);
    const pathParts = window.location.pathname.split('/').filter(Boolean);
    const lastPathPart = pathParts[pathParts.length - 1];
    const userId = !isNaN(parseInt(lastPathPart)) ? lastPathPart : urlParams.get('id');
    const isEditMode = Boolean(userId);

    if (isEditMode) {
        // Сначала загружаем все справочники
        await Promise.all([
            loadRolesSelect(),
            loadBranchesSelect()
        ]);

        // Теперь загружаем данные пользователя
        try {
            const response = await axios.get(`${contextPath}personal/card/${userId}`);
            fillFormData(response.data);
        } catch (error) {
            console.error('CRITICAL: Error loading personal data:', error);
        }
    } else {
        // В режиме создания просто загружаем справочники
        await Promise.all([
            loadRolesSelect(),
            loadBranchesSelect()
        ]);
        $('#branchesSelect').select2({
            placeholder: 'Выберите филиал',
            width: '100%'
        });
    }
});
