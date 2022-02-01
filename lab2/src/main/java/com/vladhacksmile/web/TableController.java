package com.vladhacksmile.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class TableController {

    public static void printTable(PrintWriter writer, TableBean tableBean) {
        printTableHeader(writer, false);
        for (UserRequest userRequest : tableBean.getUserRequests()) {
            boolean belong = userRequest.isBelong();
            writer.println("<tr class=\"neg\">");
            writer.println("<td>" + new SimpleDateFormat("dd.MM.yy HH:mm:ss").format(userRequest.getRequestDate()) + "</td>");
            writer.println("<td>" + userRequest.getExecutionTime() + "</td>");
            writer.println("<td>" + userRequest.getX() + "</td>");
            writer.println("<td>" + userRequest.getY() + "</td>");
            writer.println("<td>" + userRequest.getR() + "</td>");
            writer.println("<td>" + (belong ? "Попадание" : "Нет попадания") + "</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");
    }

    public static void printTableHeader(PrintWriter writer, boolean tag) {
        writer.println("<table>\n" +
                "\t\t\t<thead>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Время запроса</td>\n" +
                "\t\t\t\t<td>Время выполнения</td>\n" +
                "\t\t\t\t<td>X</td>\n" +
                "\t\t\t\t<td>Y</td>\n" +
                "\t\t\t\t<td>R</td>\n" +
                "\t\t\t\t<td>Результат</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t</thead>");
        if(tag) writer.println("</table>");
    }
}
