package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import es.fdi.reservas.reserva.business.entity.Reserva;


@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Long>{

	public List<Reserva> findByUsername(String username); 
	
	//public List<Reserva> findBySpacename(String spacename); 
	
	public List<Reserva> findAll();

	public List<Reserva> findByEspacio_Id(long id_espacio); 
	
	public List<Reserva> findByEstado(boolean estado);
	
}
