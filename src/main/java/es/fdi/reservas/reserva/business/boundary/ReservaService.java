package es.fdi.reservas.reserva.business.boundary;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import es.fdi.reservas.fileupload.business.control.AttachmentRepository;
import es.fdi.reservas.fileupload.business.entity.Attachment;
import es.fdi.reservas.reserva.business.control.EspacioRepository;
import es.fdi.reservas.reserva.business.control.GrupoReservaRepository;
import es.fdi.reservas.reserva.business.control.ReservaRepository;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.GrupoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.reserva.web.ReservaDTO;

@Service
public class ReservaService {
	
	private ReservaRepository reserva_repository;
	private EspacioRepository espacio_repository;
	private GrupoReservaRepository grupo_repository;
	
	private AttachmentRepository attachment_repository;
	
	@Autowired
	public ReservaService(ReservaRepository rr, EspacioRepository sr, GrupoReservaRepository gr, AttachmentRepository ar){
		reserva_repository = rr;
		espacio_repository = sr;
		grupo_repository = gr;
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
		List<Espacio> lista= espacio_repository.findById(reserva.getEspacio().getId());
		//List<Espacio> lista= espacio_repository.findAll();
		
		Espacio esp=lista.get(0);
		
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
				throw new ReservaSolapadaException(String.format("La reserva %s, solapa con la reserva %s", 
						  reserva.getComienzo().toString("dd/MM/yyyy HH:mm") + "-" + 
				          reserva.getFin().toString("HH:mm"), 
				          r.getComienzo().toString("dd/MM/yyyy HH:mm") + "-" +
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

	

	// todas las reservas de un espacio
	public List<Reserva> getReservasEspacio(long idEspacio) {
		return reserva_repository.findByEspacioId(idEspacio);
	}
	// todos los espacios de un edificio 
	

	public Reserva getReserva(long idReserva) {
		return reserva_repository.findOne(idReserva);
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
		r.setEspacio(espacio_repository.getOne(reservaActualizada.getIdEspacio()));
		r.setReservaColor(reservaActualizada.getColor());
		r.setGrupoReserva(grupo_repository.findOne(reservaActualizada.getIdGrupo()));
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

	
	
	
	
	
	
	public Page<Reserva> getTodasReservasPaginadas(PageRequest pageRequest) {
		/*List<Reserva> lista = reserva_repository.findAll();
		Page<Reserva> pagina = new PageImpl<Reserva>(lista,pageRequest, 5);
		return pagina;*/
		return reserva_repository.findAll(pageRequest);
	}
	
	/*public Facultad eliminarFacultad(FacultadDTO facultad) {
	Facultad f = facultad_repository.findOne(facultad.getId());
	f.setDeleted(true);
	return facultad_repository.save(f);
	
}*/
	
	
	
	
	
	/*public Page<Reserva> getReservasPaginadasUser(PageRequest pageRequest, Long idUsuario) {
		List<Reserva> lista = reserva_repository.findByUserId(idUsuario);
		Page<Reserva> pagina = new PageImpl<Reserva>(lista,pageRequest, 5);
		return pagina;
	}
	
	public Page<Reserva> getReservasPaginadas(PageRequest pageRequest, Long sala) {
		List<Reserva> lista = reserva_repository.findByEspacioId(sala);
		Page<Reserva> pagina = new PageImpl<Reserva>(lista,pageRequest, 5);
		return pagina;
	}
	
	public Page<Reserva> getReservasUsuario(String username, PageRequest pageRequest) {
		return reserva_repository.findByUsername(username, pageRequest);
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
	
	public Espacio editarEspacio(Espacio espacio){
		Espacio e = espacio_repository.findOne(espacio.getId());
		e.setNombreEspacio(espacio.getNombreEspacio());
		e.setCapacidad(espacio.getCapacidad());
		e.setMicrofono(espacio.isMicrofono());
		e.setProyector(espacio.isProyector());
		e.setTipoEspacio(espacio.getTipoEspacio());
		return espacio_repository.save(e);
	}*/

	
	
	

	
	
	
	
	
	
	

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
		return reserva_repository.findByGrupoReservaIdAndUserId(idGrupo, idUsuario);
	}

//	public List<Espacio> getEspaciosPorFacultad(String nombreFacultad) {
//		return espacio_repository.getEspacioPorFacultad(nombreFacultad);
//		//return null;
//	}


	public void cambiarDeCalendario(Long idGrupo2, ReservaDTO rfDTO) {
		Reserva reserva = getReserva(rfDTO.getId());
		reserva.setComienzo(rfDTO.getStart());
		reserva.setFin(rfDTO.getEnd());
		GrupoReserva grupo = grupo_repository.getOne(idGrupo2);
		reserva.setGrupoReserva(grupo);
		
		reserva_repository.save(reserva);
		
	}

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

}
