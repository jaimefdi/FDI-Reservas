package es.fdi.reservas.reserva.business.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.fdi.reservas.reserva.business.control.GrupoReservaRepository;
import es.fdi.reservas.reserva.business.entity.GrupoReserva;
import es.fdi.reservas.reserva.web.GrupoReservaDTO;
import es.fdi.reservas.users.business.entity.User;

@Service
public class GrupoReservaService {

	private GrupoReservaRepository grupo_repository;
	
	@Autowired
	public GrupoReservaService(GrupoReservaRepository grr){
		this.grupo_repository = grr;
	}
	
	public GrupoReserva addNuevoGrupo(GrupoReserva grupo, User user){
		GrupoReserva newGrupo = null;
		List<GrupoReserva> listaGrupos = getGruposUsuario(user.getId());
		
		for(GrupoReserva g : listaGrupos){
			if(g.equals(grupo))
				return newGrupo;
		}
		newGrupo = new GrupoReserva(grupo.getNombreCorto(), grupo.getNombreLargo(), user);
		newGrupo = grupo_repository.save(newGrupo);
		
		return newGrupo;
	}
	
	public List<GrupoReserva> getGruposReservas(){
		return grupo_repository.findAll();
	}

	public List<GrupoReserva> getGruposPorTagName(String tagName, Long idUsuario) {
		return grupo_repository.getGruposPorTagName(tagName,idUsuario);
	}

	public GrupoReserva getGrupoReserva(long idGrupo) {
		return grupo_repository.findOne(idGrupo);
	}

	public void eliminarGrupo(long idGrupo) {
		grupo_repository.delete(idGrupo);
		
	}

	public List<GrupoReserva> getGruposUsuario(Long idUsuario) {
		return grupo_repository.findByUserId(idUsuario);
	}

	public void editarGrupoReserva(Long idGrupo, GrupoReservaDTO grDTO) {
		GrupoReserva grupo = getGrupoReserva(idGrupo);
		grupo.setNombreCorto(grDTO.getNombreCorto());
		grupo.setNombreLargo(grDTO.getNombreLargo());
		
		grupo_repository.save(grupo);		
	}
	
}
