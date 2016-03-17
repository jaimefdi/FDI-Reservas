package es.fdi.reservas.reserva.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.boundary.ReservaSolapadaException;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;


@Controller
public class ReservaController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
	
	private ReservaService reserva_service;
	
	private UserService user_service;
	
	@Autowired
	public ReservaController(ReservaService rs, UserService us){
		reserva_service = rs;
		user_service = us;
	}
		
	@RequestMapping({"/","","/mis_reservas"})
    public String misReservas() {
        return "redirect:/mis_reservas/page/1";
    }
	
	@RequestMapping(value="/mis_reservas/page/{pageNumber}", method=RequestMethod.GET)
    public String misReservasPaginadas(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Reserva> currentResults = reserva_service.getReservasPaginadas(pageRequest);
        
        model.addAttribute("currentResults", currentResults);
    
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("user", u);
		model.addAttribute("view", "mis_reservas");
		
        return "index";
    }
	
	@RequestMapping(value="/nueva",method=RequestMethod.POST)
    public String crearReserva(Reserva r) throws ReservaSolapadaException {
		User u = user_service.getCurrentUser();
		long id_esp = r.getEspacio().getId();
		Espacio e = reserva_service.getSpaceById(id_esp);
		r.setEspacio(e);
		try{
			reserva_service.agregarReserva(r,u.getUsername());
		}
		catch(ReservaSolapadaException ex){
			logger.error("Problemas en la reserva", ex);
		}
        return "redirect:/mis_reservas";
    }
	
	
	@RequestMapping(value="/edificios", method=RequestMethod.GET)
    public ModelAndView edificios() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("user", user_service.getCurrentUser());
		//model.addObject("Facultades", reserva_service.getFacultades());// todas las facultades
		//model.addObject("allBuildings", reserva_service.getAllBuildings());
		model.addObject("view", "edificio");
        return model;
    }
	
	// Todos los espacios del edificio {id_edif}
	@RequestMapping(value="/edificio/{id_edif}/espacios", method=RequestMethod.GET)
    public ModelAndView espacios(@PathVariable("id_edif") long id_edif) {
		ModelAndView model = new ModelAndView("index");
		model.addObject("user", user_service.getCurrentUser());
		model.addObject("tipoEspacios",reserva_service.tiposDeEspacios(id_edif));
		model.addObject("allSpaces", reserva_service.getAllSpaces(id_edif));
		model.addObject("view", "espacios");
        return model;
    }
	
	
	// Carga los eventos del espacio {id_espacio} del edificio {id_edif}
	@RequestMapping(value="/edificio/{id_edif}/espacio/{id_espacio}", method=RequestMethod.GET) 
	public ModelAndView ReservaPaso2(@PathVariable("id_edif") long id_edif,@PathVariable("id_espacio") long id_espacio,
									 @RequestParam(required=false) boolean checked) {
		ModelAndView model = new ModelAndView("index");
		Espacio e = reserva_service.getSpaceById(id_espacio);
		Reserva r = new Reserva();
		r.setEspacio(e);	
		model.addObject("checked", checked);
		model.addObject("user", user_service.getCurrentUser());
		model.addObject("Reserva", r);
		model.addObject("allSpaces", reserva_service.getAllSpaces(id_edif));
		model.addObject("view", "reservas_aula_paso2");
		model.addObject("url","/edificio/" + id_edif + "/espacio/" + id_espacio );
		
        return model;
    }
	
	
	
	@RequestMapping(value="/reservas_fecha", method=RequestMethod.GET)
    public ModelAndView reservaPorFecha() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("user", user_service.getCurrentUser());
		model.addObject("view", "reservas_fecha");
        return model;
    }
	
	
	@RequestMapping(value="/gestion_reservas/page/{pageNumber}", method=RequestMethod.GET)
    public String gestiona_reservas(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Reserva> currentResults = reserva_service.getReservasPaginadas(pageRequest);
        
        model.addAttribute("currentResults", currentResults);
    
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("user", u);
		model.addAttribute("view", "gestion_reservas");
		
        return "index";
    }

}
