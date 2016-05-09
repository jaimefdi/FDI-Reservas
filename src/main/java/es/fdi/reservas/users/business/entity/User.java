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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.reserva.business.entity.GrupoReserva;
import es.fdi.reservas.reserva.business.entity.Reserva;


@SuppressWarnings("serial")
@Entity
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="UserId")
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
	
	@OneToMany(mappedBy="user")
	private Set<Reserva> reservas;
	
	@OneToMany(mappedBy="user")
	private Set<GrupoReserva> gruposReservas;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="FacultadId")
	private Facultad facultad;


	public User() {
		
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
	
	
	public Set<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	

	public Set<GrupoReserva> getGruposReservas() {
		return gruposReservas;
	}

	public void setGruposReservas(Set<GrupoReserva> gruposReservas) {
		this.gruposReservas = gruposReservas;
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
	
	public void setEnabled(boolean en){
		this.enabled = en;
	}

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}
	
	
	
}
