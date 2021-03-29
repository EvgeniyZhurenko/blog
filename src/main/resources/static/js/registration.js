let inputPasswordRepeat = document.getElementsByName("repeat_password")[0];

inputPasswordRepeat.addEventListener("input", buttonActivate);

function buttonActivate() {

    let inputPassword = document.getElementsByName("password")[0];
    let button = document.getElementById("button");

    if (inputPassword.value === inputPasswordRepeat.value) {
        button.disabled = "";
        button.classList.remove("btn_disable");
        button.classList.add("btn" );
        if(button.innerHTML === "Пароли не совпадают!"){
            button.innerHTML = "Регистрация";
        }
    } else {
        if(inputPassword.value.length === inputPasswordRepeat.value.length) {
            button.innerHTML = "";
            button.innerHTML = "Пароли не совпадают!";
            button.style.color = "red";
            if (button.className === "btn") {
                button.classList.remove("btn");
                button.classList.add("btn_disable");
            }
        }
    }
}