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
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelualue;

/**
 *
 * @author lilja
 */
public class KeskustelualueDao implements Dao<Keskustelualue, Integer> {
    private Database database;
    
    public KeskustelualueDao(Database database) {
        this.database=database;
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

    @Override
    public void delete(Integer key) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement s=c.prepareStatement("DELETE FROM Keskustelualue WHERE alue_id = ?");
        s.setObject(1, key);
        
        ResultSet rs=s.executeQuery();
        rs.close();
        s.close();
        c.close();
    }

}
