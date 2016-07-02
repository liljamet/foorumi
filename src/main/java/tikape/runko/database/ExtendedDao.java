/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.SQLException;
import java.util.List;
import tikape.runko.service.DisplayableKeskustelu;

/**
 *
 * @author lilja
 */
public interface ExtendedDao<T, K> extends Dao<T, K> {
    List<DisplayableKeskustelu> findAllKeskusteluWithInfo() throws SQLException;
}
