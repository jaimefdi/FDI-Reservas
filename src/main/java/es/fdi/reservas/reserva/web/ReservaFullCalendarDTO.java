package es.fdi.reservas.reserva.web;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.Reserva;

public class ReservaFullCalendarDTO {

	private String id;
	private String title;
	private DateTime start;
	private DateTime end;
	private Espacio espacio;

	public ReservaFullCalendarDTO(String id, String title, DateTime start, DateTime end) {
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getStart() {
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		return fmt.print(start);
	}

	public String getEnd() {
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		return fmt.print(end);
	}

	

	
	public Espacio getEspacio() {
		return espacio;
	}


	public void setEspacio(Espacio espacio) {
		this.espacio = espacio;
	}


	public void setStart(DateTime start) {
		this.start = start;
	}


	public void setEnd(DateTime end) {
		this.end = end;
	}


	public static ReservaFullCalendarDTO fromReserva(Reserva reserva) {
		return new ReservaFullCalendarDTO(reserva.getId().toString(), reserva.getAsunto(), reserva.getFecha_ini(), reserva.getFecha_fin());
	}
}
