package es.fdi.reservas.reserva.business.boundary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import es.fdi.reservas.reserva.business.control.EdificioRepository;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;


@Service
public class EdificioService {

	private EdificioRepository edificio_repository;
	private UserService user_service;
	private FacultadService facultad_service;
	
	@Autowired
	public EdificioService(EdificioRepository er, UserService us, FacultadService fs){
		edificio_repository = er;
		user_service = us;
		facultad_service = fs;
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
	
}
