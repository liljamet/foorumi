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
    
    public FoorumiDatabase(String address) throws ClassNotFoundException{
        this.databaseAddress=address;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    } 
    
    
    
    
}
