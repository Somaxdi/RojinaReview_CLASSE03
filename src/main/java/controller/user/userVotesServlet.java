package controller.user;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.beans.Utente;
import model.dao.CommentoDAO;
import model.dao.VotoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "userVotesServlet", value = "/userVotesServlet")
public class userVotesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = "/WEB-INF/results/userVotes.jsp";
        HttpSession session = request.getSession();
        Utente u = (Utente) session.getAttribute("utente");
        try {
            request.setAttribute("commenti", new CommentoDAO().getCommentByUser(u.getEmail()));
            request.setAttribute("voti", new VotoDAO().doRetrieveByUser(u.getEmail()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher(result).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
