package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.fdi.reservas.reserva.business.entity.Facultad;

@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Long>{

	@Query("from Facultad f where lower(f.nombreFacultad) like lower(concat('%',:nombreFacultad, '%'))")
	Page<Facultad> getFacultadesPorTagName(@Param("nombreFacultad") String nombreFacultad, Pageable pagerequest);
	
	@Query("from Facultad f where f.deleted=true and lower(f.nombreFacultad) like lower(concat('%',:nombreFacultad, '%'))")
	Page<Facultad> getFacultadesEliminadasPorTagName(@Param("nombreFacultad") String nombreFacultad, Pageable pagerequest);
	
	@Query("from Facultad f where lower(f.nombreFacultad) like lower(concat('%',:nombreFacultad, '%'))")
	List<Facultad> getFacultadesPorTagName(@Param("nombreFacultad") String nombreFacultad);
	
	@Query("from Facultad f where f.nombreFacultad = :nombre")
	Facultad getFacultadesPorNombre(@Param("nombre") String nombre);
	
	@Query("from Facultad f where f.id= :idFacultad")
	Facultad getFacultadPorId(@Param("idFacultad") Long idFacultad);
	
	@Query("from #{#entityName} f where f.deleted=false")
	List<Facultad> findAll();

	@Query("select e from #{#entityName} e where e.deleted=true")
	Page<Facultad> recycleBin(Pageable page);
	
	@Query("select e from #{#entityName} e where e.deleted=true")
	List<Facultad> recycleBin();
	
	@Modifying
	@Query("update #{#entityName} e set e.deleted=true where e.id= :idFacultad")
	void softDelete(@Param("idFacultad") Long idFacultad);

	@Query("from Facultad f where f.webFacultad like lower(concat('%',:nombre, '%'))")
	Page<Facultad> getFacultadesPorWeb(@Param("nombre") String nombre, Pageable pagerequest);
	
	@Query("from Facultad f where f.deleted=true and f.webFacultad like lower(concat('%',:nombre, '%'))")
	Page<Facultad> getFacultadesEliminadasPorWeb(@Param("nombre") String nombre, Pageable pagerequest);
	
	@Query("from Facultad f where f.webFacultad like lower(concat('%',:nombre, '%'))")
	List<Facultad> getFacultadesPorWeb(@Param("nombre") String nombre);
}
