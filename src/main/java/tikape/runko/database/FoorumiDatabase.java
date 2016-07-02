/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lilja
 */
public class FoorumiDatabase {

    private String databaseAddress;

    public FoorumiDatabase(String address) throws ClassNotFoundException {
        this.databaseAddress = address;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE IF NOT EXISTS Keskustelualue(\n"
                + "alue_id integer PRIMARY KEY,\n"
                + "nimi varchar(200)\n"
                + ");");
        lista.add("CREATE TABLE IF NOT EXISTS Keskustelu(\n"
                + "keskustelu_id integer PRIMARY KEY,\n"
                + "otsikko varchar(200),\n"
                + "keskustelualue integer,\n"
                + "FOREIGN KEY(keskustelualue) REFERENCES Keskustelualue(alue_id)\n"
                + ");");
        lista.add("CREATE TABLE IF NOT EXISTS Lahettaja(\n"
                + "lahettaja_id integer PRIMARY KEY,\n"
                + "nimimerkki varchar(50)\n"
                + ");");
        lista.add("CREATE TABLE IF NOT EXISTS Viesti(\n"
                + "viesti_id integer PRIMARY KEY,\n"
                + "kellonaika timestamp,\n"
                + "viesti varchar(1000),\n"
                + "keskustelu integer,\n"
                + "lahettaja integer,\n"
                + "FOREIGN KEY(keskustelu) REFERENCES Keskustelu(keskustelu_id),\n"
                + "FOREIGN KEY(lahettaja) REFERENCES Lahettaja(lahettaja_id)\n"
                + ");");
        lista.add("CREATE TABLE IF NOT EXISTS Vastaus(\n"
                + "vastaus_id integer PRIMARY KEY,\n"
                + "viesti_vastattava integer,\n"
                + "viesti_vastaus integer,\n"
                + "FOREIGN KEY(viesti_vastattava) REFERENCES Viesti(viesti_id),\n"
                + "FOREIGN KEY(viesti_vastaus) REFERENCES Viesti(viesti_id)\n"
                + ");");

        return lista;
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                st.executeUpdate(lause);
            }
            st.close();
            conn.close();

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

}
