package tr.com.minetrack.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import tr.com.minetrack.app.db.DAOHelper;
import tr.com.minetrack.app.helpers.Export;
import tr.com.minetrack.app.helpers.TimeAndRid;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.RFIDReader;
import tr.com.minetrack.app.model.lists.RFIDReaderList;
import tr.com.minetrack.app.model.lists.TrackedList;
import tr.com.minetrack.app.view.dialogs.DetailedReportView;

public class DetailedReportController implements ActionListener {
	private final DetailedReportView parent;

	public DetailedReportController(final DetailedReportView parent) {
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		JButton jButton = (JButton) e.getSource();
		DefaultTableModel model = (DefaultTableModel) parent.getTable().getModel();

		String name = jButton.getName();
		switch (name) {
		case "show":
			showPersonelReport(model);
			break;
		case "export":
			exportModel(model);
			break;
		default:
			//System.out.println("default case");
		}
	}

	private void exportModel(final DefaultTableModel model) {
		//System.out.println("export to exel");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileFilter xlsFilter = new FileNameExtensionFilter(".xlsx", "Microsoft Excel Documents");
		fileChooser.addChoosableFileFilter(xlsFilter);
		fileChooser.setAcceptAllFileFilterUsed(true);
		int result = fileChooser.showSaveDialog(parent);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();

//	    JTable table = parent.getTable();
//	    DefaultTableModel newModel = model;

			Export.toExcel(parent.getTable(), selectedFile);
			//System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}

	private void showPersonelReport(final DefaultTableModel model) {
		HashMap<Integer, RFIDReader> mapOfReaders = RFIDReaderList.getInstance().getList();

		model.setRowCount(0);

		// tarih sorgu yap
		DateTimeFormatter formatter = DateTimeFormat.forPattern(Messages.getString("DailyReportView.datepattern"));
		DateTime dt1 = formatter.parseDateTime(parent.getEntryDate1().getText());
		DateTime dt2 = dt1.plusDays(1); // bir sonraki gun

		int index = parent.getAdSoyadBox().getSelectedIndex();
		String nameSpaceSurname = parent.getAdSoyadBox().getItemAt(index).toString();
		String[] parts = nameSpaceSurname.split(" ");
		String fname = parts[0];
		String lname = parts[1];
		int tid = TrackedList.getInstance().getTidByNameSurname(fname, lname);

		ArrayList<TimeAndRid> list = DAOHelper.getDetailedReportDAO().get(tid, dt1, dt2);

		for (TimeAndRid o : list) {

			DateTimeFormatter toHourWithMinute = DateTimeFormat
					.forPattern(Messages.getString("DailyReportView.timepattern")); //$NON-NLS-1$
			String time = toHourWithMinute.print(o.getDt());
			int rid = o.getRid();
			String readerName = mapOfReaders.get(rid).getName();

			model.addRow(new Object[] { time, readerName });
		}
	}
}
