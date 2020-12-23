package org.javaworld.w2j.main;

import org.javaworld.w2j.logging.AppLogger;
import org.javaworld.w2j.model.Library;
import org.javaworld.w2j.util.CommandLineRunner;
import org.javaworld.w2j.util.FileUtil;


public class Generator {
	
	private static final AppLogger logger = AppLogger.getLogger();

	private String wsdlPath;
	private String javaBinPath;
	private String apacheCxfBinPath;
	private String library;
	
	public Generator(String wsdlPath, String javaBinPath, String apacheCxfBinPath, String library) {
		this.wsdlPath = wsdlPath;
		this.javaBinPath = javaBinPath;
		this.apacheCxfBinPath = apacheCxfBinPath;
		this.library = library;
	}

	public void generate() {
		
		//delete temp folder if exist	
		String tempFolderPath = System.getProperty("user.home") + "\\Documents\\temp";
		FileUtil.deleteFolder(tempFolderPath);
		
		//create a temp folder
		FileUtil.createFolder(tempFolderPath);		
		
		// generate the client into the temp folder
		generateClient(tempFolderPath);
		
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
			logger.info("jar generated successfully");
		} else {
			throw new RuntimeException("failed to generate the jar");
		}
	}
		
	private  void generateClient(String targetPath)  {
		
		
		String generateClientCommand = null;
		
		if(library.equals(Library.APACHE_CXF.toString())) {
			generateClientCommand = apacheCxfBinPath + "\\wsdl2java" 
										+ " -classdir " + targetPath 
										+ " -d "        + targetPath 
										+ " -compile " 
										+ wsdlPath;
		} else if(library.equals(Library.WSIMPORT.toString())) {
			generateClientCommand = "\"" + javaBinPath + " \\wsimport" + "\""
					                     + " -keep -verbose " + wsdlPath
					                     + " -d " + targetPath;
		} else {
			throw new RuntimeException("not supported library");
		}
		
		int result = CommandLineRunner.runCommand(generateClientCommand);
		
		if(result == 0) {
			logger.info("client generated successfully \n");
		} else {
			throw new RuntimeException("failed to generate the client");
		}
		
	}

}
