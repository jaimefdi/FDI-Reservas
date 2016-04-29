package es.fdi.reservas.reserva.business.boundary;

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
import org.springframework.data.domain.Page;

import es.fdi.reservas.reserva.web.*;
import es.fdi.reservas.reserva.web.ReservaFullCalendarDTO;

@Service
public class ReservaService {
	
	private ReservaRepository reserva_repository;
	private FacultadRepository facultad_repository;
	private EdificioRepository edificio_repository;
	private EspacioRepository espacio_repository;
	
	@Autowired
	public ReservaService(ReservaRepository rr, FacultadRepository fr, EdificioRepository er, EspacioRepository sr){
		reserva_repository = rr;
		facultad_repository = fr;
		edificio_repository = er;
		espacio_repository = sr;
	}

	public List<Reserva> getAllReservations(String username) {
		return reserva_repository.findByUsername(username);
	}

	public Reserva agregarReserva(Reserva reserva, String username) {
		List<Reserva> reservas = reserva_repository.reservasConflictivas(reserva.getEspacio().getId(), reserva.getComienzo(), reserva.getFin());
		
		for(Reserva r: reservas ){
			if ( r.solapa(reserva.getComienzo(), reserva.getFin()) ) {
				throw new ReservaSolapadaException(String.format("La reserva %d, solapa con la reserva %d", reserva.getId(), r.getId()));
			}
		}
		
		Reserva nuevaReserva = new Reserva(reserva.getAsunto(),reserva.getComienzo(),reserva.getFin(),username, reserva.getEspacio());
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
	public List<Espacio> getAllSpaces(long id_edif) {
		return espacio_repository.findByEdificio_Id(id_edif);
	}

	public Espacio getSpaceById(long id_espacio) {
		return espacio_repository.findOne(id_espacio);
	}

	public List<Espacio> getTypeSpaces(long id_edif, TipoEspacio id_tipoEspacio) {
		return espacio_repository.findByEdificio_IdAndTipoEspacio(id_edif, id_tipoEspacio);
	}

	public Reserva getReservaById(long id_res) {
		return reserva_repository.findOne(id_res);
	}

	public Iterable<Facultad> getFacultades() {
		return facultad_repository.findAll();
	}
	
	public Iterable<Espacio> getEspacios() {
		return espacio_repository.findAll();
	}

	public List<Edificio> getEdificiosPorIdFacultad(long id_facultad) {
		return edificio_repository.findByFacultad_Id(id_facultad);
	}

	public Reserva editaReserva(ReservaFullCalendarDTO reservaActualizada) {
		DateTime start = reservaActualizada.getStart().withTime(0, 0, 0, 0);
		DateTime end = start.plusDays(1);
		List<Reserva> reservas = reserva_repository.findByEspacio_IdAndComienzoBetween(reservaActualizada.getIdEspacio(), start, end);
		for(Reserva r: reservas ){
			if ( r.solapa(reservaActualizada.getStart(), reservaActualizada.getEnd()) && ! reservaActualizada.getId().equals(r.getId())) {
				throw new ReservaSolapadaException(String.format("La reserva %d, solapa con la reserva %d", reservaActualizada.getId(), r.getId()));
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

	public Page<Reserva> getReservasPaginadas(PageRequest pageRequest) {
		return reserva_repository.findAll(pageRequest);
	}

	public void eliminarEdificio(long idEdificio) {
		//edificio_repository.delete(idEdificio);
		edificio_repository.softDelete(Long.toString(idEdificio));
	}
	
	public Edificio editarEdificioDeleted(Long idEdificio){
		Edificio f = edificio_repository.findOne(idEdificio);
		f.setDeleted(true);
		return edificio_repository.save(f);
	}
	
	public Page<Edificio> getEdificiosPaginados(PageRequest pageRequest) {
		return edificio_repository.findAll(pageRequest);
	}
	
	public Edificio editarEdificio(EdificioDTO edificio){
		Edificio e = edificio_repository.findOne(edificio.getId());
		e.setNombre_edificio(edificio.getNombre_edificio());
		e.setFacultad(facultad_repository.findOne(edificio.getIdFacultad()));
		
		return edificio_repository.save(e);
	}
	
	public void eliminarFacultad(Long idFacultad) {
		//String aux = Long.toString(idFacultad);
		facultad_repository.softDelete(idFacultad);
		
	}
	
	public Page<Facultad> getFacultadesPaginadas(PageRequest pageRequest) {
		return facultad_repository.findAll(pageRequest);
	}
	
	/*public Facultad eliminarFacultad(FacultadDTO facultad) {
		Facultad f = facultad_repository.findOne(facultad.getId());
		f.setDeleted(true);
		return facultad_repository.save(f);
		
	}*/
	
	public Facultad editarFacultad(FacultadDTO facultad){
		Facultad f = facultad_repository.findOne(facultad.getId());
		f.setNombreFacultad(facultad.getNombreFacultad());
		f.setDir(facultad.getDir());
		return facultad_repository.save(f);
	}
	
	public Facultad editarFacultadDeleted(Long idFacultad){
		Facultad f = facultad_repository.findOne(idFacultad);
		f.setDeleted(true);
		return facultad_repository.save(f);
	}
	
	public void eliminarEspacio(long idEspacio) {
		//espacio_repository.delete(idEspacio);
		espacio_repository.softDelete(Long.toString(idEspacio));
	}
	
	public Page<Espacio> getEspaciosPaginados(PageRequest pageRequest) {
		return espacio_repository.findAll(pageRequest);
	}
	
	public Espacio editarEspacioDeleted(Long idEspacio){
		Espacio e = espacio_repository.findOne(idEspacio);
		e.setDeleted(true);
		return espacio_repository.save(e);
	}
	
	public Espacio editarEspacio(EspacioTipoDTO espacio){
		Espacio e = espacio_repository.findOne(espacio.getId());
		e.setNombre_espacio(espacio.getNombre_espacio());
		e.setCapacidad(espacio.getCapacidad());
		e.setMicrofono(espacio.isMicrofono());
		e.setProyector(espacio.isProyector());
		e.setTipoEspacio(TipoEspacio.fromTipoEspacio(espacio.getTipoEspacio()));
		e.setEdificio(edificio_repository.findOne(espacio.getIdEdificio()));
		return espacio_repository.save(e);
	}

	public List<Facultad> getFacultadesPorTagName(String tagName) {
		return facultad_repository.getFacultadesPorTagName(tagName);
	}
	
	public Facultad addNewFacultad(Facultad facultad){
		Facultad newFacultad = new Facultad(facultad.getNombreFacultad(), facultad.getDir());
		newFacultad = facultad_repository.save(newFacultad);
		
		if (newFacultad != null){
			System.out.println("Facultad añadida correctamente");
			
		}
		
		return newFacultad;
	}
	
	public Espacio addNewEspacio(EspacioTipoDTO espacio){
		Espacio newEspacio = new Espacio(espacio.getNombre_espacio(), espacio.getCapacidad(), espacio.isMicrofono(), espacio.isProyector(), 
				TipoEspacio.fromTipoEspacio(espacio.getTipoEspacio()), edificio_repository.findOne(espacio.getIdEdificio()));
		newEspacio = espacio_repository.save(newEspacio);
		
		return newEspacio;
	}

	public List<TipoEspacio> tiposDeEspacios(long idEdificio) {
		return espacio_repository.tiposDeEspacios(idEdificio);
	}

	public Edificio addNewEdificio(EdificioDTO edificio) {
		
		Edificio newEdificio = new Edificio(edificio.getNombre_edificio(), facultad_repository.findOne(edificio.getIdFacultad()));
		newEdificio = edificio_repository.save(newEdificio);
		
		return newEdificio;
		
	}

	public List<Edificio> getEdificiosEliminados() {
		
		return edificio_repository.recycleBin();
	}

	public List<Facultad> getFacultadesEliminadas() {
		
		return facultad_repository.recycleBin();
	}
	
	public List<Espacio> getEspaciosEliminados() {
		
		return espacio_repository.recycleBin();
	}

	public Edificio restaurarEdificio(Long idEdificio) {
		Edificio e = edificio_repository.findOne(idEdificio);
		e.setDeleted(false);		
		return edificio_repository.save(e);
		
	}
	
	public Facultad restaurarFacultad(Long idFacultad) {
		Facultad e = facultad_repository.findOne(idFacultad);
		e.setDeleted(false);		
		return facultad_repository.save(e);
		
	}
	
	public Espacio restaurarEspacio(Long idEspacio) {
		Espacio e = espacio_repository.findOne(idEspacio);
		e.setDeleted(false);		
		return espacio_repository.save(e);
		
	}

	
}
