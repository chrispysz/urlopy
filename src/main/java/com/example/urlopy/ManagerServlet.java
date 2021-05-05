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
import java.sql.DriverManager;
import java.util.List;

@WebServlet("/ManagerServlet")
public class ManagerServlet extends HttpServlet {

    private DBUtilManager dbUtil;
    private final String db_url = "jdbc:postgresql://localhost:5432/urlopy?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {

            dbUtil = new DBUtilManager(db_url);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        response.setContentType("text/html");

        String name = request.getParameter("loginInput");
        String password = request.getParameter("passwordInput");

        dbUtil.setName(name);
        dbUtil.setPassword(password);

        if (validate(name, password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/manager_view.jsp");

            List<VacationDB> vacationList = null;

            try {

                vacationList = dbUtil.getVacations();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // dodanie listy do obiektu zadania
            request.setAttribute("VACATIONS_LIST", vacationList);

            dispatcher.forward(request, response);
        } else {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
            dispatcher.include(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {


        try {

            // odczytanie zadania
            String command = request.getParameter("command");

            if (command == null)
                command = "LIST";

            switch (command) {

                case "LIST":
                    listVacations(request, response);
                    break;

                case "LOAD":
                    loadVacation(request, response);
                    break;

                case "ACCEPT":
                    acceptVacation(request, response);
                    break;

                default:
                    listVacations(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }


    private void acceptVacation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String id = request.getParameter("vacationId");

        // uaktualnienie danych w BD
        dbUtil.acceptVacation(id);

        // wyslanie danych do strony z lista urlopow
        listVacations(request, response);

    }

    private void loadVacation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // odczytanie id urlopu z formularza
        String id = request.getParameter("vacationId");

        // pobranie  danych urlopu z BD
        VacationDB vacation = dbUtil.getVacation(id);

        // przekazanie urlopu do obiektu request
        request.setAttribute("VACATION", vacation);

        // wyslanie danych do formmularza JSP (update_vacation_form)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update_vacation_form.jsp");
        dispatcher.forward(request, response);

    }


    private void listVacations(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<VacationDB> vacationsList = dbUtil.getVacations();

        // dodanie listy do obiektu zadania
        request.setAttribute("VACATIONS_LIST", vacationsList);

        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/manager_view.jsp");

        // przekazanie do JSP
        dispatcher.forward(request, response);

    }


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
