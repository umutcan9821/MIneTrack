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
import tr.com.minetrack.app.model.Machine;
import tr.com.minetrack.app.model.lists.MachineList;
import tr.com.minetrack.app.view.dialogs.AddMachineView;

/**
 * @author Gafur Hayytbayev
 *
 */
public class AddMachineController implements ActionListener {

	private final AddMachineView parent;

	public AddMachineController(final AddMachineView view) {
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
			deleteRowFromTable(model);
			break;
		case "update": //$NON-NLS-1$
			updateTableRow(model);
			break;
		case "add": //$NON-NLS-1$
			addMachine(model);
			break;
		default:
			// System.out.println("default case"); //$NON-NLS-1$
		}

	}

	private void updateTableRow(final DefaultTableModel model) {
		// System.out.println("update"); //$NON-NLS-1$

		// start remove from table
		int selectedRow = parent.getTable().getSelectedRow();
		if (selectedRow >= 0) {
			// table data
			long mno = Long.parseLong(model.getValueAt(selectedRow, 0).toString());
			String fname = model.getValueAt(selectedRow, 1).toString();
			String lname = model.getValueAt(selectedRow, 2).toString();
			String role = model.getValueAt(selectedRow, 3).toString();
			int tid = Integer.parseInt(model.getValueAt(selectedRow, 4).toString());

			long mnoNew = Long.parseLong(parent.getMnoTextField().getText());
			String fnameNew = parent.getFnameTextField().getText();
			String lnameNew = parent.getLnameTextField().getText();
			String roleNew = parent.getRoleTextField().getText();
			int tidNew = Integer.parseInt(parent.getTagidTextField().getText());

			// eski verilerde degisiklik var mi?
			if (mno != mnoNew || !fname.equals(fnameNew) || !lname.equals(lnameNew) || !role.equals(roleNew)
					|| tid != tidNew) {
				// update islemi basariyla gerceklesti mi?
				if (MachineList.getInstance().update(new Machine(mnoNew, fnameNew, lnameNew, roleNew, tidNew))) {
					model.setValueAt("" + mnoNew, selectedRow, 0);
					model.setValueAt(fnameNew, selectedRow, 1);
					model.setValueAt(lnameNew, selectedRow, 2);
					model.setValueAt(roleNew, selectedRow, 3);
					model.setValueAt("" + tidNew, selectedRow, 4);
					JOptionPane.showMessageDialog(parent.getComponent(0),
							Messages.getString("AddMachineController.success"), //$NON-NLS-1$
							Messages.getString("AddMachineController.updated"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
				} // end remove from table
				else {
					JOptionPane.showMessageDialog(parent.getComponent(0),
							Messages.getString("AddMachineController.error"), //$NON-NLS-1$
							Messages.getString("AddMachineController.errorwhenupdating"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(parent.getComponent(0),
						Messages.getString("AddMachineController.nochange"), //$NON-NLS-1$
						Messages.getString("AddMachineController.samedata"), //$NON-NLS-1$
						JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

	/**
	 * @param model
	 */
	private void deleteRowFromTable(final DefaultTableModel model) {
		// do something
		// System.out.println("delete"); //$NON-NLS-1$

		// start remove from table
		int[] rows = parent.getTable().getSelectedRows();
		if (rows.length > 0) {
			ArrayList<Integer> tagList = new ArrayList<>();

			for (int i = 0; i < rows.length; i++) {

				int tid = Integer.parseInt(model.getValueAt(rows[i], 4).toString());

				tagList.add(tid);

			} // end remove from table

			if (MachineList.getInstance().remove(tagList)) {
				for (int i = 0; i < rows.length; i++) {

					model.removeRow(rows[i] - i);
				} // end remove from table
				JOptionPane.showMessageDialog(parent.getComponent(0),
						Messages.getString("AddMachineController.success"), //$NON-NLS-1$
						Messages.getString("AddMachineController.successremoving"), //$NON-NLS-1$
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(parent.getComponent(0), Messages.getString("AddMachineController.error"), //$NON-NLS-1$
						Messages.getString("AddMachineController.erroraddmachine"), //$NON-NLS-1$
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void addMachine(final DefaultTableModel model) {
		Machine machine = null;

		long mno = Long.valueOf(parent.getMnoTextField().getText());
		String fname = parent.getFnameTextField().getText();
		String lname = parent.getLnameTextField().getText();
		String role = parent.getRoleTextField().getText();
		int tid = Integer.parseInt(parent.getTagidTextField().getText());
		try {
			machine = new Machine(mno, fname, lname, role, tid);
		} catch (NumberFormatException e) {
			//System.out.println("Number Format Exception in MachineViewController!"); //$NON-NLS-1$
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			return;
		}

		if (MachineList.getInstance().add(machine)) {

			model.addRow(new Object[] { "" + mno, fname, lname, role, "" + tid });

			JOptionPane.showMessageDialog(parent.getComponent(0), Messages.getString("AddMachineController.success"), //$NON-NLS-1$
					Messages.getString("AddMachineController.successaddmachine"), //$NON-NLS-1$
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(parent.getComponent(0), Messages.getString("AddMachineController.error"), //$NON-NLS-1$
					Messages.getString("AddMachineController.erroraddmachine"), //$NON-NLS-1$
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
