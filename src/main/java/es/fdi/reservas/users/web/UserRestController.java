package es.fdi.reservas.users.web;

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
	public String eliminarUsuario(@PathVariable("idUsuario") long idUser) {
		user_service.editarUserDeleted(idUser);
		return "redirect:/administrar/usuarios/1";
	}
	
	@RequestMapping(value = "/administrar/usuarios/{numPag}/restaurar/{idUsuario}", method = RequestMethod.GET)
	public String restaurarUsuario(@PathVariable("idUsuario") Long idUser, @PathVariable("numPag") Long numPag){
		user_service.restaurarUser(idUser);
		return "redirect:/reservas/administrar/usuarios/{numPag}";
	}
	
	@RequestMapping(value = "/admin/administrar/usuarios/{numPag}/restaurar")
	public ModelAndView restaurarUsers(@PathVariable("numPag") Long numPag){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("usuarios", user_service.getEliminados());
		model.addObject("User", u);
		model.addObject("pagina", numPag);
		model.addObject("view", "/admin/papelera_usuarios");
		return model;
	}
	
	@RequestMapping(value="/admin/administrar/usuarios/editar/{idUser}/{user}/{admin}/{secre}", method=RequestMethod.PUT)
	public void editarUsuario(@PathVariable("idUser") long idUser, @PathVariable("user") String user,
			@PathVariable("admin") String admin, @PathVariable("secre") String secre, @RequestBody UserDTO userActualizado) {
		user_service.editaUsuario(userActualizado, user, admin, secre);
	}	

	/*
	 * Administracion edificios
	 */
	@RequestMapping(value = "/edificio/{idEdificio}", method = RequestMethod.DELETE)
	public void eliminarEdificio(@PathVariable("idEdificio") long idEdificio) {
		reserva_service.editarEdificioDeleted(idEdificio);
	}
	
	@RequestMapping(value = "/admin/administrar/edificios/editar/{idEdificio}", method = RequestMethod.PUT)
	public void editarEdificio(@PathVariable("idEdificio") long idEdificio, @RequestBody EdificioDTO edificioActualizado) {
		reserva_service.editarEdificio(edificioActualizado);
	}
	
	@RequestMapping(value = "/admin/administrar/edificios/{numPag}/restaurar")
	public ModelAndView restaurarEdificios(@PathVariable("numPag") Long numPag){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("pagina", numPag);
		model.addObject("edificios", reserva_service.getEdificiosEliminados());
		model.addObject("view", "admin/papelera_edificios");
		return model;
	}
	
	@RequestMapping(value = "/admin/administrar/edificio/{numPag}/restaurar/{idEdificio}", method = RequestMethod.GET)
	public String restaurarEdificio(@PathVariable("numPag") Long numPag, @PathVariable("idEdificio") Long idEdificio){
		reserva_service.restaurarEdificio(idEdificio);
		return "redirect:/administrar/edificio/{numPag}/restaurar";
	}
	/*
	 * Administracion Facultades
	 */
	@RequestMapping(value = "/facultad/{idFacultad}", method = RequestMethod.DELETE)
	public void eliminarFacultad(@PathVariable("idFacultad") Long idFacultad) {
		//reserva_service.eliminarFacultad(facultad);
		reserva_service.editarFacultadDeleted(idFacultad);
	}
	
	@RequestMapping(value = "/admin/administrar/facultad/editar/{idFacultad}", method = RequestMethod.PUT)
	public void editarFacultad(@PathVariable("idFacultad") long idFacultad, @RequestBody FacultadDTO facultadActualizado) {
		reserva_service.editarFacultad(facultadActualizado);
	}
	
	
	@RequestMapping(value = "/admin/administrar/facultad/{numPag}/restaurar")
	public ModelAndView restaurarFacultades(@PathVariable("numPag") Long numPag){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("facultades", reserva_service.getFacultadesEliminadas());
		
		model.addObject("pagina", numPag);
		model.addObject("view", "admin/papelera_facultades");
		return model;
	}
	
	@RequestMapping(value = "/administrar/facultad/{numPag}/restaurar/{idFacultad}", method = RequestMethod.GET)
	public String restaurarFacultad(@PathVariable("idFacultad") Long idFacultad, @PathVariable("numPag") Long numPag){
		reserva_service.restaurarFacultad(idFacultad);
		return "redirect:/administrar/facultad/{numPag}";
	}
	/*
	 * Administracion espacios
	 */
	@RequestMapping(value = "/espacio/{idEspacio}", method = RequestMethod.DELETE)
	public void eliminarEspacios(@PathVariable("idEspacio") long idEspacio) {
		reserva_service.editarEspacioDeleted(idEspacio);
	}
	
	@RequestMapping(value = "/admin/administrar/espacio/editar/{idEspacio}", method = RequestMethod.PUT)
	public String editarEspacios(@PathVariable("idEspacio") long idEspacio, @RequestBody EspacioDTO espacioActualizado) {
		reserva_service.editarEspacio(espacioActualizado);
		return "redirect:/admin/administrar/espacios";
	}
	
	@RequestMapping(value = "/admin/administrar/espacio/{numPag}/restaurar")
	public ModelAndView restaurarEspacios(@PathVariable("numPag") String numPag){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("pagina", numPag);
		model.addObject("espacios", reserva_service.getEspaciosEliminados());
		model.addObject("view", "admin/papelera_espacios");
		return model;
	}
	
	@RequestMapping(value = "/administrar/espacio/{numPag}/restaurar/{idEspacio}", method = RequestMethod.GET)
	public String restaurarEspacio(@PathVariable("numPag") Long numPag, @PathVariable("idEspacio") Long idEspacio){
		reserva_service.restaurarEspacio(idEspacio);
		return "redirect:/administrar/espacio/{numPag}/restaurar";
	}
	
	
	@RequestMapping(value = "/usuarios/tag/{tagName}", method = RequestMethod.GET)
	public List<UserDTO> usuariosFiltro(@PathVariable("tagName") String tagName) {
		
		List<UserDTO> result = new ArrayList<>();
		List<User> usuarios = new ArrayList<>();

		usuarios = user_service.getUsuariosPorTagName(tagName);
				
		for(User u : usuarios) {
			result.add(UserDTO.fromUserDTO(u));
		}
		 
		return result;
	}
	

	@RequestMapping(value = "/usuarios/facultades/tag/{tagName}", method = RequestMethod.GET)
	public List<FacultadDTO> FacultadesFiltro(@PathVariable("tagName") String tagName) {
		
		List<FacultadDTO> result = new ArrayList<>();
		List<Facultad> facultades = new ArrayList<>();
		facultades = reserva_service.getFacultadesPorTagName(tagName);
				
		for(Facultad f : facultades) {
			result.add(FacultadDTO.fromFacultadDTO(f));
		}
		 
		return result;
	}
	
	
	
	
}
