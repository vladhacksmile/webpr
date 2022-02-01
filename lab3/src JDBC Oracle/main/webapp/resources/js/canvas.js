// Canvas и переменные для работы с ним
var canvas = document.getElementById("graphCanvas");
var context = canvas.getContext("2d");

document.getElementById("graphCanvas").onclick = function (e) {
    x = e.offsetX;
    y = e.offsetY;
    $('[id$=x]').val(e.offsetX).trigger('change');
    $('[id$=y]').val(e.offsetY).trigger('change');
    sendCanvasPoint();
}

function drawGraph() {
    // Нарисуем круг
    context.beginPath();
    context.moveTo(130, 130);
    context.lineTo(130 + Math.cos(-Math.PI / 2), 130 + Math.sin(0))
    context.arc(130, 130, 80, -Math.PI / 2, 0);
    context.closePath();
    context.strokeStyle = "#0badea";
    context.fillStyle = "#0badea";
    context.stroke();
    context.fill();

    // Нарисуем треугольник
    context.beginPath();
    context.moveTo(130, 130);
    context.lineTo(130, 90);
    context.lineTo(90, 130);
    context.closePath();
    context.strokeStyle = '#0badea';
    context.stroke();
    context.fillStyle = "#0badea";
    context.fill();

    // Нарисуем прямоугольнк
    context.fillRect(130, 130, 40, 80);

    context.beginPath();

    // Ось X (линии)
    context.moveTo(0, 130);
    context.lineTo(260, 130);

    // Ось Y (линии)
    context.moveTo(130, 0);
    context.lineTo(130, 260);

    context.strokeStyle = "#000";
    context.stroke();

    context.font = "18px Arial";
    context.fillStyle = "#000"

    /* Треугольники к осям */

    // Первый треугольник
    context.beginPath();
    context.moveTo(0, 130);
    context.lineTo(5, 135);
    context.lineTo(5, 125);
    context.fill();

    // Второй треугольник
    context.beginPath();
    context.moveTo(130, 0);
    context.lineTo(135, 5);
    context.lineTo(125, 5);
    context.fill();

    // Третий треугольник
    context.beginPath();
    context.moveTo(260, 130);
    context.lineTo(255, 125);
    context.lineTo(255, 135);
    context.fill();

    // Четвертый треугольник
    context.beginPath();
    context.moveTo(130, 260);
    context.lineTo(125, 255);
    context.lineTo(135, 255);
    context.fill();

    // Ось X (координаты)
    context.fillText("R/2", 170, 128);
    context.fillText("R", 210, 128);

    context.fillText("-R/2", 90, 128);
    context.fillText("-R", 50, 128);

    // Ось Y (координаты)
    context.fillText("R/2", 133, 80);
    context.fillText("R", 133, 40);

    context.fillText("-R/2", 133, 180);
    context.fillText("-R", 133, 220);
}

function clearGraph() {
    context.clearRect(0, 0, canvas.width, canvas.height);
}

function redrawGraph() {
    clearGraph();
    drawGraph();
}

function drawPoint(posX, posY, belong) {
    context.beginPath();
    context.moveTo(posX, posY);
    context.arc(posX, posY, 3,0, 360);
    context.closePath();
    context.fillStyle = belong ? "#61f200" : "#ff0000";
    context.fill();
}