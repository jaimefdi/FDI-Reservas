package es.fdi.reservas.reserva.business.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.fdi.reservas.fileupload.business.control.AttachmentRepository;
import es.fdi.reservas.fileupload.business.entity.Attachment;
import es.fdi.reservas.reserva.business.control.EdificioRepository;
import es.fdi.reservas.reserva.business.control.FacultadRepository;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.reserva.web.EdificioDTO;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Service
public class EdificioService {
	
	private EdificioRepository edificio_repository;
	private UserService user_service;
	private FacultadService facultad_service;
	private AttachmentRepository attachment_repository;

	@Autowired
	public EdificioService(FacultadService fs, UserService us,
			EdificioRepository edificio_repository, AttachmentRepository ar) {
		super();
		this.facultad_service = fs;
		this.user_service = us;
		this.edificio_repository = edificio_repository;
		attachment_repository = ar;
	}

	public Edificio editarEdificio(EdificioDTO edificioDTO){
		Edificio e = edificio_repository.findOne(edificioDTO.getId());
		
		if (edificioDTO.getIdFacultad() != null){
			e.setFacultad(facultad_service.getFacultad(edificioDTO.getIdFacultad()));
		}
		
		Attachment attachment = new Attachment("");
		if ((edificioDTO.getImagen() != null) && attachment_repository.getAttachmentByName(edificioDTO.getImagen()).isEmpty()){
			//si no esta, lo añado
			String img = edificioDTO.getImagen();
			int pos = img.lastIndexOf(".");
			String punto = img.substring(0, pos);
			String fin = img.substring(pos+1, img.length());
			String nom = punto + "-" + edificioDTO.getId() + "." + fin;
			nom = nom.replace(nom, "/img/" + nom);
			
			
			attachment.setAttachmentUrl("/img/" + edificioDTO.getImagen());
			attachment.setStorageKey(nom);
		}else{
			attachment = attachment_repository.getAttachmentByName(edificioDTO.getImagen()).get(0);
		}
		
		e.setNombreEdificio(edificioDTO.getNombreEdificio());
		e.setDireccion(edificioDTO.getDireccion());
		e.setImagen(attachment);
		attachment_repository.save(attachment);
		
		return edificio_repository.save(e);
	}
	
	public Edificio addNewEdificio(EdificioDTO edificio) {
		
		Edificio newEdificio = new Edificio();
		newEdificio.setNombreEdificio(edificio.getNombreEdificio());
		newEdificio.setDeleted(false); 
		newEdificio.setDireccion(edificio.getDireccion());
		newEdificio.setFacultad(facultad_service.getFacultadPorId(edificio.getIdFacultad()));
				
		newEdificio.setImagen(attachment_repository.findOne((long) 2));
				
		newEdificio = edificio_repository.save(newEdificio);

		return null;
		
	}

	public Page<Edificio> getEdificiosEliminadosPaginados(Pageable pr) {
		
		return edificio_repository.recycleBin(pr);
	}

	public Attachment getAttachment(Long idAttachment){
		return attachment_repository.getOne(idAttachment);
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
	
	public Edificio restaurarEdificio(Long idEdificio) {
		Edificio e = edificio_repository.findOne(idEdificio);
		e.setDeleted(false);		
		return edificio_repository.save(e);
		
	}

	public Page<Edificio> getEdificiosPorTagName(String tagName, Pageable pagerequest) {
		return edificio_repository.getEdificiosPorTagName(tagName, pagerequest);
	}
	
	public Page<Edificio> getEdificiosEliminadosPorTagName(String tagName, Pageable pagerequest) {
		return edificio_repository.getEdificiosEliminadosPorTagName(tagName, pagerequest);
	}
	
	public List<Edificio> getEdificiosPorTagName(String tagName) {
		return edificio_repository.getEdificiosPorTagName(tagName);
	}

	public Edificio getEdificiosPorNombre(Long nombre) {
		return edificio_repository.findOne(nombre);
	}

	public Page<Edificio> getEdificiosPorDireccion(String tagName, Pageable pagerequest) {
		
		return edificio_repository.getEdificiosPorDireccion(tagName, pagerequest);
	}
	
	public Page<Edificio> getEdificiosEliminadosPorDireccion(String tagName, Pageable pagerequest) {
		
		return edificio_repository.getEdificiosEliminadosPorDireccion(tagName, pagerequest);
	}
	
	public Page<Edificio> getEdificiosPorFacultad(String tagName, Pageable pagerequest) {
		
		return edificio_repository.getEdificiosPorFacultad(tagName, pagerequest);
	}
	
	public Page<Edificio> getEdificiosEliminadosPorFacultad(String tagName, Pageable pagerequest) {
		
		return edificio_repository.getEdificiosEliminadosPorFacultad(tagName, pagerequest);
	}
	
	public List<Edificio> getEdificiosPorFacultad(String tagName) {
		
		return edificio_repository.getEdificiosPorFacultad(tagName);
	}

	public User getCurrentUser(){
		return user_service.getCurrentUser();
	}
	
	public Iterable<Facultad> getFacultades(){
		return facultad_service.getFacultades();
	}
	
	public Edificio getEdificio(long idEdificio){
		return edificio_repository.findOne(idEdificio);
	}
	
	public Iterable<Edificio> getEdificios(){
		return edificio_repository.findAll();
	}
	
	public List<Edificio> getEdificiosFacultad(long idFacultad) {
		return edificio_repository.findByFacultadId(idFacultad);
	}
	
	public Page<Edificio> getEdificiosPaginados(PageRequest pageRequest) {
		return edificio_repository.findAll(pageRequest);
	}
	
	public void desactivarEdificio(long idEdificio) {
		edificio_repository.softDelete(Long.toString(idEdificio));
	}
	
	public List<Edificio> getEdificiosEliminados() {		
		return edificio_repository.recycleBin();
	}
	
	public Edificio save(Edificio e){
		return edificio_repository.save(e);
	}

	public List<Attachment> getAttachmentByName(String imagen) {
		return attachment_repository.getAttachmentByName(imagen);
	}

	public List<Reserva> reservasPendientesUsuario(Long idUsuario, EstadoReserva estadoReserva) {
		return user_service.reservasPendientesUsuario(idUsuario, estadoReserva);
	}

	public Page<Edificio> getEdificiosPaginadosPorNombre(String nombre, PageRequest pagerequest) {
		// TODO Auto-generated method stub
		return edificio_repository.getEdificiosPorTagName(nombre, pagerequest);
	}

	public Page<Edificio> getEdificiosPaginadosPorFacultad(String nombre, PageRequest pagerequest) {
		// TODO Auto-generated method stub
		return edificio_repository.getEdificiosPorFacultad(nombre, pagerequest);
	}

	public Page<Edificio> getEdificiosPaginadosPorDireccion(String nombre, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return edificio_repository.getEdificiosPorDireccion(nombre, pageRequest);
	}
}

