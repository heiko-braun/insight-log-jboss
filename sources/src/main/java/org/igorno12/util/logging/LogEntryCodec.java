package org.igorno12.util.logging;

/**
 * Codec for transcoding log entries
 * 
 * 
 * <p><em>Warning: </em> Other code in this library assumes that
 * all implementations of this interface are thread safe.</p>
 */
public interface LogEntryCodec {

	/**
	 * Transcodes from Log4j to Java Utility Logging API (JUL)
	 * 
	 * @param loggingEvent		Log4j log entry to be transcoded
	 * @return					JUL equivalent of Log4j log entry
	 */
	public java.util.logging.LogRecord transcode(
			org.apache.log4j.spi.LoggingEvent loggingEvent);

	/**
	 * Transcodes from Java Utility Logging API (JUL) to Log4j
	 * 
	 * @param logger		Log4j logger used in the process
	 * @param formatter		JUL formatter used in the process
	 * @param logRecord		JUL log entry to be transcoded
	 * @return				Log4j equivalent of JUL log entry
	 */
	public org.apache.log4j.spi.LoggingEvent transcode(
			org.apache.log4j.Category logger,
			java.util.logging.Formatter formatter,
			java.util.logging.LogRecord logRecord);

}