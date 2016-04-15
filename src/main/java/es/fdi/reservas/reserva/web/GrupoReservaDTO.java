package es.fdi.reservas.reserva.web;

import es.fdi.reservas.reserva.business.entity.GrupoReserva;

public class GrupoReservaDTO {

	private Long id;
	private String nombreGrupo;
	
	public GrupoReservaDTO(){ }
	
	public GrupoReservaDTO(Long idGrupo, String nombre){ 
		this.id = idGrupo;
		this.nombreGrupo = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}
	
	public static GrupoReservaDTO fromGrupoReserva(GrupoReserva g){
		return new GrupoReservaDTO(g.getId(), g.getNombreGrupo());
	}
	
	
}
