/**
 * 
 */
package tr.com.minetrack.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.SignalMap;
import tr.com.minetrack.app.model.lists.SignalMapList;
import tr.com.minetrack.app.view.dialogs.SettingsView;

/**
 * @author Gafur Hayytbayev
 *
 */
public class SettingsViewController implements ActionListener {

	private final SettingsView parent;

	/**
	 * 
	 */
	public SettingsViewController(final SettingsView view) {
		this.parent = view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		JButton jButton = (JButton) e.getSource();
		DefaultTableModel model = (DefaultTableModel) parent.getTable().getModel();

		String name = jButton.getName();
		switch (name) {
		case "delete": //$NON-NLS-1$
			deleteRowsFromTable(model);
			break;
		case "update": //$NON-NLS-1$
			updateTable(model);
			break;
		case "add": //$NON-NLS-1$
			//System.out.println("add"); //$NON-NLS-1$
			addSignalMap(model);
			break;
		default:
			//System.out.println("default case"); //$NON-NLS-1$
		}

	}

	private void deleteRowsFromTable(final DefaultTableModel model) {
		// do something
		//System.out.println("delete"); //$NON-NLS-1$

		// start remove from table
		int[] rows = parent.getTable().getSelectedRows();

		if (rows.length > 0) {
			ArrayList<String> pidList = new ArrayList<>();
			for (int i = 0; i < rows.length; i++) {

				int pid = Integer.parseInt(model.getValueAt(rows[i], 0).toString());
				int rid = Integer.parseInt(model.getValueAt(rows[i], 1).toString());
				String str = "" + pid + "-" + rid; //$NON-NLS-1$ //$NON-NLS-2$
				pidList.add(str);

			} // end remove from table
			if (SignalMapList.getInstance().remove(pidList)) {
				for (int i = 0; i < rows.length; i++) {

					model.removeRow(rows[i] - i);
				} // end remove from table
				JOptionPane.showMessageDialog(parent.getComponent(0),
						Messages.getString("SettingsViewController.success"), //$NON-NLS-1$
						Messages.getString("SettingsViewController.successremove"), //$NON-NLS-1$
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(parent.getComponent(0),
						Messages.getString("SettingsViewController.error"), //$NON-NLS-1$
						Messages.getString("SettingsViewController.errorremove"), //$NON-NLS-1$
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void addSignalMap(final DefaultTableModel model) {

		SignalMap sm = null;
		try {
			sm = new SignalMap(Integer.valueOf(parent.getPidChoice()), Integer.parseInt(parent.getRidChoice()),
					Integer.parseInt(parent.getMinRssiTextField().getText()),
					Integer.parseInt(parent.getMaxRssiTextField().getText()));
		} catch (NumberFormatException e) {
			//System.out.println("Number Format Exception in Setting View Controller"); //$NON-NLS-1$
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			return;
		}

		if (SignalMapList.getInstance().add(sm)) {

			model.addRow(new Object[] { parent.getPidChoice(), parent.getRidChoice(),
					parent.getMinRssiTextField().getText(), parent.getMaxRssiTextField().getText() });

			JOptionPane.showMessageDialog(parent.getComponent(0), Messages.getString("SettingsViewController.success"), //$NON-NLS-1$
					Messages.getString("SettingsViewController.successaddsignalmap"), //$NON-NLS-1$
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(parent.getComponent(0), Messages.getString("SettingsViewController.error"), //$NON-NLS-1$
					Messages.getString("SettingsViewController.erroradd"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}

	}

	/**
	 * 
	 */
	private void updateTable(final DefaultTableModel model) {
		//System.out.println("update"); //$NON-NLS-1$

		// start remove from table
		int selectedRow = parent.getTable().getSelectedRow();
		if (selectedRow >= 0) {
			// table data
			int pid = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
			int rid = Integer.parseInt(model.getValueAt(selectedRow, 1).toString());
			int minrssi = Integer.parseInt(model.getValueAt(selectedRow, 2).toString());
			int maxrssi = Integer.parseInt(model.getValueAt(selectedRow, 3).toString());

			int pidText = Integer.parseInt(parent.getPidChoice());
			int ridText = Integer.parseInt(parent.getRidChoice());
			int minrssiText = Integer.parseInt(parent.getMinRssiTextField().getText());
			int maxrssiText = Integer.parseInt(parent.getMaxRssiTextField().getText());

			// eski verilerde degisiklik var mi?
			if (pid != pidText || rid != ridText || minrssi != minrssiText || maxrssi != maxrssiText) {
				// update islemi basariyla gerceklesti mi?
				if (SignalMapList.getInstance().update(new SignalMap(pidText, ridText, minrssiText, maxrssiText))) {
					model.setValueAt(pidText, selectedRow, 0);
					model.setValueAt(ridText, selectedRow, 1);
					model.setValueAt(minrssiText, selectedRow, 2);
					model.setValueAt(maxrssiText, selectedRow, 3);
					JOptionPane.showMessageDialog(parent.getComponent(0),
							Messages.getString("SettingsViewController.success"), //$NON-NLS-1$
							Messages.getString("SettingsViewController.successupdate"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
				} // end remove from table
				else {
					JOptionPane.showMessageDialog(parent.getComponent(0),
							Messages.getString("SettingsViewController.error"), //$NON-NLS-1$
							Messages.getString("SettingsViewController.errorupdate"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(parent.getComponent(0),
						Messages.getString("SettingsViewController.nochange"), //$NON-NLS-1$
						Messages.getString("SettingsViewController.thesamedata"), //$NON-NLS-1$
						JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}
}
