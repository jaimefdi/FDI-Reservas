package es.fdi.reservas.reserva.business.boundary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import es.fdi.reservas.reserva.business.control.FacultadRepository;
import es.fdi.reservas.reserva.business.entity.Facultad;


@Service
public class FacultadService {

	private FacultadRepository facultad_repository;
	
	@Autowired
	public FacultadService(FacultadRepository fr){
		facultad_repository = fr;
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
