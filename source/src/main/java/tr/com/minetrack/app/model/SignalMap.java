/**
 * 
 */
package tr.com.minetrack.app.model;

/**
 * @author Gafur Hayytbayev
 *
 */
public class SignalMap {

	private int pid;
	private int rid;
	private int minrssi;
	private int maxrssi;

	public SignalMap(int pid, int rid, int minrssi, int maxrssi) {
		this.setPid(pid);
		this.setRid(rid);
		this.setMinrssi(minrssi);
		this.setMaxrssi(maxrssi);
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getMinrssi() {
		return minrssi;
	}

	public void setMinrssi(int minrssi) {
		this.minrssi = minrssi;
	}

	public int getMaxrssi() {
		return maxrssi;
	}

	public void setMaxrssi(int maxrssi) {
		this.maxrssi = maxrssi;
	}

}
