package com.vladhacksmile.web.servlets;

import com.vladhacksmile.web.TableBean;
import com.vladhacksmile.web.TableController;
import com.vladhacksmile.web.UserRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ControllerServlet", value = "/ControllerServlet")

public class ControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("mode") != null) {
            //
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            HttpSession session = req.getSession();
            PrintWriter writer = resp.getWriter();
            //
            switch (req.getParameter("mode")) {
                case "clearTable":
                    session.setAttribute("table", null);
                    writer.println("Таблица очищена");
                    TableController.printTableHeader(writer, true);
                    break;
                case "getTable":
                    TableBean tableBean = (TableBean) session.getAttribute("table");
                    if (tableBean == null) {
                        tableBean = new TableBean(new ArrayList<UserRequest>());
                    }
                    TableController.printTable(writer, tableBean);
                    break;
                case "checkArea":
                    if(req.getParameter("coordinateX") != null && req.getParameter("coordinateY") != null && req.getParameter("radius") != null)
                        req.getRequestDispatcher("/AreaCheckServlet").forward(req, resp);
                    break;
                default:
                    writer.println("Неизвестный запрос!");
                    break;
            }
        } else {
            if(req.getParameter("coordinateX") != null && req.getParameter("coordinateY") != null && req.getParameter("radius") != null)
                req.getRequestDispatcher("/AreaCheckServlet").forward(req, resp);
        }
    }
}
