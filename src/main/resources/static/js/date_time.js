
class DateTimeWork {
    days = ["Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"];
    months = ["января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"];

    constructor(n) {
        this.time = new Date();
        this.n = n;
    }

    dayMonth(time,n) {
        let date = '';
        date = (this.time.getDate() + n) + " " + this.months[time.getMonth()];
        return date;
    }

    day(time,n) {
        let day = (time.getDay() + n) < 7 ? (time.getDay() + n) : ((time.getDay() + n)-7);
        return day;
    }
}

let dateTimeWork = new DateTimeWork();

//внедрение даты и часов
document.querySelector('#date').innerHTML = dateTimeWork.days[dateTimeWork.time.getDay()] + "   "
    + dateTimeWork.time.getDate() + " " + dateTimeWork.months[dateTimeWork.time.getMonth()] + " "
    + dateTimeWork.time.getFullYear() + " г.   ";

setInterval( function() {
    let seconds = new Date().getSeconds();
    document.querySelector("#sec").innerHTML = ( seconds < 10 ? "0" : "" ) + seconds;
},1000);
setInterval( function() {
    let minutes = new Date().getMinutes();
    document.querySelector("#minutes").innerHTML = ( minutes < 10 ? "0" : "" ) + minutes;
},1000);
setInterval( function() {
    let hours = new Date().getHours();
    document.querySelector("#hours").innerHTML = ( hours < 10 ? "0" : "" ) + hours;
}, 1000);
