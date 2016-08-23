package es.fdi.reservas.users.web;

import java.util.ArrayList;
import java.util.Collection;
import es.fdi.reservas.users.business.entity.User;
import es.fdi.reservas.users.business.entity.UserRole;

public class UserDTO {
	
	private Long id;
	
	private String username;
	
	private String password;
	
	private String oldPassword, newPassword;
	
	private String email;
	
	private boolean enabled;
	
	
	private Collection<UserRole> roles;
	
	private Long facultad;
	
	private String imagen;
	
	//private Set<Facultad> facultades;
	
	public UserDTO(){}
	
	public UserDTO(Long id, String username, String email, Long facultad, String imagen) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.facultad = facultad;
		this.imagen = imagen;
	}



	public UserDTO(String username, String email, boolean enabled, Long facultad, String imagen) {
		this.username = username;
		this.email = email;
		this.enabled = enabled;
		this.roles = new ArrayList<UserRole>();
		this.facultad = facultad;
		this.imagen = imagen;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String user_password){
		password = user_password;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String userName){
		username = userName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}
	
	public Long getFacultad() {
		return facultad;
	}

	public void setFacultad(Long facultad) {
		this.facultad = facultad;
	}

	public Collection<UserRole> getRoles() {
		return roles;
	}

	public void addRole(UserRole role) {
		this.roles.add(role);
	}
	
	public void removeRole(UserRole role) {
		this.roles.remove(role);
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean en){
		this.enabled = en;
	}

	public void setRoles(Collection<UserRole> roles) {
		this.roles = roles;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getOldPassword() {
		return oldPassword;
	}



	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}



	public String getNewPassword() {
		return newPassword;
	}



	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}



	public static UserDTO fromUserDTO(User user){
		return new UserDTO(user.getUsername(), user.getEmail(), user.isEnabled(), user.getFacultad().getId(), user.getImagen().getAttachmentUrl());
	}
	
	public static UserDTO fromUserDTOAutocompletar(User user){
		return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFacultad().getId(), user.getImagen().getAttachmentUrl());
	}
}
