const fileInput = document.getElementById('fileUpload');
const fileListDiv = document.getElementById('uploadedFilesList');
let files = [];
let myDropzone;
const containerCitySelector = $('#cityId');
const containerBuildingCompanySelector = $('#buildingCompanyId');
const containerTopozoneSelector = $('#topozoneId');
let containerRegDistrictSelector = $('#regDistrictId');
let containerDistrictSelector = $('#districtId');
let imageId = 0;
let layoutBlock = 0;

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
            downloadUrl = `${contextPath}builder/documents/${file.id}/download`;
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


async function fillFormData(data) {
    document.getElementById('builderId').value = data.id;
    document.getElementById('name').value = data.name;
    document.getElementById('regDistrictId').value = data.regDistrictId;
    document.getElementById('cityId').value = data.cityId;
    document.getElementById('districtId').value = data.districtId;
    document.getElementById('topozoneId').value = data.topozoneId;
    document.getElementById('street').value = data.street;
    document.getElementById('houseNumber').value = data.houseNumber || '';
    document.getElementById('houseSection').value = data.houseSection || '';
    document.getElementById('totalFloor').value = data.totalFloor || '';
    document.getElementById('buildingCompanyId').value = data.buildingCompanyId;
    document.getElementById('deliveryType').value = data.deliveryType;
    document.getElementById('phoneNumber').value = data.phoneNumber;
    document.getElementById('description').value = data.description;
    document.getElementById('actionTitle').value = data.actionTitle;
    document.getElementById('actionDescription').value = data.actionDescription;
    document.getElementById('isAction').checked = data.isAction;
    document.getElementById('priceFileLink').textContent = substringFilePath(data.pathToPriceFile);
    document.getElementById('mortgageConditionsFileLink').textContent = substringFilePath(data.pathToMortgageConditionsFile);
    document.getElementById('chessPlanFileLink').textContent = substringFilePath(data.pathToChessPlanFile);

    if (data.pathAvatar) {
        console.log(window.contextPath + data.pathAvatar);
        document.getElementById('uploadedAvatar').src = window.contextPath + data.pathAvatar;
    }

    $('#buildingCompanyId').val(data.buildingCompanyId);
    $('#regDistrictId').val(data.regDistrictId);

    await loadCitiesByRegDistrictId(data.regDistrictId);
    $('#cityId').val(data.cityId);

    await loadDistrictByCity(data.cityId);
    $('#districtId').val(data.districtId);

    await loadTopZonesByDistrict(data.districtId);
    $('#topozoneId').val(data.topozoneId);

    let layoutContainer = $('#layoutsContainer');
    if (layoutContainer.length) {
        data.layoutDto.forEach(function (item) {
            layoutContainer.append(` <div class="layout-block col-12 row border rounded p-3 mb-4">
        <div class="col-12 text-end mb-2">
            <a href="javascript:void(0);" class="ti ti-trash text-danger delete-layout" style="cursor: pointer;"></a>
        </div>
        <div class="col-12">
            <label class="form-label" data-i18n="builder.layout.name">${translate('builder.layout.name')}</label>
            <input type="text" class="form-control layout-name" placeholder="Планировка 2к левое крыло" value="${item.name}"
            id="layoutDto[${layoutBlock}].name">
        </div>
        <div class="col">
            <label class="form-label" data-i18n="builder.layout.price">${translate('builder.layout.price')}</label>
            <input type="number" class="form-control layout-price" placeholder="500" value="${item.priceByM2}"
            id="layoutDto[${layoutBlock}].priceByM2">
        </div>
        <div class="col">
            <label class="form-label" data-i18n="builder.layout.room">${translate('builder.layout.room')}</label>
            <input type="number" class="form-control layout-rooms" placeholder="2" value="${item.rooms}"
            id="layoutDto[${layoutBlock}].rooms">
        </div>
        <div class="col">
            <label class="form-label" data-i18n="builder.layout.totalArea">${translate('builder.layout.totalArea')}</label>
            <input type="number" class="form-control layout-total-area" placeholder="36" value="${item.totalArea}"
            id="layoutDto[${layoutBlock}].totalArea">
        </div>
        <div class="col">
            <label class="form-label" data-i18n="builder.layout.livingRoom">${translate('builder.layout.livingRoom')}</label>
            <input type="number" class="form-control layout-living-area" placeholder="21" value="${item.livingArea}"
            id="layoutDto[${layoutBlock}].livingArea">
        </div>
        <div class="col">
            <label class="form-label" data-i18n="builder.layout.kitchenRoom">${translate('builder.layout.kitchenRoom')}</label>
            <input type="number" class="form-control layout-kitchen-area" placeholder="12" value="${item.kitchenArea}"
            id="layoutDto[${layoutBlock}].kitchenArea">
        </div>
        <div class="col-12">
            <div class="form-check mt-4">
                <input class="form-check-input layout-visible" type="checkbox" ${item.visibleForSite ? 'checked' : ''}>
                <label class="form-check-label" data-i18n="builder.layout.showSite">${translate('builder.layout.showSite')}</label>
            </div>
        </div>
        <div class="col-md-4">
            <label class="form-label" data-i18n="builder.layout.description">${translate('builder.layout.description')}</label>
            <textarea class="form-control layout-description" rows="3"
            id="layoutDto[${layoutBlock}].description">${item.description}</textarea>
        </div>
        <div class="col-12 mt-3">
            <div class="row g-3">
                <div class="col-md-4">
                    <div class="card h-100 p-2 text-center">
                        <img src="${item.pathImage1 ? '/admin/' + item.pathImage1 : 'https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg'}" class="img-fluid mb-2" alt="error"
                        style="height: 204px; width: 100%;">
                        <button type="button" id="${imageId + 1}" class="btn btn-label-primary btn-select"
                                style="left: 13%;" data-i18n="builder.layout.load"
                                id="layoutDto[${layoutBlock}].file1">${translate('builder.layout.load')}</button>
                        <input type="file" class="form-control files layout-file1" accept="image/*,video/*" style="display: none;">
                        <input type="text" class="paths layout-path1" style="display: none;" value="${item.pathImage1}">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card h-100 p-2 text-center">
                        <img src="${item.pathImage2 ? '/admin/' + item.pathImage2 : 'https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg'}" class="img-fluid mb-2" alt="error"
                        style="height: 204px; width: 100%;">
                        <button type="button" id="${imageId + 2}" class="btn btn-label-primary btn-select"
                                style="left: 13%;" data-i18n="builder.layout.load"
                                id="layoutDto[${layoutBlock}].file2">${translate('builder.layout.load')}</button>
                        <input type="file" class="files layout-file2" accept="image/*,video/*" style="display: none;">
                        <input type="text" class="paths layout-path2" style="display: none;" value="${item.pathImage2}">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card h-100 p-2 text-center">
                        <img src="${item.pathImage3 ? '/admin/' + item.pathImage3 : 'https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg'}" class="img-fluid mb-2" alt="error"
                        style="height: 204px; width: 100%;">
                        <button type="button" id="${imageId + 3}" class="btn btn-label-primary btn-select"
                                style="left: 13%;" data-i18n="builder.layout.load"
                                id="layoutDto[${layoutBlock}].file3">${translate('builder.layout.load')}</button>
                        <input type="file" class="files layout-file3" accept="image/*,video/*" style="display: none;">
                        <input type="text" class="paths layout-path3" style="display: none;" value="${item.pathImage3}">
                    </div>
                </div>
            </div>
        </div>
    </div>`);
        });
    }
}

async function loadBuilderData(builderId) {
    try {
        const response = await axios.get(`${contextPath}builder/${builderId}`);
        console.log(response.data);
        fillFormData(response.data);
        myDropzone = new Dropzone("#dropzone-basic", {
            url: "/upload",
            autoProcessQueue: false,
            uploadMultiple: true,
            parallelUploads: 5,
            maxFiles: 5,
            paramName: "filesDto",
            previewTemplate: document.querySelector("#preview-template").innerHTML,
            init: function () {
                const dz = this;
                console.log(dz);
                response.data.filesDto.forEach(fileInfo => {
                    console.log(fileInfo);
                    const mockFile = {
                        name: fileInfo.name,
                        size: Number(fileInfo.size) || 0,
                        accepted: true,
                        dataURL: fileInfo.pathImage,
                    };
                    dz.emit("addedfile", mockFile);
                    dz.emit("thumbnail", mockFile, '/admin/' + fileInfo.pathImage);
                    dz.emit("complete", mockFile);
                    dz.files.push(mockFile);
                });
            }
        });

    } catch (error) {
        console.error('Error loading builder data:', error);
    }
}

document.addEventListener('DOMContentLoaded', async function () {
    const builderId = new URLSearchParams(window.location.search).get('id') ||
        window.location.pathname.split('/').filter(Boolean).pop();
    const isEditMode = Boolean(Number(builderId));

    handleSelectChange('#regDistrictId', [containerCitySelector, containerDistrictSelector, containerTopozoneSelector], loadCitiesByRegDistrictId);
    handleSelectChange('#cityId', [containerDistrictSelector, containerTopozoneSelector], loadDistrictByCity);
    handleSelectChange('#districtId', [containerTopozoneSelector], loadTopZonesByDistrict);
    await loadRegDistrict();
    await loadBuildCompanies();
    if (isEditMode) {
        loadBuilderData(builderId);
    } else {
        myDropzone = new Dropzone("#dropzone-basic", {
            url: "/upload",
            autoProcessQueue: false,
            uploadMultiple: true,
            parallelUploads: 5,
            maxFiles: 5,
            paramName: "filesDto",
            previewTemplate: document.querySelector("#preview-template").innerHTML
        });
    }
    setupFormSubmission();
    setupFileUpload();
    includeAd();

    document.getElementById('addLayoutBtn').addEventListener('click', function () {
        const container = document.getElementById('layoutsContainer');
        const layoutHTML = `
    <div class="layout-block col-12 row border rounded p-3 mb-4">
        <div class="col-12 text-end mb-2">
            <a href="javascript:void(0);" class="ti ti-trash text-danger delete-layout" style="cursor: pointer;"></a>
        </div>
        <div class="col-12">
            <label class="form-label" data-i18n="builder.name">${translate('builder.name')}</label>
            <input type="text" class="form-control layout-name" placeholder="Планировка 2к левое крыло"
            id="layoutDto[${layoutBlock}].name">
        </div>
        <div class="col">
            <label class="form-label" data-i18n="builder.layout.price">${translate('builder.layout.price')}</label>
            <input type="number" class="form-control layout-price" placeholder="500"
            id="layoutDto[${layoutBlock}].priceByM2">
        </div>
        <div class="col">
            <label class="form-label" data-i18n="builder.layout.room">${translate('builder.layout.room')}</label>
            <input type="number" class="form-control layout-rooms" placeholder="2"
            id="layoutDto[${layoutBlock}].rooms">
        </div>
        <div class="col">
            <label class="form-label" data-i18n="builder.layout.totalArea">${translate('builder.layout.totalArea')}</label>
            <input type="number" class="form-control layout-total-area" placeholder="36"
            id="layoutDto[${layoutBlock}].totalArea">
        </div>
        <div class="col">
            <label class="form-label" data-i18n="builder.layout.livingRoom">${translate('builder.layout.livingRoom')}</label>
            <input type="number" class="form-control layout-living-area" placeholder="21"
            id="layoutDto[${layoutBlock}].livingArea">
        </div>
        <div class="col">
            <label class="form-label" data-i18n="builder.layout.kitchenRoom">${translate('builder.layout.kitchenRoom')}</label>
            <input type="number" class="form-control layout-kitchen-area" placeholder="12"
            id="layoutDto[${layoutBlock}].kitchenArea">
        </div>
        <div class="col-12">
            <div class="form-check mt-4">
                <input class="form-check-input layout-visible" type="checkbox">
                <label class="form-check-label" data-i18n="builder.layout.showSite">
${translate('builder.layout.showSite')}</label>
            </div>
        </div>
        <div class="col-md-4">
            <label class="form-label" data-i18n="builder.layout.description">${translate('builder.layout.description')}</label>
            <textarea class="form-control layout-description" id="layoutDto[${layoutBlock}].description" rows="3"></textarea>
        </div>
        <div class="col-12 mt-3">
            <div class="row g-3">
                <div class="col-md-4">
                    <div class="card h-100 p-2 text-center">
                        <img src="https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg" class="img-fluid mb-2" alt="error"
                        style="height: 204px; width: 100%;">
                        <button type="button" id="${imageId + 1}" class="btn btn-label-primary btn-select"
                                style="left: 13%;" data-i18n="builder.layout.load"
                                data-imgId="layoutDto[${layoutBlock}].file1">${translate('builder.layout.load')}</button>
                        <input type="file" class="form-control files layout-file1" accept="image/*,video/*" style="display: none;">
                        <input type="text" class="paths layout-path1" style="display: none;">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card h-100 p-2 text-center">
                        <img src="https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg" class="img-fluid mb-2" alt="error"
                        style="height: 204px; width: 100%;">
                        <button type="button" id="${imageId + 2}" class="btn btn-label-primary btn-select"
                                style="left: 13%;" data-i18n="builder.layout.load"
                                data-imgId="layoutDto[${layoutBlock}].file2">${translate('builder.layout.load')}</button>
                        <input type="file" class="files layout-file2" accept="image/*,video/*" style="display: none;">
                        <input type="text" class="paths layout-path2" style="display: none;">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card h-100 p-2 text-center">
                        <img src="https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg" class="img-fluid mb-2" alt="error"
                        style="height: 204px; width: 100%;">
                        <button type="button" id="${imageId + 3}" class="btn btn-label-primary btn-select"
                                style="left: 13%;" data-i18n="builder.layout.load"
                                data-imgId="layoutDto[${layoutBlock}].file">${translate('builder.layout.load')}</button>
                        <input type="file" class="files layout-file3" accept="image/*,video/*" style="display: none;">
                        <input type="text" class="paths layout-path3" style="display: none;">
                    </div>
                </div>
            </div>
        </div>
    </div>
    `;
        imageId += 3;
        layoutBlock += 1;
        container.insertAdjacentHTML('beforeend', layoutHTML);
    });

    document.getElementById('layoutsContainer').addEventListener('click', function (e) {
        if (e.target.classList.contains('delete-layout')) {
            e.target.closest('.layout-block').remove();
        }
    });

    document.querySelectorAll('input[type="file"].files').forEach(input => {
        input.setAttribute('accept', 'application/pdf, .pdf');
    });

    document.querySelectorAll('input.file-pdf').forEach(input => {
        input.setAttribute('accept', 'application/pdf, .pdf');
    });


});

function setupFormSubmission() {
    const form = document.getElementById('formBuilderProperty');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const builderId = formData.get('builderId');
        const url = `${contextPath}builder${builderId ? `/${builderId}` : ''}`;
        const method = builderId ? 'put' : 'post';

        try {
            const response = await axios[method](url, Object.fromEntries(formData), {
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            window.location.href = `${contextPath}builder`;
        } catch (error) {
            console.error('Error saving builder data:', error);
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
            uploadedAvatar.src = defaultAvatarPath.slice(1, -1);
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

function collectFormData(selector = 'form') {
    const formData = new FormData();
    const $elements = $(`${selector} input, ${selector} select, ${selector} textarea`);
    $elements.each(function () {
        const $el = $(this);
        const name = $el.attr('id');
        if (!name) return;
        if ($el.attr('type') === 'checkbox') {
            formData.append(name, $el.prop('checked'));
        } else if ($el.attr('type') === 'file') {
            const files = $el[0].files;
            if (files.length > 0) {
                formData.append(name, files[0]);
            }
        } else {
            formData.append(name, $el.val());
        }
    });

    $('#layoutsContainer .layout-block').each(function (i) {
        const $block = $(this);
        const prefix = `layoutDto[${i}]`;

        let file1 = $block.find('.layout-file1')[0].files[0];
        if (file1) {
            formData.append(`${prefix}.file1`, file1);
        }
        console.log(file1);
        let file2 = $block.find('.layout-file2')[0].files[0];
        if (file2) {
            formData.append(`${prefix}.file2`, file2);
        }

        let file3 = $block.find('.layout-file3')[0].files[0];
        if (file3) {
            formData.append(`${prefix}.file3`, file3);
        }

        formData.append(`${prefix}.path1`, $block.find('.layout-path1').val());
        formData.append(`${prefix}.path2`, $block.find('.layout-path2').val());
        formData.append(`${prefix}.path3`, $block.find('.layout-path2').val());
    });

    console.log(myDropzone);
    appendDropzoneFilesToFormData(myDropzone,formData);

    return formData;
}

// function collectFormData() {
//
//     const formData = new FormData();
//
//     const builderId = document.getElementById('builderId').value;
//     if (builderId) formData.append('id', builderId);
//
//     formData.append('surname', document.getElementById('surname').value);
//     formData.append('name', document.getElementById('name').value);
//     formData.append('lastName', document.getElementById('lastName').value);
//     formData.append('phoneNumber', document.getElementById('phoneNumber').value);
//     formData.append('email', document.getElementById('email').value);
//     formData.append('role', document.getElementById('rolebuilder').value);
//
//     const password = document.getElementById('password').value;
//     const confirmPassword = document.getElementById('confirmPassword').value;
//     if (password) {
//         formData.append('password', password);
//         formData.append('confirmPassword', confirmPassword);
//     }
//
//     const avatarInput = document.getElementById('upload');
//     if (avatarInput.files.length > 0) {
//         formData.append('avatar', avatarInput.files[0]);
//     }
//     return formData;
// }

function clearValidationErrors() {
    document.querySelectorAll('.error-message').forEach(el => el.remove());

    document.querySelectorAll('.is-invalid').forEach(el => {
        el.classList.remove('is-invalid');
    });
}

function displayValidationErrors(errors) {
    console.log(errors);
    clearValidationErrors();

    let hasFeedbackErrors = false;

    for (const fieldPath in errors) {
        if (!errors.hasOwnProperty(fieldPath)) continue;

        const errorMessage = errors[fieldPath];
        console.log("Error message:", errorMessage);
        console.log("Field path:", fieldPath);

        if (errorMessage.includes('layout')) {
            console.log('no');
        }

        hasFeedbackErrors = true;

        let targetElement;

        if (fieldPath.includes('layoutDto') && fieldPath.includes('file')) {
            targetElement = document.querySelector(`[data-imgId="${fieldPath}"]`);
        } else {
            targetElement = document.getElementById(fieldPath);
        }

        if (targetElement) {
            targetElement.classList.add('is-invalid');
            const errorDiv = createErrorDiv(errorMessage);
            targetElement.parentNode.appendChild(errorDiv);
        } else {
            console.warn('Не удалось найти поле для ошибки:', fieldPath);
        }
    }

    if (hasFeedbackErrors) {
        const testimonialsTab = document.querySelector('[data-bs-target="#testimonialsbuilder"]');
        if (testimonialsTab) {
            new bootstrap.Tab(testimonialsTab).show();
        }
    }
}

function createErrorDiv(errorMessage) {
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message invalid-feedback';
    const errorKey = `builder.validation.${errorMessage}`;
    errorDiv.setAttribute('data-i18n', errorKey);
    errorDiv.textContent = i18next.t(errorKey);
    return errorDiv;
}

function submitForm() {
    clearValidationErrors();

    const formData = collectFormData();
    const builderId = document.getElementById('builderId').value;
    const url = `${contextPath}builder${builderId ? `/${builderId}/save` : '/save'}`;
    const method = builderId ? 'put' : 'post';
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
                'builder.toast.update.successful');
            setTimeout(() => {
                // window.location.href = `${contextPath}builder`;
            }, 1500);
        })
        .catch(error => {
            console.error('Ошибка при отправке данных:', error);

            if (error.response && error.response.data) {
                console.error('Детали ошибки:', error.response.data);

                if (typeof error.response.data === 'object' && !Array.isArray(error.response.data)) {
                    console.log(1);
                    displayValidationErrors(error.response.data);
                } else if (Array.isArray(error.response.data)) {
                    console.log(2);
                    const errorMessages = error.response.data.map(err => err.defaultMessage || err.message).join('\n');
                    showI18nToast('error', 'system.toast.title.error', 'builder.toast.update.error');
                    console.log('Ошибка при сохранении: ' + errorMessages);
                } else if (typeof error.response.data === 'string') {
                    console.log(3);
                    showI18nToast('error', 'system.toast.title.error', 'builder.toast.update.error');
                    console.log('Ошибка: ' + error.response.data);
                } else {
                    console.log(4);
                    showI18nToast('error', 'system.toast.title.error', 'builder.toast.update.error');
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
});


document.addEventListener('DOMContentLoaded', function () {
    const submitButtons = document.querySelectorAll('button[type="submit"]');
    submitButtons.forEach(function (submitButton) {
        submitButton.addEventListener('click', function (e) {
            e.preventDefault();
            submitForm();
        });
    });

    $('#deliveryType').on('change', function () {
        console.log(this.selectedIndex);
        console.log(this.options);
        const selectedIndex = this.selectedIndex;

        Array.from(this.options).forEach(opt => {
            const key = opt.getAttribute('data-i18n');
            if (key) opt.textContent = translate(key);
        });

        this.selectedIndex = selectedIndex;
    });
});

function appendDropzoneFilesToFormData(dropzoneInstance, formData) {
    if (!dropzoneInstance || !Array.isArray(dropzoneInstance.files)) {
        console.warn('Dropzone instance or its files array is undefined or invalid:', dropzoneInstance);
        return;
    }

    dropzoneInstance.files.forEach(function (file, index) {
        if (file.path !== undefined && file.path !== '') {
            formData.append(`filesDto[${index}].pathImage`, file.path);
        } else {
            formData.append(`filesDto[${index}].file`, file);
        }
    });
}


function loadCitiesByRegDistrictId(regDistrictId) {
    if (!regDistrictId) return;
    return fetch(`/admin/city/get?regDistrictId=` + encodeURIComponent(regDistrictId))
        .then(response => response.json())
        .then(data => {
            if (containerCitySelector) {
                containerCitySelector.prop('disabled', false);
                containerDistrictSelector.prop('disabled', true);
                containerTopozoneSelector.prop('disabled', true);
                let optionFirst = containerCitySelector.find('option').first().prop('outerHTML');
                containerCitySelector.empty();
                containerCitySelector.append(optionFirst);
                data.forEach(el => {
                    containerCitySelector.append(`<option value="${el.id}">${el.name}</option>`);
                });
            }
        })
        .catch(error => {
            console.error('Error loading user data:', error);
            showErrorMessage('Ошибка при загрузке данных');
        });
}

function loadBuildCompanies() {
    return fetch(`/admin/builderCompany/getAll`)
        .then(response => response.json())
        .then(data => {
            if (containerBuildingCompanySelector) {
                let optionFirst = containerBuildingCompanySelector.find('option').first().prop('outerHTML');
                containerBuildingCompanySelector.empty();
                containerBuildingCompanySelector.append(optionFirst);
                data.forEach(el => {
                    containerBuildingCompanySelector.append(`<option value="${el.id}">${el.name}</option>`);
                });
            }
        })
        .catch(error => {
            console.error('Error loading user data:', error);
            showErrorMessage('Ошибка при загрузке данных');
        });
}

function loadTopZonesByDistrict(districtId) {
    if (!districtId) return;
    return fetch(`/admin/topozone/get?districtId=` + encodeURIComponent(districtId))
        .then(response => response.json())
        .then(data => {
            if (containerTopozoneSelector) {
                containerTopozoneSelector.prop('disabled', false);
                let optionFirst = containerTopozoneSelector.find('option').first().prop('outerHTML');
                containerTopozoneSelector.empty();
                containerTopozoneSelector.append(optionFirst);
                data.forEach(el => {
                    containerTopozoneSelector.append(`<option value="${el.id}">${el.name}</option>`);
                });
            }
        })
        .catch(error => {
            console.error('Error loading user data:', error);
            showErrorMessage('Ошибка при загрузке данных');
        });
}

function loadRegDistrict() {
    return fetch(`/admin/regDistrict/getAll`)
        .then(response => response.json())
        .then(data => {
            if (containerRegDistrictSelector) {
                containerCitySelector.prop('disabled', true);
                containerDistrictSelector.prop('disabled', true);
                containerTopozoneSelector.prop('disabled', true);
                let firstOption = containerRegDistrictSelector.find('option').first();
                containerRegDistrictSelector.empty();
                containerRegDistrictSelector.append(firstOption);
                data.forEach(el => {
                    containerRegDistrictSelector.append(`<option value="${el.id}">${el.name}</option>`);
                });
            }
        })
        .catch(error => {
            console.error('Error loading user data:', error);
            showErrorMessage('Ошибка при загрузке данных');
        });
}

function loadDistrictByCity(cityId) {
    if (!cityId) return;
    return fetch(`/admin/district/get?cityId=` + encodeURIComponent(cityId))
        .then(response => response.json())
        .then(data => {
            if (containerDistrictSelector) {
                containerDistrictSelector.prop('disabled', false);
                containerTopozoneSelector.prop('disabled', true);
                let optionFirst = containerDistrictSelector.find('option').first().prop('outerHTML');
                containerDistrictSelector.empty();
                containerDistrictSelector.append(optionFirst);
                data.forEach(el => {
                    containerDistrictSelector.append(`<option value="${el.id}">${el.name}</option>`);
                });
            }
        })
        .catch(error => {
            console.error('Error loading user data:', error);
            showErrorMessage('Ошибка при загрузке данных');
        });
}

const translate = (key) => {
    return i18next.isInitialized ? i18next.t(key) : key;
};

function substringFilePath(path) {
    if (!path) return;
    return path.split('/').pop();
}

function showErrorMessage(message) {
    // Здесь можно добавить логику отображения ошибок пользователю
    // Например, через всплывающее уведомление или в определенном месте на странице
    alert(message);
}

function includeAd() {
    $("#isAction").on('click', function () {
        const title = $('#actionTitle');
        const description = $('#actionDescription');
        if ($(this).prop('checked')) {
            title.prop('required', true).prop('disabled', false);
            description.prop('required', true).prop('disabled', false);
        } else {
            title.prop('required', false).prop('disabled', true);
            description.prop('required', false).prop('disabled', true);
        }
    });
}


function handleSelectChange(selectorId, targetSelectors, loaderFn) {
    $(selectorId).on('change', function () {
        const value = $(this).val();
        if (value) {
            loaderFn(value);
            console.log(value)
        } else {
            targetSelectors.forEach(el => {
                el.prop('disabled', true);
                el.find('option:not(:first)').remove();
            });
        }
    });
}
