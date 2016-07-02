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
public class Keskustelu {

    private int keskustelu_id;
    private String otsikko;
    private Keskustelualue keskustelualue;

    public Keskustelu(int keskusteluID, String otsikko, Keskustelualue keskus) {
        this.keskustelu_id = keskusteluID;
        this.otsikko = otsikko;
        this.keskustelualue = keskus;
    }

    /**
     * @return the keskustelu_id
     */
    public int getKeskustelu_id() {
        return keskustelu_id;
    }

    /**
     * @param keskustelu_id the keskustelu_id to set
     */
    public void setKeskustelu_id(int keskustelu_id) {
        this.keskustelu_id = keskustelu_id;
    }

    /**
     * @return the otsikko
     */
    public String getOtsikko() {
        return otsikko;
    }

    /**
     * @param otsikko the otsikko to set
     */
    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    /**
     * @return the keskustelualue
     */
    public Keskustelualue getKeskustelualue() {
        return keskustelualue;
    }

    /**
     * @param keskustelualue the keskustelualue to set
     */
    public void setKeskustelualue(Keskustelualue keskustelualue) {
        this.keskustelualue = keskustelualue;
    }

}
