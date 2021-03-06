package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.fdi.reservas.reserva.business.entity.Facultad;

@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Long>{

	@Query("from Facultad f where lower(f.nombreFacultad) like lower(concat('%',:nombreFacultad, '%')) and f.deleted=false")
	List<Facultad> getFacultadesPorTagName(@Param("nombreFacultad") String nombreFacultad);
	
	@Query("from #{#entityName} f where f.deleted=false")
	List<Facultad> findAll();

	@Query("select e from #{#entityName} e where e.deleted=true")
	List<Facultad> recycleBin();
	
	@Modifying
	@Query("update #{#entityName} e set e.deleted=true where e.id= :idFacultad")
	void softDelete(@Param("idFacultad") Long idFacultad);
}
