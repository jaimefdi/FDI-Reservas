package es.fdi.reservas.reserva.business.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
public class Espacio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="EspacioId")
	private Long id;
	@NotNull
	private String nombreEspacio;
	@NotNull
	private int capacidad;
	private boolean microfono, proyector;
	//private String nombre_edificio;
	
	@OneToMany(mappedBy="espacio", fetch=FetchType.EAGER)
	private List<Reserva> reservas;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="EdificioId")
	private Edificio edificio;
	
	@Enumerated(EnumType.ORDINAL)
	private TipoEspacio tipoEspacio;

	@NotNull
	private Autorizacion tipoAutorizacion;
	@NotNull
	private int horasAutorizacion;
	@NotNull
	private boolean deleted;
	
	@OneToOne(optional=true)
	@JoinColumn(name="ImagenId")
	private Attachment imagen;
	
	public Espacio(){
		
	}
	
	/*public Espacio(String nombre_espacio, int capacidad, boolean microfono, boolean proyector,
			TipoEspacio tipoEspacio, Edificio edificio) {
		super();
		this.nombreEspacio = nombre_espacio;
		this.capacidad = capacidad;
		this.microfono = microfono;
		this.proyector = proyector;
		this.tipoEspacio = tipoEspacio;
		this.edificio = edificio;
		this.deleted = false;
	}*/
	
	public Espacio(String nombre_espacio, int capacidad, boolean microfono, boolean proyector,
			TipoEspacio tipoEspacio) {
		super();
		this.nombreEspacio = nombre_espacio;
		this.capacidad = capacidad;
		this.microfono = microfono;
		this.proyector = proyector;
		this.tipoEspacio = tipoEspacio;
		this.deleted = false;
	}

	public Autorizacion getTipoAutorizacion() {
		return tipoAutorizacion;
	}

	public void setTipoAutorizacion(Autorizacion tipoAutorizacion) {
		this.tipoAutorizacion = tipoAutorizacion;
	}

	public int getHorasAutorizacion() {
		return horasAutorizacion;
	}

	public void setHorasAutorizacion(int horasAutorizacion) {
		if (horasAutorizacion<=0)
			this.horasAutorizacion=0;
		else
			this.horasAutorizacion = horasAutorizacion;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Espacio(Long idEspacio){
		id = idEspacio;
	}
	
	public Attachment getImagen() {
		return imagen;
	}

	public void setImagen(Attachment imagen) {
		this.imagen = imagen;
	}

	public String getNombreEspacio() {
		return nombreEspacio;
	}

	
	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isMicrofono() {
		return microfono;
	}

	public void setMicrofono(boolean microfono) {
		this.microfono = microfono;
	}

	public boolean isProyector() {
		return proyector;
	}

	public void setProyector(boolean proyector) {
		this.proyector = proyector;
	}


	public void setNombreEspacio(String nombreEspacio) {
		this.nombreEspacio = nombreEspacio;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public Edificio getEdificio() {
		return edificio;
	}

	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}
	
	 public TipoEspacio getTipoEspacio() {
		return tipoEspacio;
	}

	public void setTipoEspacio(TipoEspacio tipoEspacio) {
		this.tipoEspacio = tipoEspacio;
	}
	
}
