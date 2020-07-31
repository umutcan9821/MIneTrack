package tr.com.minetrack.app.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import tr.com.minetrack.app.db.DAO;
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;

public class DailyReportDAO implements DAO<DateTime, Integer> {

	@Override
	public boolean insert(DateTime t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(DateTime t, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<Integer, DateTime> get(String[] params) {

		int tid = Integer.parseInt(params[0]);
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime date1 = formatter.parseDateTime(params[1]);
		DateTime date2 = formatter.parseDateTime(params[2]);

		HashMap<Integer, DateTime> dates = new LinkedHashMap<>();

		String sqlQuery = "(SELECT time " + "FROM signal " + "WHERE tid=" + tid + " AND time BETWEEN '\"" + date1
				+ "' AND '\"" + date2 + "\"'\r\n" + "ORDER BY time ASC, time ASC LIMIT 1) " + "UNION " + "(SELECT time "
				+ "FROM signal " + "WHERE tid=" + tid + " AND time BETWEEN '\"" + date1 + "' AND '\"" + date2
				+ "\"'\r\n" + "ORDER BY time DESC, time DESC LIMIT 1)";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = PostgreSQL.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			int ids = 0;
			while (rs.next()) {
				Timestamp timeStamp = rs.getTimestamp("time");
				DateTime dt = new DateTime(timeStamp);
				dates.put(ids++, dt);
			}

		} catch (SQLException | NullPointerException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		} finally {
			try {
				rs.close();
				statement.close();
			} catch (SQLException | NullPointerException e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}
		}

		return dates;
	}

	@Override
	public ArrayList<DateTime> get(int tid, DateTime dt1, DateTime dt2) {
		// TODO Auto-generated method stub
		return null;
	}

}
