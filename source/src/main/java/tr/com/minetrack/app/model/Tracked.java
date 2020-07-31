/**
 * 
 */
package tr.com.minetrack.app.model;

/**
 * @author Gafur Hayytbayev
 *
 */
public class Tracked {

	private String fname;
	private String lname;
	private int tagId;
	private boolean state;
	private double x;
	private double y;
	private String konum;
	private Signal prevSignal;
	private int prevPointIndex;
//	private int solSag; // +1 ise soldan geldi, -1 ise sagdan geldi

	public Tracked(String fname, String lname, int tagId) {
		this.fname = fname;
		this.lname = lname;
		this.tagId = tagId;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
		result = prime * result + tagId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tracked))
			return false;
		Tracked other = (Tracked) obj;
		if (fname == null) {
			if (other.fname != null)
				return false;
		} else if (!fname.equals(other.fname))
			return false;
		if (lname == null) {
			if (other.lname != null)
				return false;
		} else if (!lname.equals(other.lname))
			return false;
		if (tagId != other.tagId)
			return false;
		return true;
	}

	public String getKonum() {
		return konum;
	}

	public void setKonum(String konum) {
		this.konum = konum;
	}

	public Signal getPrevSignal() {
		return prevSignal;
	}

	public void setPrevSignal(Signal prevSignal) {
		this.prevSignal = prevSignal;
	}

	public int getPrevPointIndex() {
		return prevPointIndex;
	}

	public void setPrevPointIndex(int prevPointIndex) {
		this.prevPointIndex = prevPointIndex;
	}

//	public int getSolSag() {
//		return solSag;
//	}
//
//	public void setSolSag(int solSag) {
//		this.solSag = solSag;
//	}

	@Override
	public String toString() {
		return fname + " " + lname;
	}
}
