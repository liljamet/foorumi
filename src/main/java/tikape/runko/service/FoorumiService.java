/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.service;

import java.util.List;
import tikape.runko.domain.Keskustelualue;
import tikape.runko.domain.Lahettaja;
import tikape.runko.domain.Viesti;

/**
 *
 * @author lilja
 */
public interface FoorumiService {
    /**
     * Palauttaa listan DisplayableKeskustelu objekteja, joka vastaa
     * keskustelualueita
     * @return 
     */
    List<DisplayableKeskustelu> getKeskustelualueet();
    
    /**
     * Palauttaa listan DisplayableKeskustelu objekteja, joka vastaa
     * keskustelualueen keskusteluja
     * @param keskusteluAlueId
     * @return 
     */
    List<DisplayableKeskustelu> getKeskustelut(int keskusteluAlueId);

    public void luoKayttaja(String nimimerkki);

    public void luoKeskustelualue(String keskustelualueenNimi);

    public void luoKeskustelu(String keskustelunNimi, int keskustelualueId);

    public List<Viesti> getViestit(int keskusteluId);

    public List<Lahettaja> getKayttajat();

    public void luoViesti(String viesti, int vastattavaViestiId, int keskusteluId, int lahettajaId);
}
