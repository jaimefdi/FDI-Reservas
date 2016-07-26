package es.fdi.reservas.reserva.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.GrupoReservaService;
import es.fdi.reservas.reserva.business.entity.GrupoReserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Controller
public class GrupoReservaController {

	private GrupoReservaService grupo_service;	
	private UserService user_service;
	
	@Autowired
	public GrupoReservaController(GrupoReservaService grs, UserService us){
		this.grupo_service = grs;
		this.user_service = us;
	}
	
	@RequestMapping(value="/grupo/{idGrupo}", method=RequestMethod.GET)
    public ModelAndView verGrupo(@PathVariable("idGrupo") long idGrupo) {
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("GrupoReservas", grupo_service.getGrupoReserva(idGrupo));
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("view", "grupo-reservas");
		
        return model;
    }
	
	@RequestMapping(value="/grupo/editar/{idGrupo}", method=RequestMethod.GET)
    public ModelAndView editarGrupo(@PathVariable("idGrupo") long idGrupo) {
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("GrupoReservas", grupo_service.getGrupoReserva(idGrupo));
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("view", "editarGrupo");
		
        return model;
    }
	
	
	@RequestMapping(value="/grupo/nuevo", method=RequestMethod.GET)
    public ModelAndView crearGrupo() {
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);	
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("view", "nuevoGrupo");
		
        return model;
    }
	
	@RequestMapping(value="/nuevoGrupo", method=RequestMethod.POST)
    public String nuevoGrupo(GrupoReserva g, Model model) {	
		User user = user_service.getCurrentUser();
		model.addAttribute("User", user);		
		model.addAttribute("view", "nuevoGrupo");
		GrupoReserva nuevoGrupo = grupo_service.addNuevoGrupo(g, user);
		
		if(nuevoGrupo != null){			
			model.addAttribute("exito", nuevoGrupo.getId());
		}
		else{
			model.addAttribute("error", "");
		}
		
		model.addAttribute("GruposReservas", grupo_service.getGruposUsuario(user.getId()));
	
        return "index";
    }
	
}
