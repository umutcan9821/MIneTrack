package tr.com.minetrack.app.helpers;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureIterator;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.DisplacementImpl;
import org.geotools.styling.Font;
import org.geotools.styling.PointPlacementImpl;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.TextSymbolizer;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.FilterFactory;

import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.model.RFIDReader;
import tr.com.minetrack.app.model.lists.RFIDReaderList;
import tr.com.minetrack.app.model.lists.SignalMapList;

public class FileOp {

	// kurulum icin
	private final static String MAP_PATH = "data/maps/harita.shp";
	private final static String READER_PATH = "data/maps/readers.shp";
	private final static String SIGNAL_PATH = "data/maps/sinyal.shp";

	static StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
	static FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();

	public static void loadFiles(MapContent map) {

		// harita
		File file1 = new File(MAP_PATH);
		FileDataStore store;
		try {
			store = FileDataStoreFinder.getDataStore(file1);
			SimpleFeatureSource featureSource = store.getFeatureSource();

			Style style = SLD.createSimpleStyle(featureSource.getSchema());

			Layer layer = new FeatureLayer(featureSource, style);
			map.addLayer(layer);
		} catch (IOException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}

		// readers
		File file2 = new File(READER_PATH);
		FileDataStore store2;
		try {
			store2 = FileDataStoreFinder.getDataStore(file2);
			SimpleFeatureSource featureSource = store2.getFeatureSource();

			setReaderList(featureSource);

			// label for readers
			Style style = createStyleForReaders();

			Layer layer = new FeatureLayer(featureSource, style);
			map.addLayer(layer);
		} catch (IOException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}

		// signals
		File file3 = new File(SIGNAL_PATH);
		FileDataStore store3;
		try {
			store3 = FileDataStoreFinder.getDataStore(file3);
			SimpleFeatureSource featureSource = store3.getFeatureSource();

			setPointList(featureSource);

			Style style = SLD.createSimpleStyle(featureSource.getSchema());
			Layer layer = new FeatureLayer(featureSource, style);
			layer.setVisible(false);
			map.addLayer(layer);
		} catch (IOException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}

		// konum
		map.addLayer(MapOperations.createEmptyLayer());
	}

	private static Style createStyleForReaders() {

		// label for readers
		StyleBuilder styleBuilder = new StyleBuilder();
		String attributeName = "Location";
		Font font = styleBuilder.createFont("Times New Roman", 13.0);
		TextSymbolizer textSymb = styleBuilder.createTextSymbolizer(Color.black, font, attributeName);

		PointPlacementImpl pointPlacement = new PointPlacementImpl();
		DisplacementImpl displacement = new DisplacementImpl();
		displacement.setDisplacementX(10.0);
		displacement.setDisplacementY(10.0);
		pointPlacement.setDisplacement(displacement);
		textSymb.setLabelPlacement(pointPlacement);

		Rule rule = styleBuilder.createRule(textSymb);

		Style style = SLD.createPointStyle("Triangle", Color.BLACK, Color.GREEN, 0.3f, 15);
		// apply rule
		style.featureTypeStyles().get(0).rules().add(rule);
		return style;
		// end
	}

	private static void setReaderList(SimpleFeatureSource featureSource) throws IOException {
		RFIDReaderList readerListInstance = RFIDReaderList.getInstance();

		FeatureIterator<SimpleFeature> featureIterator = featureSource.getFeatures().features();

		SimpleFeature feature;
		List<?> list;
		int id;
		RFIDReader r;
		while (featureIterator.hasNext()) {
			feature = featureIterator.next();
			/*
			 * System.out.print(feature.getID()); System.out.print(": ");
			 */

			list = feature.getAttributes();
			id = Integer.parseInt(list.get(1).toString());

			r = new RFIDReader(id, Double.parseDouble(list.get(2).toString()),
					Double.parseDouble(list.get(3).toString()));
			r.setGate(Integer.parseInt(list.get(4).toString()));
			r.setName(list.get(5).toString());
			readerListInstance.putToMap(id, r);
		}
		if (featureIterator != null)
			featureIterator.close();
	}

	private static void setPointList(SimpleFeatureSource featureSource) throws IOException {
		ArrayList<MyPoint> pointList = PointList.getInstance().getList();

		FeatureIterator<SimpleFeature> featureIterator = featureSource.getFeatures().features();

		SimpleFeature feature;
		List<?> list;
		int index;
		double x, y;
		MyPoint p;
		while (featureIterator.hasNext()) {
			feature = featureIterator.next();
			/*
			 * System.out.print(feature.getID()); System.out.print(": ");
			 */
			list = feature.getAttributes();
			index = Integer.parseInt(list.get(1).toString());
			x = Double.parseDouble(list.get(2).toString());
			y = Double.parseDouble(list.get(3).toString());

			p = new MyPoint(x, y, index);

			// set all the signalmap from db to mypoint object

			HashMap<Integer, RFIDReader> ls = RFIDReaderList.getInstance().getList();
			Iterator<Integer> it = ls.keySet().iterator();
			while (it.hasNext()) {
				int readerID = it.next();
				String key = "" + index + "-" + readerID;

				if (SignalMapList.getInstance().getList().containsKey(key)) {
					int minrssi = SignalMapList.getInstance().getList().get(key).getMinrssi();
					int maxrssi = SignalMapList.getInstance().getList().get(key).getMaxrssi();

					p.setRssiMap(readerID, minrssi, maxrssi);
				}

			}

			pointList.add(p);
		}
		if (featureIterator != null)
			featureIterator.close();
	}
}
