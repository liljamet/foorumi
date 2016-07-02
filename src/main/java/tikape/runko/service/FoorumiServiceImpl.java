/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tikape.runko.config.Config;
import tikape.runko.database.FoorumiDatabase;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.ViestiDao;

public class FoorumiServiceImpl implements FoorumiService {

    private KeskustelualueDao keskustelualueDao;
    private KeskusteluDao keskusteluDao;
    private ViestiDao viestiDao;

    public FoorumiServiceImpl() {
        try {
            FoorumiDatabase database = Config.getDatabase();
            keskustelualueDao = new KeskustelualueDao(database);
            keskusteluDao = new KeskusteluDao(database);
            viestiDao = new ViestiDao(database);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FoorumiServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<DisplayableKeskustelu> getKeskustelualueet() {
        try {
            return keskustelualueDao.findAllKeskusteluWithInfo();
        } catch (SQLException ex) {
            throw new RuntimeException("Haku epäonnistui", ex);
        }
    }

    @Override
    public List<DisplayableKeskustelu> getKeskustelut(int keskusteluAlueId) {
        try {
            return keskusteluDao.findAllKeskusteluWithInfo(keskusteluAlueId);
        } catch (SQLException ex) {
            throw new RuntimeException("Haku epäonnistui");
        }
    }

}
