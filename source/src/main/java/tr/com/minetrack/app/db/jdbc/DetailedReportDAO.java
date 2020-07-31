package tr.com.minetrack.app.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

import tr.com.minetrack.app.db.DAO;
import tr.com.minetrack.app.helpers.TimeAndRid;
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;

public class DetailedReportDAO implements DAO<TimeAndRid, Integer> {

	@Override
	public boolean insert(TimeAndRid t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(TimeAndRid t, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<Integer, TimeAndRid> get(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<TimeAndRid> get(int tid, DateTime dt1, DateTime dt2) {

		ArrayList<TimeAndRid> list = new ArrayList<>();

		String sqlQuery = "SELECT \"time\", rid\r\n" + 
				"  FROM signal\r\n" + 
				"  WHERE tid = " + tid + " AND time >= '"+dt1+"' \r\n" + 
				"  and time < '" + dt2 +"'";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = PostgreSQL.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			
			while (rs.next()) {
				Timestamp timeStamp = rs.getTimestamp("time");
				DateTime dt = new DateTime(timeStamp);
				int readerID = rs.getInt("rid");
				list.add(new TimeAndRid(dt, readerID));
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
		
		return list;
	}

}
