package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Espacio;

public class EspacioTipoDTO {
	
	private Long id;
	private String nombre_espacio;
	private int capacidad;
	private boolean microfono;
	private boolean proyector;
	private String tipoEspacio;
	private Long idEdificio;
	
	public EspacioTipoDTO(){}
	
	public EspacioTipoDTO(Long id, String spacename, int capacidad, boolean micro, boolean proyec, String tipoEspacio, Long idEdificio){
		this.id = id;
		this.nombre_espacio = spacename;
		this.capacidad = capacidad;
		this.microfono = micro;
		this.proyector = proyec;
		this.tipoEspacio = tipoEspacio;
		this.idEdificio = idEdificio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre_espacio() {
		return nombre_espacio;
	}

	public void setNombre_espacio(String nombre_espacio) {
		this.nombre_espacio = nombre_espacio;
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
	
	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
	}

	public static EspacioTipoDTO fromEspacioTipoDTO(Espacio e){
		return new EspacioTipoDTO(e.getId(), e.getNombre_espacio(), e.getCapacidad(), e.isMicrofono(), e.isProyector(), e.getTipoEspacio().getTipo(), e.getEdificio().getId());
	}
}
