/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * The contents of this file are subject to the terms of the GNU
 * General Public License Version 2 only ("GPL").  
 * You may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at http://www.gnu.org/licenses/gpl-2.0.html
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * This particular file is designated as subject to the "Classpath"
 * exception. 
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Copyright (c) 2009-2010 Oracle and/or its affiliates. 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fusesource.insight.log.jboss;

import java.lang.management.ManagementFactory;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.igorno12.util.logging.DefaultLogEntryCodec;
import org.igorno12.util.logging.LogEntryCodec;
import org.jboss.logmanager.formatters.PatternFormatter;

public class InsightLogHandler extends Handler {
    private final ObjectName objectName;
    private final Formatter formatter = new PatternFormatter("%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n");
	private final Logger delegate = Logger.getRootLogger();
	private final LogEntryCodec entryCodec = new DefaultLogEntryCodec();

    public InsightLogHandler() {
        setFormatter(formatter);

        // register MBean
        try {
        	objectName = new ObjectName("org.fusesource.insight:type=LogQuery");
        } catch (Exception e) {
            throw new IllegalStateException("Problem creating object name.", e);
        }
    }

    private void log(LogRecord record) throws InstanceNotFoundException, ReflectionException, MBeanException {
        LoggingEvent event = entryCodec.transcode(delegate, this.getFormatter(), record);
    	ManagementFactory.getPlatformMBeanServer().invoke(objectName, "logMessage", new Object[] { event }, 
                new String[] { LoggingEvent.class.getName() });
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
     */
    @Override
    public void publish(LogRecord record) {
        try {
            log(record);
        } catch (InstanceNotFoundException e) {
            // waiting for the instance to be installed	
        } catch (Exception e) {
            throw new IllegalStateException("Can't invoke LogQuery MBean", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.logging.Handler#close()
     */
    @Override
    public void close() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.logging.Handler#flush()
     */
    @Override
    public void flush() {
    }

}
