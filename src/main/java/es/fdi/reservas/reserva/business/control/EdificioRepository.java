package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import es.fdi.reservas.reserva.business.entity.Edificio;

@Repository
public interface EdificioRepository extends JpaRepository<Edificio, Long>{

	
	@Query("select f from #{#entityName} f where f.deleted=false")
	List<Edificio> findAll();

	@Query("select e from #{#entityName} e where e.deleted=true")
	List<Edificio> recycleBin();
	
	@Modifying
	@Query("update #{#entityName} e set e.deleted=true where e.id= :idEdificio")
	void softDelete(@Param("idEdificio") String idEdificio);

	public List<Edificio> findByFacultadId(Long idFacultad);

	@Query("from Edificio f where f.deleted=false and lower(f.nombreEdificio) like lower(concat('%',:nombreEdificio, '%'))")
	List<Edificio> getEdificiosPorTagName(@Param("nombreEdificio") String nombreEdificio);
	
	@Query("from Edificio f where f.deleted=false and lower(f.nombreEdificio) like lower(concat('%',:nombreEdificio, '%'))")
	Page<Edificio> getEdificiosPorTagName(@Param("nombreEdificio") String nombreEdificio, Pageable pagerequest);

	@Query("from Edificio f where f.deleted=true and lower(f.nombreEdificio) like lower(concat('%',:nombreEdificio, '%'))")
	Page<Edificio> getEdificiosEliminadosPorTagName(@Param("nombreEdificio") String nombreEdificio, Pageable pagerequest);
	
//	@Query("from Edificio f where lower(f.direccion) like lower(concat('%',:direccion, '%'))")
//	List<Edificio> getEdificiosPorDireccion(@Param("direccion") String tagName);
	
	@Query("from Edificio f where f.deleted=false and lower(f.direccion) like lower(concat('%',:direccion, '%'))")
	Page<Edificio> getEdificiosPorDireccion(@Param("direccion") String tagName, Pageable pagerequest);
	
	@Query("from Edificio f where lower(f.direccion) like lower(concat('%',:direccion, '%'))")
	Page<Edificio> getEdificiosEliminadosPorDireccion(@Param("direccion") String tagName, Pageable pagerequest);
	
//	@Query("select e from #{#entityName} e where e.deleted=false and e.facultad.nombreFacultad like lower(concat('%',:nombre, '%'))")
//	List<Edificio> getEdificiosPorFacultad(@Param("nombre") String nombre);
	
	@Query("select e from #{#entityName} e where e.facultad.nombreFacultad like lower(concat('%',:nombre, '%'))")
	Page<Edificio> getEdificiosEliminadosPorFacultad(@Param("nombre") String nombre, Pageable pagerequest);
	
	@Query("select e from #{#entityName} e where e.deleted=false and e.facultad.nombreFacultad like lower(concat('%',:nombre, '%'))")
	Page<Edificio> getEdificiosPorFacultad(@Param("nombre") String nombre, Pageable pagerequest);
	
	@Query("select e from #{#entityName} e where e.deleted=false and e.nombreEdificio = :edificio")
	Edificio getEdificiosPorNombre(@Param("edificio") String edificio);
		
}
