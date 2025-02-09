package rojinaReview.shop.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import rojinaReview.model.beans.Prodotto;
import rojinaReview.model.exception.ProductIDMissingException;
import rojinaReview.utilities.Utils;
import rojinaReview.shop.service.ShopService;
import rojinaReview.shop.service.ShopServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "modificaProdottoServlet", value = "/modificaProdottoServlet")
@MultipartConfig(maxFileSize = 1024*1024*10)
public class modificaProdottoServlet extends HttpServlet {
    private ShopService ssi;



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ssi = new ShopServiceImpl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Prodotto prodotto = new Prodotto();

        prodotto.setNome(request.getParameter("nome"));
        prodotto.setTesto(request.getParameter("descrizione"));
        prodotto.setCategoria(request.getParameter("productType"));
        prodotto.setPrezzo(Float.parseFloat(request.getParameter("prezzo")));
        prodotto.setQuantità(Integer.parseInt(request.getParameter("quantita")));
        prodotto.setId(Integer.parseInt(request.getParameter("id")));
        if(request.getPart("foto").getSize() <= 0){
            prodotto.setImmagine(null);
        }
        else{
            Part filePart = request.getPart("foto");
            String imageType = "products";
            String fileName = prodotto.getNome() + ".jpg";
            prodotto.setImmagine(Utils.saveImageWar(imageType, fileName, filePart));
            Utils.saveImageFileSystem(imageType, fileName, filePart);
        }

        try {
            ssi.modificaProdotto(prodotto);
        } catch (ProductIDMissingException e) {
            throw new RuntimeException(e);
        }
        String result = "/Rojina_Review_war/visualizzaShop";
        response.sendRedirect(result);
    }
}
