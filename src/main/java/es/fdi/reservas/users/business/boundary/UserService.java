package es.fdi.reservas.users.business.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.control.UserRepository;
import es.fdi.reservas.users.business.entity.User;
import es.fdi.reservas.users.business.entity.UserDTO;
import es.fdi.reservas.users.business.entity.UserRole;


@Service
public class UserService implements UserDetailsService{

	private UserRepository user_ropository;
	
	private PasswordEncoder password_encoder;
	
	@Autowired
	public UserService(UserRepository usuarios, PasswordEncoder passwordEncoder){
		user_ropository = usuarios;
		password_encoder = passwordEncoder;
	}
	
	public User getUser(Long idUsuario) {
		return user_ropository.findOne(idUsuario);
	}
	
	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if ( principal instanceof User) {
			return (User) principal;
		}
		return null;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = user_ropository.findByEmail(username);
		if (user == null)  {
			user = user_ropository.findByUsername(username);
		}
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s not found", username));
		}
		
		return user;
	}

	public User addNewUser(User user){
		User newUser = new User(user.getUsername(), user.getEmail());
		newUser.addRole(new UserRole("ROLE_USER"));
		newUser.setPassword(password_encoder.encode(user.getPassword()));
		newUser = user_ropository.save(newUser);
		
		return newUser;
	}
	
	public Iterable<User> getUsuarios() {
		return user_ropository.findAll();
	}
	
	public void eliminarUsuario(long idUser) {
		user_ropository.delete(idUser);
	}

	public User editaUsuario(UserDTO userActualizado) {
		
		User u = user_ropository.findOne(userActualizado.getId());
		u.setUsername(userActualizado.getUsername());
		u.setEmail(userActualizado.getUsername());
		u.setEnabled(userActualizado.isEnabled());
		return user_ropository.save(u);
		
		
	}

}
