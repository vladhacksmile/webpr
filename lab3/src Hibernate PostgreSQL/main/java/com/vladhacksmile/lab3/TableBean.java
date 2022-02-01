package com.vladhacksmile.lab3;

import com.vladhacksmile.lab3.hibernate.HibernateUtil;
import org.hibernate.query.Query;
import org.primefaces.PrimeFaces;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;
@ManagedBean
public class TableBean implements Serializable {
    private ArrayList<UserRequest> userRequests = new ArrayList<>();
    private String coordinateX;
    private String coordinateY;
    private String radius;
    private boolean belong;

    private String canvasCoordinateX;
    private String canvasCoordinateY;

    @PostConstruct
    public void init() {
        Session hibernateSession = HibernateUtil.getNewSession();
        hibernateSession.beginTransaction();
        String queryString = "FROM UserRequest";
        Query query = hibernateSession.createQuery(queryString, UserRequest.class);
        userRequests = (ArrayList<UserRequest>) query.list();
        if(!userRequests.isEmpty()) {
            radius = "1";
        }
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
    }

    public boolean isValid(String strX, String strY, String strR) {
        if(strX == null || strX.isEmpty()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Произошла ошибка", "Выберите координату X!");
        }
        if(strY == null || strY.isEmpty()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Произошла ошибка", "Выберите координату Y!");
        }
        if(strR == null || strR.isEmpty()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Произошла ошибка", "Выберите радиус R!");
        }
        if(strX == null || strX.isEmpty() || strY == null || strY.isEmpty() || strR == null || strR.isEmpty()) {
            return false;
        }
        float x;
        float y;
        float r;
        try {
            x = Float.parseFloat(strX);
            y = Float.parseFloat(strY);
            r = Float.parseFloat(strR);
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Произошла ошибка", "Проверьте введенные данные!");
            return false;
        }
        if(x < -4 || x > 4) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Произошла ошибка", "Координата X должна быть в диапазоне от -4 до 4!");
        }
        if(y < -3 || y > 5) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Произошла ошибка", "Координата Y должна быть в диапазоне от -3 до 5!");
        }
        if(r < 1 || r > 5) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Произошла ошибка", "Радиус R должен быть в диапазоне от 1 до 5!");
        }
        return (x >= -4 && x <= 4) && (y >= -3 && y <= 5) && (r >= 1 && r <= 5);
    }

    public ArrayList<UserRequest> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(ArrayList<UserRequest> userRequests) {
        this.userRequests = userRequests;
    }

    public boolean isRectangleTrigger(float x, float y, float r) {
        return x <= r / 2 && x >= 0 && y >= -r && y <= 0;
    }

    public boolean isCircleTrigger(float x, float y, float r) {
        return (Math.pow(x, 2) + Math.pow(y, 2)) <= ((Math.pow(r, 2))) && (x >= 0) && (y >= 0);
    }

    public boolean isTriangleTrigger(float x, float y, float r) {
        return (((x >= -r/2) && (x <= 0) && (y >= 0) && (y <= r / 2)) && (y <= x + r/2));
    }

    public boolean isBelong(float x, float y, float r) {
        return isRectangleTrigger(x, y, r) || isCircleTrigger(x, y, r) || isTriangleTrigger(x, y, r);
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void check() {
        long timeBefore = System.currentTimeMillis();
        if(isValid(coordinateX, coordinateY, radius)) {
            belong = isBelong(Float.parseFloat(coordinateX), Float.parseFloat(coordinateY), Float.parseFloat(radius));
            long timeAfter = System.currentTimeMillis();
            UserRequest userRequest = new UserRequest((float) (timeAfter - timeBefore), new SimpleDateFormat().format(new Date()), Float.parseFloat(coordinateX), Float.parseFloat(coordinateY), Float.parseFloat(radius), belong);
            addRequest(userRequest);
            addMessage(FacesMessage.SEVERITY_INFO, "Информация", "Точка (" + coordinateX + ", " + coordinateY + ") при R = " + radius + " проверена!<br>Результат: <strong>" + (belong ? "принадлежит" : "не приналеджит") + "</strong>");
        }
    }

    public void clear() {
        userRequests.clear();
        Session hibernateSession = HibernateUtil.getNewSession();
        hibernateSession.beginTransaction();
        String stringQuery = "DELETE FROM UserRequest";
        Query query = hibernateSession.createQuery(stringQuery);
        query.executeUpdate();
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
        addMessage(FacesMessage.SEVERITY_WARN, "Внимание", "Таблица очищена!");
    }

    public void addRequest(UserRequest userRequest) {
        userRequests.add(userRequest);
        Session hibernateSession = HibernateUtil.getNewSession();
        hibernateSession.beginTransaction();
        hibernateSession.save(userRequest);
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
    }

    public String getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(String coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(String coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public boolean isBelong() {
        return belong;
    }

    public void setBelong(boolean belong) {
        this.belong = belong;
    }

    public String getCanvasCoordinateX() {
        return canvasCoordinateX;
    }

    public void setCanvasCoordinateX(String canvasCoordinateX) {
        this.canvasCoordinateX = canvasCoordinateX;
    }

    public String getCanvasCoordinateY() {
        return canvasCoordinateY;
    }

    public void setCanvasCoordinateY(String canvasCoordinateY) {
        this.canvasCoordinateY = canvasCoordinateY;
    }

    public void foo() {
        System.out.println("foo");
    }

    public void redrawGraph() {
        PrimeFaces.current().executeScript("redrawGraph();");
        if(!userRequests.isEmpty() && radius == null) {
            radius = "1";
        }
        for(UserRequest req: userRequests) {
            float pointX = 130 + (req.getX() * 80) / Float.parseFloat(radius);
            float pointY = 130 - (req.getY() * 80) / Float.parseFloat(radius);
            PrimeFaces.current().executeScript("drawPoint(" + pointX + ", " + pointY + ", " + isBelong(req.getX(), req.getY(), Float.parseFloat(radius)) + ");");
        }
    }

    public void addFromCanvas() {
        long timeBefore = System.currentTimeMillis();
        if(radius == null || radius.isEmpty()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Произошла ошибка", "Выберите радиус R!");
            return;
        }
        try {
            float parsedCanvasX = Float.parseFloat(canvasCoordinateX);
            float parsedCanvasY = Float.parseFloat(canvasCoordinateY);
            float cordX = ((parsedCanvasX < 130 ? (130 - parsedCanvasX) * -1 : parsedCanvasX - 130) / 80) * Float.parseFloat(radius);
            float cordY = ((parsedCanvasY > 130 ? (parsedCanvasY - 130) * -1 : 130 - parsedCanvasY) / 80) * Float.parseFloat(radius);
            if(isValid(String.valueOf(cordX), String.valueOf(cordY), radius)) {
                belong = isBelong(cordX, cordY, Float.parseFloat(radius));
                long timeAfter = System.currentTimeMillis();
                UserRequest userRequest = new UserRequest((timeAfter - timeBefore), new SimpleDateFormat().format(new Date()), cordX, cordY, Float.parseFloat(radius), belong);
                addRequest(userRequest);
                addMessage(FacesMessage.SEVERITY_INFO, "Информация", "Точка (" + cordX + ", " + cordY + ") при R = " + radius + " проверена!<br>Результат: <strong>" + (belong ? "принадлежит" : "не приналеджит") + "</strong>");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Произошла ошибка", "Не удалось преобразовать данные в тип Float!");
            e.printStackTrace();
        }
    }
}
