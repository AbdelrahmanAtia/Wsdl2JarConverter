package org.javaworld.w2j.main;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main {

	private JFrame frame;
	private JTextField javaBinPathField;
	private JTextField apacheCxfBinPathField;
	private JTextField wsdlPathField;
	private JLabel lblNewLabel_2;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 565, 311);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		javaBinPathField = new JTextField();
		javaBinPathField.setText("");
		javaBinPathField.setBounds(167, 31, 320, 23);
		frame.getContentPane().add(javaBinPathField);
		javaBinPathField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Java Bin Path:");
		lblNewLabel.setBounds(26, 34, 121, 20);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Apache Cxf Bin Path:");
		lblNewLabel_1.setBounds(26, 80, 121, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		apacheCxfBinPathField = new JTextField();
		apacheCxfBinPathField.setBounds(167, 81, 320, 22);
		frame.getContentPane().add(apacheCxfBinPathField);
		apacheCxfBinPathField.setColumns(10);
		
		wsdlPathField = new JTextField();
		wsdlPathField.setColumns(10);
		wsdlPathField.setBounds(167, 130, 320, 22);
		frame.getContentPane().add(wsdlPathField);
		
		lblNewLabel_2 = new JLabel("Wsdl Path:");
		lblNewLabel_2.setBounds(26, 129, 121, 23);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Generate");
		
		btnNewButton.addActionListener(e -> {
			String javaBinPath = javaBinPathField.getText();
			String apacheCxfBinPath = apacheCxfBinPathField.getText();
			String wsdlPath = wsdlPathField.getText();
			
			Generator generator = new Generator(wsdlPath, javaBinPath, apacheCxfBinPath);
			try {
				generator.generate();
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
		});
		
	
		btnNewButton.setBounds(398, 190, 89, 36);
		frame.getContentPane().add(btnNewButton);
	}
}
