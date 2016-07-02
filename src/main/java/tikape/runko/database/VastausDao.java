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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static tikape.runko.database.DaoUtil.createNewObject;
import tikape.runko.domain.Vastaus;
import tikape.runko.domain.Viesti;

/**
 *
 * @author lilja
 */
public class VastausDao implements Dao<Vastaus, Integer> {

    private FoorumiDatabase db;

    public VastausDao(FoorumiDatabase db) {
        this.db = db;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
//        Connection c = this.db.getConnection();
//        PreparedStatement s = c.prepareStatement("SELECT * FROM Vastaus WHERE vastaus_id = ?");
        return null;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        Connection c = this.db.getConnection();
        PreparedStatement s = c.prepareStatement("SELECT * FROM Vastaus");
        ResultSet rs = s.executeQuery();

        List<Vastaus> vastaukset = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("vastasus_id");
            int vastattava_id = rs.getInt("viesti_vastattava");
            int vastaus_id = rs.getInt("viesti_vastaus");

            ViestiDao dao = new ViestiDao(this.db);
            Viesti vastattava = dao.findOne(vastattava_id);
            Viesti vastaus = dao.findOne(vastaus_id);
            Vastaus uusi = new Vastaus(id, vastattava, vastaus);
            vastaukset.add(uusi);
        }
        return vastaukset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vastaus createNew(Vastaus newObject) throws SQLException {
        if (newObject.getVastaus_id() != 0) {
            throw new RuntimeException("Tried to create new object with a user defined id");
        }
        Connection c = this.db.getConnection();
        PreparedStatement s = c.prepareStatement("INSERT INTO Vastaus (viesti_vastattava, viesti_vastaus) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        Viesti vastattava = newObject.getVastattava();
        int vastattavaViestiId = 0;
        if (vastattava != null) {
            vastattavaViestiId = vastattava.getViesti_id();
        }

        s.setObject(1, vastattavaViestiId);
        s.setObject(2, newObject.getVastaus().getViesti_id());
        int newId = createNewObject(s);
        s.close();
        c.close();
        return findOne(newId);
    }

}
