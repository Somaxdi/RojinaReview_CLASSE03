package rojinaReview.rivista.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import rojinaReview.model.beans.Notizia;

import org.json.JSONArray;
import rojinaReview.model.exception.LoadingNewsException;
import rojinaReview.rivista.service.RivistaServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "NotizieServlet", value = "/NotizieServlet")
public class NotizieServlet extends HttpServlet {
    private RivistaServiceImpl rs;
    private String path;

    public NotizieServlet() {
        path = "/WEB-INF/results/mainPage/notizie.jsp";
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            rs = new RivistaServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Notizia> notizie  = null;
        try {
            notizie = rs.visualizzaNotizie();
        } catch (LoadingNewsException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("notizie",notizie);
        request.setAttribute("articoli", "news");
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            rs = new RivistaServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Notizia> notizie  = null;
        String offset = request.getParameter("offset");
        String piattafomra = request.getParameter("piattaforma");
        String tipologia = request.getParameter("tipologia");
        String ordine = request.getParameter("ordine");

        try {
            notizie = rs.visualizzaNotizie(piattafomra, tipologia, ordine);
        } catch (LoadingNewsException e) {
            e.printStackTrace();
        }

        if (notizie != null) {
            JSONArray json = new JSONArray(notizie);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        }
        response.getWriter().flush(); //Calling flush() on the PrintWriter commits the response.
    }
}
