package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.reserva.business.entity.TipoEspacio;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@RestController
public class ReservasRestController {
	
	private ReservaService reserva_service;
	
	private UserService user_service;
	
	@Autowired
	public ReservasRestController(ReservaService rs, UserService us){
		reserva_service = rs;
		user_service = us;
	}
	
	@RequestMapping(value="{id_espacio}/eventos", method=RequestMethod.GET)
	public List<ReservaFullCalendarDTO> allReserv(@PathVariable("id_espacio") long id_espacio){
		//List<Reserva> allReservas = reserva_service.getAllReservations();
		List<Reserva> allReservas = reserva_service.getReservations(id_espacio);//espacio 1
		List<ReservaFullCalendarDTO> result = new ArrayList<>();
		for(Reserva r : allReservas) {
			result.add(ReservaFullCalendarDTO.fromReserva(r));
		}
		 
		return result;
	}
	
	@RequestMapping(value="/misEventos", method=RequestMethod.GET)
	public List<ReservaFullCalendarDTO> reservasUsuario(){
		User user = user_service.getCurrentUser();
		List<Reserva> userReser = reserva_service.getAllReservations(user.getUsername());
		List<ReservaFullCalendarDTO> result = new ArrayList<>();
		for(Reserva r : userReser) {
			result.add(ReservaFullCalendarDTO.fromReserva(r));
		}
		 
		return result;
	}
	
	@RequestMapping(value="{id_edif}/tipoEspacio/{tipoEspacio}", method=RequestMethod.GET)
	public List<EspacioTipoDTO> todosLosEspacios(@PathVariable("id_edif") long id_edif, @PathVariable("tipoEspacio") String tipoEspacio){
		List<EspacioTipoDTO> result = new ArrayList<>();
		List<Espacio> allSpaces = new ArrayList<>();
		
		if(tipoEspacio.equals("Todos"))
			 allSpaces = reserva_service.getAllSpaces(id_edif);
		else
		     allSpaces = reserva_service.getTypeSpaces(id_edif, TipoEspacio.fromTipoEspacio(tipoEspacio));
		
		
		for(Espacio e : allSpaces) {
			result.add(EspacioTipoDTO.fromEspacioTipoDTO(e));
		}
		 
		return result;
			
	}
	
	
	
	@RequestMapping(value="/facultad/{id_facultad}", method=RequestMethod.GET)
	public List<EdificioDTO> edificiosDeUnaFacultad(@PathVariable("id_facultad") long id_facultad){
		List<EdificioDTO> result = new ArrayList<>();
		List<Edificio> edificios = new ArrayList<>();
		
		
		edificios = reserva_service.getEdificiosPorIdFacultad(id_facultad);
		
		
		for(Edificio e : edificios) {
			result.add(EdificioDTO.fromEdificioDTO(e));
		}
		 
		return result;
			
	}
	
	@RequestMapping(value="/reserva/{idReserva}",method=RequestMethod.DELETE)
    public void eliminarReserva(@PathVariable("idReserva") long idReserva) {
		
		reserva_service.eliminarReserva(idReserva);
		
        
    }
	
	@RequestMapping(value = "/reserva/{idReserva}", method = RequestMethod.PUT)
	public void editarReserva(@PathVariable("idReserva") long idReserva, @RequestBody ReservaFullCalendarDTO reservaActualizada) {
		reserva_service.editaReserva(reservaActualizada);
	}
	
	
	@RequestMapping(value = "/facultad/tag/{tagName}", method = RequestMethod.GET)
	public List<FacultadDTO> facultadesFiltro(@PathVariable("tagName") String tagName) {
		
		List<FacultadDTO> result = new ArrayList<>();
		List<Facultad> facultades = new ArrayList<>();
		
		
		facultades = reserva_service.getFacultadesPorTagName(tagName);
		
		
		for(Facultad f : facultades) {
			result.add(FacultadDTO.fromFacultadDTO(f));
		}
		 
		return result;
	}
	
	

	
	
}
