/**
 * 
 */
package tr.com.minetrack.app.positioning;

import java.util.ArrayList;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;

import tr.com.minetrack.app.helpers.MyPoint;
import tr.com.minetrack.app.helpers.TrackedLayer;

/**
 * @author Gafur Hayytbayev
 *
 */

public class Simulation {

	public static void play(MyPoint oldPoint, GeometryFactory geomFactory, TrackedLayer trackedLayer,
			SimpleFeature feature, ArrayList<MyPoint> signalPoints, MyPoint newPoint) {

		Point point;
		double newx, newy;
		int oldindex = oldPoint.getIndex();
		int newindex = newPoint.getIndex();

		if (oldindex <= newindex) {
			for (int i = oldindex + 1; i <= newindex; i++) {

				MyPoint mypoint = signalPoints.get(i);
				newx = mypoint.getX();
				newy = mypoint.getY();

				point = geomFactory.createPoint(new Coordinate(newx, newy));

				feature.setAttribute("point", point);
				feature.setAttribute("MyPoint", mypoint);

				trackedLayer.updated();
			}
		} else {
			for (int i = oldindex - 1; i >= newindex; i--) {
				MyPoint mypoint = signalPoints.get(i);
				newx = mypoint.getX();
				newy = mypoint.getY();

				point = geomFactory.createPoint(new Coordinate(newx, newy));

				feature.setAttribute("point", point);
				feature.setAttribute("MyPoint", mypoint);

				trackedLayer.updated();
			}
		}
	}
}
