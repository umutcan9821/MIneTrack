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

import org.joda.time.DateTime;

import tr.com.minetrack.app.db.DAO;
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.model.Account;

/**
 * @author Gafur Hayytbayev
 *
 */
public class AccountDAO implements DAO<Account, Integer> {

	@Override
	public boolean insert(Account t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Account t, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<Integer, Account> get(String[] params) {
		HashMap<Integer, Account> accountMap = new HashMap<>();

		String sqlQuery = "SELECT * FROM public.account";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {

			con = PostgreSQL.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			rs.next();
			String un = rs.getString("username");
			String pw = rs.getString("password");

			accountMap.put(0, new Account(un, pw));

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

		return accountMap;
	}

	@Override
	public ArrayList<Account> get(int tid, DateTime dt1, DateTime dt2) {
		// TODO Auto-generated method stub
		return null;
	}
}
