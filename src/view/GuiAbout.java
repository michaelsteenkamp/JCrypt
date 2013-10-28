package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class GuiAbout extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiAbout frame = new GuiAbout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiAbout() {
		setTitle("JCrpyto - About");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtAbout = new JTextArea();
		txtAbout.setEditable(false);
		txtAbout.setText("-------------------------------------------------------------------------------------------------------------------------------" + "\n"
						+ "	   J Crpyto - Keeping your files safe. " + "\n"
						+ "-------------------------------------------------------------------------------------------------------------------------------" + "\n" + "\n"
						+ " - J crypto uses AES(Advanced Encryption Standard) to keep your" + "\n" + " text files safe." + "\n" 
						+ " - The simple interface allows you to effortlessly save, open" + "\n" + "    and edit encrypted text files." + "\n"
						+ " - To get started either click new, or open an existing text file. " + "\n"
						+ " - Please save files with the .crypt extension." + "\n"
						+ "\n" + "\n" + "\n"
						+ "-------------------------------------------------------------------------------------------------------------------------------" + "\n"
						+ "	  Coded by Michael Steenkamp " + "\n"
						+ "-------------------------------------------------------------------------------------------------------------------------------" + "\n");
		txtAbout.setBounds(12, 12, 420, 249);
		contentPane.add(txtAbout);
	}
}
