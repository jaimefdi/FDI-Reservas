package es.fdi.reservas.users.business.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.fdi.reservas.reserva.business.entity.Facultad;
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
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="UserFacultades", joinColumns={@JoinColumn(name="user")}, inverseJoinColumns={@JoinColumn(name="FACULTAD_ID")})
	private Set<Facultad> facultades;
	

	public User() {
		
	}
	
	public User(String username, String email) {
		this.username = username;
		this.email = email;
		this.enabled = true;
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
	
	
}
