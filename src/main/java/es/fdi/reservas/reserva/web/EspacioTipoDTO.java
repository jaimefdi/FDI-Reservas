package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Espacio;

public class EspacioTipoDTO {
	
	private String id;
	private String nombre_espacio;
	
	public EspacioTipoDTO(String id, String spacename){
		this.id = id;
		this.nombre_espacio = spacename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre_espacio() {
		return nombre_espacio;
	}

	public void setNombre_espacio(String nombre_espacio) {
		this.nombre_espacio = nombre_espacio;
	}
	
	public static EspacioTipoDTO fromEspacioTipoDTO(Espacio e){
		return new EspacioTipoDTO(e.getId().toString(), e.getNombre_espacio());
	}
}
