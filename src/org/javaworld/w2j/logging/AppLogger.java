package org.javaworld.w2j.logging;

import java.util.logging.Handler;
import java.util.logging.Logger;

public class AppLogger {

	private static Logger logger;

	private AppLogger() {
		if (logger == null) {
			logger = Logger.getLogger("MyLogger");
			logger.addHandler(new TextAreaHandler());
		}
	}

	public static AppLogger getLogger() {
		return new AppLogger();
	}

	public void info(String msg) {
		logger.info(">> " + msg);
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
