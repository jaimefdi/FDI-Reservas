package es.fdi.reservas.users.business.control;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import es.fdi.reservas.users.business.entity.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	public UserDetails findByEmail(String username);

	public UserDetails findByUsername(String username);
	
	@Query("from User u where lower(u.username) like lower(concat('%',:username, '%'))")
	List<User> getUsuariosPorTagName(@Param("username") String username);
}
