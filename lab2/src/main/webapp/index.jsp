<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Second lab</title>
    <meta charset="utf-8">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <style type="text/css">
        table {
            background: white;
            color: white;
            border-spacing: 1px;
            margin: auto;
            border-radius: 10px;
            margin-top: 10px;
        }
        /* Селектор элемента */
        td, th {
            background: limegreen;
            padding: 5px;
            border-style: solid;
            border-width: 0 1px 1px 0;
            border-color: white;
        }
        .neg {
            background: red;
        }

        div.container thead {
            background: #3EDE8E;
        }

        div.container {
            margin: 5% 20%;
            text-align: center;
        }
        /* Селектор атрибута (дополнительно)*/
        input[type="text"] {
            border: 2px solid skyblue;
        }
        /* Дочерний селектор */
        .container > header {
            color: blue;
            border: 3px solid red;
            padding: 10px;
            font-family: monospace;
            font-size: 14pt;
        }

        input[type="submit"] {
            width:90px;
            text-align:center;
            display:block;
            font-family: arial;
            text-decoration: none;
            font-weight: 300;
            font-size: 14px;
            border: #1071FF 1px solid;
            color: #1071FF;
            padding: 3px;
            padding-left: 5px;
            padding-right: 5px;
            margin: 20px auto;
            transition: .5s;
            border-radius: 0px;
        }
        input[type="submit"]:hover {
            top: 5px;
            transition: .5s;
            color: #10FF58;
            border: #10FF58 1px solid;
            border-radius: 10px;
        }
        input[type="submit"]:active {
            color: #000;
            border: #1A1A1A 1px solid;
            transition: .07s;
            background-color: #FFF;
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <b>Лабораторная работа 2<br>
            Вариант 15319</b><br>
        Харин Владислав Сергеевич<br>
        Группа: P3215<br>
    </header>
    <div>
        <canvas id="graphCanvas" width="260" height="260" style="border:1px solid #000000; margin-top: 5px;">
            Ваш браузер не поддерживает технологию Canvas
            <img src ="./images/graphic.png">
        </canvas>
    </div>
    <div id="localtime">Загрузка....</div>
    <div>
        <p><b>Выберите координату X:</b><br>
            <select name="coordinateX">
                <% for(int i = 4; i >= -4; i--) { %>
                    <option value="<%=i%>"><%=i%></option>
                <% } %>
            </select>

        <div type="message" id="coordinateXMessage"></div>
    </div>
    <div>
        <p><b>Введите координату Y:</b><br>
            <input type="text" name="coordinateY" id="text1" placeholder="(-3 ... 5)" maxlength="12">
        <div type="message" id="coordinateYMessage"></div>
    </div>
    <div>
        <p><b>Выберите радиус:</b><br>
            <input type="text" name="radius" id="text2" placeholder="(1 ... 4)" maxlength="12">
        <div type="message" id="radiusMessage"></div>
    </div>
    <div>
        <input type="submit" id="submitButton" value="Проверить">
        <input type="submit" id="debugButton" value="Очистить">
    </div>
    <div id="result">
        <table>
            <thead>
            <tr>
                <td>Время запроса</td>
                <td>Время выполнения</td>
                <td>X</td>
                <td>Y</td>
                <td>R</td>
                <td>Результат</td>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script src="js/processor.js"></script>
</body>
</html>