/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.sql.Timestamp;

/**
 *
 * @author lilja
 */
public class Viesti {

    private int viesti_id;
    private Timestamp kellonaika;
    private String viesti;
    private Keskustelu keskustelu;
    private Lahettaja lahettaja;

    public Viesti(int id, Timestamp kellonaika, String viesti, Keskustelu keskustelu, Lahettaja lahettaja) {
        this.kellonaika = kellonaika;
        this.keskustelu = keskustelu;
        this.lahettaja = lahettaja;
        this.viesti = viesti;
        this.viesti_id = id;
    }

    /**
     * @return the viesti_id
     */
    public int getViesti_id() {
        return viesti_id;
    }

    /**
     * @param viesti_id the viesti_id to set
     */
    public void setViesti_id(int viesti_id) {
        this.viesti_id = viesti_id;
    }

    /**
     * @return the kellonaika
     */
    public Timestamp getKellonaika() {
        return kellonaika;
    }

    /**
     * @param kellonaika the kellonaika to set
     */
    public void setKellonaika(Timestamp kellonaika) {
        this.kellonaika = kellonaika;
    }

    /**
     * @return the viesti
     */
    public String getViesti() {
        return viesti;
    }

    /**
     * @param viesti the viesti to set
     */
    public void setViesti(String viesti) {
        this.viesti = viesti;
    }

    /**
     * @return the keskustelu
     */
    public Keskustelu getKeskustelu() {
        return keskustelu;
    }

    /**
     * @param keskustelu the keskustelu to set
     */
    public void setKeskustelu(Keskustelu keskustelu) {
        this.keskustelu = keskustelu;
    }

    /**
     * @return the lahettaja
     */
    public Lahettaja getLahettaja() {
        return lahettaja;
    }

    /**
     * @param lahettaja the lahettaja to set
     */
    public void setLahettaja(Lahettaja lahettaja) {
        this.lahettaja = lahettaja;
    }

}
