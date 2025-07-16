/**
 *  Страница аутентификации
 */
'use strict';

document.addEventListener('DOMContentLoaded', function () {
  (() => {
    // Получаем форму аутентификации по ID
    const formAuthentication = document.querySelector('#formAuthentication');

    // // Валидация формы для добавления нового пользователя
    // if (formAuthentication && typeof FormValidation !== 'undefined') {
    //   // Инициализация валидации формы с помощью FormValidation
    //   FormValidation.formValidation(formAuthentication, {
    //     fields: {
    //       username: {
    //         validators: {
    //           notEmpty: {
    //             message: 'Пожалуйста, введите имя пользователя'
    //           },
    //           stringLength: {
    //             min: 6,
    //             message: 'Имя пользователя должно быть больше 6 символов'
    //           }
    //         }
    //       },
    //       email: {
    //         validators: {
    //           notEmpty: {
    //             message: 'Пожалуйста, введите ваш email'
    //           },
    //           emailAddress: {
    //             message: 'Пожалуйста, введите правильный email адрес'
    //           }
    //         }
    //       },
    //       'email-username': {
    //         validators: {
    //           notEmpty: {
    //             message: 'Пожалуйста, введите email или имя пользователя'
    //           },
    //           stringLength: {
    //             min: 6,
    //             message: 'Имя пользователя должно быть больше 6 символов'
    //           }
    //         }
    //       },
    //       password: {
    //         validators: {
    //           notEmpty: {
    //             message: 'Пожалуйста, введите ваш пароль'
    //           },
    //           stringLength: {
    //             min: 6,
    //             message: 'Пароль должен быть длиннее 6 символов'
    //           }
    //         }
    //       },
    //       'confirm-password': {
    //         validators: {
    //           notEmpty: {
    //             message: 'Пожалуйста, подтвердите пароль'
    //           },
    //           identical: {
    //             // Проверка совпадения пароля и его подтверждения
    //             compare: () => formAuthentication.querySelector('[name="password"]').value,
    //             message: 'Пароль и его подтверждение не совпадают'
    //           },
    //           stringLength: {
    //             min: 6,
    //             message: 'Пароль должен быть длиннее 6 символов'
    //           }
    //         }
    //       },
    //       terms: {
    //         validators: {
    //           notEmpty: {
    //             message: 'Пожалуйста, согласитесь с условиями'
    //           }
    //         }
    //       }
    //     },
    //     plugins: {
    //       trigger: new FormValidation.plugins.Trigger(),
    //       bootstrap5: new FormValidation.plugins.Bootstrap5({
    //         eleValidClass: '',
    //         rowSelector: '.form-control-validation'
    //       }),
    //       submitButton: new FormValidation.plugins.SubmitButton(),
    //       defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
    //       autoFocus: new FormValidation.plugins.AutoFocus()
    //     },
    //     init: instance => {
    //       // Перемещаем сообщения о ошибках в соответствующее место в форме
    //       instance.on('plugins.message.placed', e => {
    //         if (e.element.parentElement.classList.contains('input-group')) {
    //           e.element.parentElement.insertAdjacentElement('afterend', e.messageElement);
    //         }
    //       });
    //     }
    //   });
    // }

    // Валидация для маски ввода числовых значений
    const numeralMaskElements = document.querySelectorAll('.numeral-mask');

    // Функция форматирования для числовой маски (оставляем только цифры)
    const formatNumeral = value => value.replace(/\D/g, ''); // Оставляем только цифры

    if (numeralMaskElements.length > 0) {
      // Применяем маску к каждому элементу
      numeralMaskElements.forEach(numeralMaskEl => {
        numeralMaskEl.addEventListener('input', event => {
          numeralMaskEl.value = formatNumeral(event.target.value);
        });
      });
    }
  })();
});
