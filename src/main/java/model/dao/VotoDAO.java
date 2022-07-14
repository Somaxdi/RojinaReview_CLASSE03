package model.dao;

import model.beans.VotoGioco;
import model.beans.VotoProdotto;
import model.utilities.ConPool;
import model.utilities.Voto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class VotoDAO {
    private Connection con;

    public VotoDAO() throws SQLException {
        con = ConPool.getConnection();
    }

    public VotoDAO(Connection con){
        this.con = con;
    }

    public ArrayList<Voto> doRetrieveByUser(String utente) throws SQLException {
        ArrayList<Voto> voti = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM voto WHERE utente=?");
        ps.setString(1, utente);
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            VotoGioco voto = new VotoGioco();
            voto.setGioco(rs.getString(1));
            voto.setUtente(utente);
            voto.setVoto(rs.getFloat(3));
            voto.setDataVotazione(rs.getDate(4));

            voti.add(voto);
        }

        ps = con.prepareStatement("SELECT * FROM gradimento WHERE utente=?");
        ps.setString(1, utente);
        rs = ps.executeQuery();

        while (rs.next())
        {
            VotoProdotto voto = new VotoProdotto();
            voto.setId(rs.getInt(1));
            voto.setUtente(utente);
            voto.setVoto(rs.getFloat(3));
            voto.setDataVotazione(rs.getDate(4));

            voti.add(voto);
        }

        voti.sort(Comparator.comparing(v -> v.getDataVotazione()));

        return voti;
    }

    public void doSave(Voto v, String table) throws SQLException {
        if(table.equalsIgnoreCase("gioco"))
        {
            VotoGioco votogioco = (VotoGioco) v;
            PreparedStatement ps = con.prepareStatement("INSERT INTO voto VALUES(?, ?, ?, ?) ");
            ps.setString(1, votogioco.getGioco());
            ps.setString(2, votogioco.getUtente());
            ps.setFloat(3, votogioco.getVoto());
            ps.setDate(4, votogioco.getDataVotazione());

            if(ps.executeUpdate() != 1)
                throw new RuntimeException("Insert error");
        }
        else if(table.equalsIgnoreCase("prodotto"))
        {
            VotoProdotto votoprodotto = (VotoProdotto) v;
            PreparedStatement ps = con.prepareStatement("INSERT INTO gradimento VALUES (?, ?, ?, ?)");
            ps.setInt(1, votoprodotto.getId());
            ps.setString(2, votoprodotto.getUtente());
            ps.setFloat(3, votoprodotto.getVoto());
            ps.setDate(4, votoprodotto.getDataVotazione());

            if(ps.executeUpdate() != 1)
                throw new RuntimeException("Insert error");
        }
    }
}
