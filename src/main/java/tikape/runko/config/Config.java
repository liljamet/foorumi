/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.config;

import tikape.runko.database.FoorumiDatabase;

/**
 *
 * @author lilja
 */
public class Config {

    private static FoorumiDatabase database;

    public static FoorumiDatabase getDatabase() throws ClassNotFoundException {
        if (database == null) {
            database = new FoorumiDatabase("jdbc:sqlite:foorumi.db");
        }
        return database;
    }
}
