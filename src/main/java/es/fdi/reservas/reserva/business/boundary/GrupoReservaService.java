package es.fdi.reservas.reserva.business.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.fdi.reservas.reserva.business.control.GrupoReservaRepository;
import es.fdi.reservas.reserva.business.entity.GrupoReserva;

@Service
public class GrupoReservaService {

	private GrupoReservaRepository grupo_repository;
	
	@Autowired
	public GrupoReservaService(GrupoReservaRepository grr){
		this.grupo_repository = grr;
	}
	
	public GrupoReserva addNuevoGrupo(GrupoReserva grupo){
		GrupoReserva newGrupo = new GrupoReserva(grupo.getNombreGrupo(), grupo.getDescripcion());
		newGrupo = grupo_repository.save(newGrupo);
		
		return newGrupo;
	}
	
	public List<GrupoReserva> gruposReservas(){
		return grupo_repository.findAll();
	}

	public List<GrupoReserva> getGruposPorTagName(String tagName) {
		return grupo_repository.getGruposPorTagName(tagName);
	}

	public GrupoReserva getGrupoReserva(long idGrupo) {
		return grupo_repository.findOne(idGrupo);
	}

	public void eliminarGrupo(long idGrupo) {
		grupo_repository.delete(idGrupo);
		
	}
	
}
