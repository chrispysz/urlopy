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
 * Klasa AdminServlet odpowiada za połączenie widoków Admina z pozostałymi klasami obsługująćymi bazę danych
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    private DBUtilAdmin dbUtil;
    private final String db_url = "jdbc:postgresql://localhost:5432/urlopy?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {

            dbUtil = new DBUtilAdmin(db_url);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Funkcja odpowiadająca za obsługę zapytań Post
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        response.setContentType("text/html");

        String name = request.getParameter("loginInput");
        String password = request.getParameter("passwordInput");

        dbUtil.setName(name);
        dbUtil.setPassword(password);

        if (validate(name, password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_view.jsp");

            List<VacationDB> editedVacationList = null;
            List<VacationDB> removedVacationList = null;

            try {

                editedVacationList = dbUtil.getEditedVacations();
                removedVacationList = dbUtil.getRemovedVacations();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // dodanie listy do obiektu zadania
            request.setAttribute("EDITED_VACATIONS_LIST", editedVacationList);
            request.setAttribute("REMOVED_VACATIONS_LIST", removedVacationList);

            dispatcher.forward(request, response);
        } else {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
            dispatcher.include(request, response);
        }


    }

    /**
     * funkcja odpowiadająca za obsługę zapytań Get
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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

                case "ADD":
                    addVacations(request, response);
                    break;

                case "LOAD":
                    loadVacations(request, response);
                    break;

                case "UPDATE":
                    updateVacation(request, response);
                    break;

                case "ACCEPTDELETE":
                    deleteVacation(request, response);
                    break;

                case "ACCEPTEDIT":
                    acceptVacation(request, response);
                    break;

                default:
                    listVacations(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    /**
     * Funkcja obsługująca akceptację edycji urlopów
     * @param request
     * @param response
     * @throws Exception
     */
    private void acceptVacation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // odczytanie danych z formularza
        String id = request.getParameter("vacationId");

        VacationDB vacation = dbUtil.getEditedVacation(id);

        // wstawienie urlopu do tabeli removed_vacations
        dbUtil.insertVacation(vacation);
        dbUtil.deleteEditedVacation(id);

        // wyslanie danych do strony z lista urlopów
        listVacations(request, response);

    }

    /**
     * funkcja odpowiadająca za zatwierdzenie usunięcia urlopów
     * @param request
     * @param response
     * @throws Exception
     */
    private void deleteVacation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // odczytanie danych z formularza
        String id = request.getParameter("vacationId");

        VacationDB vacation = dbUtil.getRemovedVacation(id);

        // wstawienie urlopu do tabeli removed_vacations
        dbUtil.deleteVacation(id);

        // wyslanie danych do strony z lista urlopów
        listVacations(request, response);

    }

    /**
     * funkcja odpowiadająca za aktualizowanie urlopu
     * @param request
     * @param response
     * @throws Exception
     */
    private void updateVacation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // odczytanie danych z formularza
        int vacationId = Integer.parseInt(request.getParameter("vacationId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        Date startDate = Date.valueOf(request.getParameter("startDateInput"));
        Date endDate = Date.valueOf(request.getParameter("endDateInput"));
        boolean accepted = Boolean.parseBoolean(request.getParameter("acceptedInput"));

        // utworzenie nowego urlopu
        VacationDB vacation=new VacationDB(vacationId, userId, startDate, endDate, accepted);

        // uaktualnienie danych w BD
        dbUtil.updateVacation(vacation);

        // wyslanie danych do strony z lista urlopow
        listVacations(request, response);

    }
    /**
     * funkcja obsługująca pobranie urlopu o podanym ID
     * @param request
     * @param response
     * @throws Exception
     */
    private void loadVacations(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

    /**
     * funkcja odpowiadająca za dodawanie urlopów do BD
     * @param request
     * @param response
     * @throws Exception
     */
    private void addVacations(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // odczytanie danych z formularza
        int userId = Integer.parseInt(request.getParameter("userId"));
        Date startDate = Date.valueOf(request.getParameter("startDateInput"));
        Date endDate = Date.valueOf(request.getParameter("endDateInput"));
        boolean accepted = Boolean.parseBoolean(request.getParameter("acceptedInput"));

        // utworzenie obiektu klasy Vacation
        VacationDB vacation=new VacationDB(userId, startDate, endDate, accepted);

        // dodanie nowego obiektu do BD
        dbUtil.addVacation(vacation);

        // powrot do listy
        listVacations(request, response);

    }

    /**
     * funkcja odpowiadająca za wypisanie listy urlopów
     * @param request
     * @param response
     * @throws Exception
     */
    private void listVacations(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<VacationDB> vacationsEditedList = dbUtil.getEditedVacations();
        List<VacationDB> vacationsRemovedList = dbUtil.getRemovedVacations();

        // dodanie listy do obiektu zadania
        request.setAttribute("EDITED_VACATIONS_LIST", vacationsEditedList);
        request.setAttribute("REMOVED_VACATIONS_LIST", vacationsRemovedList);

        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_view.jsp");

        // przekazanie do JSP
        dispatcher.forward(request, response);

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
