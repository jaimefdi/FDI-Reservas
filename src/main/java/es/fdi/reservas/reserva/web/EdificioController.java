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
import es.fdi.reservas.reserva.business.boundary.EdificioService;
import es.fdi.reservas.reserva.business.entity.Edificio;

@Controller
public class EdificioController {

	private EdificioService edificio_service;
	
	@Autowired
	public EdificioController(EdificioService es){
		edificio_service = es;
	}
	
	@RequestMapping(value="/admin/administrar/edificios/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginados(@PathVariable Integer pageNumber, Model model) {
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Edificio> currentResults = edificio_service.getEdificiosPaginados(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", edificio_service.getCurrentUser());
		model.addAttribute("view", "admin/administrar_edificios");
		
        return "index";
    }
	
	@RequestMapping(value = "/admin/administrar/edificios/{numPag}/restaurar")
	public ModelAndView restaurarEdificios(@PathVariable("numPag") Long numPag){
		ModelAndView model = new ModelAndView("index");
		model.addObject("User", edificio_service.getCurrentUser());
		model.addObject("pagina", numPag);
		model.addObject("edificios", edificio_service.getEdificiosEliminados());
		model.addObject("view", "admin/papelera_edificios");
		return model;
	}
	
	@RequestMapping(value="/admin/administrar/edificios/editar/{idEdificio}", method=RequestMethod.GET)
	public String editarEdificio(@PathVariable("idEdificio") long idEdificio, Model model){
		model.addAttribute("User", edificio_service.getCurrentUser());
		model.addAttribute("edificio", edificio_service.getEdificio(idEdificio));
		model.addAttribute("view", "admin/editarEdificio");
		return "index";
	}
	
	@RequestMapping(value="/admin/nuevoEdificio",method=RequestMethod.GET)
	public ModelAndView nuevoEdificio(){
		ModelAndView model = new ModelAndView("admin/nuevoEdificio", "Edificio", new Edificio());
		model.addObject("User", edificio_service.getCurrentUser());
		model.addObject("view", "index");
		model.addObject("facultades", edificio_service.getFacultades());
		return model;
	}
}
