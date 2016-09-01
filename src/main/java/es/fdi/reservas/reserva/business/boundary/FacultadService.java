package es.fdi.reservas.reserva.business.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.fdi.reservas.reserva.business.control.FacultadRepository;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.web.FacultadDTO;

@Service
public class FacultadService {
	
	private FacultadRepository facultad_repository;
	
	@Autowired
	public FacultadService(FacultadRepository facultad_repository) {
		this.facultad_repository = facultad_repository;
	}

	public Iterable<Facultad> getFacultades() {
		return facultad_repository.findAll();
	}
	
	public void eliminarFacultad(Long idFacultad) {
		//String aux = Long.toString(idFacultad);
		facultad_repository.softDelete(idFacultad);
		
	}
	
	public Facultad getFacultad(long idFacul){
		return facultad_repository.findOne(idFacul);
	//	return facultad_repository.getFacultadPorId(idFacul);
	}
	
//	public Facultad getFacultadPorNombre(String nombre){
//		return facultad_repository.getFacultadPorNombre(nombre);
//	}
	
	public Page<Facultad> getFacultadesPaginadas(PageRequest pageRequest) {
		return facultad_repository.findAll(pageRequest);
	}
	
	public Facultad editarFacultad(FacultadDTO facultad){
		Facultad f = facultad_repository.findOne(facultad.getId());
		f.setNombreFacultad(facultad.getNombreFacultad());
		f.setWebFacultad(facultad.getWebFacultad());
		return facultad_repository.save(f);
	}
	
	public Facultad editarFacultadDeleted(Long idFacultad){
		Facultad f = facultad_repository.findOne(idFacultad);
		f.setDeleted(true);
		return facultad_repository.save(f);
	}
	
	public Page<Facultad> getFacultadesPorTagName(String tagName, Pageable pagerequest) {
		return facultad_repository.getFacultadesPorTagName(tagName, pagerequest);
	}
	
	public List<Facultad> getFacultadesPorTagName(String tagName) {
		return facultad_repository.getFacultadesPorTagName(tagName);
	}
	
	public Facultad addNewFacultad(Facultad facultad){
		Facultad newFacultad = new Facultad(facultad.getNombreFacultad(), facultad.getWebFacultad());
		newFacultad = facultad_repository.save(newFacultad);
		
		if (newFacultad != null){
			System.out.println("Facultad añadida correctamente");
			
		}
		
		return newFacultad;
	}
	
	public Facultad restaurarFacultad(Long idFacultad) {
		Facultad e = facultad_repository.findOne(idFacultad);
		e.setDeleted(false);		
		return facultad_repository.save(e);
		
	}
	
	public Facultad getFacultadPorId(long l) {
		// TODO Auto-generated method stub
		return facultad_repository.getFacultadPorId(l);
	}
	
	public List<Facultad> getFacultadesEliminadas() {
		
		return facultad_repository.recycleBin();
	}

	public Page<Facultad> getFacultadesPaginadasPorNombre(Long nombre, PageRequest pageRequest) {
		
		return null;
	}

	public Page<Facultad> getFacultadesPorWeb(String tagName, Pageable pagerequest) {
		
		return facultad_repository.getFacultadesPorWeb(tagName, pagerequest);
	}
}
