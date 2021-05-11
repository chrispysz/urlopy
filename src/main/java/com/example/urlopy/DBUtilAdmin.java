package com.example.urlopy;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa odpowiedzialna za obsługę bazy danych w zakresie działań admina
 */
public class DBUtilAdmin extends DBUtil {

    private String URL;
    private String name;
    private String password;

    public DBUtilAdmin(String URL) {
        this.URL = URL;
    }

    @Override
    List<VacationDB> getVacations() throws Exception {
        return null;
    }

    /**
     * zwraca liste z tabeli edited_vacations
     * @return liste z tabeli edited_vacations
     * @throws Exception
     */
    public List<VacationDB> getEditedVacations() throws Exception {

        List<VacationDB> vacations = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT * FROM edited_vacations";
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

    /**
     * zwraca liste z tabeli removed_vacations
     * @return lista z tabeli removed_vacations
     * @throws Exception
     */
    public List<VacationDB> getRemovedVacations() throws Exception {

        List<VacationDB> vacations = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT * FROM removed_vacations";
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

    /**
     * funkcja odpowiedzialna za wstawienie urlopu do tabeli vacations
     * @param vacation
     * @throws Exception
     */
    public void addVacation(VacationDB vacation) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie INSERT i ustawienie jego parametrow
            String sql = "INSERT INTO vacations(vacation_id, user_id, start_date, end_date, accepted) " +
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

            close(conn, statement, null);

        }

    }

    /**
     * zwraca urlop z podanym ID
     * @param id
     * @return urlop z podanym ID
     * @throws Exception
     */
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
            String sql = "SELECT * FROM edited_vacations WHERE vacation_id =?";

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

    /**
     * zwraca urlop z podanym ID z tabeli edited_vacations
     * @param id
     * @return urlop z podanym ID z tabeli edited_vacations
     * @throws Exception
     */
    public VacationDB getEditedVacation(String id) throws Exception {

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
            String sql = "SELECT * FROM edited_vacations WHERE vacation_id =?";

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
    /**
     * zwraca urlop z podanym ID z tabeli removed_vacations
     * @param id
     * @return urlop z podanym ID z tabeli removed_vacations
     * @throws Exception
     */
    public VacationDB getRemovedVacation(String id) throws Exception {

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
            String sql = "SELECT * FROM removed_vacations WHERE vacation_id =?";

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

    /**
     * aktualizuję urlop w tabeli vacations
     * @param vacation dane urlopu do edycji
     * @throws Exception
     */
    public void updateVacation(VacationDB vacation) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie UPDATE
            String sql = "UPDATE vacations SET start_date=?, end_date=?, accepted=? " +
                    "WHERE vacation_id =?";

            statement = conn.prepareStatement(sql);
            statement.setDate(1, vacation.getStartDate());
            statement.setDate(2, vacation.getEndDate());
            statement.setBoolean(3, vacation.isAccepted());
            statement.setInt(4, vacation.getVacationId());

            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    /**
     * usuwa urlop z tabeli removed_vacations
     * @param id id urlopu do usunięcia
     * @throws Exception
     */
    public void deleteVacation(String id) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {

            // konwersja id na liczbe
            int vacationId = Integer.parseInt(id);

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie DELETE
            String sql = "DELETE FROM removed_vacations WHERE vacation_id =?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, vacationId);

            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }
    /**
     * usuwa urlop z tabeli edited_vacations
     * @param id id urlopu do usunięcia
     * @throws Exception
     */
    public void deleteEditedVacation(String id) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {

            // konwersja id na liczbe
            int vacationId = Integer.parseInt(id);

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie DELETE
            String sql = "DELETE FROM edited_vacations WHERE vacation_id =?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, vacationId);

            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    /**
     * dodaje urlop do tabeli vacations
     * @param vacation parametry urlopu do dodania
     * @throws Exception
     */
    public void insertVacation(VacationDB vacation) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {


            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie INSERT
            String sql = "INSERT INTO vacations(vacation_id, user_id, start_date, end_date, accepted) " +
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



    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
