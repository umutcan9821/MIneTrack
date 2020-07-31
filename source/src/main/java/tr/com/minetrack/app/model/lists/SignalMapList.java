/**
 * 
 */
package tr.com.minetrack.app.model.lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import tr.com.minetrack.app.db.DAOHelper;
import tr.com.minetrack.app.model.SignalMap;

/**
 * @author Gafur Hayytbayev
 *
 */
public class SignalMapList {

	private HashMap<String, SignalMap> mapOfSignalMap = new LinkedHashMap<>();

	/**
	 * tek nesne
	 */
	private static volatile SignalMapList signalMapInstance = null;

	/**
	 * Double check locking
	 * 
	 * @return
	 */
	private static Object lock = new Object();

	public static SignalMapList getInstance() {
		if (signalMapInstance == null) {
			// double checked locking
			synchronized (lock) {
				if (signalMapInstance == null) {
					signalMapInstance = new SignalMapList();
				}
			}
		}

		return signalMapInstance;
	}

	/**
	 * 
	 */
	private SignalMapList() {
		mapOfSignalMap = DAOHelper.getSignalMapDAO().get(null);
	}

	public HashMap<String, SignalMap> getList() {
		mapOfSignalMap = DAOHelper.getSignalMapDAO().get(null);
		return mapOfSignalMap;
	}

	public boolean remove(ArrayList<String> pidPlusRidList) {
		if (DAOHelper.getSignalMapDAO().delete(pidPlusRidList)) {
			for (String key : pidPlusRidList) {
				mapOfSignalMap.remove(key);
			}
			return true;
		}
		return false;
	}

	public boolean add(SignalMap signalmap) {
		if (DAOHelper.getSignalMapDAO().insert(signalmap)) {
			mapOfSignalMap.put("" + signalmap.getPid() + "-" + signalmap.getRid(), signalmap);
			return true;
		}
		return false;
	}

	public boolean update(SignalMap signalMap) {
		if (DAOHelper.getSignalMapDAO().update(signalMap, null)) {
			mapOfSignalMap.put("" + signalMap.getPid() + "-" + signalMap.getRid(), signalMap);
			return true;
		}
		return false;
	}
}
