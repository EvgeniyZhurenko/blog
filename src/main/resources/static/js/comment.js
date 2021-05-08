
function send(){

    ajax_func_comment(idBlog, $('#name').attr('value'), $('#inputText').val(),
        $('#id_user').attr('value'))
};

let ajax_func_comment = function(idBlog, name, comment, idUser) {
    $.ajax({
        type:"GET",
        contentType : 'application/json',
        url: '/ajax/comment',
        data: {idBlog: idBlog, name: name, comment: comment, idUser: idUser},
        dataType: 'json',
        success: function (response) {

            let div = $('#comment');
            let pName = document.createElement('p');
            pName.text(new Date().toISOString() + " " + name );
            let br = document.createElement('br');
            let pComment = document.createElement('p');
            pComment.text(comment);
            div.append(pName).append(br).append(pComment);
            console.dir(div);
        },
        error: function(jqXhr, textStatus, errorMessage){
            console.log("Error: ", errorMessage);
        }
    });
};