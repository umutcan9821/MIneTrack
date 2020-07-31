package tr.com.minetrack.app.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

import tr.com.minetrack.app.db.DAO;
import tr.com.minetrack.app.db.jdbc.PostgreSQL;
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.model.License;

public class LicenseDAO implements DAO<License, Integer> {

	@Override
	public boolean insert(License t) {
		return false;
	}

	@Override
	public boolean update(License t, String[] params) {
		boolean result = false;

		Connection con = null;
		PreparedStatement prepStatement = null;
		String updateString = "UPDATE license " + "SET licensevalue=? " + "WHERE licensekey=?";

		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);

			prepStatement = con.prepareStatement(updateString);

			prepStatement.setString(1, t.getDate());
			prepStatement.setString(2, t.getKey());

			prepStatement.executeUpdate();
			con.commit();
			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			result = false;
			if (con != null) {
				try {
					//System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					//System.out.println("exception is being in roll back");
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(excep));
				}
			}
		} finally {
			if (prepStatement != null) {
				try {
					prepStatement.close();
				} catch (SQLException e) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}
		}

		return result;
	}

	@Override
	public boolean delete(ArrayList<Integer> list) {
		return false;
	}

	@Override
	public HashMap<Integer, License> get(String[] params) {
		HashMap<Integer, License> licenseMap = new HashMap<>();

		String sqlQuery = "SELECT * FROM license";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);
			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				String encodedKey = rs.getString("licensekey");
				String encrypted = rs.getString("licensevalue");
				licenseMap.put(0, new License(encodedKey, encrypted));
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

		return licenseMap;
	}

	@Override
	public ArrayList<License> get(int tid, DateTime dt1, DateTime dt2) {
		// TODO Auto-generated method stub
		return null;
	}

}
