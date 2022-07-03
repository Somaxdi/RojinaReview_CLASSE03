package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.beans.Giornalista;
import model.dao.RecensioneDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "journalistReviewsServlet", value = "/journalistReviewsServlet")
public class journalistReviewsServlet extends HttpServlet {
    private String path = "/WEB-INF/results/journalistReviews.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Giornalista g = (Giornalista) session.getAttribute("giornalista");
        try {
            request.setAttribute("recensioniGiornalista", new RecensioneDAO().doRetrieveByIdJournalist(g.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher(path).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
