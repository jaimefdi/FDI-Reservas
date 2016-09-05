package es.fdi.reservas.reserva.business.boundary;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import es.fdi.reservas.fileupload.business.control.AttachmentRepository;
import es.fdi.reservas.fileupload.business.entity.Attachment;
import es.fdi.reservas.reserva.business.control.EspacioRepository;
import es.fdi.reservas.reserva.business.control.GrupoReservaRepository;

import es.fdi.reservas.reserva.business.control.ReservaRepository;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.business.entity.GrupoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;

import es.fdi.reservas.reserva.web.ReservaDTO;

import es.fdi.reservas.reserva.business.entity.TipoEspacio;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;
import es.fdi.reservas.reserva.web.*;

@Service
public class ReservaService {
	
	private ReservaRepository reserva_repository;

	private FacultadService facultad_service;
	private EdificioService edificio_service;
	private EspacioService espacio_service;
	private GrupoReservaService grupo_service;
	private UserService user_service;

	
	private AttachmentRepository attachment_repository;
	
	@Autowired
	public ReservaService(ReservaRepository rr, FacultadService fs, EdificioService es, 
						EspacioService ess, GrupoReservaService grs, UserService us, AttachmentRepository ar){
		reserva_repository = rr;
		facultad_service = fs;
		edificio_service = es;
		espacio_service = ess;
		grupo_service = grs;
		user_service = us;
		attachment_repository = ar;
	}

	public List<Reserva> getAllReservasConflictivas(Long idEspacio, DateTime start, DateTime end){
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
	
	
	public List<Reserva> getReservasUsuario(Long idUsuario) {
		return reserva_repository.findByUserId(idUsuario);
	}

	public Reserva compruebaAutorizacion(Reserva reserva)
	{
		Espacio esp = espacio_service.getEspacio(reserva.getEspacio().getId());
		//List<Espacio> lista= espacio_repository.findAll();
		
		//Espacio esp=lista.get(0);
		
		if (esp.getTipoAutorizacion().toString()=="Necesaria")
			//Autorizacion Obligatoria
			reserva.setEstadoReserva(EstadoReserva.PENDIENTE);
		else if ((esp.getTipoAutorizacion().toString()=="Por horas") && 
				(reserva.getComienzo().plusHours(esp.getHorasAutorizacion()).isBefore(reserva.getFin())))
			//horaComienzo + horasAutorizacion > horaFin
			reserva.setEstadoReserva(EstadoReserva.PENDIENTE);
		else
			reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
		return reserva;
	}
	
	public Reserva agregarReserva(Reserva reserva) {		
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
				throw new ReservaSolapadaException(String.format("La reserva que estás intentando realizar solapa con la reserva del %s", 
				          r.getComienzo().toString("dd/MM/yyyy") + " de " +
				          r.getComienzo().toString("HH:mm") + " a " +
				          r.getFin().toString("HH:mm")));
			}
		}
		
		Reserva nuevaReserva = new Reserva(reserva.getAsunto(),reserva.getComienzo(),reserva.getFin(),
										   reserva.getUser(), reserva.getEspacio(), reserva.getGrupoReserva(),
										   reserva.getStartRecurrencia(), reserva.getEndRecurrencia(),
										   reserva.getReservaColor(), reserva.getRecurrenteId());
		
		nuevaReserva = compruebaAutorizacion(nuevaReserva);
		
		nuevaReserva.setReglasRecurrencia(reserva.getReglasRecurrencia());
		nuevaReserva = reserva_repository.save(nuevaReserva);
		
		return nuevaReserva;
	}

	public List<Reserva> getReservas() {
		return reserva_repository.findAll();
	}

	public Iterable<Edificio> getEdificios(){
		return edificio_service.getEdificios();
	}

	// todas las reservas de un espacio
	public List<Reserva> getReservasEspacio(long idEspacio) {
		return reserva_repository.reservasEspacio(idEspacio);
	}
	// todos los espacios de un edificio 

	public List<Espacio> getEspaciosEdificio(long idEdificio) {
		return espacio_service.getEspaciosEdificio(idEdificio);
	}

	public Espacio getEspacio(long idEspacio) {
		return espacio_service.getEspacio(idEspacio);
	}

	public List<Espacio> getTiposEspacio(long idEdificio, TipoEspacio idTipoEspacio) {
		return espacio_service.getTiposEspacio(idEdificio, idTipoEspacio);
	}


	public Reserva getReserva(long idReserva) {
		return reserva_repository.findOne(idReserva);
	}

	public Iterable<Facultad> getFacultades() {
		return facultad_service.getFacultades();
	}
	
	public Iterable<Espacio> getEspacios() {
		return espacio_service.getEspacios();
	}

	public List<Edificio> getEdificiosFacultad(long idFacultad) {
		return edificio_service.getEdificiosFacultad(idFacultad);
	}

	public Reserva editarReservaSimple(ReservaDTO reservaActualizada) {
		Reserva reserva = new Reserva();
		reserva.setComienzo(reservaActualizada.getStart());
		reserva.setFin(reservaActualizada.getEnd());
		reserva.setReglasRecurrencia(reservaActualizada.getReglasRecurrencia());
		
		String recurrenteID = reservaActualizada.getRecurrenteId();
		if(recurrenteID != null){
			String[] w = recurrenteID.split("_");
			Long idR = Long.valueOf(w[0]);
			reservaActualizada.setId(idR);
		}
		
		
		
		Long idEspacio = reservaActualizada.getIdEspacio();
		DateTime start = reservaActualizada.getStart();
		DateTime end = reservaActualizada.getEnd();
		
		List<Reserva> reservas = getAllReservasConflictivas(idEspacio, start, end);
		for(Reserva r: reservas ){
			if ( r.solapa(reserva) && ! reservaActualizada.getId().equals(r.getId())) {
				throw new ReservaSolapadaException(	String.format("La reserva que estás intentando realizar solapa con la reserva %s",						  					 
								  					r.getComienzo().toString("dd/MM/yyyy HH:mm") + "-" +
								  					r.getFin().toString("HH:mm")));
			}
		}
		
		Reserva r = reserva_repository.findOne(reservaActualizada.getId());
		r.setComienzo(reservaActualizada.getStart());
		r.setFin(reservaActualizada.getEnd());
		r.setAsunto(reservaActualizada.getTitle());
		r.setEspacio(getEspacio(reservaActualizada.getIdEspacio()));
		r.setReservaColor(reservaActualizada.getColor());
		r.setGrupoReserva(grupo_service.getGrupoReserva(reservaActualizada.getIdGrupo()));
		if(reservaActualizada.getEstado() != null){
		  r.setEstadoReserva(EstadoReserva.fromEstadoReserva(reservaActualizada.getEstado()));
		}
		
		return reserva_repository.save(r);
	}
	
	public void eliminarReserva(long idReserva) {
		reserva_repository.delete(idReserva);
	}
	
	public Page<Reserva> getReservasUsuario(Long idUsuario, PageRequest pageRequest) {
		return reserva_repository.findByUserId(idUsuario, pageRequest);
	}
	
	public Page<Reserva> getReservasEspacio(Long espacio, PageRequest pageRequest) {
		return reserva_repository.findByEspacioId(espacio, pageRequest);
	}

	public void desactivarEdificio(long idEdificio) {
		edificio_service.desactivarEdificio(idEdificio);
	}
	
	public Edificio editarEdificioDeleted(Long idEdificio){
		Edificio e = getEdificio(idEdificio);
		e.setDeleted(true);
		
		return edificio_service.save(e);
	}
	
	public Page<Edificio> getEdificiosPaginados(PageRequest pageRequest) {
		return edificio_service.getEdificiosPaginados(pageRequest);
	}
	
	public Edificio editarEdificio(EdificioDTO edificio){
		Edificio e = getEdificio(edificio.getId());
		e.setNombreEdificio(edificio.getNombreEdificio());
		e.setDireccion(edificio.getDireccion());
		
		return edificio_service.save(e);
	}
	
	public void eliminarFacultad(Long idFacultad) {
		facultad_service.eliminarFacultad(idFacultad);	
	}
	
	public Facultad getFacultad(long idFacultad){
		return facultad_service.getFacultad(idFacultad);
	}
	
	public Page<Facultad> getFacultadesPaginadas(PageRequest pageRequest) {
		return facultad_service.getFacultadesPaginadas(pageRequest);
	}

	
	public Page<Reserva> getTodasReservasPaginadas(PageRequest pageRequest) {
		return reserva_repository.findAll(pageRequest);
	}
	
	public Facultad eliminarFacultad(FacultadDTO facultad) {
		Facultad f = getFacultad(facultad.getId());
		f.setDeleted(true);
		
		return facultad_service.save(f);	
	}

	public Page<Reserva> getReservasPaginadasUser(PageRequest pageRequest, Long idUsuario) {
		List<Reserva> lista = reserva_repository.findByUserId(idUsuario);
		Page<Reserva> pagina = new PageImpl<Reserva>(lista,pageRequest, 5);
		return pagina;
	}
	
	public Page<Reserva> getReservasPaginadas(PageRequest pageRequest, Long sala) {
		List<Reserva> lista = reserva_repository.findByEspacioId(sala);
		Page<Reserva> pagina = new PageImpl<Reserva>(lista,pageRequest, 5);
		return pagina;
	}
	
	public Facultad editarFacultad(FacultadDTO facultad){
		Facultad f = getFacultad(facultad.getId());
		f.setNombreFacultad(facultad.getNombreFacultad());
		f.setWebFacultad(facultad.getWebFacultad());
		
		return facultad_service.save(f);
	}
	
	public Facultad editarFacultadDeleted(Long idFacultad){
		Facultad f = getFacultad(idFacultad);
		f.setDeleted(true);
		return facultad_service.save(f);
	}
	
	public List<Espacio> getEspaciosPorTagName(String tag) {
		return espacio_service.getEspaciosPorTagName(tag);
	}
	
	public void eliminarEspacio(long idEspacio) {
		espacio_service.eliminarEspacio(idEspacio);
	}
	
	public Page<Espacio> getEspaciosPaginados(PageRequest pageRequest) {
		return espacio_service.getEspaciosPaginados(pageRequest);
	}
	
	public Espacio editarEspacioDeleted(Long idEspacio){
		Espacio e = getEspacio(idEspacio);
		e.setDeleted(true);
		return espacio_service.save(e);
	}
	
	public Espacio editarEspacio(EspacioDTO espacio){
		Espacio e = getEspacio(espacio.getId());
		e.setNombreEspacio(espacio.getNombreEspacio());
//		e.setCapacidad(espacio.getCapacidad());
//		e.setMicrofono(espacio.isMicrofono());
//		e.setProyector(espacio.isProyector());
//		e.setTipoEspacio(TipoEspacio.fromTipoEspacio(espacio.getTipoEspacio()));
		
		return espacio_service.save(e);

	}

	public List<Facultad> getFacultadesPorTagName(String tagName) {
		return facultad_service.getFacultadesPorTagName(tagName);
	}
	
	public Facultad addNewFacultad(Facultad facultad){
		Facultad newFacultad = new Facultad(facultad.getNombreFacultad(), facultad.getWebFacultad());
		newFacultad = facultad_service.save(newFacultad);
		
		if (newFacultad != null){
			System.out.println("Facultad añadida correctamente");
			
		}
		
		return newFacultad;
	}
	
	public Espacio addNewEspacio(Espacio espacio){
//		Espacio newEspacio = new Espacio(espacio.getNombreEspacio(),espacio.getEdificio(),
//				                         true, true,espacio.getTipoEspacio(), 
//		TipoEspacio.fromTipoEspacio(espacio.getTipoEspacio()), edificio_repository.findOne(espacio.getIdEdificio()));
//		newEspacio = espacio_repository.save(newEspacio);
		
		return null;
	}

	public List<TipoEspacio> tiposDeEspacios(long idEdificio) {
		return espacio_service.tiposDeEspacios(idEdificio);
	}

	public Edificio addNewEdificio(EdificioDTO edificio) {
		
//		Edificio newEdificio = new Edificio(edificio.getNombre_edificio(), facultad_repository.findOne(edificio.getIdFacultad()));
//		newEdificio = edificio_repository.save(newEdificio);
//		
		return null;
		
	}

	public List<Edificio> getEdificiosEliminados() {		
		return edificio_service.getEdificiosEliminados();
	}

	public List<Facultad> getFacultadesEliminadas() {		
		return facultad_service.getFacultadesEliminadas();
	}
	
	public List<Espacio> getEspaciosEliminados() {		
		return espacio_service.getEspaciosEliminados();
	}

	public Edificio restaurarEdificio(Long idEdificio) {
		Edificio e = getEdificio(idEdificio);
		e.setDeleted(false);	
		
		return edificio_service.save(e);		
	}
	
	public Facultad restaurarFacultad(Long idFacultad) {
		Facultad e = getFacultad(idFacultad);
		e.setDeleted(false);	
		
		return facultad_service.save(e);		
	}
	
	public Espacio restaurarEspacio(Long idEspacio) {
		Espacio e = getEspacio(idEspacio);
		e.setDeleted(false);	
		
		return espacio_service.save(e);	
	}
	
	public Edificio getEdificio(long idEdificio) {
		return edificio_service.getEdificio(idEdificio);
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
		if(r.rangoRecurrencias().size() > 1){
			reserva_repository.save(r);
		}
		else{
			// si queda un solo evento lo transformo a un evento simple
			r.setReglasRecurrencia(new ArrayList<String>());
			r.setStartRecurrencia(null);
			r.setEndRecurrencia(null);
			reserva_repository.save(r);
		}
		
	}

	public List<Reserva> getReservasGrupo(long idGrupo, long idUsuario) {
		return reserva_repository.reservasGrupoUsuario(idGrupo, idUsuario);
	}

//	public List<Espacio> getEspaciosPorFacultad(String nombreFacultad) {
//		return espacio_repository.getEspacioPorFacultad(nombreFacultad);
//		//return null;
//	}


	public List<Reserva> reservasPendientesUsuario(Long idUsuario, EstadoReserva estado) {
		return reserva_repository.reservasPendientesUsuario(idUsuario, estado);
	}

	public void eliminarExdate(ReservaDTO rf) {
		Reserva r = reserva_repository.findOne(rf.getId());
		List<String> s = r.getReglasRecurrencia();
		String[] w = s.get(1).split(":");
		String[] st = w[1].split(";");
		List<String> aux = new ArrayList<>();
		int i = 0;
		if(st.length == 1){
			s.remove(1);
		}
		else{// si tiene más de un EXDATE
			while(i < st.length-1){
				aux.add(st[i]);
				i++;
			}
			String q = "EXDATE:" + String.join(";", aux);
			r.removeValorRegla(w[0], q);
		}
		
		reserva_repository.save(r);
		
	}


	public void addAttachment(Attachment attachment) {
		attachment_repository.save(attachment);
		
	}

	public List<Reserva> misReservasCalendario(Long idUsuario) {
		return reserva_repository.misReservasCalendario(idUsuario);
	}

	public List<GrupoReserva> getGruposUsuario(Long idUsuario) {
		return grupo_service.getGruposUsuario(idUsuario);
	}

	public User getCurrentUser() {
		return user_service.getCurrentUser();
	}

}
