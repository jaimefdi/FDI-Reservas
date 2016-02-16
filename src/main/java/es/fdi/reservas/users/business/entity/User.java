package es.fdi.reservas.users.business.entity;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.fdi.reservas.reserva.business.entity.Reserva;

@Entity
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="User_Id")
	private Long id;
	@Size(min=3, max=20, message="La longitud debe estar entre 3 y 20")
	private String username;
	
	private String password;
	@Email
	private String email;
	
	private boolean accountExpired;

	private boolean accountLocked;
	
	private boolean credentialsExpired;
	
	private boolean enabled;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="UserRole", joinColumns=@JoinColumn(name="user"),  uniqueConstraints=@UniqueConstraint(columnNames={"user", "role"}))
	private Collection<UserRole> roles;
	


	User() {
		
	}
	
	public User(String username, String email) {
		this.username = username;
		this.email = email;
		this.enabled = true;
		this.roles = new ArrayList<UserRole>();
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

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
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
	
	
	
}
