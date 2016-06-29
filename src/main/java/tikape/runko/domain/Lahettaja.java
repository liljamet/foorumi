/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author lilja
 */
public class Lahettaja {
    private int lahettaja_id;
    private String nimimerkki;
    
    public Lahettaja(int id, String nimim) {
        this.lahettaja_id=id;
        this.nimimerkki=nimim;
    }

    /**
     * @return the lahettaja_id
     */
    public int getLahettaja_id() {
        return lahettaja_id;
    }

    /**
     * @param lahettaja_id the lahettaja_id to set
     */
    public void setLahettaja_id(int lahettaja_id) {
        this.lahettaja_id = lahettaja_id;
    }

    /**
     * @return the nimimerkki
     */
    public String getNimimerkki() {
        return nimimerkki;
    }

    /**
     * @param nimimerkki the nimimerkki to set
     */
    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }
    
    
}
