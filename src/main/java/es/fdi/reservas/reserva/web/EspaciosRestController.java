package es.fdi.reservas.reserva.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@RestController
public class EspaciosRestController {

	private ReservaService reserva_service;
	
	private UserService user_service;
	
	@Autowired
	public EspaciosRestController(UserService userService, ReservaService reservaservice){
		user_service = userService;
		reserva_service = reservaservice;
	}
	
	@RequestMapping(value = "/espacio/{idEspacio}", method = RequestMethod.GET)
	public EspacioDTO espacio(@PathVariable("idEspacio") long idEspacio) {
		Espacio e = reserva_service.getEspacio(idEspacio);
		
		return EspacioDTO.fromEspacioDTO(e);
	}
	
	@RequestMapping(value = "/espacio/{idEspacio}", method = RequestMethod.DELETE)
	public void eliminarEspacios(@PathVariable("idEspacio") long idEspacio) {
		reserva_service.editarEspacioDeleted(idEspacio);
	}
	
	@RequestMapping(value = "/admin/administrar/espacio/editar/{idEspacio}", method = RequestMethod.PUT)
	public String editarEspacios(@PathVariable("idEspacio") long idEspacio, @RequestBody EspacioDTO espacioActualizado) {
		reserva_service.editarEspacio(espacioActualizado);
		return "redirect:/admin/administrar/espacios";
	}
	
	
	@RequestMapping(value = "/administrar/espacio/{numPag}/restaurar/{idEspacio}", method = RequestMethod.GET)
	public String restaurarEspacio(@PathVariable("numPag") Long numPag, @PathVariable("idEspacio") Long idEspacio){
		reserva_service.restaurarEspacio(idEspacio);
		return "redirect:/administrar/espacio/{numPag}/restaurar";
	}
	
	@RequestMapping(value="/admin/nuevoEspacio", method=RequestMethod.POST)
	public String crearEspacio(EspacioTipoDTO f){
		//reserva_service.addNewEspacio(f);
	   return "redirect:/administrar/espacios";
		//return "nuevoUsuario";
	}
}
