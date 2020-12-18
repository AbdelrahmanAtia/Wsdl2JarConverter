package org.javaworld.w2j.main;

import org.javaworld.w2j.util.CommandLineRunner;
import org.javaworld.w2j.util.FileUtil;


public class Generator {
	
	private String wsdlPath;
	private String javaBinPath;
	private String apacheCxfBinPath;

	public Generator(String wsdlPath, String javaBinPath, String apacheCxfBinPath) {
		this.wsdlPath = wsdlPath;
		this.javaBinPath = javaBinPath;
		this.apacheCxfBinPath = apacheCxfBinPath;
	}
	
	public void generate() {
		
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
	
	private void generateJar(String jarFilePath, String targetPath)  {
		String jarPkgCmd = "\"" + javaBinPath + "\\jar\" cvf " + jarFilePath + " -C " + targetPath + " .";
		int result = CommandLineRunner.runCommand(jarPkgCmd);
		if(result == 0) {
			System.out.println("jar generated successfully \n");
		} else {
			throw new RuntimeException("failed to generate the jar");
		}
	}
		
	private  void generateClient(String wsdlPath, String targetPath)  {		
		
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
