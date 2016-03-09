package es.fdi.reservas.reserva.web;

import org.joda.time.DateTime;

import es.fdi.reservas.reserva.business.entity.Reserva;

public class ReservaFullCalendarDTO {

	private Long id;
	private String title;
	private DateTime start;
	private DateTime end;
	private String nombreEspacio;
	private Long idEspacio;

	public ReservaFullCalendarDTO(){
		
	}
	
	public ReservaFullCalendarDTO(Long id, String title, DateTime start, DateTime end, String nombre_espacio, Long idEspacio) {
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
		this.nombreEspacio = nombre_espacio;
		this.idEspacio = idEspacio;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public DateTime getStart() {
		return this.start;
	}

	public DateTime getEnd() {
		return this.end;
	}


	public String getNombreEspacio() {
		return nombreEspacio;
	}


	public void setNombreEspacio(String nombreEspacio) {
		this.nombreEspacio = nombreEspacio;
	}


	public void setStart(DateTime start) {
		this.start = start;
	}


	public void setEnd(DateTime end) {
		this.end = end;
	}

  
	public Long getIdEspacio() {
		return idEspacio;
	}


	public void setIdEspacio(Long idEspacio) {
		this.idEspacio = idEspacio;
	}


	public static ReservaFullCalendarDTO fromReserva(Reserva reserva) {
		return new ReservaFullCalendarDTO(reserva.getId(), reserva.getAsunto(), reserva.getComienzo(), reserva.getFin(), reserva.getEspacio().getNombre_espacio(), reserva.getEspacio().getId());
	}
}
