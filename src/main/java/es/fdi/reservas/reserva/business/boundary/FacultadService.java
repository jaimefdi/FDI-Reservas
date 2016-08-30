package es.fdi.reservas.reserva.business.boundary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import es.fdi.reservas.reserva.business.control.FacultadRepository;
import es.fdi.reservas.reserva.business.entity.Facultad;
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
