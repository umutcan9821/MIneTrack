/**
 * 
 */
package tr.com.minetrack.app.view.dialogs;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import tr.com.minetrack.app.controller.SettingsViewController;
import tr.com.minetrack.app.helpers.MyPoint;
import tr.com.minetrack.app.helpers.PointList;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.RFIDReader;
import tr.com.minetrack.app.model.SignalMap;
import tr.com.minetrack.app.model.lists.RFIDReaderList;
import tr.com.minetrack.app.model.lists.SignalMapList;

/**
 * @author Gafur Hayytbayev
 *
 */
public class SettingsView extends JDialog {

	// private JTextField pidTextField;
	private Choice pidChoice;
	private Choice ridChoice;
	private JTextField minrssiTextField;
	private JTextField maxrssiTextField;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1239067299108059415L;
	private JTable table;

	/**
	 * @param owner
	 */
	public SettingsView(Frame owner) {
		super(owner, Messages.getString("SettingsView.setting"), true); //$NON-NLS-1$

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		SettingsViewController controller = new SettingsViewController(this);

		JPanel panel = new JPanel(new GridBagLayout());
		getContentPane().add(panel);

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

		model.addColumn(Messages.getString("SettingsView.pointid")); //$NON-NLS-1$
		model.addColumn(Messages.getString("SettingsView.readerid")); //$NON-NLS-1$
		model.addColumn(Messages.getString("SettingsView.rssivalue")); //$NON-NLS-1$
		model.addColumn("Max RSSI Value");

		HashMap<String, SignalMap> signalMapList = SignalMapList.getInstance().getList();

		for (String pidPlusRid : signalMapList.keySet()) {
			SignalMap sm = signalMapList.get(pidPlusRid);
			int pid = Integer.parseInt(pidPlusRid.split("-")[0]); //$NON-NLS-1$
			model.addRow(new Object[] { pid, sm.getRid(), sm.getMinrssi(), sm.getMaxrssi() });
		}

		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// get selected row data From table to textfields
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				// i = the index of the selected row
				int i = table.getSelectedRow();

				// pidTextField.setText(model.getValueAt(i, 0).toString());
				// ridTextField.setText(model.getValueAt(i, 1).toString());
				pidChoice.select(model.getValueAt(i, 0).toString());
				ridChoice.select(model.getValueAt(i, 1).toString());
				minrssiTextField.setText(model.getValueAt(i, 2).toString());
				maxrssiTextField.setText(model.getValueAt(i, 3).toString());
			}
		});

		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setPreferredSize(new Dimension(400, 400));

		JLabel labelSignalMapInfo = new JLabel(Messages.getString("SettingsView.signalmapinfo")); //$NON-NLS-1$
		JLabel labelAddNewSignalMap = new JLabel(Messages.getString("SettingsView.addnewsignalmap")); //$NON-NLS-1$

		JPanel tableButtonPanel = new JPanel();
		JButton deletebtn = new JButton(Messages.getString("SettingsView.delete")); //$NON-NLS-1$
		deletebtn.setName("delete"); //$NON-NLS-1$
		deletebtn.addActionListener(controller);
		tableButtonPanel.add(deletebtn);
		JButton updatebtn = new JButton(Messages.getString("SettingsView.update")); //$NON-NLS-1$
		updatebtn.setName("update"); //$NON-NLS-1$
		updatebtn.addActionListener(controller);
		tableButtonPanel.add(updatebtn);

		JPanel detailsPanel = new JPanel();
		detailsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(labelSignalMapInfo, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(labelAddNewSignalMap, gbc);

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

		panel.add(createDetailsPanel(controller), gbc);

		this.setSize(600, 300);
		this.center(owner);
		this.pack();
		this.setVisible(true);
	}

	private Component createDetailsPanel(SettingsViewController controller) {
		JPanel panel = new JPanel();

		JLabel pidLabel = new JLabel(Messages.getString("SettingsView.pid")); //$NON-NLS-1$
		pidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel ridLabel = new JLabel(Messages.getString("SettingsView.rid")); //$NON-NLS-1$
		ridLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel minrssiLabel = new JLabel("Min RSSI"); //$NON-NLS-1$
		minrssiLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel maxrssiLabel = new JLabel("Max RSSI"); //$NON-NLS-1$
		maxrssiLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		// pidTextField = new JTextField(15);
		// ridTextField = new JTextField(15);
		pidChoice = new Choice();
		ridChoice = new Choice();
		minrssiTextField = new JTextField(15);
		maxrssiTextField = new JTextField(15);

		JPanel tableButtonPanel = new JPanel();
		JButton addbtn = new JButton(Messages.getString("SettingsView.add")); //$NON-NLS-1$
		addbtn.setName("add"); //$NON-NLS-1$
		addbtn.addActionListener(controller);
		tableButtonPanel.add(addbtn);

		panel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		int i = 0;

		// pid label
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTHEAST;

		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(pidLabel, gbc);

		// pid text field

		ArrayList<MyPoint> pointList = PointList.getInstance().getList();
		for (int j = 0; j < pointList.size(); j++) {
			pidChoice.add("" + pointList.get(j).getIndex());
		}

		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;

		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(pidChoice, gbc);

		i++;
		// rid label
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTHEAST;

		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(ridLabel, gbc);

		// rid text field
		HashMap<Integer, RFIDReader> list = RFIDReaderList.getInstance().getList();

		for (Integer key : list.keySet()) {

			ridChoice.add("" + key);
		}

		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(ridChoice, gbc);

		i++;

		// min rssi label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(minrssiLabel, gbc);

		// min rssi text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(minrssiTextField, gbc);

		i++;

		// max rssi label
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(maxrssiLabel, gbc);

		// max rssi text field
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(maxrssiTextField, gbc);

		i++;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panel.add(tableButtonPanel, gbc);

		return panel;
	}

	private void center(Frame frame) {
		Rectangle r = frame.getBounds();
		int x = r.x + (r.width - this.getSize().width) / 2;
		int y = r.y + (r.height - this.getSize().height) / 2;
		this.setLocation(x, y);
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public String getPidChoice() {
		return pidChoice.getSelectedItem();
	}

	public String getRidChoice() {
		return ridChoice.getSelectedItem();
	}

	public JTextField getMinRssiTextField() {
		return minrssiTextField;
	}

	public JTextField getMaxRssiTextField() {
		return maxrssiTextField;
	}
}
