package tr.com.minetrack.app.helpers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.geotools.feature.FeatureCollection;
import org.geotools.map.FeatureLayer;
import org.geotools.map.MapLayerEvent;
import org.geotools.styling.Style;

public class TrackedLayer extends FeatureLayer {

	private Lock updateMapLock = new ReentrantLock();

	public TrackedLayer(FeatureCollection<?, ?> collection, Style style) {
		super(collection, style);
	}

	public void updated() {
		updateMapLock.lock();
		fireMapLayerListenerLayerChanged(MapLayerEvent.DATA_CHANGED);
		updateMapLock.unlock();
	}
}
