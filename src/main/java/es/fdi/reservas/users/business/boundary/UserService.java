package es.fdi.reservas.users.business.boundary;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import es.fdi.reservas.reserva.business.boundary.GrupoReservaService;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.reserva.business.entity.EstadoReserva;
import es.fdi.reservas.reserva.business.entity.GrupoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;
import es.fdi.reservas.users.business.control.UserRepository;
import es.fdi.reservas.users.business.entity.User;
import es.fdi.reservas.users.web.UserDTO;
import es.fdi.reservas.users.business.entity.UserRole;


@Service
public class UserService implements UserDetailsService{

	private UserRepository user_ropository;
	private PasswordEncoder password_encoder;
	private ReservaService reserva_service;
	
	@Autowired
	public UserService(UserRepository ur, PasswordEncoder pe){
		user_ropository = ur;
		password_encoder = pe;
		//reserva_service = rs;
	}
	
	@Autowired @Lazy
	public void setReservaService(ReservaService rs){
		reserva_service = rs;
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
	
	public User editarUserDeleted(Long idUsuario){
		User u = getUser(idUsuario);
		u.setEnabled(false);
		
		return user_ropository.save(u);
	}

	public User editaUsuario(UserDTO userActualizado, String user, String admin, String secre) {
		
		User u = getUser(userActualizado.getId());
		u.setUsername(userActualizado.getUsername());
		u.setEmail(userActualizado.getEmail());
		u.setEnabled(userActualizado.isEnabled());
		if (user.equals("1") || admin.equals("1") || secre.equals("1")){//si hay alguno seleccionado
			u.getAuthorities().clear();
			if (user.equals("1")){
				u.addRole(new UserRole("ROLE_USER"));
			}
			if (admin.equals("1")){
				u.addRole(new UserRole("ROLE_ADMIN"));
			}
			if (secre.equals("1")){
				u.addRole(new UserRole("ROLE_SECRE"));
			}
		}
		return user_ropository.save(u);
		
		
	}
	
	public Page<User> getUsuariosPaginados(PageRequest pageRequest) {
		return user_ropository.findAll(pageRequest);
	}

	public User restaurarUser(long idUsuario) {
		User u = getUser(idUsuario);
		u.setEnabled(true);
		
		return user_ropository.save(u);		
	}

	public List<User> getEliminados() {		
		return user_ropository.recycleBin();
	}


	public List<User> getUsuariosPorTagName(String tagName) {
		return user_ropository.getUsuariosPorTagName(tagName);
	}

	public void editarPerfil(UserDTO userDTO) {
		User user = getUser(userDTO.getId());
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		if(userDTO.getOldPassword() != null){
			// Cambiar las contraseñas
			if(password_encoder.matches(userDTO.getOldPassword(),user.getPassword())){
				user.setPassword(password_encoder.encode(userDTO.getNewPassword()));
			}
			else{
				// Error: las contraseñas no coinciden
				throw new UserPasswordException("La contraseña actual no es correcta");
			}
		}
		
		// Actualiza el usuario actual sin cerrar sesión
		Authentication request = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());		
		SecurityContextHolder.getContext().setAuthentication(request);		
		
		// Guarda los cambios en la base de datos
		user_ropository.save(user);
	}

	public List<Reserva> reservasPendientesUsuario(Long idUsuario, EstadoReserva estadoReserva) {
		return reserva_service.reservasPendientesUsuario(idUsuario, estadoReserva);
	}

	public Iterable<Espacio> getEspacios() {
		return reserva_service.getEspacios();
	}

	public List<GrupoReserva> getGruposUsuario(Long idUsuario) {
		return reserva_service.getGruposUsuario(idUsuario);
	}
	
	

}
