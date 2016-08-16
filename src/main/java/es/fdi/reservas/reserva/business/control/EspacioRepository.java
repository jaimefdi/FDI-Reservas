package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.TipoEspacio;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long>{

	@Query("select f from #{#entityName} f where f.deleted=false and f.edificio.id = :idEdificio")
	public List<Espacio> findByEdificioId(@Param("idEdificio") Long idEdificio);
	
	//@Query("select f from #{#entityName} f where f.deleted=false and f.id = :id and f.edificio.id = :idEdificio")
	public List<Espacio> findById(Long Id); 

	@Query("select f from #{#entityName} f where f.deleted=false and f.edificio.id = :idEdificio and f.tipoEspacio = :tipoEspacio")
	public List<Espacio> findByEdificioIdAndTipoEspacio(@Param("idEdificio") Long idEdificio, @Param("tipoEspacio") TipoEspacio tipoEspacio);
	
	//public List<Espacio> findByNombre_espacio(String nombre);
	
	@Query("SELECT DISTINCT e.tipoEspacio FROM Espacio e WHERE e.edificio.id = :idEdificio")
	public List<TipoEspacio> tiposDeEspacios(@Param("idEdificio") long idEdificio);
	
	@Query("select f from #{#entityName} f where f.deleted=false")
	List<Espacio> findAll();

	@Query("select e from #{#entityName} e where e.deleted=true")
	List<Espacio> recycleBin();
	
	@Modifying
	@Query("update #{#entityName} e set e.deleted=true where e.id= :idEspacio")
	void softDelete(@Param("idEspacio") String idEspacio);
	
	@Query("from #{#entityName} e where lower(e.nombreEspacio) like lower(concat('%',:nombreEspacio, '%'))")
	List<Espacio> getEspaciosByTagName(@Param("nombreEspacio") String nombreEspacio);
	
//	@Query("FROM #{#entityName} e WHERE e.EdificioId IN( SELECT EdificioId FROM #{#edificio} d WHERE d.EdificioId = e.EdificioId AND d.FacultadId IN( SELECT facultad.FacultadId FROM #{#facultad} f WHERE f.FacultadId = d.FacultadId AND f.nombreFacultad = :nombreFacultad ) )")
//	List<Espacio> getEspacioPorFacultad(@Param("nombreFacultad") String nombreFacultad);
}
