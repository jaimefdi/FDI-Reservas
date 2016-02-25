package es.fdi.reservas.reserva.business.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="RESERVA_ID")
	private Long id;
	@NotNull
	private String asunto;
	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private DateTime fecha_ini;
	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private DateTime fecha_fin;
	@NotNull
	private boolean estado;
	@NotNull
	private String username;
	//@NotNull
	//private String spacename;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="ESPACIO_ID")
	private Espacio espacio;
	

	public Reserva(){
		
	}
	
	public Reserva(String a, DateTime ini, DateTime fin, String user_name){
		asunto = a;
		fecha_ini = ini;
		fecha_fin = fin;
		estado = true;
		username = user_name;
		//spacename = space_name;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
	public DateTime getFecha_ini() {
		return fecha_ini;
	}

	public void setFecha_ini(DateTime fecha_ini) {
		this.fecha_ini = fecha_ini;
	}

	public DateTime getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(DateTime fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public String getUsername() {
		return username;
	}
	
	
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public Long getId() {
		return id;
	}
	
	public Espacio getEspacio() {
		return espacio;
	}

	public void setEspacio(Espacio espacio) {
		this.espacio = espacio;
	}


	
	
}
