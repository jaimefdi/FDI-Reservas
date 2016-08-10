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
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Controller
public class FacultadController {
	
	private UserService user_service;
	
	private FacultadService facultad_service;
	
	@Autowired
	public FacultadController(UserService userService, FacultadService fs){
		user_service = userService;
		facultad_service = fs;
	}
	
	@RequestMapping(value="/admin/administrar/facultad/{pageNumber}", method=RequestMethod.GET)
    public String misFacultadesPaginadas(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Facultad> currentResults = facultad_service.getFacultadesPaginadas(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

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
		//System.out.println(user_service.getUser(idUser).getUsername());
		model.addAttribute("view", "admin/editarFacultad");
		return "index";
	}
	
	@RequestMapping(value="/admin/nuevaFacultad",method=RequestMethod.GET)
	public String nuevaFacultad(Model model){
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("Facultad", new Facultad());
		model.addAttribute("view", "admin/nuevaFacultad");
		return "index";
	}
	
	
	
	
	@RequestMapping(value = "/admin/administrar/facultad/{numPag}/restaurar",method=RequestMethod.GET)
	public ModelAndView restaurarFacultades(@PathVariable("numPag") Long numPag){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("facultades", facultad_service.getFacultadesEliminadas());
		
		model.addObject("pagina", numPag);
		model.addObject("view", "admin/papelera_facultades");
		return model;
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
