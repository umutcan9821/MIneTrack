package tr.com.minetrack.app.model;

public class Employee extends Tracked {
	private long tcno;
	private String role;

	public Employee(long tcno, String fname, String lname, String role, int tagId) {
		super(fname, lname, tagId);
		this.role = role;
		this.tcno = tcno;
	}

	public long getTcno() {
		return tcno;
	}

	public void setTcno(long tcno) {
		this.tcno = tcno;
	}

	public String getRole() {
		return role;
	}
}
