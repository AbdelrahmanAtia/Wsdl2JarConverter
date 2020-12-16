package org.javaworld.w2j.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesUtil {

	private static Path propertiesFilePath;

	static {
		try {

			Path path = Paths.get(System.getProperty("user.home") + "\\Documents\\w2j.properties");
			LinkOption[] linkOptions = { LinkOption.NOFOLLOW_LINKS };

			propertiesFilePath = Files.exists(path, linkOptions) ? path
					: Files.createFile(Paths.get(System.getProperty("user.home") + "\\Documents\\w2j.properties"));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void addProperty(String key, String val)  {
		
		Properties prop = new Properties();

		FileInputStream in = null;
		FileOutputStream out = null;
		try {

			in = new FileInputStream(propertiesFilePath.toFile());
			prop.load(in);
			prop.setProperty(key, val);
			in.close();

			out = new FileOutputStream(propertiesFilePath.toFile());
			prop.store(out, null);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getProperty(String key) {

		Properties prop = new Properties();
		FileInputStream in = null;
		String val = "";
		try {

			in = new FileInputStream(propertiesFilePath.toFile());
			prop.load(in);
			val = (String) prop.get(key);
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return val;
	}

}
