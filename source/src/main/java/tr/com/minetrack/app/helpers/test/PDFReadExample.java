package tr.com.minetrack.app.helpers.test;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;

public class PDFReadExample {
	public static void main(String args[]) {
		try {
			// Create PdfReader instance.
			PdfReader pdfReader = new PdfReader("data/depo/kullanim-dok.pdf");

			// Get the number of pages in pdf.
			int pages = pdfReader.getNumberOfPages();

			// Iterate the pdf through pages.
			for (int i = 1; i <= pages; i++) {
				// Extract the page content using PdfTextExtractor.
				String pageContent = PdfTextExtractor.getTextFromPage(pdfReader, i);

				// Print the page content on console.
				System.out.println("Content on Page " + i + ": " + pageContent);
			}

			// Close the PdfReader.
			pdfReader.close();
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
	}
}
