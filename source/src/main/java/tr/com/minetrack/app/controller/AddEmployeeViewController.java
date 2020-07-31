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
import tr.com.minetrack.app.model.Employee;
import tr.com.minetrack.app.model.lists.EmployeeList;
import tr.com.minetrack.app.view.dialogs.AddEmployeeView;

public class AddEmployeeViewController implements ActionListener {

	private final AddEmployeeView parent;

	public AddEmployeeViewController(final AddEmployeeView parent) {
		this.parent = parent;
	}

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
			addEmployee(model);
			break;
		default:
			//System.out.println("default case"); //$NON-NLS-1$
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
			long tcno = Long.parseLong(model.getValueAt(selectedRow, 0).toString());
			String fname = model.getValueAt(selectedRow, 1).toString();
			String lname = model.getValueAt(selectedRow, 2).toString();
			String role = model.getValueAt(selectedRow, 3).toString();
			int tid = Integer.parseInt(model.getValueAt(selectedRow, 4).toString());

			long tcnoNew = Long.parseLong(parent.getTcTextField().getText());
			String fnameNew = parent.getFnameTextField().getText();
			String lnameNew = parent.getLnameTextField().getText();
			String roleNew = parent.getRoleTextField().getText();
			int tidNew = Integer.parseInt(parent.getTagidTextField().getText());

			// eski verilerde degisiklik var mi?
			if (tcno != tcnoNew || !fname.equals(fnameNew) || !lname.equals(lnameNew) || !role.equals(roleNew)
					|| tid != tidNew) {
				// update islemi basariyla gerceklesti mi?
				if (EmployeeList.getInstance().update(new Employee(tcnoNew, fnameNew, lnameNew, roleNew, tidNew))) {
					model.setValueAt("" + tcnoNew, selectedRow, 0);
					model.setValueAt(fnameNew, selectedRow, 1);
					model.setValueAt(lnameNew, selectedRow, 2);
					model.setValueAt(roleNew, selectedRow, 3);
					model.setValueAt("" + tidNew, selectedRow, 4);
					JOptionPane.showMessageDialog(parent.getComponent(0),
							Messages.getString("AddEmployeeViewController.successful"), //$NON-NLS-1$
							Messages.getString("AddEmployeeViewController.updated"), //$NON-NLS-1$
							JOptionPane.INFORMATION_MESSAGE);
				} // end remove from table
				else {
					JOptionPane.showMessageDialog(parent.getComponent(0),
							Messages.getString("AddEmployeeViewController.error"), //$NON-NLS-1$
							Messages.getString("AddEmployeeViewController.errorupdated"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(parent.getComponent(0),
						Messages.getString("AddEmployeeViewController.nochange"), //$NON-NLS-1$
						Messages.getString("AddEmployeeViewController.samedata"), //$NON-NLS-1$
						JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

	/**
	 * @param model
	 */
	private void deleteRowsFromTable(final DefaultTableModel model) {
		//System.out.println("delete"); //$NON-NLS-1$

		// start remove from table
		int[] rows = parent.getTable().getSelectedRows();
		if (rows.length > 0) {
			ArrayList<Integer> tagList = new ArrayList<>();

			for (int i = 0; i < rows.length; i++) {

				int tid = Integer.parseInt(model.getValueAt(rows[i], 4).toString());

				tagList.add(tid);

			}

			if (EmployeeList.getInstance().remove(tagList)) {
				for (int i = 0; i < rows.length; i++) {

					model.removeRow(rows[i] - i);
				} // end remove from table
				JOptionPane.showMessageDialog(parent.getComponent(0),
						Messages.getString("AddEmployeeViewController.successful"), //$NON-NLS-1$
						Messages.getString("AddEmployeeViewController.successremove"), //$NON-NLS-1$
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(parent.getComponent(0),
						Messages.getString("AddEmployeeViewController.error"), //$NON-NLS-1$
						Messages.getString("AddEmployeeViewController.add"), //$NON-NLS-1$
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void addEmployee(final DefaultTableModel model) {

		Employee emp = null;

		long tcno = Long.valueOf(parent.getTcTextField().getText());
		String fname = parent.getFnameTextField().getText();
		String lname = parent.getLnameTextField().getText();
		String role = parent.getRoleTextField().getText();
		int tid = Integer.parseInt(parent.getTagidTextField().getText());
		try {
			emp = new Employee(tcno, fname, lname, role, tid);
		} catch (NumberFormatException e) {
			//System.out.println("Number Format Exception here!");
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			return;
		}

		if (EmployeeList.getInstance().add(emp)) {

			model.addRow(new Object[] { "" + tcno, fname, lname, role, "" + tid });

			JOptionPane.showMessageDialog(parent.getComponent(0),
					Messages.getString("AddEmployeeViewController.succuss"), //$NON-NLS-1$
					Messages.getString("AddEmployeeViewController.successaddemployee"), //$NON-NLS-1$
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(parent.getComponent(0), Messages.getString("AddEmployeeViewController.error"), //$NON-NLS-1$
					Messages.getString("AddEmployeeViewController.erroraddemployee"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}
	}

}
