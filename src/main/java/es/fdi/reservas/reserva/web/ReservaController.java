package es.fdi.reservas.reserva.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import es.fdi.reservas.reserva.business.entity.GrupoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;


@Controller
public class ReservaController {
	
	private ReservaService reserva_service;
	private GrupoReservaService grupo_service;
	private UserService user_service;
	
	@Autowired
	public ReservaController(ReservaService rs, GrupoReservaService grr, UserService us){
		reserva_service = rs;
		grupo_service = grr;
		user_service = us;
	}
	
	
	@RequestMapping({"/","","/mis-reservas"})
    public String misReservas() {
        return "redirect:/mis-reservas/page/1";
    }
	
	@RequestMapping(value="/mis-reservas/page/{pageNumber}", method=RequestMethod.GET)
    public String misReservasPaginadas(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 7, new Sort(new Sort.Order(Sort.Direction.ASC,"comienzo")));
        Page<Reserva> currentResults = reserva_service.getReservasUsuario(u.getId(),pageRequest);
        
        model.addAttribute("currentResults", currentResults);
    
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("User", u);
		model.addAttribute("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addAttribute("view", "mis-reservas");
		
        return "index";
    }
	
	
	@RequestMapping(value="/edificios", method=RequestMethod.GET)
    public String edificios(Model model) {
		
		User user = user_service.getCurrentUser();
		model.addAttribute("User", user);
		List<Edificio> edificios = reserva_service.getEdificiosFacultad(user.getFacultad().getId());
		if(edificios.size() > 1){
		   model.addAttribute("Edificios", edificios);
		   model.addAttribute("GruposReservas", grupo_service.getGruposUsuario(user.getId()));
		   model.addAttribute("view", "edificios");
		   
		   return "index";
		}
		else{
		   long idEdificio = edificios.get(0).getId();
		   
		   return "redirect:/edificio/" + idEdificio + "/espacios";
		}
        
    }
	
	
	@RequestMapping(value="/edificio/{idEdificio}/espacios", method=RequestMethod.GET)
    public ModelAndView espacios(@PathVariable("idEdificio") long idEdificio) {
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("Edificio", reserva_service.getEdificio(idEdificio));		
		model.addObject("TiposEspacio",reserva_service.tiposDeEspacios(idEdificio));
		model.addObject("Espacios", reserva_service.getEspaciosEdificio(idEdificio));
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("view", "espacios");
		
        return model;
    }
	
	
	@RequestMapping(value="/edificio/{idEdificio}/espacio/{idEspacio}", method=RequestMethod.GET) 
	public ModelAndView reservasEspacio(@PathVariable("idEdificio") long idEdificio,@PathVariable("idEspacio") long idEspacio) {
		ModelAndView model = new ModelAndView("index");
		User user = user_service.getCurrentUser();
		Espacio e = reserva_service.getEspacio(idEspacio);
		Reserva r = new Reserva();
		r.setEspacio(e);	
		model.addObject("User", user);
		model.addObject("Reserva", r);
		model.addObject("IdEspacio", idEspacio);
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(user.getId()));
		model.addObject("view", "reservas-calendario");
		
        return model;
    }
	
	
	@RequestMapping(value="/reservas-fecha", method=RequestMethod.GET)
    public ModelAndView reservasFecha() {
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		model.addObject("User", u);
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("view", "reservas-fecha");
		
        return model;
    }
	
	
	
	
	//@PreAuthorize("principal.username == 'user'")
	//@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/editar/{idReserva}", method=RequestMethod.GET)
    public String editarReserva(@PathVariable("idReserva") long idReserva, Model model) {
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);	
		model.addAttribute("Reserva", reserva_service.getReserva(idReserva));
		model.addAttribute("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addAttribute("view", "editarReserva");
		

        return "index";
    }
	
	
	
}
