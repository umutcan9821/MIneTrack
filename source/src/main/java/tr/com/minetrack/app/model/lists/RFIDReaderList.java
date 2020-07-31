package tr.com.minetrack.app.model.lists;

import java.util.HashMap;

import tr.com.minetrack.app.model.RFIDReader;

public class RFIDReaderList {
	private final static RFIDReaderList readerListInstance = new RFIDReaderList();

	private HashMap<Integer, RFIDReader> mapOfReaders = new HashMap<>();

	// private constructor
	private RFIDReaderList() {
	}

	public static RFIDReaderList getInstance() {
		return readerListInstance;
	}

	public HashMap<Integer, RFIDReader> getList() {
		return mapOfReaders;
	}

	public void putToMap(int id, RFIDReader r) {
		mapOfReaders.put(id, r);
	}
}
