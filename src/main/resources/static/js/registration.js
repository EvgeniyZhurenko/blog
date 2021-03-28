let inputPasswordRepeat = document.getElementsByName("repeat_password");

console.dir(inputPasswordRepeat);

inputPasswordRepeat.oninput = function() {

    let inputPassword = document.getElementsByName("password");
    let button = document.getElementsByName("button");

    console.log(inputPassword.value() === inputPasswordRepeat.value());

    if(inputPassword.value() === inputPasswordRepeat.value()){
        button.disabled=false;
    }
}