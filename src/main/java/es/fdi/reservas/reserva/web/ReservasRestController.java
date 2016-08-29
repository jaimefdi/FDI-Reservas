package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
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
	public List<ReservaDTO> reservasEspacio(@PathVariable("idEspacio") long idEspacio){
		List<Reserva> reservasEspacio = reserva_service.getReservasEspacio(idEspacio);
		List<Reserva> reservasTotales = new ArrayList<>();
		User user = user_service.getCurrentUser();
		for(Reserva r : reservasEspacio) {
			if(!r.getReglasRecurrencia().isEmpty()){
				reservasTotales.addAll(r.getInstanciasEvento());
			}
			else
				reservasTotales.add(r);
		}
		
		List<ReservaDTO> result = new ArrayList<>();
		for(Reserva r : reservasTotales) {
			if(user.getUsername().equals(r.getUser().getUsername())){
				result.add(ReservaDTO.fromReservaEditable(r));
			}
			else{
				result.add(ReservaDTO.fromReserva(r));
			}
			
		}
		 
		return result;
	}
	
	@RequestMapping(value="{idGrupo}/reservasGrupo", method=RequestMethod.GET)
	public List<ReservaDTO> reservasGrupo(@PathVariable("idGrupo") long idGrupo){
		User user = user_service.getCurrentUser();
		List<Reserva> reservasGrupo = reserva_service.getReservasGrupo(idGrupo, user.getId());
		List<Reserva> reservasTotales = new ArrayList<>();
		for(Reserva r : reservasGrupo) {
			if(!r.getReglasRecurrencia().isEmpty()){
				reservasTotales.addAll(r.getInstanciasEvento());
			}
			else
				reservasTotales.add(r);
		}
		
		List<ReservaDTO> result = new ArrayList<>();
		for(Reserva r : reservasTotales) {
			result.add(ReservaDTO.fromReservaEditable(r));
		}
		 
		return result;
	}
	
	@RequestMapping(value="{idEspacio}/eventosMan", method=RequestMethod.GET)
	public List<ReservaDTO> reservasEspacioDeMañana(@PathVariable("idEspacio") long idEspacio){
		List<Reserva> reservasEspacio = reserva_service.getReservasEspacioDeMañana(idEspacio);
		List<Reserva> reservasTotales = new ArrayList<>();
		User user = user_service.getCurrentUser();
		for(Reserva r : reservasEspacio) {
			if(!r.getReglasRecurrencia().isEmpty()){
				reservasTotales.addAll(r.getInstanciasEvento());
			}
			else
				reservasTotales.add(r);
		}
		
		List<ReservaDTO> result = new ArrayList<>();
		for(Reserva r : reservasTotales) {
			if(user.getUsername().equals(r.getUser().getUsername())){
				result.add(ReservaDTO.fromReservaEditable(r));
			}
			else{
				result.add(ReservaDTO.fromReserva(r));
			}
		}
		 
		return result;
	}
	
	@RequestMapping(value="{idEspacio}/eventosTar", method=RequestMethod.GET)
	public List<ReservaDTO> reservasEspacioDeTarde(@PathVariable("idEspacio") long idEspacio){
		List<Reserva> reservasEspacio = reserva_service.getReservasEspacioDeTarde(idEspacio);
		List<Reserva> reservasTotales = new ArrayList<>();
		User user = user_service.getCurrentUser();
		for(Reserva r : reservasEspacio) {
			if(!r.getReglasRecurrencia().isEmpty()){
				reservasTotales.addAll(r.getInstanciasEvento());
			}
			else
				reservasTotales.add(r);
		}
		
		List<ReservaDTO> result = new ArrayList<>();
		for(Reserva r : reservasTotales) {
			if(user.getUsername().equals(r.getUser().getUsername())){
				result.add(ReservaDTO.fromReservaEditable(r));
			}
			else{
				result.add(ReservaDTO.fromReserva(r));
			}
		}
		 
		return result;
	}
	
	@RequestMapping(value="/reservasCalendario", method=RequestMethod.GET)
	public List<ReservaDTO> reservasUsuario(){
		User user = user_service.getCurrentUser();
		List<Reserva> userReser = reserva_service.misReservasCalendario(user.getId());
		List<Reserva> reservasTotales = new ArrayList<>();
		for(Reserva r : userReser) {
			if(!r.getReglasRecurrencia().isEmpty()){
				reservasTotales.addAll(r.getInstanciasEvento());
			}
			else
				reservasTotales.add(r);
		}
		
		List<ReservaDTO> result = new ArrayList<>();
		for(Reserva r : reservasTotales) {
			result.add(ReservaDTO.fromReservaEditable(r));
		}
		 
		return result;
	}
	
	@RequestMapping(value = "/espacios/tag/{tagName}", method = RequestMethod.GET)
	public List<EspacioDTO> espaciosFiltro(@PathVariable("tagName") String tagName) {
		
		List<EspacioDTO> result = new ArrayList<>();
		List<Espacio> espacios = new ArrayList<>();

		espacios = reserva_service.getEspaciosPorTagName(tagName);
				
		for(Espacio e : espacios) {
			result.add(EspacioDTO.fromEspacioDTO(e));
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
		reserva_service.eliminarReserva(idReserva);
    }
	
	
	@RequestMapping(value = "/reserva/editar/{idReserva}", method = RequestMethod.PUT)
	public void editarReserva(@PathVariable("idReserva") long idReserva,
			                  @RequestBody ReservaDTO reservaActualizada)throws ReservaSolapadaException{
		
				reserva_service.editarReservaSimple(reservaActualizada);
		
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
	
	
	@RequestMapping(value="/nuevaReservaAJAX",method=RequestMethod.POST)
    public void crearReservaAJAX(@RequestBody ReservaDTO rf) throws ReservaSolapadaException {
		User user = user_service.getCurrentUser();
		Reserva r = new Reserva();
		r.setComienzo(rf.getStart());
		r.setFin(rf.getEnd());
		r.setAsunto(rf.getTitle());
		r.setEspacio(reserva_service.getEspacio(rf.getIdEspacio()));
		r.setReservaColor(rf.getColor());
		r.setReglasRecurrencia(rf.getReglasRecurrencia());
		r.setUser(user);
		Long idGrupo = rf.getIdGrupo();
		
		if(idGrupo != 0){
			r.setGrupoReserva(grupo_service.getGrupoReserva(idGrupo));
		}
			
		reserva_service.agregarReserva(r);		
			
    }
	
	
	
	@RequestMapping(value="/editarReserRecurrente",method=RequestMethod.POST)
    public void editarReserRecurrente(@RequestBody ReservaDTO rf){		
		reserva_service.editarReglasRecurrencia(rf);
    }
	
	@RequestMapping(value="/borrarExdate",method=RequestMethod.POST)
    public void eliminarExdate(@RequestBody ReservaDTO rf){		
		reserva_service.eliminarExdate(rf);
    }
	
	@RequestMapping(value = "/busquedaFecha", method = RequestMethod.POST)
	public List<ReservaDTO> busquedaPorFecha(@RequestBody BusquedaFechaDTO bfDTO){
		List<ReservaDTO> result = new ArrayList<>();
		List<Reserva> resultAux = new ArrayList<>();
		DateTime start = bfDTO.getDesde();
		DateTime end = bfDTO.getHasta();
		List<Espacio> espacios = reserva_service.getEspaciosEdificio(bfDTO.getIdEdificio());
		for(Espacio e : espacios){
			resultAux = reserva_service.getAllReservasConflictivas(e.getId(), start, end);
			
			if(resultAux.size() == 0){//si no hay reservas se crea una
				ReservaDTO reservaDTO = new ReservaDTO();
				reservaDTO.setIdEspacio(e.getId());
				reservaDTO.setNombreEspacio(e.getNombreEspacio());
				reservaDTO.setTitle("Sin asunto");
				reservaDTO.setStart(start);
				reservaDTO.setEnd(end);
				
				result.add(reservaDTO);
			}
			
			resultAux.clear();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/cambiarReservaDeCalendario/{idGrupo2}", method = RequestMethod.POST)
	public void cambiarDeCalendario(@PathVariable("idGrupo2") Long idGrupo2, @RequestBody ReservaDTO rfDTO){
		reserva_service.cambiarDeCalendario(idGrupo2, rfDTO);
	}
	

}
