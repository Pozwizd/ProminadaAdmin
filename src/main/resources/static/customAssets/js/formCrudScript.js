/**
 * @fileoverview Этот файл содержит набор классов JavaScript для создания универсального интерфейса
 * для отображения данных в таблице с пагинацией, фильтрацией, операциями CRUD (Create, Read, Update, Delete)
 * через API и модальным окном подтверждения удаления.
 *
 * Основные компоненты:
 * - GenericAPIService: Обрабатывает все взаимодействия с API.
 * - Pagination: Управляет элементами управления пагинацией.
 * - GenericTable: Отображает данные в HTML-таблице и обрабатывает действия строк.
 * - GenericDeleteConfirmModal: Управляет модальным окном подтверждения удаления.
 * - GenericCRUDApp: Координирует все компоненты для создания полноценного приложения.
 */

/**
 * @class GenericAPIService
 * @description Обрабатывает все взаимодействия с API, включая операции CRUD (Create, Read, Update, Delete).
 * Предоставляет унифицированный способ выполнения HTTP-запросов и обработки ответов/ошибок.
 */
class GenericAPIService {
    /**
     * @constructor
     * @description Инициализирует сервис API.
     * @param {object} config - Объект конфигурации.
     * @param {string} config.baseURL - Базовый URL API.
     * @param {object} config.endpoints - Объект с конечными точками API для различных операций (e.g., getAll, getById, create, update, delete).
     *                                     Конечные точки для update, delete и getById должны содержать плейсхолдер '{id}'.
     * @param {string} [entityType='json'] - Тип данных, отправляемых при создании/обновлении ('json' или 'formdata').
     */
    constructor(config, entityType = 'json') {
        /** @property {string} baseURL - Базовый URL API. */
        this.baseURL = config.baseURL;
        /** @property {object} endpoints - Конечные точки API. */
        this.endpoints = config.endpoints;
        /** @property {string} entityType - Тип отправляемых данных по умолчанию ('json' или 'formdata'). */
        this.entityType = entityType;
    }

    /**
     * @method request
     * @description Выполняет HTTP-запрос к указанной конечной точке с заданными опциями.
     * Это основной метод для взаимодействия с API.
     * @param {string} endpoint - Конечная точка API (относительно baseURL).
     * @param {object} [options={}] - Опции для Fetch API (например, method, headers, body).
     * @returns {Promise<object|null>} - Promise, который разрешается с парсенным JSON-ответом, если Content-Type 'application/json', иначе null.
     * @throws {object} - В случае ошибки сети или ответа с кодом не 2xx, бросает объект вида `{ status: number, data: object }`,
     *                    где `data` - это парсенное тело ошибки или объект `{ message: 'Неизвестная ошибка' }`.
     * @async
     */
    async request(endpoint, options = {}) {
        const url = `${this.baseURL}${endpoint}`;
        console.log(url);
        try {
            const response = await fetch(url, options);
            if (!response.ok) {
                const errorData = await this._parseErrorResponse(response);
                // Бросаем стандартизированный объект ошибки
                throw { status: response.status, data: errorData };
            }
            // Парсим успешный ответ
            return await this._parseResponse(response);
        } catch (error) {
            // Перебрасываем ошибку (либо из fetch, либо нашу кастомную)
            throw error;
        }
    }

    /**
     * @method _parseResponse
     * @description Вспомогательный метод для парсинга успешного ответа API.
     * @param {Response} response - Объект Response от Fetch API.
     * @returns {Promise<object|null>} - Promise, который разрешается с парсенным JSON-объектом или null.
     * @private
     * @async
     */
    async _parseResponse(response) {
        const contentType = response.headers.get('Content-Type');
        // Проверяем, содержит ли Content-Type 'application/json'
        if (contentType && contentType.includes('application/json')) {
            return response.json(); // Парсим как JSON
        }
        // Если контент не JSON или отсутствует, возвращаем null
        return null;
    }

    /**
     * @method _parseErrorResponse
     * @description Вспомогательный метод для парсинга ответа API в случае ошибки.
     * Пытается парсить тело ответа как JSON, иначе возвращает стандартное сообщение об ошибке.
     * @param {Response} response - Объект Response от Fetch API (с кодом ошибки).
     * @returns {Promise<object>} - Promise, который разрешается с парсенным объектом ошибки или объектом `{ message: 'Неизвестная ошибка' }`.
     * @private
     * @async
     */
    async _parseErrorResponse(response) {
        try {
            // Пытаемся получить тело ошибки как JSON
            return await response.json();
        } catch {
            // Если не получилось (например, тело пустое или не JSON), возвращаем стандартный объект
            return { message: 'Неизвестная ошибка' };
        }
    }

    /**
     * @method getAll
     * @description Получает список сущностей с сервера с поддержкой пагинации и фильтрации.
     * @param {number} [page=0] - Номер запрашиваемой страницы (начиная с 0).
     * @param {number} [size=5] - Количество элементов на странице.
     * @param {object} [filters={}] - Объект с параметрами фильтрации { ключ: значение }.
     *                                  Пустые значения фильтров игнорируются.
     * @returns {Promise<object>} - Promise, который разрешается с ответом API. Ожидается, что ответ будет содержать
     *                             свойства `content` (массив элементов) и информацию о пагинации (например, `totalPages`, `currentPage`).
     * @async
     */
    getAll(page = 0, size = 5, filters = {}) {
        // Формируем строку запроса с пагинацией
        let queryParams = `page=${page}&size=${size}`;
        // Добавляем параметры фильтрации
        for (const key in filters) {
            // Проверяем, что свойство принадлежит объекту и значение не пустое
            if (Object.prototype.hasOwnProperty.call(filters, key) && filters[key]) {
                queryParams += `&${encodeURIComponent(key)}=${encodeURIComponent(filters[key])}`;
            }
        }
        // Формируем конечную точку с параметрами
        const endpoint = `${this.endpoints.getAll}?${queryParams}`;
        // Выполняем GET-запрос
        return this.request(endpoint);
    }

    /**
     * @method getById
     * @description Получает одну сущность по ее уникальному идентификатору.
     * @param {number|string} id - ID сущности для получения.
     * @returns {Promise<object>} - Promise, который разрешается с данными запрошенной сущности.
     * @async
     */
    getById(id) {
        // Заменяем плейсхолдер {id} в конечной точке на реальный ID
        const endpoint = this.endpoints.getById.replace('{id}', id);
        // Выполняем GET-запрос
        return this.request(endpoint);
    }

    /**
     * @method create
     * @description Создает новую сущность на сервере.
     * @param {object|FormData} entityData - Данные для создания новой сущности. Может быть объектом JavaScript или FormData.
     * @param {string} [entityType=this.entityType] - Тип отправляемых данных ('json' или 'formdata'). По умолчанию используется тип, заданный в конструкторе.
     * @returns {Promise<object>} - Promise, который разрешается с данными созданной сущности.
     * @async
     */
    create(entityData, entityType = this.entityType) {
        const endpoint = this.endpoints.create;
        // Подготавливаем опции запроса (метод POST, тело, заголовки)
        const options = this._prepareBody('POST', entityData, entityType);
        // Выполняем POST-запрос
        return this.request(endpoint, options);
    }

    /**
     * @method update
     * @description Обновляет существующую сущность на сервере.
     * @param {number|string} id - ID сущности для обновления.
     * @param {object|FormData} entityData - Новые данные для сущности. Может быть объектом JavaScript или FormData.
     * @param {string} [entityType=this.entityType] - Тип отправляемых данных ('json' или 'formdata'). По умолчанию используется тип, заданный в конструкторе.
     * @returns {Promise<object>} - Promise, который разрешается с обновленными данными сущности.
     * @async
     */
    update(id, entityData, entityType = this.entityType) {
        // Заменяем плейсхолдер {id} в конечной точке на реальный ID
        const endpoint = this.endpoints.update.replace('{id}', id);
        // Подготавливаем опции запроса (метод PUT, тело, заголовки)
        const options = this._prepareBody('PUT', entityData, entityType);
        // Выполняем PUT-запрос
        return this.request(endpoint, options);
    }

    /**
     * @method delete
     * @description Удаляет сущность на сервере по ее ID.
     * @param {number|string} id - ID сущности для удаления.
     * @returns {Promise<object|null>} - Promise, который разрешается с ответом от сервера (может быть пустым или содержать сообщение подтверждения) или null.
     * @async
     */
    delete(id) {
        // Заменяем плейсхолдер {id} в конечной точке на реальный ID
        const endpoint = this.endpoints.delete.replace('{id}', id);
        // Выполняем DELETE-запрос
        return this.request(endpoint, { method: 'DELETE' });
    }

    /**
     * @method _prepareBody
     * @description Вспомогательный метод для подготовки опций запроса (метод, тело, заголовки) в зависимости от типа данных.
     * @param {string} method - HTTP метод (например, 'POST', 'PUT').
     * @param {object|FormData} data - Данные для отправки.
     * @param {string} entityType - Тип данных ('json' или 'formdata').
     * @returns {object} - Объект опций для Fetch API.
     * @private
     */
    _prepareBody(method, data, entityType) {
        let options = {
            method: method,
            headers: { 'Accept': 'application/json' } // Ожидаем JSON в ответе
        };

        if (entityType === 'formdata') {
            // Если тип - FormData, просто передаем его в body.
            // Content-Type будет установлен браузером автоматически (включая boundary).
            options.body = data;
            // Убедимся, что Content-Type не установлен вручную для FormData
            delete options.headers['Content-Type'];
        } else {
            // Если тип - JSON, строгифицируем объект и устанавливаем заголовок Content-Type.
            options.body = JSON.stringify(data);
            options.headers['Content-Type'] = 'application/json';
        }
        return options;
    }
}


/**
 * @class Pagination
 * @description Управляет элементами пагинации (кнопки "вперед", "назад", номера страниц) и взаимодействием с пользователем.
 */
class Pagination {
    /**
     * @constructor
     * @param {string} containerId - ID HTML-элемента (обычно `<ul>`), в который будет рендериться пагинация.
     * @param {function(number)} onPageChange - Callback-функция, вызываемая при выборе пользователем новой страницы. Получает номер новой страницы (с 0).
     */
    constructor(containerId, onPageChange) {
        /** @property {HTMLElement} container - DOM-элемент контейнера пагинации. */
        this.container = document.getElementById(containerId);
        /** @property {function(number)} onPageChange - Callback-функция для смены страницы. */
        this.onPageChange = onPageChange;
        /** @property {number} currentPage - Текущий номер активной страницы (начиная с 0). */
        this.currentPage = 0;
        /** @property {number} totalPages - Общее количество страниц. */
        this.totalPages = 0;

        // Добавляем обработчик кликов на контейнер пагинации (делегирование событий)
        if (this.container) {
            this.container.addEventListener('click', this._handleClick.bind(this));
        } else {
            console.error(`Pagination container with ID "${containerId}" not found.`);
        }
    }

    /**
     * @method setup
     * @description Устанавливает параметры пагинации (общее количество страниц, текущая страница) и перерисовывает компонент.
     * @param {number} totalPages - Общее количество доступных страниц.
     * @param {number} currentPage - Номер текущей активной страницы (начиная с 0).
     */
    setup(totalPages, currentPage) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.render(); // Перерисовываем пагинацию с новыми данными
    }

    /**
     * @method _handleClick
     * @description Обработчик кликов по элементам пагинации. Определяет, на какую страницу был клик, и вызывает `onPageChange`.
     * @param {Event} e - Объект события клика.
     * @private
     */
    _handleClick(e) {
        // Ищем ближайший родительский элемент <a>
        const link = e.target.closest('a');
        // Проверяем, что ссылка найдена и принадлежит нашему контейнеру
        if (link && this.container.contains(link)) {
            e.preventDefault(); // Предотвращаем стандартное действие ссылки
            // Получаем номер страницы из data-атрибута
            const page = link.getAttribute('data-page');
            if (page !== null) {
                const pageNumber = parseInt(page, 10);
                // Проверяем, что номер страницы валиден
                if (!isNaN(pageNumber) && pageNumber >= 0 && pageNumber < this.totalPages) {
                    this.onPageChange(pageNumber); // Вызываем callback с новым номером страницы
                } else {
                    console.error("Invalid page number requested:", page);
                }
            }
        }
    }

    /**
     * @method render
     * @description Генерирует HTML-разметку для пагинации и вставляет ее в контейнер.
     * Отображает кнопки навигации (первая, пред., след., последняя) и номера страниц с многоточиями, если страниц много.
     */
    render() {
        if (!this.container) return; // Не рендерить, если контейнер не найден

        // Если страниц нет или одна, пагинация не нужна
        if (this.totalPages <= 1) {
            this.container.innerHTML = '';
            return;
        }

        let paginationItems = '';
        // Кнопка "Первая страница"
        paginationItems += this._createNavItem(0, '<i class="ti ti-chevrons-left ti-xs"></i>', 'first', this.currentPage > 0);
        // Кнопка "Предыдущая страница"
        paginationItems += this._createNavItem(this.currentPage - 1, '<i class="ti ti-chevron-left ti-xs"></i>', 'prev', this.currentPage > 0);
        // Номера страниц
        paginationItems += this._renderPageNumbers();
        // Кнопка "Следующая страница"
        paginationItems += this._createNavItem(this.currentPage + 1, '<i class="ti ti-chevron-right ti-xs"></i>', 'next', this.currentPage < this.totalPages - 1);
        // Кнопка "Последняя страница"
        paginationItems += this._createNavItem(this.totalPages - 1, '<i class="ti ti-chevrons-right ti-xs"></i>', 'last', this.currentPage < this.totalPages - 1);

        this.container.innerHTML = paginationItems; // Вставляем сгенерированную разметку
    }

    /**
     * @method _renderPageNumbers
     * @description Генерирует HTML для номеров страниц, включая логику отображения многоточий.
     * @returns {string} - HTML-строка с элементами `<li>` для номеров страниц.
     * @private
     */
    _renderPageNumbers() {
        const ellipsisThreshold = 2; // Минимальное кол-во страниц между текущей и краем для показа многоточия
        const ellipsisOffset = 1; // Сколько страниц показывать рядом с текущей с каждой стороны
        let paginationItems = '';

        // Функция для добавления первой страницы и многоточия (если нужно)
        const addFirstPageAndEllipsis = () => {
            paginationItems += this._createPageItem(0, '1'); // Всегда показываем первую страницу
            if (this.currentPage > ellipsisThreshold + ellipsisOffset) {
                paginationItems += this._createDisabledItem('...'); // Показываем многоточие после первой
            }
        };

        // Функция для добавления последней страницы и многоточия (если нужно)
        const addLastPageAndEllipsis = () => {
            if (this.currentPage < this.totalPages - (ellipsisThreshold + ellipsisOffset + 1)) {
                paginationItems += this._createDisabledItem('...'); // Показываем многоточие перед последней
            }
            paginationItems += this._createPageItem(this.totalPages - 1, this.totalPages); // Всегда показываем последнюю страницу
        };

        // Определяем диапазон отображаемых номеров страниц вокруг текущей
        const startPage = Math.max(0, this.currentPage - ellipsisOffset);
        const endPage = Math.min(this.totalPages - 1, this.currentPage + ellipsisOffset);

        // Добавляем первую страницу и возможное многоточие, если текущая страница достаточно далеко от начала
        if (startPage > 0) {
            addFirstPageAndEllipsis();
        }

        // Генерируем номера страниц в вычисленном диапазоне
        for (let i = startPage; i <= endPage; i++) {
            paginationItems += this._createPageItem(i, i + 1, i === this.currentPage);
        }

        // Добавляем последнюю страницу и возможное многоточие, если текущая страница достаточно далеко от конца
        if (endPage < this.totalPages - 1) {
            addLastPageAndEllipsis();
        }

        return paginationItems;
    }

    /**
     * @method _createNavItem
     * @description Создает HTML-элемент `<li>` для кнопок навигации (первая, пред., след., последняя).
     * @param {number} page - Номер страницы, на которую ведет кнопка.
     * @param {string} content - HTML-содержимое кнопки (например, иконка).
     * @param {string} className - Дополнительный CSS-класс для элемента `<li>`.
     * @param {boolean} isEnabled - Определяет, активна ли кнопка (не `disabled`).
     * @returns {string} - HTML-строка с элементом `<li>`.
     * @private
     */
    _createNavItem(page, content, className, isEnabled) {
        return `<li class="page-item ${className} ${isEnabled ? '' : 'disabled'}"> <a class="page-link" href="#" data-page="${page}">${content}</a> </li>`;
    }

    /**
     * @method _createPageItem
     * @description Создает HTML-элемент `<li>` для номера страницы.
     * @param {number} page - Номер страницы, на которую ведет ссылка (начиная с 0).
     * @param {string|number} text - Текст ссылки (обычно номер страницы + 1).
     * @param {boolean} [isActive=false] - Является ли этот номер текущей страницей.
     * @returns {string} - HTML-строка с элементом `<li>`.
     * @private
     */
    _createPageItem(page, text, isActive = false) {
        return `<li class="page-item ${isActive ? 'active' : ''}"> <a class="page-link" href="#" data-page="${page}">${text}</a> </li>`;
    }

    /**
     * @method _createDisabledItem
     * @description Создает HTML-элемент `<li>` для неактивного элемента (например, многоточия).
     * @param {string} text - Текст элемента (например, '...').
     * @returns {string} - HTML-строка с элементом `<li>`.
     * @private
     */
    _createDisabledItem(text) {
        return `<li class="page-item disabled"> <span class="page-link">${text}</span> </li>`;
    }
}


/**
 * @class GenericTable
 * @description Отвечает за отображение данных в HTML-таблице и обработку действий пользователя (редактирование, удаление).
 */
class GenericTable {
    /**
     * @constructor
     * @param {object} config - Конфигурация таблицы.
     * @param {string} config.tableBodyId - ID элемента `<tbody>` таблицы.
     * @param {Array<object>} config.columns - Массив объектов, описывающих колонки таблицы.
     *                                         Каждый объект может иметь:
     *                                         - `key` {string}: Ключ для доступа к данным в объекте элемента.
     *                                         - `render` {function(object): string}: Функция для кастомного рендеринга ячейки. Принимает объект элемента, возвращает HTML-строку.
     *                                         Если указана `render`, `key` игнорируется.
     * @param {function(number|string)} config.onDelete - Callback-функция, вызываемая при нажатии кнопки "Удалить". Получает ID элемента.
     * @param {string} [config.noDataText='Нет данных для отображения'] - Текст, отображаемый при отсутствии данных.
     * @param {string} [config.editUrl='/edit/'] - Базовый URL для ссылки "Редактировать". К нему будет добавлен ID элемента.
     * @param {function(number|string)} [config.onEdit] - Callback-функция для кнопки "Редактировать" (если используется кнопка вместо ссылки). Не используется в текущей реализации `_createActionButton` для 'edit'.
     */
    constructor({ tableBodyId, columns, onEdit, onDelete, noDataText = 'Нет данных для отображения', editUrl = '/edit/' }) {
        /** @property {HTMLElement} tableBody - DOM-элемент `<tbody>` таблицы. */
        this.tableBody = document.getElementById(tableBodyId);
        /** @property {Array<object>} columns - Конфигурация колонок. */
        this.columns = columns;
        /** @property {function} onEdit - Callback для редактирования (не используется для ссылки). */
        this.onEdit = onEdit; // Note: Currently edit uses a link, so this callback isn't directly invoked by the default button
        /** @property {function(number|string)} onDelete - Callback для удаления. */
        this.onDelete = onDelete;
        /** @property {string} noDataText - Текст при отсутствии данных. */
        this.noDataText = noDataText;
        /** @property {string} editUrl - Базовый URL для ссылок редактирования. */
        this.editUrl = editUrl;

        if (!this.tableBody) {
            console.error(`Table body with ID "${tableBodyId}" not found.`);
        }
    }

    /**
     * @method populate
     * @description Заполняет тело таблицы (`<tbody>`) данными.
     * @param {Array<object>} items - Массив объектов данных для отображения. Каждый объект представляет одну строку.
     */
    populate(items) {
        if (!this.tableBody) return; // Не продолжать, если tbody не найден

        this.tableBody.innerHTML = ''; // Очищаем содержимое таблицы

        // Если данных нет, показываем сообщение
        if (!items || items.length === 0) {
            this._renderNoDataRow();
            return;
        }

        // Используем DocumentFragment для эффективного добавления строк в DOM
        const fragment = document.createDocumentFragment();
        items.forEach(item => {
            // Создаем строку для каждого элемента данных
            const row = this._createRow(item);
            fragment.appendChild(row);
        });
        this.tableBody.appendChild(fragment); // Добавляем все строки разом
    }

    /**
     * @method _createActionButton
     * @description Создает HTML-разметку для кнопки действия (редактировать или удалить).
     * @param {'edit'|'delete'} action - Тип действия.
     * @param {number|string} id - ID сущности, к которой относится действие.
     * @param {string} iconClass - CSS-класс для иконки кнопки (например, 'ti ti-pencil').
     * @param {string} className - CSS-класс для самой кнопки (например, 'btn-warning').
     * @returns {string} - HTML-строка кнопки или ссылки.
     * @private
     */
    _createActionButton(action, id, iconClass, className) {
        if (action === 'edit') {
            // Для редактирования создаем ссылку
            return `<a href="${this.editUrl}${id}" class="btn btn-sm ${className}"><i class="${iconClass}"></i></a>`;
        }
        // Для удаления создаем кнопку с data-атрибутами
        return `<button class="btn btn-sm ${className}" data-action="${action}" data-id="${id}"><i class="${iconClass}"></i></button>`;
    }

    /**
     * @method _createRow
     * @description Создает HTML-элемент строки таблицы (`<tr>`) для одного элемента данных.
     * @param {object} item - Объект данных для строки. Ожидается, что у него есть свойство `id`.
     * @returns {HTMLTableRowElement} - Созданный элемент `<tr>`.
     * @private
     */
    _createRow(item) {
        const row = document.createElement('tr');

        let cells = '';
        // Генерируем HTML для ячеек данных на основе конфигурации колонок
        this.columns.forEach(col => {
            let cellValue = '';
            // Если задана функция рендеринга, используем ее
            if (typeof col.render === 'function') {
                cellValue = col.render(item);
            } else if (col.key) {
                // Иначе получаем значение по ключу
                const value = item[col.key];
                // Отображаем '-' для null, undefined или пустой строки
                cellValue = (value === null || value === undefined || value === '') ? '-' : value;
            }
            cells += `<td class="text-center">${cellValue}</td>`; // Добавляем ячейку
        });

        // Создаем ячейку с кнопками действий
        const actionCell = `
            <td class="text-center action-buttons">
                ${this._createActionButton('edit', item.id, 'ti ti-pencil', 'btn-warning me-2')}
                ${this._createActionButton('delete', item.id, 'ti ti-trash', 'btn-danger')}
            </td>
        `;

        // Устанавливаем HTML строки (ячейки данных + ячейка действий)
        row.innerHTML = cells + actionCell;

        // Применяем стили для выравнивания кнопок в ячейке действий
        const actionTd = row.querySelector('td.action-buttons');
        if (actionTd) {
            actionTd.style.display = 'flex';
            actionTd.style.justifyContent = 'center';
            actionTd.style.alignItems = 'center';
            actionTd.style.gap = '5px'; // Пространство между кнопками
        }

        // Находим кнопку удаления в созданной строке
        const deleteButton = row.querySelector('button[data-action="delete"]');
        if (deleteButton) {
            const id = deleteButton.getAttribute('data-id');
            // Добавляем обработчик клика, который вызовет `onDelete` с ID элемента
            deleteButton.addEventListener('click', () => {
                 if (this.onDelete) {
                     this.onDelete(id);
                 }
            });
        }

        return row; // Возвращаем готовую строку
    }

    /**
     * @method _renderNoDataRow
     * @description Отображает строку с сообщением об отсутствии данных в таблице.
     * @private
     */
    _renderNoDataRow() {
        if (!this.tableBody) return;
        const row = document.createElement('tr');
        // Создаем одну ячейку, занимающую всю ширину таблицы
        row.innerHTML = `<td colspan="${this.columns.length + 1}" class="text-center">${this.noDataText}</td>`;
        this.tableBody.appendChild(row);
    }
}


/**
 * @class GenericDeleteConfirmModal
 * @description Управляет модальным окном Bootstrap для подтверждения операции удаления.
 */
class GenericDeleteConfirmModal {
    /**
     * @constructor
     * @param {string} modalId - ID HTML-элемента модального окна.
     * @param {string} confirmButtonId - ID кнопки подтверждения внутри модального окна.
     * @param {function(number|string)} onConfirm - Callback-функция, вызываемая при подтверждении удаления. Получает ID удаляемого элемента.
     */
    constructor(modalId, confirmButtonId, onConfirm) {
        /** @property {HTMLElement} modal - DOM-элемент модального окна. */
        this.modal = document.getElementById(modalId);
        /** @property {HTMLElement} confirmButton - DOM-элемент кнопки подтверждения. */
        this.confirmButton = document.getElementById(confirmButtonId);
        /** @property {function(number|string)} onConfirm - Callback при подтверждении. */
        this.onConfirm = onConfirm;
        /** @property {number|string|null} entityId - ID сущности, ожидающей подтверждения удаления. */
        this.entityId = null;
        /** @property {bootstrap.Modal|null} modalInstance - Экземпляр модального окна Bootstrap. */
        this.modalInstance = null;

        if (this.modal && this.confirmButton) {
            // Добавляем обработчик на кнопку подтверждения
            this.confirmButton.addEventListener('click', this._handleConfirm.bind(this));
            // Инициализируем экземпляр модального окна Bootstrap
            this.initializeModal();
        } else {
            console.error(`Modal (ID: ${modalId}) or Confirm Button (ID: ${confirmButtonId}) not found.`);
        }
    }

    /**
     * @method _handleConfirm
     * @description Обработчик клика по кнопке подтверждения. Вызывает `onConfirm` и закрывает модальное окно.
     * @private
     */
    _handleConfirm() {
        // Если ID сущности установлен, вызываем callback и закрываем окно
        if (this.entityId !== null && this.onConfirm) {
            this.onConfirm(this.entityId);
            this.close();
        }
    }

    /**
     * @method open
     * @description Открывает модальное окно для подтверждения удаления указанной сущности.
     * @param {number|string} id - ID сущности, удаление которой нужно подтвердить.
     */
    open(id) {
        if (!this.modalInstance) return; // Не открывать, если окно не инициализировано
        this.entityId = id; // Сохраняем ID
        this.modalInstance.show(); // Показываем модальное окно
    }

    /**
     * @method close
     * @description Закрывает модальное окно и сбрасывает сохраненный ID сущности.
     */
    close() {
        if (!this.modalInstance) return; // Не закрывать, если окно не инициализировано
        this.modalInstance.hide(); // Скрываем модальное окно
        this.entityId = null; // Сбрасываем ID
    }

    /**
     * @method initializeModal
     * @description Инициализирует экземпляр модального окна Bootstrap с заданными параметрами.
     */
    initializeModal() {
        if (this.modal && typeof bootstrap !== 'undefined' && bootstrap.Modal) {
             // Создаем экземпляр Bootstrap Modal
            this.modalInstance = new bootstrap.Modal(this.modal, {
                backdrop: 'static', // Не закрывать при клике на фон
                keyboard: false     // Не закрывать по клавише Esc
            });
        } else if (!this.modal) {
            console.error("Modal element not found for initialization.");
        } else {
            console.error("Bootstrap Modal is not available. Make sure Bootstrap's JavaScript is loaded.");
        }
    }
}


/**
 * @class GenericCRUDApp
 * @description Основной класс приложения, координирующий работу всех компонентов:
 * API сервиса, таблицы, пагинации, модального окна удаления, фильтров и кнопок действий.
 */
class GenericCRUDApp {
    /**
     * @constructor
     * @param {object} config - Объект конфигурации приложения.
     * @param {object} config.api - Конфигурация для `GenericAPIService` (baseURL, endpoints).
     * @param {string} config.tableBodyId - ID элемента `<tbody>` таблицы.
     * @param {Array<object>} config.columns - Конфигурация колонок для `GenericTable`.
     * @param {string} config.paginationId - ID контейнера для `Pagination`.
     * @param {string} config.deleteModalId - ID модального окна подтверждения удаления.
     * @param {string} config.confirmDeleteBtnId - ID кнопки подтверждения в модальном окне.
     * @param {string} [config.entityName=''] - Название сущности (используется в сообщениях).
     * @param {string} [config.editUrl='/edit/'] - Базовый URL для ссылок редактирования.
     * @param {string} [config.createUrl='/create'] - URL для перехода на страницу создания.
     * @param {string} [config.filterInputSelector='.filter-input'] - CSS-селектор для полей ввода фильтров.
     *        Каждое поле должно иметь атрибут `data-filter-key` с именем параметра фильтрации.
     * @param {string} [config.clearFiltersBtnId] - ID кнопки для очистки фильтров (опционально).
     * @param {string} config.pageSizeSelectId - ID элемента `<select>` для выбора размера страницы.
     * @param {string} [config.createButtonId] - ID кнопки для перехода на страницу создания (опционально).
     * @param {string} [config.loadingIndicatorId] - ID элемента индикатора загрузки (опционально, используется как fallback если jQuery BlockUI недоступен).
     * @param {object} [config.toastConfig={}] - Дополнительные опции для `toastr` уведомлений.
     * @param {string} [config.notificationKeyword='notification'] - Ключ в ответе API (успешном или ошибочном), содержащий текст уведомления для toastr.
     */
    constructor(config) {
        /** @property {GenericAPIService} apiService - Экземпляр сервиса API. */
        this.apiService = new GenericAPIService(config.api);
        /** @property {string} entityName - Название сущности. */
        this.entityName = config.entityName || 'Запись'; // Добавлено значение по умолчанию
        /** @property {GenericTable} table - Экземпляр таблицы. */
        this.table = new GenericTable({
            tableBodyId: config.tableBodyId,
            columns: config.columns,
            onDelete: this.handleDelete.bind(this), // Привязываем обработчик удаления
            editUrl: config.editUrl || '/edit/'      // Передаем URL редактирования
        });
        /** @property {Pagination} pagination - Экземпляр пагинации. */
        this.pagination = new Pagination(config.paginationId, this.handlePageChange.bind(this)); // Привязываем обработчик смены страницы
        /** @property {GenericDeleteConfirmModal} deleteConfirmModal - Экземпляр модального окна удаления. */
        this.deleteConfirmModal = new GenericDeleteConfirmModal(
            config.deleteModalId,
            config.confirmDeleteBtnId,
            this.handleDeleteConfirm.bind(this) // Привязываем обработчик подтверждения удаления
        );

        // Получаем ссылки на элементы управления
        /** @property {NodeListOf<HTMLInputElement>} filterInputs - Коллекция полей ввода для фильтрации. */
        this.filterInputs = document.querySelectorAll(config.filterInputSelector || '.filter-input');
        /** @property {HTMLElement|null} clearFiltersButton - Кнопка очистки фильтров. */
        this.clearFiltersButton = config.clearFiltersBtnId ? document.getElementById(config.clearFiltersBtnId) : null;
        /** @property {HTMLSelectElement|null} pageSizeSelect - Выпадающий список для выбора размера страницы. */
        this.pageSizeSelect = document.getElementById(config.pageSizeSelectId);
        /** @property {HTMLElement|null} createButton - Кнопка для перехода к созданию новой сущности. */
        this.createButton = config.createButtonId ? document.getElementById(config.createButtonId) : null;
        /** @property {HTMLElement|null} loadingIndicator - Элемент индикатора загрузки (fallback). */
        this.loadingIndicator = config.loadingIndicatorId ? document.getElementById(config.loadingIndicatorId) : null;

        // Проверяем наличие обязательных элементов
        if (!this.pageSizeSelect) {
             console.error(`Page size select element with ID "${config.pageSizeSelectId}" not found.`);
             // Можно бросить ошибку или предпринять другие действия
        }


        // Инициализация состояния приложения
        /** @property {number} currentPage - Текущая страница (начиная с 0). */
        this.currentPage = 0;
        /** @property {number} pageSize - Количество элементов на странице. */
        this.pageSize = this.pageSizeSelect ? parseInt(this.pageSizeSelect.value, 10) : 10; // Значение по умолчанию, если select не найден
         /** @property {object} filters - Объект с текущими значениями фильтров { ключ: значение }. */
        this.filters = {};

        // Конфигурация уведомлений и URL
        /** @property {object} toastConfig - Опции для toastr. */
        this.toastConfig = config.toastConfig || {};
        /** @property {string} notificationKeyword - Ключ для сообщений в ответах API. */
        this.notificationKeyword = config.notificationKeyword || 'notification';
        /** @property {string} createUrl - URL страницы создания. */
        this.createUrl = config.createUrl || '/create';
        // URL редактирования уже установлен в `this.table.editUrl`

        // Запускаем инициализацию приложения
        this._initialize();
    }

    /**
     * @method _initialize
     * @description Выполняет начальную настройку: инициализирует модальное окно,
     * загружает первые данные и устанавливает обработчики событий.
     * @private
     */
    _initialize() {
        // Модальное окно уже инициализируется в своем конструкторе
        // this.deleteConfirmModal.initializeModal();
        this.fetchAndRender(); // Загружаем и отображаем начальные данные
        this._attachEventListeners(); // Устанавливаем слушатели событий
    }

    /**
     * @method _attachEventListeners
     * @description Устанавливает все необходимые обработчики событий для элементов управления
     * (выбор размера страницы, фильтры, кнопка создания, кнопка очистки фильтров).
     * @private
     */
    _attachEventListeners() {
        // Обработчик изменения размера страницы
        if (this.pageSizeSelect) {
            this.pageSizeSelect.addEventListener('change', () => {
                this.pageSize = parseInt(this.pageSizeSelect.value, 10);
                this.currentPage = 0; // Сбрасываем на первую страницу при изменении размера
                this.fetchAndRender(); // Перезагружаем данные
            });
        }

        // Обработчик кнопки "Создать" (переход по URL)
        if (this.createButton) {
            this.createButton.addEventListener('click', (e) => {
                e.preventDefault(); // Предотвращаем стандартное действие, если это ссылка/кнопка формы
                window.location.href = this.createUrl; // Перенаправляем пользователя
            });
        }

        // Обработчики для полей фильтрации с использованием debounce
        let debounceTimeout;
        this.filterInputs.forEach(input => {
            input.addEventListener('input', () => {
                clearTimeout(debounceTimeout); // Отменяем предыдущий таймаут
                // Устанавливаем новый таймаут для отложенного выполнения
                debounceTimeout = setTimeout(() => {
                    this._updateFilters(); // Обновляем объект фильтров
                    this.currentPage = 0; // Сбрасываем на первую страницу при изменении фильтра
                    this.fetchAndRender(); // Перезагружаем данные
                }, 300); // Задержка 300 мс
            });
        });

        // Обработчик кнопки "Очистить фильтры"
        if (this.clearFiltersButton) {
            this.clearFiltersButton.addEventListener('click', () => {
                // Очищаем значения в полях ввода
                this.filterInputs.forEach(input => input.value = '');
                this._updateFilters(); // Обновляем внутренний объект фильтров (станет пустым)
                this.currentPage = 0; // Сбрасываем на первую страницу
                this.fetchAndRender(); // Перезагружаем данные
            });
        }
    }

    /**
     * @method _updateFilters
     * @description Собирает значения из полей ввода фильтров (`this.filterInputs`)
     * и обновляет внутренний объект `this.filters`. Использует `data-filter-key` атрибут поля как ключ фильтра.
     * @private
     */
    _updateFilters() {
        this.filters = {}; // Очищаем текущие фильтры
        this.filterInputs.forEach(input => {
            const key = input.getAttribute('data-filter-key'); // Получаем ключ фильтра из data-атрибута
            const value = input.value.trim(); // Получаем и очищаем значение поля
            // Добавляем фильтр, только если есть ключ и непустое значение
            if (key && value) {
                this.filters[key] = value;
            }
        });
    }

    /**
     * @method blockCard
     * @description Отображает индикатор загрузки (overlay) на элементе с ID 'card-block'.
     * Использует jQuery BlockUI, если он доступен.
     * @param {string} [message='Загрузка...'] - Сообщение, отображаемое во время загрузки.
     */
    blockCard(message = 'Загрузка...') {
        // Проверяем наличие jQuery и плагина BlockUI
        if (typeof $ !== 'undefined' && $.fn.block) {
            $('#card-block').block({
                message: `
                    <span class="spinner-border text-primary" role="status"></span>
                    <span>${message}</span>
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
            // Fallback: Показываем простой индикатор загрузки
            this.loadingIndicator.style.display = 'block';
        }
    }

    /**
     * @method unblockCard
     * @description Скрывает индикатор загрузки (overlay) с элемента 'card-block'.
     * Использует jQuery BlockUI, если он доступен.
     */
    unblockCard() {
        // Проверяем наличие jQuery и плагина BlockUI
        if (typeof $ !== 'undefined' && $.fn.unblock) {
            $('#card-block').unblock();
        } else if (this.loadingIndicator) {
            // Fallback: Скрываем простой индикатор загрузки
            this.loadingIndicator.style.display = 'none';
        }
    }

    /**
     * @method fetchAndRender
     * @description Основной метод для загрузки данных с API на основе текущего состояния
     * (страница, размер, фильтры) и последующего обновления таблицы и пагинации.
     * Обрабатывает состояние загрузки и возможные ошибки.
     * Также корректирует номер текущей страницы, если она стала пустой после удаления.
     * @async
     */
    async fetchAndRender() {
        const cardBlockElement = document.getElementById('card-block'); // Проверяем наличие элемента для блокировки

        try {
            // Показываем индикатор загрузки (BlockUI или fallback)
            this.blockCard(); // Используем метод blockCard

            this._updateFilters(); // Убедимся, что фильтры актуальны перед запросом
            // Запрашиваем данные с API
            const data = await this.apiService.getAll(this.currentPage, this.pageSize, this.filters);

            // Проверяем, не является ли текущая страница некорректной (например, после удаления последнего элемента)
            if (this.currentPage >= data.totalPages && data.totalPages > 0) {
                 // Если текущая страница за пределами существующих, переходим на последнюю доступную
                this.currentPage = data.totalPages - 1;
                // Повторно запрашиваем данные для скорректированной страницы
                const adjustedData = await this.apiService.getAll(this.currentPage, this.pageSize, this.filters);
                this.table.populate(adjustedData.content); // Обновляем таблицу
                this.pagination.setup(adjustedData.totalPages, this.currentPage); // Обновляем пагинацию
            } else {
                // Если общее количество страниц равно 0, сбрасываем текущую страницу на 0
                if (data.totalPages === 0) {
                   this.currentPage = 0;
                }
                // Если страница корректна или данных нет, просто отображаем полученные данные
                this.table.populate(data.content); // Обновляем таблицу
                this.pagination.setup(data.totalPages, this.currentPage); // Обновляем пагинацию
            }
        } catch (error) {
            console.error('Error fetching or rendering data:', error);
            // Показываем уведомление об ошибке пользователю
             if (error && error.data && error.data[this.notificationKeyword]) {
                 this.showToast('error', 'Ошибка', error.data[this.notificationKeyword]);
             } else {
                 this.showToast('error', 'Ошибка', 'Не удалось загрузить данные. Пожалуйста, попробуйте позже.');
             }
            // Очищаем таблицу и пагинацию в случае ошибки загрузки, чтобы не показывать старые данные
            this.table.populate([]);
            this.pagination.setup(0, 0);
        } finally {
            // Скрываем индикатор загрузки в любом случае (успех или ошибка)
            this.unblockCard(); // Используем метод unblockCard
        }
    }

    /**
     * @method handlePageChange
     * @description Обработчик события смены страницы от компонента `Pagination`.
     * @param {number} newPage - Новый номер выбранной страницы (начиная с 0).
     */
    handlePageChange(newPage) {
        this.currentPage = newPage; // Обновляем текущую страницу
        this.fetchAndRender(); // Загружаем и отображаем данные для новой страницы
    }

    /**
     * @method handleDelete
     * @description Обработчик события нажатия кнопки "Удалить" в таблице `GenericTable`.
     * Открывает модальное окно подтверждения.
     * @param {number|string} id - ID элемента, который пользователь хочет удалить.
     */
    handleDelete(id) {
        this.deleteConfirmModal.open(id); // Открываем модальное окно, передавая ID
    }

    /**
     * @method handleDeleteConfirm
     * @description Обработчик события подтверждения удаления в `GenericDeleteConfirmModal`.
     * Выполняет запрос на удаление через API, обрабатывает результат и обновляет интерфейс.
     * @param {number|string} id - ID элемента для удаления.
     * @async
     */
    async handleDeleteConfirm(id) {
        try {
            // Выполняем запрос на удаление
            const result = await this.apiService.delete(id);

            // После успешного удаления необходимо обновить данные.
            // Проверим, не стала ли текущая страница пустой.
            // Запросим данные для ТЕКУЩЕЙ страницы снова.
            const checkData = await this.apiService.getAll(this.currentPage, this.pageSize, this.filters);

            // Если на текущей странице больше нет элементов И это была не первая страница,
            // то нужно перейти на предыдущую страницу.
            if (checkData.content.length === 0 && this.currentPage > 0) {
                this.currentPage--; // Уменьшаем номер текущей страницы
            }

            // Перезагружаем и рендерим данные (для текущей или предыдущей страницы)
            // fetchAndRender сам обработает ситуацию, если после перехода на предыдущую данных все равно нет.
            await this.fetchAndRender();

            // Показываем уведомление об успехе
            if (result && result[this.notificationKeyword]) {
                this.showToast('success', 'Успех', result[this.notificationKeyword]);
            } else {
                this.showToast('success', 'Успех', `${this.entityName} успешно удален(а)!`);
            }
        } catch (error) {
            console.error('Error during deletion:', error);
            // Показываем уведомление об ошибке
            if (error && error.data && error.data[this.notificationKeyword]) {
                this.showToast('error', 'Ошибка удаления', error.data[this.notificationKeyword]);
            } else if (error && error.status) {
                 this.showToast('error', `Ошибка ${error.status}`, `Не удалось удалить ${this.entityName.toLowerCase()}.`);
            }
             else {
                this.showToast('error', 'Ошибка удаления', `Произошла неизвестная ошибка при удалении ${this.entityName.toLowerCase()}.`);
            }
            // Данные не перезагружаем в случае ошибки, чтобы пользователь видел текущее состояние
        }
    }

    /**
     * @method showToast
     * @description Отображает всплывающее уведомление (toast) с использованием библиотеки `toastr`.
     * @param {'success'|'error'|'warning'|'info'} type - Тип уведомления.
     * @param {string} title - Заголовок уведомления.
     * @param {string} message - Текст сообщения уведомления.
     */
    showToast(type, title, message) {
        // Проверяем, доступна ли библиотека toastr
        if (typeof toastr === 'undefined') {
            console.warn('Toastr library is not loaded. Cannot display toast notification.');
            // В качестве fallback можно использовать alert или console.log
            // alert(`${title}: ${message}`);
            return;
        }

        // Настраиваем опции toastr, объединяя стандартные и пользовательские из конфига
        toastr.options = {
            closeButton: true,      // Показывать кнопку закрытия
            debug: false,
            newestOnTop: false,     // Новые уведомления сверху
            progressBar: true,      // Показывать прогресс-бар времени жизни
            positionClass: 'toast-top-right', // Позиция на экране
            preventDuplicates: false, // Предотвращать дубликаты
            onclick: null,
            showDuration: '300',    // Длительность появления
            hideDuration: '1000',   // Длительность скрытия
            timeOut: '5000',        // Время отображения (мс)
            extendedTimeOut: '1000',// Доп. время при наведении
            showEasing: 'swing',
            hideEasing: 'linear',
            showMethod: 'fadeIn',   // Анимация появления
            hideMethod: 'fadeOut',  // Анимация скрытия
            ...this.toastConfig      // Применяем пользовательские опции поверх стандартных
        };

        // Вызываем метод toastr соответствующего типа
        toastr[type](message, title);
    }
}