package com.example.urlopy;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * Klasa DBUtil to ogólna klasa odpowiedzialna za obsługę BD, bardziej wyspecjalizowane czynności przechowywane są w pozostałych klasach DBUtil
 */
public abstract class DBUtil {


     abstract List<VacationDB> getVacations() throws Exception;

    /**
     * funkcja odpowiadająca za obsługę połączenia z BD
     * @param conn
     * @param statement
     * @param resultSet
     */
    protected static void close(Connection conn, Statement statement, ResultSet resultSet) {

        try {

            if (resultSet != null)
                resultSet.close();

            if (statement != null)
                statement.close();

            if (conn != null)
                conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}


