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
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.boundary.ReservaSolapadaException;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.business.entity.GrupoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.reserva.business.entity.TipoEspacio;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@RestController
public class ReservasRestController {
	
	private ReservaService reserva_service;
	
	private GrupoReservaService grupo_service;
	
	private UserService user_service;
	
	@Autowired
	public ReservasRestController(ReservaService rs,GrupoReservaService grs, UserService us){
		reserva_service = rs;
		grupo_service = grs;
		user_service = us;
	}
	
	@RequestMapping(value="{idEspacio}/eventos", method=RequestMethod.GET)
	public List<ReservaFullCalendarDTO> reservasEspacio(@PathVariable("idEspacio") long idEspacio){
		List<Reserva> reservasEspacio = reserva_service.getReservasEspacio(idEspacio);
		List<Reserva> reservasTotales = new ArrayList<>();
		for(Reserva r : reservasEspacio) {
			if(!r.getReglasRecurrencia().isEmpty()){
				reservasTotales.addAll(r.getInstanciasEvento());
			}
			else
				reservasTotales.add(r);
		}
		
		List<ReservaFullCalendarDTO> result = new ArrayList<>();
		for(Reserva r : reservasTotales) {
			result.add(ReservaFullCalendarDTO.fromReserva(r));
		}
		 
		return result;
	}
	
	@RequestMapping(value="{idGrupo}/reservasGrupo", method=RequestMethod.GET)
	public List<ReservaFullCalendarDTO> reservasGrupo(@PathVariable("idGrupo") long idGrupo){
		List<Reserva> reservasGrupo = reserva_service.getReservasGrupo(idGrupo);
		List<Reserva> reservasTotales = new ArrayList<>();
		for(Reserva r : reservasGrupo) {
			if(!r.getReglasRecurrencia().isEmpty()){
				reservasTotales.addAll(r.getInstanciasEvento());
			}
			else
				reservasTotales.add(r);
		}
		
		List<ReservaFullCalendarDTO> result = new ArrayList<>();
		for(Reserva r : reservasTotales) {
			result.add(ReservaFullCalendarDTO.fromReserva(r));
		}
		 
		return result;
	}
	
	@RequestMapping(value="{idEspacio}/eventosMan", method=RequestMethod.GET)
	public List<ReservaFullCalendarDTO> reservasEspacioDeMañana(@PathVariable("idEspacio") long idEspacio){
		List<Reserva> reservasEspacio = reserva_service.getReservasEspacioDeMañana(idEspacio);
		List<Reserva> reservasTotales = new ArrayList<>();
		for(Reserva r : reservasEspacio) {
			if(!r.getReglasRecurrencia().isEmpty()){
				reservasTotales.addAll(r.getInstanciasEvento());
			}
			else
				reservasTotales.add(r);
		}
		
		List<ReservaFullCalendarDTO> result = new ArrayList<>();
		for(Reserva r : reservasTotales) {
			result.add(ReservaFullCalendarDTO.fromReserva(r));
		}
		 
		return result;
	}
	
	@RequestMapping(value="{idEspacio}/eventosTar", method=RequestMethod.GET)
	public List<ReservaFullCalendarDTO> reservasEspacioDeTarde(@PathVariable("idEspacio") long idEspacio){
		List<Reserva> reservasEspacio = reserva_service.getReservasEspacioDeTarde(idEspacio);
		List<Reserva> reservasTotales = new ArrayList<>();
		for(Reserva r : reservasEspacio) {
			if(!r.getReglasRecurrencia().isEmpty()){
				reservasTotales.addAll(r.getInstanciasEvento());
			}
			else
				reservasTotales.add(r);
		}
		
		List<ReservaFullCalendarDTO> result = new ArrayList<>();
		for(Reserva r : reservasTotales) {
			result.add(ReservaFullCalendarDTO.fromReserva(r));
		}
		 
		return result;
	}
	
	@RequestMapping(value="/misEventos", method=RequestMethod.GET)
	public List<ReservaFullCalendarDTO> reservasUsuario(){
		User user = user_service.getCurrentUser();
		List<Reserva> userReser = reserva_service.getReservasUsuario(user.getUsername());
		List<Reserva> reservasTotales = new ArrayList<>();
		for(Reserva r : userReser) {
			if(!r.getReglasRecurrencia().isEmpty()){
				reservasTotales.addAll(r.getInstanciasEvento());
			}
			else
				reservasTotales.add(r);
		}
		
		List<ReservaFullCalendarDTO> result = new ArrayList<>();
		for(Reserva r : reservasTotales) {
			result.add(ReservaFullCalendarDTO.fromReserva(r));
		}
		 
		return result;
	}
	
	@RequestMapping(value="{idEdificio}/tipoEspacio/{tipoEspacio}", method=RequestMethod.GET)
	public List<EspacioTipoDTO> todosLosEspacios(@PathVariable("idEdificio") long idEdificio, @PathVariable("tipoEspacio") String tipoEspacio){
		List<EspacioTipoDTO> result = new ArrayList<>();
		List<Espacio> allSpaces = new ArrayList<>();
		
		if(tipoEspacio.equals("Todos"))
			 allSpaces = reserva_service.getEspaciosEdificio(idEdificio);
		else
		     allSpaces = reserva_service.getTiposEspacio(idEdificio, TipoEspacio.fromTipoEspacio(tipoEspacio));
		
		
		for(Espacio e : allSpaces) {
			result.add(EspacioTipoDTO.fromEspacioTipoDTO(e));
		}
		 
		return result;
			
	}
	
	
	
	@RequestMapping(value="/facultad/{idFacultad}", method=RequestMethod.GET)
	public List<EdificioDTO> edificiosDeUnaFacultad(@PathVariable("idFacultad") long idFacultad){
		List<EdificioDTO> result = new ArrayList<>();
		List<Edificio> edificios = new ArrayList<>();
			
		edificios = reserva_service.getEdificiosFacultad(idFacultad);
			
		for(Edificio e : edificios) {
			result.add(EdificioDTO.fromEdificioDTO(e));
		}
		 
		return result;		
	}
	
	@RequestMapping(value="/reserva/{idReserva}",method=RequestMethod.DELETE)
    public void eliminarReserva(@PathVariable("idReserva") long idReserva) {
		  Reserva r = reserva_service.getReserva(idReserva);
		  
		  if(r.getRecurrenteId() == null){
			  reserva_service.eliminarReserva(idReserva);
		  }
		  else{
			  
		  }
		
		
    }
	
	
	@RequestMapping(value = "/reserva/{idReserva}", method = RequestMethod.PUT)
	public void editarReserva(@PathVariable("idReserva") long idReserva, @RequestBody ReservaFullCalendarDTO reservaActualizada) {
		//reserva_service.editaReserva(reservaActualizada);
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
	
	@RequestMapping(value = "/grupo/tag/{tagName}", method = RequestMethod.GET)
	public List<GrupoReservaDTO> gruposFiltro(@PathVariable("tagName") String tagName) {
		
		List<GrupoReservaDTO> result = new ArrayList<>();
		List<GrupoReserva> grupos = new ArrayList<>();

		grupos = grupo_service.getGruposPorTagName(tagName);
				
		for(GrupoReserva g : grupos) {
			result.add(GrupoReservaDTO.fromGrupoReserva(g));
		}
		 
		return result;
	}
	
	
	@RequestMapping(value="/nuevaReservaAJAX",method=RequestMethod.POST)
    public void crearReservaAJAX(@RequestBody ReservaFullCalendarDTO rf) throws ReservaSolapadaException {
		User u = user_service.getCurrentUser();
		Reserva r = new Reserva();
		r.setComienzo(rf.getStart());
		r.setFin(rf.getEnd());
		r.setAsunto(rf.getTitle());
		r.setEspacio(reserva_service.getEspacio(rf.getIdEspacio()));
		r.setReservaColor(rf.getColor());
		r.setReglasRecurrencia(rf.getReglasRecurrencia());
		try{
			reserva_service.agregarReserva(r,u.getUsername());
		}
		catch(ReservaSolapadaException ex){
			System.out.println(ex.getMessage());
		}
		
    }
	
	
	
	@RequestMapping(value="/editarReserRecurrente",method=RequestMethod.POST)
    public void editarReserRecurrente(@RequestBody ReservaFullCalendarDTO rf){		
		reserva_service.editarReservaRecurrente(rf);
    }
	
	
	
	
	
	
}
