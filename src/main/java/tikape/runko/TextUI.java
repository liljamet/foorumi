/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

import java.util.List;
import java.util.Scanner;
import tikape.runko.domain.Lahettaja;
import tikape.runko.domain.Viesti;
import tikape.runko.service.DisplayableKeskustelu;
import tikape.runko.service.FoorumiService;
import tikape.runko.service.FoorumiServiceImpl;
import tikape.runko.service.UusiKeskustelu;

/**
 *
 * @author lilja
 */
public class TextUI {

    private FoorumiService service;
    private KayttoliittymaTaso taso;
    private int keskustelualueId = 0;
    private int keskusteluId = 0;
    private Scanner lukija = new Scanner(System.in);

    public TextUI() {
        service = new FoorumiServiceImpl();
        taso = KayttoliittymaTaso.PAA;
    }

    void launch() {
        System.out.println("Tervetuloa keskustelu foorumille");

        while (true) {
            printOhje();
            String komento = lueKomento();
            if (komento != null) {
                toteutaKomento(komento);
            }

            if (taso == null) {
                break;
            }
        }
        lukija.close();
    }

    private void printOhje() {
        StringBuilder ohje = new StringBuilder("Näytä kaikki : show all\n");
        switch (taso) {
            case PAA:
                ohje.append("Luo uusi käyttäjä : new user\n");
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

    private void toteutaKomento(String komento) {
        if ("exit".equalsIgnoreCase(komento)) {
            int tasonumero = this.taso.getTasoNumero() - 1;
            this.taso = KayttoliittymaTaso.getKayttoliittymaTaso(tasonumero);
            if (taso == KayttoliittymaTaso.KESKUSTELUALUE) {
                keskusteluId = 0;
            }
            if (taso == KayttoliittymaTaso.PAA) {
                keskusteluId = 0;
                keskustelualueId = 0;
            }
            return;
        }

        if (komento.startsWith("show")) {
            String[] argumentit = komento.split(" ");
            if (argumentit.length == 1) {
                return;
            }
            String argumentti = argumentit[1];
            if ("all".equalsIgnoreCase(argumentti)) {
                switch (taso) {
                    case PAA:
                        printKeskusteluAlueet();
                        break;
                    case KESKUSTELUALUE:
                        printKeskustelut();
                        break;
                    case KESKUSTELU:
                        printViestit();
                        break;
                }
            }
            int id = 0;
            try {
                id = Integer.parseInt(argumentti);
            } catch (Exception e) {
                //Ignore
            }
            if (id == 0) {
                return;
            }

            switch (taso) {
                case PAA:
                    this.keskustelualueId = id;
                    printKeskustelut();
                    this.taso = KayttoliittymaTaso.KESKUSTELUALUE;
                    break;
                case KESKUSTELUALUE:
                    this.keskusteluId = id;
                    printViestit();
                    this.taso = KayttoliittymaTaso.KESKUSTELU;
                    break;
                case KESKUSTELU:
                    break;
            }
            return;
        }

        if (komento.equals("new user") && taso.equals(KayttoliittymaTaso.PAA)) {
            String nimimerkki = lueNimimerkki();
            service.luoKayttaja(nimimerkki);
            return;
        }

        if (komento.equals("create")) {
            switch (taso) {
                case PAA:
                    luoKeskustelualue();
                    break;
                case KESKUSTELUALUE:
                    luoKeskustelu();
                    break;
                case KESKUSTELU:
                    luoViesti();
                    break;
            }
        }

    }

    private void printKeskusteluAlueet() {
        System.out.println("id nimi viestienlukumaara viimeisinviesti");
        for (DisplayableKeskustelu keskustelu : service.getKeskustelualueet()) {
            printKeskustelu(keskustelu);
        }
    }

    private void printKeskustelut() {
        System.out.println("id nimi viestienlukumaara viimeisinviesti");
        for (DisplayableKeskustelu keskustelu : service.getKeskustelut(keskustelualueId)) {
            printKeskustelu(keskustelu);
        }
    }

    private void printKeskustelu(DisplayableKeskustelu keskustelu) {
        print(keskustelu.getId() + " " + keskustelu.getNimi() + " " + keskustelu.getViestienLukumaara() + " " + keskustelu.getViimeisinViesti());
    }

    private void printViestit() {
        List<Viesti> viestit = service.getViestit(this.keskusteluId);
        for (Viesti viesti : viestit) {
            System.out.println(viesti.getViesti_id() + " " + viesti.getLahettaja().getNimimerkki() + " " + viesti.getKellonaika() + "\n"
                    + " " + viesti.getViesti());
        }
    }

    private String lueKomento() {
        System.out.print("Komento: ");
        return lueSyote();
    }

    private String lueSyote() {
        String komento = lukija.nextLine().trim().toLowerCase();
        if (komento.isEmpty()) {
            return null;
        }
        return komento;
    }

    private void print(String message) {
        System.out.println(message);
    }

    private String lueNimimerkki() {
        String nimimerkki = null;
        while (nimimerkki == null) {
            System.out.print("Nimimerkki: ");
            nimimerkki = lueSyote();
        }
        return nimimerkki;
    }

    private void luoKeskustelualue() {
        System.out.print("Anna uusi keskustelualue: ");
        String keskustelualueenNimi = lueSyote();
        service.luoKeskustelualue(keskustelualueenNimi);

    }

    private void luoKeskustelu() {
        System.out.print("Anna uusi keskustelu: ");
        String keskustelunNimi = lueSyote();
        System.out.print("Kirjoita viesti: ");
        String viesti = lueSyote();
        printKayttajat();
        System.out.print("Valitse nimimerkin id: ");
        int lahettajaId = Integer.parseInt(lueSyote());

        UusiKeskustelu uusiKeskustelu = new UusiKeskustelu();
        uusiKeskustelu.setKeskustelualue_id(keskustelualueId);
        uusiKeskustelu.setLahettaja_id(lahettajaId);
        uusiKeskustelu.setOtsikko(keskustelunNimi);
        uusiKeskustelu.setViesti(viesti);
        service.luoKeskustelu(uusiKeskustelu);
    }

    private void luoViesti() {
        System.out.print("Valitse vastattavan viestin id: ");
        int vastattavaViestiId = Integer.parseInt(lueSyote());
        System.out.print("Kirjoita viesti: ");
        String viesti = lueSyote();
        printKayttajat();
        System.out.print("Valitse nimimerkin id: ");
        int lahettajaId = Integer.parseInt(lueSyote());
        service.luoViesti(viesti, vastattavaViestiId, this.keskusteluId, lahettajaId);
    }

    private void printKayttajat() {
        List<Lahettaja> kayttajat = service.getKayttajat();
        for (Lahettaja lahettaja : kayttajat) {
            System.out.println(lahettaja.getLahettajaId() + " " + lahettaja.getNimimerkki());
        }
    }

}
