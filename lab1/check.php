<?php
$x = (int) $_POST["coordinateX"];
$y = (double) $_POST["coordinateY"];
$r = (float) $_POST["radius"];

session_start();
if(!$_SESSION["requests"])
	$_SESSION["requests"] = array(); // Инициализируем массив, если сессия запущена впервые

function isRectangleTrigger($x, $y, $r) {
	return ($x >= -$r / 2 && $x <= 0 && $y >= $r && $y <= 0);
}

function isCircleTrigger($x, $y, $r) {
	return (($x >= 0) && ($y <= 0) && ((($y + $r / 2) - $x * $r / 2) >= 0));
}

function isTringleTrigger($x, $y, $r) {
	return (($x*$x + $y*$y) <= $r*$r/4 && $x >= 0 && $y >= 0);
}

function isBelong($x, $y, $r) {
	return (isRectangleTrigger($x, $y, $r) || isCircleTrigger($x, $y, $r) || isTringleTrigger($x, $y, $r));
}

function isValide($x, $y, $r) {
	return is_int($x) && in_array($x, range(-4, 4)) &&
	is_double($y) && ($y >= -3) && ($y <= 5) &&
	is_float($r) && in_array($r, array(1, 1.5, 2, 2.5, 3));
}

$state = isBelong($x, $y, $r);
$result = $state ? "Попадание" : "Нет попадания";
$style = $state ? ""  : ' class="neg"';
$executeTime = round(microtime(true) - $_SERVER['REQUEST_TIME'], 5);
date_default_timezone_set('Europe/Moscow'); // Установим часовую зону
$serverTime = date("H:i:s"); // Сформируем дату
		if(isValide($x, $y, $r)) {
			$_SESSION["requests"][] =
				"<tr$style>
				<td$style>$serverTime</td>
				<td$style>$executeTime</td>
				<td$style>$x</td>
				<td$style>$y</td>
				<td$style>$r</td>
				<td$style>$result</td>
			</tr>";
		} else {
			echo '[PHP] Введенные данные выходят за пределы допустимых значений! Допустимые данные: X: [-4; 4] - целое число, Y: [-3; 5], R: [1; 3] - целое число';
		}
		echo "<table>
			<thead>
			<tr>
				<td>Время запроса</td>
				<td>Время выполнения</td>
				<td>X</td>
				<td>Y</td>
				<td>R</td>
				<td>Результат</td>
			</tr>
			</thead>";
foreach ($_SESSION["requests"] as $request)
	echo $request;
echo "</table>";
?>