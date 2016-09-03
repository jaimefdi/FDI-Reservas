package es.fdi.reservas.users.business.control;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("select e from #{#entityName} e where e.enabled=true")
	public List<User> findAll();
	
	public UserDetails findByEmail(String username);
	
	//public Page<User> findByUserName(long userName, Pageable pageable);
	
	//public Page<User> findByUserId(long idUsuario, Pageable pageable); 
	
	public UserDetails findByUsername(String username);

	@Query("select e from #{#entityName} e where e.enabled=false")
	public Page<User> recycleBin(Pageable pagerequest);
	
	@Query("from User u where lower(u.username) like lower(concat('%',:username, '%'))")
	Page<User> getUsuariosPorTagName(@Param("username") String username, Pageable pagerequest);
	
	@Query("from User u where u.enabled=false and lower(u.username) like lower(concat('%',:username, '%'))")
	Page<User> getUsuariosEliminadosPorTagName(@Param("username") String username, Pageable pagerequest);
	
	@Query("from User u where lower(u.username) like lower(concat('%',:username, '%'))")
	List<User> getUsuariosPorTagName(@Param("username") String username);

	@Query("select e from #{#entityName} e where e.enabled=true and e.facultad.nombreFacultad like lower(concat('%',:nombre, '%'))")
	Page<User> getUsuariosPorFacultad(@Param("nombre") String nombre, Pageable pagerequest);
	
	@Query("select e from #{#entityName} e where e.enabled=false and e.facultad.nombreFacultad like lower(concat('%',:nombre, '%'))")
	Page<User> getUsuariosEliminadosPorFacultad(@Param("nombre") String nombre, Pageable pagerequest);

	@Query("from User u where lower(u.email) like lower(concat('%',:email, '%'))")
	Page<User> getUsuariosPorEmail(@Param("email") String email, Pageable pagerequest);
	
	@Query("from User u where u.enabled=false and lower(u.email) like lower(concat('%',:email, '%'))")
	Page<User> getUsuariosEliminadosPorEmail(@Param("email") String email, Pageable pagerequest);
	
	@Query("select e from #{#entityName} e where e.enabled=true and e.facultad.nombreFacultad like lower(concat('%',:nombre, '%'))")
	List<User> getUsuariosPorFacultad(@Param("nombre") String nombre);

	@Query("from User u where lower(u.email) like lower(concat('%',:email, '%'))")
	List<User> getUsuariosPorEmail(@Param("email") String email);
	
}
