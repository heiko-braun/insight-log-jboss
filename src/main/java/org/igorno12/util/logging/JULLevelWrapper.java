package org.igorno12.util.logging;

/**
 * Wrapper for enumerating Java Utility Logging API (JUL) log levels
 *
 */
public enum JULLevelWrapper {
	/**
	 * Wrapper for java.util.logging.Level.OFF
	 * @see java.util.logging.Level#OFF
	 */
	OFF(java.util.logging.Level.OFF), 
	/**
	 * Wrapper for java.util.logging.Level.SEVERE
	 * @see java.util.logging.Level#SEVERE
	 */
	SEVERE(java.util.logging.Level.SEVERE), 
	/**
	 * Wrapper for java.util.logging.Level.WARNING
	 * @see java.util.logging.Level#WARNING
	 */
	WARNING(java.util.logging.Level.WARNING), 
	/**
	 * Wrapper for java.util.logging.Level.INFO
	 * @see java.util.logging.Level#INFO
	 */
	INFO(java.util.logging.Level.INFO), 
	/**
	 * Wrapper for java.util.logging.Level.CONFIG
	 * @see java.util.logging.Level#CONFIG
	 */
	CONFIG(java.util.logging.Level.CONFIG), 
	/**
	 * Wrapper for java.util.logging.Level.FINE
	 * @see java.util.logging.Level#FINE
	 */
	FINE(java.util.logging.Level.FINE), 
	/**
	 * Wrapper for java.util.logging.Level.FINER
	 * @see java.util.logging.Level#FINER
	 */
	FINER(java.util.logging.Level.FINER), 
	/**
	 * Wrapper for java.util.logging.Level.FINEST
	 * @see java.util.logging.Level#FINEST
	 */
	FINEST(java.util.logging.Level.FINEST), 
	/**
	 * Wrapper for java.util.logging.Level.ALL
	 * @see java.util.logging.Level#ALL
	 */
	ALL(java.util.logging.Level.ALL);
	
	private final java.util.logging.Level realLevel;

	private JULLevelWrapper(java.util.logging.Level realLevel) {
		this.realLevel = realLevel;
	}

	/**
	 * Gets the Java Utility Logging API (JUL) log level represented by this wrapper
	 * 
	 * @return		JUL log level represented by this wrapper
	 */
	public java.util.logging.Level getLevel() {
		return this.realLevel;
	}
}
