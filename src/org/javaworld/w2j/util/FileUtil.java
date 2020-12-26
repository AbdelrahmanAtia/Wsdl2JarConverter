package org.javaworld.w2j.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.javaworld.w2j.logging.AppLogger;

public class FileUtil {
	
	private static final AppLogger logger = AppLogger.getLogger();

	public static boolean isExist(String filePath){
		LinkOption[] linkOptions = { LinkOption.NOFOLLOW_LINKS };
		return Files.exists(Paths.get(filePath), linkOptions);		
	}

	/*public static void copyFile(String filePath, String targetPath) {
		String copyFileCmd = "copy " + filePath + " " + targetPath;
		CommandLineRunner.runCommand(copyFileCmd);
		logger.info("file copied successfully");
	}
	*/
	
	public static void copyFile(String sourceFilePath, String targetDirPath) {
		logger.info("starting to copy file from   " + sourceFilePath + " to   " + targetDirPath);
		try {
			
			Path source = Paths.get(sourceFilePath);
			Path target = Paths.get(targetDirPath + "\\" + source.getFileName());

			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

			logger.info("file copied successfully");
		} catch (IOException ex) {
			logger.severe("erorr: failed to copy file");
			throw new RuntimeException(ex);
		}
	}

	public static void deleteFolder(String tempFolderPath) {
		String removeDirCmd = "rmdir /s /q " + tempFolderPath;
		CommandLineRunner.runCommand(removeDirCmd);
		logger.info("directory deleted");
	}

	/*
	public static void createFolder(String tempFolderPath) {
		String makeDirCmd = "md " + tempFolderPath;
		CommandLineRunner.runCommand(makeDirCmd);
		logger.info("directory created successfully");
	}
	*/
	
	public static void createFolder(String dirPath) {
		logger.info("starting to create a directory at: " + dirPath);
		try {
			Files.createDirectories(Paths.get(dirPath));
			logger.info("directory created successfully");
		} catch (IOException e) {
			logger.severe("erorr: failed to create directory");
			throw new RuntimeException(e);
		}
	}
	
	
}
