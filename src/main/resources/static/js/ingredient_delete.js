/**
 @author Zhurenko Evgeniy
 */


let allPTagIngredients = document.getElementById('ingredients').getElementsByTagName('p');

if(allPTagIngredients.length !== 0) {
    for(let i=0; i < allPTagIngredients.length; i++) {
        let deleteIngredient = allPTagIngredients[i].querySelector('#delete');
        deleteIngredient.addEventListener('click', deleteIngredientInput);
    }
}

function deleteIngredientInput()  {

    let div = this.parentElement.parentElement;

    let idBlog = div.getAttribute('id');
    let idIngredient = div.getAttribute('name');

    let getRequest = new XMLHttpRequest();
    getRequest.open('GET', `/ajax/delete-ingredient?idBlog=${idBlog}&idIngredient=${idIngredient}`, true);
    getRequest.setRequestHeader("Content-type", 'application/json');


    getRequest.onload = function(response){
        console.log(response);
        if(getRequest.readyState === 4 && getRequest.status === 200) {
            if (response.returnValue === true)
                div.remove();
        } else {
            console.log(getRequest.responseText);
        }
    };

    getRequest.send();

}