package tr.com.minetrack.app.model;

import org.joda.time.DateTime;

public class Signal {
	private int rssi;
	private DateTime dt;
	private int rid;
	private int tid;

	public Signal(int rssi, DateTime dt, int rid, int tid) {
		this.rssi = rssi;
		this.dt = dt;
		this.rid = rid;
		this.tid = tid;
	}

	public Signal(DateTime dt, int rid, int tid) {
		this.dt = dt;
		this.rid = rid;
		this.tid = tid;
	}

	// getters

	public int getRssi() {
		return rssi;
	}

	public DateTime getDt() {
		return dt;
	}

	public void setDt(DateTime dt) {
		this.dt = dt;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String toString() {
		// return rid + " - " + tid + " - " + rssi + " - " + dt;
		return rid + "," + tid + "," + rssi;
	}

}
