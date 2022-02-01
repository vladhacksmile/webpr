/*
 Функция для установки радиуса посредством нажатия commandButton
 У меня не получилось сделать это средствами primeFaces, поэтому
 пришлось прибегнуть к такому не совсем правильному решению
*/
let radius = 0;
let previousEvent;
function setR(r, event) {
    radius = r;
    jQuery(previousEvent).removeClass('ui-state-disabled');
    previousEvent = event;
    jQuery(event).addClass('ui-state-disabled');
}

let coordinateX = 0;
function setX(x) {
    coordinateX = x;
}