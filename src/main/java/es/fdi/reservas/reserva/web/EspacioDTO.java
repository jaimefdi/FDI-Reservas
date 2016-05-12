package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Espacio;

public class EspacioDTO {
	
	private Long id;
	private String nombreEspacio;
	private String edificio;
	
	public EspacioDTO(){}
	
	public EspacioDTO(Long id, String nombre, String edif){
		this.id = id;
		this.nombreEspacio = nombre;
		this.edificio = edif;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreEspacio() {
		return nombreEspacio;
	}

	public void setNombreEspacio(String nombreEspacio) {
		this.nombreEspacio = nombreEspacio;
	}

	public String getEdificio() {
		return edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}
	
	public static EspacioDTO fromEspacioDTO(Espacio e){
		return new EspacioDTO(e.getId(), e.getNombreEspacio(), e.getEdificio().getNombreEdificio());
	}
}
