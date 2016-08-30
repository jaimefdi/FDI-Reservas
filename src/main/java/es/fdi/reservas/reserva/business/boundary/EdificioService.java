package es.fdi.reservas.reserva.business.boundary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import es.fdi.reservas.reserva.business.control.EdificioRepository;
import es.fdi.reservas.reserva.business.entity.Edificio;


@Service
public class EdificioService {

	private EdificioRepository edificio_repository;
	
	@Autowired
	public EdificioService(EdificioRepository er){
		edificio_repository = er;
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
