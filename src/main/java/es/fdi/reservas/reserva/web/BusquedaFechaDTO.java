package es.fdi.reservas.reserva.web;

import org.joda.time.DateTime;

public class BusquedaFechaDTO {

	private Long idEdificio;
	private DateTime desde;
	private DateTime hasta;
	
	public BusquedaFechaDTO() {}
	
	public BusquedaFechaDTO(Long id, DateTime ini, DateTime fin){
		this.idEdificio = id;
		this.desde = ini;
		this.hasta = fin;
	}

	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
	}

	public DateTime getDesde() {
		return desde;
	}

	public void setDesde(DateTime desde) {
		this.desde = desde;
	}

	public DateTime getHasta() {
		return hasta;
	}

	public void setHasta(DateTime hasta) {
		this.hasta = hasta;
	}
	
	
}
