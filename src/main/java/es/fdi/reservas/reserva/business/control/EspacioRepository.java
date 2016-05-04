package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.TipoEspacio;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long>{

	@Query("select f from #{#entityName} f where f.deleted=false and f.id = :id_edif")
	public List<Espacio> findByEdificioId(Long idEdificio);
	
	@Query("select f from #{#entityName} f where f.deleted=false and f.id = :id_edif")
	public List<Espacio> findByEdificioIdAndTipoEspacio(Long idEdificio, TipoEspacio idTipoEspacio);
	
	@Query("SELECT DISTINCT e.tipoEspacio FROM Espacio e WHERE e.edificio.id = :idEdificio")
	public List<TipoEspacio> tiposDeEspacios(@Param("idEdificio") long idEdificio);
	
	@Query("select f from #{#entityName} f where f.deleted=false")
	List<Espacio> findAll();

	@Query("select e from #{#entityName} e where e.deleted=true")
	List<Espacio> recycleBin();
	
	@Modifying
	@Query("update #{#entityName} e set e.deleted=true where e.id= :idEspacio")
	void softDelete(@Param("idEspacio") String idEspacio);
}
