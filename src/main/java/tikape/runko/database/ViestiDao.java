/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Lahettaja;
import tikape.runko.domain.Viesti;

/**
 *
 * @author lilja
 */
public class ViestiDao implements Dao<Viesti, Integer> {

    private FoorumiDatabase database;

    public ViestiDao(FoorumiDatabase db) {
        this.database = db;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection c = this.database.getConnection();
        PreparedStatement s = c.prepareStatement("SELECT * FROM Viesti WHERE viesti_id=?");
        s.setObject(1, key);

        ResultSet rs = s.executeQuery();

        if (!rs.next()) {
            return null;
        }

        int id = rs.getInt("viesti_id");
        Timestamp kellonaika = rs.getTimestamp("kellonaika");
        String viesti = rs.getString("viesti");
        int keskustelu_id = rs.getInt("keskustelu");
        int lahettaja_id = rs.getInt("lahettaja");
        KeskusteluDao kDao = new KeskusteluDao(database);
        LahettajaDao lDao = new LahettajaDao(database);

        Keskustelu keskustelu = kDao.findOne(keskustelu_id);
        Lahettaja lahettaja = lDao.findOne(lahettaja_id);

        Viesti uusi = new Viesti(id, kellonaika, viesti, keskustelu, lahettaja);
        return uusi;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection c = this.database.getConnection();
        PreparedStatement s = c.prepareStatement("SELECT * FROM Viesti");
        ResultSet rs = s.executeQuery();

        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("viesti_id");
            Timestamp kellonaika = rs.getTimestamp("kellonaika");
            String viesti = rs.getString("viesti");
            int keskustelu_id = rs.getInt("keskustelu");
            int lahettaja_id = rs.getInt("lahettaja");
            KeskusteluDao kDao = new KeskusteluDao(database);
            LahettajaDao lDao = new LahettajaDao(database);

            Keskustelu keskustelu = kDao.findOne(keskustelu_id);
            Lahettaja lahettaja = lDao.findOne(lahettaja_id);

            Viesti uusi = new Viesti(id, kellonaika, viesti, keskustelu, lahettaja);
            viestit.add(uusi);
        }
        return viestit;
    }
    
    public Timestamp findLatest(int keskustelu_id) throws SQLException {
        Connection c = this.database.getConnection();
        PreparedStatement s = c.prepareStatement("SELECT MAX(kellonaika) FROM Viesti WHERE keskustelu=?");
        s.setObject(1, keskustelu_id);
        ResultSet rs = s.executeQuery();
        
        List<Timestamp> kellonajat = new ArrayList<>();
        while(rs.next()) {
            Timestamp kellonaika = rs.getTimestamp("kellonaika");
            kellonajat.add(kellonaika);
        }
        
        return kellonajat.get(0);
    }
    
    public int allInKeskustelualue(int keskusteluale_id) throws SQLException {
        Connection c = this.database.getConnection();
        PreparedStatement s = c.prepareStatement("SELECT COUNT(viesti_id) FROM Viesti, Keskustelu WHERE Keskustelu.keskusteluale=? AND Keskustelu.keskustelu_id=Viesti.keskustelu");
        s.setObject(1, keskusteluale_id);
        
        ResultSet rs = s.executeQuery();
        int lukumaara = rs.getInt(1);
        
        return lukumaara;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
