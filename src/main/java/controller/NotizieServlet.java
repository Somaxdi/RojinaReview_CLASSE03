package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.utilities.Articolo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet(name = "NotizieServlet", value = "/NotizieServlet")
public class NotizieServlet extends HttpServlet {
    private String path ="/WEB-INF/results/notizie.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
