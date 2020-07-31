package tr.com.minetrack.app.view.dialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import tr.com.minetrack.app.controller.DetailedReportController;
import tr.com.minetrack.app.db.DAOHelper;
import tr.com.minetrack.app.helpers.DateLabelFormatter;
import tr.com.minetrack.app.helpers.TimeAndRid;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.RFIDReader;
import tr.com.minetrack.app.model.Tracked;
import tr.com.minetrack.app.model.lists.RFIDReaderList;
import tr.com.minetrack.app.model.lists.TrackedList;

public class DetailedReportView extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;
	private JFormattedTextField entryDate1;
	private JDatePickerImpl datePicker1;
	private JComboBox<Tracked> adSoyad;

	private DetailedReportController controller = new DetailedReportController(this);

	public DetailedReportView(JFrame jFrame) {
		super(jFrame, "Ayrıntılı Rapor", true);

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
		model.addColumn("Saat");
		model.addColumn("Konum");

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

		JButton exportBtn = new JButton("Dışarı Aktar");
		exportBtn.setName("export");
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

		//
		addDataTo(model);
		//

		this.center(jFrame);
		this.pack();
		panel.revalidate();
		this.setVisible(true);
	}

	private JPanel createDetailsPanel() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		JLabel labelEntryTime = new JLabel("Tarih");
		JLabel fnamlnamelabel = new JLabel("Ad/Soyad");

		adSoyad = new JComboBox<>();

		HashMap<Integer, Tracked> trackedList = TrackedList.getInstance().getList();
		for (Integer tid : trackedList.keySet()) {

			adSoyad.addItem(trackedList.get(tid));
		}

		JButton showBtn = new JButton("Göster");
		showBtn.setName("show");
		showBtn.addActionListener(controller);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);

		// label entry time
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(labelEntryTime, gbc);

		// entry text field
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(entryDate1, gbc);

		// entry date picker
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(datePicker1, gbc);

		// label fname/lname
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(fnamlnamelabel, gbc);

		// combobox fname/lname
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(adSoyad, gbc);

		// show button
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(showBtn, gbc);

		Border loweredbevel = BorderFactory.createLoweredBevelBorder();

		Border compound = BorderFactory.createTitledBorder(loweredbevel, "Tarih Seçiniz");

		panel.setBorder(compound);

		panel.setPreferredSize(new Dimension(650, 150));
		// panel.revalidate();
		return panel;
	}

	private void center(JFrame jFrame) {
		Rectangle r = jFrame.getBounds();
		int x = r.x + (r.width - this.getSize().width) / 2;
		int y = r.y + (r.height - this.getSize().height) / 2;
		this.setLocation(x, y);
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
	}

	/**
	 * @param model
	 */
	private void addDataTo(DefaultTableModel model) {
		HashMap<Integer, RFIDReader> mapOfReaders = RFIDReaderList.getInstance().getList();

		// tarih sorgu yap
		DateTimeFormatter formatter = DateTimeFormat.forPattern(Messages.getString("DailyReportView.datepattern"));
		DateTime dt1 = formatter.parseDateTime(getEntryDate1().getText());
		DateTime dt2 = dt1.plusDays(1); // bir sonraki gun

		String nameSpaceSurname = this.getAdSoyadBox().getItemAt(0).toString();
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

	public JFormattedTextField getEntryDate1() {
		return entryDate1;
	}

	public JComboBox<Tracked> getAdSoyadBox() {
		return adSoyad;
	}

	public JTable getTable() {
		return table;
	}
}
