package rojinaReview.rivista.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import rojinaReview.model.beans.Carrello;
import rojinaReview.model.beans.Articolo;
import rojinaReview.model.exception.LoadingArticlesException;
import rojinaReview.model.exception.ServiceNotAvailableException;
import rojinaReview.rivista.service.RivistaService;
import rojinaReview.rivista.service.RivistaServiceImpl;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "HomeServlet", value = "/HomeServlet")
public class HomeServlet extends HttpServlet {
    private RivistaService rs;
    private String path;

    public HomeServlet() {
        path = "/WEB-INF/results/mainPage/home.jsp";
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            rs = new RivistaServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //crea la sessione se non esistente
        ServletContext servCon = request.getServletContext();
        HttpSession session = request.getSession();
           /*
        se non c'e un utente loggato ed accedo per la prima volta alla home
        setto il carrello per l'ospite nella sessione
        */
        if(session.getAttribute("ospite") == null && session.getAttribute("videogiocatore") == null)
            session.setAttribute("ospite", new Carrello());
        /*
        if(session.getAttribute("prodottiSession") == null)
            session.setAttribute("prodottiSession", new ArrayList<Prodotto>());
        */

        ArrayList<Articolo> articoli = null;
        try {
            articoli = rs.visualizzaArticoli();
        } catch (LoadingArticlesException e) {
            e.printStackTrace();
        }


        //estraggo l'articolo piu recente per la prima pagina
        Articolo copertina = articoli.remove(0);

        //setto gli attributi nella request
        request.setAttribute("copertina", copertina);
        request.setAttribute("articoli", articoli);
        //dispatch verso home.jsp
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
