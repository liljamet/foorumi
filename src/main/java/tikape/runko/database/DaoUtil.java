/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lilja
 */
public class DaoUtil {

    public static int createNewObject(PreparedStatement s) throws SQLException {
        s.executeUpdate();
        ResultSet rs = s.getGeneratedKeys();
        int newId = -1;
        if (rs.next()) {
            newId = rs.getInt(1);
        }
        rs.close();
        return newId;
    }

}
