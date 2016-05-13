package es.fdi.reservas.reserva.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@RestController
public class EdificioRestController {

	private ReservaService reserva_service;
	
	private UserService user_service;
	
	@Autowired
	public EdificioRestController(UserService userService, ReservaService reservaservice){
		user_service = userService;
		reserva_service = reservaservice;
	}
	
	@RequestMapping(value = "/edificio/{idEdificio}", method = RequestMethod.DELETE)
	public void eliminarEdificio(@PathVariable("idEdificio") long idEdificio) {
		reserva_service.editarEdificioDeleted(idEdificio);
	}
	
	@RequestMapping(value = "/admin/administrar/edificios/editar/{idEdificio}", method = RequestMethod.PUT)
	public void editarEdificio(@PathVariable("idEdificio") long idEdificio, @RequestBody EdificioDTO edificioActualizado) {
		reserva_service.editarEdificio(edificioActualizado);
	}
	
	@RequestMapping(value = "/admin/administrar/edificio/{numPag}/restaurar/{idEdificio}", method = RequestMethod.GET)
	public String restaurarEdificio(@PathVariable("numPag") Long numPag, @PathVariable("idEdificio") Long idEdificio){
		reserva_service.restaurarEdificio(idEdificio);
		return "redirect:/administrar/edificio/{numPag}/restaurar";
	}
	
	@RequestMapping(value="/admin/nuevoEdificio", method=RequestMethod.POST)
	public String crearEdificio(Edificio f){
		reserva_service.addNewEdificio(f);
	    return "redirect:/admin/administrar/edificios/1";
	}
}
