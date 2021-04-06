function send(){
    let div = document.querySelector('.listComments');

    // let elem = [0,0];
    // elem[0] = "name";
    // elem[1] = "inputText";

    let elem = ["name","inputText"];

    for(let i = 0;i < elem.length; i++){
        let p = document.createElement('p');
        p.textContent = document.querySelector(`#${elem[i]}`).value;
        div.appendChild(p);
        let item = document.querySelector(`textarea[id = ${elem[i]}]`);
        item.value = "";
        if(i == 0){
            let hr = document.createElement('hr');
            div.appendChild(hr);
        }
    }
    let pDate = document.createElement('p');
    pDate.innerHTML = new Date().toDateString();
    div.appendChild(pDate);
}