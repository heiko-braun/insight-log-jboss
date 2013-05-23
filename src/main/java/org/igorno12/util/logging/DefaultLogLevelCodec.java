package org.igorno12.util.logging;

import java.util.*;

/**
 * Default implementation of a log level codec.
 * 
 *
 */
public final class DefaultLogLevelCodec implements LogLevelCodec {
	
	private static final Map<JULLevelWrapper, Log4jLevelWrapper> JUL_TO_LOG4J;
	
	static {
		/*
		 * setup mapping from java util to log4j
		 */		
		final Map<JULLevelWrapper, Log4jLevelWrapper> map =
			new HashMap<JULLevelWrapper, Log4jLevelWrapper>();
		map.put(JULLevelWrapper.OFF, Log4jLevelWrapper.OFF);
		map.put(JULLevelWrapper.SEVERE, Log4jLevelWrapper.FATAL);
		map.put(JULLevelWrapper.WARNING, Log4jLevelWrapper.WARN);
		map.put(JULLevelWrapper.INFO, Log4jLevelWrapper.INFO);
		map.put(JULLevelWrapper.CONFIG, Log4jLevelWrapper.DEBUG);
		map.put(JULLevelWrapper.FINE, Log4jLevelWrapper.DEBUG);
		map.put(JULLevelWrapper.FINER, Log4jLevelWrapper.DEBUG);
		map.put(JULLevelWrapper.FINEST, Log4jLevelWrapper.TRACE);
		map.put(JULLevelWrapper.ALL, Log4jLevelWrapper.ALL);
		JUL_TO_LOG4J = Collections.unmodifiableMap(map);
	}
	
	private static final Map<Log4jLevelWrapper, JULLevelWrapper> LOG4J_TO_UTIL;
	
	static {
		/*
		 * setup mapping from log4j to java util
		 */
		Map<Log4jLevelWrapper, JULLevelWrapper> map =
			new HashMap<Log4jLevelWrapper, JULLevelWrapper>();
		map.put(Log4jLevelWrapper.OFF, JULLevelWrapper.OFF);
		map.put(Log4jLevelWrapper.FATAL, JULLevelWrapper.SEVERE);
		map.put(Log4jLevelWrapper.ERROR, JULLevelWrapper.SEVERE);
		map.put(Log4jLevelWrapper.WARN, JULLevelWrapper.WARNING);
		map.put(Log4jLevelWrapper.INFO, JULLevelWrapper.INFO);
		map.put(Log4jLevelWrapper.DEBUG, JULLevelWrapper.FINE);
		map.put(Log4jLevelWrapper.TRACE, JULLevelWrapper.FINEST);
		map.put(Log4jLevelWrapper.ALL, JULLevelWrapper.ALL);
		LOG4J_TO_UTIL = Collections.unmodifiableMap(map);
	}
	
	public DefaultLogLevelCodec() {
		super();
	}
	
	/**
	 * Gets the equivalent Java Utility Logging API (JUL) level wrapper used by the codec
	 * 
	 * @param wrapper	The original Log4j level wrapper
	 * @return			The equivalent JUL level wrapper used by the codec.
	 */
	public JULLevelWrapper getEquivalent(Log4jLevelWrapper wrapper) {
		if (wrapper == null) {
			return null;
		}
		return LOG4J_TO_UTIL.get(wrapper);
	}
	
	/**
	 * Gets the equivalent Log4j level wrapper used by the codec
	 * 
	 * @param wrapper	The original Java Utility Logging API (JUL) level wrapper
	 * @return			The equivalent Log4j wrapper used by the codec.
	 */
	public Log4jLevelWrapper getEquivalent(JULLevelWrapper wrapper) {
		if (wrapper == null) {
			return null;
		}
		return JUL_TO_LOG4J.get(wrapper);
	}

	public java.util.logging.Level getLevel(org.apache.log4j.Level level) {
		if (null == level) {
			return null;
		}
		for(Log4jLevelWrapper log4jLevel : Log4jLevelWrapper.values()) {
			if (log4jLevel.getLevel().equals(level)) {
				return this.getEquivalent(log4jLevel).getLevel();
			}
		}
		throw new IllegalArgumentException("level " + level + " is not supported");
	}

	public org.apache.log4j.Level getLevel(java.util.logging.Level level) {
		if (null == level) {
			return null;
		}
		for(JULLevelWrapper javaUtilLevel : JULLevelWrapper.values()) {
			if (javaUtilLevel.getLevel().equals(level)) {
				return this.getEquivalent(javaUtilLevel).getLevel();
			}
		}
		throw new IllegalArgumentException("level " + level + " is not supported");
	}

}
