/**
 * 
 */
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
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.model.Machine;

/**
 * @author Gafur Hayytbayev
 *
 */
public class MachineDAO implements DAO<Machine, Integer> {

	@Override
	public boolean insert(Machine t) {
		boolean result = false;

		String sqlInsertQueryV2 = "INSERT INTO machine (mno,fname,lname, role, tagid) VALUES (?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement prepStatement = null;
		try {

			con = PostgreSQL.getInstance().getConnection();

			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(sqlInsertQueryV2);

			prepStatement.setLong(1, t.getMachineNo());
			prepStatement.setString(2, t.getFname());
			prepStatement.setString(3, t.getLname());
			prepStatement.setString(4, t.getRole());
			prepStatement.setInt(5, t.getTagId());

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
	public boolean update(Machine m, String[] params) {
		boolean result = false;

		Connection con = null;
		PreparedStatement prepStatement = null;
		String updateString = "UPDATE machine " + "SET mno=?, fname=?, lname=?, role=? " + "WHERE tagid=?";

		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);

			prepStatement = con.prepareStatement(updateString);

			prepStatement.setLong(1, m.getMachineNo());
			prepStatement.setString(2, m.getFname());
			prepStatement.setString(3, m.getLname());
			prepStatement.setString(4, m.getRole());
			prepStatement.setInt(5, m.getTagId());

			prepStatement.executeUpdate();
			con.commit();
			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
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
		boolean result = false;
		String table = "machine";
		String column = "tagid";
		String sqlDeleteQuery = PostgreSQL.getInstance().createDeleteQuery(list, table, column);
		Connection con = null;
		PreparedStatement prepStatement = null;
		try {

			con = PostgreSQL.getInstance().getConnection();

			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(sqlDeleteQuery);

			int index = 1;

			for (Object o : list) {
				prepStatement.setObject(index++, o);
			}

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
	public HashMap<Integer, Machine> get(String[] params) {
		HashMap<Integer, Machine> machineMap = new HashMap<>();

		String sqlQuery = "SELECT mno, fname, lname, role, tagid FROM public.machine";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = PostgreSQL.getInstance().getConnection();
			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				long mno = rs.getLong("mno");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String role = rs.getString("role");
				int tagid = rs.getInt("tagid");
				machineMap.put(tagid, new Machine(mno, fname, lname, role, tagid));
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

		return machineMap;
	}

	@Override
	public ArrayList<Machine> get(int tid, DateTime dt1, DateTime dt2) {
		// TODO Auto-generated method stub
		return null;
	}

}
