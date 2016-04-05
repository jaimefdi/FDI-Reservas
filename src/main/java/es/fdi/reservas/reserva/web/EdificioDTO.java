package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;

public class EdificioDTO {

	private Long id;
	private String nombre_edificio;
	private Long idFacultad;
	
	public EdificioDTO(Long id, String spacename, Long idFacultad){
		this.id = id;
		this.nombre_edificio = spacename;
		this.idFacultad = idFacultad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(Long idFacultad) {
		this.idFacultad = idFacultad;
	}

	public String getNombre_edificio() {
		return nombre_edificio;
	}

	public void setNombre_edificio(String nombre_edificio) {
		this.nombre_edificio = nombre_edificio;
	}

	public static EdificioDTO fromEdificioDTO(Edificio e){
		return new EdificioDTO(e.getId(), e.getNombre_edificio(), e.getFacultad().getId());
	}
}
