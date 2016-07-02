/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.post;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.service.FoorumiService;
import tikape.runko.service.FoorumiServiceImpl;
import tikape.runko.service.UusiKeskustelu;

/**
 *
 * @author lilja
 */
public class GraphicUI {

    private FoorumiService service;

    public GraphicUI() {
        service = new FoorumiServiceImpl();
    }

    public void launch() {
        get("/", (req, res) -> {
            return getKeskustelualueetNakyma();
        }, new ThymeleafTemplateEngine());

        post("/", (req, res) -> {
            String nimi = req.queryParams("nimi");
            service.luoKeskustelualue(nimi);

            return getKeskustelualueetNakyma();
        }, new ThymeleafTemplateEngine());

        get("/keskustelualue/:id", (req, res) -> {
            return getKeskustelutNakyma(Integer.parseInt(req.params("id")));
        }, new ThymeleafTemplateEngine());

        post("/keskustelualue/:id", (req, res) -> {
            String otsikko = req.queryParams("otsikko");
            String viesti = req.queryParams("viesti");
            int keskustelualueId = Integer.parseInt(req.params("id"));
            int kayttajaId = Integer.parseInt(req.queryParams("kayttajaid"));

            UusiKeskustelu uusiKeskustelu = new UusiKeskustelu();
            uusiKeskustelu.setKeskustelualue_id(keskustelualueId);
            uusiKeskustelu.setOtsikko(otsikko);
            uusiKeskustelu.setViesti(viesti);
            uusiKeskustelu.setLahettaja_id(kayttajaId);

            service.luoKeskustelu(uusiKeskustelu);

            return getKeskustelutNakyma(keskustelualueId);
        }, new ThymeleafTemplateEngine());

    }

    private ModelAndView getKeskustelutNakyma(int keskustelualueId) throws NumberFormatException {
        Map<String, Object> map = new HashMap<>();
        map.put("keskustelut", service.getKeskustelut(keskustelualueId));
        map.put("keskustelualueId", keskustelualueId);
        map.put("lahettajat", service.getKayttajat());
        ModelAndView modelAndView = new ModelAndView(map, "keskustelut");
        return modelAndView;
    }

    private ModelAndView getKeskustelualueetNakyma() {
        Map<String, Object> map = new HashMap<>();
        map.put("keskustelualueet", service.getKeskustelualueet());
        return new ModelAndView(map, "keskustelualueet");
    }

}
