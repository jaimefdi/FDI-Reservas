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

import es.fdi.reservas.reserva.business.boundary.EdificioService;
import es.fdi.reservas.reserva.business.boundary.FacultadService;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;

@Controller
public class EdificioController {

	private EdificioService edificio_service;
	
	private ReservaService reserva_service;
		
	@Autowired
	public EdificioController(EdificioService es){
		edificio_service = es;
		
	}
	
	@RequestMapping(value="/admin/administrar/edificios")
	public String espacios(){
		return "redirect:/admin/administrar/edificios/page/1";
	}
	@RequestMapping(value="/admin/administrar/edificios/page/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginados(@PathVariable Integer pageNumber, Model model) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
	    Page<Edificio> currentResults = edificio_service.getEdificiosPaginados(pageRequest);
	            
	    model.addAttribute("currentResults", currentResults);
	    
	    int current = currentResults.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, currentResults.getTotalPages()); 
	
	    model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(edificio_service.getCurrentUser().getId(), EstadoReserva.PENDIENTE).size());
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current); 
	    model.addAttribute("totalPages", currentResults.getTotalPages()); 
		model.addAttribute("User", edificio_service.getCurrentUser());
		model.addAttribute("view", "admin/administrar_edificios");
		
        return "index";
    }
	
	@RequestMapping(value = "/admin/administrar/edificios/restaurar/page/{numPag}")
	public String restaurarEdificios(@PathVariable("numPag") Integer numPag, Model model){
		
		PageRequest pageRequest = new PageRequest(numPag - 1, 5);
		Page<Edificio> currentResults = edificio_service.getEdificiosEliminadosPaginados(pageRequest);
		
		int current = currentResults.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, currentResults.getTotalPages());
	
	    model.addAttribute("currentResults", currentResults);
	    model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(edificio_service.getCurrentUser().getId(), EstadoReserva.PENDIENTE).size());
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
		model.addAttribute("User", edificio_service.getCurrentUser());
		model.addAttribute("pagina", numPag);
		model.addAttribute("view", "admin/papelera_edificios");
		return "index";	
	}
	
	@RequestMapping(value="/admin/administrar/edificios/editar/{idEdificio}", method=RequestMethod.GET)
	public String editarEdificio(@PathVariable("idEdificio") long idEdificio, Model model){
		
		model.addAttribute("edificio", edificio_service.getEdificio(idEdificio));
		model.addAttribute("facultades", edificio_service.getFacultades());
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(edificio_service.getCurrentUser().getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("User", edificio_service.getCurrentUser());
		model.addAttribute("edificio", edificio_service.getEdificio(idEdificio));
		model.addAttribute("view", "admin/editarEdificio");
		return "index";
	}
	
	@RequestMapping(value="/admin/nuevoEdificio",method=RequestMethod.GET)
	public String nuevoEdificio(Model model){
		
		model.addAttribute("User", edificio_service.getCurrentUser());
		model.addAttribute("Edificio", new Edificio());
		model.addAttribute("view", "admin/nuevoEdificio");
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(edificio_service.getCurrentUser().getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("facultades", edificio_service.getFacultades());
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
