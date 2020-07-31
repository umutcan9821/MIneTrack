package tr.com.minetrack.app.view.msgdialogs;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.RFIDReader;
import tr.com.minetrack.app.view.UI;

public class ReaderStatus {
	public ReaderStatus(UI parent, HashMap<Integer, RFIDReader> readers) {

		String[] columnNames = { Messages.getString("ReaderStatus.reader"), //$NON-NLS-1$
				Messages.getString("ReaderStatus.status") }; //$NON-NLS-1$

		Object[][] data = new Object[readers.size()][2];

		int i = 0;
		for (Integer rid : readers.keySet()) {
			data[i] = new Object[2];
			RFIDReader reader = readers.get(rid);
			String name = reader.getName();
			data[i][0] = name;

			if (reader.getStatus())
				data[i][1] = reader.getDt().getHourOfDay() + ":" + reader.getDt().getMinuteOfHour();// Messages.getString("ReaderStatus.active"); //$NON-NLS-1$
			else
				data[i][1] = Messages.getString("ReaderStatus.passive"); //$NON-NLS-1$

			i++;
		}

		JTable table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 250));

		JOptionPane.showMessageDialog(parent.getFrame(), new JScrollPane(table),
				Messages.getString("ReaderStatus.raderstatus"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
	}
}
