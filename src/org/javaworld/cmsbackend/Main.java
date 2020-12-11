package org.javaworld.cmsbackend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.management.RuntimeErrorException;


/**
 * 
 * @author O-AbdelRahman.Attya
 *
 *issues >> 
 * 1 - contents not packaged starting from temp folder
 * 2-  make file util class that uses nio library instead of cmd commands
 * 3-  make a static class called commandline runner to run commands 
 */


public class Main {
	
	public static String wsdlPath = "C:\\Users\\o-abdelrahman.attya\\Desktop\\test\\ERPServiceRequestManagementHTTP.wsdl";
	public static final String JAVA_BIN_PATH = "C:\\Program Files\\Java\\jdk1.8.0_251\\bin";
	public static final String APACHE_CFX_BIN_PATH = "C:\\_apache-cxf-3.2.1\\bin";

	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		//delete temp folder if exist	
		String tempFolderPath = System.getProperty("user.home") + "\\Documents\\temp";
		deleteFolder(tempFolderPath);
		
		//create a temp folder
		createFolder(tempFolderPath);		
		
		// generate the client into the temp folder
		generateClient(wsdlPath, tempFolderPath);
		
		//copy wsdl file to temp folder
		copyFile(wsdlPath, tempFolderPath);
		
		//package contents of temp folder to a jar file
		String jarFilePath = wsdlPath.replace(".wsdl", ".jar");
		generateJar(jarFilePath, tempFolderPath);

	}
	
	private static void generateJar(String jarFilePath, String targetPath) throws IOException, InterruptedException {
		String jarPkgCmd = "\"" + JAVA_BIN_PATH + "\\jar\" cf " + jarFilePath + " " + targetPath;
		runCommand(jarPkgCmd);
		System.out.println("jar generated successfully \n");
	}
	
	
	private static void copyFile(String filePath, String targetPath) throws IOException, InterruptedException {
		String copyFileCmd = "copy " + filePath + " " + targetPath;
		runCommand(copyFileCmd);
		System.out.println("file copied successfully \n");
	}
	
	
	private static void deleteFolder(String tempFolderPath) throws IOException, InterruptedException {
		String removeDirCmd = "rmdir /s /q " + tempFolderPath;
		runCommand(removeDirCmd);
		System.out.println("directory deleted \n");
	}

	private static void createFolder(String tempFolderPath) throws IOException, InterruptedException {
		String makeDirCmd = "md " + tempFolderPath;
		runCommand(makeDirCmd);
		System.out.println("directory created successfully \n");
	}

	public static void generateClient(String wsdlPath, String targetPath) throws IOException, InterruptedException {

		String generateClientCommand = APACHE_CFX_BIN_PATH + "\\wsdl2java" 
								+ " -classdir " + targetPath 
								+ " -d "        + targetPath 
								+ " -compile " 
								+ wsdlPath;
		int result = runCommand(generateClientCommand);
		if(result == 0) {
			System.out.println("client generated successfully \n");
		} else {
			throw new RuntimeException("failed to generate the client");
		}
		
	}

	public static int runCommand(String command) throws IOException, InterruptedException {
		System.out.println("executing command: \n\t" + command);
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		Process process = processBuilder.command("cmd.exe", "/c", command)
		                            .start();
		printResultsV2(process.getInputStream());
		int exitVal = process.waitFor();	
		printResultsV2(process.getErrorStream());
		return exitVal;
	}
	
	/*
	private static void printResults(Process process) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    String line = "";
	    while ((line = reader.readLine()) != null) {
	        System.out.println(line);
	    }
	}
	*/
	
	private static void printResultsV2(InputStream inputStream) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    String line = "";
	    while ((line = reader.readLine()) != null) {
	        System.out.println(line);
	    }
	} 

}
