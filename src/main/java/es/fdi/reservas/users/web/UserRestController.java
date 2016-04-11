package es.fdi.reservas.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.*;
import es.fdi.reservas.reserva.web.*;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;
import es.fdi.reservas.users.business.entity.UserDTO;

@RestController
public class UserRestController {

	private ReservaService reserva_service;
	
	private UserService user_service;
	
	@Autowired
	public UserRestController(ReservaService rs, UserService us){
		reserva_service = rs;
		user_service = us;
	}
	
	/*
	 * Administracion usuarios
	 */
	@RequestMapping(value = "/user/{idUsuario}", method = RequestMethod.DELETE)
	public void eliminarUsuario(@PathVariable("idUsuario") long idUser) {
		user_service.eliminarUsuario(idUser);
	}
	
	@RequestMapping(value = "/user/{idUsuario}", method = RequestMethod.PUT)
	public void editarUsuario(@PathVariable("idUsuario") long idUsuario, @RequestBody UserDTO userActualizado) {
		user_service.editaUsuario(userActualizado);
	}	
	
	/*
	 * Administracion edificios
	 */
	@RequestMapping(value = "/edificio/{idEdificio}", method = RequestMethod.DELETE)
	public void eliminarEdificio(@PathVariable("idEdificio") long idEdificio) {
		reserva_service.eliminarEdificio(idEdificio);
	}
	
	@RequestMapping(value = "/edificio/{idEdificio}", method = RequestMethod.PUT)
	public void editarEdificio(@PathVariable("idEdificio") long idEdificio, @RequestBody EdificioDTO edificioActualizado) {
		reserva_service.editarEdificio(edificioActualizado);
	}
	
	/*
	 * Administracion Facultades
	 */
	@RequestMapping(value = "/facultad/{idFacultad}", method = RequestMethod.DELETE)
	public void eliminarFacultad(@PathVariable("idFacultad") long idFacultad) {
		//reserva_service.eliminarFacultad(idFacultad);
	}
	
	@RequestMapping(value = "/facultad/{idFacultad}", method = RequestMethod.PUT)
	public void editarFacultad(@PathVariable("idFacultad") long idFacultad, @RequestBody FacultadDTO facultadActualizado) {
		reserva_service.editarFacultad(facultadActualizado);
	}
	
	/*
	 * Administracion espacios
	 */
	@RequestMapping(value = "/espacio/{idEspacio}", method = RequestMethod.DELETE)
	public void eliminarEspacios(@PathVariable("idEspacio") long idEspacio) {
		reserva_service.eliminarEspacio(idEspacio);
	}
	
	@RequestMapping(value = "/espacio/{idEspacio}", method = RequestMethod.PUT)
	public String editarEspacios(@PathVariable("idEspacio") long idEspacio, @RequestBody EspacioTipoDTO espacioActualizado) {
		reserva_service.editarEspacio(espacioActualizado);
		return "redirect:/administrar/espacios";
	}
}
