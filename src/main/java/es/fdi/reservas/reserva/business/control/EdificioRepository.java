package es.fdi.reservas.reserva.business.control;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.users.business.entity.User;

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

	@Query("from Edificio f where lower(f.nombreEdificio) like lower(concat('%',:nombreEdificio, '%'))")
	List<Edificio> getEdificiosPorTagName(@Param("nombreEdificio") String nombreEdificio);

	@Query("from Edificio f where lower(f.direccion) like lower(concat('%',:direccion, '%'))")
	List<Edificio> getEdificiosPorDireccion(@Param("direccion") String tagName);
	
	@Query("select e from #{#entityName} e where e.deleted=false and e.facultad.nombreFacultad like lower(concat('%',:nombre, '%'))")
	List<Edificio> getEdificiosPorFacultad(@Param("nombre") String nombre);
		
}
