package com.example.urlopy;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBUtilManager extends DBUtil {

    private String URL;
    private String name;
    private String password;

    public DBUtilManager(String URL) {
        this.URL = URL;
    }

    public List<VacationDB> getVacations() throws Exception {

        List<VacationDB> vacations = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT * FROM vacations WHERE accepted=false";
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int vacationId = resultSet.getInt("vacation_id");
                int userId = resultSet.getInt("user_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                boolean accepted=resultSet.getBoolean("accepted");

                // dodanie do listy nowego obiektu
                vacations.add(new VacationDB(vacationId, userId, startDate, endDate, accepted));

            }

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }

        Collections.sort(vacations);
        return vacations;


    }


    public VacationDB getVacation(String id) throws Exception {

        VacationDB vacation = null;

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            // konwersja id na liczbe
            int vacationId = Integer.parseInt(id);

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT * FROM vacations WHERE vacation_id =?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, vacationId);

            // wykonanie zapytania
            resultSet = statement.executeQuery();

            // przetworzenie wyniku zapytania

            if (resultSet.next()) {

                //int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                boolean accepted=resultSet.getBoolean("accepted");

                // dodanie do listy nowego obiektu
                vacation=new VacationDB(vacationId, userId, startDate, endDate, accepted);

            } else {
                throw new Exception("Could not find vacation with id " + vacationId);
            }

            return vacation;

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);

        }

    }


    public void acceptVacation(String id) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {

            // konwersja id na liczbe
            int vacationId = Integer.parseInt(id);

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie DELETE
            String sql = "UPDATE vacations SET accepted=true " +
                    "WHERE vacation_id =?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, vacationId);

            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
