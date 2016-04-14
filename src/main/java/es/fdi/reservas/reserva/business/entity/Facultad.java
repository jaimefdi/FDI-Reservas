package es.fdi.reservas.reserva.business.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import es.fdi.reservas.users.business.entity.User;

@Entity
public class Facultad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="FACULTAD_ID")
	private Long id;
	@NotNull
	private String nombreFacultad;
	@NotNull
	private String dir;
	
	@OneToMany(mappedBy="facultad")
	private Set<Edificio> edificios;
	
	@ManyToMany(mappedBy="facultades")
	private Set<User> usuarios;
	
	@NotNull
	private boolean deleted;
	
	public Facultad(){
		
	}
	
	
	public Facultad(String nombreFacultad, String dir) {
		super();
		
		this.nombreFacultad = nombreFacultad;
		this.dir = dir;
		this.deleted = false;
	}


	public Facultad(String name){
		this.nombreFacultad = name;
	}

	public Set<User> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(Set<User> usuarios) {
		this.usuarios = usuarios;
	}


	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Set<Edificio> getEdificios() {
		return edificios;
	}

	public void setEdificios(Set<Edificio> edificios) {
		this.edificios = edificios;
	}
	
	
}
