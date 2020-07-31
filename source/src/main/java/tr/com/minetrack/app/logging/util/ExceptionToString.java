package tr.com.minetrack.app.logging.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionToString {
	public static String convert(Exception e) {
		StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
	}
}
