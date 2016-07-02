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
public class Vastaus {

    private int vastaus_id;
    private Viesti vastattava;
    private Viesti vastaus;

    public Vastaus(int id, Viesti vastattava, Viesti vastaus) {
        this.vastattava = vastattava;
        this.vastaus = vastaus;
        this.vastaus_id = id;
    }

    /**
     * @return the vastaus_id
     */
    public int getVastaus_id() {
        return vastaus_id;
    }

    /**
     * @param vastaus_id the vastaus_id to set
     */
    public void setVastaus_id(int vastaus_id) {
        this.vastaus_id = vastaus_id;
    }

    /**
     * @return the vastattava
     */
    public Viesti getVastattava() {
        return vastattava;
    }

    /**
     * @param vastattava the vastattava to set
     */
    public void setVastattava(Viesti vastattava) {
        this.vastattava = vastattava;
    }

    /**
     * @return the vastaus
     */
    public Viesti getVastaus() {
        return vastaus;
    }

    /**
     * @param vastaus the vastaus to set
     */
    public void setVastaus(Viesti vastaus) {
        this.vastaus = vastaus;
    }

}
