/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.service.DisplayableKeskustelu;

/**
 *
 * @author lilja
 */
public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private FoorumiDatabase database;

    public KeskusteluDao(FoorumiDatabase database) {
        this.database = database;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection c = this.database.getConnection();
        PreparedStatement s = c.prepareStatement("SELECT * FROM Keskustelu WHERE keskustelu_id=?");
        s.setObject(1, key);

        ResultSet rs = s.executeQuery();
        if (!rs.next()) {
            return null;
        }

        int id = rs.getInt("keskustelu_id");
        String otsikko = rs.getString("otsikko");
        int keskustelualue_id = rs.getInt("keskustelualue");
        KeskustelualueDao dao = new KeskustelualueDao(database);
        Keskustelualue alue = dao.findOne(keskustelualue_id);

        Keskustelu uusi = new Keskustelu(id, otsikko, alue);

        rs.close();
        s.close();
        c.close();

        return uusi;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        Connection c = this.database.getConnection();
        PreparedStatement s = c.prepareStatement("SELECT * FROM Keskustelu");
        ResultSet rs = s.executeQuery();

        List<Keskustelu> keskustelut = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("keskustelu_id");
            String otsikko = rs.getString("otsikko");
            int keskustelualue_id = rs.getInt("keskustelualue");
            KeskustelualueDao dao = new KeskustelualueDao(database);
            Keskustelualue alue = dao.findOne(keskustelualue_id);

            Keskustelu uusi = new Keskustelu(id, otsikko, alue);
            keskustelut.add(uusi);
        }
        rs.close();
        s.close();
        c.close();

        return keskustelut;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    public List<DisplayableKeskustelu> findAllKeskusteluWithInfo(int keskusteluAlueId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT"
                + " Keskustelu.keskustelu_id AS id, Keskustelu.otsikko AS nimi, COUNT(Viesti.viesti_id) AS lukumaara,"
                + " (CASE WHEN MAX(Viesti.kellonaika) IS NULL"
                + " THEN 0"
                + " ELSE MAX(Viesti.kellonaika)"
                + " END) as viimeisin"
                + " FROM"
                + " Keskustelu"
                + " LEFT JOIN Viesti ON Keskustelu.keskustelu_id=Viesti.keskustelu"
                + " WHERE"
                + " Keskustelu.keskustelualue = ?"
                + " GROUP BY Keskustelu.keskustelu_id");

        stmt.setObject(1, keskusteluAlueId);
        ResultSet rs = stmt.executeQuery();
        List<DisplayableKeskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            Integer lukumaara = rs.getInt("lukumaara");
            Timestamp kellonaika = rs.getTimestamp("viimeisin");
            DisplayableKeskustelu keskustelu = new DisplayableKeskustelu();
            keskustelu.setId(id);
            keskustelu.setNimi(nimi);
            keskustelu.setViestienLukumaara(lukumaara);
            keskustelu.setViimeisinViesti(kellonaika);
            keskustelut.add(keskustelu);
        }
        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    @Override
    public void createNew(Keskustelu newObject) throws SQLException {
        if (newObject.getKeskustelu_id() != 0) {
            throw new RuntimeException("Tried to create new object with a user defined id");
        }

        Connection c = this.database.getConnection();
        PreparedStatement s = c.prepareStatement("INSERT INTO Keskustelu (otsikko, keskustelualue) VALUES (?,?)");
        s.setObject(1, newObject.getOtsikko());
        s.setObject(2, newObject.getKeskustelualue().getAlue_id());
        s.execute();

        s.close();
        c.close();
    }
}
