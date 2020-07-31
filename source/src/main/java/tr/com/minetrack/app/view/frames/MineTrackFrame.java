package tr.com.minetrack.app.view.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.geotools.swing.MapLayerTable;
import org.geotools.swing.action.InfoAction;
import org.geotools.swing.action.NoToolAction;
import org.geotools.swing.action.PanAction;
import org.geotools.swing.action.ResetAction;
import org.geotools.swing.action.ZoomInAction;
import org.geotools.swing.action.ZoomOutAction;
import org.geotools.swing.control.JMapStatusBar;
import org.geotools.swing.tool.ScrollWheelTool;

import net.miginfocom.swing.MigLayout;
import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.model.Tracked;
import tr.com.minetrack.app.view.abstracts.AbstractMineTrackFrame;

@SuppressWarnings("serial")
public class MineTrackFrame extends AbstractMineTrackFrame {

	/**
	 * 
	 */

	private JTable table;
	private DefaultTableModel trackedModel;

	private Lock setTrackedModelLock = new ReentrantLock();

	@Override
	public void initComponents() {
		if (uiSet) {
			// @todo log a warning ?
			return;
		}

		/*
		 * We use the MigLayout manager to make it easy to manually code our UI design
		 */
		StringBuilder sb = new StringBuilder();
		if (!toolSet.isEmpty()) {
			sb.append("[]"); // fixed size //$NON-NLS-1$
		}
		sb.append("[grow]"); // map pane and optionally layer table fill space //$NON-NLS-1$
		if (showStatusBar) {
			sb.append("[min!]"); // status bar height //$NON-NLS-1$
		}

		JPanel panel = new JPanel(new MigLayout("wrap 1, insets 0", // layout constrains: 1 component per //$NON-NLS-1$
				// row, no
				// insets
				"[grow]", // column constraints: col grows when frame is resized //$NON-NLS-1$
				sb.toString()));

		/*
		 * A toolbar with buttons for zooming in, zooming out, panning, and resetting
		 * the map to its full extent. The cursor tool buttons (zooming and panning) are
		 * put in a ButtonGroup.
		 *
		 * Note the use of the XXXAction objects which makes constructing the tool bar
		 * buttons very simple.
		 */
		if (showToolBar) {
			toolBar = new JToolBar();
			toolBar.setOrientation(JToolBar.HORIZONTAL);
			toolBar.setFloatable(false);

			JButton btn;
			ButtonGroup cursorToolGrp = new ButtonGroup();
			if (toolSet.contains(Tool.SCROLLWHEEL)) {
				mapPane.addMouseListener(new ScrollWheelTool(mapPane));
			}
			if (toolSet.contains(Tool.POINTER)) {
				btn = new JButton(new NoToolAction(mapPane));
				btn.setName(TOOLBAR_POINTER_BUTTON_NAME);
				toolBar.add(btn);
				cursorToolGrp.add(btn);
			}

			if (toolSet.contains(Tool.ZOOM)) {
				btn = new JButton(new ZoomInAction(mapPane));
				btn.setName(TOOLBAR_ZOOMIN_BUTTON_NAME);
				toolBar.add(btn);
				cursorToolGrp.add(btn);

				btn = new JButton(new ZoomOutAction(mapPane));
				btn.setName(TOOLBAR_ZOOMOUT_BUTTON_NAME);
				toolBar.add(btn);
				cursorToolGrp.add(btn);

				toolBar.addSeparator();
			}

			if (toolSet.contains(Tool.PAN)) {
				btn = new JButton(new PanAction(mapPane));
				btn.setName(TOOLBAR_PAN_BUTTON_NAME);
				toolBar.add(btn);
				cursorToolGrp.add(btn);

				toolBar.addSeparator();
			}

			if (toolSet.contains(Tool.INFO)) {
				btn = new JButton(new InfoAction(mapPane));
				btn.setName(TOOLBAR_INFO_BUTTON_NAME);
				toolBar.add(btn);

				toolBar.addSeparator();
			}

			if (toolSet.contains(Tool.RESET)) {
				btn = new JButton(new ResetAction(mapPane));
				btn.setName(TOOLBAR_RESET_BUTTON_NAME);
				toolBar.add(btn);
			}

			panel.add(toolBar, "grow"); //$NON-NLS-1$
		}

		if (showPersonelTable) {

			trackedModel = new DefaultTableModel() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			table = new JTable(trackedModel);
			trackedModel.addColumn("ID"); //$NON-NLS-1$
			trackedModel.addColumn("Adı"); //$NON-NLS-1$
			trackedModel.addColumn("Soyadı"); //$NON-NLS-1$
			trackedModel.addColumn("Tag ID"); //$NON-NLS-1$
			trackedModel.addColumn(Messages.getString("MineTrackFrame.location")); //$NON-NLS-1$

			JScrollPane tableScrollPane = new JScrollPane(table);
			tableScrollPane.setPreferredSize(new Dimension(-1, 200));

			TitledBorder border = new TitledBorder("Takip Edilen"); //$NON-NLS-1$
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.TOP);

			tableScrollPane.setBorder(border);

			JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, mapPane, tableScrollPane);

			mapPanel.add(splitPane, BorderLayout.SOUTH);
			mapPanel.add(mapPane, BorderLayout.CENTER);
		}

		if (showLayerTable) {
			mapLayerTable = new MapLayerTable(mapPane);

			/*
			 * We put the map layer panel and the map pane into a JSplitPane so that the
			 * user can adjust their relative sizes as needed during a session. The call to
			 * setPreferredSize for the layer panel has the effect of setting the initial
			 * position of the JSplitPane divider
			 */
			mapLayerTable.setPreferredSize(new Dimension(200, -1));
			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, mapLayerTable, mapPanel);
			panel.add(splitPane, "grow"); //$NON-NLS-1$

		} else {
			/*
			 * No layer table, just the map pane
			 */
			panel.add(mapPanel, "grow"); //$NON-NLS-1$
		}

		if (showStatusBar) {
			panel.add(JMapStatusBar.createDefaultStatusBar(mapPane), "grow"); //$NON-NLS-1$
		}

		this.getContentPane().add(panel);
		uiSet = true;

	}

	public void setTrackedModel(HashMap<Integer, Tracked> mapOfTracked) {
		//
		setTrackedModelLock.lock();
		try {
			for (int i = 0; i < trackedModel.getRowCount(); i++) {
				trackedModel.removeRow(i);
			}
			trackedModel.setRowCount(0);
			int id = 0;
			for (Map.Entry<Integer, Tracked> entry : mapOfTracked.entrySet()) {
				int tagid = entry.getKey();
				Tracked v = entry.getValue();
				if (v.isState())
					trackedModel.addRow(new Object[] { id++, v.getFname(), v.getLname(), tagid, v.getKonum() });
			}
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		} finally {
			setTrackedModelLock.unlock();
		}
	}

	public void clearTrackedModel() {
		try {
			for (int i = 0; i < trackedModel.getRowCount(); i++) {
				trackedModel.removeRow(i);
			}
			trackedModel.setRowCount(0);
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
	}
}
