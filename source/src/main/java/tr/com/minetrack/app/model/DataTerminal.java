package tr.com.minetrack.app.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import tr.com.minetrack.app.logging.LoggerImpl;
import tr.com.minetrack.app.logging.util.ExceptionToString;
import tr.com.minetrack.app.model.lists.RFIDReaderList;
import tr.com.minetrack.app.model.lists.SignalList;

public class DataTerminal {
	// instance variables
	private final String ip = "192.168.1.2";
	private final int portNumber = 4001;

	private volatile boolean state;

	private DataInputStream input;
	private DataOutputStream output;

	private Socket socket;

	// static variables for write command
	private static final String START = "aa";
	private static final String START2 = "aa0";
	private static final String END = "03";

	/**
	 * tek nesne
	 */
	private static volatile DataTerminal terminalInstance;// new DataTerminal("192.168.1.2", 4001);

	/**
	 * Double check locking
	 * 
	 * @return
	 */
	private static Object lock = new Object();

	public static DataTerminal getInstance() {
		if (terminalInstance == null) {
			// double checked locking
			synchronized (lock) {
				if (terminalInstance == null) {
					terminalInstance = new DataTerminal();
				}
			}
		}

		return terminalInstance;
	}

	// contructor
	private DataTerminal() {

	}

	// connection method
	public void connect() {

		try {
			if (socket != null) {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
				socket.close();
				socket = null;
			}
			if (socket == null) // ilk basildiginda buraya gir
			{
				socket = new Socket();

				socket.connect(new InetSocketAddress(ip, portNumber)); /* Terminal IP ve Port Number */
				input = new DataInputStream(socket.getInputStream());

				output = new DataOutputStream(socket.getOutputStream());

				this.state = true;
			} else if (input == null || output == null) {
				showIPv4MessageDialog("connect");
				disconnect();
			}

		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			showIPv4MessageDialog("connect");
		}
	}

	private void showIPv4MessageDialog(String msg) {

		JOptionPane.showMessageDialog(null, "Wrong IPv4 Settings" + ", " + msg, "Local Area Connection",
				JOptionPane.ERROR_MESSAGE);

	}

	// disconnection method
	public void disconnect() {
		if (state) {
			try {
				this.state = false;
			} catch (Exception e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}
		}
	}

	// getter for state
	public boolean getState() {
		return this.state;
	}

	// write and read methods
	public void write() {

		HashMap<Integer, RFIDReader> mapOfReader = RFIDReaderList.getInstance().getList();

		for (RFIDReader r : mapOfReader.values()) {
			if (state) {
				String readerID = Integer.toString(r.getId());
				String ask = START + readerID + END;
				if (ask.length() % 2 != 0) {
					ask = START2 + readerID + END;
				}
				byte[] arrayOfBytes = javax.xml.bind.DatatypeConverter.parseHexBinary(ask);
				try {
					output.write(arrayOfBytes);
					output.flush();
					Thread.sleep(50);
					Thread.yield();
				} catch (IOException e) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
				} catch (InterruptedException e) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
				}
			}
		}
	}

	public void read() {
		int beginChar;
		List<Integer> bytes = new ArrayList<Integer>();

		try {
			if (input.available() == 0)
				return;
			beginChar = input.read();
			if (beginChar == 0x43) {
				input.read();// datalength
				bytes = readBytes();
				if (calcXorAndCheck(bytes)) {
					SignalList.getInstance().add(bytes);
				}
			} else if (beginChar == 0x7E) {
				input.read();// data length 14byte
				bytes = read14Bytes();
				// printBytes(bytes, "read");
				SignalList.getInstance().add(bytes);
			}
		} catch (IOException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}

	}

	void printBytes(List<Integer> bytes, String callMethod) {

		System.out.println("start:");
		for (int i = 0; i < bytes.size(); i++) {
			System.out.println(i + ".byte : " + Integer.toHexString(bytes.get(i)));
		}
		System.out.println("rssi: " + Integer.toHexString(bytes.get(14)));
		System.out.println("end!");
	}

	// helper method for reading
	private List<Integer> readBytes() {
		List<Integer> bytes = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			try {
				bytes.add(input.read());
			} catch (IOException e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}
		}
		// also read check code and end code
		try {
			bytes.add(input.read());// check code
			bytes.add(input.read());// end code
		} catch (IOException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return bytes;
	}

	boolean calcXorAndCheck(List<Integer> arr) {
		int result = 0;

		result = result ^ 0x43;
		result = result ^ 0x09;
		for (int i = 0; i < 9; i++) {
			result = result ^ arr.get(i);
		}
		return result == arr.get(9);
	}

	private List<Integer> read14Bytes() {
		List<Integer> bytes = new ArrayList<Integer>();

		for (int i = 0; i < 14; i++) {
			try {
				bytes.add(input.read());
			} catch (IOException e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}
		}
		// printBytes(bytes, "read14Bytes");
		// remove unnecessary bytes
		for (int i = 0; i <= 5; i++) {
			try {
				bytes.remove(7);
			} catch (IndexOutOfBoundsException e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}
		}
		int tmp = bytes.get(7);
		try {
			bytes.remove(7);
		} catch (IndexOutOfBoundsException e1) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e1));
		}
		bytes.add(3, tmp);
		bytes.add(4, 0);
		//

		// also read check code and end code
		int checkcode;
		try {
			checkcode = input.read();
			bytes.add(checkcode);// check code

			bytes.add(input.read());// end code
		} catch (IOException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return bytes;
	}
}
