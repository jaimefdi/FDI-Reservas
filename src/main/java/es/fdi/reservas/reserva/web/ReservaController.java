package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
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
    public ModelAndView MisReservas() {
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("user", u);
		model.addObject("allReservations", reserva_service.getAllReservations(u.getUsername()));
		model.addObject("view", "mis_reservas");
		model.addObject("uri", "/");
        return model;
    }
	
	@RequestMapping(value="/nueva",method=RequestMethod.POST)
    public ModelAndView crearReserva(Reserva r) {
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("user", u);
		reserva_service.agregarReserva(r,u.getUsername());
		model.addObject("allReservations", reserva_service.getAllReservations(u.getUsername()));
		model.addObject("view", "mis_reservas");
		model.addObject("uri", "/");
        return model;
    }
	
	
	@RequestMapping(value="/edificios", method=RequestMethod.GET)
    public ModelAndView Edificios() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("user", user_service.getCurrentUser());
		model.addObject("allBuildings", reserva_service.getAllBuildings());
		model.addObject("view", "edificio");
		model.addObject("uri", "/reservar-por-aulas/paso-1");
        return model;
    }
	
	@RequestMapping(value="/edificio/{id_edif}/espacios", method=RequestMethod.GET)
    public ModelAndView Espacios(@PathVariable("id_edif") long id_edif) {
		ModelAndView model = new ModelAndView("index");
		model.addObject("user", user_service.getCurrentUser());
		model.addObject("allSpaces", reserva_service.getAllSpaces(id_edif));
		model.addObject("view", "espacios");
		model.addObject("uri", "/reservar-por-aulas/paso-1");
        return model;
    }
	
	
	//carga los eventos del espacio {id_espacio} del edificio {id_edif}
	@RequestMapping(value="/edificio/{id_edif}/espacio/{id_espacio}", method=RequestMethod.GET) 
	public ModelAndView ReservaPaso2(@PathVariable("id_edif") long id_edif,@PathVariable("id_espacio") long id_espacio) {
		ModelAndView model = new ModelAndView("index");
		model.addObject("user", user_service.getCurrentUser());
		model.addObject("espacio", new Espacio(id_espacio));
		model.addObject("allSpaces", reserva_service.getAllSpaces(id_edif));
		model.addObject("view", "reservas_aula_paso2");
		model.addObject("uri", "/reservar-por-aulas/paso-2");
        return model;
    }
	

	
	@RequestMapping(value="/reservas_fecha", method=RequestMethod.GET)
    public ModelAndView ReservaPrFecha() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("user", user_service.getCurrentUser());
		model.addObject("view", "reservas_fecha");
		model.addObject("uri", "/reservar-por-fecha");
        return model;
    }
}
