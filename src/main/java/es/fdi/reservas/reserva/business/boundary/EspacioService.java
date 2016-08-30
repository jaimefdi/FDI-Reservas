package es.fdi.reservas.reserva.business.boundary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import es.fdi.reservas.reserva.business.control.EspacioRepository;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.TipoEspacio;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;

@Service
public class EspacioService {
	
	private EspacioRepository espacio_repository;
	private UserService user_service;
	private EdificioService edificio_service;
	
	@Autowired
	public EspacioService(EspacioRepository er, UserService us, EdificioService es){
		espacio_repository = er;
		user_service = us;
		edificio_service = es;
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
	
}
