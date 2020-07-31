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

import tr.com.minetrack.app.controller.AddMachineController;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.Machine;
import tr.com.minetrack.app.model.lists.MachineList;

public class AddMachineView extends JDialog {

	private JTextField mnoTextField;
	private JTextField fnameTextField;
	private JTextField lnameTextField;
	private JTextField roleTextField;
	private JTextField tagidTextField;
	private JTable table;

	AddMachineController controller = new AddMachineController(this);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddMachineView(Frame frame) {
		super(frame, Messages.getString("AddMachineView.manageMachines"), true); //$NON-NLS-1$

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

		model.addColumn(Messages.getString("AddMachineView.machineId"));
		model.addColumn(Messages.getString("AddMachineView.fname"));
		model.addColumn(Messages.getString("AddMachineView.lname"));
		model.addColumn(Messages.getString("AddMachineView.role"));
		model.addColumn(Messages.getString("AddMachineView.tagid"));

		HashMap<Integer, Machine> tagIdList = MachineList.getInstance().getList();

		for (Integer tid : tagIdList.keySet()) {
			Machine m = tagIdList.get(tid);
			model.addRow(new Object[] { m.getMachineNo(), m.getFname(), m.getLname(), m.getRole(), m.getTagId() });
		}

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// i = the index of the selected row
				int i = table.getSelectedRow();

				mnoTextField.setText(model.getValueAt(i, 0).toString());
				fnameTextField.setText(model.getValueAt(i, 1).toString());
				lnameTextField.setText(model.getValueAt(i, 2).toString());
				roleTextField.setText(model.getValueAt(i, 3).toString());
				tagidTextField.setText(model.getValueAt(i, 4).toString());
			}
		});

		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setPreferredSize(new Dimension(400, 250));

		JLabel labelMachines = new JLabel(Messages.getString("AddMachineView.allMachines")); 
		JLabel labelAddNewMachine = new JLabel(Messages.getString("AddMachineView.addNewMachine"));

		JPanel tableButtonPanel = new JPanel();
		JButton deletebtn = new JButton(Messages.getString("AddMachineView.delete"));
		deletebtn.setName("delete");
		deletebtn.addActionListener(controller);
		tableButtonPanel.add(deletebtn);
		JButton updatebtn = new JButton(Messages.getString("AddMachineView.update"));
		updatebtn.setName("update");
		updatebtn.addActionListener(controller);
		tableButtonPanel.add(updatebtn);

		JPanel detailsPanel = new JPanel();
		detailsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(labelMachines, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(labelAddNewMachine, gbc);

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

		JLabel mnoLabel = new JLabel(Messages.getString("AddMachineView.machineId")); 
		JLabel fname = new JLabel(Messages.getString("AddMachineView.fName")); 
		JLabel lnameLabel = new JLabel(Messages.getString("AddMachineView.lName")); 
		JLabel roleLabel = new JLabel(Messages.getString("AddMachineView.role")); 
		JLabel tagidLabel = new JLabel(Messages.getString("AddMachineView.tagId")); 

		mnoTextField = new JTextField(12);
		fnameTextField = new JTextField(12);
		lnameTextField = new JTextField(12);
		roleTextField = new JTextField(12);
		tagidTextField = new JTextField(12);

		panel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		int i = 0;

		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTHEAST;

		// tc label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		panel.add(mnoLabel, gbc);

		// tc text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;

		// gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(mnoTextField, gbc);

		i++;
		// fname label
		// gbc.insets = new Insets(2, 2, 2, 2);
		// gbc.anchor = GridBagConstraints.NORTHEAST;

		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(fname, gbc);

		// fname text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(fnameTextField, gbc);

		i++;

		// lname label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		// gbc.fill = GridBagConstraints.NONE;
		panel.add(lnameLabel, gbc);

		// lname text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(lnameTextField, gbc);

		i++;

		// role label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		// gbc.fill = GridBagConstraints.NONE;
		panel.add(roleLabel, gbc);

		// lname text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(roleTextField, gbc);

		i++;

		// tag id label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		// gbc.fill = GridBagConstraints.NONE;
		panel.add(tagidLabel, gbc);
		// tag id text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(tagidTextField, gbc);

		JPanel tableButtonPanel = new JPanel();
		JButton addbtn = new JButton(Messages.getString("AddMachineView.add")); //$NON-NLS-1$
		addbtn.setName("add"); //$NON-NLS-1$
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

	public JTextField getMnoTextField() {
		return mnoTextField;
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
