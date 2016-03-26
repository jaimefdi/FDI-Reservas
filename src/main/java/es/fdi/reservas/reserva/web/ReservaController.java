package es.fdi.reservas.reserva.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.boundary.ReservaSolapadaException;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;


@Controller
public class ReservaController {
	
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
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 7, new Sort(new Sort.Order(Sort.Direction.ASC,"comienzo")));
        Page<Reserva> currentResults = reserva_service.getReservasUsuario(u.getUsername(),pageRequest);
        
        model.addAttribute("currentResults", currentResults);
    
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("view", "mis_reservas");
		
        return "index";
    }
	
	@RequestMapping(value="/nueva",method=RequestMethod.POST)
    public String crearReserva(Reserva r) throws ReservaSolapadaException {
		User u = user_service.getCurrentUser();
		long idEspacio = r.getEspacio().getId();
		Espacio e = reserva_service.getEspacio(idEspacio);
		r.setEspacio(e);
		r.setRecurrencia("RRULE:FREQ=DAILY;COUNT=3");
		try{
			reserva_service.agregarReserva(r,u.getUsername());
		}
		catch(ReservaSolapadaException ex){
			
		}
        return "redirect:/mis_reservas";
    }
	
	
	@RequestMapping(value="/edificios", method=RequestMethod.GET)
    public String edificios(Model model) {
		
		User user = user_service.getCurrentUser();
		model.addAttribute("User", user);
		List<Edificio> edificios = reserva_service.getEdificiosFacultad(user.getFacultad().getId());
		if(edificios.size() > 1){
		   model.addAttribute("Edificios", edificios);
		   model.addAttribute("view", "edificios");
		   
		   return "index";
		}
		else{
		   long idEdificio = edificios.get(0).getId();
		   
		   return "redirect:/edificio/" + idEdificio + "/espacios";
		}
		

        
    }
	
	
	@RequestMapping(value="/edificio/{idEdificio}/espacios", method=RequestMethod.GET)
    public ModelAndView espacios(@PathVariable("idEdificio") long idEdificio) {
		ModelAndView model = new ModelAndView("index");
		model.addObject("User", user_service.getCurrentUser());
		model.addObject("Edificio", reserva_service.getEdificio(idEdificio));
		model.addObject("TiposEspacio",reserva_service.tiposDeEspacios(idEdificio));
		model.addObject("Espacios", reserva_service.getEspaciosEdificio(idEdificio));
		model.addObject("view", "espacios");
		
        return model;
    }
	
	
	@RequestMapping(value="/edificio/{idEdificio}/espacio/{idEspacio}", method=RequestMethod.GET) 
	public ModelAndView ReservaPaso2(@PathVariable("idEdificio") long idEdificio,@PathVariable("idEspacio") long idEspacio,
									 @RequestParam(required=false) boolean checked) {
		ModelAndView model = new ModelAndView("index");
		Espacio e = reserva_service.getEspacio(idEspacio);
		Reserva r = new Reserva();
		r.setEspacio(e);	
		model.addObject("checked", checked);
		model.addObject("User", user_service.getCurrentUser());
		model.addObject("Reserva", r);
		model.addObject("Espacios", reserva_service.getEspaciosEdificio(idEdificio));
		model.addObject("view", "reservas_calendario");
		model.addObject("url","/edificio/" + idEdificio + "/espacio/" + idEspacio );
		
        return model;
    }
	
	
	@RequestMapping(value="/reservas_fecha", method=RequestMethod.GET)
    public ModelAndView reservaPorFecha() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("User", user_service.getCurrentUser());
		model.addObject("view", "reservas_fecha");
		
        return model;
    }
	
	
	
	
	
}
