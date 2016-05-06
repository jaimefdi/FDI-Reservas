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
	
	@Autowired
	public UserController(UserService userService, ReservaService reservaservice, GrupoReservaService grr){
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
		model.addObject("user", u);
		model.addObject("view", "admin/administrar");
		return model;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/administrar")
	public ModelAndView administrar(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("user", u);
		model.addObject("view", "administrar");
		return model;
	}
	
	@RequestMapping(value="/administrar/usuarios/{pageNumber}", method=RequestMethod.GET)
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
		model.addAttribute("user", u);
		model.addAttribute("view", "administrar_usuarios");
		
        return "index";
    }
	
	/*@RequestMapping(value="/administrar/usuarios")
	public ModelAndView administrarUsuarios(){
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("user", u);
		
		Iterable<User> lista = user_service.getUsuarios();
		List<User> aux = new ArrayList<User>();
		
		Iterator<User> it = lista.iterator();
		while (it.hasNext()){
			User user= it.next();
			if (!user.getId().equals(u.getId())){
				aux.add(user);
			}
		}
		
		model.addObject("userList", aux);
		model.addObject("view", "administrar_usuarios");
		model.addObject("url","/administrar/usuarios" );
		return model;
	}*/
	
	@RequestMapping(value="/administrar/edificios/{pageNumber}", method=RequestMethod.GET)
    public String misEdificiosPaginados(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Edificio> currentResults = reserva_service.getEdificiosPaginados(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("user", u);
		model.addAttribute("view", "administrar_edificios");
		
        return "index";
    }
	
//	@RequestMapping(value="/administrar/edificios")
//	public ModelAndView administrarEdificios(){
//		ModelAndView model = new ModelAndView("index");
//		User u = user_service.getCurrentUser();
//		model.addObject("user", u);
//		model.addObject("edificios", reserva_service.getAllBuildings());
//		model.addObject("facultades", reserva_service.getFacultades());
//		model.addObject("view", "administrar_edificios");
//		return model;
//	}
	
	@RequestMapping(value="/administrar/facultad/{pageNumber}", method=RequestMethod.GET)
    public String misFacultadesPaginadas(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Facultad> currentResults = reserva_service.getFacultadesPaginadas(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("user", u);
		model.addAttribute("view", "administrar_facultad");
		
        return "index";
    }
	
//	@RequestMapping(value="/administrar/facultad/1")
//	public ModelAndView administrarFacultades(){
//		ModelAndView model = new ModelAndView("index");
//		User u = user_service.getCurrentUser();
//		model.addObject("user", u);
//		model.addObject("facultades", reserva_service.getFacultades());
//		model.addObject("view", "administrar_facultad");
//		return model;
//	}
	
	@RequestMapping(value="/administrar/espacios/{pageNumber}", method=RequestMethod.GET)
    public String misEspaciosPaginados(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Espacio> currentResults = reserva_service.getEspaciosPaginados(pageRequest);
                
        model.addAttribute("currentResults", currentResults);
        
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("user", u);
		model.addAttribute("view", "administrar_espacios");
		
        return "index";
    }
	
//	@RequestMapping(value="/administrar/espacios")
//	public ModelAndView administrarEspacios(){
//		ModelAndView model = new ModelAndView("index");
//		User u = user_service.getCurrentUser();
//		model.addObject("user", u);
//		model.addObject("edificios", reserva_service.getAllBuildings());
//		model.addObject("espacios", reserva_service.getEspacios());
//		model.addObject("view", "administrar_espacios");
//		return model;
//	}

@RequestMapping(value="/nuevaFacultad",method=RequestMethod.GET)
	public ModelAndView nuevaFacultad(){
		return new ModelAndView("nuevaFacultad", "Facultad", new Facultad());
		//return model;
	}
	
	@RequestMapping(value="/nuevaFacultad", method=RequestMethod.POST)
	public String crearFacultad(Facultad f){
		reserva_service.addNewFacultad(f);
	   return "redirect:/administrar/facultad";
		//return "nuevoUsuario";
	}
	
	
	@RequestMapping(value="/nuevoEspacio",method=RequestMethod.GET)
	public ModelAndView nuevoEspacio(){
		ModelAndView model = new ModelAndView("nuevoEspacio", "Espacio", new Espacio());
		model.addObject("edificios", reserva_service.getEdificios());
		return model;
	}
	
	@RequestMapping(value="/nuevoEspacio", method=RequestMethod.POST)
	public String crearEspacio(EspacioTipoDTO f){
		reserva_service.addNewEspacio(f);
	   return "redirect:/administrar/espacios";
		//return "nuevoUsuario";
	}
	
	@RequestMapping(value="/nuevoEdificio",method=RequestMethod.GET)
	public ModelAndView nuevoEdificio(){
		ModelAndView model = new ModelAndView("nuevoEdificio", "Edificio", new Edificio());
		model.addObject("facultades", reserva_service.getFacultades());
		return model;
	}
	
	@RequestMapping(value="/nuevoEdificio", method=RequestMethod.POST)
	public String crearEdificio(EdificioDTO f){
		reserva_service.addNewEdificio(f);
	    return "redirect:/administrar/edificios";
		//return "nuevoUsuario";
	}

	@RequestMapping(value="/perfil", method=RequestMethod.GET)
	public ModelAndView verPerfil(){
		ModelAndView model = new ModelAndView("index");
		model.addObject("User", user_service.getCurrentUser());
		model.addObject("view", "perfil");
		
	   return model;
	}
}
