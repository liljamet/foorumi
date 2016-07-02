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
import tikape.runko.database.LahettajaDao;
import tikape.runko.database.VastausDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Lahettaja;
import tikape.runko.domain.Vastaus;
import tikape.runko.domain.Viesti;

public class FoorumiServiceImpl implements FoorumiService {

    private KeskustelualueDao keskustelualueDao;
    private KeskusteluDao keskusteluDao;
    private ViestiDao viestiDao;
    private LahettajaDao lahettajaDao;
    private VastausDao vastausDao;

    public FoorumiServiceImpl() {
        try {
            FoorumiDatabase database = Config.getDatabase();
            keskustelualueDao = new KeskustelualueDao(database);
            keskusteluDao = new KeskusteluDao(database);
            viestiDao = new ViestiDao(database);
            lahettajaDao = new LahettajaDao(database);
            vastausDao = new VastausDao(database);
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
            throw new RuntimeException("Haku epäonnistui", ex);
        }
    }

    @Override
    public void luoKayttaja(String nimimerkki) {

        Lahettaja lahettaja = new Lahettaja(0, nimimerkki);

        try {

            lahettajaDao.createNew(lahettaja);

        } catch (SQLException ex) {
            throw new RuntimeException("Lahettajan lisääminen epäonnistui", ex);
        }
    }

    public void luoKeskustelualue(String nimi) {
        Keskustelualue alue = new Keskustelualue(0, nimi);
        try {
            keskustelualueDao.createNew(alue);
        } catch (SQLException ex) {
            throw new RuntimeException("Alueen lisääminen epäonnistui", ex);
        }
    }

    public void luoKeskustelu(String keskustelunNimi, int keskustelualueId) {
        Keskustelu keskustelu = null;
        try {
            keskustelu = new Keskustelu(0, keskustelunNimi, keskustelualueDao.findOne(keskustelualueId));
        } catch (SQLException ex) {
            throw new RuntimeException("Keskustelualue " + keskustelualueId + " ei löytynyt", ex);
        }

        try {
            keskusteluDao.createNew(keskustelu);
        } catch (SQLException ex) {
            throw new RuntimeException("Keskustelun lisääminen epäonnistui", ex);
        }
    }

    @Override
    public List<Viesti> getViestit(int keskusteluId) {
        try {
            return this.viestiDao.findByKeskusteluId(keskusteluId);
        } catch (SQLException ex) {
            throw new RuntimeException("Keskustelua " + keskusteluId + " ei löytynyt", ex);
        }
    }

    @Override
    public List<Lahettaja> getKayttajat() {
        try {
            return lahettajaDao.findAll();
        } catch (SQLException ex) {
            throw new RuntimeException("Tapahtui virhe lahettajia etsiessa", ex);
        }
    }

    @Override
    public void luoViesti(String viestiTeksti, int vastattavaViestiId, int keskusteluId, int lahettajaId) {
        Keskustelu keskustelu;
        try {
            keskustelu = keskusteluDao.findOne(keskusteluId);
        } catch (SQLException ex) {
            throw new RuntimeException("Keskustelua ei loytynyt", ex);
        }
        Lahettaja lahettaja;
        try {
            lahettaja = lahettajaDao.findOne(lahettajaId);
        } catch (SQLException ex) {
            throw new RuntimeException("Lahettajaa ei loytynyt", ex);
        }
        Viesti viesti = new Viesti(0, null, viestiTeksti, keskustelu, lahettaja);

        try {
            Viesti uusiViesti = null;// viestiDao.createNew(viesti);
            vastausDao.createNew(new Vastaus(0, viestiDao.findOne(vastattavaViestiId), uusiViesti));
        } catch (SQLException ex) {
            Logger.getLogger(FoorumiServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
