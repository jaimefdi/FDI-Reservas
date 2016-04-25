package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Facultad;

public class FacultadDTO {

	private String id;
	private String nombreFacultad;
	private String webFacultad;
	
	public FacultadDTO(String id, String name, String web){
		this.id = id;
		this.nombreFacultad = name;
		this.webFacultad = web;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}


	public String getWebFacultad() {
		return webFacultad;
	}

	public void setWebFacultad(String webFacultad) {
		this.webFacultad = webFacultad;
	}

	public static FacultadDTO fromFacultadDTO(Facultad f){
		return new FacultadDTO(f.getId().toString(), f.getNombreFacultad(), f.getWebFacultad());
	}
}
