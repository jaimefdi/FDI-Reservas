package es.fdi.reservas.reserva.business.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import es.fdi.reservas.users.business.entity.User;


@Entity
public class Reserva{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ReservaId")
	private Long id;
	
	@NotNull
	private String asunto;
	
	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private DateTime comienzo, fin;
	

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private DateTime startRecurrencia,endRecurrencia;
	
	@Enumerated(EnumType.ORDINAL)
	private EstadoReserva estadoReserva;
	
	
	@ManyToOne(optional=true)
	@JoinColumn(name="UserId")
	private User user;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="EspacioId")
	private Espacio espacio;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="GrupoReservaId")
	private GrupoReserva grupoReserva;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="reglasRecurrentes", joinColumns=@JoinColumn(name="reservaId"))
	private List<String> reglasRecurrencia;
	
	private String reservaColor;
	
	private String recurrenteId;
	

	public Reserva(){
		
	}
	
	
	public Reserva(String a, DateTime ini, DateTime fin, User user, Espacio esp,
				   DateTime startR, DateTime endR, String color, String recurrId){
		this.asunto = a;
		this.comienzo = ini;
		this.fin = fin;
		this.estadoReserva = EstadoReserva.CONFIRMADA;
		this.user = user;
		this.espacio = esp;
		this.startRecurrencia = startR;
		this.endRecurrencia = endR;
		this.reservaColor = color;
		this.reglasRecurrencia = new ArrayList<String>();
		this.recurrenteId = recurrId;
		
	}
	
	public boolean mismoGReserva(GrupoReserva valor)
	{
		return this.grupoReserva.equals(valor);
	}

    public void addReglaRecurrente(String regla){
    	this.reglasRecurrencia.add(regla);
    }
	
	public List<String> getReglasRecurrencia() {
		return reglasRecurrencia;
	}


	public void setReglasRecurrencia(List<String> reglasRecurrencia) {
		this.reglasRecurrencia = reglasRecurrencia;
	}

	
	public DateTime getStartRecurrencia() {
		return startRecurrencia;
	}

	public void setStartRecurrencia(DateTime startRecurrencia) {
		this.startRecurrencia = startRecurrencia;
	}

	public DateTime getEndRecurrencia() {
		return endRecurrencia;
	}

	public void setEndRecurrencia(DateTime endRecurrencia) {
		this.endRecurrencia = endRecurrencia;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
	public DateTime getComienzo() {
		return comienzo;
	}

	public void setComienzo(DateTime fecha_ini) {
		this.comienzo = fecha_ini;
	}

	public DateTime getFin() {
		return fin;
	}

	public void setFin(DateTime fecha_fin) {
		this.fin = fecha_fin;
	}
	

	public User getUser() {
		return user;
	}


	public void setUser(User usuario) {
		this.user = usuario;
	}

	public EstadoReserva getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstadoReserva(EstadoReserva estadoReserva) {
		this.estadoReserva = estadoReserva;
	}


	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Espacio getEspacio() {
		return espacio;
	}

	public void setEspacio(Espacio espacio) {
		this.espacio = espacio;
	}
	

	public boolean reservaAConfirmar(){
		DateTime finEsperado;
		finEsperado=comienzo.plusHours(2);
		return fin.compareTo(finEsperado) >= 0;
    }

	public String getReservaColor() {
		return reservaColor;
	}

	public void setReservaColor(String reservaColor) {
		this.reservaColor = reservaColor;
	}
	
	public String getRecurrenteId() {
		return recurrenteId;
	}


	public void setRecurrenteId(String recurrenteId) {
		this.recurrenteId = recurrenteId;
	}

	public GrupoReserva getGrupoReserva() {
		return grupoReserva;
	}


	public void setGrupoReserva(GrupoReserva grupoReserva) {
		this.grupoReserva = grupoReserva;
	}
	
	public int getRegla(String regla){
		for(int i = 0; i < reglasRecurrencia.size(); i++){
			String[] w = reglasRecurrencia.get(i).split(":");
			if(w[0].equals(regla))
				return i;
		}
		
		return -1;
	}
	
	public void addValorRegla(String regla, String valor){
		int index = getRegla(regla);
		String currentValue = reglasRecurrencia.get(index);
		reglasRecurrencia.set(index, currentValue + ";" + valor);
	}
	
	private int diaSemana(String d){
		switch(d){
			case "L": return 1; 
			case "M": return 2; 
			case "X": return 3; 
			case "J": return 4; 
			case "V": return 5; 
			case "S": return 6; 
			default: return 7;
		}
	}
	
	private void rangoDateTimeDia(RangoDateTime newRango,DateTime start, DateTime end,
								  int freq, int interval, int k){
		newRango.setComienzo(start.plusDays((freq*interval*k) ));
		newRango.setFin(end.plusDays((freq*interval*k) ));
	}
	
	private void rangoDateTimeSemana(RangoDateTime newRango,DateTime start, DateTime end,
								     int diasAsumar, int freq, int interval, int i){
		newRango.setComienzo(start.plusDays( diasAsumar + (freq*interval*i) ));
		newRango.setFin(end.plusDays(diasAsumar + (freq*interval*i) ));
	}
	
	private void rangoDateTimeMes(RangoDateTime newRango,DateTime start, DateTime end,
							      int interval, int i){
		newRango.setComienzo(start.plusMonths(interval*i));
		newRango.setFin(end.plusMonths(interval*i));
	}
	
	private void rangoDateTimeAño(RangoDateTime newRango,DateTime start, DateTime end,
								  int interval, int i){
		newRango.setComienzo(start.plusYears(interval*i));
		newRango.setFin(end.plusYears(interval*i));
	}
	
	public List<RangoDateTime> rangoRecurrencias() {
		List<RangoDateTime> recurrencias = new ArrayList<RangoDateTime>();
		int freq = 1;
		int interval = 1;
		List<String> byday = new ArrayList<String>();				
		@SuppressWarnings("static-access")
		DateTime until = new DateTime().now().plusYears(1);
		int count = Integer.MAX_VALUE;
		
		Iterator it = reglasRecurrencia.iterator();
		
		while(it.hasNext()){			
		    String[] w = it.next().toString().split(":");
			String[] v = w[1].split(";");
			int j = 0;
			switch(w[0]){
			    
				case "RRULE": 
					while(j < v.length){
						String[] f = v[j].split("=");
						switch(f[0]){
							case "FREQ": 
								if(f[1].equals("DAILY")){
									freq = 1;
					             }
								 else if(f[1].equals("WEEKLY")){
									 freq = 7;
								 }
								 else if(f[1].equals("MONTHLY")){
									 freq = 30;
								 }
								 else{
									 freq = 365;
								 }
								break;
							case "INTERVAL": interval = Integer.valueOf(f[1]); 
										    break;
							case "COUNT": count = Integer.valueOf(f[1]);
										break;
							case "UNTIL": until = DateTime.parse(f[1]);
										break;
							case "BYDAY": 
								  if(f.length > 1){
							          String[] d = f[1].split(",");
						              int k = 0;
						              while(k < d.length){										            	
						            	  byday.add(d[k]);
						            	  k++;
						              }
								  }
								  break;
						}
						
						j++;
					 }
				           
					// hacer el bucle que calcule todas las reservas
					int i = 0;
					RangoDateTime newRango = new RangoDateTime(comienzo, fin);
									
						while(count > 0 && newRango.getComienzo().compareTo(until) < 0){
							
							DateTime start = comienzo;
							DateTime end = fin;
							
							if(freq == 1){
								for(int k = 0; count > 0  && newRango.getComienzo().compareTo(until) < 0; k++ ){
									rangoDateTimeDia(newRango, start, end, freq, interval, k);
															
									recurrencias.add(new RangoDateTime(newRango.getComienzo(),newRango.getFin()));
									count--;
								}
							}
							else if(freq == 7){	
								for(int k = 0; k < byday.size() && (count > 0 && newRango.getComienzo().compareTo(until) < 0  ); k++){
																		
									int nextDay = diaSemana(byday.get(k));
									int currentDay = start.getDayOfWeek();
									int diasAsumar = nextDay - currentDay;
									//este if sirve para que no calcule la primera reserva previa a la de comienzo
									if(i != 0 || diasAsumar >= 0){	
										rangoDateTimeSemana(newRango, start, end, diasAsumar, freq, interval, i);
																
										recurrencias.add(new RangoDateTime(newRango.getComienzo(),newRango.getFin()));
										count--;
									}
								}
							}
							else if(freq == 30){
								rangoDateTimeMes(newRango, start, end, interval, i);
								recurrencias.add(new RangoDateTime(newRango.getComienzo(),newRango.getFin()));
								count--;
							}
							else{
								rangoDateTimeAño(newRango, start, end, interval, i);
								recurrencias.add(new RangoDateTime(newRango.getComienzo(),newRango.getFin()));
								count--;
							}
							
						   i++;
						}
					
					
		
					break;
				
				case "RDATE": break;
				case "EXDATE": 
						while(j < v.length){
							String[] f = v[j].split("=");
							if(f[0].equals("VALUE")){
								for(int q = 0; q < recurrencias.size(); q++){
									if(recurrencias.get(q).getComienzo().toString("dd/MM/yyyy").equals(f[1])){
										recurrencias.remove(q);
									}
								}
							}
							j++;
						}
						break;
			}
			
		}
		
		
		//actualizamos el comienzo y el final de la recurrencia
		startRecurrencia = recurrencias.get(0).getComienzo();
		endRecurrencia = recurrencias.get(recurrencias.size()-1).getFin();
			
		return recurrencias;
	}
	
	
	public List<Reserva> getInstanciasEvento(){
		List<Reserva> instancias = new ArrayList<Reserva>();
		List<RangoDateTime> rango = rangoRecurrencias();
		
		for(RangoDateTime r : rango){
			Reserva reserva = new Reserva(asunto, r.getComienzo(), r.getFin(), user,
							              espacio, startRecurrencia, endRecurrencia, reservaColor,
							              id + "_" + r.getComienzo().toString("dd/MM/yyyy"));
								
			reserva.setId(id);
			instancias.add(reserva);
		}
		
		return instancias;
	}


	public boolean solapa(Reserva r){
	  if (r.getReglasRecurrencia() == null) {
		return solapaSimple(new RangoDateTime(r.getComienzo(), r.getFin()));
	  } 
	  else {
	    for(RangoDateTime rango : r.rangoRecurrencias() ) {
	      if (solapaSimple(rango)) {
	        return true;
	      }
	    }
	  }
	  return false;
	}
	
	private boolean solapaSimple(RangoDateTime rango) {	
		boolean solapa = (rango.getComienzo().compareTo(comienzo)) <= 0 && !(rango.getFin().compareTo(comienzo) <= 0);
		solapa = solapa || (rango.getComienzo().compareTo(comienzo)>= 0 && rango.getComienzo().compareTo(fin) < 0);
		
		return solapa;
	}

	

	
}
