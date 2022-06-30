package model.dao;

import model.beans.Amministratore;
import model.utilities.ConPool;
import model.utilities.GenericStaffDAO;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmministratoreDAO implements GenericStaffDAO {
    private Connection con;


    public AmministratoreDAO() throws SQLException {
        con = ConPool.getConnection();
    }

    public AmministratoreDAO(Connection con) {
        this.con = con;
    }

    public Object doRetriveByEmail(String email) throws SQLException, UnsupportedEncodingException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Amministratore WHERE email=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Amministratore(
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("email"),
                    rs.getString("pass"),
                    rs.getInt("id"));
        }

        return null;
    }

}
