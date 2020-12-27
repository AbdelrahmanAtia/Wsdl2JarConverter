package org.javaworld.w2j.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppLogger {

	private static Logger logger;

	private AppLogger() {
		if (logger == null) {
			logger = Logger.getLogger("MyLogger");
			logger.addHandler(new TextAreaHandler());
			//logger.setLevel(Level.FINE);
			logger.setLevel(Level.INFO);
		}
	}

	public static AppLogger getLogger() {
		return new AppLogger();
	}
	
	public void severe(String msg) {
		logger.severe(msg);
	}

	public void info(String msg) {
		logger.info(msg);
	}
	
	public void fine(String msg) {
		logger.fine(msg);
	}

	public TextAreaHandler getTextAreaHandlers() {
		for (Handler handler : logger.getHandlers()) {
			if (handler instanceof TextAreaHandler) {
				return (TextAreaHandler) handler;
			}
		}
		return null;
	}

}
