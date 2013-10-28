package view;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;



import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


import model.Encryption;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class GuiMain extends JFrame {

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
					GuiMain frame = new GuiMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public static JTextArea textArea = null;
	public static JLabel lblStatus = null;
	public static boolean fileOpen = false;
	boolean fileEncrypted = false;
	public static boolean caught = false;

	/**
	 * Create the frame.
	 */
	public GuiMain() {
		setTitle("J-Crypto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 552, 489);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mnuOpen = new JMenuItem("Open (Decrypt)");
		mnFile.add(mnuOpen);
		mnuOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				//Open byte file, ask for security key and show result in text box
				if(fileOpen){
					lblStatus.setText("WARNING: Current file should be saved to avoid overwriting");
				}
				String passphrase = JOptionPane.showInputDialog("Enter decryption key");
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();
				//In response to a button click:
				int returnVal = fc.showOpenDialog(null);
				byte[] cipherText = null;
				FileInputStream fis = null;
				boolean cancelled = false;
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					int len = (int)(file.length());
					cipherText = new byte[len];
					try {
						fis = new FileInputStream(file);
						fis.read(cipherText);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else{
					lblStatus.setText("File open cancelled");
					cancelled = true;
				}
				caught = false;
				Encryption e = new Encryption(passphrase);
				e.setCipherText(cipherText);
				
				try{
					e.decrypt();
				}
				catch(Exception j){
					caught = true;
				}
				if(caught && !cancelled){
					lblStatus.setText("Incorrect decryption key");
					fileOpen = false;
					textArea.setEnabled(false);
				}
				else if(!caught && !cancelled){
					textArea.setText(e.getPlainText());
					fileOpen = true;
					textArea.setEnabled(true);
					lblStatus.setText("File open successful");
				}

					
				
			}
		});
		
		JMenuItem mnuEncrypt = new JMenuItem("Save (Encrypt)");
		mnFile.add(mnuEncrypt);
		mnuEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!fileOpen){
					lblStatus.setText("No file currently open");
				}
				else{
					lblStatus.setText("Select or create a new file. Use the .crypt extension");
					String passphrase = JOptionPane.showInputDialog("Enter encryption key");
					//Create a file chooser
					final JFileChooser fc = new JFileChooser();
					//In response to a button click:
					int returnVal = fc.showSaveDialog(null);
					FileOutputStream fop = null;
					if(returnVal == JFileChooser.APPROVE_OPTION){
						File file = fc.getSelectedFile();
						try {
							fop = new FileOutputStream(file);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						
					}
					Encryption e = new Encryption(passphrase);
					e.setPlainText(textArea.getText());
					e.encrypt();
					try {
						fop.write(e.getCipherText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						fop.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					textArea.setText("");
					textArea.setEnabled(false);
					fileOpen = false;
					lblStatus.setText("File saved successfully");
					
				}
			
			}
		});
		mnFile.addSeparator();
		JMenuItem mnuNew = new JMenuItem("Create New");
		mnFile.add(mnuNew);
		mnuNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileOpen = true;
				textArea.setEnabled(true);
				lblStatus.setText("File created");
			}
		});
		
		JMenu mnOther = new JMenu("Other");
		menuBar.add(mnOther);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblStatus = new JLabel("Open or create file to begin editing");
		lblStatus.setBounds(12, 413, 522, 15);
		contentPane.add(lblStatus);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 522, 389);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEnabled(false);
		
		JMenuItem mnuAbout = new JMenuItem("About");
		mnOther.add(mnuAbout);
		mnuAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GuiAbout about = new GuiAbout();
				about.setVisible(true);
				
			}
		});
		
		//Add separator between options
		mnOther.addSeparator();
		
		JMenuItem mnuExit = new JMenuItem("Exit");
		mnOther.add(mnuExit);
		mnuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}
}
