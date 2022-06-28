package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.utilities.Articolo;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet(name = "HomeServlet", value = "/HomeServlet")
public class HomeServlet extends HttpServlet {

    private String path ="/WEB-INF/results/home.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servCon = request.getServletContext();
        ArrayList<Articolo> articoli = new ArrayList<>();
        articoli.addAll((ArrayList<Articolo>) servCon.getAttribute("notizie"));
        articoli.addAll((ArrayList<Articolo>) servCon.getAttribute("recensioni"));
        articoli.sort(Comparator.comparing(a -> a.getDataCaricamento()));
        Articolo copertina = articoli.remove(0);

        request.setAttribute("copertina",copertina);
        request.setAttribute("articoli",articoli);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
