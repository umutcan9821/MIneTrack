package tr.com.minetrack.app.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;

public class PostgreSQL {

	// kurulum icin
	private String url = "jdbc:postgresql://localhost:5432/minetrack";
	// ofis test icin
//	private String url = "jdbc:postgresql://localhost:5432/ytu";
	// remote
	// private String url = "jdbc:postgresql://78.188.36.154:5432/test";
	private String dbuser = "postgres";
	private String dbpass = "admin";

	private static Connection con;

	/**
	 * tek nesne
	 */
	private static volatile PostgreSQL sqlInstance = null;

	/**
	 * Double check locking yapabilmek icin kullanilan nesne
	 */
	private static Object lock = new Object();

	/**
	 * Tek nesneye ulasmak icin bir metod
	 * 
	 * @return PostgreSQL
	 */
	public static PostgreSQL getInstance() {
		if (sqlInstance == null) {
			// double checked locking
			synchronized (lock) {
				if (sqlInstance == null) {
					sqlInstance = new PostgreSQL();
				}
			}
		}
		return sqlInstance;
	}

	/**
	 * private constructor
	 */
	private PostgreSQL() {
		System.out.println("init()...");
		init();
	}

	private void init() {
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, dbuser, dbpass);

		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		} catch (ClassNotFoundException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
	}

	public Connection getConnection() {
		try {
			if (con.isClosed()) {
				con = DriverManager.getConnection(url, dbuser, dbpass);
			}
		} catch (SQLException | NullPointerException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return con;
	}

	public <T> String createDeleteQuery(ArrayList<T> list, String table, String column) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			builder.append("?,");
		}

		String sqlDeleteQuery = "DELETE FROM " + table + " WHERE " + column + " IN ("
				+ builder.deleteCharAt(builder.length() - 1).toString() + ")";
		return sqlDeleteQuery;
	}
}
