// Обновление времени
			var localtime = document.getElementById('localtime');
			setInterval(() => localtime.innerHTML = "Текущее время: " + new Date().toLocaleTimeString(), 1000);

			// Обработчик чекбоксов - позволяет выбирать только один вариант
			$('input[name="coordinateX"]').on('change', function() {
		  		$('input[name="coordinateX"]').not(this).prop('checked', false);
			});

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
					document.querySelector("input[name=coordinateX]:checked").value;
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
				try {
					document.querySelector("input[name=radius]:checked").value;
				} catch(error) {
					preparedHtml = "<div style=\"color: red;\">Выберите координату R!</div>"
				}
				document.getElementById("radiusMessage").innerHTML = preparedHtml;
				return isEmpty(preparedHtml);
			}

			function isValide() {
				let triggered;
				if(!validateX()) triggered = true;
				if(!validateY()) triggered = true;
				if(!validateR()) triggered = true;
				return !triggered;
			}

			// Вешаем "лисенер" на кнопку проверки и шлем POST запрос
			document.getElementById("submitButton").onclick = function () {
				if(isValide()) {
				let x = document.querySelector("input[name=coordinateX]:checked").value;
				let y = document.querySelector("input[name=coordinateY]").value.replace(/,/, '.');
				let r = document.querySelector("input[name=radius]:checked").value;
        			sendPost('check.php', formatRequest(x, y, r)).then(function (data) {
            			document.getElementById("result").innerHTML = data;
        			}).catch(error => alert("Произошла ошибка. " + error));
        		}
     		};

     		document.getElementById("debugButton").onclick = function () {
     			sendPost('debug.php');
     			document.getElementById("result").innerHTML = "<table><thead><tr><td>Время запроса</td><td>Время выполнения</td><td>X</td><td>Y</td><td>R</td><td>Результат</td></tr></thead></table>";
     		};


			function ready() {
				sendPost('check.php', formatRequest(0, 0, 0)).then(function (data) {
            			document.getElementById("result").innerHTML = data;
        			}).catch(error => alert("Произошла ошибка. " + error));
			}

     		document.addEventListener("DOMContentLoaded", ready);