package es.fdi.reservas.reserva.business.entity;

import java.util.List;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;

@Entity
public class Espacio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ESPACIO_ID")
	private Long id;
	@NotNull
	private String nombre_espacio;
	@NotNull
	private int capacidad;
	private boolean microfono, proyector;
	//private String nombre_edificio;
	
	@OneToMany(mappedBy="espacio", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	private List<Reserva> reservas;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="EDIFICIO_ID")
	private Edificio edificio;
	
	@Enumerated(EnumType.ORDINAL)
	private TipoEspacio tipoEspacio;


	public Espacio(){
		
	}
	
	public Espacio(Long id_espacio){
		id = id_espacio;
	}

	public String getNombre_espacio() {
		return nombre_espacio;
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

	public void setNombre_espacio(String nombre_espacio) {
		this.nombre_espacio = nombre_espacio;
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
