package es.fdi.reservas.users.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import es.fdi.reservas.reserva.business.boundary.EspacioService;
import es.fdi.reservas.reserva.business.boundary.FacultadService;
import es.fdi.reservas.reserva.business.boundary.GrupoReservaService;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;


@Controller
public class UserController {

	private UserService user_service;	
	private ReservaService reserva_service;
	private GrupoReservaService grupo_service;
	private EspacioService espacio_service;
	
	@Autowired
	public UserController(UserService userService, GrupoReservaService grs, EspacioService es, ReservaService rs){
		user_service = userService;
		grupo_service = grs;
		espacio_service = es;
		reserva_service = rs;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
	   return "login";
	}

	@RequestMapping(value="/login-error", method=RequestMethod.GET)
    public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
  }
	
	@RequestMapping(value ="/administrar/usuarios")
    public String usuarios() {
        return "redirect:/administrar/usuarios/page/1";
    }
	
	@RequestMapping(value="/admin/nuevoUsuario", method=RequestMethod.GET)
	public String nuevoUsuario(Model model){
		//ModelAndView model = new ModelAndView("admin/nuevoUsuario", "User", new User());
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("us", new User());
		model.addAttribute("view", "admin/nuevoUsuario");
	   return "index";
	}
	
	@RequestMapping(value="/admin/nuevoUser", method=RequestMethod.GET)
	public ModelAndView nuevoUser(){
	   return new ModelAndView("admin/nuevoUsuario", "User", new User());
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
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/admin/administrar",method=RequestMethod.GET)
	public ModelAndView administrar(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);

		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("userList", user_service.getUsuarios());
		model.addObject("view", "administrar_usuarios");
		model.addObject("url","/administrar/usuarios" );

		model.addObject("view", "admin/administrar");

		return model;
	}
	
	@RequestMapping(value="/admin/administrar/usuarios/page/{pageNumber}", method=RequestMethod.GET)
    public String misUsuariosPaginados(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
	
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<User> currentResults = user_service.getUsuariosPaginados(pageRequest);
        
        model.addAttribute("currentResults", currentResults);
        String wsp = System.getProperty("user.dir");
        File f = new File(u.getImagen().getAttachmentUrl());
        wsp = f.getAbsolutePath();
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPages", currentResults.getTotalPages());
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_usuarios");
		
        return "index";
    }
	
	@RequestMapping(value="/admin/administrar/user/nombre/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misUsuariosPaginadosPorNombre(@PathVariable Integer pageNumber, Model model, @PathVariable Long nombre) {
		User u = user_service.getCurrentUser();

		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		ArrayList<User> currentResults = new ArrayList<User>();
        currentResults.add(user_service.getUsuariosPorNombre(nombre));
        
        model.addAttribute("currentResults", currentResults);
        
        int current =  1;
        int begin = Math.max(1, current - 5);
        int end = 1;//Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPages", 1);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_usuarios");
		
        return "index";
    }
	
	@RequestMapping(value="/admin/administrar/user/email/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misUsuariosPaginadosPorEmail(@PathVariable Integer pageNumber, Model model, @PathVariable Long nombre) {
		User u = user_service.getCurrentUser();

	
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		ArrayList<User> currentResults = new ArrayList<User>();
        currentResults.add(user_service.getUsuariosPorNombre(nombre));
        
        model.addAttribute("currentResults", currentResults);
        
        int current =  1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, 1); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
        model.addAttribute("totalPages", 1);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_usuarios");
		
        return "index";
    }
	
	@RequestMapping(value="/admin/administrar/user/facultad/{nombre}/page/{pageNumber}", method=RequestMethod.GET)
    public String misUsuariosPaginadosPorFacultad(@PathVariable Integer pageNumber, Model model, @PathVariable Long nombre) {
		User u = user_service.getCurrentUser();

	
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
		ArrayList<User> currentResults = new ArrayList<User>();
        currentResults.add(user_service.getUsuariosPorNombre(nombre));
        
        model.addAttribute("currentResults", currentResults);
        
        int current =  1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, 1); 

        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
        model.addAttribute("totalPages", 1);
		model.addAttribute("User", u);
		model.addAttribute("view", "admin/administrar_usuarios");
		
        return "index";
    }

	
	@RequestMapping(value="/admin/administrar/usuarios/editar/{idUser}", method=RequestMethod.GET)
	public String editarUsuario(@PathVariable("idUser") long idUser, Model model){
		User u = user_service.getCurrentUser();

		model.addAttribute("User", u);
		model.addAttribute("usuario", user_service.getUser(idUser));
		//System.out.println(user_service.getUser(idUser).getUsername());
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("view", "admin/editarUsuario");
		return "index";
	}
	
	@RequestMapping({"/admin/administrar/espacios"})
	public ModelAndView administrarEspacios(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("espacios", espacio_service.getEspacios());
		model.addObject("view", "admin/administrar_espacios");
		return model;
	}

	

	@RequestMapping(value="/perfil", method=RequestMethod.GET)
	public ModelAndView verPerfil(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("view", "perfil");
		
	   return model;
	}
	
	@RequestMapping(value="/perfil/editar", method=RequestMethod.GET)
	public ModelAndView editarPerfil(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("view", "editarPerfil");
		
	   return model;
	}
}
