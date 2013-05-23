package org.igorno12.util.logging;

import java.util.logging.Formatter;
import org.apache.log4j.LogManager;

/**
 * This adapter directs logging from JUL into Log4j.
 * 
 * <h4>Code Example</h4>
 * <pre>
		org.apache.log4j.Logger log4jLogger = org.apache.log4j.Logger.getLogger("foo");
		Log4jHandler handler = new Log4jHandler(new DefaultLogEntryCodec(),log4jLogger);
		java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger("bar");
		julLogger.addHandler(handler);
 * </pre>
 */
public final class Log4jHandler extends java.util.logging.Handler {

	private final java.util.logging.Formatter formatter =  new java.util.logging.SimpleFormatter();	
	private final org.apache.log4j.Logger delegate;
	private final LogEntryCodec entryCodec;
	private boolean closed = false;

        public Log4jHandler() {
             this(new DefaultLogEntryCodec(), LogManager.getRootLogger());
        }

	/**
	 * Constructs an instance of the handler
	 * 
	 * @param logEntryCodec
	 *            Codec to use to transcode log entries for delegate. The
	 *            implementation of this codec is assumed to be thread safe.
	 * @param delegate
	 *            Logging delegate for this handler.
	 */
	public Log4jHandler(LogEntryCodec logEntryCodec,
			org.apache.log4j.Logger delegate) {
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
	public Formatter getFormatter() {
		return this.formatter;
	}

	/**
	 * Closes the handler
	 * 
	 * In this case, this method only "closes" this adapter, and not the
	 * underlying delegate.
	 */
	@Override
	public void close() throws SecurityException {
		/*
		 * Only flips close flag to true, does NOT affect delegate.
		 */
		this.closed = true;
	}

	/**
	 * Flushes the handler
	 * 
	 * In this caes, this method does nothing. i.e. it is a no-op.
	 */
	@Override
	public void flush() {
		/*
		 * Do nothing. No equivalent in delegate
		 */
		assert (true);
	}

	/**
	 * Publishes the JUL record to the underlying Log4j delegate
	 * 
	 * @param record
	 *            The JUL record to be logged to the delegate.
	 * 
	 */
	@Override
	public void publish(java.util.logging.LogRecord record) {
		if (this.closed) {
			throw new UnsupportedOperationException("already closed");
		}
		final org.apache.log4j.spi.LoggingEvent event = this.entryCodec
				.transcode(delegate, this.getFormatter(), record);
		this.delegate.callAppenders(event);
	}

}
