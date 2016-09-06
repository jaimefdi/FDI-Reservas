package es.fdi.reservas.reserva.business.boundary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import es.fdi.reservas.fileupload.business.control.AttachmentRepository;
import es.fdi.reservas.fileupload.business.entity.Attachment;
import es.fdi.reservas.reserva.business.control.EspacioRepository;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.reserva.business.entity.TipoEspacio;
import es.fdi.reservas.reserva.web.EspacioDTO;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Service
public class EspacioService {

	private EspacioRepository espacio_repository;
	private UserService user_service;
	private EdificioService edificio_service;
	private AttachmentRepository attachment_repository;
	
	@Autowired
	public EspacioService(EspacioRepository er, UserService us, EdificioService es, AttachmentRepository ar){
		espacio_repository = er;
		user_service = us;
		edificio_service = es;
		attachment_repository = ar;
	}
	
	public Page<Espacio> getEspaciosPaginados(Pageable pageRequest) {
		return espacio_repository.findAll(pageRequest);
	}
	
	public Espacio editarEspacioDeleted(Long idEspacio){
		Espacio e = espacio_repository.findOne(idEspacio);
		e.setDeleted(true);
		return espacio_repository.save(e);
	}
	
	public Espacio editarEspacio(EspacioDTO espacio, Attachment attachment){
		Espacio e = espacio_repository.findOne(espacio.getId());
		e.setNombreEspacio(espacio.getNombreEspacio());
		e.setCapacidad(espacio.getCapacidad());
		e.setMicrofono(espacio.isMicrofono());
		e.setProyector(espacio.isProyector());
		e.setTipoEspacio(TipoEspacio.fromTipoEspacio(espacio.getTipoEspacio()));
		e.setEdificio(edificio_service.getEdificio(espacio.getIdEdificio()));
		e.setImagen(attachment);
		
		return espacio_repository.save(e);
	}
	
	public Espacio addNewEspacio(EspacioDTO espacioDTO){
		TipoEspacio tipoEspacio = TipoEspacio.fromTipoEspacio(espacioDTO.getTipoEspacio());
		Espacio newEspacio = new Espacio(espacioDTO.getNombreEspacio(),espacioDTO.getCapacidad(), tipoEspacio); 

		newEspacio.setEdificio(edificio_service.getEdificio(1));
		newEspacio.setImagen(attachment_repository.findOne((long) 1));
		newEspacio = espacio_repository.save(newEspacio);
		
		return newEspacio;
	}
	
	public Espacio restaurarEspacio(Long idEspacio) {
		Espacio e = espacio_repository.findOne(idEspacio);
		e.setDeleted(false);		
		return espacio_repository.save(e);
		
	}

	public List<Attachment> getAttachmentByName(String imagen) {
		return attachment_repository.getAttachmentByName(imagen);
	}

	public List<Edificio> getEdificiosPorTagName(String tagName) {	
		return edificio_service.getEdificiosPorTagName(tagName);
	}

	public Page<Espacio> getEspaciosPorNombre(String nombre, Pageable pagerequest) {
		return espacio_repository.getEspaciosByTagName(nombre,pagerequest);
	}

	public Page<Espacio> getEspaciosPorEdificio(String tagName, Pageable pagerequest) {
		return espacio_repository.getEspaciosPorEdificio(tagName, pagerequest);
	}
	
	public Page<Espacio> getEspaciosEliminadosPorNombre(String nombre, Pageable pagerequest) {
		return espacio_repository.getEspaciosEliminadosByTagName(nombre,pagerequest);
	}

	public Page<Espacio> getEspaciosEliminadosPorEdificio(String tagName, Pageable pagerequest) {
		return espacio_repository.getEspaciosEliminadosPorEdificio(tagName, pagerequest);
	}

	public User getCurrentUser(){
		return user_service.getCurrentUser();
	}
	
	public Iterable<Edificio> getEdificios(){
		return edificio_service.getEdificios();
	}
	
	public Espacio getEspacio(long idEspacio){
		return espacio_repository.findOne(idEspacio);
	}
	
	public Iterable<Espacio> getEspacios() {
		return espacio_repository.findAll();
	}
	
	public List<Espacio> getEspaciosEdificio(long idEdificio) {
		return espacio_repository.findByEdificioId(idEdificio);
	}
	
	// Espacios de un edificio de un TipoEspacio en concreto
	public List<Espacio> getTiposEspacio(long idEdificio, TipoEspacio idTipoEspacio) {
		return espacio_repository.findByEdificioIdAndTipoEspacio(idEdificio, idTipoEspacio);
	}
	
	// Todos los TipoEspacio que tiene un edificio
	public List<TipoEspacio> tiposDeEspacios(long idEdificio) {
		return espacio_repository.tiposDeEspacios(idEdificio);
	}
	
	public List<Espacio> getEspaciosPorTagName(String tag) {
		return espacio_repository.getEspaciosByTagName(tag);
	}
	
	public Page<Espacio> getEspaciosPaginados(PageRequest pageRequest) {
		return espacio_repository.findAll(pageRequest);
	}
	
	public void eliminarEspacio(long idEspacio) {
		espacio_repository.softDelete(Long.toString(idEspacio));
	}
	
	public List<Espacio> getEspaciosEliminados() {		
		return espacio_repository.recycleBin();
	}
	
	public Espacio save(Espacio e){
		return espacio_repository.save(e);
	}

	public Page<Espacio> getEspaciosEliminadosPaginados(Pageable pageRequest) {
		return espacio_repository.getEspaciosEliminadosPaginados(pageRequest);
	}

	public List<Reserva> reservasPendientesUsuario(Long idUsuario, EstadoReserva estadoReserva) {
		return user_service.reservasPendientesUsuario(idUsuario, estadoReserva);
	}
}

