package tr.com.minetrack.app.view.msgdialogs;

import javax.swing.JOptionPane;

import tr.com.minetrack.app.messages.Messages;
import tr.com.minetrack.app.view.UI;

public class TerminalStatus {
	public TerminalStatus(UI parent, boolean state) {
		// default title and icon
		if (state) {
			JOptionPane.showMessageDialog(parent.getFrame(), Messages.getString("TerminalStatus.connected"), //$NON-NLS-1$
					Messages.getString("TerminalStatus.state"), //$NON-NLS-1$
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(parent.getFrame(), Messages.getString("TerminalStatus.notconnected"), //$NON-NLS-1$
					Messages.getString("TerminalStatus.state"), //$NON-NLS-1$
					JOptionPane.WARNING_MESSAGE);
		}

	}
}
