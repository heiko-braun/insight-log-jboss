package org.igorno12.util.logging;

/**
 * Wrapper for enumerating Log4j log levels
 *
 */
public enum Log4jLevelWrapper {
	/**
	 * Wrapper for org.apache.log4j.Level.OFF
	 * 
	 * @see org.apache.log4j.Level#OFF
	 */
	OFF(org.apache.log4j.Level.OFF),
	
	/**
	 * Wrapper for org.apache.log4j.Level.FATAL
	 * 
	 * @see org.apache.log4j.Level#FATAL
	 */
	FATAL(org.apache.log4j.Level.FATAL),
	
	/**
	 * Wrapper for org.apache.log4j.Level.ERROR
	 * 
	 * @see org.apache.log4j.Level#ERROR
	 */
	ERROR(org.apache.log4j.Level.ERROR),
	
	/**
	 * Wrapper for org.apache.log4j.Level.WARN
	 * 
	 * @see org.apache.log4j.Level#WARN
	 */
	WARN(org.apache.log4j.Level.WARN), 
	
	/**
	 * Wrapper for org.apache.log4j.Level.INFO
	 * 
	 * @see org.apache.log4j.Level#INFO
	 */
	INFO(org.apache.log4j.Level.INFO),
	
	/**
	 * Wrapper for org.apache.log4j.Level.DEBUG
	 * 
	 * @see org.apache.log4j.Level#DEBUG
	 */
	DEBUG(org.apache.log4j.Level.DEBUG),
	
	/**
	 * Wrapper for org.apache.log4j.Level.TRACE
	 * 
	 * @see org.apache.log4j.Level#TRACE
	 */
	TRACE(org.apache.log4j.Level.TRACE),
	
	/**
	 * Wrapper for org.apache.log4j.Level.ALL
	 * 
	 * @see org.apache.log4j.Level#ALL
	 */
	ALL(org.apache.log4j.Level.ALL);
	
	private final org.apache.log4j.Level realLevel;
	private Log4jLevelWrapper(org.apache.log4j.Level realLevel) {
		this.realLevel = realLevel;
	}		
	
	/**
	 * Gets the Log4j log level represented by this wrapper
	 * 
	 * @return		Log4j log level represented by this wrapper
	 */
	public org.apache.log4j.Level getLevel() {
		return this.realLevel;
	}
}
