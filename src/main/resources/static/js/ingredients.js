

let div = document.getElementById('ingredient');
let button = document.getElementById('button');

button.addEventListener('click', addIngredientInput);


function addIngredientInput()  {

        let div = document.getElementById('ingredient');
        let p = document.createElement('p');
        p.setAttribute('style','display: flex; flex-direction: row;')
        let input = document.createElement('input');
        let deleteButton = document.createElement('input');
        input.setAttribute('type','text');
        input.setAttribute('placeholder','Внесите ингредиент');
        input.setAttribute('class','form-control');
        input.setAttribute('name','ingredient');
        input.setAttribute('value','');
        input.setAttribute('style','width: auto;')
        deleteButton.setAttribute('id', 'delete');
        deleteButton.classList.add('btn','btn-outline-danger');
        deleteButton.setAttribute('type', 'button');
        deleteButton.setAttribute('value', 'Удалить ингредиент');
        p.appendChild(input);
        p.appendChild(deleteButton);
        div.appendChild(p);

        deleteButton.addEventListener('click', deleteIngredientInput);
}

function deleteIngredientInput() {
        let buttonDelete = this;
        let p = buttonDelete.parentElement;
        p.remove();
}