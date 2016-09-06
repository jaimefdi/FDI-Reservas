package es.fdi.reservas.reserva.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import es.fdi.reservas.reserva.business.boundary.EspacioService;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.users.business.entity.User;

@Controller
public class EspacioController {

	private EspacioService espacio_service;
	
	@Autowired
	public EspacioController(EspacioService es){
		espacio_service = es;
	}
		
	@RequestMapping(value="/admin/administrar/espacios")
	public String espacios(){
		return "redirect:/admin/administrar/espacios/page/1";
	
	}
	@RequestMapping(value="/admin/administrar/espacios/page/{pageNumber}", method=RequestMethod.GET)
    public String misEspaciosPaginados(@PathVariable Integer pageNumber, Model model) {
	 
		User u = espacio_service.getCurrentUser();
	
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);		
        Page<Espacio> currentResults = espacio_service.getEspaciosPaginados(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("reservasPendientes", espacio_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("view", "admin/administrar_espacios");	
				
        return "index";
    }
	
	/*
	 * Filtro nombre
	 */
	
	@RequestMapping(value="/admin/administrar/espacios/nombre/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misEspaciosPaginadosPorNombre(@PathVariable Integer pageNumber, Model model) {
		
		User u = espacio_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);		
        Page<Espacio> currentResults = espacio_service.getEspaciosPaginados(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("reservasPendientes", espacio_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("view", "admin/administrar_espacios");	
				
        return "index";
    }

	@RequestMapping(value="/admin/nuevoEspacio",method=RequestMethod.GET)
	public String nuevoEspacio(Model model){
		
		User u = espacio_service.getCurrentUser();
		
		model.addAttribute("Espacio", new Espacio());
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/nuevoEspacio");
		model.addAttribute("edificios", espacio_service.getEdificios());
		model.addAttribute("reservasPendientes", espacio_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		return "index";
	}
	
	@RequestMapping(value="/admin/administrar/espacio/editar/{idEspacio}", method=RequestMethod.GET)
	public String editarEspacio(@PathVariable("idEspacio") long idEspacio, Model model){
		
		User u = espacio_service.getCurrentUser();
		
		model.addAttribute("User", u);
		model.addAttribute("reservasPendientes", espacio_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("espacio", espacio_service.getEspacio(idEspacio));
		model.addAttribute("User", espacio_service.getCurrentUser());
		model.addAttribute("espacio", espacio_service.getEspacio(idEspacio));
		model.addAttribute("view", "admin/editarEspacio");
		
		return "index";
	}
	

	@RequestMapping(value = "/admin/administrar/espacio/restaurar/page/{numPag}")
	public String restaurarEspacios(@PathVariable("numPag") Integer numPag, Model model){
		
		User u = espacio_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(numPag - 1, 5);
		Page<Espacio> currentResults = espacio_service.getEspaciosEliminadosPaginados(pageRequest);
		
		model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 
		
		model.addAttribute("User", u);
		model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current); 
		model.addAttribute("reservasPendientes", espacio_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("view", "admin/papelera_espacios");
		
		return "index";
	}
	
	
}
