package rojinaReview.model.dao;

import rojinaReview.model.beans.Notizia;
import rojinaReview.model.utilities.ConPool;

import java.sql.*;
import java.util.ArrayList;

public class NotiziaDAO {
    private final Connection con;

    public Connection getCon() {
        return con;
    }

    public NotiziaDAO() throws SQLException {
        con = ConPool.getConnection();
    }

    public NotiziaDAO(Connection con) {
        this.con = con;
    }

    public Notizia doRetrieveById(int id) throws SQLException {
        PreparedStatement ps =
                con.prepareStatement("SELECT * FROM notizia WHERE id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Notizia n = new Notizia();

            n.setId(rs.getInt(1));
            n.setTitolo(rs.getString(2));
            n.setTesto(rs.getString(3));
            n.setImmagine(rs.getString(4));
            n.setDataScrittura(rs.getDate(5));

            return n;
        }

        return null;
    }

    public ArrayList<Notizia> doRetrieveByGameMentioned(int game) throws SQLException {
        ArrayList<Notizia> notizie = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM notizia n JOIN videogioco_notizia vn ON n.id = vn.id_notizia WHERE vn.id_videogioco=?");
        ps.setInt(1, game);
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            Notizia n = new Notizia();

            n.setId(rs.getInt(1));
            n.setTitolo(rs.getString(2));
            n.setTesto(rs.getString(3));
            n.setImmagine(rs.getString(4));
            n.setDataScrittura(rs.getDate(5));

            notizie.add(n);
        }

        return notizie;
    }

    public ArrayList<Notizia> doRetrieveLast() throws SQLException {

        ArrayList<Notizia> notizie = new ArrayList<>();

        PreparedStatement ps =
                con.prepareStatement("" +
                        "SELECT * FROM notizia ORDER BY id DESC LIMIT 13");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Notizia n = new Notizia();

            n.setId(rs.getInt(1));
            n.setTitolo(rs.getString(2));
            n.setTesto(rs.getString(3));
            n.setImmagine(rs.getString(4));
            n.setDataScrittura(rs.getDate(5));

            notizie.add(n);
        }

        return notizie;
    }

    public ArrayList<Notizia> doRetrieveByIdJournalist(int giornalista) throws SQLException {
        ArrayList<Notizia> notizie = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT n.id, n.titolo, n.testo, n.immagine, n.dataScrittura " +
                "FROM notizia n JOIN giornalista g on g.id = n.id_giornalista WHERE n.id_giornalista=? ORDER BY n.dataScrittura DESC");
        ps.setInt(1, giornalista);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Notizia n = new Notizia();

            n.setId(rs.getInt(1));
            n.setTitolo(rs.getString(2));
            n.setTesto(rs.getString(3));
            n.setImmagine(rs.getString(4));
            n.setDataScrittura(rs.getDate(5));

            notizie.add(n);
        }

        return notizie;
    }

    public void doSave(Notizia n, int idGiornalista) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO Notizia (testo, id_giornalista, titolo, dataScrittura, immagine) VALUES" +
                "(?, ?, ?, ?, ?)");

        ps.setString(1, n.getTesto());
        ps.setInt(2, idGiornalista);
        ps.setString(3, n.getTitolo());
        ps.setDate(4, n.getDataScrittura());
        ps.setString(5, n.getImmagine());

        if (ps.executeUpdate() != 1)
            throw new RuntimeException("Insert error");
    }

    //fa l'insert nella table gioco_notizia per i giochi menzionati
    public void doSaveMentioned(ArrayList<String> mentioned, int idGiornalista) throws SQLException {
        int idNotizia;
        PreparedStatement ps = con.prepareStatement("SELECT id FROM Notizia ORDER BY id DESC LIMIT 1"); //prende l'id della notizia appena inserita in insertNewServlet
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            idNotizia = rs.getInt("id");
        else return;

        for (String s : mentioned) {
            ps = con.prepareStatement("INSERT INTO videogioco_notizia (id_notizia, id_videogioco) VALUES (?, ?)");
            ps.setInt(1, idNotizia);
            ps.setString(2, new VideogiocoDAO().retrieveId(s));

            if (ps.executeUpdate() != 1)
                throw new RuntimeException("Insert error");
        }
    }

    public ArrayList<Notizia> updateContent(String offset, String piattaforma, String tipologia, String ordina) throws SQLException {
        ArrayList<Notizia> notizie = new ArrayList<>();
        int limit = 12;
        String select = " SELECT n.id, n.titolo, n.testo, n.immagine ";
        String from = " FROM notizia n";
        from += (!piattaforma.equals("Piattaforma") ?
                " JOIN gioco_notizia gn1 on n.id=gn1.notizia JOIN gioco_piattaforma gp on gn1.gioco=gp.gioco "
                : " ");
        from += (!tipologia.equals("Tipologia") ?
                " JOIN gioco_notizia gn2 on n.id=gn2.notizia JOIN gioco_tipologia gt on gn2.gioco=gt.gioco "
                : " ");
        String where = " WHERE ";
        where += (!piattaforma.equals("Piattaforma") ?
                " gp.piattaforma='" + piattaforma + "'" :
                "");
        where += (!tipologia.equals("Tipologia") ?
                (where.equals(" WHERE ") ?
                        " gt.tipologia='" + tipologia + "'" :
                        " AND gt.tipologia='" + tipologia + "'")
                : "");
        if (where.equals(" WHERE ")) where = " ";
        String order = " ORDER BY n.dataCaricamento " +
                (ordina.equals("Least Recent") ? " ASC " : " DESC ") +
                " LIMIT " + limit + " OFFSET " + offset;

        PreparedStatement ps =
                con.prepareStatement(select + from + where + order);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Notizia n = new Notizia();
            n.setId(rs.getInt(1));
            n.setTitolo(rs.getString(2));
            n.setTesto(rs.getString(3));
            n.setImmagine(rs.getString(4));
            notizie.add(n);
        }

        return notizie;
    }

    public ArrayList<Notizia> doRetriveAll() throws SQLException {
        ArrayList<Notizia> list = new ArrayList<>();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Notizia");

        while (rs.next()) {
            Notizia n = new Notizia();

            n.setId(rs.getInt(1));
            n.setTitolo(rs.getString(2));
            n.setTesto(rs.getString(3));
            n.setImmagine(rs.getString(4));
            n.setDataScrittura(rs.getDate(5));

            list.add(n);
        }
        return list;
    }

    //non serve?
    public boolean doRemoveById(int Id) throws SQLException {
        PreparedStatement ps =
                con.prepareStatement("DELETE FROM Notizia WHERE id=?");
        ps.setInt(1,Id);
        int i = ps.executeUpdate();
        return i == 1;
    }
}