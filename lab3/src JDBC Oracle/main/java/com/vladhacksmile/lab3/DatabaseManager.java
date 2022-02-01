package com.vladhacksmile.lab3;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orbis";
    private static final String USER = "s307405";
    private static final String PASS = "hji837";
    private static Connection connection = null;
    private static Statement statement = null;
    private static boolean connected;

    public static boolean connect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Oracle JDBC Driver успешно загружен!");

            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            if (connection != null) {
                System.out.println("Успешное подключение к базе данных!");
                statement = connection.createStatement();
                connected = true;
                return true;
            } else {
                System.out.println("Не удалось подключиться к базе данных!");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver не найден. Подключите библиотеку PostgreSQL!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connected = false;
        return false;
    }

    public static boolean addRequest(UserRequest userRequest) {
        String select = "INSERT INTO requests(x, y, r, belong, requestdate, execution) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setFloat(1, userRequest.getX());
            preparedStatement.setFloat(2, userRequest.getY());
            preparedStatement.setFloat(3, userRequest.getR());
            preparedStatement.setBoolean(4, userRequest.isBelong());
            preparedStatement.setString(5, userRequest.getDate());
            preparedStatement.setFloat(6, userRequest.getExecutionTime());
            if(preparedStatement.executeUpdate() != 0) return true;
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении запроса в базу данных! " + e.getMessage());
        } catch (ClassCastException e){
            System.out.println("ClassCastException при добавлении запроса в базу данных!");
        }
        return false;
    }

    public static ArrayList<UserRequest> load() {
        ArrayList<UserRequest> arrayList = new ArrayList<>();
        String request = "SELECT * from requests";
        try {
            ResultSet resultSet = getStatement().executeQuery(request);
            while (resultSet.next()) {
                float cordX = resultSet.getFloat("x");
                float cordY = resultSet.getFloat("y");
                float radius = resultSet.getFloat("r");
                boolean belong = resultSet.getBoolean("belong");
                String date = resultSet.getString("requestdate");
                float execution = resultSet.getFloat("execution");
                UserRequest userRequest = new UserRequest(execution, date, cordX, cordY, radius, belong);
                arrayList.add(userRequest);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке таблицы запросов! " + e.getMessage());
        }
        return arrayList;
    }

    public static boolean clearRequests() {
        String request = "TRUNCATE TABLE requests";
        return execute(request);
    }

    public static boolean execute(String request) {
        try {
            if(statement.execute(request)) return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static boolean isConnected() {
        return connected;
    }
}
