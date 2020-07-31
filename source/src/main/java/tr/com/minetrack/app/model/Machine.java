/**
 * 
 */
package tr.com.minetrack.app.model;

/**
 * @author Gafur Hayytbayev
 *
 */
public class Machine extends Tracked {
	private long machineNo;
	private String role;

	public Machine(long machineNo, String fname, String lname, String role, int tagId) {
		super(fname, lname, tagId);
		this.role = role;
		this.machineNo = machineNo;
	}

	public long getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(long machineNo) {
		this.machineNo = machineNo;
	}

	public String getRole() {
		return role;
	}
}
