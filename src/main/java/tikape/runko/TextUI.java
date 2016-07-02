/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

import tikape.runko.service.DisplayableKeskustelu;
import tikape.runko.service.FoorumiService;
import tikape.runko.service.FoorumiServiceImpl;

/**
 *
 * @author lilja
 */
public class TextUI {
    private FoorumiService service;

    public TextUI() {
        service = new FoorumiServiceImpl();
    }

    void lauch() {
        System.out.println("Tervetuloa keskustelu foorumille");
        printKeskusteluAlue();
    }

    private void printKeskusteluAlue() {
        System.out.println("id nimi viestienlukumaara viimeisinviesti");
        for(DisplayableKeskustelu keskustelu : service.getKeskustelualueet()) {
            printKeskustelu(keskustelu);
        }
    }

    private void printKeskustelu(DisplayableKeskustelu keskustelu) {
        System.out.println(keskustelu.getId() + " " + keskustelu.getNimi() + " " + keskustelu.getViestienLukumaara() + " " + keskustelu.getViimeisinViesti());
    }
    
    
}
