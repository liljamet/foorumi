/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Lahettaja;

/**
 *
 * @author lilja
 */
public class LahettajaDao implements Dao<Lahettaja, Integer> {

    private FoorumiDatabase db;

    public LahettajaDao(FoorumiDatabase db) {
        this.db = db;
    }

    @Override
    public Lahettaja findOne(Integer key) throws SQLException {
        Connection c = this.db.getConnection();
        PreparedStatement s = c.prepareStatement("SELECT * FROM Lahettaja WHERE lahettaja_id=?");
        s.setObject(1, key);

        ResultSet rs = s.executeQuery();

        if (!rs.next()) {
            return null;
        }

        int id = rs.getInt("lahettaja_id");
        String nimim = rs.getString("nimimerkki");

        Lahettaja uusi = new Lahettaja(id, nimim);
        return uusi;

    }

    @Override
    public List<Lahettaja> findAll() throws SQLException {
        Connection c = this.db.getConnection();
        PreparedStatement s = c.prepareStatement("SELECT * FROM Lahettaja");

        ResultSet rs = s.executeQuery();
        List<Lahettaja> lahettajat = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("lahettaja_id");
            String nimim = rs.getString("nimimerkki");

            Lahettaja uusi = new Lahettaja(id, nimim);
            lahettajat.add(uusi);
        }
        return lahettajat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
