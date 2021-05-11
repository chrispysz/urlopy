package com.example.urlopy;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.List;

/**
 * Klasa RegisterServlet odpowiada za połączenie widoków rejestracji z pozostałymi klasami obsługująćymi bazę danych
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private DBUtilRegister dbUtil;
    private final String db_url = "jdbc:postgresql://localhost:5432/urlopy?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {

            dbUtil = new DBUtilRegister(db_url);
            dbUtil.setName("test_admin");
            dbUtil.setPassword("pass");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    /**
     * funkcja odpowiedzialna za obsługę zapytań Post
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        response.setContentType("text/html");

        if (validate("test_admin", "pass")) {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");


            try {

                String uname = request.getParameter("usernameInput");
                String pass = request.getParameter("passwordInput");
                String properPass="'"+pass+"'";
                String email = request.getParameter("emailInput");

                dbUtil.registerUser(uname, properPass,pass, email);

                request.setAttribute("success", "Poprawnie utworzono użytkownika!");

            } catch (Exception e) {
                request.setAttribute("error", "Rejestracja nie powiodła się!");
            }

            dispatcher.forward(request, response);
        } else {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
            dispatcher.include(request, response);
        }


    }

    /**
     * funkcja odpowiadająca za potwierdzenie poprawności hasła
     * @param name login
     * @param pass hasło
     * @return czy potwierdzenie przzebiegło pomyślnie
     */
    private boolean validate(String name, String pass) {
        boolean status = false;

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(db_url, name, pass);
            status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

}
