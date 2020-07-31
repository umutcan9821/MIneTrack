package tr.com.minetrack.app.helpers;

import java.util.HashMap;

public class MyPoint {

	private double x;
	private double y;
	private int index;
	private HashMap<Integer, MinMaxRssi> rssiMap = new HashMap<Integer, MinMaxRssi>();

	public MyPoint(double x, double y, int index) {
		this.x = x;
		this.y = y;
		this.setIndex(index);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public MinMaxRssi getRssiMap(int readerID) {
		MinMaxRssi mp = rssiMap.get(readerID);
		if (mp != null) {
			return mp;
		} else {
			return null;
		}
	}

	public void setRssiMap(int readerID, int minrssi, int maxrssi) {
		rssiMap.put(readerID, new MinMaxRssi(minrssi, maxrssi));
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean equals(MyPoint point) {
		if (point != null) {
			if (point.getX() == x && point.getY() == y) {
				return true;
			}
		}
		return false;
	}
}
