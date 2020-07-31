package tr.com.minetrack.app.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.messages.Messages;

public class Export {
	private static Workbook workbook;

	public static void toExcel(JTable table, File file) {
		TableModel model = table.getModel();

		workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Contacts"); //$NON-NLS-1$

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < model.getColumnCount(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(model.getColumnName(i));
			cell.setCellStyle(headerCellStyle);
		}

		// Create Other rows and cells with contacts data

		for (int i = 0; i < model.getRowCount(); i++) {
			Row row = sheet.createRow(i + 1);
			for (int j = 0; j < model.getColumnCount(); j++) {
				try {
					row.createCell(j).setCellValue(model.getValueAt(i, j).toString());
				} catch (Exception e) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
					break;
				}
			}
		}

		// Resize all columns to fit the content size
		for (int i = 0; i < model.getColumnCount(); i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the output to a file
		try {
			FileOutputStream fileOut = new FileOutputStream(file + ".xlsx"); //$NON-NLS-1$
			workbook.write(fileOut);
			fileOut.close();

			JOptionPane.showMessageDialog(null, Messages.getString("Export.3") + file + Messages.getString("Export.4"), //$NON-NLS-1$ //$NON-NLS-2$
					Messages.getString("Export.5"), //$NON-NLS-1$
					JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		} catch (IOException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
	}
}
