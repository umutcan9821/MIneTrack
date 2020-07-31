package tr.com.minetrack.app.threads;

import tr.com.minetrack.app.model.DataTerminal;

public class ReadService implements Runnable {
	@Override
	public void run() {
		while (DataTerminal.getInstance().getState()) {
			DataTerminal.getInstance().read();
		}
	}

}
