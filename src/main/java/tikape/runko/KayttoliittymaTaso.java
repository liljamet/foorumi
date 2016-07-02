/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

/**
 *
 * @author lilja
 */
public enum KayttoliittymaTaso {

    PAA("Pää", 0), KESKUSTELUALUE("Keskustelualue", 1), KESKUSTELU("Keskustelu", 2);

    private final int taso;
    private final String nimi;

    KayttoliittymaTaso(String nimi, int taso) {
        this.nimi = nimi;
        this.taso = taso;
    }

    public int getTasoNumero() {
        return taso;
    }

    public static KayttoliittymaTaso getKayttoliittymaTaso(int taso) {
        KayttoliittymaTaso[] tasot = KayttoliittymaTaso.values();
        if (taso >= tasot.length || taso < 0) {
            return null;
        }
        return tasot[taso];
    }
}
