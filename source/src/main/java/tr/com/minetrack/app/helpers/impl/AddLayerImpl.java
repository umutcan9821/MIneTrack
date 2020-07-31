package tr.com.minetrack.app.helpers.impl;

import java.io.File;
import java.io.IOException;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;

import tr.com.minetrack.app.helpers.interfaces.AddLayerI;
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;

public class AddLayerImpl implements AddLayerI {
	private File file;
	private MapContent content;

	public AddLayerImpl(File file, MapContent mapContent) {
		this.file = file;
		this.content = mapContent;
	}

	@Override
	public void add() {
		FileDataStore store;
		try {
			store = FileDataStoreFinder.getDataStore(file);
			SimpleFeatureSource featureSource = store.getFeatureSource();

			Style style = SLD.createSimpleStyle(featureSource.getSchema());

			Layer layer = new FeatureLayer(featureSource, style);
			if (content.addLayer(layer)) {
				System.out.println("Layer added!");
			}
		} catch (IOException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
	}

}
