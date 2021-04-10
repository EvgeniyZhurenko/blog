$(document).ready(function () {
    $('.chek').change(function () {
        let ban_user = false;
        let input = $(this);
        let div = input.parent('.div_ban');
        let idUser = div.attr('value');
        if (input.prop("checked")) {
            ban_user = true;
            ajax_func_comment(idUser, ban_user);
        } else {
            ban_user = false;
            ajax_func_comment(idUser, ban_user);
        }
    });
});

let ajax_func_comment = function (id, ban) {
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: '/ajax/ban/user',
        data: {idUser: id, bunUser: ban},
        dataType: 'json',
        success: function (response) {
            let div_ban = $('.div_ban');
            div_ban.each(function(index, elem){
                console.log(elem);
                if($(elem).attr('value') === id){
                    if(response === true){
                        let label = $(elem).find('label');
                        label.text('Cнять бан с пользователя');
                        let p = $('<p style="color: red" > Этот пользователь забанен администратором</p>');
                        $(elem).append(p);
                        return;
                    } else {
                        let label = $(elem).find('label');
                        label.text('Забанить пользователя');
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