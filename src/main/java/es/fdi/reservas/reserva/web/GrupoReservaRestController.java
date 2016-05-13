package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import es.fdi.reservas.reserva.business.boundary.GrupoReservaService;
import es.fdi.reservas.reserva.business.entity.GrupoReserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@RestController
public class GrupoReservaRestController {

	private GrupoReservaService grupo_service;
	private UserService user_service;
	
	@Autowired
	public GrupoReservaRestController(GrupoReservaService grs, UserService us){
		this.grupo_service = grs;
		this.user_service = us;
	}
	
	@RequestMapping(value="/reserva/grupo/editar/{idGrupo}",method=RequestMethod.PUT)
    public void editarGrupoReserva(@PathVariable("idGrupo") Long idGrupo, @RequestBody GrupoReservaDTO grDTO){		
		grupo_service.editarGrupoReserva(idGrupo, grDTO);
    }
	

//	@RequestMapping(value="/grupo/{idGrupo}", method=RequestMethod.DELETE)
//	public void eliminarGrupo(@PathVariable("idGrupo") long idGrupo){
//		grupo_service.eliminarGrupo(idGrupo);
//	}
	
//	@RequestMapping(value = "/grupo/tag/{tagName}", method = RequestMethod.GET)
//	public List<GrupoReservaDTO> gruposFiltro(@PathVariable("tagName") String tagName) {
//		User user = user_service.getCurrentUser();
//		List<GrupoReservaDTO> result = new ArrayList<>();
//		List<GrupoReserva> grupos = new ArrayList<>();
//
//		grupos = grupo_service.getGruposPorTagName(tagName, user.getId());
//				
//		for(GrupoReserva g : grupos) {
//			result.add(GrupoReservaDTO.fromGrupoReserva(g));
//		}
//		 
//		return result;
//	}
	
}
