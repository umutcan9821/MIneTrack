/**
 * 
 */
package tr.com.minetrack.app.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import tr.com.minetrack.app.helpers.Login;
import tr.com.minetrack.app.messages.Messages;

/**
 * @author Gafur Hayytbayev
 *
 */
public class AdminView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JLabel lbUsername;
	private JLabel lbPassword;
	private JButton btnLogin;
	private JButton btnCancel;
	private boolean succeeded;

	public AdminView(Frame parent) {
		super(parent, Messages.getString("AdminView.login"), true); //$NON-NLS-1$

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		lbUsername = new JLabel(Messages.getString("AdminView.username")); //$NON-NLS-1$
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbUsername, cs);

		tfUsername = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfUsername, cs);

		lbPassword = new JLabel(Messages.getString("AdminView.password")); //$NON-NLS-1$
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(lbPassword, cs);

		pfPassword = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(pfPassword, cs);
		panel.setBorder(new LineBorder(Color.GRAY));

		btnLogin = new JButton(Messages.getString("AdminView.login")); //$NON-NLS-1$

		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (Login.authenticate(getUsername(), getPassword())) {
					JOptionPane.showMessageDialog(AdminView.this, Messages.getString("AdminView.hi") + getUsername() //$NON-NLS-1$
							+ Messages.getString("AdminView.successfully"), //$NON-NLS-1$
							Messages.getString("AdminView.login"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
					Login.loggedIn = true;
					succeeded = true;
					dispose();
				} else {
					JOptionPane.showMessageDialog(AdminView.this, Messages.getString("AdminView.invalidUsername"), //$NON-NLS-1$
							Messages.getString("AdminView.login"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
					// reset username and password
					tfUsername.setText(Messages.getString(""));
					pfPassword.setText(Messages.getString(""));
					Login.loggedIn = false;
					succeeded = false;
				}
			}
		});
		btnCancel = new JButton(Messages.getString("AdminView.calcel")); //$NON-NLS-1$
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JPanel bp = new JPanel();
		bp.add(btnLogin);
		bp.add(btnCancel);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	public String getUsername() {
		return tfUsername.getText().trim();
	}

	public String getPassword() {
		return new String(pfPassword.getPassword());
	}

	public boolean isSucceeded() {
		return succeeded;
	}
}
