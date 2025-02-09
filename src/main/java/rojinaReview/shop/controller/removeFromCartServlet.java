package rojinaReview.shop.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import rojinaReview.model.beans.Carrello;
import rojinaReview.model.beans.Prodotto;
import rojinaReview.model.beans.Videogiocatore;
import rojinaReview.shop.service.ShopService;
import rojinaReview.shop.service.ShopServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class removeFromCartServlet extends HttpServlet {
    private ShopService ss;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ss = new ShopServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String resp="-1";
        int id=-1;
        /*
            Controllo che il parametro id e' stato passato
            -Se e' stato passato procedo con le operazioni per la rimozione
            -Altrimenti dovrei far visualizzare un errore -> lanciare ProductIDMissingException?
         */
        if(request.getParameter("id") != null) {

            /*
                -Verifico se il carello appertiene ad un utente loggato o ad un ospite
                -L'attributo ospite della sessione corrisponde ad un entita Carello
             */
            Carrello cart;
            Videogiocatore u = (Videogiocatore) request.getSession().getAttribute("videogiocatore");
            if (u != null)
                cart = u.getCarrello();
            else
                cart = (Carrello) request.getSession().getAttribute("ospite");

            /*
                Controllo se il prodotto è presente nel carello
                -Se presente procedo con l'operazioni per la rimozione
                 -Altrimenti dovrei far visualizzare un errore -> lanciare ProductIDMissingException?
                Da documentazione questa operazione dovrebbe svolgerla shopService.rimuoviProdottoDalCarrello(Prodotto prodotto)
             */
            id = Integer.parseInt(request.getParameter("id"));
            Prodotto prodottoDaRimuovere = new Prodotto();
            prodottoDaRimuovere.setId(id);
            ss.rimuoviProdottoDalCarrello(prodottoDaRimuovere, cart);



            resp = String.valueOf(cart.getTotale());

        }
        response.getWriter().print(resp);
    }
}
