package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;

public class EdificioDTO {

	private String id;
	private String nombre_edificio;
	
	public EdificioDTO(String id, String spacename){
		this.id = id;
		this.nombre_edificio = spacename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	

	public String getNombre_edificio() {
		return nombre_edificio;
	}

	public void setNombre_edificio(String nombre_edificio) {
		this.nombre_edificio = nombre_edificio;
	}

	public static EdificioDTO fromEdificioDTO(Edificio e){
		return new EdificioDTO(e.getId().toString(), e.getNombre_edificio());
	}
}
