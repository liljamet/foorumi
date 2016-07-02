/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.service;

import java.sql.Timestamp;

/**
 *
 * @author lilja
 */
public class DisplayableKeskustelu {
    
    private int id;
    private String nimi;
    private int viestienLukumaara;
    private Timestamp viimeisinViesti;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getViestienLukumaara() {
        return viestienLukumaara;
    }

    public void setViestienLukumaara(int viestienLukumaara) {
        this.viestienLukumaara = viestienLukumaara;
    }

    public Timestamp getViimeisinViesti() {
        return viimeisinViesti;
    }

    public void setViimeisinViesti(Timestamp viimeisinViesti) {
        this.viimeisinViesti = viimeisinViesti;
    }
    
    
}
