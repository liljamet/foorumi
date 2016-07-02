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
        
    }

    private ModelAndView getKeskustelualueetNakyma() {
        Map<String, Object> map = new HashMap<>();
        map.put("keskustelualueet", service.getKeskustelualueet());
        return new ModelAndView(map, "keskustelualueet");
    }

}
