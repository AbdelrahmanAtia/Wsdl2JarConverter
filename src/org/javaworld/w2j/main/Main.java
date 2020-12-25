package org.javaworld.w2j.main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.javaworld.w2j.exception.Handler;
import org.javaworld.w2j.logging.AppLogger;
import org.javaworld.w2j.logging.TextAreaHandler;
import org.javaworld.w2j.model.Library;
import org.javaworld.w2j.util.FileUtil;
import org.javaworld.w2j.util.PropertiesUtil;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JScrollPane;


/**
 * 
 * @author O-AbdelRahman.Attya
 *
 *issues >> 
 * 
 * 0-  improve logging performance
 * 1-  allow converting multiple wsdls
 * 2-  make properties util use Fileutil.isExist() in static initializer
 * 3-  improve add property by allowing adding multiple key & value
 *     pairs in one shot
 * 4-  make create & delete folder in FileUtil uses nio library instead of cmd commands
 * 5-  allow generating dependencies text file
 * 6-   
 * 7-  change project name to w2j
 * 8-  find a better way for handling input validation by using setInputVerifier()
 *     method of the text field
 * 9-  create a log file for the project.
 * 10- adjust spacing between logs.
 * 11- change error logs color to red.
 * 
 * 
 * Done
 * =====
 * 	>> create properties file if not exist inside add property method
 *  >> handle case when wsdl file path field is empty or invalid in both gui and generator class
 *  >> add field validation to GUI.
 *  >> allow generating jars with different libraries:-
 *     apache cxf & wsimport   use a check box to specify the library
 *  >> use java default logger for logging
 *  >> find a better way for button shape during generation
 */

public class Main {
	
	private static final AppLogger logger = AppLogger.getLogger();
	
	private JFrame frame;
	private JTextField javaBinPathField;
	private JTextField apacheCxfBinPathField;
	private JTextField wsdlPathField;
	private JLabel lblNewLabel_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private String library = Library.APACHE_CXF.toString();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public Main() {
		// set exception handler for all threads 
		Thread.setDefaultUncaughtExceptionHandler(new Handler());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		
		//create the frame
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBounds(100, 100, 516, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// java bin path field label
		JLabel lblNewLabel = new JLabel("Java Bin Path:");
		lblNewLabel.setBounds(26, 34, 121, 20);
		frame.getContentPane().add(lblNewLabel);
		
		//java bin path text field
		javaBinPathField = new JTextField();
		javaBinPathField.setText("");
		javaBinPathField.setBounds(167, 31, 320, 23);
		frame.getContentPane().add(javaBinPathField);
		javaBinPathField.setColumns(10);
						
		
		JPanel panel = new JPanel();
		panel.setBounds(26, 114, 461, 120);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		apacheCxfBinPathField = new JTextField();
		apacheCxfBinPathField.setBounds(141, 0, 320, 22);
		panel.add(apacheCxfBinPathField);
		apacheCxfBinPathField.setColumns(10);
		
		JLabel apacheCxfFieldLabel = new JLabel("Apache Cxf Bin Path:");
		apacheCxfFieldLabel.setBounds(0, 0, 121, 23);
		panel.add(apacheCxfFieldLabel);
		
		lblNewLabel_2 = new JLabel("Wsdl Path:");
		lblNewLabel_2.setBounds(0, 34, 121, 23);
		panel.add(lblNewLabel_2);
		
		
		wsdlPathField = new JTextField();
		wsdlPathField.setBounds(141, 37, 320, 22);
		panel.add(wsdlPathField);
		wsdlPathField.setColumns(10);
		
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.setBounds(372, 82, 89, 36);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(e -> {
			
			String javaBinPath = javaBinPathField.getText().trim();
			String apacheCxfBinPath = apacheCxfBinPathField.getText().trim();
			String wsdlPath = wsdlPathField.getText().trim();
			
			// validate java bin path field
			if (javaBinPath.isEmpty() || !FileUtil.isExist(javaBinPath + "\\jar.exe")) {
				JOptionPane.showMessageDialog(null, "java bin path field is invalid");
				return;
			}
			
			// validate Apache CXF bin path field
			if (apacheCxfBinPath.isEmpty() || !FileUtil.isExist(apacheCxfBinPath + "\\wsdl2java.bat")) {
				JOptionPane.showMessageDialog(null, "Apache CXF bin path field is invalid");
				return;
			}
			
			// validate WSDL file path
			if (!wsdlPath.toLowerCase().endsWith(".wsdl") || !FileUtil.isExist(wsdlPath)) {
				JOptionPane.showMessageDialog(null, "WSDL path field is invalid");
				return;
			}
			
			// generate jar at WSDL path
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					
					btnNewButton.setEnabled(false);
					
					Generator generator = new Generator(wsdlPath, javaBinPath, apacheCxfBinPath, library);
					generator.generate();
					
					//save bin paths in properties file
					PropertiesUtil.addProperty("java.bin.path", javaBinPath);
					PropertiesUtil.addProperty("apache.cxf.bin.path", apacheCxfBinPath);
					
					JOptionPane.showMessageDialog(null, "Jar Generated Successfully");
					
					btnNewButton.setEnabled(true);
				}
			});
			
			t.start();		
			
		});
		
		// apache cxf radio button
		JRadioButton rdbtnNewRadioButton_1_1 = new JRadioButton("Apache CXF");
		rdbtnNewRadioButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				library = Library.APACHE_CXF.toString();
				apacheCxfBinPathField.setVisible(true);
				apacheCxfFieldLabel.setVisible(true);
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_1_1);
		rdbtnNewRadioButton_1_1.setSelected(true);
		rdbtnNewRadioButton_1_1.setBounds(167, 74, 109, 23);
		frame.getContentPane().add(rdbtnNewRadioButton_1_1);
						
		//wsimport radio button
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("wsimport");
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				library = Library.WSIMPORT.toString();
				apacheCxfBinPathField.setVisible(false);
				apacheCxfFieldLabel.setVisible(false);
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(333, 74, 109, 23);
		frame.getContentPane().add(rdbtnNewRadioButton_1);
		
		//radio buttons labels
		JLabel lblNewLabel_3 = new JLabel("Library");
		lblNewLabel_3.setBounds(26, 78, 46, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		//separator
		JSeparator separator = new JSeparator();
		separator.setBounds(26, 245, 461, 2);
		frame.getContentPane().add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 258, 461, 250);
		frame.getContentPane().add(scrollPane);
		
		//text area used for logging
		TextAreaHandler textAreaHandler = logger.getTextAreaHandlers();
		JTextArea textArea = textAreaHandler.getTextArea();
		//JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setForeground(Color.black);
		textArea.setBackground(Color.white);
		
		//initialize text fields
		javaBinPathField.setText(PropertiesUtil.getProperty("java.bin.path"));
		apacheCxfBinPathField.setText(PropertiesUtil.getProperty("apache.cxf.bin.path"));		
		
	}
}
