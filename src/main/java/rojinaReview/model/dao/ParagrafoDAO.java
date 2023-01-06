package rojinaReview.model.dao;

import rojinaReview.model.beans.Paragrafo;
import rojinaReview.model.utilities.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParagrafoDAO {
    private final Connection con;

    public ParagrafoDAO() throws SQLException {
        con = ConPool.getConnection();
    }

    public ParagrafoDAO(Connection con) {
        this.con = con;
    }

    public ArrayList<Paragrafo> doRetrieveAllByArticle(int article, boolean type) throws SQLException {
        ArrayList<Paragrafo> paragrafi = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;

        if (type) //recensione
        {
            ps = con.prepareStatement("SELECT id_paragrafo, titolo, testo, immagine FROM paragrafo WHERE id_recensione=?");
            ps.setInt(1, article);
            rs = ps.executeQuery();

            while (rs.next()) {
                Paragrafo p = new Paragrafo();

                p.setId(rs.getInt(1));
                p.setTitolo(rs.getString(2));
                p.setTesto(rs.getString(3));
                p.setImmagine(rs.getString(4));

                paragrafi.add(p);
            }
        } else if (!type) //notizia
        {
            ps = con.prepareStatement("SELECT id_paragrafo, titolo, testo, immagine FROM paragrafo WHERE id_notizia=?");
            ps.setInt(1, article);
            rs = ps.executeQuery();

            while (rs.next()) {
                Paragrafo p = new Paragrafo();

                p.setId(rs.getInt(1));
                p.setTitolo(rs.getString(2));
                p.setTesto(rs.getString(3));
                p.setImmagine(rs.getString(4));

                paragrafi.add(p);
            }
        }

        return paragrafi;
    }

    public void doSave(Paragrafo p, int article, boolean type) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        if(type) //recensione
        {
            ps = con.prepareStatement("INSERT INTO paragrafo (titolo, testo, immagine, id_recensione) VALUES (?, ?, ?, ?)");
            ps.setString(1, p.getTitolo());
            ps.setString(2, p.getTesto());
            ps.setString(3, p.getImmagine());
            ps.setInt(4, article);

            ps.executeUpdate();

        }
        else if(!type) //notizia
        {
            ps = con.prepareStatement("SELECT id FROM recensione ORDER BY id DESC LIMIT 1");
            rs = ps.executeQuery();
            article = rs.getInt(1);

            ps = con.prepareStatement("INSERT INTO paragrafo (titolo, testo, immagine, id_notizia) VALUES (?, ?, ?, ?)");
            ps.setString(1, p.getTitolo());
            ps.setString(2, p.getTesto());
            ps.setString(3, p.getImmagine());
            ps.setInt(4, article);

            ps.executeUpdate();

        }
    }

    public void doRemoveAll(int article, int type) throws SQLException //1 recensione, 2 notizia
    {
        PreparedStatement ps;
        if (type == 1) //recensione
        {
            ps = con.prepareStatement("DELETE FROM paragrafo WHERE id_recensione=?");
            ps.setInt(1, article);
            ps.executeUpdate();
        }
        else if (type == 2)
        {
            ps = con.prepareStatement("DELETE FREE paragrafo WHERE id_notizia?=");
            ps.setInt(1, article);
            ps.executeUpdate();
        }
    }
}


