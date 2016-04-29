package es.fdi.reservas.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.web.*;
import es.fdi.reservas.users.business.boundary.UserService;
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
		user_service.editarUserDeleted(idUser);
	}
	
	@RequestMapping(value = "/administrar/usuarios/{numPag}/restaurar/{idUsuario}", method = RequestMethod.GET)
	public String restaurarUsuario(@PathVariable("idUsuario") Long idUser, @PathVariable("numPag") Long numPag){
		user_service.restaurarUser(idUser);
		return "redirect:/administrar/usuarios/{numPag}";
	}
	
	@RequestMapping(value = "/administrar/usuarios/{numPag}/restaurar")
	public ModelAndView restaurarUsers(@PathVariable("numPag") Long numPag){
		ModelAndView model = new ModelAndView("index");
		model.addObject("usuarios", user_service.getEliminados());
		model.addObject("pagina", numPag);
		model.addObject("view", "papelera_usuarios");
		return model;
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
		reserva_service.editarEdificioDeleted(idEdificio);
	}
	
	@RequestMapping(value = "/edificio/{idEdificio}", method = RequestMethod.PUT)
	public void editarEdificio(@PathVariable("idEdificio") long idEdificio, @RequestBody EdificioDTO edificioActualizado) {
		reserva_service.editarEdificio(edificioActualizado);
	}
	
	@RequestMapping(value = "/administrar/edificio/restaurar")
	public ModelAndView restaurarEdificios(){
		ModelAndView model = new ModelAndView("index");
		model.addObject("edificios", reserva_service.getEdificiosEliminados());
		model.addObject("view", "papelera_edificios");
		return model;
	}
	
	@RequestMapping(value = "/administrar/edificio/restaurar/{idEdificio}", method = RequestMethod.GET)
	public String restaurarEdificio(@PathVariable("idEdificio") Long idEdificio){
		reserva_service.restaurarEdificio(idEdificio);
		return "redirect:/administrar/edificio/restaurar";
	}
	/*
	 * Administracion Facultades
	 */
	@RequestMapping(value = "/facultad/{idFacultad}", method = RequestMethod.DELETE)
	public void eliminarFacultad(@PathVariable("idFacultad") Long idFacultad) {
		//reserva_service.eliminarFacultad(facultad);
		reserva_service.editarFacultadDeleted(idFacultad);
	}
	
	@RequestMapping(value = "/facultad/{idFacultad}", method = RequestMethod.PUT)
	public void editarFacultad(@PathVariable("idFacultad") long idFacultad, @RequestBody FacultadDTO facultadActualizado) {
		reserva_service.editarFacultad(facultadActualizado);
	}
	
	@RequestMapping(value = "/administrar/facultad/restaurar")
	public ModelAndView restaurarFacultades(){
		ModelAndView model = new ModelAndView("index");
		model.addObject("facultades", reserva_service.getFacultadesEliminadas());
		model.addObject("view", "papelera_facultades");
		return model;
	}
	
	@RequestMapping(value = "/administrar/facultad/restaurar/{idFacultad}", method = RequestMethod.GET)
	public String restaurarFacultad(@PathVariable("idFacultad") Long idFacultad){
		reserva_service.restaurarFacultad(idFacultad);
		return "redirect:/administrar/facultad";
	}
	/*
	 * Administracion espacios
	 */
	@RequestMapping(value = "/espacio/{idEspacio}", method = RequestMethod.DELETE)
	public void eliminarEspacios(@PathVariable("idEspacio") long idEspacio) {
		reserva_service.editarEspacioDeleted(idEspacio);
	}
	
	@RequestMapping(value = "/espacio/{idEspacio}", method = RequestMethod.PUT)
	public String editarEspacios(@PathVariable("idEspacio") long idEspacio, @RequestBody EspacioTipoDTO espacioActualizado) {
		reserva_service.editarEspacio(espacioActualizado);
		return "redirect:/administrar/espacios";
	}
	
	@RequestMapping(value = "/administrar/espacio/restaurar")
	public ModelAndView restaurarEspacios(){
		ModelAndView model = new ModelAndView("index");
		model.addObject("espacios", reserva_service.getEspaciosEliminados());
		model.addObject("view", "papelera_espacios");
		return model;
	}
	
	@RequestMapping(value = "/administrar/espacio/restaurar/{idEspacio}", method = RequestMethod.GET)
	public String restaurarEspacio(@PathVariable("idEspacio") Long idEspacio){
		reserva_service.restaurarEspacio(idEspacio);
		return "redirect:/administrar/espacio/restaurar";
	}
}
