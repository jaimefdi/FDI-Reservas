package es.fdi.reservas.reserva.business.boundary;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import es.fdi.reservas.reserva.business.control.EdificioRepository;
import es.fdi.reservas.reserva.business.control.EspacioRepository;
import es.fdi.reservas.reserva.business.control.FacultadRepository;
import es.fdi.reservas.reserva.business.control.ReservaRepository;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.reserva.business.entity.TipoEspacio;
import es.fdi.reservas.reserva.web.ReservaDTO;
import org.springframework.data.domain.Page;

@Service
public class ReservaService {
	
	private ReservaRepository reserva_repository;
	private FacultadRepository facultad_repository;
	private EdificioRepository edificio_repository;
	private EspacioRepository espacio_repository;
	
	@Autowired
	public ReservaService(ReservaRepository rr, FacultadRepository fr, EdificioRepository er, 
							EspacioRepository sr){
		reserva_repository = rr;
		facultad_repository = fr;
		edificio_repository = er;
		espacio_repository = sr;
	}

	private List<Reserva> getAllReservasConflictivas(Long idEspacio, DateTime start, DateTime end){
		List<Reserva> resRecurrentes = new ArrayList<Reserva>();
		List<Reserva> resConflictivas = new ArrayList<Reserva>();
		List<Reserva> resAux = new ArrayList<Reserva>();
		
		resConflictivas = reserva_repository.reservasConflictivas(idEspacio, start, end); 

		resRecurrentes = reserva_repository.reservasRecurrentes(idEspacio, start, end);
		
		for(Reserva r: resRecurrentes){
			resAux.addAll(r.getInstanciasEvento());
		}
		resConflictivas.addAll(resAux);
		
		return resConflictivas;
	}
	
	
	public List<Reserva> getReservasUsuario(String username) {
		return reserva_repository.findByUsername(username);
	}

	public Reserva agregarReserva(Reserva reserva, String username) {		
		List<Reserva> reservas = new ArrayList<Reserva>();		
		Long idEspacio = reserva.getEspacio().getId();
		DateTime start, end;
		// si la reserva es recurrente
		if(!reserva.getReglasRecurrencia().isEmpty()){
			//calcula el startRecurrencia y el endRecurrencia
			reserva.rangoRecurrencias();
			start = reserva.getStartRecurrencia();
			end = reserva.getEndRecurrencia();	   
		} 
		// si la reserva es simple
		else{
			start = reserva.getComienzo();
			end = reserva.getFin();
		}
		
		reservas = getAllReservasConflictivas(idEspacio, start, end);
		
		for(Reserva r: reservas ){
			if ( r.solapa(reserva) ) {
				throw new ReservaSolapadaException(String.format("La reserva %s, solapa con la reserva %s", 
						  reserva.getComienzo().toString("dd/MM/yyyy HH:mm") + "-" + 
				          reserva.getFin().toString("HH:mm"), 
				          r.getComienzo().toString("dd/MM/yyyy HH:mm") + "-" +
				          r.getFin().toString("HH:mm")));
			}
		}
		
		Reserva nuevaReserva = new Reserva(reserva.getAsunto(),reserva.getComienzo(),reserva.getFin(),
										   username, reserva.getEspacio(),reserva.getStartRecurrencia(),
										   reserva.getEndRecurrencia(),reserva.getReservaColor(),
										   reserva.getRecurrenteId());
		
		nuevaReserva.setReglasRecurrencia(reserva.getReglasRecurrencia());
		if(reserva.getGrupoReserva() != null){
			nuevaReserva.setGrupoReserva(reserva.getGrupoReserva());
		}
		nuevaReserva = reserva_repository.save(nuevaReserva);
		
		return nuevaReserva;
	}

	public List<Reserva> getReservas() {
		return reserva_repository.findAll();
	}

	public Iterable<Edificio> getEdificios(){
		return edificio_repository.findAll();
	}

	// todas las reservas de un espacio
	public List<Reserva> getReservasEspacio(long idEspacio) {
		return reserva_repository.findByEspacioId(idEspacio);
	}
	// todos los espacios de un edificio 
	public List<Espacio> getEspaciosEdificio(long idEdificio) {
		return espacio_repository.findByEdificioId(idEdificio);
	}

	public Espacio getEspacio(long id_espacio) {
		return espacio_repository.findOne(id_espacio);
	}

	public List<Espacio> getTiposEspacio(long idEdificio, TipoEspacio idTipoEspacio) {
		return espacio_repository.findByEdificioIdAndTipoEspacio(idEdificio, idTipoEspacio);
	}

	public Reserva getReserva(long idReserva) {
		return reserva_repository.findOne(idReserva);
	}

	public Iterable<Facultad> getFacultades() {
		return facultad_repository.findAll();
	}
	
	public Iterable<Espacio> getEspacios() {
		return espacio_repository.findAll();
	}

	public List<Edificio> getEdificiosFacultad(long idFacultad) {
		return edificio_repository.findByFacultadId(idFacultad);
	}

	public Reserva editarReservaSimple(ReservaDTO reservaActualizada) {
		Reserva reserva = new Reserva();
		reserva.setComienzo(reservaActualizada.getStart());
		reserva.setFin(reservaActualizada.getEnd());
		
		Long idEspacio = reservaActualizada.getIdEspacio();
		DateTime start = reservaActualizada.getStart();
		DateTime end = reservaActualizada.getEnd();
		
		List<Reserva> reservas = getAllReservasConflictivas(idEspacio, start, end);
		for(Reserva r: reservas ){
			if ( r.solapa(reserva) && ! reservaActualizada.getId().equals(r.getId())) {
				throw new ReservaSolapadaException(	String.format("La reserva %s, solapa con la reserva %s", 
								  					reserva.getComienzo().toString("dd/MM/yyyy HH:mm") + "-" + 
								  					reserva.getFin().toString("HH:mm"), 
								  					r.getComienzo().toString("dd/MM/yyyy HH:mm") + "-" +
								  					r.getFin().toString("HH:mm")));
			}
		}
		
		Reserva r = reserva_repository.findOne(reservaActualizada.getId());
		r.setComienzo(reservaActualizada.getStart());
		r.setFin(reservaActualizada.getEnd());
		r.setAsunto(reservaActualizada.getTitle());
		r.setEspacio(espacio_repository.getOne(reservaActualizada.getIdEspacio()));
		return reserva_repository.save(r);
	}
	

	public void eliminarReserva(long idReserva) {
		reserva_repository.delete(idReserva);
	}

	public Page<Reserva> getReservasUsuario(String username, PageRequest pageRequest) {
		return reserva_repository.findByUsername(username, pageRequest);
	} 

	public void eliminarEdificio(long idEdificio) {
		edificio_repository.delete(idEdificio);
		
	}
	
	public Edificio editarEdificio(Edificio edificio){
		Edificio e = edificio_repository.findOne(edificio.getId());
		e.setNombreEdificio(edificio.getNombreEdificio());
		e.setFacultad(edificio.getFacultad());
		return edificio_repository.save(e);
	}
	
	public void eliminarFacultad(long idFacultad) {
		facultad_repository.delete(idFacultad);
		
	}
	
	public Facultad editarFacultad(Facultad facultad){
		Facultad f = facultad_repository.findOne(facultad.getId());
		f.setNombreFacultad(facultad.getNombreFacultad());
		return facultad_repository.save(f);
	}
	
	public void eliminarEspacio(long idEspacio) {
		espacio_repository.delete(idEspacio);
		
	}
	
	public Espacio editarEspacio(Espacio espacio){
		Espacio e = espacio_repository.findOne(espacio.getId());
		e.setNombreEspacio(espacio.getNombreEspacio());
		e.setCapacidad(espacio.getCapacidad());
		e.setMicrofono(espacio.isMicrofono());
		e.setProyector(espacio.isProyector());
		e.setTipoEspacio(espacio.getTipoEspacio());
		return espacio_repository.save(e);
	}

	public List<Facultad> getFacultadesPorTagName(String tagName) {
		return facultad_repository.getFacultadesPorTagName(tagName);
	}

	public List<TipoEspacio> tiposDeEspacios(long idEdificio) {
		return espacio_repository.tiposDeEspacios(idEdificio);
	}


	public Edificio getEdificio(long idEdificio) {
		return edificio_repository.findOne(idEdificio);
	}

	public List<Reserva> getReservasEspacioDeMañana(long idEspacio) {
		return reserva_repository.reservasEspacioDeMañana(idEspacio);
	}

	public List<Reserva> getReservasEspacioDeTarde(long idEspacio) {
		return reserva_repository.reservasEspacioDeTarde(idEspacio);
	}

	public void editarReglasRecurrencia(ReservaDTO rf) {
		Reserva r = reserva_repository.findOne(rf.getId());
		List<String> s = rf.getReglasRecurrencia();
		int i = 0;
		while(i < s.size()){
			String[] w = s.get(i).split(":");
			if(r.getRegla(w[0]) != -1){
				r.addValorRegla(w[0], w[1]);
			}
			else{
				r.addReglaRecurrente(s.get(i));
			}
			
			i++;
		}
		
		reserva_repository.save(r);
		
	}

	public List<Reserva> getReservasGrupo(long idGrupo) {
		return reserva_repository.findByGrupoReservaId(idGrupo);
	}



	
	
}
