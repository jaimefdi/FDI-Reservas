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
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Service
public class FacultadService {
	
	private FacultadRepository facultad_repository;
	private UserService user_service;
	
	@Autowired
	public FacultadService(FacultadRepository fr, UserService us){
		facultad_repository = fr;
		user_service = us;
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

	public Page<Facultad> getFacultadesEliminadasPorTagName(String tagName, Pageable pagerequest) {
		return facultad_repository.getFacultadesEliminadasPorTagName(tagName, pagerequest);
	}
	
	public User getCurrentUser(){
		return user_service.getCurrentUser();
	}
	
	public Facultad getFacultad(long idFacultad){
		return facultad_repository.findOne(idFacultad);
	}
	
	public Iterable<Facultad> getFacultades() {
		return facultad_repository.findAll();

	}
	
	public List<Facultad> getFacultadesPorTagName(String tagName) {
		return facultad_repository.getFacultadesPorTagName(tagName);
	}
	

	public Facultad addNewFacultad(FacultadDTO facultad){
		Facultad newFacultad = new Facultad(facultad.getNombreFacultad(), facultad.getWebFacultad());
		newFacultad = facultad_repository.save(newFacultad);
				
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
	
	public Page<Facultad> getFacultadesEliminadasPaginadas(Pageable pr) {
		
		return facultad_repository.recycleBin(pr);
	}

	public Page<Facultad> getFacultadesPaginadasPorNombre(Long nombre, PageRequest pageRequest) {
		
		return null;
	}

	public Page<Facultad> getFacultadesPorWeb(String tagName, Pageable pagerequest) {
		
		return facultad_repository.getFacultadesPorWeb(tagName, pagerequest);
	}
	
	public Page<Facultad> getFacultadesEliminadasPorWeb(String tagName, Pageable pagerequest) {
			
			return facultad_repository.getFacultadesEliminadasPorWeb(tagName, pagerequest);
	}
	
	public Page<Facultad> getFacultadesPaginadas(PageRequest pageRequest) {
		return facultad_repository.findAll(pageRequest);
	}
	
	public void eliminarFacultad(Long idFacultad) {
		facultad_repository.softDelete(idFacultad);	
	}
	
	public List<Facultad> getFacultadesEliminadas() {		
		return facultad_repository.recycleBin();
	}
	
	public Facultad save(Facultad f){
		return facultad_repository.save(f);

	}
}

