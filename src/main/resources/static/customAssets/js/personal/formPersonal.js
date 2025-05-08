const fileInput = document.getElementById('fileUpload');
const fileListDiv = document.getElementById('uploadedFilesList');
let files = [];

function loadBranchesSelect(selectedBranchIds = []) {
    axios.get(contextPath + 'branch/list')
        .then(response => {
            const branches = response.data;
            const select = document.getElementById('branchesSelect');
            select.innerHTML = '';

            branches.forEach(branch => {
                const option = document.createElement('option');
                option.value = branch.id;
                option.text = branch.name;
                if (selectedBranchIds.includes(String(branch.id))) {
                    option.selected = true;
                }
                select.appendChild(option);
            });

            if ($(select).hasClass('select2-hidden-accessible')) {
                $(select).trigger('change');
            } else {
                $(select).select2({
                    placeholder: 'Выберите филиал',
                    width: '100%'
                });
            }
        })
        .catch(error => {
            console.error('Ошибка при загрузке филиалов:', error);
        });
}

function loadRolesSelect() {
    const selectElement = document.getElementById('rolePersonal');
    selectElement.innerHTML = '<option value="" disabled selected>' + i18next.t('loading') + '</option>';

    axios.get(`${contextPath}personal/getRoles`)
        .then(function (response) {
            selectElement.innerHTML = '<option value="" data-i18n="selectRole" disabled selected>' + i18next.t('selectRole') + '</option>';

            if (response.data && Array.isArray(response.data)) {
                response.data.forEach(function (role) {
                    const option = document.createElement('option');
                    // Сохраняем оригинальное значение enum в value
                    option.setAttribute("data-i18n", role);
                    option.value = role;
                    // Используем i18n для перевода
                    option.textContent = i18next.t(role);
                    selectElement.appendChild(option);
                });
            } else {
                console.error('Неверный формат данных ролей', response.data);
                selectElement.innerHTML = '<option value="" disabled selected >' + i18next.t('errorLoadingRoles') + '</option>';
            }
        })
        .catch(function (error) {
            console.error('Ошибка при загрузке ролей:', error);
            selectElement.innerHTML = '<option value="" disabled selected >' + i18next.t('errorLoadingRoles') + '</option>';
        });
}


loadBranchesSelect();

document.addEventListener('DOMContentLoaded', function () {
    loadRolesSelect();
});



fileInput.addEventListener('change', (e) => {
    const invalidFiles = [];

    for (const file of e.target.files) {
        // Проверка формата файла - только PDF
        if (file.type === 'application/pdf' || file.name.toLowerCase().endsWith('.pdf')) {
            files.push({
                id: null,
                name: file.name,
                file: file,
                path: null,
            });
        } else {
            invalidFiles.push(file.name);
        }
    }

    if (invalidFiles.length > 0) {
        alert(`Следующие файлы не были загружены, так как они не в формате PDF: ${invalidFiles.join(', ')}`);
    }

    console.log(files);
    renderFiles();
});


function getFileIcon(file) {

    if (file.file && file.file.type) {
        if (file.file.type.startsWith('image/')) return '<i class="ti ti-photo me-2"></i>';
        if (file.file.type === 'application/pdf') return '<i class="ti ti-file-text me-2"></i>';
        if (file.file.type.startsWith('video/')) return '<i class="ti ti-video me-2"></i>';
    }
    return '<i class="ti ti-file me-2"></i>';
}

function renderFiles() {
    fileListDiv.innerHTML = '';
    files.forEach((file, idx) => {
        let downloadUrl;
        let fileIcon = '<i class="ti ti-file me-2"></i>';
        let fileName = file.name;


        if (file.file) {

            downloadUrl = URL.createObjectURL(file.file);
            fileIcon = getFileIcon(file);
        } else if (file.id) {
            // Это файл с сервера
            downloadUrl = `${contextPath}personal/documents/${file.id}/download`;
            // Определяем иконку по расширению файла
            if (file.name.toLowerCase().endsWith('.pdf')) {
                fileIcon = '<i class="ti ti-file-text me-2"></i>';
            } else if (/\.(jpg|jpeg|png|gif)$/i.test(file.name)) {
                fileIcon = '<i class="ti ti-photo me-2"></i>';
            }
        } else {

            downloadUrl = '#';
        }

        fileListDiv.innerHTML += `
            <div class="d-flex align-items-center justify-content-between p-2 border rounded mb-2">
                <span class="d-flex align-items-center">
                    ${fileIcon}
                    ${fileName}
                </span>
                <div class="d-flex gap-2">
                    <a href="${downloadUrl}" download="${fileName}" class="btn btn-icon btn-sm btn-outline-primary" title="Скачать">
                        <i class="ti ti-download"></i>
                    </a>
                    <button class="btn btn-icon btn-sm btn-outline-danger" title="Удалить" onclick="deleteFile(${idx})">
                        <i class="ti ti-trash"></i>
                    </button>
                </div>
            </div>
        `;
    });
}

window.deleteFile = function (idx) {
    const file = files[idx];
    files.splice(idx, 1);
    renderFiles();
};



function fillFormData(data) {
    document.getElementById('userId').value = data.id || '';
    document.getElementById('surname').value = data.surname || '';
    document.getElementById('name').value = data.name || '';
    document.getElementById('lastName').value = data.lastName || '';
    document.getElementById('phoneNumber').value = data.phoneNumber || '';
    document.getElementById('email').value = data.email || '';

    const roleElement = document.getElementById('rolePersonal');
    if (data.role) {
        roleElement.value = data.role;
    } else {
        console.log('Role not found');
        roleElement.value = 'ADMIN';
    }

    let selectedBranchIds = [];
    if (data.branches && Array.isArray(data.branches)) {
        selectedBranchIds = data.branches.map(b => String(b.id));
    }
    loadBranchesSelect(selectedBranchIds);
    console.log(window.contextPath + data.pathAvatar);
    if (data.pathAvatar) {
        console.log(window.contextPath + data.pathAvatar);
        document.getElementById('uploadedAvatar').src = window.contextPath + data.pathAvatar;
    }

    // Заполнение отзывов
    if (data.feedBacks && data.feedBacks.length > 0) {
        const container = document.getElementById('reviewSectionsContainer');
        container.innerHTML = '';
        data.feedBacks.forEach((feedback, index) => {
            const reviewSection = createReviewSection(index + 1, feedback);
            container.appendChild(reviewSection);
        });
    }

    // Заполнение документов
    if (data.documentFeedbacks && data.documentFeedbacks.length > 0) {
        files = []; // Очистим текущий массив файлов
        data.documentFeedbacks.forEach(doc => {
            files.push({
                id: doc.id,
                name: doc.name,
                path: doc.pathImage,
                file: null
            });
        });
        renderFiles();
    }
}

async function loadPersonalData(userId) {
    try {
        const response = await axios.get(`${contextPath}personal/${userId}`);
        console.log(response.data);
        fillFormData(response.data);
    } catch (error) {
        console.error('Error loading personal data:', error);
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const userId = new URLSearchParams(window.location.search).get('id') ||
        window.location.pathname.split('/').filter(Boolean).pop();
    const isEditMode = Boolean(userId);

    if (isEditMode) {
        loadPersonalData(userId);
    } else {
        loadBranchesSelect();
    }

    setupFormSubmission();
    setupFileUpload();
});


function createReviewSection(index, feedback = {}) {
    const section = document.createElement('div');
    section.className = 'review-section mb-4 border p-3 rounded position-relative';
    section.innerHTML = `
            <input type="hidden" class="feedback-id" value="${feedback.id || ''}">
            <button type="button" class="btn btn-icon btn-sm btn-outline-danger position-absolute top-0 end-0 mt-2 me-2"
                    title="Удалить отзыв" onclick="removeReviewSection(this)">
                <i class="ti ti-trash"></i>
            </button>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="reviewerName${index}" class="form-label">Имя</label>
                    <input type="text" class="form-control" id="reviewerName${index}" value="${feedback.name || ''}">
                </div>
                <div class="col-md-6 mb-3">
                    <label for="reviewerContact${index}" class="form-label">Контакт</label>
                    <input type="text" class="form-control" id="reviewerContact${index}" value="${feedback.phoneNumber || ''}">
                </div>
            </div>
            <div class="mb-3">
                <label for="reviewText${index}" class="form-label">Отзыв</label>
                <textarea class="form-control" id="reviewText${index}" rows="3">${feedback.text || ''}</textarea>
            </div>
        `;
    return section;
}

function createFileListItem(doc) {
    const item = document.createElement('div');
    item.className = 'd-flex align-items-center justify-content-between p-2 border rounded mb-2';
    item.innerHTML = `
            <span class="d-flex align-items-center">
                <i class="ti ti-file-text me-2"></i>
                ${doc.fileName || 'Document'}
            </span>
            <div class="d-flex gap-2">
                <button class="btn btn-icon btn-sm btn-outline-primary" title="Скачать"
                        onclick="downloadDocument('${doc.id}')">
                    <i class="ti ti-download"></i>
                </button>
                <button class="btn btn-icon btn-sm btn-outline-danger" title="Удалить"
                        onclick="deleteDocument('${doc.id}')">
                    <i class="ti ti-trash"></i>
                </button>
            </div>
        `;
    return item;
}

function setupFormSubmission() {
    const form = document.getElementById('formAdminUserProfile');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const userId = formData.get('userId');
        const url = `${contextPath}personal${userId ? `/${userId}` : ''}`;
        const method = userId ? 'put' : 'post';

        try {
            const response = await axios[method](url, Object.fromEntries(formData), {
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            window.location.href = `${contextPath}personal`;
        } catch (error) {
            console.error('Error saving personal data:', error);
        }
    });
}

function setupFileUpload() {
    const upload = document.getElementById('upload');
    const uploadedAvatar = document.getElementById('uploadedAvatar');
    const resetButton = document.querySelector('.account-image-reset');
    const pdfUpload = document.getElementById('fileUpload');
    const pdfUploadStatus = document.getElementById('pdfUploadStatus');

    if (upload) {
        upload.onchange = () => {
            if (upload.files[0]) {
                const reader = new FileReader();
                reader.onload = e => {
                    uploadedAvatar.src = e.target.result;
                };
                reader.readAsDataURL(upload.files[0]);
            }
        };
    }

    if (resetButton) {
        resetButton.onclick = () => {
            uploadedAvatar.src = defaultAvatarPath;
        };
    }

    if (pdfUpload) {
        pdfUpload.setAttribute('accept', 'application/pdf, .pdf');

        pdfUpload.onchange = () => {
            const filesCount = pdfUpload.files.length;
            pdfUploadStatus.textContent = filesCount > 0
                ? `Выбрано файлов: ${filesCount}`
                : 'Файл не выбран';
        };
    }
}


async function downloadDocument(docId) {
    try {
        const response = await axios.get(`${contextPath}personal/documents/${docId}/download`, {
            responseType: 'blob'
        });

        const blob = response.data;
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'document.pdf';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        a.remove();
    } catch (error) {
        console.error('Error downloading document:', error);
    }
}

async function deleteDocument(docId) {
    if (!confirm('Вы уверены, что хотите удалить этот документ?')) return;

    try {
        await axios.delete(`${contextPath}personal/documents/${docId}`);
        const fileIndex = files.findIndex(file => file.id === parseInt(docId) || file.id === docId);
        if (fileIndex !== -1) {
            files.splice(fileIndex, 1);
            renderFiles();
        } else {
            const fileItem = document.querySelector(`[onclick*="${docId}"]`).closest('.d-flex');
            if (fileItem) fileItem.remove();
        }
    } catch (error) {
        console.error('Error deleting document:', error);
    }
}

let currentReviewButton = null;

function removeReviewSection(button) {
    currentReviewButton = button;
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteReviewConfirmModal'));
    deleteModal.show();
}

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('confirm-delete-review-btn').addEventListener('click', function () {
        if (currentReviewButton) {
            const section = currentReviewButton.closest('.review-section');
            const feedbackId = section.querySelector('.feedback-id').value;

            section.remove();

            const deleteModal = bootstrap.Modal.getInstance(document.getElementById('deleteReviewConfirmModal'));
            deleteModal.hide();

            currentReviewButton = null;
        }
    });
});


let languageDropdown = document.getElementsByClassName('dropdown-language');

if (languageDropdown.length) {
    let dropdownItems = languageDropdown[0].querySelectorAll('.dropdown-item');

    for (let i = 0; i < dropdownItems.length; i++) {
        dropdownItems[i].addEventListener('click', function () {
            let currentLanguage = this.getAttribute('data-language'),
                selectedLangFlag = this.querySelector('.fi').getAttribute('class');

            for (let sibling of this.parentNode.children) {
                sibling.classList.remove('selected');
            }
            this.classList.add('selected');

            languageDropdown[0].querySelector('.dropdown-toggle .fi').className = selectedLangFlag;


            localStorage.setItem('userLanguage', currentLanguage);

            i18next.changeLanguage(currentLanguage, (err, t) => {
                if (err) return console.log('something went wrong loading', err);
                localize();
            });
        });
    }
}

function localize() {
    let i18nList = document.querySelectorAll('[data-i18n]');

    let currentLanguageEle = document.querySelector('.dropdown-item[data-language="' + i18next.language + '"]');

    if (currentLanguageEle) {
        currentLanguageEle.click();
    }

    i18nList.forEach(function (item) {
        item.innerHTML = i18next.t(item.dataset.i18n);
    });
}


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
                // Существующий документ
                formData.append(`documents[${index}].id`, fileObj.id);
                formData.append(`documents[${index}].name`, fileObj.name);
            } else if (fileObj.file) {
                // Новый документ
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

    for (const fieldPath in errors) {
        if (errors.hasOwnProperty(fieldPath)) {
            const errorMessage = errors[fieldPath];

            if (fieldPath.startsWith('feedBacks[')) {
                hasFeedbackErrors = true;

                const match = fieldPath.match(/feedBacks\[(\d+)\]\.(\w+)/);
                if (match) {
                    const index = parseInt(match[1]);
                    const fieldName = match[2];

                    const sections = document.querySelectorAll('.review-section');
                    if (index < sections.length) {
                        const section = sections[index];

                        let fieldElement;
                        if (fieldName === 'name') {
                            fieldElement = section.querySelector('input[id^="reviewerName"]');
                        } else if (fieldName === 'phoneNumber') {
                            fieldElement = section.querySelector('input[id^="reviewerContact"]');
                        } else if (fieldName === 'description') {
                            fieldElement = section.querySelector('textarea[id^="reviewText"]');
                        }

                        if (fieldElement) {
                            fieldElement.classList.add('is-invalid');
                            const errorSpan = document.createElement('div');
                            errorSpan.className = 'error-message text-danger mt-1';
                            errorSpan.textContent = errorMessage;
                            fieldElement.parentNode.appendChild(errorSpan);
                        }
                    }
                }
            } else {
                const fieldElement = document.getElementById(fieldPath);
                if (fieldElement) {
                    fieldElement.classList.add('is-invalid');
                    const errorSpan = document.createElement('div');
                    errorSpan.className = 'error-message text-danger mt-1';
                    errorSpan.textContent = errorMessage;
                    fieldElement.parentNode.appendChild(errorSpan);
                } else if (fieldPath === 'branchIds') {
                    const select2Container = document.querySelector('.select2-container');
                    if (select2Container) {
                        const errorSpan = document.createElement('div');
                        errorSpan.className = 'error-message text-danger mt-1';
                        errorSpan.textContent = errorMessage;
                        select2Container.parentNode.appendChild(errorSpan);
                    }
                } else {
                    console.warn('Не удалось найти поле для ошибки:', fieldPath);
                }
            }
        }
    }

    if (hasFeedbackErrors) {
        const testimonialsTab = document.querySelector('[data-bs-target="#testimonialsPersonal"]');
        if (testimonialsTab) {
            const tab = new bootstrap.Tab(testimonialsTab);
            tab.show();
        }
    }
}

function submitForm() {
    clearValidationErrors();

    const formData = collectFormData();
    const userId = document.getElementById('userId').value;
    const url = `${contextPath}personal${userId ? `/${userId}` : '/create'}`;
    const method = userId ? 'put' : 'post';

    axios({
        method: method,
        url: url,
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
        .then(response => {
            showI18nToast('success',
                'system.toast.title.success',
                'personal.toast.update.successful');
            setTimeout(() => {
                window.location.href = `${contextPath}personal`;
            }, 1500);
        })
        .catch(error => {
            console.error('Ошибка при отправке данных:', error);

            if (error.response && error.response.data) {
                console.error('Детали ошибки:', error.response.data);

                if (typeof error.response.data === 'object' && !Array.isArray(error.response.data)) {
                    displayValidationErrors(error.response.data);
                } else if (Array.isArray(error.response.data)) {
                    const errorMessages = error.response.data.map(err => err.defaultMessage || err.message).join('\n');
                    showI18nToast('error', 'system.toast.title.error', 'personal.toast.update.error');
                    console.log('Ошибка при сохранении: ' + errorMessages);
                } else if (typeof error.response.data === 'string') {
                    showI18nToast('error', 'system.toast.title.error', 'personal.toast.update.error');
                    console.log('Ошибка: ' + error.response.data);
                } else {
                    showI18nToast('error', 'system.toast.title.error', 'personal.toast.update.error');
                    console.log('Произошла ошибка при сохранении данных');
                }
            } else {
                console.log('Произошла ошибка при сохранении данных');
            }
        });
}


document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('input, select, textarea').forEach(input => {
        input.addEventListener('input', function () {
            this.classList.remove('is-invalid');

            const errorMessage = this.parentNode.querySelector('.error-message');
            if (errorMessage) {
                errorMessage.remove();
            }
        });
    });

    $('#branchesSelect').on('change', function () {
        const container = document.querySelector('.select2-container');
        if (container) {
            const errorMessage = container.parentNode.querySelector('.error-message');
            if (errorMessage) {
                errorMessage.remove();
            }
        }
    });
});


document.addEventListener('DOMContentLoaded', function () {
    const submitButton = document.querySelector('button[type="submit"]');
    if (submitButton) {
        submitButton.addEventListener('click', function (e) {
            e.preventDefault();
            submitForm();
        });
    }
});
