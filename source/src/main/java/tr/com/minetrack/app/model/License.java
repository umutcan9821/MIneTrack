package tr.com.minetrack.app.model;

import java.util.Base64;
import java.util.HashMap;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import tr.com.minetrack.app.db.DAOHelper;
import tr.com.minetrack.app.helpers.DesEncrypter;

public class License {
	private String key;
	private String date;

	private String dateString;
	/**
	 * tek nesne
	 */
	private static volatile License licenseInstance;

	/**
	 * Double check locking
	 * 
	 * @return
	 */
	private static Object lock = new Object();

	public static License getInstance() {
		if (licenseInstance == null) {
			// double checked locking
			synchronized (lock) {
				if (licenseInstance == null) {
					licenseInstance = new License();
				}
			}
		}

		return licenseInstance;
	}

	// contructor
	private License() {
		init();
	}

	private void init() {
		HashMap<Integer, License> licenseMap = DAOHelper.getLicenseDAO().get(null);

		License license = licenseMap.get(0);

		key = license.getKey();
		date = license.getDate();

		// decode the base64 encoded string
		byte[] decodedKey = Base64.getDecoder().decode(key);

		// rebuild key using SecretKeySpec
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");

		DesEncrypter encrypter = new DesEncrypter(originalKey);

		// Şifreyi çöz
		String decryptedDate = encrypter.decrypt(date);
		dateString = decryptedDate;
	}

	private boolean initUpdate(String tmp) {
		if (DAOHelper.getLicenseDAO().update(this, null)) {
			// decode the base64 encoded string
			byte[] decodedKey = Base64.getDecoder().decode(key);
			// rebuild key using SecretKeySpec
			SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
			DesEncrypter encrypter = new DesEncrypter(originalKey);
			// Şifreyi çöz
			String decryptedDate = encrypter.decrypt(date);
			dateString = decryptedDate;
			return true;
		} else {
			this.date = tmp;
			return false;
		}

	}

	public String getDateString() {
		return dateString;
	}

	public License(String key, String date) {
		this.key = key;
		this.date = date;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDate() {
		return date;
	}

	public boolean setDate(String date) {
		String tmp = this.date;
		this.date = date;
		return initUpdate(tmp);
	}

	public boolean licenseExpired(DateTime now) {

		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime licenseDate = formatter.parseDateTime(getDateString());

		if (licenseDate.isAfter(now)) {
			return true;
		}
		return false;
	}
}
