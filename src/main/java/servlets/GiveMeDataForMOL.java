package servlets;

import db.DBService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GiveMeDataForMOL extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        DBService db = new DBService();
        String resultForResponce;
        String mol = request.getParameter("mol");

        try {
            response.setContentType("text/html;charset=utf-8");
            resultForResponce = db.getDataForMOL(mol);
            if (resultForResponce.equals("")) {
                response.getWriter().println("Данные не найдены");
            } else {
                response.getWriter().println(resultForResponce);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
