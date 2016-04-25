package es.fdi.reservas.reserva.business.control;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.TipoEspacio;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long>{

	public List<Espacio> findByEdificioId(Long idEdificio);
	
	public List<Espacio> findByEdificioIdAndTipoEspacio(Long idEdificio, TipoEspacio idTipoEspacio);
	
	//public List<Espacio> findByNombre_espacio(String nombre);
	
	@Query("SELECT DISTINCT e.tipoEspacio FROM Espacio e WHERE e.edificio.id = :idEdificio")
	public List<TipoEspacio> tiposDeEspacios(@Param("idEdificio") long idEdificio);
}
