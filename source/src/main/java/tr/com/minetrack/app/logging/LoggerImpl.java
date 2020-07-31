package tr.com.minetrack.app.logging;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import tr.com.minetrack.app.logging.api.ILogger;
import tr.com.minetrack.app.logging.format_options.MyFormatter;

public class LoggerImpl implements ILogger {

	@Override
	public synchronized void keepLog(String msg) {
		this.getLogger().log(Level.SEVERE, msg);
	}

	/**
	 * tek nesne
	 */
	private static volatile LoggerImpl instance;
	private Logger logger;

	/**
	 * Double check locking
	 * 
	 * @return
	 */
	private static Object lock = new Object();

	public static LoggerImpl getInstance() {
		if (instance == null) {
			// double checked locking
			synchronized (lock) {
				if (instance == null) {
					instance = new LoggerImpl();
				}
			}
		}

		return instance;
	}

	// contructor
	private LoggerImpl() {
		try {
			// FileHandler file name with max size and number of log files limit
			Handler fileHandler = new FileHandler("./logger.log", true);
			fileHandler.setFormatter(new MyFormatter());
			logger = Logger.getLogger(LoggerImpl.class.getName());
			logger.addHandler(fileHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Logger getLogger() {
		return logger;
	}
}
