package tr.com.minetrack.app.helpers;

public class RidAndTid {
	private int rid;
	private int tid;

	public RidAndTid(int rid, int tid) {
		super();
		this.rid = rid;
		this.tid = tid;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rid;
		result = prime * result + tid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RidAndTid other = (RidAndTid) obj;
		if (rid != other.rid)
			return false;
		if (tid != other.tid)
			return false;
		return true;
	}

//    @Override
//    public boolean equals(Object o)
//    {
//	if (this == o) return true;        
//        if (o == null || getClass() != o.getClass()) return false;
//	RidAndTid obj = (RidAndTid) o;
//        return rid == obj.getRid() && tid == obj.getTid();
//    }

}
