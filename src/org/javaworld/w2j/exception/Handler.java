package org.javaworld.w2j.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import org.javaworld.w2j.logging.AppLogger;

public class Handler implements UncaughtExceptionHandler {

	private static final AppLogger logger = AppLogger.getLogger();

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		logger.severe("error msg: " + e.getMessage());
	}

}
