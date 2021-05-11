package com.example.urlopy;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa odpowiedzialna za obsługę bazy danych w zakresie rejestracji
 */
public class DBUtilRegister extends DBUtil {

    private String URL;
    private String name;
    private String password;

    public DBUtilRegister(String URL) {
        this.URL = URL;
    }

    /**
     * zwraca listę niezaakceptowanych urlopów
     * @return lista niezakceptowanych urlopów
     * @throws Exception
     */
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
                boolean accepted = resultSet.getBoolean("accepted");

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
     * tworzy nowego usera (pracownika), na podstawie przesłanych danych, dodaje go do listy użytkowników, oraz nadaje mu potrzebne przywileje
     * @param uname
     * @param pass
     * @param barePass
     * @param emai
     * @throws Exception
     */
    public void registerUser(String uname, String pass, String barePass, String emai) throws Exception {

        Connection conn = null;
        Statement statement = null;

        try {

            conn = DriverManager.getConnection(URL, name, password);

            statement = conn.createStatement();
            statement.executeUpdate("CREATE USER " + uname + " WITH PASSWORD " + pass);

            statement = conn.createStatement();
            statement.executeUpdate("GRANT USAGE, SELECT ON SEQUENCE vacations_vacation_id_seq TO "+uname);

            statement = conn.createStatement();
            statement.executeUpdate("GRANT SELECT ON vacations TO "+uname);

            statement = conn.createStatement();
            statement.executeUpdate("GRANT SELECT ON accounts TO "+uname);

            statement = conn.createStatement();
            statement.executeUpdate("GRANT INSERT ON vacations TO "+uname);

            statement = conn.createStatement();
            statement.executeUpdate("GRANT INSERT ON edited_vacations TO "+uname);

            statement = conn.createStatement();
            statement.executeUpdate("GRANT INSERT ON removed_vacations TO "+uname);

            statement = conn.createStatement();
            statement.executeUpdate("GRANT DELETE ON vacations TO "+uname);

            statement = conn.createStatement();
            statement.executeUpdate("GRANT UPDATE ON accounts TO "+uname);

            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO accounts(username, password, email) " +
                    "VALUES ('"+uname+"','"+barePass+"','"+emai+"')");

            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO accounts_roles(user_id, role_id) " +
                    "VALUES ("+getUserId(uname)+",3)");


        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    /**
     * zwraca id użytkownika o podanym loginie
     * @param uname
     * @return
     * @throws Exception
     */
    public int getUserId(String uname) throws Exception {

        int id=0;

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
            statement.setString(1, uname);

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
}
