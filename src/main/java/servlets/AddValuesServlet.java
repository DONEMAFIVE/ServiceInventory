package servlets;

import db.DBService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

//AddValuesServlet - Сервлет на обновление записи в БД
public class AddValuesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBService dbService = new DBService();
        String resultForResponce;

        String mol;
        String name;
        String inumber;
        String status;
        String date;
        String writeCheck;

        mol = request.getParameter("mol");
        name = request.getParameter("name");
        inumber = request.getParameter("inumber");
        status = request.getParameter("status");
        date = request.getParameter("date");

        writeCheck = request.getParameter("writeCheck");

        if (writeCheck.equals("takeIt")) {
            try {
                dbService.addDataToDB(mol, name, inumber, status, Integer.parseInt(date));
                resultForResponce = dbService.readValues();
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println(resultForResponce);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
