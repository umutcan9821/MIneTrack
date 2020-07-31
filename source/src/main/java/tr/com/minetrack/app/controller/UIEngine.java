package tr.com.minetrack.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import org.joda.time.DateTime;

import tr.com.minetrack.app.model.DataTerminal;
import tr.com.minetrack.app.model.License;
import tr.com.minetrack.app.model.lists.RFIDReaderList;
import tr.com.minetrack.app.threads.ThreadManager;
import tr.com.minetrack.app.view.UI;

//Controller Class
public final class UIEngine implements ActionListener {
	private final UI parent;

	public UIEngine(final UI parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		String name;
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();
			name = btn.getName();
		} else {
			JMenuItem clickedMenu = (JMenuItem) e.getSource();
			name = clickedMenu.getName();
		}

		switch (name) {
		// File menu options
		case "addlayer":
			parent.openFileDialog();
			break;
		case "savefile":
			//System.out.println("Saving File...");
			break;
		case "exit":
			parent.exitApp();
			break;
		// Status bar options
		case "terminal":
			parent.showTerminalStatus(DataTerminal.getInstance().getState());
			break;
		case "rfid":
			parent.showReaderStatus(RFIDReaderList.getInstance().getList());
			break;
		// add employee and machine
		case "emlpoyee":
			//System.out.println("add employee");
			parent.addEmployeeView();
			break;
		case "machines":
			//System.out.println("add machines");
			parent.addMachineView();
			break;
		// reports
		case "dailyreport":
			//System.out.println("dailyreport");
			parent.showDailyReport();
			break;
		case "personalreport":
			//System.out.println("personalreport");
			parent.showPersonalReport();
			break;
		case "detailedreport":
			//System.out.println("detailedreport");
			parent.showDetailedReport();
			break;
		// administrator menu
		case "admin":
			//System.out.println("admin");
			parent.showAdminView();
			break;
		case "signalmap":
			//System.out.println("settings");
			parent.showSettingsView();
			break;
		case "updatelicense":
			//System.out.println("update license");
			parent.showLicenseView();
			// static pages
		case "welcome":
			//System.out.println("welcome");
			break;
		case "usermanual":
			//System.out.println("usermanual");
			break;
		case "contact":
			//System.out.println("contact");
			parent.showContactView();
			break;
		case "license":
			//System.out.println("license");
			parent.showLicenseInfoView();
			break;
//		case "zoom-to-layer":
//			System.out.println("zoom to layer");
//			try {
//				zoomToLayer();
//			} catch (TransformException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (FactoryException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			break;
		case "startbtn":
			//System.out.println("start button pressed");
			License license = License.getInstance();
			if (license.licenseExpired(new DateTime())) {
				parent.disableStartBtn();
				ThreadManager.startServices(parent);
			} else {
				parent.showLicenseView();
			}

			break;
		case "stopbtn":
			//System.out.println("stop button pressed");
			parent.disableStopBtn();
			ThreadManager.stopService(parent);
			break;
		default:
			//System.out.println("Default case...");
			break;
		}
	}

//	private void zoomToLayer() throws TransformException, FactoryException {
//		MapContent content = parent.getFrame().getMapContent();
//		MapViewport viewport = content.getViewport();
//		ReferencedEnvelope maxBounds = null;
//		CoordinateReferenceSystem mapCRS = viewport.getCoordinateReferenceSystem();
//
//		for (Layer layer : content.layers()) {
//		    ReferencedEnvelope dataBounds = layer.getBounds();
//		    //CoordinateReferenceSystem dataCrs = dataBounds.getCoordinateReferenceSystem();
//		    
//		        //dataBounds = dataBounds.transform(mapCRS, true);
//		    
//		    if (maxBounds == null) {
//		        maxBounds = dataBounds;
//		    } else {
//		        maxBounds.expandToInclude(dataBounds);
//		    }
//		}
//		viewport.setBounds( maxBounds );
//		content.setViewport(viewport);
//	}

}
