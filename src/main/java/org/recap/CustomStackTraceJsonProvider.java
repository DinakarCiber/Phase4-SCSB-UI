package org.recap;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import net.logstash.logback.composite.JsonWritingUtils;
import net.logstash.logback.composite.loggingevent.StackTraceJsonProvider;

public class CustomStackTraceJsonProvider extends StackTraceJsonProvider {

	public CustomStackTraceJsonProvider() {
		super();
	}

	@Override
	public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
		IThrowableProxy throwableProxy = event.getThrowableProxy();
		if (throwableProxy != null) {
			String msg = getThrowableConverter().convert(event);
			String[] lines = msg.split("\\n\\t");
			JsonWritingUtils.writeStringArrayField(generator, getFieldName(), lines);

		}
	}
}