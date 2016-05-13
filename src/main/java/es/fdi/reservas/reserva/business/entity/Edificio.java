package es.fdi.reservas.reserva.business.entity;

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
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import es.fdi.reservas.fileupload.business.entity.Attachment;

@Entity
public class Edificio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="EdificioId")
	private Long id;
	@NotNull
	private String nombreEdificio;
	@NotNull
	private String direccion;
	
	@OneToMany(mappedBy="edificio")
	private Set<Espacio> espacios;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="FacultadId")
	private Facultad facultad;

	@NotNull
	private boolean deleted;
	
//	@OneToOne
//	@JoinColumn(name="ImagenId")
//	private Attachment imagen;
	
	public Edificio(){
		
	}
	

	public Edificio(String nombreEdificio,String direccion, Facultad facultad) {
		super();
		this.nombreEdificio = nombreEdificio;
		this.direccion = direccion;
		this.deleted = false;
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

	public String getNombreEdificio() {
		return nombreEdificio;
	}

	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Set<Espacio> getEspacios() {
		return espacios;
	}

	public void setEspacios(Set<Espacio> espacios) {
		this.espacios = espacios;
	}
	
	public Facultad getFacultad() {
		return facultad;
	}
//	
//	public Attachment getImagen() {
//		return imagen;
//	}
//
//	public void setImagen(Attachment imagen) {
//		this.imagen = imagen;
//	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}
	
}
