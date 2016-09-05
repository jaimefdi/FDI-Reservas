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
import es.fdi.reservas.users.business.entity.User;

@Controller
public class EspacioController {

	private EspacioService espacio_service;
	
	private EdificioService edificio_service;
	
	private ReservaService reserva_service;
	
	@Autowired
	public EspacioController(EspacioService es, EdificioService eds, ReservaService rs){
		espacio_service = es;
		edificio_service = eds;
		reserva_service = rs;
	}
		
	@RequestMapping(value="/admin/administrar/espacios")
	public String espacios(){
		return "redirect:/admin/administrar/espacios/page/1";
	
	}
	@RequestMapping(value="/admin/administrar/espacios/page/{pageNumber}", method=RequestMethod.GET)
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
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(espacio_service.getCurrentUser().getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("view", "admin/administrar_espacios");	
				
        return "index";
    }
	
	/*
	 * Filtro nombre
	 */
	
	@RequestMapping(value="/admin/administrar/espacios/page/{pageNumber}", method=RequestMethod.GET)
    public String misEspaciosPaginadosPorNombre(@PathVariable Integer pageNumber, Model model) {
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
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(espacio_service.getCurrentUser().getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("view", "admin/administrar_espacios");	
				
        return "index";
    }

	@RequestMapping(value="/nuevoEspacio",method=RequestMethod.GET)
	public String nuevoEspacio(Model model){
		
		model.addAttribute("Espacio", new Espacio());
		model.addAttribute("User", espacio_service.getCurrentUser());
		model.addAttribute("view", "admin/nuevoEspacio");
		model.addAttribute("edificios", edificio_service.getEdificios());
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(espacio_service.getCurrentUser().getId(), EstadoReserva.PENDIENTE).size());
		return "index";
	}
	
	@RequestMapping(value="/admin/administrar/espacio/editar/{idEspacio}", method=RequestMethod.GET)
	public String editarEspacio(@PathVariable("idEspacio") long idEspacio, Model model){
		
		model.addAttribute("User", espacio_service.getCurrentUser());
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(espacio_service.getCurrentUser().getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("espacio", espacio_service.getEspacio(idEspacio));
		//System.out.println(user_service.getUser(idUser).getUsername());
		model.addAttribute("User", espacio_service.getCurrentUser());
		model.addAttribute("espacio", espacio_service.getEspacio(idEspacio));
		model.addAttribute("view", "admin/editarEspacio");
		
		return "index";
	}
	

	@RequestMapping(value = "/admin/administrar/espacio/{numPag}/restaurar")
	public String restaurarEspacios(@PathVariable("numPag") Integer numPag, Model model){
		
		PageRequest pageRequest = new PageRequest(numPag - 1, 5);
		Page<Espacio> currentResults = espacio_service.getEspaciosEliminadosPaginados(pageRequest);
		
		model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 
		
		model.addAttribute("User", espacio_service.getCurrentUser());
		model.addAttribute("pagina", numPag);
		model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(espacio_service.getCurrentUser().getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("currentResults", espacio_service.getEspaciosEliminados());
		model.addAttribute("view", "admin/papelera_espacios");
		
		return "index";
	}
	
	
}
