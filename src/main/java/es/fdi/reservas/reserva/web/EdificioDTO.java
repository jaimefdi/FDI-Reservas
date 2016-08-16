package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Edificio;

public class EdificioDTO {

	private long id;
	private String nombreEdificio;
	private String direccion;
	private long idFacultad;
	
	private boolean deleted;
	
	private long idAttachment;
	
	public EdificioDTO(){}
	
	public EdificioDTO(String nombre, String dir, long idFac, boolean deleted, long idAttachment){
		
		this.nombreEdificio = nombre;
		this.direccion = dir;
		this.idFacultad = idFac;
		this.deleted = deleted;
		this.idAttachment = idAttachment;
	}

	public EdificioDTO(Long id, String nombre, String dir, long idFac, long idImg){
		this.id = id;
		this.nombreEdificio = nombre;
		this.direccion = dir;
		this.idFacultad = idFac;
		this.idAttachment = idImg;
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
	
	public long getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(long idFacultad) {
		this.idFacultad = idFacultad;
	}
	
	public long getIdAttachment() {
		return idAttachment;
	}

	public void setIdAttachment(long idAttachment) {
		this.idAttachment = idAttachment;
	}

	public static EdificioDTO fromEdificioDTO(Edificio e){
		return new EdificioDTO(e.getNombreEdificio(), e.getDireccion(), e.getFacultad().getId(), e.isDeleted(), e.getImagen().getId());
	}

	public static EdificioDTO fromEdificioDTOAutocompletar(Edificio e) {
		return new EdificioDTO(e.getId(), e.getNombreEdificio(), e.getDireccion(), e.getFacultad().getId(), e.getImagen().getId());
	}
}
