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

import es.fdi.reservas.reserva.business.boundary.FacultadService;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Controller
public class FacultadController {
	
	private UserService user_service;
	
	private FacultadService facultad_service;

	private ReservaService reserva_service;
	
	@Autowired
	public FacultadController(UserService userService, FacultadService fs, ReservaService rs){
		user_service = userService;
		facultad_service = fs;
		reserva_service = rs;
	}
	
	@RequestMapping(value="/admin/administrar/facultad")
	public String espacios(){
		return "redirect:/admin/administrar/facultad/page/1";
	}
	
	@RequestMapping(value="/admin/administrar/facultad/page/{pageNumber}", method=RequestMethod.GET)
    public String misFacultadesPaginadas(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Facultad> currentResults = facultad_service.getFacultadesPaginadas(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_facultad");
		
        return "index";
    }
	
	/*
	 * Filtrar por nombre
	 */
	@RequestMapping(value="/admin/administrar/facultad/nombre/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misFacultadesPaginadasPorNombre(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Facultad> currentResults = facultad_service.getFacultadesPorTagName(nombre, pageRequest);
               
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages());

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_facultad");
		
        return "index";
    }
	
	@RequestMapping(value="/admin/administrar/facultad/restaurar/nombre/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misFacultadesPaginadasPorNombreRestaurar(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Facultad> currentResults = facultad_service.getFacultadesEliminadasPorTagName(nombre, pageRequest);
               
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages());

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/papelera_facultad");
		
        return "index";
    }
	
	/*
	 * Filtrar por web
	 */
	
	@RequestMapping(value="/admin/administrar/facultad/web/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misFacultadesPaginadasPorWeb(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		Page<Facultad> currentResults = facultad_service.getFacultadesPorWeb(nombre, pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_facultad");
		
        return "index";
    }
	
	@RequestMapping(value="/admin/administrar/facultad/restaurar/web/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misFacultadesPaginadasPorWebRestaurar(@PathVariable Integer pageNumber, Model model, @PathVariable String nombre) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		Page<Facultad> currentResults = facultad_service.getFacultadesEliminadasPorWeb(nombre, pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_facultad");
		
        return "index";
    }
	
	@RequestMapping(value="/admin/administrar/facultad/editar/{idFacul}", method=RequestMethod.GET)
	public String editarFacultad(@PathVariable("idFacul") long idFacul, Model model){
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("facultad", facultad_service.getFacultad(idFacul));
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		//System.out.println(user_service.getUser(idUser).getUsername());
		model.addAttribute("view", "admin/editarFacultad");
		return "index";
	}
	
	@RequestMapping(value="/admin/nuevaFacultad",method=RequestMethod.GET)
	public String nuevaFacultad(Model model){
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("Facultad", new Facultad());
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("view", "admin/nuevaFacultad");
		return "index";
	}
	
	
	
	
	@RequestMapping(value = "/admin/administrar/facultad/restaurar/page/{numPag}",method=RequestMethod.GET)
	public String restaurarFacultades(@PathVariable("numPag") Integer numPag, Model model){
		//ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(numPag - 1, 5);
		Page<Facultad> currentResults = facultad_service.getFacultadesEliminadasPaginadas(pageRequest);
		
		int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 
		
		model.addAttribute("currentResults", currentResults);
		model.addAttribute("User", u);
		model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("pagina", numPag);
		model.addAttribute("view", "admin/papelera_facultades");
		return "index";
	}
	

	@RequestMapping(value = "/facultades/tag/{tagName}", method = RequestMethod.GET)
	public List<FacultadDTO> facultadesFiltro(@PathVariable("tagName") String tagName) {
		
		List<FacultadDTO> result = new ArrayList<>();
		List<Facultad> facultades = new ArrayList<>();

		facultades = facultad_service.getFacultadesPorTagName(tagName);
				
		for(Facultad f : facultades) {
			result.add(FacultadDTO.fromFacultadDTOAutocompletar(f));
		}
		 
		return result;
	}
	
}
