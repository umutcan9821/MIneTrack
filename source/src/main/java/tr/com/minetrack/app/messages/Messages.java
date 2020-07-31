package tr.com.minetrack.app.messages;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	// private static final String BUNDLE_NAME =
	// "tr.com.minetrack.app.messages.messages_tr_TR";
	private static final String BUNDLE_NAME = "tr.com.minetrack.app.messages.messages_tr_TR";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
