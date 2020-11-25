package org.javaworld.cmsbackend;

import java.io.IOException;

import javax.management.RuntimeErrorException;

public class Main {

	public static final String GENERATE_CLINET_CMD = "C:\\_apache-cxf-3.2.1\\bin\\wsdl2java -compile SharePointReachManagementHTTP.wsdl";
	
	public static void main(String[] args) throws IOException, InterruptedException {

		// generate the client
		runCommand(GENERATE_CLINET_CMD);
		
		//move generated files & WSDL to destination folder
		
		
		//generate jar from moved files
		

	}

	public static void runCommand(String command) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		int exitVal = processBuilder.command("cmd.exe", "/c", command)
		                            .start()
		                            .waitFor();
		
		if(exitVal != 0) {
			throw new RuntimeException("an error occured while executing command: " + command);
		}
	}

}
