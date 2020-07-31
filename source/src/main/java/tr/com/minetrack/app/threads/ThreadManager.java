package tr.com.minetrack.app.threads;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import tr.com.minetrack.app.helpers.MapOperations;
import tr.com.minetrack.app.helpers.RidAndTid;
import tr.com.minetrack.app.helpers.TrackedLayer;
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.model.DataTerminal;
import tr.com.minetrack.app.model.RFIDReader;
import tr.com.minetrack.app.model.Signal;
import tr.com.minetrack.app.model.Tracked;
import tr.com.minetrack.app.model.lists.RFIDReaderList;
import tr.com.minetrack.app.model.lists.SignalList;
import tr.com.minetrack.app.model.lists.TrackedList;
import tr.com.minetrack.app.view.UI;

public class ThreadManager {
	private static Timer timer;
	private static TimerTask timerTask;

	private static Thread reader;

//	private static Thread writer;			// ---------------------------------------------passif icin

	public static void startServices(UI parent) {

		// connect to data terminal
		DataTerminal.getInstance().connect();
		if (DataTerminal.getInstance().getState()) {
			SignalList.getInstance().setParent(parent);
			timer = new Timer();
			// create timer task
			timerTask = new TimerTask() {

				@Override
				public void run() {
					// en son kapıdan almıssa ve 5 dk icinde sinyal almamıssa sil
					MapOperations.refreshTrackedLayer(parent);
				}
			};
			// Schedule to run after every 5 minut(240000 millisecond)
			timer.scheduleAtFixedRate(timerTask, 0, 240000);
//			writer = new Thread(new WriteService());				//------------------------------------passif icin
//			writer.start();											//------------------------------------passif icin
			reader = new Thread(new ReadService());
			reader.start();
			parent.enableStopBtn();
		} else {
			parent.enableStartBtn();
		}
	}

	public static void stopService(UI parent) {
		// update state of terminal
		DataTerminal.getInstance().disconnect();

		// clear signal list
		Map<RidAndTid, Signal> mapOfSignalList = SignalList.getInstance().getSignalMap();
		mapOfSignalList.clear();

		// update state of tracked
		HashMap<Integer, Tracked> mapOfTracked = TrackedList.getInstance().getList();
		for (Integer key : mapOfTracked.keySet()) {
			Tracked tr = mapOfTracked.get(key);
			tr.setState(false);
			tr.setKonum("");
			tr.setPrevPointIndex(0);
			tr.setPrevSignal(null);
		}

		// update state of readers
		HashMap<Integer, RFIDReader> mapOfReaders = RFIDReaderList.getInstance().getList();
		for (Integer key : mapOfReaders.keySet()) {
			RFIDReader r = mapOfReaders.get(key);
			r.setStatus(false);
		}

		// delete employees from employeelayer
		TrackedLayer konumLayer = MapOperations.getTrackedLayer();
		MapOperations.getList().clear();
		konumLayer.updated();

		// clear employee table
		parent.getFrame().clearTrackedModel();

		// timer.cancel();
		try {
//			writer.join();				//------------------------------------------------passif icin
			reader.join();
			parent.enableStartBtn();
		} catch (InterruptedException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
	}
}