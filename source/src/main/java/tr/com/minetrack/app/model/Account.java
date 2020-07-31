/**
 * 
 */
package tr.com.minetrack.app.model;

/**
 * @author Gafur Hayytbayev
 *
 */
public class Account {
	private String username;
	private String password;

	public Account(String u, String p) {
		this.username = u;
		this.password = p;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
