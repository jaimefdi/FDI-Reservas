package es.fdi.reservas.reserva.web;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class BusquedaFecha {
	private Long idEdificio;
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private DateTime desde;
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private DateTime hasta;
	
	public BusquedaFecha(Long id, DateTime ini, DateTime fin){
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
