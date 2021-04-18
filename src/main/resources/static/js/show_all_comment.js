$(document).ready( function () {
   let href = $('#show_comment');
   href.on('click', function () {
       let idBlog = $('#value_blog').attr('value');
       ajax_show_comment(idBlog);
   })
});

let ajax_show_comment = function(idBlog){
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: '/ajax/show/all-comment-blog/' + idBlog,
        date:{idBlog: idBlog},
        dataType: 'json',
        success: function (response) {
                console.dir(JSON.parse('['+ response + ']'));
                for (let i = 2; i < response.length; i++) {
                    let comment = '<div class="comment" ><hr>\n<p></p>\n<p></p>\n<hr>\n</div>';
                    let div = $('#comment');
                    div.append(comment);
                    let selectDiv = '.comment:nth-child(' + (i + 1) + ')';
                    let selectDivFirstP = '.comment:nth-child(' + (i + 1) + ')>p:nth-child(2)';
                    let selectDivSecondP = '.comment:nth-child(' + (i + 1) + ')>p:nth-child(3)';
                    $(selectDiv).attr('value', response[i].id);
                    $(selectDivFirstP).html(response[i].dateCreateComment);
                    $(selectDivSecondP).html(response[i].text);
                }
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
})};