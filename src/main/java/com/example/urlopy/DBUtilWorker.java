package com.example.urlopy;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBUtilWorker extends DBUtil {

    private String URL;
    private String name;
    private String password;
    private int id;

    public DBUtilWorker(String URL) {
        this.URL = URL;
    }

    public List<VacationDB> getVacations() throws Exception {

        int x=getUserId();

        List<VacationDB> vacations = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT * FROM vacations WHERE user_id=?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery();

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

    public void addVacation(VacationDB vacation) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie INSERT i ustawienie jego parametrow
            String sql = "INSERT INTO vacations(user_id, start_date, end_date, accepted) " +
                    "VALUES(?,?,?,?)";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, vacation.getUserId());
            statement.setDate(2, vacation.getStartDate());
            statement.setDate(3, vacation.getEndDate());
            statement.setBoolean(4, false);


            // wykonanie zapytania
            statement.execute();


        } finally {

            close(conn, statement, null);

        }

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

    public void updateVacation(VacationDB vacation) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie UPDATE
            String sql = "INSERT INTO edited_vacations(vacation_id, user_id, start_date, end_date, accepted) " +
                    "VALUES(?,?,?,?,?)";

            statement = conn.prepareStatement(sql);

            statement.setInt(1, vacation.getVacationId());
            statement.setInt(2, vacation.getUserId());
            statement.setDate(3, vacation.getStartDate());
            statement.setDate(4, vacation.getEndDate());;
            statement.setBoolean(5, vacation.isAccepted());


            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    public void insertVacation(VacationDB vacation) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {


            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie INSERT
            String sql = "INSERT INTO removed_vacations(vacation_id, user_id, start_date, end_date, accepted) " +
                    "VALUES(?,?,?,?,?)";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, vacation.getVacationId());
            statement.setInt(2, vacation.getUserId());
            statement.setDate(3, vacation.getStartDate());
            statement.setDate(4, vacation.getEndDate());
            statement.setBoolean(5, vacation.isAccepted());

            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }


    }

    public void deleteVacation(String id) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {

            // konwersja id na liczbe
            int vacationId = Integer.parseInt(id);

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie DELETE
            String sql = "DELETE FROM vacations WHERE vacation_id =?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, vacationId);

            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    public int getUserId() throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie UPDATE
            String sql = "SELECT user_id from accounts " +
                    "WHERE username =?";

            statement = conn.prepareStatement(sql);
            statement.setString(1, name);

            resultSet = statement.executeQuery();

            while (resultSet.next()){
                id= resultSet.getInt("user_id");
            }

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
    }
}
