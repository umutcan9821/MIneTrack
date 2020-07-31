package tr.com.minetrack.app.view.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import tr.com.minetrack.app.controller.AddEmployeeViewController;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.Employee;
import tr.com.minetrack.app.model.lists.EmployeeList;

public class AddEmployeeView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tcTextField;
	private JTextField fnameTextField;
	private JTextField lnameTextField;
	private JTextField tagidTextField;
	private JTextField roleTextField;

	private JTable table;

	AddEmployeeViewController controller = new AddEmployeeViewController(this);

	public AddEmployeeView(Frame frame) {
		super(frame, Messages.getString("AddEmployeeView.manageEmployee"), true); //$NON-NLS-1$

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new GridBagLayout());
		this.getContentPane().add(panel);

		DefaultTableModel model = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		model.addColumn(Messages.getString("AddEmployeeView.tcno"));
		model.addColumn(Messages.getString("AddEmployeeView.firstName"));
		model.addColumn(Messages.getString("AddEmployeeView.lastName"));
		model.addColumn(Messages.getString("AddEmployeeView.role"));
		model.addColumn(Messages.getString("AddEmployeeView.tagid"));

		HashMap<Integer, Employee> tagIdList = EmployeeList.getInstance().getList();

		for (Integer tid : tagIdList.keySet()) {
			Employee emp = tagIdList.get(tid);
			model.addRow(new Object[] { emp.getTcno(), emp.getFname(), emp.getLname(), emp.getRole(), emp.getTagId() });
		}

		// get selected row data From table to textfields
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				// i = the index of the selected row
				int i = table.getSelectedRow();

				tcTextField.setText(model.getValueAt(i, 0).toString());
				fnameTextField.setText(model.getValueAt(i, 1).toString());
				lnameTextField.setText(model.getValueAt(i, 2).toString());
				roleTextField.setText(model.getValueAt(i, 3).toString());
				tagidTextField.setText(model.getValueAt(i, 4).toString());
			}
		});

		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setPreferredSize(new Dimension(400, 250));

		JLabel labelEmployees = new JLabel(Messages.getString("AddEmployeeView.employees")); //$NON-NLS-1$
		JLabel labelAddNewEmployee = new JLabel(Messages.getString("AddEmployeeView.addNewEmployee")); //$NON-NLS-1$

		JPanel tableButtonPanel = new JPanel();
		JButton deletebtn = new JButton(Messages.getString("AddEmployeeView.delete")); //$NON-NLS-1$
		deletebtn.setName("delete"); //$NON-NLS-1$
		deletebtn.addActionListener(controller);
		tableButtonPanel.add(deletebtn);
		JButton updatebtn = new JButton(Messages.getString("AddEmployeeView.update")); //$NON-NLS-1$
		updatebtn.setName("update"); //$NON-NLS-1$
		updatebtn.addActionListener(controller);
		tableButtonPanel.add(updatebtn);

		JPanel detailsPanel = new JPanel();
		detailsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(labelEmployees, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(labelAddNewEmployee, gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panel.add(tableScrollPane, gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panel.add(tableButtonPanel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.NORTH;

		panel.add(createDetailsPanel(), gbc);

		this.setSize(600, 300);
		this.center(frame);
		this.pack();

		this.setVisible(true);
	}

	public JTable getTable() {
		return table;
	}

	private JPanel createDetailsPanel() {

		JPanel panel = new JPanel();

		JLabel tcLabel = new JLabel(Messages.getString("AddEmployeeView.tcno")); 
		JLabel fname = new JLabel(Messages.getString("AddEmployeeView.firstName"));
		JLabel lnameLabel = new JLabel(Messages.getString("AddEmployeeView.lastName"));
		JLabel roleLabel = new JLabel(Messages.getString("AddEmployeeView.role"));
		JLabel tagidLabel = new JLabel(Messages.getString("AddEmployeeView.tagid"));

		tcTextField = new JTextField(12);
		fnameTextField = new JTextField(12);
		lnameTextField = new JTextField(12);
		roleTextField = new JTextField(12);
		tagidTextField = new JTextField(12);

		panel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		int i = 0;

		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.LINE_END;

		// tc label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		panel.add(tcLabel, gbc);

		// tc text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		panel.add(tcTextField, gbc);

		i++;
		// fname label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		panel.add(fname, gbc);

		// fname text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		panel.add(fnameTextField, gbc);

		i++;

		// lname label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		panel.add(lnameLabel, gbc);

		// lname text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		panel.add(lnameTextField, gbc);

		i++;

		// role label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		panel.add(roleLabel, gbc);

		// lname text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		panel.add(roleTextField, gbc);

		i++;

		// tag id label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		panel.add(tagidLabel, gbc);

		// tag id text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		panel.add(tagidTextField, gbc);

		JPanel tableButtonPanel = new JPanel();
		JButton addbtn = new JButton(Messages.getString("AddEmployeeView.add"));
		addbtn.setName("add");
		addbtn.addActionListener(controller);
		tableButtonPanel.add(addbtn);

		i++;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panel.add(tableButtonPanel, gbc);

		return panel;
	}

	public JTextField getTcTextField() {
		return tcTextField;
	}

	public JTextField getFnameTextField() {
		return fnameTextField;
	}

	public JTextField getLnameTextField() {
		return lnameTextField;
	}

	public JTextField getRoleTextField() {
		return roleTextField;
	}

	public JTextField getTagidTextField() {
		return tagidTextField;
	}

	private void center(Frame frame) {
		Rectangle r = frame.getBounds();
		int x = r.x + (r.width - this.getSize().width) / 2;
		int y = r.y + (r.height - this.getSize().height) / 2;
		this.setLocation(x, y);
	}

}
