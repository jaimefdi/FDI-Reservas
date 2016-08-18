package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Edificio;

public class EdificioDTO {

	private long id;
	private String nombreEdificio;
	private String direccion;
	private Long idFacultad;
	
	private boolean deleted;
	
	private String imagen;
	
	public EdificioDTO(){}
	
	public EdificioDTO(String nombre, String dir, Long idFac, boolean deleted, String idAttachment){
		
		this.nombreEdificio = nombre;
		this.direccion = dir;
		this.idFacultad = idFac;
		this.deleted = deleted;
		this.imagen = idAttachment;
	}

	public EdificioDTO(Long id, String nombre, String dir, Long idFac, String idImg){
		this.id = id;
		this.nombreEdificio = nombre;
		this.direccion = dir;
		this.idFacultad = idFac;
		this.imagen = idImg;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String idAttachment) {
		this.imagen = idAttachment;
	}

	public static EdificioDTO fromEdificioDTO(Edificio e){
		return new EdificioDTO(e.getNombreEdificio(), e.getDireccion(), e.getFacultad().getId(), e.isDeleted(), e.getImagen().getAttachmentUrl());
	}

	public static EdificioDTO fromEdificioDTOAutocompletar(Edificio e) {
		return new EdificioDTO(e.getId(), e.getNombreEdificio(), e.getDireccion(), e.getFacultad().getId(), e.getImagen().getAttachmentUrl());
	}
}
