package rojinaReview.autenticazione.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import rojinaReview.model.exception.LoadingCommentException;
import rojinaReview.opinione.service.OpinioneServiceImpl;
import rojinaReview.shop.service.ShopServiceImpl;

import java.io.IOException;

@WebServlet(name = "visualizzaCommentiSegnalazioniServlet", value = "/visualizzaCommentiSegnalazioniServlet")
public class visualizzaCommentiSegnalatiServlet extends HttpServlet {

    private String path = "/WEB-INF/results/managerPages/managerSegnalazioni.jsp";
    private OpinioneServiceImpl osi = new OpinioneServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("segnalazioni", osi.visualizzaCommentiSegnalati());
        } catch (LoadingCommentException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher(path).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
