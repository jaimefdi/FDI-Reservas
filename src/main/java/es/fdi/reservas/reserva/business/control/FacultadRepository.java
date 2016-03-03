package es.fdi.reservas.reserva.business.control;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.fdi.reservas.reserva.business.entity.Facultad;

@Repository
public interface FacultadRepository extends CrudRepository<Facultad, Long>{

}
