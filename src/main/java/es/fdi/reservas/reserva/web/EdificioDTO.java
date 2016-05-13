package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Edificio;

public class EdificioDTO {

	private Long id;
	private String nombreEdificio;
	private String direccion;
	private Long idFacultad;
	
	public EdificioDTO(){}
	
	public EdificioDTO(Long id, String nombre, String dir, Long idFac){
		this.id = id;
		this.nombreEdificio = nombre;
		this.direccion = dir;
		this.idFacultad = idFac;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNombreEdificio() {
		return nombreEdificio;
	}

	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}


	public String getDireccion() {
		return direccion;
		}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
}

	
	public Long getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(Long idFacultad) {
		this.idFacultad = idFacultad;
	}

	public static EdificioDTO fromEdificioDTO(Edificio e){
		return new EdificioDTO(e.getId(), e.getNombreEdificio(), e.getDireccion(), e.getFacultad().getId());
	}
}
