package es.fdi.reservas.reserva.business.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import es.fdi.reservas.users.business.entity.User;

@Entity
public class GrupoReserva {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="GrupoReservaId")
	private Long id;
	
	@NotNull
	private String nombreGrupo;
	
	private String descripcion;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="grupoReserva")
	private Set<Reserva> reservasGrupo;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="UserId")
	private User user;
	
	public GrupoReserva(){
		
	}
	
	public GrupoReserva(String nombre, String des, User user){
		this.nombreGrupo = nombre;
		this.descripcion = des;
		this.reservasGrupo = new HashSet<Reserva>();
		this.user = user;
	}


	public void addReserva(Reserva reserva){
		reservasGrupo.add(reserva);
	}
	
	public Long getId() {
		return id;
	}


	public String getNombreGrupo() {
		return nombreGrupo;
	}


	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Set<Reserva> getReservasGrupo() {
		return reservasGrupo;
	}


	public void setReservasGrupo(Set<Reserva> reservasGrupo) {
		this.reservasGrupo = reservasGrupo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
}
