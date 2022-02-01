package com.vladhacksmile.web.servlets;

import com.vladhacksmile.web.TableBean;
import com.vladhacksmile.web.TableController;
import com.vladhacksmile.web.UserRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

@WebServlet("/AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {

    public boolean isRectangleTrigger(float x, float y, float r) { // Valid
        return x >= -r / 2 && x <= 0 && y >= -r && y <= 0;
    }

    public boolean isCircleTrigger(float x, float y, float r) { // Fixed
        return (Math.pow(x, 2) + Math.pow(y, 2) - 0.1) <= ((Math.pow(r / 2, 2))) && (x >= 0) && (y >= 0);
    }

    public boolean isTriangleTrigger(float x, float y, float r) {
        return x >= 0 && y <= 0 && (r + r / 10 - x >= -2 * y);
    }

    public boolean isBelong(float x, float y, float r) {
        return isRectangleTrigger(x, y, r) || isCircleTrigger(x, y, r) || isTriangleTrigger(x, y, r);
    }

    public boolean isValid(String strX, String strY, String strR) {
        float x;
        float y;
        float r;
        try {
            x = Float.parseFloat(strX);
            y = Float.parseFloat(strY);
            r = Float.parseFloat(strR);
        } catch (Exception e) {
            return false;
        }
        return (x >= -4 && x <= 4) && (y >= -3 && y <= 5) && (r >= 1 && r <= 4);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long timeBefore = System.currentTimeMillis();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();

        String cordX = request.getParameter("coordinateX");
        String cordY = request.getParameter("coordinateY");
        String radius = request.getParameter("radius");

        HttpSession session = request.getSession();
        TableBean tableBean = (TableBean) session.getAttribute("table");

        if (tableBean == null) {
            tableBean = new TableBean(new ArrayList<UserRequest>());
        }

        if(isValid(cordX, cordY, radius)) {
            writer.println("X: " + cordX + ",  ");
            writer.println("Y: " + cordY + ", ");
            writer.println("R: " + radius + "");
            boolean status = isBelong(Float.parseFloat(cordX), Float.parseFloat(cordY),Float.parseFloat(radius));
            writer.println(status ? "<div style=\"color: green;\">Попадание</div>" : "<div style=\"color: red;\">Непопадание</div>");
            writer.println();

            long timeAfter = System.currentTimeMillis();
            tableBean.addRequest(new UserRequest((float) (timeAfter - timeBefore) / 100, new Date(),
                    Float.parseFloat(cordX), Float.parseFloat(cordY), Float.parseFloat(radius), status));
        } else {
            writer.println("Введенные данные выходят за пределы допустимых значений! Допустимые данные: X: [-4; 4], Y: [-3; 5], R: [1; 4]");
        }

        session.setAttribute("table", tableBean);

        TableController.printTable(writer, tableBean);
    }
}
