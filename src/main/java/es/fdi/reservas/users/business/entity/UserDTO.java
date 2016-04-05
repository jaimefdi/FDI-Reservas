package es.fdi.reservas.users.business.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import es.fdi.reservas.reserva.business.entity.Facultad;

public class UserDTO {

	
	private Long id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private boolean accountExpired;

	private boolean accountLocked;
	
	private boolean credentialsExpired;
	
	private boolean enabled;
	
	
	private Collection<UserRole> roles;
	
	
	private Set<Facultad> facultades;
	
	public UserDTO(){}
	
	public UserDTO(String username, String email, boolean enabled) {
		this.username = username;
		this.email = email;
		this.enabled = enabled;
		this.roles = new ArrayList<UserRole>();
		this.facultades = new HashSet<Facultad>();
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
	
	public void addRole(UserRole role) {
		this.roles.add(role);
	}
	
	public void removeRole(UserRole role) {
		this.roles.remove(role);
	}

		
	public boolean isAccountNonExpired() {
		return ! accountExpired;
	}

	
	public boolean isAccountNonLocked() {
		return ! accountLocked;
	}

	
	public boolean isCredentialsNonExpired() {
		return ! credentialsExpired;
	}

	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean en){
		this.enabled = en;
	}

	public Set<Facultad> getFacultades() {
		return facultades;
	}

	public void setFacultades(Set<Facultad> facultades) {
		this.facultades = facultades;
	}
	
	public void addFacultad(Facultad f){
		this.facultades.add(f);
	}
	
	public void removeFacultad(Facultad f){
		this.facultades.remove(f);
	}
	
	public static UserDTO fromUserDTO(User user){
		return new UserDTO(user.getUsername(), user.getEmail(), user.isEnabled());
	}
}
