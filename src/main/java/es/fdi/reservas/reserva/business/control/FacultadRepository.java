package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.fdi.reservas.reserva.business.entity.Facultad;

@Repository
public interface FacultadRepository extends CrudRepository<Facultad, Long>{

	@Query("from Facultad f where lower(f.nombreFacultad) like lower(concat('%',:nombreFacultad, '%'))")
	List<Facultad> getFacultadesPorTagName(@Param("nombreFacultad") String nombreFacultad);

}
