package es.fdi.reservas.reserva.web;

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
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Controller
public class EspacioController {

	private ReservaService reserva_service;
	
	private UserService user_service;
	
	@Autowired
	public EspacioController(UserService userService, ReservaService reservaservice){
		user_service = userService;
		reserva_service = reservaservice;
	}
	
	@RequestMapping(value="/admin/administrar/espacios/{pageNumber}", method=RequestMethod.GET)
    public String misEspaciosPaginados(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Espacio> currentResults = reserva_service.getEspaciosPaginados(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_espacios");
		
        return "index";
    }

	@RequestMapping(value="/admin/nuevoEspacio",method=RequestMethod.GET)
	public String nuevoEspacio(Model model){
		User u = user_service.getCurrentUser();
		model.addAttribute("Espacio", new Espacio());
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/nuevoEspacio");
		model.addAttribute("edificios", reserva_service.getEdificios());
		return "index";
	}
	
	@RequestMapping(value="/admin/administrar/espacio/editar/{idEspacio}", method=RequestMethod.GET)
	public String editarEspacio(@PathVariable("idEspacio") long idEspacio, Model model){
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("espacio", reserva_service.getEspacio(idEspacio));
		//System.out.println(user_service.getUser(idUser).getUsername());
		model.addAttribute("view", "admin/editarEspacio");
		return "index";
	}
	

	@RequestMapping(value = "/admin/administrar/espacio/{numPag}/restaurar")
	public ModelAndView restaurarEspacios(@PathVariable("numPag") String numPag){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("pagina", numPag);
		model.addObject("espacios", reserva_service.getEspaciosEliminados());
		model.addObject("view", "admin/papelera_espacios");
		return model;
	}
}
