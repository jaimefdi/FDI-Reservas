package es.fdi.reservas.reserva.business.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Edificio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="EDIFICIO_ID")
	private Long id;
	@NotNull
	private String nombre_edificio;
	
	@OneToMany(mappedBy="edificio", cascade= CascadeType.ALL)
	private Set<Espacio> espacios;
	

	Edificio(){
		
	}
	
	public Edificio(String name){
		nombre_edificio = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre_edificio() {
		return nombre_edificio;
	}

	public void setNombre_edificio(String nombre_edificio) {
		this.nombre_edificio = nombre_edificio;
	}
	
	public Set<Espacio> getEspacios() {
		return espacios;
	}

	public void setEspacios(Set<Espacio> espacios) {
		this.espacios = espacios;
	}
}
