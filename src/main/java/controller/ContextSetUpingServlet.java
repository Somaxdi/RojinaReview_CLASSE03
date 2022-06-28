package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.beans.*;
import model.dao.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ContextSetUpingServlet", value = "/ContextSetUpingServlet")
public class ContextSetUpingServlet extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {

        ArrayList<Notizia> notizie;
        NotiziaDAO nDAO;
        try {
            nDAO = new NotiziaDAO();
            notizie = nDAO.doRetrieveLast();
        } catch (SQLException e) {
            System.out.println("News Error");
            throw new RuntimeException(e);
        }
        config.getServletContext().setAttribute("notizie",notizie);

        ArrayList<Recensione> recensioni;
        RecensioneDAO rDAO;
        try {
            rDAO = new RecensioneDAO();
            recensioni = rDAO.doRetrieveLast();
        } catch (SQLException e) {
            System.out.println("Reviews ERROR");
            throw new RuntimeException(e);
        }
        config.getServletContext().setAttribute("recensioni",recensioni);

        ArrayList<Tipologia> tipologie;
        TipologiaDAO tDAO;
        try {
            tDAO = new TipologiaDAO();
            tipologie = tDAO.doRetrieveAll();
        } catch (SQLException e) {
            System.out.println("Tipologie ERROR");
            throw new RuntimeException(e);
        }
        config.getServletContext().setAttribute("tipologie",tipologie);

        ArrayList<Categoria> categorie;
        CategoriaDAO cDAO;
        try {
            cDAO = new CategoriaDAO();
            categorie = cDAO.doRetrieveAll();
        } catch (SQLException e) {
            System.out.println("Categorie ERROR");
            throw new RuntimeException(e);
        }
        config.getServletContext().setAttribute("categorie",categorie);

        ArrayList<Piattaforma> piattaforme;
        PiattaformaDAO pDAO;
        try {
            pDAO = new PiattaformaDAO();
            piattaforme = pDAO.doRetrieveAll();
        } catch (SQLException e) {
            System.out.println("Piattaforma ERROR");
            throw new RuntimeException(e);
        }
        config.getServletContext().setAttribute("piattaforme",piattaforme);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
