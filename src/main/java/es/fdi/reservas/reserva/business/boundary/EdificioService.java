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
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.web.EdificioDTO;
import es.fdi.reservas.users.business.entity.User;

@Service
public class EdificioService {
	
	private FacultadRepository facultad_repository;
	private AttachmentRepository attachment_repository;
	private EdificioRepository edificio_repository;

	@Autowired
	public EdificioService(FacultadRepository facultad_repository, AttachmentRepository attachment_repository,
			EdificioRepository edificio_repository) {
		super();
		this.facultad_repository = facultad_repository;
		this.attachment_repository = attachment_repository;
		this.edificio_repository = edificio_repository;
	}

	public List<Edificio> getEdificiosFacultad(long idFacultad) {
		return edificio_repository.findByFacultadId(idFacultad);
	}
	
	public Page<Edificio> getEdificiosPaginados(PageRequest pageRequest) {
		return edificio_repository.findAll(pageRequest);
	}
	
	public Edificio editarEdificio(EdificioDTO edificio, Attachment attachment){
		Edificio e = edificio_repository.findOne(edificio.getId());
		
		e.setNombreEdificio(edificio.getNombreEdificio());
		e.setDireccion(edificio.getDireccion());
		Facultad fac = facultad_repository.findOne(edificio.getIdFacultad());
		e.setFacultad(fac);
		//e.setFacultad(facultad_repository.getOne(edificio.getIdFacultad()));
		e.setImagen(attachment);
		attachment_repository.save(attachment);
		return edificio_repository.save(e);
	}
	
public Edificio addNewEdificio(Edificio edificio) {
		
		Attachment img = edificio.getImagen();
		if (img == null){
			//img = attachment_repository.getAttachmentByName("casa").get(0);
			img = attachment_repository.findOne((long) 2);
			edificio.setImagen(img);
			//attachment_repository.save(img);
		}
		
		Facultad fac = edificio.getFacultad();
		if (fac == null){
			fac = facultad_repository.findOne((long) 27);
			edificio.setFacultad(fac);
			//facultad_repository.save(fac);
		}
		
		Edificio newEdificio = new Edificio(edificio.getNombreEdificio(), edificio.getDireccion(),edificio.getFacultad(), edificio.getImagen());
		newEdificio = edificio_repository.save(newEdificio);

		return null;
		
	}

	public List<Edificio> getEdificiosEliminados() {
		
		return edificio_repository.recycleBin();
	}
	
	public Edificio getEdificio(long idEdificio) {
		return edificio_repository.findOne(idEdificio);
	}
	
	public Iterable<Edificio> getEdificios(){
		return edificio_repository.findAll();
	}
	
	public Attachment getAttachment(Long idAttachment){
		return attachment_repository.getOne(idAttachment);
	}

	public List<Attachment> getAttachmentByName(String img) {
		return attachment_repository.getAttachmentByName(img);
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
		// TODO Auto-generated method stub
		return edificio_repository.getEdificiosPorTagName(tagName, pagerequest);
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
	
	public Page<Edificio> getEdificiosPorFacultad(String tagName, Pageable pagerequest) {
		
		return edificio_repository.getEdificiosPorFacultad(tagName, pagerequest);
	}

}
