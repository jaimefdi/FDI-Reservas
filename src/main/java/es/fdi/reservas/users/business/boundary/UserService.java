package es.fdi.reservas.users.business.boundary;

import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	
	private AttachmentRepository attachment_repository;

	private FacultadRepository facultad_repository;
	
	@Autowired
	public UserService(UserRepository usuarios, PasswordEncoder passwordEncoder, AttachmentRepository ar, FacultadRepository fr){
		user_ropository = usuarios;
		password_encoder = passwordEncoder;
		attachment_repository = ar;
		facultad_repository = fr;
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

	public User editarUserDeleted(Long idUser){
		User f = user_ropository.findOne(idUser);
		if (!f.isEnabled()){//si ya esta eliminado
			JOptionPane.showMessageDialog(null, "El usuario ya está eliminado", "Información", JOptionPane.OK_CANCEL_OPTION);
			return f;
		}else{
			f.setEnabled(false);
			return user_ropository.save(f);
		}
		
	}
	
	public User addNewUser(User user){
		User newUser = new User(user.getUsername(), user.getEmail(), user.getImagen());
		newUser.addRole(new UserRole("ROLE_USER"));
		newUser.setPassword(password_encoder.encode(user.getPassword()));
		Attachment img = user.getImagen();
		if (img == null){
			//img = attachment_repository.getAttachmentByName("casa").get(0);
			img = attachment_repository.findOne((long) 2);
			newUser.setImagen(img);
			//attachment_repository.save(img);
		}
		
		Facultad fac = user.getFacultad();
		if (fac == null){
			fac = facultad_repository.findOne((long) 27);
			newUser.setFacultad(fac);
			//facultad_repository.save(fac);
		}
		//newUser.setImagen(user.getImagen());
		newUser = user_ropository.save(newUser);
		
		return newUser;
	}
	
	public Iterable<User> getUsuarios() {
		return user_ropository.findAll();
	}
	
	public void eliminarUsuario(long idUser) {
		user_ropository.delete(idUser);
	}

	public User editaUsuario(UserDTO userActualizado, String user, String admin, String gestor, Attachment imagen) {
		
		User u = user_ropository.findOne(userActualizado.getId());
		u.setUsername(userActualizado.getUsername());
		u.setEmail(userActualizado.getEmail());
		//Facultad fac = reserva_service.getFacultad((long) userActualizado.getFacultad());
		//u.setFacultad(fac);
		u.setImagen(imagen);
		attachment_repository.save(imagen);
		if (user.equals("user") || admin.equals("admin") || gestor.equals("gestor")){//si hay alguno seleccionado
			u.getAuthorities().clear();
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
	
	public List<User> getUsuariosPorFacultad(String nombreFacultad){
		return user_ropository.getUsuariosPorFacultad(nombreFacultad);
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
	
	public Attachment getAttachment(Long idAttachment){
		return attachment_repository.getOne(idAttachment);
	}

	public List<Attachment> getAttachmentByName(String img) {
		return attachment_repository.getAttachmentByName(img);
	}

}
