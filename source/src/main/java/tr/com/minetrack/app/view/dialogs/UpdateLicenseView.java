package tr.com.minetrack.app.view.dialogs;

import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JTextField;

import tr.com.minetrack.app.controller.UpdateLicenseController;
import tr.com.minetrack.app.helpers.JTextFieldLimit;

@SuppressWarnings("serial")
public class UpdateLicenseView extends JDialog {
	// Variables declaration - do not modify
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JRadioButton jRadioButton1;
	private javax.swing.JRadioButton jRadioButton2;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.ButtonGroup licenseGroup;

	public JDialog owner;
	@SuppressWarnings("unused")
	private String licenseKey;
	// End of variables declaration

	/**
	 * Creates new form LicenseJDialog
	 */
	public UpdateLicenseView(JDialog parent, boolean modal) {
		super(parent, modal);
		owner = parent;
		initComponents();
	}

	public JDialog getParent() {
		return owner;
	}

	private void initComponents() {
		UpdateLicenseController controller = new UpdateLicenseController(this);

		licenseGroup = new javax.swing.ButtonGroup();
		jPanel2 = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jRadioButton1 = new javax.swing.JRadioButton();
		jTextField1 = new javax.swing.JTextField();
		jTextField1.setDocument(new JTextFieldLimit(24));
		jRadioButton2 = new javax.swing.JRadioButton();
		jTextField2 = new javax.swing.JTextField();
		jTextField2.setDocument(new JTextFieldLimit(24));
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();

		jTextField1.setEnabled(false);
		jTextField2.setEnabled(false);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("License Validation");
		setBackground(new java.awt.Color(255, 255, 255));

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("License Information"));

		licenseGroup.add(jRadioButton1);
		jRadioButton1.setText("I have a license key");
		jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField1.setEnabled(true);
				jTextField2.setEnabled(false);
			}
		});

		licenseGroup.add(jRadioButton2);
		jRadioButton2.setText("I have a license text");
		jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField2.setEnabled(true);
				jTextField1.setEnabled(false);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(36, 36, 36)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jRadioButton1).addComponent(jRadioButton2))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
								.addComponent(jTextField1))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jRadioButton1).addComponent(jTextField1,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jRadioButton2).addComponent(jTextField2,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(22, Short.MAX_VALUE)));

		jButton1.setText("Close");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dispose();
			}
		});

		jButton2.setText("Validate");
		jButton2.setName("validate");
		jButton2.addActionListener(controller);
		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton2)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton1)
						.addGap(22, 22, 22)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButton1).addComponent(jButton2))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel1.getAccessibleContext().setAccessibleName("License Information");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel2,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap().addComponent(jPanel2,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
		this.center((LicenseView) super.getParent());
		this.setVisible(true);
	}// </editor-fold>

	private void center(LicenseView frame) {
		Rectangle r = frame.getBounds();
		int x = r.x + (r.width - this.getSize().width) / 2;
		int y = r.y + (r.height - this.getSize().height) / 2;
		this.setLocation(x, y);
	}

	public JTextField getEnabledField() {
		if (jTextField1.isEnabled()) {
			return jTextField1;
		}
		return jTextField2;
	}
}
