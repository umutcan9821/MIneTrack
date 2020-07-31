package tr.com.minetrack.app.helpers.impl;

import tr.com.minetrack.app.helpers.interfaces.LicenseManagerI;

public class LicenseManagerImpl implements LicenseManagerI {
	boolean activated;

	@Override
	public void activate() {

	}

	@Override
	public boolean checkLicense() {

		return activated;
	}

}
