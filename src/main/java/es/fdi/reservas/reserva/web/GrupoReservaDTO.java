package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.GrupoReserva;

public class GrupoReservaDTO {

	private Long id;
	private String nombreCorto;
	private String nombreLargo;
	
	public GrupoReservaDTO(){ }
	
	public GrupoReservaDTO(Long idGrupo, String nombreCorto, String nombreLargo){ 
		this.id = idGrupo;
		this.nombreCorto = nombreCorto;
		this.nombreLargo = nombreLargo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getNombreLargo() {
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	public static GrupoReservaDTO fromGrupoReserva(GrupoReserva g){
		return new GrupoReservaDTO(g.getId(), g.getNombreCorto(), g.getNombreLargo());
	}
	
	
}
