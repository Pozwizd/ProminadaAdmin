function clearValid() {
    document.querySelectorAll(".error-message").forEach(function (element) {
        element.remove();
    });
    document.querySelectorAll(".errorMy").forEach(function (element) {
        element.classList.remove("errorMy");
    });
}

function validate(data) {
    Object.entries(data).forEach(function ([field, message]) {
        const inputFields = document.querySelectorAll(`[data-error="${field}"]`);
        console.log(inputFields);
        inputFields.forEach((inputField) => {
            console.log("Field:", field, "Message:", message);
            inputField.classList.add("errorMy");
            let div = document.createElement("div");
            div.style.marginBottom = '10px';
            let errorMessage = document.createElement("span");
            errorMessage.className = "error-message";
            errorMessage.style.fontFamily = 'monospace';
            errorMessage.style.color = "red";
            errorMessage.innerText = message;
            div.appendChild(errorMessage);
            if (inputField.getAttribute("class") === "files") {
                inputField.parentNode.append(errorMessage);
            } else {
                inputField.after(div);
            }
        });
    });
}