package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.boundary.UserService;

@RestController
public class ReservasRestController {
	
	private ReservaService reserva_service;
	
	private UserService user_service;
	
	@Autowired
	public ReservasRestController(ReservaService rs, UserService us){
		reserva_service = rs;
		user_service = us;
	}
	
	@RequestMapping(value="/eventos", method=RequestMethod.GET)
	public List<ReservaFullCalendarDTO> allReserv(){
		List<Reserva> allReservas = reserva_service.getAllReservations();
		//List<Reserva> allReservas = reserva_service.getReservations(1);//espacio 1
		List<ReservaFullCalendarDTO> result = new ArrayList<>();
		for(Reserva r : allReservas) {
			result.add(ReservaFullCalendarDTO.fromReserva(r));
		}
		 
		return result;
	}

	
	
}
