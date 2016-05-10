package es.fdi.reservas.reserva.business.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.fdi.reservas.users.business.entity.User;

@Entity
public class GrupoReserva {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="GrupoReservaId")
	private Long id;
	
	@NotNull @Size(min=1, max=15)
	private String nombreCorto;
	
	@NotNull @Size(min=1, max=30)
	private String nombreLargo;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="grupoReserva")
	private Set<Reserva> reservasGrupo;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="UserId")
	private User user;
	
	public GrupoReserva(){
		
	}
	
	public GrupoReserva(String nombreCorto, String nombreLargo, User user){
		this.nombreCorto = nombreCorto;
		this.nombreLargo = nombreLargo;
		this.reservasGrupo = new HashSet<Reserva>();
		this.user = user;
	}


	public void addReserva(Reserva reserva){
		reservasGrupo.add(reserva);
	}
	
	public Long getId() {
		return id;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getNombreLargo() {
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
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
