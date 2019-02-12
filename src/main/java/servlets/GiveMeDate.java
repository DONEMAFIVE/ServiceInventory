package servlets;

import db.DBService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//GiveMeDate - Сервлет на получение всех имеющихся дат в БД
public class GiveMeDate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String resultForResponce;

        DBService db = new DBService();
        try {
            resultForResponce = db.getDate();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(resultForResponce);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
