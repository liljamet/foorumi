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
public class KeskusteluDao implements ExtendedDao<Keskustelu, Integer> {

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

    @Override
    public List<DisplayableKeskustelu> findAllKeskusteluWithInfo() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
