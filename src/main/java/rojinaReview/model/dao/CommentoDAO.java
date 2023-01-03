package rojinaReview.model.dao;

import rojinaReview.model.beans.Commento;
import rojinaReview.model.utilities.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class CommentoDAO {
    private Connection con;

    public CommentoDAO() throws SQLException {
        con = ConPool.getConnection();
    }

    public CommentoDAO(Connection con) {
        this.con = con;
    }

    /*table: Prodotto-Recensione-Notizia*/
    public ArrayList<Commento> getCommentById(int id, int tipo) throws SQLException {
        String stringType = null;
        if(tipo == 0)
            stringType = "id_prodotto";
        else if(tipo == 1)
            stringType = "id_recensione";
        else if(tipo == 2)
            stringType = "id_notizia"
        String query = "SELECT id, testo, dataScrittura, id_videogiocatore FROM commento WHERE " + stringType + "=? " + "ORDER BY dataScrittura DESC";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        ArrayList<Commento> commenti = new ArrayList();
        while (rs.next()) {
            commenti.add(
                    new Commento(   rs.getInt(1),
                                    rs.getString(2),
                                    rs.getTimestamp(3),
                                    new VideogiocatoreDAO(con).retrieveNickname(rs.getInt(4)));
            }



        return commenti;

    }

    public ArrayList<Commento> getCommentsByUser(int user) throws SQLException {
        ArrayList<Commento> commenti = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT id, testo, dataScrittura, id_prodotto, id_recensione, id_notizia FROM commento WHERE id_videogiocatore=?");
        ps.setInt(1, user);
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            Commento c = new Commento();
            c.setId(rs.getInt(1));
            c.setTesto(rs.getString(2));
            c.setData(rs.getTimestamp(3));
            int idContenuto;
            String contenuto = null;
            if(rs.getInt("id_prodotto") != null)
            {
                idContenuto = rs.getInt("id_prodotto");
                contenuto = new ProdottoDAO(con).retrieveNome(rs.getInt("id_prodotto"));
            }

            else if (rs.getInt("id_recensione") != null)
            {
                idContenuto = rs.getInt("id_recensione");
                contenuto = new RecensioneDAO(con).retrieveNome(rs.getInt("id_recensione"));
            }

            else if (rs.getInt("id_notizia") != null)
            {
                idContenuto = rs.getInt("id_notizia");
                contenuto = new NotiziaDAO(con).retrieveNome(rs.getInt("id_notizia"));
            }

            c.setIdContenuto(idContenuto);
            c.setContenuto(contenuto);
            commenti.add(c);
        }


        commenti.sort(Comparator.comparing(c -> c.getData()));

        return commenti;

    }

    public void doSave(Commento c, int tipo) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO commento VALUES (?, ?, ?, ?, ?, ?)");
        ps.setString(1, c.getTesto());
        ps.setTimestamp(2, c.getData());
        ps.setInt(3, c.getIdVideogiocatore());
        //se non setto gli altri parametri viene direttamente messo null o lanciata eccezione?
        if(tipo == 0)
            ps.setInt(4, c.getIdContenuto());
        if(tipo == 1)
            ps.setInt(5, c.getIdContenuto());
        if(tipo == 2)
            ps.setInt(6, c.getIdContenuto());

        if(ps.executeUpdate() != 1)
            throw new RuntimeException("Insert error");
    }

}
