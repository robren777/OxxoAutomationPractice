package RDM.utils.password;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PassMessagePane {

	public PassMessagePane(String  encryptPass) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}
				JTextArea ta = new JTextArea(1,50);
				ta.setText(encryptPass);
				ta.setWrapStyleWord(true);
				ta.setLineWrap(true);
				ta.setCaretPosition(0);
				ta.setEditable(false);
				JOptionPane.showMessageDialog(null, new JScrollPane(ta), "Encrypt password", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
}
