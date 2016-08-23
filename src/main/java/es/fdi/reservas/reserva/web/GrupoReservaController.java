package es.fdi.reservas.reserva.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import es.fdi.reservas.reserva.business.boundary.GrupoReservaService;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.GrupoReserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Controller
public class GrupoReservaController {

	private GrupoReservaService grupo_service;
	private ReservaService reserva_service;
	private UserService user_service;
	
	@Autowired
	public GrupoReservaController(GrupoReservaService grs, ReservaService rs, UserService us){
		this.grupo_service = grs;
		this.reserva_service = rs;
		this.user_service = us;
	}
	
	
	@RequestMapping(value="/grupo/{idGrupo}", method=RequestMethod.GET)
    public ModelAndView verGrupo(@PathVariable("idGrupo") long idGrupo) {
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
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
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
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
        model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("GrupoReserva", new GrupoReserva());
		model.addObject("view", "nuevoGrupo");
		
        return model;
    }
	
	@RequestMapping(value="/nuevoGrupo", method=RequestMethod.POST)
    public ModelAndView nuevoGrupo(@ModelAttribute("GrupoReserva") @Valid GrupoReserva grupo, BindingResult bindingResult) {	
		ModelAndView model = new ModelAndView("index");
		User user = user_service.getCurrentUser();
		model.addObject("User", user);
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(user.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("view", "nuevoGrupo");
		
		if (bindingResult.hasErrors()) {
            return model;
        }
		
		GrupoReserva nuevoGrupo = grupo_service.addNuevoGrupo(grupo, user);
		
		if(nuevoGrupo != null){
			
			model.addObject("exito", nuevoGrupo.getId());
		}
		else{
			
			model.addObject("error", grupo.getNombreCorto());
		}
		
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(user.getId()));
	
        return model;
    }
	
}
