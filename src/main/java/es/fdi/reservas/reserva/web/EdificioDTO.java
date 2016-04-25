package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Edificio;

public class EdificioDTO {

	private String id;
	private String nombreEdificio;
	private String direccion;
	
	public EdificioDTO(String id, String nombre, String dir){
		this.id = id;
		this.nombreEdificio = nombre;
		this.direccion = dir;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public static EdificioDTO fromEdificioDTO(Edificio e){
		return new EdificioDTO(e.getId().toString(), e.getNombreEdificio(), e.getDireccion());
	}
}
