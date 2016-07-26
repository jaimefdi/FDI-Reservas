package es.fdi.reservas.reserva.web;

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

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Controller
public class EdificioController {

	
	private ReservaService reserva_service;
	
	private UserService user_service;
	
	@Autowired
	public EdificioController(UserService userService, ReservaService reservaservice){
		user_service = userService;
		reserva_service = reservaservice;
	}
	
	@RequestMapping(value="/admin/administrar/edificios/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginados(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
	    Page<Edificio> currentResults = reserva_service.getEdificiosPaginados(pageRequest);
	            
	    model.addAttribute("currentResults", currentResults);
	    
	    int current = currentResults.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, currentResults.getTotalPages()); 
	
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_edificios");
		
		
		
        return "index";
    }
	
	@RequestMapping(value = "/admin/administrar/edificios/{numPag}/restaurar")
	public ModelAndView restaurarEdificios(@PathVariable("numPag") Long numPag){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("pagina", numPag);
		model.addObject("edificios", reserva_service.getEdificiosEliminados());
		model.addObject("view", "admin/papelera_edificios");
		return model;
	}
	
	@RequestMapping(value="/admin/administrar/edificios/editar/{idEdificio}", method=RequestMethod.GET)
	public String editarEdificio(@PathVariable("idEdificio") long idEdificio, Model model){
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("edificio", reserva_service.getEdificio(idEdificio));
		model.addAttribute("facultades", reserva_service.getFacultades());
		//System.out.println(user_service.getUser(idUser).getUsername());
		model.addAttribute("view", "admin/editarEdificio");
		return "index";
	}
	
	@RequestMapping(value="/admin/nuevoEdificio",method=RequestMethod.GET)
	public String nuevoEdificio(Model model){
		
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("Edificio", new Edificio());
		model.addAttribute("view", "admin/nuevoEdificio");
		model.addAttribute("facultades", reserva_service.getFacultades());
		return "index";
	}
}
