package es.fdi.reservas.reserva.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.reserva.business.entity.TipoEspacio;
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
    public ModelAndView MisReservas() {
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("user", u);
		model.addObject("allReservations", reserva_service.getAllReservations(u.getUsername()));
		model.addObject("view", "mis_reservas");
        return model;
    }
	
	@RequestMapping(value="/nueva",method=RequestMethod.POST)
    public String crearReserva(Reserva r) {
		User u = user_service.getCurrentUser();
		long id_esp = r.getEspacio().getId();
		Espacio e = reserva_service.getSpaceById(id_esp);
		r.setEspacio(e);
		
		reserva_service.agregarReserva(r,u.getUsername());
		
        return "redirect:/mis_reservas";
    }
	
	@RequestMapping(value="/reserva/editar/{id_res}",method=RequestMethod.PUT)
    public String editarReserva(@PathVariable("id_res") long id_res, Reserva nuevaReserva) {
		User u = user_service.getCurrentUser();
		
		Reserva viejaReserva = reserva_service.getReservaById(id_res);
		//se copian las propiedades de la nuevaReserva a la vieja y se guarda
		
		//reserva_service.actualizarReserva(r,u.getUsername());
		
        return "redirect:/mis_reservas";
    }
	
	@RequestMapping(value="/edificios", method=RequestMethod.GET)
    public ModelAndView edificios() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("user", user_service.getCurrentUser());
		model.addObject("Facultades", reserva_service.getFacultades());// todas las facultades
		model.addObject("allBuildings", reserva_service.getAllBuildings());
		model.addObject("view", "edificio");
        return model;
    }
	
	// Todos los espacios del edificio {id_edif}
	@RequestMapping(value="/edificio/{id_edif}/espacios", method=RequestMethod.GET)
    public ModelAndView espacios(@PathVariable("id_edif") long id_edif) {
		ModelAndView model = new ModelAndView("index");
		model.addObject("user", user_service.getCurrentUser());
		//model.addObject("typeSpaces",funcion);
		model.addObject("allSpaces", reserva_service.getAllSpaces(id_edif));
		model.addObject("view", "espacios");
        return model;
    }
	
	
	// Carga los eventos del espacio {id_espacio} del edificio {id_edif}
	@RequestMapping(value="/edificio/{id_edif}/espacio/{id_espacio}", method=RequestMethod.GET) 
	public ModelAndView ReservaPaso2(@PathVariable("id_edif") long id_edif,@PathVariable("id_espacio") long id_espacio) {
		ModelAndView model = new ModelAndView("index");
		Espacio e = reserva_service.getSpaceById(id_espacio);
		Reserva r = new Reserva();
		r.setEspacio(e);
		model.addObject("user", user_service.getCurrentUser());
		model.addObject("Reserva", r);
		model.addObject("allSpaces", reserva_service.getAllSpaces(id_edif));
		model.addObject("view", "reservas_aula_paso2");
		model.addObject("url","/edificio/" + id_edif + "/espacio/" + id_espacio );
		
        return model;
    }
	
	
	
	@RequestMapping(value="/reservas_fecha", method=RequestMethod.GET)
    public ModelAndView ReservaPrFecha() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("user", user_service.getCurrentUser());
		model.addObject("view", "reservas_fecha");
        return model;
    }
	
	
}
