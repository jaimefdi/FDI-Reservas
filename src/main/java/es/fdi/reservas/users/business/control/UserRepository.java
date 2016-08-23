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
	public List<User> recycleBin();
	
	@Query("from User u where lower(u.username) like lower(concat('%',:username, '%'))")
	List<User> getUsuariosPorTagName(@Param("username") String username);

	@Query("select e from #{#entityName} e where e.enabled=true and e.facultad.nombreFacultad = :nombre")
	public List<User> getUsuariosPorFacultad(@Param("nombre") String nombre);

	@Query("from User u where lower(u.email) like lower(concat('%',:email, '%'))")
	public List<User> getUsuariosPorEmail(@Param("email") String email);
	
	
}
