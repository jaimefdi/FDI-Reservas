package es.fdi.reservas.users.business.boundary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.fdi.reservas.fileupload.business.control.AttachmentRepository;
import es.fdi.reservas.fileupload.business.entity.Attachment;
import es.fdi.reservas.reserva.business.control.FacultadRepository;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.users.business.control.UserRepository;
import es.fdi.reservas.users.business.entity.User;
import es.fdi.reservas.users.web.UserDTO;
import es.fdi.reservas.users.business.entity.UserRole;

@Service
public class UserService implements UserDetailsService{

	private UserRepository user_ropository;
	
	private PasswordEncoder password_encoder;
	
	private FacultadRepository facultad_repository;
	
	private AttachmentRepository attachment_repository;
	
	@Autowired
	public UserService(UserRepository usuarios, PasswordEncoder passwordEncoder, FacultadRepository fr, AttachmentRepository ar){
		user_ropository = usuarios;
		password_encoder = passwordEncoder;
		facultad_repository = fr;
		attachment_repository = ar;
	}
	
	public User getUser(Long idUsuario) {
		return user_ropository.findOne(idUsuario);
	}
	
	/*public List<User> getUsers(String idUsuario) {
		return user_ropository.findByUsername(idUsuario);
	}*/
	
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
		User newUser = new User(user.getUsername(), user.getEmail(), user.getImagen());
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
	
	public User editarUserDeleted(Long idUser){
		User f = user_ropository.findOne(idUser);
		f.setEnabled(false);
		return user_ropository.save(f);
	}

	public User editaUsuario(UserDTO userActualizado, String user, String admin, String gestor, Attachment imagen) {
		
		User u = user_ropository.findOne(userActualizado.getId());
		u.setUsername(userActualizado.getUsername());
		u.setEmail(userActualizado.getEmail());
		Facultad fac = facultad_repository.findOne(userActualizado.getFacultad());
		u.setFacultad(fac);
		u.setImagen(imagen);
		attachment_repository.save(imagen);
		if (user.equals("user") || admin.equals("admin") || gestor.equals("gestor")){//si hay alguno seleccionado
			//u.getAuthorities().clear();
			if (user.equals("user")){
				u.addRole(new UserRole("ROLE_USER"));
			}
			if (admin.equals("admin")){
				u.addRole(new UserRole("ROLE_ADMIN"));
			}
			if (gestor.equals("gestor")){
				u.addRole(new UserRole("ROLE_GESTOR"));
			}
		}
		return user_ropository.save(u);
		
		
	}
	
	public Page<User> getUsuariosPaginados(PageRequest pageRequest) {
		return user_ropository.findAll(pageRequest);
	}

	public User restaurarUser(long idUser) {
		User f = user_ropository.findOne(idUser);
		f.setEnabled(true);
		return user_ropository.save(f);		
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
	
	public Attachment getAttachment(Long idAttachment){
		return attachment_repository.getOne(idAttachment);
	}

	public List<Attachment> getAttachmentByName(String img) {
		return attachment_repository.getAttachmentByName(img);
	}

	public Page<User> getUsuariosPorEmail(String email, Pageable pagerequest) {
		return user_ropository.getUsuariosPorEmail(email, pagerequest);
	}

	public Page<User> getUsuariosPorNombre(String nombre, Pageable pagerequest) {
		return user_ropository.getUsuariosPorTagName(nombre, pagerequest);
	}

	public Page<User> getUsuariosPorFacultad(String nombre, Pageable pagerequest) {
		// TODO Auto-generated method stub
		return user_ropository.getUsuariosPorFacultad(nombre, pagerequest);
	}

	public void actualizaReferencias(String nombreViejo, String nombreNuevo) {
		List<Attachment> imgs = attachment_repository.getAttachmentByName(nombreViejo);
		Iterator<Attachment> it = imgs.iterator();
		Attachment i;
		while (it.hasNext()){
			i = it.next();
			i.setAttachmentUrl(i.getAttachmentUrl().replace(nombreViejo, nombreNuevo));
			attachment_repository.save(i);
		}
		
	}

	

}
