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
import tr.com.minetrack.app.model.Employee;

/**
 * @author Gafur Hayytbayev
 *
 */
public class EmployeeDAO implements DAO<Employee, Integer> {

	@Override
	public boolean insert(Employee emp) {
		boolean result = false;

		String sqlInsertQueryV2 = "INSERT INTO employee (tcno,fname,lname,role, tagid) VALUES (?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement prepStatement = null;
		try {

			con = PostgreSQL.getInstance().getConnection();

			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(sqlInsertQueryV2);

			prepStatement.setLong(1, emp.getTcno());
			prepStatement.setString(2, emp.getFname());
			prepStatement.setString(3, emp.getLname());
			prepStatement.setString(4, emp.getRole());
			prepStatement.setInt(5, emp.getTagId());

			prepStatement.executeUpdate();

			con.commit();
			result = true;
		} catch (SQLException e1) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e1));
			// update
			result = false;
			if (con != null) {
				try {
					//System.err.print("Transaction is being rolled back: " + e1.getMessage());
					con.rollback();
					//System.out.println("error in insertion[insert method]");
				} catch (SQLException e2) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e2));
				}
			}
		} finally {
			if (prepStatement != null) {
				try {
					con.setAutoCommit(true);
					prepStatement.close();
				} catch (SQLException e3) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e3));
				}
			}
		}

		return result;
	}

	@Override
	public boolean update(Employee emp, String[] params) {
		boolean result = false;

		Connection con = null;
		PreparedStatement prepStatement = null;
		String updateString = "UPDATE employee " + "SET tcno=?, fname=?, lname=?, role=? " + "WHERE tagid=?";

		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);

			prepStatement = con.prepareStatement(updateString);

			prepStatement.setLong(1, emp.getTcno());
			prepStatement.setString(2, emp.getFname());
			prepStatement.setString(3, emp.getLname());
			prepStatement.setString(4, emp.getRole());
			prepStatement.setInt(5, emp.getTagId());

			prepStatement.executeUpdate();
			con.commit();
			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			result = false;
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
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
	public boolean delete(ArrayList<Integer> tagList) {
		boolean result = false;
		String table = "employee";
		String column = "tagid";
		String sqlDeleteQuery = PostgreSQL.getInstance().createDeleteQuery(tagList, table, column);
		Connection con = null;
		PreparedStatement prepStatement = null;
		try {

			con = PostgreSQL.getInstance().getConnection();

			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(sqlDeleteQuery);

			int index = 1;

			for (Object o : tagList) {
				prepStatement.setObject(index++, o);
			}

			prepStatement.executeUpdate();

			con.commit();
			result = true;
		} catch (SQLException e1) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e1));
			// update
			result = false;
			if (con != null) {
				try {
					//System.err.print("Transaction is being rolled back: " + e1.getMessage());
					con.rollback();
					//System.out.println("error in delete[delete method]");
				} catch (SQLException e2) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e2));
				}
			}
		} finally {
			if (prepStatement != null) {
				try {
					con.setAutoCommit(true);
				} catch (SQLException e3) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e3));
				}
			}
		}

		return result;
	}

	@Override
	public HashMap<Integer, Employee> get(String[] params) {
		HashMap<Integer, Employee> employeeMap = new HashMap<>();

		String sqlQuery = "SELECT tcno, fname, lname, role, tagid FROM public.employee";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {

			con = PostgreSQL.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				long tcno = rs.getLong("tcno");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String role = rs.getString("role");
				int tagid = rs.getInt("tagid");
				employeeMap.put(tagid, new Employee(tcno, fname, lname, role, tagid));
			}

		} catch (SQLException e1) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e1));
		} finally {
			try {
				rs.close();
				statement.close();
			} catch (SQLException e2) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e2));
			}
		}

		return employeeMap;
	}

	@Override
	public ArrayList<Employee> get(int tid, DateTime dt1, DateTime dt2) {
		// TODO Auto-generated method stub
		return null;
	}
}
