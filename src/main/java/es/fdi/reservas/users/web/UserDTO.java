package es.fdi.reservas.users.web;

import es.fdi.reservas.users.business.entity.User;

public class UserDTO {
	
	private Long id;
	private String username;
	private String email;
	
	public UserDTO(Long id, String nombre, String email){
		this.id = id;
		this.username = nombre;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public static UserDTO fromUserDTO(User u){
		return new UserDTO(u.getId(), u.getUsername(), u.getEmail());
	}
}
