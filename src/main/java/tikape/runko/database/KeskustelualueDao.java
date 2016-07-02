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
import tikape.runko.domain.Keskustelualue;
import tikape.runko.service.DisplayableKeskustelu;

/**
 *
 * @author lilja
 */
public class KeskustelualueDao implements Dao<Keskustelualue, Integer> {

    private FoorumiDatabase database;

    public KeskustelualueDao(FoorumiDatabase database) {
        this.database = database;
    }

    @Override
    public Keskustelualue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue WHERE alue_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("alue_id");
        String nimi = rs.getString("nimi");

        Keskustelualue uusiAlue = new Keskustelualue(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return uusiAlue;
    }

    @Override
    public List<Keskustelualue> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelualue");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelualue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            alueet.add(new Keskustelualue(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    public List<DisplayableKeskustelu> findAllKeskusteluWithInfo() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT"
                + " Keskustelualue.alue_id as id, Keskustelualue.nimi as nimi, COUNT(Viesti.viesti_id) as count,"
                        + " (CASE WHEN MAX(Viesti.kellonaika) IS NULL"
                        + " THEN 0"
                        + " ELSE MAX(Viesti.kellonaika)"
                        + " END) as kellonaika"
                + " FROM"
                + " Keskustelualue"
                        + " LEFT JOIN Keskustelu ON Keskustelualue.alue_id = Keskustelu.keskustelualue"
                        + " LEFT JOIN Viesti ON Keskustelu.keskustelu_id = Viesti.keskustelu"
                + " GROUP BY Keskustelualue.alue_id");

        ResultSet rs = stmt.executeQuery();
        List<DisplayableKeskustelu> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            Integer lukumaara = rs.getInt("count");
            Timestamp kellonaika = rs.getTimestamp("kellonaika");
            DisplayableKeskustelu alue = new DisplayableKeskustelu();
            alue.setId(id);
            alue.setNimi(nimi);
            alue.setViestienLukumaara(lukumaara);
            alue.setViimeisinViesti(kellonaika);
            alueet.add(alue);
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement s = c.prepareStatement("DELETE FROM Keskustelualue WHERE alue_id = ?");
        s.setObject(1, key);

        ResultSet rs = s.executeQuery();
        rs.close();
        s.close();
        c.close();
    }

    @Override
    public void createNew(Keskustelualue newObject) throws SQLException {
        if(newObject.getAlue_id()!= 0){
            throw new RuntimeException("Tried to create new object with a user defined id");
        }
        
        Connection c = this.database.getConnection();
        PreparedStatement s = c.prepareStatement("INSERT INTO Keskustelualue (nimi) VALUES (?)");
        s.setObject(1, newObject.getNimi());
        s.execute();
        
        s.close();
        c.close();
    }

}
