let inputPasswordRepeat = document.getElementsByName("repeat_password")[0];

inputPasswordRepeat.addEventListener("input", buttonActivate);

function buttonActivate() {

    let inputPassword = document.getElementsByName("password")[0];
    let button = document.getElementById("button");

    if (inputPassword.value === inputPasswordRepeat.value) {
        button.removeAttribute("disabled");
        button.classList.remove("btn_disable");
        button.classList.add("btn" );
        button.style.color = "#fff";
        if(button.innerHTML === "Пароли не совпадают!"){
            button.innerHTML = "Регистрация";

        }
    } else {
        if(inputPassword.value.length === inputPasswordRepeat.value.length) {
            button.innerHTML = "";
            button.innerHTML = "Пароли не совпадают!";
            button.style.color = "red";
            button.setAttribute("disabled", "disabled");
            if (button.className === "btn") {
                button.classList.remove("btn");
                button.classList.add("btn_disable");
            }
        }else if(inputPasswordRepeat.value.length > inputPassword.value.length ||
                 inputPasswordRepeat.value.length < inputPassword.value.length){
            button.innerHTML = "";
            button.innerHTML = "Пароли не совпадают!";
            button.style.color = "red";
            button.setAttribute("disabled", "disabled");
            if (button.className === "btn") {
                button.classList.remove("btn");
                button.classList.add("btn_disable");
            }
        }
    }
}