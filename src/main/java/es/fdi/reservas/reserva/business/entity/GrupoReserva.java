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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

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
	
	public GrupoReserva(){
		
	}
	
	public GrupoReserva(String nombre, String des){
		this.nombreGrupo = nombre;
		this.descripcion = des;
		reservasGrupo = new HashSet<Reserva>();
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
	
	
	
	
	
}
