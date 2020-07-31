package tr.com.minetrack.app.helpers;

import org.joda.time.DateTime;

public class TimeAndRid {

	private DateTime dt;
	private int rid;

	public TimeAndRid(DateTime time, int rid) {
		this.dt = time;
		this.rid = rid;
	}

	public DateTime getDt() {
		return dt;
	}

	public int getRid() {
		return rid;
	}
}
