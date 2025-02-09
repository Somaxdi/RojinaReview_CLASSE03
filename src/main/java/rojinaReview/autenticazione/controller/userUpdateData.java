package rojinaReview.autenticazione.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import rojinaReview.autenticazione.service.AutenticazioneService;
import rojinaReview.autenticazione.service.AutenticazioneServiceImpl;
import rojinaReview.model.beans.Videogiocatore;
import rojinaReview.model.exception.InvalidTextException;
import rojinaReview.model.exception.UpdateDataException;
import rojinaReview.utilities.Utils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "userUpdateData", value = "/userUpdateData")
public class userUpdateData extends HttpServlet {
    private AutenticazioneService as;
    private String path = "/WEB-INF/results/videogiocatorePages/utenteModificaDati.jsp";
    private  String homePage = "./home";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            as = new AutenticazioneServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();

        if (session.getAttribute("videogiocatore") == null)
            response.sendRedirect(homePage);
        else
        {
            Videogiocatore videogiocatore = (Videogiocatore) session.getAttribute("videogiocatore");

            videogiocatore.setEmail(request.getParameter("email"));
            try {
                Utils.textCheckPassword(request.getParameter("password"));
                videogiocatore.setPassword(Utils.calcolaHash(request.getParameter("password")));
            } catch (InvalidTextException e) {
                e.printStackTrace(); //da aggiungere pagina errore "nuova password non valida"
            }
            videogiocatore.setNickname(request.getParameter("nickname"));
            videogiocatore.setNome(request.getParameter("nome"));
            videogiocatore.setCognome(request.getParameter("cognome"));

            try {
                as.modificaVideogiocatore(videogiocatore);
            } catch (UpdateDataException e) {
                e.printStackTrace();
            }


            request.getRequestDispatcher(path).forward(request, response);



        }
    }
}

