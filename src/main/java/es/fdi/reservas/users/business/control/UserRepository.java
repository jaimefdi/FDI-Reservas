package es.fdi.reservas.users.business.control;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import es.fdi.reservas.users.business.entity.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	public UserDetails findByEmail(String username);

	public UserDetails findByUsername(String username);
}
