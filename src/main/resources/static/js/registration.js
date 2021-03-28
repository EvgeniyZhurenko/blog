let inputPasswordRepeat = document.getElementsByName("repeat_password")[0];

console.dir(inputPasswordRepeat);

inputPasswordRepeat.addEventListener("input", buttonActivate);


function buttonActivate() {

    let inputPassword = document.getElementsByName("password")[0];
    let button = document.getElementById("button");

    console.dir(button);
    console.log(inputPassword.value);
    console.log(inputPasswordRepeat.value);

    console.log(inputPassword.value === inputPasswordRepeat.value);

    if (inputPassword.value.length === inputPasswordRepeat.value.length){
        if (inputPassword.value === inputPasswordRepeat.value) {
            button.disabled = "";
        } else {
            button.innerHTML = "";
            button.innerHTML = "Пароли не совпадают!";
            button.color = "red";
            if(button.disabled !== "disabled")
                button.disabled = "disabled";
        }
    }
}