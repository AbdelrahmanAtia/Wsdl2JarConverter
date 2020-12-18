package org.javaworld.w2j.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

public class FileUtil {
	
	
	public static boolean isExist(String filePath){
		LinkOption[] linkOptions = { LinkOption.NOFOLLOW_LINKS };
		return Files.exists(Paths.get(filePath), linkOptions);		
	}

	public static void copyFile(String filePath, String targetPath) {
		String copyFileCmd = "copy " + filePath + " " + targetPath;
		CommandLineRunner.runCommand(copyFileCmd);
		System.out.println("file copied successfully \n");
	}

	public static void deleteFolder(String tempFolderPath) {
		String removeDirCmd = "rmdir /s /q " + tempFolderPath;
		CommandLineRunner.runCommand(removeDirCmd);
		System.out.println("directory deleted \n");
	}

	public static void createFolder(String tempFolderPath) {
		String makeDirCmd = "md " + tempFolderPath;
		CommandLineRunner.runCommand(makeDirCmd);
		System.out.println("directory created successfully \n");
	}
	
	
}
