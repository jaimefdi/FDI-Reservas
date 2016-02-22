package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.TipoEspacio;

@Repository
public interface EspacioRepository extends CrudRepository<Espacio, Long>{

	public List<Espacio> findByEdificio_Id(Long id_edif);
	
	public List<Espacio> findByEdificio_IdAndTipoEspacio(Long id_edif, TipoEspacio id_tipoEspacio);
	
}
