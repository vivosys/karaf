/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.karaf.shell.log;

import java.io.PrintStream;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.log.layout.PatternConverter;
import org.apache.karaf.shell.log.layout.PatternParser;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.ops4j.pax.logging.spi.PaxLoggingEvent;

/**
 * Displays the last log entries
 */
@Command(scope = "log", name = "display", description = "Displays log entries.")
public class DisplayLog extends OsgiCommandSupport {

    @Option(name = "-n", aliases = {}, description="Number of entries to display", required = false, multiValued = false)
    protected int entries;

    @Option(name = "-p", aliases = {}, description="Pattern for formatting the output", required = false, multiValued = false)
    protected String overridenPattern;

    @Option(name = "--no-color", description="Disable syntax coloring of log events", required = false, multiValued = false)
    protected boolean noColor;

    @Argument(index = 0, name = "logger", description = "The name of the logger. This can be ROOT, ALL, or the name of a logger specified in the org.ops4j.pax.logger.cfg file.", required = false, multiValued = false)
    String logger;

    protected String pattern;
    protected LruList events;
    protected String fatalColor;
    protected String errorColor;
    protected String warnColor;
    protected String infoColor;
    protected String debugColor;
    protected String traceColor;

    private static final String FATAL = "fatal";
    private static final String ERROR = "error";
    private static final String WARN = "warn";
    private static final String INFO = "info";
    private static final String DEBUG = "debug";
    private static final String TRACE = "trace";

    private static final char FIRST_ESC_CHAR = 27;
	private static final char SECOND_ESC_CHAR = '[';
    private static final char COMMAND_CHAR = 'm';

    public LruList getEvents() {
        return events;
    }

    public void setEvents(LruList events) {
        this.events = events;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getFatalColor() {
        return fatalColor;
    }

    public void setFatalColor(String fatalColor) {
        this.fatalColor = fatalColor;
    }

    public String getErrorColor() {
        return errorColor;
    }

    public void setErrorColor(String errorColor) {
        this.errorColor = errorColor;
    }

    public String getWarnColor() {
        return warnColor;
    }

    public void setWarnColor(String warnColor) {
        this.warnColor = warnColor;
    }

    public String getInfoColor() {
        return infoColor;
    }

    public void setInfoColor(String infoColor) {
        this.infoColor = infoColor;
    }

    public String getDebugColor() {
        return debugColor;
    }

    public void setDebugColor(String debugColor) {
        this.debugColor = debugColor;
    }

    public String getTraceColor() {
        return traceColor;
    }

    public void setTraceColor(String traceColor) {
        this.traceColor = traceColor;
    }

    protected Object doExecute() throws Exception {
        final PatternConverter cnv = new PatternParser(overridenPattern != null ? overridenPattern : pattern).parse();
        final PrintStream out = System.out;

        Iterable<PaxLoggingEvent> le = events.getElements(entries == 0 ? Integer.MAX_VALUE : entries);
        for (PaxLoggingEvent event : le) {
			if ((logger != null) && (event != null)
					&& (checkIfFromRequestedLog(event))) {
				display(cnv, event, out);
			} else if ((event != null) && (logger == null)) {
				display(cnv, event, out);
			}
        }
        out.println();
        return null;
    }
        
    protected boolean checkIfFromRequestedLog(PaxLoggingEvent event) {
    	return (event.getLoggerName().lastIndexOf(logger)>=0) ? true : false;
    }

    protected void display(PatternConverter cnv, PaxLoggingEvent event, PrintStream stream) {
        String color = getColor(event);
        StringBuffer sb = new StringBuffer();
        sb.setLength(0);
        if (color != null) {
            sb.append(FIRST_ESC_CHAR);
            sb.append(SECOND_ESC_CHAR);
            sb.append(color);
            sb.append(COMMAND_CHAR);
        }
        for (PatternConverter pc = cnv; pc != null; pc = pc.next) {
            pc.format(sb, event);
        }
        if (event.getThrowableStrRep() != null) {
            for (String r : event.getThrowableStrRep()) {
                sb.append(r).append('\n');
            }
        }
        if (color != null) {
            sb.append(FIRST_ESC_CHAR);
            sb.append(SECOND_ESC_CHAR);
            sb.append("0");
            sb.append(COMMAND_CHAR);
        }
        stream.print(sb.toString());
    }

    private String getColor(PaxLoggingEvent event) {
        String color = null;
        if (!noColor) {
            String lvl = event.getLevel().toString().toLowerCase();
            if (FATAL.equals(lvl)) {
                color = fatalColor;
            } else if (ERROR.equals(lvl)) {
                color = errorColor;
            } else if (WARN.equals(lvl)) {
                color = warnColor;
            } else if (INFO.equals(lvl)) {
                color = infoColor;
            } else if (DEBUG.equals(lvl)) {
                color = debugColor;
            } else if (TRACE.equals(lvl)) {
                color = traceColor;
            }
            if (color != null && color.length() == 0) {
                color = null;
            }
        }
        return color;
    }

}
