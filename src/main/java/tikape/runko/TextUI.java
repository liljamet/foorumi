/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

import java.util.Scanner;
import tikape.runko.service.DisplayableKeskustelu;
import tikape.runko.service.FoorumiService;
import tikape.runko.service.FoorumiServiceImpl;

/**
 *
 * @author lilja
 */
public class TextUI {

    private FoorumiService service;
    private KayttoliittymaTaso taso;

    public TextUI() {
        service = new FoorumiServiceImpl();
        taso = KayttoliittymaTaso.PAA;
    }

    void lauch() {
        System.out.println("Tervetuloa keskustelu foorumille");

        while (true) {
            printOhje();
            String komento = lueKomento();
            toteutaKomento(komento);

            if (taso == null) {
                break;
            }
        }

    }

    private void printKeskusteluAlue() {
        System.out.println("id nimi viestienlukumaara viimeisinviesti");
        for (DisplayableKeskustelu keskustelu : service.getKeskustelualueet()) {
            printKeskustelu(keskustelu);
        }
    }

    private void printKeskustelu(DisplayableKeskustelu keskustelu) {
        print(keskustelu.getId() + " " + keskustelu.getNimi() + " " + keskustelu.getViestienLukumaara() + " " + keskustelu.getViimeisinViesti());
    }

    private void printOhje() {
        StringBuilder ohje = new StringBuilder("Näytä kaikki : show all\n");
        switch (taso) {
            case PAA:
                ohje.append("Näytä keskustelualue : show <id>\n");
                break;
            case KESKUSTELUALUE:
                ohje.append("Näytä keskustelu : show <id>\n");
                break;
            case KESKUSTELU:
                break;
        }
        ohje.append("Luo uusi : create\n");
        ohje.append("Palaa : exit\n");

        print(ohje.toString());
    }

    private void print(String message) {
        System.out.println(message);
    }

    private String lueKomento() {
        Scanner lukija = new Scanner(System.in);
        System.out.print("Komento: ");
        String komento = lukija.nextLine();
        return komento;
    }

    private void toteutaKomento(String komento) {
        if ("exit".equalsIgnoreCase(komento)) {
            int tasonumero = this.taso.getTasoNumero() - 1;
            this.taso = KayttoliittymaTaso.getKayttoliittymaTaso(tasonumero);
            return;
        }
        
        if ("show all".equalsIgnoreCase(komento)) {
            printKeskusteluAlue();
        }
        
    }

}
