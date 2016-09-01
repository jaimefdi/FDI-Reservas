package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.fileupload.business.boundary.NewFileCommand;
import es.fdi.reservas.reserva.business.boundary.EdificioService;
import es.fdi.reservas.reserva.business.boundary.FacultadService;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;
import es.fdi.reservas.users.web.UserDTO;

@Controller
public class EdificioController {
	
	private UserService user_service;
	
	private EdificioService edificio_service;
	
	private FacultadService facultad_service;
	
	private ReservaService reserva_service;
	
	@Autowired
	public EdificioController(UserService userService, EdificioService es, FacultadService fs, ReservaService rs){
		user_service = userService;
		edificio_service = es;
		facultad_service = fs;
		reserva_service = rs;
	}
	
	@RequestMapping(value="/admin/administrar/edificios")
	public String espacios(){
		return "redirect:/admin/administrar/edificios/page/1";
	}
	
	@RequestMapping(value="/admin/administrar/edificios/page/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginados(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
	    Page<Edificio> currentResults = edificio_service.getEdificiosPaginados(pageRequest);
	            
	    model.addAttribute("currentResults", currentResults);
	    
	    int current = currentResults.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, currentResults.getTotalPages()); 
	
	    model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current); 
	    model.addAttribute("totalPages", currentResults.getTotalPages()); 
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_edificios");
		
        return "index";
    }
	
	/*
	 * Filtrar por nombre de edificio
	 */
	@RequestMapping(value="/admin/administrar/edificios/nombre/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginadosPorNombre(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
   
		Page<Edificio> currentResults = edificio_service.getEdificiosPorTagName(nombre, pageRequest);
        
	    model.addAttribute("currentResults", currentResults);
	    
	    int current = currentResults.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, currentResults.getTotalPages()); 
	
	    model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_edificios");
		
        return "index";
    }
	
	/*
	 * Filtrar por edificios eliminados (nombre)
	 */
	@RequestMapping(value="/admin/administrar/edificios/restaurar/nombre/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginadosPorNombreRestaurar(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
   
		Page<Edificio> currentResults = edificio_service.getEdificiosEliminadosPorTagName(nombre, pageRequest);
        
	    model.addAttribute("currentResults", currentResults);
	    
	    int current = currentResults.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, currentResults.getTotalPages()); 
	
	    model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/papelera_edificios");
		
        return "index";
    }
	
	/*
	 * Filtrar por direccion del edificio
	 */
	@RequestMapping(value="/admin/administrar/edificios/direccion/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginadosPorDireccion(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		Page<Edificio> currentResults = edificio_service.getEdificiosPorTagName(nombre, pageRequest);
        
	    model.addAttribute("currentResults", currentResults);
	    
	    int current = currentResults.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, currentResults.getTotalPages());
	
	    model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_edificios");
		
        return "index";
    }
	
	@RequestMapping(value="/admin/administrar/restaurar/edificios/direccion/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginadosPorDireccionRestaurar(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		Page<Edificio> currentResults = edificio_service.getEdificiosEliminadosPorTagName(nombre, pageRequest);
        
	    model.addAttribute("currentResults", currentResults);
	    
	    int current = currentResults.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, currentResults.getTotalPages());
	
	    model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/papelera_edificios");
		
        return "index";
    }
	
	/*
	 * Filtrar por facultad
	 */
	@RequestMapping(value="/admin/administrar/edificios/facultad/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginadosPorFacultad(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		Page<Edificio> currentResults = edificio_service.getEdificiosPorFacultad(nombre, pageRequest);
        
	    model.addAttribute("currentResults", currentResults);
	    
	    int current = currentResults.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, currentResults.getTotalPages());
	
	    model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_edificios");
		
        return "index";
    }
	
	@RequestMapping(value="/admin/administrar/edificios/restaurar/facultad/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginadosPorFacultadRestaurar(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		Page<Edificio> currentResults = edificio_service.getEdificiosEliminadosPorFacultad(nombre, pageRequest);
        
	    model.addAttribute("currentResults", currentResults);
	    
	    int current = currentResults.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, currentResults.getTotalPages());
	
	    model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/papelera_edificios");
		
        return "index";
    }
	
	@RequestMapping(value = "/admin/administrar/edificios/page/{numPag}/restaurar")
	public ModelAndView restaurarEdificios(@PathVariable("numPag") Long numPag){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("User", u);
		model.addObject("pagina", numPag);
		model.addObject("edificios", edificio_service.getEdificiosEliminados());
		model.addObject("view", "admin/papelera_edificios");
		return model;
	}
	
	@RequestMapping(value="/admin/administrar/edificios/editar/{idEdificio}", method=RequestMethod.GET)
	public String editarEdificio(@PathVariable("idEdificio") long idEdificio, Model model){
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("edificio", edificio_service.getEdificio(idEdificio));
		model.addAttribute("facultades", facultad_service.getFacultades());
		model.addAttribute("command", new NewFileCommand());
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("view", "admin/editarEdificio");
		return "index";
	}
	
	@RequestMapping(value="/admin/nuevoEdificio",method=RequestMethod.GET)
	public String nuevoEdificio(Model model){
		
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("Edificio", new Edificio());
		model.addAttribute("view", "admin/nuevoEdificio");
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("facultades", facultad_service.getFacultades());
		return "index";
	}
	
	@RequestMapping(value = "/edificio/tag/{tagName}", method = RequestMethod.GET)
	public List<EdificioDTO> edificiosFiltro(@PathVariable("tagName") String tagName) {
		
		List<EdificioDTO> result = new ArrayList<>();
		List<Edificio> edificios = new ArrayList<>();
		
		
		edificios = edificio_service.getEdificiosPorTagName(tagName);
		
		
		for(Edificio u : edificios) {
			result.add(EdificioDTO.fromEdificioDTOAutocompletar(u));
		}
		 
		return result;

	}
}
