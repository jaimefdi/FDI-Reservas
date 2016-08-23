package es.fdi.reservas.reserva.web;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.EdificioService;
import es.fdi.reservas.reserva.business.boundary.EspacioService;
import es.fdi.reservas.reserva.business.boundary.GrupoReservaService;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;


@Controller
public class ReservaController {
	
	private ReservaService reserva_service;
	private GrupoReservaService grupo_service;
	private UserService user_service;
	private EdificioService edificio_service;
	private EspacioService espacio_service;
	
	@Autowired
	public ReservaController(ReservaService rs, GrupoReservaService grr, UserService us, EdificioService eds, EspacioService es){
		reserva_service = rs;
		grupo_service = grr;
		user_service = us;
		edificio_service = eds;
		espacio_service = es;
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
        model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("User", u);
		model.addAttribute("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addAttribute("view", "mis-reservas");
		
        return "index";
    }
	 
	
	
	@RequestMapping(value="/edificios", method=RequestMethod.GET)
    public String edificios(Model model) {
		
		User user = user_service.getCurrentUser();
		model.addAttribute("User", user);
		List<Edificio> edificios = edificio_service.getEdificiosFacultad(user.getFacultad().getId());
		if(edificios.size() > 1){
		   model.addAttribute("Edificios", edificios);
		   model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(user.getId(), EstadoReserva.PENDIENTE).size());
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
		model.addObject("Edificio", edificio_service.getEdificio(idEdificio));		
		model.addObject("TiposEspacio",espacio_service.tiposDeEspacios(idEdificio));
		model.addObject("Espacios", espacio_service.getEspaciosEdificio(idEdificio));
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("view", "espacios");
		
        return model;
    }
	
	
	@RequestMapping(value="/edificio/{idEdificio}/espacio/{idEspacio}", method=RequestMethod.GET) 
	public ModelAndView reservarPorEspacio(@PathVariable("idEdificio") long idEdificio,@PathVariable("idEspacio") long idEspacio) {
		ModelAndView model = new ModelAndView("index");
		User user = user_service.getCurrentUser();
		Espacio e = espacio_service.getEspacio(idEspacio);
		Reserva r = new Reserva();
		r.setEspacio(e);	
		model.addObject("User", user);
		model.addObject("Reserva", r);
		model.addObject("IdEspacio", idEspacio);
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(user.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(user.getId()));
		model.addObject("view", "reservas-calendario");
		
        return model;
    }
	
	
	@RequestMapping(value="/reservas-fecha", method=RequestMethod.GET)
    public ModelAndView reservarPorFecha() {
		ModelAndView model = new ModelAndView("index");
		User u = user_service.getCurrentUser();
		List<Edificio> edificios = edificio_service.getEdificiosFacultad(u.getFacultad().getId());
		model.addObject("User", u);
		model.addObject("Edificios", edificios);
		model.addObject("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addObject("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addObject("view", "reservas-fecha");
		
        return model;
    }

	//@PreAuthorize("principal.username == 'user'")
	//@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/editar/{idReserva}", method=RequestMethod.GET)
    public String editarReservaSimple(@PathVariable("idReserva") long idReserva, Model model) {
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		model.addAttribute("Reserva", reserva_service.getReserva(idReserva));
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addAttribute("view", "editarReserva");
		

        return "index";
    }
	
	@RequestMapping(value="/editar/{idReserva}/{recurrenteId}", method=RequestMethod.GET)
    public String editarReservaRecurrente(@PathVariable("idReserva") long idReserva, @PathVariable("recurrenteId") String recurrenteId, Model model) {
		User u = user_service.getCurrentUser();
		model.addAttribute("User", u);
		String[] w = recurrenteId.split("_");
		Reserva r = reserva_service.getReserva(idReserva);
		//cambiar el comienzo y fin con w[1]
		String[] s = w[1].split("-");
		DateTime currentStart = r.getComienzo();
		DateTime currentEnd = r.getFin();
		Long range = currentEnd.getMillis() - currentStart.getMillis();
		
		DateTime newStart = currentStart.withDayOfMonth(Integer.valueOf(s[0]))
						.withMonthOfYear(Integer.valueOf(s[1]))
						.withYear(Integer.valueOf(s[2]));
		
		r.setComienzo(newStart);		
		DateTime newEnd = newStart.plus(range);		
		r.setFin(newEnd);
		
		// el newStart y el newEnd pasarlos cen el modelo
		
		model.addAttribute("Reserva", r);
		model.addAttribute("reservasPendientes", reserva_service.reservasPendientesUsuario(u.getId(), EstadoReserva.PENDIENTE).size());
		model.addAttribute("GruposReservas", grupo_service.getGruposUsuario(u.getId()));
		model.addAttribute("view", "editarReserva");
		

        return "index";
    }
	
	
	
}
