package org.javaworld.w2j.util;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import org.javaworld.w2j.logging.AppLogger;

public class FileUtil {
	
	private static final AppLogger logger = AppLogger.getLogger();

	public static boolean isExist(String filePath){
		LinkOption[] linkOptions = { LinkOption.NOFOLLOW_LINKS };
		return Files.exists(Paths.get(filePath), linkOptions);		
	}

	public static void copyFile(String filePath, String targetPath) {
		String copyFileCmd = "copy " + filePath + " " + targetPath;
		CommandLineRunner.runCommand(copyFileCmd);
		logger.info("file copied successfully");
	}

	public static void deleteFolder(String tempFolderPath) {
		String removeDirCmd = "rmdir /s /q " + tempFolderPath;
		CommandLineRunner.runCommand(removeDirCmd);
		logger.info("directory deleted");
	}

	public static void createFolder(String tempFolderPath) {
		String makeDirCmd = "md " + tempFolderPath;
		CommandLineRunner.runCommand(makeDirCmd);
		logger.info("directory created successfully");
	}
	
	
}
