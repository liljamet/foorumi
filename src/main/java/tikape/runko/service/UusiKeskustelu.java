/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.service;

/**
 *
 * @author lilja
 */
public class UusiKeskustelu {

    private String otsikko;
    private String viesti;
    private int keskustelualue_id;
    private int lahettaja_id;

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public String getViesti() {
        return viesti;
    }

    public void setViesti(String viesti) {
        this.viesti = viesti;
    }

    public int getKeskustelualue_id() {
        return keskustelualue_id;
    }

    public void setKeskustelualue_id(int keskustelualue_id) {
        this.keskustelualue_id = keskustelualue_id;
    }

    public int getLahettaja_id() {
        return lahettaja_id;
    }

    public void setLahettaja_id(int lahettaja_id) {
        this.lahettaja_id = lahettaja_id;
    }

}
