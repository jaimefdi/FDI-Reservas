package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.Espacio;

public class EspacioTipoDTO {
	
	private Long id;
	private String nombreEspacio;
	
	public EspacioTipoDTO(Long id, String nombre){
		this.id = id;
		this.nombreEspacio = nombre;
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
	
	public static EspacioTipoDTO fromEspacioTipoDTO(Espacio e){
		return new EspacioTipoDTO(e.getId(), e.getNombreEspacio());
	}
}
