/**
 * 
 */
package tr.com.minetrack.app.db;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

/**
 * @author Gafur Hayytbayev
 *
 */
public interface DAO<T, V>
{
    boolean insert(T t);

    boolean update(T t, String[] params);

    boolean delete(ArrayList<V> list);

    HashMap<V, T> get(String[] params);
    
    ArrayList<T> get(int tid, DateTime dt1, DateTime dt2);
}
