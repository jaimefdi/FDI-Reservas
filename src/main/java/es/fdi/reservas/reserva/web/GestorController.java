package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.fdi.reservas.reserva.business.boundary.GrupoReservaService;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Controller
public class GestorController {

	private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
	
	private ReservaService reserva_service;
	
	private GrupoReservaService grupo_service;
	
	private UserService user_service;
	
	@Autowired
	public GestorController(ReservaService rs, GrupoReservaService grr, UserService us){
		reserva_service = rs;
		grupo_service = grr;
		user_service = us;
	}
	
	@RequestMapping({"/","","/gestor/mis-reservas"})
    public String misReservas() {
        return "redirect:/gestor/mis-reservas/page/1";
    }
	
	@RequestMapping(value="/gestor/mis-reservas/page/{pageNumber}", method=RequestMethod.GET)
    public String misReservasPaginadas(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 7, new Sort(new Sort.Order(Sort.Direction.ASC,"comienzo")));
        Page<Reserva> currentResults = reserva_service.getReservasUsuario(u.getUsername(),pageRequest);
        
        model.addAttribute("currentResults", currentResults);
    
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("user", u);
		model.addAttribute("view", "mis-reservas");
		model.addAttribute("User", u);
		model.addAttribute("Reservas", reserva_service.getReservasUsuario(u.getUsername()));
		model.addAttribute("GruposReservas", grupo_service.gruposReservas());
		model.addAttribute("view", "mis-reservas");
		
        return "gestor/index";
    }
	
	@RequestMapping(value="/gestor/gestion-reservas/page/{pageNumber}", method=RequestMethod.GET)
    public String gestiona_reservas(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Reserva> currentResults = reserva_service.getTodasReservasPaginadas(pageRequest);
        
        model.addAttribute("currentResults", currentResults);
    
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("user", u);
		List<EstadoReserva> lista=new ArrayList<EstadoReserva>();
		lista.add(EstadoReserva.CONFIRMADA);
		lista.add(EstadoReserva.PENDIENTE);
		lista.add(EstadoReserva.DENEGADA);
		model.addAttribute("estadoReserva", lista);
		model.addAttribute("view", "gestor/gestion-reservas");
		
        return "gestor/index";
    }
	
	@RequestMapping(value="/gestor/gestion-reservas/page/{pageNumber}/user/{user}", method=RequestMethod.GET)
    public String gestiona_reservas_usuario(@PathVariable String user, @PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Reserva> currentResults = reserva_service.getReservasPaginadasUser(pageRequest, user);
        
        model.addAttribute("currentResults", currentResults);
    
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("user", u);
		model.addAttribute("view", "gestor/gestion-reservas");
		
        return "gestor/index";
    }
	
	@RequestMapping(value="/gestor/gestion-reservas/page/{pageNumber}/sala/{sala}", method=RequestMethod.GET)
    public String gestiona_reservas_usuario(@PathVariable Long sala, @PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Reserva> currentResults = reserva_service.getReservasPaginadas(pageRequest, sala);
        
        model.addAttribute("currentResults", currentResults);
    
        int current = currentResults.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, currentResults.getTotalPages()); 

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current); 
		model.addAttribute("user", u);
		model.addAttribute("view", "gestor/gestion-reservas");
		
        return "gestor/index";
    }
}
