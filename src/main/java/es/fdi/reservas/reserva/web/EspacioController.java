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
import es.fdi.reservas.reserva.business.boundary.EspacioService;
import es.fdi.reservas.reserva.business.entity.Espacio;

@Controller
public class EspacioController {

	private EspacioService espacio_service;
	
	@Autowired
	public EspacioController(EspacioService es){
		espacio_service = es;
	}
	
	@RequestMapping(value="/admin/administrar/espacios/{pageNumber}", method=RequestMethod.GET)
    public String misEspaciosPaginados(@PathVariable Integer pageNumber, Model model) {
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Espacio> currentResults = espacio_service.getEspaciosPaginados(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", espacio_service.getCurrentUser());
		model.addAttribute("view", "admin/administrar_espacios");
		
        return "index";
    }

	@RequestMapping(value="/admin/nuevoEspacio",method=RequestMethod.GET)
	public ModelAndView nuevoEspacio(){
		ModelAndView model = new ModelAndView("admin/nuevoEspacio", "Espacio", new Espacio());
		model.addObject("edificios", espacio_service.getEdificios());
		
		return model;
	}
	
	@RequestMapping(value="/admin/administrar/espacio/editar/{idEspacio}", method=RequestMethod.GET)
	public String editarEspacio(@PathVariable("idEspacio") long idEspacio, Model model){
		model.addAttribute("User", espacio_service.getCurrentUser());
		model.addAttribute("espacio", espacio_service.getEspacio(idEspacio));
		model.addAttribute("view", "admin/editarEspacio");
		
		return "index";
	}
	

	@RequestMapping(value = "/admin/administrar/espacio/{numPag}/restaurar")
	public ModelAndView restaurarEspacios(@PathVariable("numPag") String numPag){
		ModelAndView model = new ModelAndView("index");
		model.addObject("User", espacio_service.getCurrentUser());
		model.addObject("pagina", numPag);
		model.addObject("espacios", espacio_service.getEspaciosEliminados());
		model.addObject("view", "admin/papelera_espacios");
		
		return model;
	}
}
