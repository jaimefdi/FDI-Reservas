package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Espacio;

public class EspacioDTO {
	
	private Long id;
	private String nombreEspacio;
	private int capacidad;
	private boolean microfono;
	private boolean proyector;
	private String tipoEspacio;
	
	public EspacioDTO(){}

	public EspacioDTO(Long id, String nombreEspacio, int capacidad, boolean microfono, boolean proyector,
			String tipoEspacio) {
		super();
		this.id = id;
		this.nombreEspacio = nombreEspacio;
		this.capacidad = capacidad;
		this.microfono = microfono;
		this.proyector = proyector;
		this.tipoEspacio = tipoEspacio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreEspacio() {
		return nombreEspacio;
	}

	public void setNombreEspacio(String nombreEspacio) {
		this.nombreEspacio = nombreEspacio;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public boolean isMicrofono() {
		return microfono;
	}

	public void setMicrofono(boolean microfono) {
		this.microfono = microfono;
	}

	public boolean isProyector() {
		return proyector;
	}

	public void setProyector(boolean proyector) {
		this.proyector = proyector;
	}

	public String getTipoEspacio() {
		return tipoEspacio;
	}

	public void setTipoEspacio(String tipoEspacio) {
		this.tipoEspacio = tipoEspacio;
	}
	
	public static EspacioDTO fromEspacioDTO(Espacio e){
		return new EspacioDTO(e.getId(), e.getNombreEspacio(), e.getCapacidad(), e.isMicrofono(), e.isProyector(), e.getTipoEspacio().getTipo());
	}
	
}
