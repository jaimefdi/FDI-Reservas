package es.fdi.reservas.reserva.business.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.fdi.reservas.reserva.business.control.EdificioRepository;
import es.fdi.reservas.reserva.business.control.EspacioRepository;
import es.fdi.reservas.reserva.business.control.ReservaRepository;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.Reserva;

@Service
public class ReservaService {
	
	private ReservaRepository reserva_repository;
	private EdificioRepository edificio_repository;
	private EspacioRepository espacio_repository;
	
	@Autowired
	public ReservaService(ReservaRepository rr, EdificioRepository er, EspacioRepository sr){
		reserva_repository = rr;
		edificio_repository = er;
		espacio_repository = sr;
	}

	public List<Reserva> getAllReservations(String username) {
		return reserva_repository.findByUsername(username);
	}

	public Reserva agregarReserva(Reserva r, String username) {
		Reserva nuevaReserva = new Reserva(r.getAsunto(),r.getFecha_ini(),r.getFecha_fin(),username);
		nuevaReserva = reserva_repository.save(nuevaReserva);
		
		return nuevaReserva;
	}

	public List<Reserva> getAllReservations() {
		return reserva_repository.findAll();
	}

	public Iterable<Edificio> getAllBuildings(){
		return edificio_repository.findAll();
	}

	// todas las reservas de un espacio
	public List<Reserva> getReservations(long id_espacio) {
		return reserva_repository.findByEspacio_Id(id_espacio);
	}
	// todos los espacios de un edificio 
	public Iterable<Espacio> getAllSpaces(long id_edif) {
		return espacio_repository.findByEdificio_Id(id_edif);
	}



	
}
