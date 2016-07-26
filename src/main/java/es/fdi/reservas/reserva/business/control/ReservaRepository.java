package es.fdi.reservas.reserva.business.control;

import java.util.List;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{

	public List<Reserva> findByUserId(long idUsuario);  

	public List<Reserva> findByEspacioId(long idEspacio);
	
	public Page<Reserva> findByUserId(long idUsuario, Pageable pageable); 

	public Page<Reserva> findByEspacioId(long idEspacio, Pageable pageable); 

  // http://stackoverflow.com/questions/18082276/spring-data-querying-datetime-with-only-date
	public List<Reserva> findByEspacioIdAndComienzoBetween(Long idEspacio, DateTime start, DateTime end); 

	public List<Reserva> findByEspacioIdAndFinBetween(Long idEspacio, DateTime start, DateTime end);
	
	@Query("FROM Reserva r WHERE (r.espacio.id = :idEspacio) AND (( :start BETWEEN r.comienzo AND r.fin) OR ( :end BETWEEN r.comienzo AND r.fin ) OR (r.comienzo BETWEEN :start AND :end) OR (r.fin BETWEEN :start AND :end) )")
	public List<Reserva> reservasConflictivas(@Param("idEspacio")Long idEspacio, @Param("start") DateTime start, @Param("end") DateTime end);
	
	@Query("FROM Reserva r WHERE (r.espacio.id = :idEspacio) AND ((r.endRecurrencia > :startRecurrencia) OR (r.startRecurrencia < :endRecurrencia))")
	public List<Reserva> reservasRecurrentes(@Param("idEspacio")Long idEspacio, @Param("startRecurrencia") DateTime startRecurrencia, @Param("endRecurrencia") DateTime endRecurrencia);
	
	@Query("FROM Reserva r WHERE (r.espacio.id = :idEspacio) AND (DATE_FORMAT(r.comienzo,'%H:%i') < '15:00')")
	public List<Reserva> reservasEspacioDeMañana(@Param("idEspacio")Long idEspacio);
	
	@Query("FROM Reserva r WHERE (r.espacio.id = :idEspacio) AND (DATE_FORMAT(r.comienzo,'%H:%i') >= '15:00')")
	public List<Reserva> reservasEspacioDeTarde(@Param("idEspacio")Long idEspacio);

	public List<Reserva> findByGrupoReservaIdAndUserId(Long idGrupo, Long idUsuario);
	
	@Query("FROM Reserva r WHERE (r.user.id = :idUsuario) AND (r.estadoReserva = :estado)")
	public List<Reserva> reservasPendientesUsuario(@Param("idUsuario") Long idUsuario,@Param("estado") EstadoReserva estado);
	
	
}
