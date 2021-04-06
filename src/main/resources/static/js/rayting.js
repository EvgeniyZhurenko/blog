
$(document).ready(function () {
    $('#rate>div').click(function () {
        let div = $(this);
        let raiting = 0;
        if (div.css('background-color') !== 'rgb(255, 165, 0)') {

            while (div) {
                div.css('background-color', 'orange');
                div = div.prev('div');
                if (div.length === 0) return;
            }

        } else {
            while (div) {
                div.next('div').css('background-color', 'black');
                div = div.next('div');
                raiting++;
                console.dir(raiting);
                if (div.length === 0) return;
            }
        }

    });
});
