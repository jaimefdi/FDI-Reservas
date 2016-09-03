package es.fdi.reservas.reserva.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.EdificioService;
import es.fdi.reservas.reserva.business.boundary.EspacioService;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Controller
@RequestMapping(value="/admin")
public class EspacioController {

	private UserService user_service;
	
	private EspacioService espacio_service;
	
	private EdificioService edificio_service;
	
	private ReservaService reserva_service;
	
	@Autowired
	public EspacioController(UserService userService, EspacioService es, EdificioService eds, ReservaService rs){
		user_service = userService;
		espacio_service = es;
		edificio_service = eds;
		reserva_service = rs;
	}
	
	
	
	@RequestMapping(value="/administrar/espacios")
	public String espacios(){
		return "redirect:/admin/administrar/espacios/page/1";
	}
	
	
	@RequestMapping(value="/administrar/espacios/page/{pageNumber}", method=RequestMethod.GET)
    public String espaciosPaginados(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		Pageable pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Espacio> currentResults = espacio_service.getEspaciosPaginados(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_espacios");	
				
        return "index";
    }
	
	
	/*
	 * Filtro por nombre
	 */
	@RequestMapping(value="/administrar/espacios/nombre/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEspaciosPaginadosPorNombre(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		Pageable pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Espacio> currentResults = espacio_service.getEspaciosPorNombre(nombre,pageRequest);
        
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/filtrar_espacios");	
				
        return "index";
    }
	
	@RequestMapping(value="/administrar/espacios/restaurar/nombre/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEspaciosPaginadosPorNombreRestaurar(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		Pageable pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Espacio> currentResults = espacio_service.getEspaciosEliminadosPorNombre(nombre,pageRequest);
        
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/papelera_espacios");	
				
        return "index";
    }
	
	/*
	 * Filtrar por edificio
	 */
	@RequestMapping(value="/administrar/espacios/edificio/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEspaciosPaginadosPorEdificio(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		Page<Espacio> currentResults = espacio_service.getEspaciosPorEdificio(nombre,pageRequest);
        
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/filtrar_espacios");	
				
        return "index";
    }
	
	@RequestMapping(value="/administrar/espacios/restaurar/edificio/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEspaciosPaginadosPorEdificioRestaurar(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		Page<Espacio> currentResults = espacio_service.getEspaciosEliminadosPorEdificio(nombre,pageRequest);
        
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/papelera_espacios");	
				
        return "index";
    }

	@RequestMapping(value="/nuevoEspacio",method=RequestMethod.GET)
	public String nuevoEspacio(Model model){
		User u = user_service.getCurrentUser();
		model.addAttribute("Espacio", new Espacio());
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/nuevoEspacio");
		model.addAttribute("edificios", edificio_service.getEdificios());
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		return "index";
	}
	
	@RequestMapping(value="/administrar/espacio/editar/{idEspacio}", method=RequestMethod.GET)
	public String editarEspacio(@PathVariable("idEspacio") long idEspacio, Model model){
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("espacio", espacio_service.getEspacio(idEspacio));
		//System.out.println(user_service.getUser(idUser).getUsername());
		model.addAttribute("view", "admin/editarEspacio");
		return "index";
	}
	

	@RequestMapping(value = "/administrar/espacio/{numPag}/restaurar")
	public ModelAndView restaurarEspacios(@PathVariable("numPag") String numPag){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("pagina", numPag);
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("espacios", espacio_service.getEspaciosEliminados());
		model.addObject("view", "admin/papelera_espacios");
		return model;
	}
}
