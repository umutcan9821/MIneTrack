package tr.com.minetrack.app.model;

import org.joda.time.DateTime;

public class RFIDReader {

	private int readerId;
	private String readerName;

	private double x;
	private double y;

	private int gate; // if 1 is gate, 0 is not

	private boolean state;

	private DateTime dt; // for storing current time

	// constructor
	public RFIDReader(int id, double x, double y) {
		this.readerId = id;
		this.x = x;
		this.y = y;
	}

	// setters and getters for x and y
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	// getter for id
	public int getId() {
		return this.readerId;
	}

	public void setStatus(boolean b) {
		this.state = b;
	}

	public boolean getStatus() {
		return this.state;
	}

	public String getName() {
		return this.readerName;
	}

	public void setName(String name) {
		readerName = name;
	}

	public int getGate() {
		return gate;
	}

	public void setGate(int gate) {
		this.gate = gate;
	}

	public DateTime getDt() {
		return dt;
	}

	public void setDt(DateTime dt) {
		this.dt = dt;
	}
}
