package org.javaworld.w2j.main;

import java.io.IOException;
import org.javaworld.w2j.util.CommandLineRunner;
import org.javaworld.w2j.util.FileUtil;


/**
 * 
 * @author O-AbdelRahman.Attya
 *
 *issues >> 
 * 1- save data java & apache bin paths to properties file then 
 *    init corresponding fields each time app is opened
 *    
 * 2-  make file util class that uses nio library instead of cmd commands
 * 3-  show logs in a text area
 * 4-  find a better way for button shape during generation
 */


public class Generator {
	
	//public static String wsdlPath = "C:\\Users\\o-abdelrahman.attya\\Desktop\\test\\ERPServiceRequestManagementHTTP.wsdl";
	//public static final String JAVA_BIN_PATH = "C:\\Program Files\\Java\\jdk1.8.0_251\\bin";
	//public static final String APACHE_CFX_BIN_PATH = "C:\\_apache-cxf-3.2.1\\bin";

	
	private String wsdlPath;
	private String javaBinPath;
	private String apacheCxfBinPath;

	public Generator(String wsdlPath, String javaBinPath, String apacheCxfBinPath) {
		this.wsdlPath = wsdlPath;
		this.javaBinPath = javaBinPath;
		this.apacheCxfBinPath = apacheCxfBinPath;
	}
	
	public void generate() throws IOException, InterruptedException {
		
		//delete temp folder if exist	
		String tempFolderPath = System.getProperty("user.home") + "\\Documents\\temp";
		FileUtil.deleteFolder(tempFolderPath);
		
		//create a temp folder
		FileUtil.createFolder(tempFolderPath);		
		
		// generate the client into the temp folder
		generateClient(wsdlPath, tempFolderPath);
		
		//copy wsdl file to temp folder
		FileUtil.copyFile(wsdlPath, tempFolderPath);
		
		//package contents of temp folder to a jar file
		String jarFilePath = wsdlPath.replace(".wsdl", ".jar");
		generateJar(jarFilePath, tempFolderPath);

	}
	
	private void generateJar(String jarFilePath, String targetPath) throws IOException, InterruptedException {
		String jarPkgCmd = "\"" + javaBinPath + "\\jar\" cvf " + jarFilePath + " -C " + targetPath + " .";
		int result = CommandLineRunner.runCommand(jarPkgCmd);
		if(result == 0) {
			System.out.println("jar generated successfully \n");
		} else {
			throw new RuntimeException("failed to generate the jar");
		}
	}
		
	private  void generateClient(String wsdlPath, String targetPath) throws IOException, InterruptedException {

		String generateClientCommand = apacheCxfBinPath + "\\wsdl2java" 
								+ " -classdir " + targetPath 
								+ " -d "        + targetPath 
								+ " -compile " 
								+ wsdlPath;
		int result = CommandLineRunner.runCommand(generateClientCommand);
		if(result == 0) {
			System.out.println("client generated successfully \n");
		} else {
			throw new RuntimeException("failed to generate the client");
		}
		
	}

}
