package tr.com.minetrack.app.view.dialogs;

import java.awt.Frame;
import java.awt.Rectangle;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.joda.time.DateTime;

import tr.com.minetrack.app.controller.LicenseViewController;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.License;

public class LicenseView extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Variables declaration - do not modify
	private JButton jButton1;
	protected JButton jButton2;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JTextField jTextField1;
	private JTextField jTextField2;
	// End of variables declaration

	/**
	 * Creates new form LicenseInfoJDialog
	 */
	public LicenseView(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	protected void initComponents() {
		jPanel1 = new JPanel();
		jPanel2 = new JPanel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		jTextField1 = new JTextField();
		jTextField2 = new JTextField();
		jButton1 = new JButton();
		jButton1.setName("close"); //$NON-NLS-1$
		jButton2 = new JButton();
		jButton2.setName("changeLicense"); //$NON-NLS-1$

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		LicenseViewController controller = new LicenseViewController(this);
		jButton1.addActionListener(controller);
		jButton2.addActionListener(controller);

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(Messages.getString("LicenseView.license_info"))); //$NON-NLS-1$

		jLabel2.setText(Messages.getString("LicenseView.license_status")); //$NON-NLS-1$

		jLabel3.setText(Messages.getString("LicenseView.license_expired_date")); //$NON-NLS-1$

		jTextField1.setEditable(false);
		jTextField1.setBackground(java.awt.Color.lightGray);
		jTextField1.setForeground(new java.awt.Color(255, 0, 51));

		jTextField2.setEditable(false);
		jTextField2.setBackground(java.awt.Color.lightGray);

		checkLicense();

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel2).addComponent(jLabel3))
						.addGap(18, 18, 18)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jTextField2)
								.addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
						.addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(22, 22, 22)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(26, Short.MAX_VALUE)));

		jButton1.setText(Messages.getString("LicenseView.close_window")); //$NON-NLS-1$

		jButton2.setText(Messages.getString("LicenseView.change_license")); //$NON-NLS-1$

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										jPanel1Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
												.addComponent(jButton2).addGap(18, 18, 18).addComponent(jButton1)))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButton1).addComponent(jButton2))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
		this.center((Frame) super.getParent());
	}

	public void checkLicense() {
		License license = License.getInstance();
		if (license.licenseExpired(new DateTime())) {
			jTextField1.setText(Messages.getString("LicenseView.state_of_activation")); //$NON-NLS-1$
			jTextField2.setText(license.getDateString());
		} else {
			jTextField1.setText(Messages.getString("LicenseView.license_period")); //$NON-NLS-1$
			jTextField2.setText(license.getDateString());
		}
	}

	private void center(Frame frame) {
		Rectangle r = frame.getBounds();
		int x = r.x + (r.width - this.getSize().width) / 2;
		int y = r.y + (r.height - this.getSize().height) / 2;
		this.setLocation(x, y);
	}

	public void closeThisDialog() {
		dispose();
	}

	public void showUpdateLicenseView() {
		new UpdateLicenseView(this, true);
	}
}
