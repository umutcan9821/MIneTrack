/**
 * 
 */
package tr.com.minetrack.app.model.lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tr.com.minetrack.app.db.DAOHelper;
import tr.com.minetrack.app.model.Tracked;

/**
 * @author Gafur Hayytbayev
 *
 */
public class TrackedList {
	private static volatile TrackedList instance = null;

	private HashMap<Integer, Tracked> mapOfTracked;

	/**
	 * Double check locking
	 * 
	 * @return
	 */
	private static Object lock = new Object();

	// constructor
	private TrackedList() {
		mapOfTracked = DAOHelper.getTrackedDAO().get(null);
	}

	public static TrackedList getInstance() {

		if (instance == null) {
			// double checked locking
			synchronized (lock) {
				if (instance == null) {
					instance = new TrackedList();
				}
			}
		}

		return instance;
	}

	public HashMap<Integer, Tracked> getList() {
		return mapOfTracked;
	}

	public void add(Tracked t) {
		mapOfTracked.put(t.getTagId(), t);
	}

	public void remove(ArrayList<Integer> tagIdList) {
		if (DAOHelper.getTrackedDAO().delete(tagIdList)) {
			for (Integer key : tagIdList) {
				mapOfTracked.remove(key);
			}
		}
	}

	public void update(Tracked t) {
		mapOfTracked.put(t.getTagId(), t);
	}

	public int getTidByNameSurname(String fname, String lname) {
		int tid = -1;
		String name, surname;

		for (Map.Entry<Integer, Tracked> entry : mapOfTracked.entrySet()) {
			Tracked value = entry.getValue();

			name = value.getFname();
			surname = value.getLname();
			if (name.equals(fname) && surname.equals(lname)) {
				tid = value.getTagId();
				break;
			}
		}

		return tid;
	}
}
