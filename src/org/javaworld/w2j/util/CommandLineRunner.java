package org.javaworld.w2j.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandLineRunner {

	
	public static int runCommand(String command) throws IOException, InterruptedException {
		System.out.println("executing command: \n\t" + command);
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		Process process = processBuilder.command("cmd.exe", "/c", command)
		                            .start();
		printResults(process.getInputStream());
		int exitVal = process.waitFor();	
		printResults(process.getErrorStream());
		return exitVal;
	}
	
	
	private static void printResults(InputStream inputStream) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    String line = "";
	    while ((line = reader.readLine()) != null) {
	        System.out.println("\t" + line);
	    }
	} 
	
}
