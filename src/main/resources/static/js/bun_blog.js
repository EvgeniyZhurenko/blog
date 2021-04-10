
$(document).ready(function () {
    $('.chek').change(function () {
        let ban_blog = false;
        let idBlog = $('h1').attr('value');
        let input = $(this);
        if (input.prop("checked")) {
            ban_blog = true;
            ajax_func_blog(idBlog, ban_blog);
        } else {
            ban_blog = false;
            ajax_func_blog(idBlog, ban_blog);
        }
    });
});

    let ajax_func_blog = function (id, ban) {
        $.ajax({
            type: "GET",
            contentType: 'application/json',
            url: '/ajax/ban/blog',
            data: {idBlog: id, bunBlog: ban},
            dataType: 'json',
            success: function (response) {
                if (response === true) {
                    let div_ban = $('.div_ban');
                    let label = $('.div_ban>label');
                    label.text('Cнять бан с блога');
                    let p = $('<p style="color: red" > Этот блог забанен администратором</p>');
                    div_ban.append(p);
                } else {
                    let label = $('.div_ban>label');
                    label.text('Пометить блог в бан');
                    let p = $('.div_ban>p');
                    p.remove();
                }
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("Error: ", errorMessage);
            }
        });
};
