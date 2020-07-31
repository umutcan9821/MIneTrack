package tr.com.minetrack.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import tr.com.minetrack.app.model.License;
import tr.com.minetrack.app.view.dialogs.LicenseView;
import tr.com.minetrack.app.view.dialogs.UpdateLicenseView;

public class UpdateLicenseController implements ActionListener {
	private final UpdateLicenseView parent;

	/**
	 * 
	 */
	public UpdateLicenseController(final UpdateLicenseView view) {
		this.parent = view;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		JButton jButton = (JButton) e.getSource();

		String name = jButton.getName();
		switch (name) {
		case "validate":
			//System.out.println("Validate");
			JTextField text = parent.getEnabledField();
			String strText = text.getText();
			if (strText.length() == 24) {
				boolean tf = License.getInstance().setDate(strText);

				if (tf) {
					// dispose()
					parent.dispose();
					// licenseview'i guncelle
					((LicenseView) parent.getParent()).checkLicense();
				} else {
					// hata mesaji ver
					// text
				}
			}
			break;
		default:
			//System.out.println("default case");
		}
	}

}
