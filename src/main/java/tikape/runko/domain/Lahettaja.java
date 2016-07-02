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

    private int lahettajaId;
    private String nimimerkki;

    public Lahettaja(int id, String nimim) {
        this.lahettajaId = id;
        this.nimimerkki = nimim;
    }

    /**
     * @return the lahettaja_id
     */
    public int getLahettajaId() {
        return lahettajaId;
    }

    /**
     * @param lahettaja_id the lahettaja_id to set
     */
    public void setLahettajaId(int lahettaja_id) {
        this.lahettajaId = lahettaja_id;
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
