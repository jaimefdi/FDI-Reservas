package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Edificio;

public class EdificioDTO {

	private Long id;
	private String nombreEdificio;
	private String direccion;
	private Long idFacultad;
	
	private boolean deleted;
	
	private Long idAttachment;
	
	public EdificioDTO(){}
	
	public EdificioDTO(String nombre, String dir, Long idFac, boolean deleted, Long idAttachment){
		
		this.nombreEdificio = nombre;
		this.direccion = dir;
		this.idFacultad = idFac;
		this.deleted = deleted;
		this.idAttachment = idAttachment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
	
	public Long getIdAttachment() {
		return idAttachment;
	}

	public void setIdAttachment(Long idAttachment) {
		this.idAttachment = idAttachment;
	}

	public static EdificioDTO fromEdificioDTO(Edificio e){
		return new EdificioDTO(e.getNombreEdificio(), e.getDireccion(), e.getFacultad().getId(), e.isDeleted(), e.getImagen().getId());
	}
}
