package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Controller
public class SecreController {

	private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
	
	private ReservaService reserva_service;
	
	private UserService user_service;
	
	@Autowired
	public SecreController(ReservaService rs, UserService us){
		reserva_service = rs;
		user_service = us;
	}
	
	@RequestMapping(value="/secre/gestion-reservas/page/{pageNumber}", method=RequestMethod.GET)
    public String gestiona_reservas(@PathVariable Integer pageNumber, Model model) {
		User u = user_service.getCurrentUser();
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, 5);
        Page<Reserva> currentResults = reserva_service.getReservasPaginadas(pageRequest);
        
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
		model.addAttribute("view", "secre/gestion-reservas");
		
        return "secre/index";
    }
	
	@RequestMapping(value="/secre/gestion-reservas/page/{pageNumber}/user/{user}", method=RequestMethod.GET)
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
		model.addAttribute("view", "secre/gestion-reservas");
		
        return "secre/index";
    }
	
	@RequestMapping(value="/secre/gestion-reservas/page/{pageNumber}/sala/{sala}", method=RequestMethod.GET)
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
		model.addAttribute("view", "secre/gestion-reservas");
		
        return "secre/index";
    }
}
