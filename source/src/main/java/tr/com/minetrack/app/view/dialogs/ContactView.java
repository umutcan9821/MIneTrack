package tr.com.minetrack.app.view.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class ContactView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ContactView dialog = new ContactView(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
	}

	/**
	 * Create the dialog.
	 */
	public ContactView(Frame frame) {
		super(frame, "Contact", true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel panel = new JPanel();
			// JPanel imgpanel = new ImagePanel();

			BufferedImage myPicture = null;
			try {
				File file = new File("data/images/minesoft.jpg");
				myPicture = ImageIO.read(file);
			} catch (Exception e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}

			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setVerticalAlignment(SwingConstants.TOP);

			picLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

			panel.add(picLabel, BorderLayout.NORTH);
			{
				JPanel contactinfo = new JPanel();
				contactinfo.setBorder(
						new TitledBorder(null, "Contact Info", TitledBorder.CENTER, TitledBorder.TOP, null, null));
				panel.add(contactinfo, BorderLayout.CENTER);
				{

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

					model.addColumn("Position");
					model.addColumn("First/Last Name");
					model.addColumn("Phone Number");
					model.addColumn("Email");

					Object[] rowData1 = { "Genel Müdür", "Mahmut ÇAVUR", "+90 552 226 22 15",
							"mahmutcavur@minesoft.com.tr" };
					model.addRow(rowData1);
					Object[] rowData2 = { "AR-GE Müdürü", "Gafur HAYYTBAYEV", "+90 552 226 22 13",
							"h.gafur@minesoft.com.tr" };
					model.addRow(rowData2);
					Object[] rowData3 = { "Proje Koordinatörü", "Alper SÜZEN", "+90 532 745 91 86",
							"alper@minesoft.com.tr" };
					model.addRow(rowData3);

					setJTableColumnsWidth(table, 600, 50, 50, 50, 80);
					table.setBounds(100, 100, 500, 100);

					// table = new JTable();
					contactinfo.add(table);
				}
			}
			// contentPanel.add(imgpanel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");

				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}

		}
		this.center(frame);
		setVisible(true);
	}

	void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
		double total = 0;
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			total += percentages[i];
		}

		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth((int) (tablePreferredWidth * (percentages[i] / total)));
		}
	}

	private void center(Frame frame) {
		Rectangle r = frame.getBounds();
		int x = r.x + (r.width - this.getSize().width) / 2;
		int y = r.y + (r.height - this.getSize().height) / 2;
		this.setLocation(x, y);
	}
}