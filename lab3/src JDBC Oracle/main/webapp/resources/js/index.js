// Обновление времени
var localtime = document.getElementById('localtime');
function tick() {
    localtime.innerHTML = "Текущее время: " + new Date().toLocaleTimeString();
}

tick();
setInterval(() => tick(), 1 * 1000);