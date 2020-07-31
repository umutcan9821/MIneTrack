/**
 * 
 */
package tr.com.minetrack.app.db;

import java.util.ResourceBundle;

import org.joda.time.DateTime;

import tr.com.minetrack.app.helpers.TimeAndRid;
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.model.Account;
import tr.com.minetrack.app.model.Employee;
import tr.com.minetrack.app.model.License;
import tr.com.minetrack.app.model.Machine;
import tr.com.minetrack.app.model.Signal;
import tr.com.minetrack.app.model.SignalMap;
import tr.com.minetrack.app.model.Tracked;

/**
 * @author Gafur Hayytbayev
 *
 */
public class DAOHelper {
	static class PropertyHandler {
		public static String getProperty(String key) {
			return ResourceBundle.getBundle("tr.com.minetrack.app.db.dao").getString(key);
		}
	}

	@SuppressWarnings("unchecked")
	public static DAO<Employee, Integer> getEmployeeDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<Employee, Integer>) Class.forName(PropertyHandler.getProperty("dao.impl.employee"))
					.newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<Signal, String> getSignalDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<Signal, String>) Class.forName(PropertyHandler.getProperty("dao.impl.signal")).newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<Tracked, Integer> getTrackedDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<Tracked, Integer>) Class.forName(PropertyHandler.getProperty("dao.impl.tracked"))
					.newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<Account, Integer> getAccountDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<Account, Integer>) Class.forName(PropertyHandler.getProperty("dao.impl.account"))
					.newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<License, Integer> getLicenseDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<License, Integer>) Class.forName(PropertyHandler.getProperty("dao.impl.license"))
					.newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<SignalMap, String> getSignalMapDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<SignalMap, String>) Class.forName(PropertyHandler.getProperty("dao.impl.signalmap"))
					.newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<Machine, Integer> getMachineDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<Machine, Integer>) Class.forName(PropertyHandler.getProperty("dao.impl.machine"))
					.newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<DateTime, Integer> getDailyReportDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<DateTime, Integer>) Class.forName(PropertyHandler.getProperty("dao.impl.daily"))
					.newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<TimeAndRid, Integer> getDetailedReportDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<TimeAndRid, Integer>) Class.forName(PropertyHandler.getProperty("dao.impl.detailed"))
					.newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}
}
