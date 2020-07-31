package tr.com.minetrack.app.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.geotools.map.MapContent;
import org.geotools.swing.data.JFileDataStoreChooser;

import tr.com.minetrack.app.controller.UIEngine;
import tr.com.minetrack.app.helpers.Login;
import tr.com.minetrack.app.helpers.impl.AddLayerImpl;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.RFIDReader;
import tr.com.minetrack.app.view.dialogs.AddEmployeeView;
import tr.com.minetrack.app.view.dialogs.AddMachineView;
import tr.com.minetrack.app.view.dialogs.AdminView;
import tr.com.minetrack.app.view.dialogs.ContactView;
import tr.com.minetrack.app.view.dialogs.DailyReportView;
import tr.com.minetrack.app.view.dialogs.DetailedReportView;
import tr.com.minetrack.app.view.dialogs.LicenseInfoView;
import tr.com.minetrack.app.view.dialogs.LicenseView;
import tr.com.minetrack.app.view.dialogs.PersonalReportView;
import tr.com.minetrack.app.view.dialogs.SettingsView;
import tr.com.minetrack.app.view.frames.MineTrackFrame;
import tr.com.minetrack.app.view.msgdialogs.ReaderStatus;
import tr.com.minetrack.app.view.msgdialogs.TerminalStatus;

/**
 * 
 * @author Gafur Hayytbayev
 *
 */

// View Class
public final class UI {

	private MineTrackFrame mapFrame;
	private MapContent map;
	private JMenuBar menuBar;

	private JMenuItem signalMapSubmenu;
	private JMenuItem updateLicenseSubmenu;

	private JButton btnStart;
	private JButton btnStop;

	// contructor
	public UI(MapContent map) {
		this.map = map;
		init();

	}// end contructor------------------------------------------------

	/**
	 * 
	 */
	private void init() {

		mapFrame = new MineTrackFrame();
		ImageIcon img = new ImageIcon("data/images/tracking.png");
		mapFrame.setIconImage(img.getImage());
		mapFrame.setMapContent(map);

		mapFrame.setTitle(Messages.getString("UI.slogan")); //$NON-NLS-1$
		mapFrame.enableToolBar(true);
		mapFrame.enableStatusBar(true);
		mapFrame.enableLayerTable(true);
		mapFrame.enablePersonelTable(true);

		menuBar = new JMenuBar();
		mapFrame.setJMenuBar(menuBar);

		// create instance for UI Control class
		UIEngine uiControl = new UIEngine(this);

		// file menu bar---------------------------------------------------
		JMenu fileMenu = new JMenu(Messages.getString("UI.minetrack")); //$NON-NLS-1$

		// adding all submenus for status bar
		JMenuItem addLayerSubmenu = new JMenuItem(Messages.getString("UI.addLayer")); //$NON-NLS-1$
		addLayerSubmenu.setName("addlayer"); //$NON-NLS-1$
		JMenuItem exitSubmenu = new JMenuItem(Messages.getString("UI.exitapp")); //$NON-NLS-1$
		exitSubmenu.setName("exit"); //$NON-NLS-1$

		// add action listener
		addLayerSubmenu.addActionListener(uiControl);
		exitSubmenu.addActionListener(uiControl);

//	fileMenu.add(addLayerSubmenu); !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! daha sonra eklenecek
		fileMenu.addSeparator();
		fileMenu.add(exitSubmenu);
		menuBar.add(fileMenu);
		// -----------------------------------------------------------------

		// status menu bar--------------------------------------------------
		JMenu stateMenu = new JMenu(Messages.getString("UI.stateOf")); //$NON-NLS-1$

		// adding all submenus
		JMenuItem dataTerminalSubmenu = new JMenuItem(Messages.getString("UI.terminal")); //$NON-NLS-1$
		dataTerminalSubmenu.setName("terminal"); //$NON-NLS-1$
		JMenuItem rfidReaderSubmenu = new JMenuItem(Messages.getString("UI.reader")); //$NON-NLS-1$
		rfidReaderSubmenu.setName("rfid"); //$NON-NLS-1$

		// add action listener
		dataTerminalSubmenu.addActionListener(uiControl);
		rfidReaderSubmenu.addActionListener(uiControl);

		stateMenu.add(dataTerminalSubmenu);
		stateMenu.add(rfidReaderSubmenu);

		menuBar.add(stateMenu);
		// -------------------------------------------------------------------

		// add data menu bar--------------------------------------------------
		JMenu addMenu = new JMenu(Messages.getString("UI.add")); //$NON-NLS-1$

		// adding all submenus
		JMenuItem employeeSubmenu = new JMenuItem(Messages.getString("UI.employee")); //$NON-NLS-1$
		employeeSubmenu.setName("emlpoyee"); //$NON-NLS-1$
		JMenuItem machinesSubmenu = new JMenuItem(Messages.getString("UI.machines")); //$NON-NLS-1$
		machinesSubmenu.setName("machines"); //$NON-NLS-1$

		// add action listener
		employeeSubmenu.addActionListener(uiControl);
		machinesSubmenu.addActionListener(uiControl);

		addMenu.add(employeeSubmenu);
		addMenu.add(machinesSubmenu);

		menuBar.add(addMenu);
		// -------------------------------------------------------------------

		// reports menu bar--------------------------------------------------
		JMenu reportMenu = new JMenu(Messages.getString("UI.reports")); //$NON-NLS-1$

		// adding all submenus
		JMenuItem dailyReportSubmenu = new JMenuItem(Messages.getString("UI.dailyReports")); //$NON-NLS-1$
		dailyReportSubmenu.setName("dailyreport"); //$NON-NLS-1$
		JMenuItem personalReportSubmenu = new JMenuItem(Messages.getString("UI.personelReport")); //$NON-NLS-1$
		personalReportSubmenu.setName("personalreport"); //$NON-NLS-1$
		JMenuItem detailedReportSubmenu = new JMenuItem(Messages.getString("UI.detailedReport")); //$NON-NLS-1$
		detailedReportSubmenu.setName("detailedreport"); //$NON-NLS-1$

		// add action listener
		dailyReportSubmenu.addActionListener(uiControl);
		personalReportSubmenu.addActionListener(uiControl);
		detailedReportSubmenu.addActionListener(uiControl);

		reportMenu.add(dailyReportSubmenu);
		reportMenu.add(personalReportSubmenu);
		reportMenu.add(detailedReportSubmenu);

		menuBar.add(reportMenu);
		// -------------------------------------------------------------------

		// settings menu bar--------------------------------------------------
		JMenu settingsMenu = new JMenu(Messages.getString("UI.settings")); //$NON-NLS-1$

		// adding all submenus
		JMenuItem adminSubmenu = new JMenuItem(Messages.getString("UI.admin")); //$NON-NLS-1$
		adminSubmenu.setName("admin");
		signalMapSubmenu = new JMenuItem(Messages.getString("UI.changeSignalMap")); //$NON-NLS-1$
		signalMapSubmenu.setName("signalmap"); //$NON-NLS-1$
		signalMapSubmenu.setEnabled(false);

		updateLicenseSubmenu = new JMenuItem(Messages.getString("UI.updateLicense")); //$NON-NLS-1$
		updateLicenseSubmenu.setName("updatelicense"); //$NON-NLS-1$
		updateLicenseSubmenu.setEnabled(false);

		// add action listener
		adminSubmenu.addActionListener(uiControl);
		signalMapSubmenu.addActionListener(uiControl);
		updateLicenseSubmenu.addActionListener(uiControl);

		settingsMenu.add(adminSubmenu);
		settingsMenu.add(signalMapSubmenu);
		settingsMenu.add(updateLicenseSubmenu);

		menuBar.add(settingsMenu);
		// -------------------------------------------------------------------

		// help menu bar--------------------------------------------------
		JMenu helpMenu = new JMenu(Messages.getString("UI.help")); //$NON-NLS-1$

		// adding all submenus
		JMenuItem userManualSubmenu = new JMenuItem(Messages.getString("UI.usermanual")); //$NON-NLS-1$
		userManualSubmenu.setName("usermanual"); //$NON-NLS-1$
		JMenuItem licenseSubmenu = new JMenuItem(Messages.getString("UI.license")); //$NON-NLS-1$
		licenseSubmenu.setName("license"); //$NON-NLS-1$
		JMenuItem aboutSubmenu = new JMenuItem(Messages.getString("UI.contact")); //$NON-NLS-1$
		aboutSubmenu.setName("contact"); //$NON-NLS-1$

		// add action listener
		userManualSubmenu.addActionListener(uiControl);
		licenseSubmenu.addActionListener(uiControl);
		aboutSubmenu.addActionListener(uiControl);

//	helpMenu.add(userManualSubmenu); !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1 daha sonra eklenecek
		helpMenu.add(licenseSubmenu);
//	helpMenu.add(aboutSubmenu); !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! daha sonra eklenecek

		menuBar.add(helpMenu);
		// -------------------------------------------------------------------

		// start and stop toolbar ---------------------------------------------
		JToolBar toolBar = mapFrame.getToolBar();

//		ImageIcon zoomToLayer = new ImageIcon("data/images/zoom-out.png"); //$NON-NLS-1$
//		
//		JButton btnZoomToLayer = new JButton(zoomToLayer);
//		toolBar.add(btnZoomToLayer);
//		btnZoomToLayer.setName("zoom-to-layer"); //$NON-NLS-1$
//		btnZoomToLayer.setToolTipText(Messages.getString("UI.zoom-to-layer")); //$NON-NLS-1$
//		btnZoomToLayer.addActionListener(uiControl);
		
		ImageIcon iconStart = new ImageIcon("data/images/start_btn.png"); //$NON-NLS-1$

		btnStart = new JButton(iconStart);

		toolBar.addSeparator();
		toolBar.add(btnStart);
		btnStart.setName("startbtn"); //$NON-NLS-1$
		btnStart.setToolTipText(Messages.getString("UI.start")); //$NON-NLS-1$
		btnStart.addActionListener(uiControl);

		ImageIcon stopIcon = new ImageIcon("data/images/stop_btn.png"); //$NON-NLS-1$
		btnStop = new JButton(stopIcon);

		//toolBar.addSeparator();
		toolBar.add(btnStop);
		btnStop.setName("stopbtn"); //$NON-NLS-1$
		btnStop.setToolTipText(Messages.getString("UI.stop")); //$NON-NLS-1$
		btnStop.addActionListener(uiControl);
		disableStopBtn();
		// ----------------------------------------------------------------------------

		Toolkit toolkit = Toolkit.getDefaultToolkit();

		Dimension screenSize = toolkit.getScreenSize();
		Insets insets = toolkit.getScreenInsets(mapFrame.getGraphicsConfiguration());
		screenSize.width -= (insets.left + insets.right);
		screenSize.height -= (insets.top + insets.bottom);
		mapFrame.setSize(screenSize.width, screenSize.height);

		mapFrame.setLocationRelativeTo(null);
		mapFrame.setVisible(true);

	}

	// getters and setters--------------------------------------------
	public MineTrackFrame getFrame() {
		return mapFrame;
	}

	public void enableStartBtn() {
		btnStart.setEnabled(true);
	}

	public void disableStartBtn() {
		btnStart.setEnabled(false);
	}

	public void enableStopBtn() {
		btnStop.setEnabled(true);
	}

	public void disableStopBtn() {
		btnStop.setEnabled(false);
	}
	// end getters and setters----------------------------------------

	// methods for controls-------------------------------------------
	public void openFileDialog() {
		File file = JFileDataStoreChooser.showOpenFile("shp", null); //$NON-NLS-1$
		if (file != null) {
			// use the shapefile
			System.out.println(file.getAbsolutePath());
			new AddLayerImpl(file, map).add();
		}

	}

	public void exitApp() {
		Runtime.getRuntime().exit(0);
	}

	public void showTerminalStatus(boolean connected) {
		System.out.println(connected);
		new TerminalStatus(this, connected);
	}

	public void showReaderStatus(HashMap<Integer, RFIDReader> readers) {
		new ReaderStatus(this, readers);
	}
	// ----------------------------------------------------------------

	public void addEmployeeView() {
		new AddEmployeeView(this.getFrame());
	}

	public void addMachineView() {
		new AddMachineView(this.getFrame());
	}

	public void showDailyReport() {
		new DailyReportView(this.getFrame());
	}

	public void showPersonalReport() {
		new PersonalReportView(this.getFrame());
	}

	public void showAdminView() {
		if (!Login.loggedIn) {
			AdminView av = new AdminView(this.getFrame());
			if (av.isSucceeded()) {
				setEnabledSettingsubmenu();
			}
		} else {
			JOptionPane.showMessageDialog(this.getFrame(), Messages.getString("UI.entered"),
					Messages.getString("UI.login"), JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void setEnabledSettingsubmenu() {
		signalMapSubmenu.setEnabled(true);
		updateLicenseSubmenu.setEnabled(true);
	}

	public void showSettingsView() {
		new SettingsView(this.getFrame());
	}

	public void showLicenseView() {
		LicenseView licenseview = new LicenseView(this.getFrame(), true);
		licenseview.setVisible(true);
	}

	public void showContactView() {
		new ContactView(this.getFrame());
	}

	public void showLicenseInfoView() {
		LicenseView licenseinfoview = new LicenseInfoView(this.getFrame(), true);
		licenseinfoview.setVisible(true);
	}

	public void showDetailedReport() {
		new DetailedReportView(this.getFrame());
	}
}
