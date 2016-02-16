package es.fdi.reservas.users.business.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import org.springframework.security.core.GrantedAuthority;

@Embeddable
public class UserRole implements GrantedAuthority, Serializable {

	@Basic
	private String role;
	
	UserRole() {
		
	}
	
	public UserRole(String role) {
		this.role = role;
	}
	
	public String getRole(){
		return this.role;
	}
	
	public String getAuthority() {
		return this.role;
	}

}
