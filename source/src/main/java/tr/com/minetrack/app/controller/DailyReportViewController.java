/**
 * 
 */
package tr.com.minetrack.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import tr.com.minetrack.app.model.Tracked;
import tr.com.minetrack.app.model.lists.TrackedList;
import tr.com.minetrack.app.view.dialogs.DailyReportView;

/**
 * @author Gafur Hayytbayev
 *
 */
public class DailyReportViewController implements ActionListener {

	private final DailyReportView parent;

	public DailyReportViewController(final DailyReportView parent) {
		this.parent = parent;
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
		case "show":
			showDailyReport(model);
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
			Export.toExcel(parent.getTable(), selectedFile);
			//System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}

	}

	private void showDailyReport(final DefaultTableModel model) {
		//System.out.println("show daily report");

		model.setRowCount(0);

		DateTime thisDay = new DateTime();

		// tarih aralıgını gungun sorgu yap
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTime dt1 = formatter.parseDateTime(parent.getEntryDate1().getText());
		DateTime dt2 = formatter.parseDateTime(parent.getEntryDate2().getText());

		if ((dt1.equals(dt2) || dt1.isBefore(dt2)) && (dt1.equals(thisDay) || dt1.isBefore(thisDay))) {
			//System.out.println("true");

			//
			HashMap<Integer, Tracked> trackedList = TrackedList.getInstance().getList();

			for (DateTime date1 = dt1; date1.isBefore(dt2.plusDays(1)); date1 = date1.plusDays(1)) {
				int countOfRows = 0;
				for (Integer tid : trackedList.keySet()) {
					Tracked tracked = trackedList.get(tid);

					// tid belli
					// date 1 = date belli
					// date 2
					DateTime date2 = date1.plusDays(1);
					String dateStr1 = outputFormatter.print(date1);
					String dateStr2 = outputFormatter.print(date2);
					HashMap<Integer, DateTime> dates = DAOHelper.getDailyReportDAO()
							.get(new String[] { "" + tid, dateStr1, dateStr2 });

					if (!dates.isEmpty()) {
						DateTimeFormatter toHourWithMinute = DateTimeFormat.forPattern("HH:mm:ss");
						String enterTime = toHourWithMinute.print(dates.get(0));
						String exitTime = toHourWithMinute.print(dates.get(1));

						model.addRow(new Object[] { tracked.getTagId(), tracked.getFname() + " " + tracked.getLname(),
								enterTime, exitTime, dateStr1 });
						countOfRows++;
					}

				}
				if (countOfRows != 0) {
					model.addRow(new Object[] {});
				}
			}

//			System.out.println("dt1: " + dt1);
//			System.out.println("dt2: " + dt2);
//			System.out.println("now: " + thisDay);
		} else {
//			System.out.println("false");
//			System.out.println("dt1: " + dt1);
//			System.out.println("dt2: " + dt2);
//			System.out.println("now: " + thisDay);
		}
	}

}
