class GenericAPIService {
    constructor(config, entityType = 'json') {
        this.baseURL = config.baseURL;
        this.endpoints = config.endpoints;
        this.entityType = entityType;
    }

    async request(endpoint, options = {}) {
        const url = `${this.baseURL}${endpoint}`;
        console.log(url);
        try {
            const response = await fetch(url, options);
            if (!response.ok) {
                const errorData = await this._parseErrorResponse(response);
                throw { status: response.status, data: errorData };
            }
            return await this._parseResponse(response);
        } catch (error) {
            throw error;
        }
    }

    async _parseResponse(response) {
        const contentType = response.headers.get('Content-Type');
        if (contentType && contentType.includes('application/json')) {
            return response.json();
        }
        return null;
    }

    async _parseErrorResponse(response) {
        try {
            return await response.json();
        } catch {
            return { message: 'Failed to parse error response' };
        }
    }

    getAll(page = 0, size = 5, filters = {}) {
        let queryParams = `page=${page}&size=${size}`;
        for (const key in filters) {
            if (Object.prototype.hasOwnProperty.call(filters, key) && filters[key]) {
                queryParams += `&${encodeURIComponent(key)}=${encodeURIComponent(filters[key])}`;
            }
        }
        const endpoint = `${this.endpoints.getAll}?${queryParams}`;
        return this.request(endpoint);
    }

    getById(id) {
        const endpoint = this.endpoints.getById.replace('{id}', id);
        return this.request(endpoint);
    }

    create(entityData, entityType = this.entityType) {
        const endpoint = this.endpoints.create;
        const options = this._prepareBody('POST', entityData, entityType);
        return this.request(endpoint, options);
    }

    update(id, entityData, entityType = this.entityType) {
        const endpoint = this.endpoints.update.replace('{id}', id);
        const options = this._prepareBody('PUT', entityData, entityType);
        return this.request(endpoint, options);
    }

    delete(id) {
        const endpoint = this.endpoints.delete.replace('{id}', id);
        return this.request(endpoint, { method: 'DELETE' });
    }

    _prepareBody(method, data, entityType) {
        let options = {
            method: method,
            headers: { 'Accept': 'application/json' }
        };

        if (entityType === 'formdata') {
            options.body = data;
            delete options.headers['Content-Type'];
        } else {
            options.body = JSON.stringify(data);
            options.headers['Content-Type'] = 'application/json';
        }
        return options;
    }
}


class Pagination {
    constructor(containerId, onPageChange) {
        this.container = document.getElementById(containerId);
        this.onPageChange = onPageChange;
        this.currentPage = 0;
        this.totalPages = 0;

        if (this.container) {
            this.container.addEventListener('click', this._handleClick.bind(this));
        } else {
            console.error(`Pagination container with ID "${containerId}" not found.`);
        }
    }

    setup(totalPages, currentPage) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.render();
    }

    _handleClick(e) {
        const link = e.target.closest('a');
        if (link && this.container.contains(link)) {
            e.preventDefault();
            const page = link.getAttribute('data-page');
            if (page !== null) {
                const pageNumber = parseInt(page, 10);
                if (!isNaN(pageNumber) && pageNumber >= 0 && pageNumber < this.totalPages) {
                    this.onPageChange(pageNumber);
                } else {
                    console.error("Invalid page number requested:", page);
                }
            }
        }
    }

    render() {
        if (!this.container) return;

        if (this.totalPages <= 1) {
            this.container.innerHTML = '';
            return;
        }

        let paginationItems = '';
        paginationItems += this._createNavItem(0, '<i class="ti ti-chevrons-left ti-xs"></i>', 'first', this.currentPage > 0);
        paginationItems += this._createNavItem(this.currentPage - 1, '<i class="ti ti-chevron-left ti-xs"></i>', 'prev', this.currentPage > 0);
        paginationItems += this._renderPageNumbers();
        paginationItems += this._createNavItem(this.currentPage + 1, '<i class="ti ti-chevron-right ti-xs"></i>', 'next', this.currentPage < this.totalPages - 1);
        paginationItems += this._createNavItem(this.totalPages - 1, '<i class="ti ti-chevrons-right ti-xs"></i>', 'last', this.currentPage < this.totalPages - 1);

        this.container.innerHTML = paginationItems;
    }

    _renderPageNumbers() {
        const ellipsisThreshold = 2;
        const ellipsisOffset = 1;
        let paginationItems = '';

        const addFirstPageAndEllipsis = () => {
            paginationItems += this._createPageItem(0, '1');
            if (this.currentPage > ellipsisThreshold + ellipsisOffset) {
                paginationItems += this._createDisabledItem('...');
            }
        };

        const addLastPageAndEllipsis = () => {
            if (this.currentPage < this.totalPages - (ellipsisThreshold + ellipsisOffset + 1)) {
                paginationItems += this._createDisabledItem('...');
            }
            paginationItems += this._createPageItem(this.totalPages - 1, this.totalPages);
        };

        const startPage = Math.max(0, this.currentPage - ellipsisOffset);
        const endPage = Math.min(this.totalPages - 1, this.currentPage + ellipsisOffset);

        if (startPage > 0) {
            addFirstPageAndEllipsis();
        }

        for (let i = startPage; i <= endPage; i++) {
            paginationItems += this._createPageItem(i, i + 1, i === this.currentPage);
        }

        if (endPage < this.totalPages - 1) {
            addLastPageAndEllipsis();
        }

        return paginationItems;
    }

    _createNavItem(page, content, className, isEnabled) {
        return `<li class="page-item ${className} ${isEnabled ? '' : 'disabled'}"> <a class="page-link" href="#" data-page="${page}">${content}</a> </li>`;
    }

    _createPageItem(page, text, isActive = false) {
        return `<li class="page-item ${isActive ? 'active' : ''}"> <a class="page-link" href="#" data-page="${page}">${text}</a> </li>`;
    }

    _createDisabledItem(text) {
        return `<li class="page-item disabled"> <span class="page-link">${text}</span> </li>`;
    }
}


class GenericTable {
    constructor({ tableBodyId, columns, onEdit, onDelete, noDataText = 'No data', editUrl = '/edit/' }) {
        this.tableBody = document.getElementById(tableBodyId);
        this.columns = columns;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
        this.noDataText = noDataText;
        this.editUrl = editUrl;

        if (!this.tableBody) {
            console.error(`Table body with ID "${tableBodyId}" not found.`);
        }
    }

    populate(items) {
        if (!this.tableBody) return;

        this.tableBody.innerHTML = '';

        if (!items || items.length === 0) {
            this._renderNoDataRow();
            return;
        }

        const fragment = document.createDocumentFragment();
        items.forEach(item => {
            const row = this._createRow(item);
            fragment.appendChild(row);
        });
        this.tableBody.appendChild(fragment);
    }

    _createActionButton(action, id, iconClass, className) {
        if (action === 'edit') {
            return `<a href="${this.editUrl}${id}" class="btn btn-sm ${className}"><i class="${iconClass}"></i></a>`;
        }
        return `<button class="btn btn-sm ${className}" data-action="${action}" data-id="${id}"><i class="${iconClass}"></i></button>`;
    }

    _createRow(item) {
        const row = document.createElement('tr');

        let cells = '';
        this.columns.forEach(col => {
            let cellValue = '';
            if (typeof col.render === 'function') {
                cellValue = col.render(item);
            } else if (col.key) {
                const value = item[col.key];
                cellValue = (value === null || value === undefined || value === '') ? '-' : value;
            }
            cells += `<td class="text-center">${cellValue}</td>`;
        });

        const actionCell = `
            <td class="text-center action-buttons">
                ${this._createActionButton('edit', item.id, 'ti ti-pencil', 'btn-warning me-2')}
                ${this._createActionButton('delete', item.id, 'ti ti-trash', 'btn-danger')}
            </td>
        `;

        row.innerHTML = cells + actionCell;

        const actionTd = row.querySelector('td.action-buttons');
        if (actionTd) {
            actionTd.style.display = 'flex';
            actionTd.style.justifyContent = 'center';
            actionTd.style.alignItems = 'center';
            actionTd.style.gap = '5px';
        }

        const deleteButton = row.querySelector('button[data-action="delete"]');
        if (deleteButton) {
            const id = deleteButton.getAttribute('data-id');
            deleteButton.addEventListener('click', () => {
                 if (this.onDelete) {
                     this.onDelete(id);
                 }
            });
        }

        return row;
    }

    _renderNoDataRow() {
        if (!this.tableBody) return;
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="${this.columns.length + 1}" class="text-center">${this.noDataText}</td>`;
        this.tableBody.appendChild(row);
    }
}


class GenericDeleteConfirmModal {
    constructor(modalId, confirmButtonId, onConfirm) {
        this.modal = document.getElementById(modalId);
        this.confirmButton = document.getElementById(confirmButtonId);
        this.onConfirm = onConfirm;
        this.entityId = null;
        this.modalInstance = null;

        if (this.modal && this.confirmButton) {
            this.confirmButton.addEventListener('click', this._handleConfirm.bind(this));
            this.initializeModal();
        } else {
            console.error(`Modal (ID: ${modalId}) or Confirm Button (ID: ${confirmButtonId}) not found.`);
        }
    }

    _handleConfirm() {
        if (this.entityId !== null && this.onConfirm) {
            this.onConfirm(this.entityId);
            this.close();
        }
    }

    open(id) {
        if (!this.modalInstance) return;
        this.entityId = id;
        this.modalInstance.show();
    }

    close() {
        if (!this.modalInstance) return;
        this.modalInstance.hide();
        this.entityId = null;
    }

    initializeModal() {
        if (this.modal && typeof bootstrap !== 'undefined' && bootstrap.Modal) {
            this.modalInstance = new bootstrap.Modal(this.modal, {
                backdrop: 'static',
                keyboard: false
            });
        } else if (!this.modal) {
            console.error("Modal element not found for initialization.");
        } else {
            console.error("Bootstrap Modal is not available. Make sure Bootstrap's JavaScript is loaded.");
        }
    }
}


class GenericCRUDApp {
    constructor(config) {
        this.apiService = new GenericAPIService(config.api);
        this.entityName = config.entityName || 'Запись';
        this.table = new GenericTable({
            tableBodyId: config.tableBodyId,
            columns: config.columns,
            onDelete: this.handleDelete.bind(this),
            editUrl: config.editUrl || '/edit/'
        });
        this.pagination = new Pagination(config.paginationId, this.handlePageChange.bind(this));
        this.deleteConfirmModal = new GenericDeleteConfirmModal(
            config.deleteModalId,
            config.confirmDeleteBtnId,
            this.handleDeleteConfirm.bind(this)
        );

        this.filterInputs = document.querySelectorAll(config.filterInputSelector || '.filter-input');
        this.clearFiltersButton = config.clearFiltersBtnId ? document.getElementById(config.clearFiltersBtnId) : null;
        this.pageSizeSelect = document.getElementById(config.pageSizeSelectId);
        this.createButton = config.createButtonId ? document.getElementById(config.createButtonId) : null;
        this.loadingIndicator = config.loadingIndicatorId ? document.getElementById(config.loadingIndicatorId) : null;

        this.currentPage = 0;
        this.pageSize = this.pageSizeSelect ? parseInt(this.pageSizeSelect.value, 10) : 10;
        this.filters = {};

        this.toastConfig = config.toastConfig || {};
        this.notificationKeyword = config.notificationKeyword || 'notification';
        this.createUrl = config.createUrl || '/create';
        this.i18nKeys = config.i18nKeys;

        this._initialize();
    }

    _initialize() {
        this.fetchAndRender();
        this._attachEventListeners();
    }

    _attachEventListeners() {
        if (this.pageSizeSelect) {
            this.pageSizeSelect.addEventListener('change', () => {
                this.pageSize = parseInt(this.pageSizeSelect.value, 10);
                this.currentPage = 0;
                this.fetchAndRender();
            });
        }

        if (this.createButton) {
            this.createButton.addEventListener('click', (e) => {
                e.preventDefault();
                window.location.href = this.createUrl;
            });
        }

        let debounceTimeout;
        this.filterInputs.forEach(input => {
            input.addEventListener('input', () => {
                clearTimeout(debounceTimeout);
                debounceTimeout = setTimeout(() => {
                    this._updateFilters();
                    this.currentPage = 0;
                    this.fetchAndRender();
                }, 300);
            });
        });

        if (this.clearFiltersButton) {
            this.clearFiltersButton.addEventListener('click', () => {
                this.filterInputs.forEach(input => input.value = '');
                this._updateFilters();
                this.currentPage = 0;
                this.fetchAndRender();
            });
        }
    }

    _updateFilters() {
        this.filters = {};
        this.filterInputs.forEach(input => {
            const key = input.getAttribute('data-filter-key');
            const value = input.value.trim();
            if (key && value) {
                this.filters[key] = value;
            }
        });
    }

    blockCard(message = 'Загрузка...') {
        if (typeof $ !== 'undefined' && $.fn.block) {
            $('#card-block').block({
                message: `
                    <span class="spinner-border text-primary" role="status"></span>
                    <span>${i18next.t(message)}</span>
                `,
                css: {
                    backgroundColor: 'transparent',
                    border: '0'
                },
                overlayCSS: {
                    opacity: 0.5
                }
            });
        } else if (this.loadingIndicator) {
            this.loadingIndicator.style.display = 'block';
        }
    }

    unblockCard() {
        if (typeof $ !== 'undefined' && $.fn.unblock) {
            $('#card-block').unblock();
        } else if (this.loadingIndicator) {
            this.loadingIndicator.style.display = 'none';
        }
    }

    async fetchAndRender() {
        const cardBlockElement = document.getElementById('card-block');

        try {
            this.blockCard();

            this._updateFilters();
            const data = await this.apiService.getAll(this.currentPage, this.pageSize, this.filters);

            if (this.currentPage >= data.totalPages && data.totalPages > 0) {
                this.currentPage = data.totalPages - 1;
                const adjustedData = await this.apiService.getAll(this.currentPage, this.pageSize, this.filters);
                this.table.populate(adjustedData.content);
                this.pagination.setup(adjustedData.totalPages, this.currentPage);
            } else {
                if (data.totalPages === 0) {
                   this.currentPage = 0;
                }
                this.table.populate(data.content);
                this.pagination.setup(data.totalPages, this.currentPage);
            }
        } catch (error) {
            console.error('Error fetching or rendering data:', error);
            if (error && error.data && error.data[this.notificationKeyword]) {
                this.showToast('error', this.i18nKeys.fetchErrorApi, null, { message: error.data[this.notificationKeyword] });
            } else {
                this.showToast('error', this.i18nKeys.fetchErrorGeneric);
            }
            this.table.populate([]);
            this.pagination.setup(0, 0);
        } finally {
            this.unblockCard();
        }
    }

    handlePageChange(newPage) {
        this.currentPage = newPage;
        this.fetchAndRender();
    }

    handleDelete(id) {
        this.deleteConfirmModal.open(id);
    }

    async handleDeleteConfirm(id) {
        try {
            const result = await this.apiService.delete(id);

            const checkData = await this.apiService.getAll(this.currentPage, this.pageSize, this.filters);

            if (checkData.content.length === 0 && this.currentPage > 0) {
                this.currentPage--;
            }

            await this.fetchAndRender();

            if (result && result[this.notificationKeyword]) {
                this.showToast('success', this.i18nKeys.deleteSuccess, 'personal.personal');
            } else {
                this.showToast('success', this.i18nKeys.deleteSuccess, 'personal.personal');
            }
        } catch (error) {
            console.error('Error during deletion:', error);
            if (error && error.data && error.data[this.notificationKeyword]) {
                this.showToast('error', this.i18nKeys.deleteErrorApi, 'personal.personal', { message: error.data[this.notificationKeyword] });
            } else if (error && error.status) {
                this.showToast('error', this.i18nKeys.deleteErrorStatus, 'personal.personal', { status: error.status });
            } else {
                this.showToast('error', this.i18nKeys.deleteErrorGeneric, 'personal.personal');
            }
        }
    }

    showToast(type, messageKey, entityNameKey = null, extraParams = {}) {
        if (typeof toastr === 'undefined') {
            console.warn('Toastr library is not loaded. Cannot display toast notification.');
            return;
        }

        // Получаем локализованное сообщение для toast
        const title = i18next.t(this.i18nKeys[type + 'Title'], { defaultValue: 'Error' });
        const message = i18next.t(messageKey, {
            ...extraParams,
            entity: i18next.t(entityNameKey || this.entityName)
        });

        toastr.options = {
            closeButton: true,
            debug: false,
            newestOnTop: false,
            progressBar: true,
            positionClass: 'toast-top-right',
            preventDuplicates: false,
            onclick: null,
            showDuration: '300',
            hideDuration: '1000',
            timeOut: '5000',
            extendedTimeOut: '1000',
            showEasing: 'swing',
            hideEasing: 'linear',
            showMethod: 'fadeIn',
            hideMethod: 'fadeOut',
            ...this.toastConfig
        };

        toastr[type](message, title);
    }
}
