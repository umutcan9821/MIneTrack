package tr.com.minetrack.app.view.dialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import tr.com.minetrack.app.controller.DailyReportViewController;
import tr.com.minetrack.app.db.DAOHelper;
import tr.com.minetrack.app.helpers.DateLabelFormatter;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.Tracked;
import tr.com.minetrack.app.model.lists.TrackedList;

public class DailyReportView extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;
	private JFormattedTextField entryDate1;
	private JFormattedTextField entryDate2;
	private JDatePickerImpl datePicker1;
	private JDatePickerImpl datePicker2;

	private DailyReportViewController controller = new DailyReportViewController(this);

	public DailyReportView(JFrame frame) {
		super(frame, Messages.getString("DailyReportView.dailyreport"), true);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new GridBagLayout());
		this.getContentPane().add(panel);

		//
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

		setDefaultDate();

		table = new JTable(model);

		model.addColumn(Messages.getString("DailyReportView.tagid")); //$NON-NLS-1$
		model.addColumn(Messages.getString("DailyReportView.flname")); //$NON-NLS-1$
		model.addColumn(Messages.getString("DailyReportView.entertime")); //$NON-NLS-1$
		model.addColumn(Messages.getString("DailyReportView.exittime")); //$NON-NLS-1$
		model.addColumn(Messages.getString("DailyReportView.date")); //$NON-NLS-1$
		// model.addColumn("Date");

		//
		addDataTo(model);

		//

		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setPreferredSize(new Dimension(650, 350));

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(createDetailsPanel(), gbc);

		// table
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(tableScrollPane, gbc);

		JButton exportBtn = new JButton(Messages.getString("DailyReportView.export")); //$NON-NLS-1$
		exportBtn.setName("export"); //$NON-NLS-1$
		exportBtn.addActionListener(controller);
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(exportBtn, gbc);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		int width = 700;
		int height = 500;

		this.setBounds(dimension.width / 2 - width / 2, dimension.height / 2 - height / 2, width, height);

		this.center(frame);
		this.pack();
		this.revalidate();
		this.setVisible(true);
	}

	/**
	 * @param model
	 */
	private void addDataTo(DefaultTableModel model) {
		HashMap<Integer, Tracked> trackedList = TrackedList.getInstance().getList();

		// tarih aralıgını gungun sorgu yap
		DateTimeFormatter formatter = DateTimeFormat.forPattern(Messages.getString("DailyReportView.datepattern")); //$NON-NLS-1$
		DateTimeFormatter outputFormatter = DateTimeFormat
				.forPattern(Messages.getString("DailyReportView.datepattern")); //$NON-NLS-1$
		DateTime dt1 = formatter.parseDateTime(getEntryDate1().getText());
		DateTime dt2 = formatter.parseDateTime(getEntryDate2().getText());

		for (DateTime date1 = dt1; date1.isBefore(dt2.plusDays(1)); date1 = date1.plusDays(1)) {
			for (Integer tid : trackedList.keySet()) {
				Tracked tracked = trackedList.get(tid);

				// tid belli
				// date 1 = date belli
				// date 2
				DateTime date2 = date1.plusDays(1);
				String dateStr1 = outputFormatter.print(date1);
				String dateStr2 = outputFormatter.print(date2);
				HashMap<Integer, DateTime> dates = DAOHelper.getDailyReportDAO()
						.get(new String[] { "" + tid, dateStr1, dateStr2 }); //$NON-NLS-1$

				if (!dates.isEmpty()) {
					DateTimeFormatter toHourWithMinute = DateTimeFormat
							.forPattern(Messages.getString("DailyReportView.timepattern")); //$NON-NLS-1$
					String enterTime = toHourWithMinute.print(dates.get(0));
					String exitTime = toHourWithMinute.print(dates.get(1));

					model.addRow(new Object[] { tracked.getTagId(), tracked.getFname() + " " + tracked.getLname(), //$NON-NLS-1$
							enterTime, exitTime, dateStr1 });
				}

			}
			model.addRow(new Object[] {});
		}
	}

	/**
	 * 
	 */
	private void setDefaultDate() {
		UtilDateModel model1 = new UtilDateModel();
		model1.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model1);
		datePicker1 = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		entryDate1 = datePicker1.getJFormattedTextField();

		UtilDateModel model2 = new UtilDateModel();
		model2.setSelected(true);
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2);
		datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		entryDate2 = datePicker2.getJFormattedTextField();
	}

	private JPanel createDetailsPanel() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		JLabel labelEntryTime = new JLabel(Messages.getString("DailyReportView.startdate")); //$NON-NLS-1$
		JLabel labelExitTime = new JLabel(Messages.getString("DailyReportView.enddate")); //$NON-NLS-1$

		JButton showBtn = new JButton(Messages.getString("DailyReportView.show")); //$NON-NLS-1$
		showBtn.setName("show"); //$NON-NLS-1$
		showBtn.addActionListener(controller);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);

		// label 1
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(labelEntryTime, gbc);

		// entry text field
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(entryDate1, gbc);// entry date 1

		// date picker
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(datePicker1, gbc);

		// label 2
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(labelExitTime, gbc);

		// entry text field
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(entryDate2, gbc);// entry date 2

		// exit text field
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(datePicker2, gbc);

		// show button
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(showBtn, gbc);

		Border loweredbevel = BorderFactory.createLoweredBevelBorder();

		Border compound = BorderFactory.createTitledBorder(loweredbevel,
				Messages.getString("DailyReportView.choosedates")); //$NON-NLS-1$

		panel.setBorder(compound);

		panel.setPreferredSize(new Dimension(650, 150));
		// panel.revalidate();
		return panel;
	}

	public JTable getTable() {
		return table;
	}

	private void center(JFrame jFrame) {
		Rectangle r = jFrame.getBounds();
		int x = r.x + (r.width - this.getSize().width) / 2;
		int y = r.y + (r.height - this.getSize().height) / 2;
		this.setLocation(x, y);
	}

	public JFormattedTextField getEntryDate1() {
		return entryDate1;
	}

	public JFormattedTextField getEntryDate2() {
		return entryDate2;
	}

}
