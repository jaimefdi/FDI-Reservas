package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Espacio;

public class EspacioDTO {
	
	private Long id;
	
	private String nombreEspacio;
	
	private Long idEdificio;

	private int capacidad;
	
	private boolean microfono, proyector;
	
	private String tipoEspacio;
	
	private String imagen;
	
	public EspacioDTO(){}
	
	public EspacioDTO(Long id, String nombreEspacio, Long edificio, int capacidad, boolean microfono,
			boolean proyector, String tipoEspacio, String imagen) {
		super();
		this.id = id;
		this.nombreEspacio = nombreEspacio;
		this.idEdificio = edificio;
		this.capacidad = capacidad;
		this.microfono = microfono;
		this.proyector = proyector;
		this.tipoEspacio = tipoEspacio;
		this.imagen = imagen;
	}
	
	public EspacioDTO(String nombreEspacio, Long edificio, int capacidad, boolean microfono,
			boolean proyector, String tipoEspacio, String imagen) {
		super();
		this.nombreEspacio = nombreEspacio;
		this.idEdificio = edificio;
		this.capacidad = capacidad;
		this.microfono = microfono;
		this.proyector = proyector;
		this.tipoEspacio = tipoEspacio;
		this.imagen = imagen;
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

	
	
	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
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
	
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public static EspacioDTO fromEspacioDTOAutocompletar(Espacio e){
		return new EspacioDTO(e.getId(), e.getNombreEspacio(), e.getEdificio().getId(), e.getCapacidad(), e.isMicrofono(), e.isProyector(), e.getTipoEspacio().getTipo(), e.getImagen().getAttachmentUrl());
	}
	
	public static EspacioDTO fromEspacioDTO(Espacio e){
		return new EspacioDTO(e.getNombreEspacio(), e.getEdificio().getId(), e.getCapacidad(), e.isMicrofono(), e.isProyector(), e.getTipoEspacio().getTipo(), e.getImagen().getAttachmentUrl());
	}
}
