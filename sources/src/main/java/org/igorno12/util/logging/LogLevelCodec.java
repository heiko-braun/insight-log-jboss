package org.igorno12.util.logging;

/**
 * Codec for transcoding log levels
 * 
 * 
 * <p><em>Warning: </em> Other code in this library assumes that
 * all implementations of this interface are thread safe.</p>
 */
public interface LogLevelCodec {

	/**
	 * Transcodes log level from Log4j to Java Utility Logging API (JUL)
	 * 
	 * @param level		Log4j log level to be transcoded
	 * @return			JUL equivalent of Log4j log level
	 */
	public java.util.logging.Level getLevel(org.apache.log4j.Level level);

	/**
	 * Transcodes log level from Java Utility Logging API (JUL) to Log4j
	 * 
	 * @param level		JUL log level to be transcoded
	 * @return			Log4j equivalent of JUL log level
	 */
	public org.apache.log4j.Level getLevel(java.util.logging.Level level);

}