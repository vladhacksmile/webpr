	// Обновление времени
	var localtime = document.getElementById('localtime');
	setInterval(() => localtime.innerHTML = "Текущее время: " + new Date().toLocaleTimeString(), 1000);

	// Функция для отправки POST запроса
	async function sendPost(url, data = '') {
	  const response = await fetch(url, {
		method: 'POST',
		headers: {
		  'Content-Type': 'application/x-www-form-urlencoded',
		  'charset': 'utf-8'
		},
		body: data
	  });
	  return await response.text();
	}

	// Функция для формирования тела запроса
	function formatRequest(x, y, r) {
		return "coordinateX=" + encodeURIComponent(x) + "&coordinateY=" + encodeURIComponent(y) + "&radius=" + encodeURIComponent(r);
	}

	// Функция для проверки на число с плавающей запятой
	function isFloat(value) {
		return String(parseFloat(value, 10)) === String(value);
	}

	// Функция для проверки стринги на пустоту
	function isEmpty(str) {
		return str.trim() == '';
	}

	/* Функции для валидации значений */

	// Валидация X
	function validateX() {
		let preparedHtml = "";
		try {
			document.querySelector("select[name=coordinateX]").value;
		} catch(error) {
			preparedHtml = "<div style=\"color: red;\">Выберите координату X!</div>"
		}
		document.getElementById("coordinateXMessage").innerHTML = preparedHtml;
		return isEmpty(preparedHtml);
	}

	// Валидация Y
	function validateY() {
		let preparedHtml = "";
		let y = document.querySelector("input[name=coordinateY]").value.replace(/,/, '.');

		if(!isFloat(y)) {
			preparedHtml = "<div style=\"color: red;\">Координата Y должна быть числом!</div>";
		} else {
			if(y <= -3 || y >= 5) {
				preparedHtml = "<div style=\"color: red;\">Координата Y должна быть в диапазоне от -3 до 5!</div>";
			}
		}
		document.getElementById("coordinateYMessage").innerHTML = preparedHtml;
		return isEmpty(preparedHtml);
	}

	// Валидация R
	function validateR() {
		let preparedHtml = "";
		let r = document.querySelector("input[name=radius]").value.replace(/,/, '.');

		if(!isFloat(r)) {
			preparedHtml = "<div style=\"color: red;\">Координата R должна быть числом!</div>";
		} else {
			if(r < 1 || r > 4) {
				preparedHtml = "<div style=\"color: red;\">Координата R должна быть в диапазоне от 1 до 4!</div>";
			}
		}
		document.getElementById("radiusMessage").innerHTML = preparedHtml;
		return isEmpty(preparedHtml);
	}

	function isValid() {
		let triggered;
		if(!validateX()) triggered = true;
		if(!validateY()) triggered = true;
		if(!validateR()) triggered = true;
		return !triggered;
	}

	// Canvas и переменные для работы с ним
	var canvas = document.getElementById("graphCanvas");
	var context = canvas.getContext("2d");
	let halfCanvasWidth = canvas.width / 2;
	let halfCanvasHeight = canvas.height / 2;
	let coeff = 80;

	// Вешаем "лисенер" на кнопку проверки и шлем POST запрос
	document.getElementById("submitButton").onclick = function () {
		if(isValid()) {
		let x = document.querySelector("select[name=coordinateX]").value;
		let y = document.querySelector("input[name=coordinateY]").value.replace(/,/, '.');
		let r = document.querySelector("input[name=radius]").value.replace(/,/, '.');
			sendPost('ControllerServlet', formatRequest(x, y, r)).then(function (data) {
				document.getElementById("result").innerHTML = data;
				let pointX = halfCanvasWidth + (x * coeff) / r;
				let pointY = halfCanvasHeight - (y * coeff) / r;
				drawPoint(pointX, pointY);
			}).catch(error => alert("Произошла ошибка. " + error));
		}
	};

	document.getElementById("debugButton").onclick = function () {
		sendPost('ControllerServlet', "mode=clearTable").then(function (data) {
			document.getElementById("result").innerHTML = data;
			redrawGraph();
		}).catch(error => alert("Произошла ошибка. " + error));
	};

	function ready() {
		sendPost('ControllerServlet', "mode=getTable").then(function (data) {
				document.getElementById("result").innerHTML = data;
				redrawGraph();
			}).catch(error => alert("Произошла ошибка. " + error));
	}

	document.addEventListener("DOMContentLoaded", ready);

	document.getElementById("graphCanvas").onclick = function (e) {
		if(validateR()) {
			x = e.offsetX;
			y = e.offsetY;
			let R = document.querySelector("input[name=radius]").value.replace(/,/, '.');
			// let cordX = ((((R / 60) * (130 - x)) * -1) / 2).toFixed(1); // 90
			// let cordY = (((R / 60) * (130 - y)) / 2).toFixed(1); // 90
			let cordX = (((x < halfCanvasWidth ? (halfCanvasWidth - x) * -1 : x - halfCanvasWidth) / coeff) * R).toFixed(1);
			let cordY = (((y > halfCanvasHeight ? (y - halfCanvasHeight) * -1 : halfCanvasHeight - y) / coeff) * R).toFixed(1);
			drawPoint(x, y);
			sendPost('ControllerServlet', formatRequest(cordX, cordY, R)).then(function (data) {
				document.getElementById("result").innerHTML = data;
			}).catch(error => alert("Произошла ошибка. " + error));
		}
	}

	function drawGraph() {
		// Нарисуем круг
		context.beginPath();
		context.moveTo(130, 130);
		context.lineTo(130 + Math.cos(-Math.PI / 2), 130 + Math.sin(0))
		context.arc(130, 130, 55, -Math.PI / 2, 0);
		context.closePath();
		context.strokeStyle = "#0badea";
		context.fillStyle = "#0badea";
		context.stroke();
		context.fill();

		// Нарисуем треугольник
		context.beginPath();
		context.moveTo(130, 130);
		context.lineTo(220, 130);
		context.lineTo(130, 180);
		context.closePath();
		context.strokeStyle = '#0badea';
		context.stroke();
		context.fillStyle = "#0badea";
		context.fill();

		// Нарисуем прямоугольнк
		context.fillRect(80, 130, 50, 90);

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
		context.moveTo(0,130);
		context.lineTo(5,135);
		context.lineTo(5,125);
		context.fill();

		// Второй треугольник
		context.beginPath();
		context.moveTo(130,0);
		context.lineTo(135,5);
		context.lineTo(125,5);
		context.fill();

		// Третий треугольник
		context.beginPath();
		context.moveTo(260,130);
		context.lineTo(255,125);
		context.lineTo(255,135);
		context.fill();

		// Четвертый треугольник
		context.beginPath();
		context.moveTo(130,260);
		context.lineTo(125,255);
		context.lineTo(135,255);
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

	function drawPoint(posX, posY) {
		redrawGraph();
		context.beginPath();
		context.moveTo(posX, posY);
		context.arc(posX, posY, 3,0, 360);
		context.closePath();
		context.fill();
	}

	drawGraph();


