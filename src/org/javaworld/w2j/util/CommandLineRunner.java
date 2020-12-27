package org.javaworld.w2j.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.javaworld.w2j.logging.AppLogger;

public class CommandLineRunner {

	private static final AppLogger logger = AppLogger.getLogger();

	public static int runCommand(String command) {
		
		logger.info("executing command: \n   " + command);
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		try {
			Process process = processBuilder.command("cmd.exe", "/c", command)
			                                .start();
			
			printResults(process.getInputStream());
			int exitVal = process.waitFor();
			logger.fine("exitVal: " + exitVal);
			printResults(process.getErrorStream());
			return exitVal;
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private static void printResults(InputStream inputStream) {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    String line = "";
	    try {
			while ((line = reader.readLine()) != null) {
			    logger.fine("   " + line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	} 
	
}
