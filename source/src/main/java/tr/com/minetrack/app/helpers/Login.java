/**
 * 
 */
package tr.com.minetrack.app.helpers;

import java.util.HashMap;

import tr.com.minetrack.app.db.DAOHelper;
import tr.com.minetrack.app.model.Account;

/**
 * @author Gafur Hayytbayev
 *
 */
public class Login {
	public static boolean loggedIn;

	public static boolean authenticate(String username, String password) {

		// hardcoded username and password
		HashMap<Integer, Account> accountMap = DAOHelper.getAccountDAO().get(null);

		if (accountMap.get(0).getUsername().equals(username) && accountMap.get(0).getPassword().equals(password)) {
			return true;
		}
		return false;
	}
}
