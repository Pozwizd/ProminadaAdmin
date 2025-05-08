document.addEventListener('click', function (event) {
    const target = event.target;

    if (target.classList.contains('btn-select')) {
        const card = target.closest('.card');
        const fileInput = card.querySelector('input[type="file"]');
        if (fileInput) {
            fileInput.click();
        }
    }

    if (target.classList.contains('btn-delete')) {
        const card = target.closest('.card');
        const img = card.querySelector('img');
        const input = card.querySelector('input[type="file"]');
        const pathInput = card.querySelector('.paths');

        if (img) img.src = path_default_image;
        if (input) input.value = '';
        if (pathInput) pathInput.value = '';

        let small = card.querySelector('small');
        if (small) small.textContent = '';
    }
});

document.addEventListener('change', function (event) {
    const target = event.target;

    if (target.classList.contains('files')) {
        const file = target.files[0];
        if (!file) return;

        const card = target.closest('.card');
        const imageElement = card.querySelector('img');
        const pathInput = card.querySelector('.paths');
        let small = card.querySelector('small');

        if (imageElement) {
            imageElement.src = URL.createObjectURL(file);
        }

        if (pathInput) {
            pathInput.value = '';
        }

        if (small) small.textContent = '';

    }

    if (target.classList.contains('file-alone')) {
        const file = target.files[0];
        if (!file) return;

        const col = target.closest('.col-md-4');
        const path = col?.querySelector('small');

        if (path) path.textContent = '';

    }
});
