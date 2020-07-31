package tr.com.minetrack;

import javax.swing.SwingUtilities;

import org.geotools.map.MapContent;

import tr.com.minetrack.app.helpers.FileOp;
import tr.com.minetrack.app.view.UI;

/**
 * Main class
 * 
 * @author Gafur Hayytbayev
 *
 */

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createAndShowGui();
			}

		});
	}

	public static void createAndShowGui() {
		MapContent map = new MapContent();
		FileOp.loadFiles(map);
		new UI(map);
	}
}
