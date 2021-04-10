
$(document).ready(function () {
    $('.comment_chek').change(function () {
        let ban_comment = false;
        let input = $(this);
        let div = input.parent('.comment_ban');
        let idComment = div.attr('value');
        if (input.prop("checked")) {
            ban_comment = true;
            ajax_func_comment(idComment, ban_comment);
        } else {
            ban_comment = false;
            ajax_func_comment(idComment, ban_comment);
        }
    });
});

let ajax_func_comment = function (id, ban) {
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: '/ajax/ban/comment',
        data: {idComment: id, bunComment: ban},
        dataType: 'json',
        success: function (response) {
            let div_ban = $('.comment_ban');
            div_ban.each(function(index, elem){
                if($(elem).attr('value') === id){
                    if(response === true){
                        let label = $(elem).find('label');
                        label.text('Cнять бан с комментария');
                        let p = $('<p style="color: red" > Этот блог забанен администратором</p>');
                        $(elem).append(p);
                        return;
                    } else {
                        let label = $(elem).find('label');
                        label.text('Забанить комменатрий');
                        let p = $(elem).find('p');
                        p.remove();
                        return;
                    }
                }
            })
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
    });
};