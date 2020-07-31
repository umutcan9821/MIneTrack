package tr.com.minetrack.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import tr.com.minetrack.app.view.dialogs.LicenseView;

public class LicenseViewController implements ActionListener {

	private final LicenseView parent;

	/**
	 * 
	 */
	public LicenseViewController(final LicenseView view) {
		this.parent = view;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		JButton jButton = (JButton) e.getSource();

		String name = jButton.getName();
		switch (name) {
		case "close":
			//System.out.println("close this dialog");
			parent.closeThisDialog();
			break;
		case "changeLicense":
			//System.out.println("changeLicense");
			parent.showUpdateLicenseView();
			break;
		default:
			//System.out.println("default case");
		}
	}

}
