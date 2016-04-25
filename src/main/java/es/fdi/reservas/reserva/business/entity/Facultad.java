package es.fdi.reservas.reserva.business.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import es.fdi.reservas.users.business.entity.User;

@Entity
public class Facultad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="FacultadId")
	private Long id;
	@NotNull
	private String nombreFacultad;
	@NotNull
	private String webFacultad;
	
	@OneToMany(mappedBy="facultad")
	private Set<Edificio> edificios;
	
	@OneToMany(mappedBy="facultad")
	private Set<User> usuarios;
	
	public Facultad(){
		
	}
	
	public Facultad(String name){
		this.nombreFacultad = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}


	public String getWebFacultad() {
		return webFacultad;
	}

	public void setWebFacultad(String webFacultad) {
		this.webFacultad = webFacultad;
	}

	public Set<Edificio> getEdificios() {
		return edificios;
	}

	public void setEdificios(Set<Edificio> edificios) {
		this.edificios = edificios;
	}
	
	
}
