/**
 @author Zhurenko Evgeniy
 */

let deleteIngredient = document.getElementById('delete');

if(deleteIngredient !== null) {
    deleteIngredient.addEventListener('click', deleteIngredientInput);
}

function deleteIngredientInput()  {

    let div = deleteIngredient.parentElement;

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


    //
    // document.ajax({
    //     type:"GET",
    //     contentType : 'application/json',
    //     url: '/ajax/delete-ingredient',
    //     data: {idBlog: idBlog, idIngredient: idIngredient},
    //     dataType: 'json',
    //     success: function (response) {
    //         if(response === true)
    //             div.remove()
    //     },
    //     error: function(jqXhr, textStatus, errorMessage){
    //         console.log("Error: ", errorMessage);
    //     }
    // });


}