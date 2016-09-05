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
	
	// Reservas de un usuario
	public List<Reserva> findByUserId(long idUsuario);
	// Reservas no denegadas de un usuario
	@Query("FROM Reserva r WHERE (r.user.id = :idUsuario) AND (r.estadoReserva <> 2)")
	public List<Reserva> misReservasCalendario(@Param("idUsuario") long idUsuario);
	// Reservas que no estén denegadas de un espacio
	@Query("FROM Reserva r WHERE (r.espacio.id = :idEspacio) AND (r.estadoReserva <> 2)")
	public List<Reserva> reservasEspacio(@Param("idEspacio") long idEspacio);
	// Reservas paginadas de un usuario
	public Page<Reserva> findByUserId(long idUsuario, Pageable pageable); 
	// Reservas paginadas de un edificio
	public Page<Reserva> findByEspacioId(long idEspacio, Pageable pageable); 
	
	public List<Reserva> findByEspacioId(long idEspacio);
	
	@Query("FROM Reserva r WHERE (r.espacio.id = :idEspacio) AND (r.estadoReserva <> 2) AND (( :start BETWEEN r.comienzo AND r.fin) OR ( :end BETWEEN r.comienzo AND r.fin ) OR (r.comienzo BETWEEN :start AND :end) OR (r.fin BETWEEN :start AND :end) )")
	public List<Reserva> reservasConflictivas(@Param("idEspacio")Long idEspacio, @Param("start") DateTime start, @Param("end") DateTime end);
	
	@Query("FROM Reserva r WHERE (r.espacio.id = :idEspacio) AND (r.estadoReserva <> 2) AND ((r.endRecurrencia > :startRecurrencia) OR (r.startRecurrencia < :endRecurrencia))")
	public List<Reserva> reservasRecurrentes(@Param("idEspacio")Long idEspacio, @Param("startRecurrencia") DateTime startRecurrencia, @Param("endRecurrencia") DateTime endRecurrencia);
	
	@Query("FROM Reserva r WHERE (r.espacio.id = :idEspacio) AND (r.estadoReserva <> 2) AND (DATE_FORMAT(r.comienzo,'%H:%i') < '15:00')")
	public List<Reserva> reservasEspacioDeMañana(@Param("idEspacio")Long idEspacio);
	
	@Query("FROM Reserva r WHERE (r.espacio.id = :idEspacio) AND (r.estadoReserva <> 2) AND (DATE_FORMAT(r.comienzo,'%H:%i') >= '15:00')")
	public List<Reserva> reservasEspacioDeTarde(@Param("idEspacio")Long idEspacio);

	@Query("FROM Reserva r WHERE (r.user.id = :idUsuario) AND (r.grupoReserva.id = :idGrupo) AND (r.estadoReserva <> 2)")
	public List<Reserva> reservasGrupoUsuario(@Param("idGrupo") Long idGrupo, @Param("idUsuario") Long idUsuario);
	
	@Query("FROM Reserva r WHERE (r.user.id = :idUsuario) AND (r.estadoReserva = :estado)")
	public List<Reserva> reservasPendientesUsuario(@Param("idUsuario") Long idUsuario,@Param("estado") EstadoReserva estado);
	
	
}
