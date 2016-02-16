package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import es.fdi.reservas.reserva.business.entity.Espacio;

@Repository
public interface EspacioRepository extends CrudRepository<Espacio, Long>{

	public List<Espacio> findByEdificio_Id(Long id_edif);
}
