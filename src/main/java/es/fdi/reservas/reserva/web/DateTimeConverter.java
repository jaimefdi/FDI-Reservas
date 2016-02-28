package es.fdi.reservas.reserva.web;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

public class DateTimeConverter implements Converter<String, DateTime>{

	@Override
	public DateTime convert(String arg0) {
		DateTimeFormatter ftr = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		return ftr.parseDateTime(arg0);
	}

}
