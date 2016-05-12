package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@RestController
public class FacultadRestController {

private ReservaService reserva_service;
	
	private UserService user_service;
	
	@Autowired
	public FacultadRestController(UserService userService, ReservaService reservaservice){
		user_service = userService;
		reserva_service = reservaservice;
	}
	

	@RequestMapping(value = "/facultad/{idFacultad}", method = RequestMethod.DELETE)
	public String eliminarFacultad(@PathVariable("idFacultad") Long idFacultad) {
		//reserva_service.eliminarFacultad(facultad);
		reserva_service.editarFacultadDeleted(idFacultad);
		return "redirect:/admin/administrar/facultad/1";
	}
	
	@RequestMapping(value = "/admin/administrar/facultad/editar/{idFacultad}", method = RequestMethod.PUT)
	public void editarFacultad(@PathVariable("idFacultad") long idFacultad, @RequestBody FacultadDTO facultadActualizado) {
		reserva_service.editarFacultad(facultadActualizado);
	}
	

	@RequestMapping(value = "/admin/administrar/facultad/{numPag}/restaurar/{idFacultad}", method = RequestMethod.DELETE)
	public String restaurarFacultad(@PathVariable("idFacultad") Long idFacultad, @PathVariable("numPag") Long numPag){
		reserva_service.restaurarFacultad(idFacultad);
		return "redirect:admin/administrar/facultad/{numPag}";
	}
	

	@RequestMapping(value="/admin/nuevaFacultad", method=RequestMethod.POST)
	public String crearFacultad(Facultad f){
		reserva_service.addNewFacultad(f);
	   return "redirect:/administrar/facultad";
		//return "nuevoUsuario";
	}
}
