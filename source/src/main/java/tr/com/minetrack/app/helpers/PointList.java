package tr.com.minetrack.app.helpers;

import java.util.ArrayList;

public class PointList {
	private static PointList instance;
	private ArrayList<MyPoint> pointList = new ArrayList<MyPoint>();

	public static PointList getInstance() {
		if (instance == null) {
			instance = new PointList();
		}
		return instance;
	}

	public static void setInstance(PointList ls) {
		instance = ls;
	}

	public void setList(ArrayList<MyPoint> list) {
		pointList = list;
	}

	public ArrayList<MyPoint> getList() {
		return pointList;
	}
}
