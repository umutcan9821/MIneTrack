/**
 * 
 */
package tr.com.minetrack.app.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

import tr.com.minetrack.app.db.DAO;
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.model.Signal;

/**
 * @author Gafur Hayytbayev
 *
 */
public class SignalDAO implements DAO<Signal, String> {

	@Override
	public boolean insert(Signal signal) {
		boolean result = false;
		String sqlInsertQueryV2 = "INSERT INTO signal (time,rid,tid,rssi) VALUES (?, ?, ?, ?)";

		Timestamp timeStamp = new Timestamp(signal.getDt().getMillis());
		Connection con = null;
		PreparedStatement prepStatement = null;
		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(sqlInsertQueryV2);

			prepStatement.setTimestamp(1, timeStamp);
			prepStatement.setInt(2, signal.getRid());
			prepStatement.setInt(3, signal.getTid());
			prepStatement.setInt(4, signal.getRssi());

			prepStatement.executeUpdate();

			con.commit();
			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			// update
			result = false;
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(excep));
				}
			}
		} finally {
			if (prepStatement != null) {
				try {
					con.setAutoCommit(true);
					prepStatement.close();
				} catch (SQLException e) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
				}
			}
		}
		return result;
	}

	@Override
	public boolean update(Signal t, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ArrayList<String> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<String, Signal> get(String[] params) {
		return null;
	}

	@Override
	public ArrayList<Signal> get(int tid, DateTime dt1, DateTime dt2) {
		// TODO Auto-generated method stub
		return null;
	}

}
