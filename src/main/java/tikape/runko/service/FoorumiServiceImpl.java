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

    @Override
    public void luoKeskustelu(UusiKeskustelu uusiKeskustelu) {
        String keskustelunNimi = uusiKeskustelu.getOtsikko();
        int keskustelualueId = uusiKeskustelu.getKeskustelualue_id();
        Keskustelu keskustelu = null;
        try {
            keskustelu = new Keskustelu(0, keskustelunNimi, keskustelualueDao.findOne(keskustelualueId));
        } catch (SQLException ex) {
            throw new RuntimeException("Keskustelualue " + keskustelualueId + " ei löytynyt", ex);
        }

        try {
            keskustelu = keskusteluDao.createNew(keskustelu);
        } catch (SQLException ex) {
            throw new RuntimeException("Keskustelun lisääminen epäonnistui", ex);
        }
        
        luoViesti(uusiKeskustelu.getViesti(), null, keskustelu.getKeskustelu_id(), uusiKeskustelu.getLahettaja_id());
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
        Viesti vastattavaViesti;
        List<Viesti> viestit;
        try {
            vastattavaViesti = viestiDao.findOne(vastattavaViestiId);
            viestit = viestiDao.findByKeskusteluId(keskusteluId);
        } catch (SQLException ex) {
            throw new RuntimeException("Tiedon loytaminen epaonnistui", ex);
        }
        
        boolean found = false;
        for(Viesti viesti : viestit) { 
            if(viesti.getViesti_id() == vastattavaViesti.getViesti_id()) {
                found = true;
            }
        }
        
        if(!found) {
            throw new RuntimeException("Viesti ei kuulunut keskusteluun");
        }
        
        luoViesti(viestiTeksti, vastattavaViesti, keskusteluId, lahettajaId);

    }

    private void luoViesti(String viestiTeksti, Viesti vastattavaViesti, int keskusteluId, int lahettajaId) throws RuntimeException {
        Keskustelu keskustelu;
        Lahettaja lahettaja;
        try {
            keskustelu = keskusteluDao.findOne(keskusteluId);
            lahettaja = lahettajaDao.findOne(lahettajaId);
        } catch (SQLException ex) {
            throw new RuntimeException("Tietoja ei loytynyt", ex);
        }
        
        Viesti viesti = new Viesti(0, null, viestiTeksti, keskustelu, lahettaja);

        try {
            viesti = viestiDao.createNew(viesti);
        } catch (SQLException ex) {
            throw new RuntimeException("Viestin luominen epaonnistui", ex);
        }

        try {
            vastausDao.createNew(new Vastaus(0, vastattavaViesti, viesti));
        } catch (SQLException ex) {
            throw new RuntimeException("Vastauksen luominen epaonnistui", ex);
        }
    }

}
