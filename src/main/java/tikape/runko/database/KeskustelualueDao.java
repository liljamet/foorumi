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
public class KeskustelualueDao implements ExtendedDao<Keskustelualue, Integer> {

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

    @Override
    public List<DisplayableKeskustelu> findAllKeskusteluWithInfo() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT"
                + " Keskustelualue.alue_id as id, Keskustelualue.nimi as nimi, COUNT(1) as count, MAX(Viesti.kellonaika) as kellonaika"
                + " FROM"
                + " Keskustelualue, Keskustelu, Viesti"
                + " WHERE"
                + " Keskustelualue.alue_id = Keskustelu.keskustelualue and Keskustelu.keskustelu_id = Viesti.keskustelu"
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

}
