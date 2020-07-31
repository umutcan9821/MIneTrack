package tr.com.minetrack.app.helpers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.Font;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.TextSymbolizer;
import org.joda.time.DateTime;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory;

import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.RFIDReader;
import tr.com.minetrack.app.model.Signal;
import tr.com.minetrack.app.model.Tracked;
import tr.com.minetrack.app.model.lists.RFIDReaderList;
import tr.com.minetrack.app.model.lists.TrackedList;
import tr.com.minetrack.app.view.UI;

import org.locationtech.jts.geom.Point;

public class MapOperations {

	public static final int VALID_SIGNAL_DURATION_MS = 300000;
	static FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();

	private static ArrayList<SimpleFeature> list;
	private static TrackedLayer trackedLayer;

	public static Style style;
	public static SimpleFeatureType TYPE;
	public static ListFeatureCollection collection;

	public static ArrayList<SimpleFeature> getList() {
		return list;
	}

	public static TrackedLayer getTrackedLayer() {
		return trackedLayer;
	}

	public static TrackedLayer createEmptyLayer() {
		list = new ArrayList<SimpleFeature>();

		style = SLD.createPointStyle("Star", Color.BLUE, Color.BLUE, 0.3f, 10);

		SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();

		// set the name
		b.setName("Konum"); //$NON-NLS-1$

		// add some attribute
		b.add("point", Point.class); //$NON-NLS-1$
		b.add("tagid", Integer.class); //$NON-NLS-1$
		b.add("name", String.class); //$NON-NLS-1$
		b.add("track", Tracked.class); //$NON-NLS-1$
		b.add("MyPoint", MyPoint.class); //$NON-NLS-1$

		// add a geometry property
		b.setCRS(DefaultGeographicCRS.WGS84);
		b.add("location", Point.class); //$NON-NLS-1$

		// build the type
		TYPE = b.buildFeatureType();

		collection = new ListFeatureCollection(TYPE, list);

		// label for konum
		StyleBuilder styleBuilder = new StyleBuilder();
		String attributeName = "name";
		Font font = styleBuilder.createFont("Times New Roman", false, true, 13.0);
		TextSymbolizer textSymb = styleBuilder.createTextSymbolizer(Color.black, font, attributeName);
		Rule rule = styleBuilder.createRule(textSymb);
		// end
		// apply rule
		style.featureTypeStyles().get(0).rules().add(rule);

		trackedLayer = new TrackedLayer(collection, style);

		trackedLayer.setTitle(Messages.getString("MapOperations.10")); //$NON-NLS-1$

		return trackedLayer;
	}

	public static void refreshTrackedLayer(UI parent) {
		HashMap<Integer, Tracked> trackMap = TrackedList.getInstance().getList();

		int counter = 0;
		for (int trackedKey : trackMap.keySet()) {
			Tracked tracked = trackMap.get(trackedKey);
			Signal signal = tracked.getPrevSignal();
			if (signal != null) {
				try {
					int rid = signal.getRid();
					RFIDReader r = RFIDReaderList.getInstance().getList().get(rid);

					DateTime validtime = DateTime.now().minusMillis(VALID_SIGNAL_DURATION_MS);

					if (r.getGate() == 1 && validtime.isAfter(signal.getDt())) {
						boolean b = true;
						for (int i = 0; i < list.size() && b; i++) {
							SimpleFeature feature = list.get(i);
							int tid = Integer.parseInt(feature.getAttribute("tagid").toString()); //$NON-NLS-1$
							if (tid == signal.getTid()) {
								list.remove(i);
								tracked.setPrevSignal(null);
								tracked.setPrevPointIndex(0);
								tracked.setState(false);
								tracked.setKonum("");
								counter++;
							}
						}
					}
				} catch (Exception e) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
				}
			}
		}
		if (counter != 0) {
			trackedLayer.updated();
			parent.getFrame().setTrackedModel(trackMap);
		}
	}
}
