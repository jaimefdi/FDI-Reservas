package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Espacio;

public class EspacioTipoDTO {
	
	private String id;
	private String nombreEspacio;
	
	public EspacioTipoDTO(String id, String nombre){
		this.id = id;
		this.nombreEspacio = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombreEspacio() {
		return nombreEspacio;
	}

	public void setNombreEspacio(String nombreEspacio) {
		this.nombreEspacio = nombreEspacio;
	}
	
	public static EspacioTipoDTO fromEspacioTipoDTO(Espacio e){
		return new EspacioTipoDTO(e.getId().toString(), e.getNombreEspacio());
	}
}
