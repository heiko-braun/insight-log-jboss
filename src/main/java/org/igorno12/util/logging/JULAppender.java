package org.igorno12.util.logging;

/**
 * This adapter directs logging from Log4j into the Java Utility Logging API (JUL)
 * 
 * <h4>Code Example</h4>
 * <pre>
		java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger("bar");
		JULAppender appender = new JULAppender(new DefaultLogEntryCodec(),julLogger);
		org.apache.log4j.Logger log4jLogger = org.apache.log4j.Logger.getLogger("foo");
		log4jLogger.addAppender(appender);
 * </pre>
 */
public final class JULAppender extends org.apache.log4j.AppenderSkeleton
		implements org.apache.log4j.Appender {

	private final LogEntryCodec entryCodec;
	private final java.util.logging.Logger delegate;
	private boolean closed = false;

	/**
	 * Constructs an instance of the appender
	 * 
	 * @param logEntryCodec
	 *            Codec to use to transcode log entries for delegate. The
	 *            implementation of this codec is assumed to be thread safe.
	 * @param delegate
	 *            Logging delegate for this handler.
	 */
	public JULAppender(LogEntryCodec logEntryCodec,
			java.util.logging.Logger delegate) {
		super();
		if (logEntryCodec == null) {
			throw new IllegalArgumentException("null log entry codec argument");
		}
		if (delegate == null) {
			throw new IllegalArgumentException("null logging delegate argument");
		}
		this.entryCodec = logEntryCodec;
		this.delegate = delegate;
	}

	@Override
	protected void append(org.apache.log4j.spi.LoggingEvent event) {
		if (this.closed) {
			throw new UnsupportedOperationException("appender is already closed");
		}
		final java.util.logging.LogRecord record = this.entryCodec
				.transcode(event);
		this.delegate.log(record);
	}
	
	/**
	 * Closes the handler
	 * 
	 * In this case, this method only "closes" this adapter, and not the
	 * underlying delegate.
	 */
	@Override
	public void close() {
		this.closed = true;
	}

	/**
	 * This implementation does not require layout
	 * 
	 * @return		Always returns false.
	 */
	@Override
	public boolean requiresLayout() {
		return false;
	}

}
