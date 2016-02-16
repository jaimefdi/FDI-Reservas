package es.fdi.reservas.reserva.business.control;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import es.fdi.reservas.reserva.business.entity.Edificio;

@Repository
public interface EdificioRepository extends CrudRepository<Edificio, Long>{

}
