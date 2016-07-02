/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.service;

import java.util.List;
import tikape.runko.domain.Keskustelualue;

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
}
