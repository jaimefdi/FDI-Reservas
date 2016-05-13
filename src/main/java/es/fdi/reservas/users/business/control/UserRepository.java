package es.fdi.reservas.users.business.control;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("select e from #{#entityName} e where e.enabled=true")
	public List<User> findAll();
	
	public UserDetails findByEmail(String username);

	public UserDetails findByUsername(String username);

	@Query("select e from #{#entityName} e where e.enabled=false")
	public List<User> recycleBin();
	
	@Query("from User u where lower(u.username) like lower(concat('%',:username, '%'))")
	List<User> getUsuariosPorTagName(@Param("username") String username);
}
