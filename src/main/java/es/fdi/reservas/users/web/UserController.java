package es.fdi.reservas.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;


@Controller
public class UserController {

	private UserService user_service;
	
	private ReservaService reserva_service;
	
	
	@Autowired
	public UserController(UserService userService, ReservaService reservaservice){
		user_service = userService;
		reserva_service = reservaservice;
	}
	

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
	   return "login";
	}
	
	@RequestMapping(value="/nuevoUsuario", method=RequestMethod.GET)
	public ModelAndView nuevoUsuario(){
	   return new ModelAndView("nuevoUsuario", "User", new User());
	}
	
	@RequestMapping(value="/nuevoUsuario", method=RequestMethod.POST)
	public String crearUsuario(User u){
		user_service.addNewUser(u);
	   return "redirect:/login";
		//return "nuevoUsuario";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping({"/administrar"})
	public ModelAndView administrar(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("user", u);
		model.addObject("view", "administrar");
		return model;
	}
	
	@RequestMapping({"/administrar/administrar_usuarios"})
	public ModelAndView administrarUsuarios(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("user", u);
		model.addObject("userList", user_service.getUsuarios());
		model.addObject("view", "administrar_usuarios");
		model.addObject("url","/administrar/usuarios" );
		return model;
	}
	
	@RequestMapping({"/administrar/administrar_edificios"})
	public ModelAndView administrarEdificios(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("user", u);
		model.addObject("edificios", reserva_service.getEdificios());
		model.addObject("view", "administrar_edificios");
		return model;
	}
	
	@RequestMapping({"/administrar/administrar_facultad"})
	public ModelAndView administrarFacultades(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("user", u);
		model.addObject("facultades", reserva_service.getFacultades());
		model.addObject("view", "administrar_facultad");
		return model;
	}
	
	@RequestMapping({"/administrar/administrar_espacios"})
	public ModelAndView administrarEspacios(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("user", u);
		model.addObject("espacios", reserva_service.getEspacios());
		model.addObject("view", "administrar_espacios");
		return model;
	}

	@RequestMapping(value="/perfil", method=RequestMethod.GET)
	public ModelAndView verPerfil(){
		ModelAndView model = new ModelAndView("index");
		model.addObject("User", user_service.getCurrentUser());
		model.addObject("view", "perfil");
		
	   return model;
	}
}
