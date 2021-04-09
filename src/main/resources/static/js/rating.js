
let rating = 0;
let idBlog = $('#p_rate').attr('value');

$(document).ready(function () {
    $('#rate>div').click(function () {
        let div = $(this);
        if (div.css('background-color') === 'rgb(255, 165, 0)') {
            while (div) {
                div.next('div').css('background-color', 'black');
                div = div.next('div');

                if (div.length === 0) {
                    let divs = $('#rate>div');
                    for(let i = 0; i < divs.length; i++){
                        if(divs[i].style.backgroundColor === 'orange'){
                            rating++;
                        }
                    }

                    ajax_func(idBlog,rating);
                    rating = 0;
                    return;
                }
            }
        } else {

            while (div) {
                div.css('background-color', 'orange');
                div = div.prev('div');

                if (div.length === 0) {
                    let divs = $('#rate>div');
                    for(let i = 0; i < divs.length; i++){
                        if(divs[i].style.backgroundColor === 'orange'){
                            rating++;
                        }
                    };

                    ajax_func(idBlog,rating);
                    rating = 0;
                    return;
                }
            }
        }
    });
});

let ajax_func = function(id, rating) {
    $.ajax({
        type:"GET",
        contentType : 'application/json',
        url: '/ajax/rating',
        data: {idBlog: id, rating: rating},
        dataType: 'json',
        success: function (response) {
                let span = $('#rating');
                span.text(response);
        },
        error: function(jqXhr, textStatus, errorMessage){
            console.log("Error: ", errorMessage);
        }
    });
};