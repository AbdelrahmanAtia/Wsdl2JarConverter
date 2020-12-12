package org.javaworld.w2j.util;

import java.io.IOException;

public class FileUtil {

	public static void copyFile(String filePath, String targetPath) throws IOException, InterruptedException {
		String copyFileCmd = "copy " + filePath + " " + targetPath;
		CommandLineRunner.runCommand(copyFileCmd);
		System.out.println("file copied successfully \n");
	}

	public static void deleteFolder(String tempFolderPath) throws IOException, InterruptedException {
		String removeDirCmd = "rmdir /s /q " + tempFolderPath;
		CommandLineRunner.runCommand(removeDirCmd);
		System.out.println("directory deleted \n");
	}

	public static void createFolder(String tempFolderPath) throws IOException, InterruptedException {
		String makeDirCmd = "md " + tempFolderPath;
		CommandLineRunner.runCommand(makeDirCmd);
		System.out.println("directory created successfully \n");
	}
}
