package es.fdi.reservas.users.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.*;
import es.fdi.reservas.reserva.web.EdificioDTO;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

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
	/*@RequestMapping(value="/user/{id_user}", method=RequestMethod.GET)
	public List<UserDTO> edificiosDeUnaFacultad(@PathVariable("id_user") String id_user){
		List<UserDTO> result = new ArrayList<>();
		List<User> users = new ArrayList<>();
		
		
		users = user_service.getUsers(id_user);
		
		
		for(User u : users) {
			result.add(UserDTO.fromUserDTO(u));
		}
		 
		return result;
			
	}*/
	
	@RequestMapping(value = "/user/{idUsuario}", method = RequestMethod.DELETE)
	public void eliminarUsuario(@PathVariable("idUsuario") long idUser) {
		user_service.eliminarUsuario(idUser);
	}
	
	@RequestMapping(value = "/user/{idUsuario}", method = RequestMethod.PUT)
	public void editarUsuario(@PathVariable("idUsuario") long idUsuario, @RequestBody User userActualizado) {
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
	public void editarEdificio(@PathVariable("idEdificio") long idEdificio, @RequestBody Edificio edificioActualizado) {
		reserva_service.editarEdificio(edificioActualizado);
	}
	
	/*
	 * Administracion Facultades
	 */
	@RequestMapping(value = "/facultad/{idFacultad}", method = RequestMethod.DELETE)
	public void eliminarFacultad(@PathVariable("idFacultad") long idFacultad) {
		reserva_service.eliminarFacultad(idFacultad);
	}
	
	@RequestMapping(value = "/facultad/{idFacultad}", method = RequestMethod.PUT)
	public void editarFacultad(@PathVariable("idFacultad") long idFacultad, @RequestBody Facultad facultadActualizado) {
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
	public void editarEspacios(@PathVariable("idEspacio") long idEspacio, @RequestBody Espacio espacioActualizado) {
		reserva_service.editarEspacio(espacioActualizado);
	}
}
