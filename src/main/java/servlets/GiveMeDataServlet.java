package servlets;

import db.DBService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//GiveMeDataServlet - Сервлет на получение всех данных
public class GiveMeDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String resultForResponce;

        DBService db = new DBService();
        try {
            resultForResponce = db.readValues();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(resultForResponce);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
