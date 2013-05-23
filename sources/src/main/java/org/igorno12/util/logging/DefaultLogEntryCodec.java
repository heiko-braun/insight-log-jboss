package org.igorno12.util.logging;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * Default implementation of a log entry codec
 *
 */
public final class DefaultLogEntryCodec implements LogEntryCodec {

	private final LogLevelCodec levelCodec;

	/**
	 * Constructs an instance using the given log level codec
	 * 
	 * @param logLevelCodec		Log level codec to be used to transcode
	 * 							log entries
	 */
	public DefaultLogEntryCodec(LogLevelCodec logLevelCodec) {
		super();
		if (logLevelCodec == null) {
			throw new IllegalArgumentException("null log level codec argument");
		}
		this.levelCodec = logLevelCodec;
	}
	
	/**
	 * Constructs an instance using the default log level codec
	 * 
	 * @see DefaultLogLevelCodec
	 */
	public DefaultLogEntryCodec() {
		this(new DefaultLogLevelCodec());
	}
	
	/**
	 * Gets the log level codec used during transcoding
	 * 
	 * @return		Log level codec used during transcoding
	 */
	public LogLevelCodec getLogLevelCodec() {
		return this.levelCodec;
	}

	/**
	 * Transcodes from Log4j to Java Utility Logging API (JUL)
	 * 
	 * <p><em>Map of transcoding</em></p>
	 * <p>
	 * <table border="1" cellpadding="1">
	 *   <tr><th>Log4j org.apache.log4j.spi.LoggingEvent</th><th>JUL java.util.logging.LogRecord</th></tr>
	 *   <tr><td>LoggingEvent.getLevel()</td>
	 *   	 <td>java.util.logging.Level used in constructor for LogRecord</td>
	 *   </tr>
	 *   <tr><td>LoggingEvent.getRenderedMessage()</td>
	 *   	<td>message argument used in constructor for LogRecord</td>
	 *   </tr>
	 *   <tr><td>LoggingEvent.timeStamp</td>
	 *   	<td>LogRecord.setMillis()</td>
	 *   </tr>
	 *   <tr><td>LoggingEvent.getLocationInformation().getClassName()</td>
	 *   	<td>LogRecord.setSourceClassName()</td>
	 *   </tr>
	 *   <tr><td>LoggingEvent.getLocationInformation().getMethodName()</td>
	 *   	<td>LogRecord.setSourceMethodName()</td>
	 *   </tr>
	 *   <tr><td>LoggingEvent.getThrowableInformation().getThrowable()</td>
	 *   	<td>LogRecord.setThrown()</td>
	 *   </tr>
	 * </table>
	 * </p>
	 * 
	 * @param loggingEvent		Log4j log entry to be transcoded
	 * @return					JUL equivalent of Log4j log entry
	 *
	 */
	public java.util.logging.LogRecord transcode(
			org.apache.log4j.spi.LoggingEvent loggingEvent) {
		if (loggingEvent == null) {
			return null;
		}
		final java.util.logging.Level utilLevel = this.levelCodec
				.getLevel(loggingEvent.getLevel());
		final String message = String
				.valueOf(loggingEvent.getRenderedMessage());
		final java.util.logging.LogRecord logRecord = new java.util.logging.LogRecord(
				utilLevel, message);
		logRecord.setMillis(loggingEvent.timeStamp);
		final org.apache.log4j.spi.LocationInfo locationInfo = loggingEvent
				.getLocationInformation();
		if (null != locationInfo) {
			logRecord.setSourceClassName(locationInfo.getClassName());
			logRecord.setSourceMethodName(locationInfo.getMethodName());
		}
		final org.apache.log4j.spi.ThrowableInformation throwableInfo = loggingEvent
				.getThrowableInformation();
		if (null != throwableInfo) {
			final Throwable throwable = throwableInfo.getThrowable();
			if (null != throwable) {
				logRecord.setThrown(throwable);
			}
		}
		return logRecord;
	}

	/**
	 * Transcodes from Java Utility Logging API (JUL) to Log4j
	 * 
	 * <p><em>Map of transcoding</em></p>
	 * <p>
	 * <table border="1" cellpadding="1">
	 *   <tr><th>JUL java.util.logging.LogRecord</th><th>Log4j org.apache.log4j.spi.LoggingEvent</th></tr>
	 *   <tr><td>LogRecord.getSourceClassName()</td><td>fqnOfCategoryClass argument to LoggingEvent constructor</td></tr>
	 *   <tr><td>LogRecord.getMillis()</td><td>timeStamp argument to LoggingEvent constructor</td></tr>
	 *   <tr><td>LogRecord.getLevel()</td><td>org.apache.log4j.Level argument to LoggingEvent constructor</td></tr>
	 *   <tr><td>java.util.logging.Formatter.format(LogRecord)</td><td>message argument to LoggingEvent constructor</td></tr>
	 *   <tr><td>LogRecord.getThrown()</td><td>throwable argument to LoggingEvent constructor</td></tr>
	 * </table>
	 * </p>
	 * 
	 * @param logger		Log4j logger used in the process
	 * @param formatter		JUL formatter used in the process
	 * @param logRecord		JUL log entry to be transcoded
	 * @return				Log4j equivalent of JUL log entry
	 */
	public org.apache.log4j.spi.LoggingEvent transcode(
			org.apache.log4j.Category logger,
			java.util.logging.Formatter formatter,
			java.util.logging.LogRecord logRecord) {
		if (logger == null) {
			throw new IllegalArgumentException("null logger argument");
		}
		if (formatter == null) {
			throw new IllegalArgumentException("null JUL formatter argument");
		}
		if (logRecord == null) {
			return null;
		}
		final String fqnOfCategoryClass = logRecord.getSourceClassName();
		final long timeStamp = logRecord.getMillis();
		final org.apache.log4j.Level level = this.levelCodec.getLevel(logRecord
				.getLevel());
		final String message = formatter.format(logRecord);
		final Throwable throwable = logRecord.getThrown();
		final ThrowableInformation throwableInfo = throwable != null ? new ThrowableInformation(throwable, logger) : null;
		final LocationInfo location = throwable != null ? new LocationInfo(throwable, fqnOfCategoryClass) :
			new LocationInfo(new Throwable(), fqnOfCategoryClass);
		
		final org.apache.log4j.spi.LoggingEvent loggingEvent = new org.apache.log4j.spi.LoggingEvent(
				fqnOfCategoryClass, logger, timeStamp, level, message, Thread.currentThread().getName(),
				throwableInfo, null, location, null);
		return loggingEvent;
	}

}
