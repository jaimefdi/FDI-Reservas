package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Facultad;

public class FacultadDTO {

	private Long id;
	private String nombreFacultad;
	private String dir;
	
	public FacultadDTO(){}
	
	public FacultadDTO(Long id, String name, String dir){
		this.id = id;
		this.nombreFacultad = name;
		this.dir = dir;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public static FacultadDTO fromFacultadDTO(Facultad f){
		return new FacultadDTO(f.getId(), f.getNombreFacultad(), f.getDir());
	}
}
