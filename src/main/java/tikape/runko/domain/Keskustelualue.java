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
public class Keskustelualue {

    private int alue_id;
    private String nimi;

    public Keskustelualue(int alueID, String nimi) {
        this.alue_id = alueID;
        this.nimi = nimi;
    }

    /**
     * @return the alue_id
     */
    public int getAlue_id() {
        return alue_id;
    }

    /**
     * @param alue_id the alue_id to set
     */
    public void setAlue_id(int alue_id) {
        this.alue_id = alue_id;
    }

    /**
     * @return the nimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * @param nimi the nimi to set
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

}
