package es.fdi.reservas.users.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.GrupoReservaService;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.web.EdificioDTO;
import es.fdi.reservas.reserva.web.EspacioTipoDTO;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;


@Controller
public class UserController {

	private UserService user_service;	
	private ReservaService reserva_service;
	private GrupoReservaService grupo_service;
	
	@Autowired
	public UserController(UserService userService, ReservaService reservaservice, GrupoReservaService grs){
		user_service = userService;
		reserva_service = reservaservice;
		grupo_service = grs;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
	   return "login";
	}

	@RequestMapping(value ="/administrar/usuarios")
    public String usuarios() {
        return "redirect:/administrar/usuarios/1";
    }
	
	@RequestMapping(value="/admin/nuevoUsuario", method=RequestMethod.GET)
	public ModelAndView nuevoUsuario(){
		ModelAndView model = new ModelAndView("admin/nuevoUsuario", "User", new User());
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("view", "index");
	   return model;
	}
	
	@RequestMapping(value="/nuevoUsuario", method=RequestMethod.GET)
	public ModelAndView nuevoUser(){
	   return new ModelAndView("nuevoUsuario", "User", new User());
	}
	
	@RequestMapping(value="/admin/nuevoUsuario", method=RequestMethod.POST)
	public String crearUsuario(User u){
		user_service.addNewUser(u);
	   return "redirect:/admin/administrar";
		//return "nuevoUsuario";
	}
	
	
	@RequestMapping(value = "usuario/tag/{tagName}", method = RequestMethod.GET)
	public List<UserDTO> usuariosFiltro(@PathVariable("tagName") String tagName) {
		
		List<UserDTO> result = new ArrayList<>();
		List<User> usuarios = new ArrayList<>();
		
		
		usuarios = user_service.getUsuariosPorTagName(tagName);
		
		
		for(User u : usuarios) {
			result.add(UserDTO.fromUserDTO(u));
		}
		 
		return result;

	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping({"/admin/administrar"})
	public ModelAndView administrar(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);

		model.addObject("userList", user_service.getUsuarios());
		model.addObject("view", "administrar_usuarios");
		model.addObject("url","/administrar/usuarios" );

		model.addObject("view", "admin/administrar");

		return model;
	}
	
	@RequestMapping(value="/admin/administrar/usuarios/{pageNumber}", method=RequestMethod.GET)
    public String misUsuariosPaginados(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
	
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<User> currentResults = user_service.getUsuariosPaginados(pageRequest);
        
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_usuarios");
		
        return "index";
    }

	
	@RequestMapping(value="/admin/administrar/usuarios/editar/{idUser}", method=RequestMethod.GET)
	public String editarUsuario(@PathVariable("idUser") long idUser, Model model){
		User u = user_service.getCurrentUser();

		model.addAttribute("User", u);
		//model.addAttribute("usuario", user_service.getUser(idUser));
		//System.out.println(user_service.getUser(idUser).getUsername());
		model.addAttribute("view", "admin/editarUsuario");
		return "index";
	}
	
	@RequestMapping({"/administrar/administrar_espacios"})
	public ModelAndView administrarEspacios(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("espacios", reserva_service.getEspacios());
		model.addObject("view", "administrar_espacios");
		return model;
	}

	

	@RequestMapping(value="/perfil", method=RequestMethod.GET)
	public ModelAndView verPerfil(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("view", "perfil");
		
	   return model;
	}
}
