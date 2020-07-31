/**
 * 
 */
package tr.com.minetrack.app.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.joda.time.DateTime;

import tr.com.minetrack.app.db.DAO;
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.model.Tracked;

/**
 * @author Gafur Hayytbayev
 *
 */
public class TrackedDAO implements DAO<Tracked, Integer> {

	@Override
	public boolean insert(Tracked t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Tracked t, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<Integer, Tracked> get(String[] params) {
		HashMap<Integer, Tracked> trackedMap = new LinkedHashMap<>();

		String sqlQuery = "SELECT employee.fname, employee.lname, employee.tagid\r\n" + "FROM employee\r\n"
				+ "UNION\r\n" + "SELECT machine.fname, machine.lname, machine.tagid\r\n" + "FROM machine;";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {

			con = PostgreSQL.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				String fname = rs.getString(1);
				String lname = rs.getString(2);
				int tagid = rs.getInt(3);

				trackedMap.put(tagid, new Tracked(fname, lname, tagid));
			}

		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		} finally {
			try {
				rs.close();
				statement.close();
			} catch (SQLException e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}
		}

		return trackedMap;
	}

	@Override
	public ArrayList<Tracked> get(int tid, DateTime dt1, DateTime dt2) {
		// TODO Auto-generated method stub
		return null;
	}

}
