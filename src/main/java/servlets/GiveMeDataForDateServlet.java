package servlets;

import db.DBService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//GiveMeDataForDateServlet - Сервлет на получение данных по дате
public class GiveMeDataForDateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        DBService db = new DBService();
        String resultForResponce;
        String date = request.getParameter("date");

        try {
            response.setContentType("text/html;charset=utf-8");
            resultForResponce = db.getDataForDate(Integer.parseInt(date));
            if (resultForResponce.equals("")) {
                response.getWriter().println("Данные за этот год не найдены");
            } else {
                response.getWriter().println(resultForResponce);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
