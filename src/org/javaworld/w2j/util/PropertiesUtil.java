package org.javaworld.w2j.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	private static final String propertiesFilePath = System.getProperty("user.home") + "\\Documents\\w2j.properties";

	public static void addProperty(String key, String val) {

		Properties prop = new Properties();

		FileInputStream in = null;
		FileOutputStream out = null;
		try {

			in = new FileInputStream(propertiesFilePath);
			prop.load(in);
			prop.setProperty(key, val);
			in.close();

			out = new FileOutputStream(propertiesFilePath);
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

			in = new FileInputStream(propertiesFilePath);
			prop.load(in);
			val = (String) prop.get(key);
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return val;
	}

}
