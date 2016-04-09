package es.fdi.reservas.reserva.business.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@NotNull
	private String username;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="EspacioId")
	private Espacio espacio;
	
	//@OneToMany(mappedBy="reserva")
	//private Set<Reserva> reglasRecurrecia;
	
	private String recurrencia;
	
	private String reservaColor;

	public Reserva(){
		
	}
	
	public Reserva(String a, DateTime ini, DateTime fin, String user_name, Espacio esp, String r){
		this.asunto = a;
		this.comienzo = ini;
		this.fin = fin;
		this.estadoReserva = EstadoReserva.CONFIRMADA;
		this.username = user_name;
		this.espacio = esp;
		this.recurrencia = r;
		
	}


	public String getRecurrencia() {
		return recurrencia;
	}

	public void setRecurrencia(String recurrencia) {
		this.recurrencia = recurrencia;
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

	public String getUsername() {
		return username;
	}

	public EstadoReserva getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstadoReserva(EstadoReserva estadoReserva) {
		this.estadoReserva = estadoReserva;
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
		
		//Iterator it = recurrencia.iterator();
		//int i = 0;
		
		//while(it.hasNext()){
			String[] w = recurrencia.split(":");
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
				case "EXDATE": break;
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
			instancias.add(new Reserva(asunto, r.getComienzo(), r.getFin(), username,
						               espacio, recurrencia));
		}
		
		return instancias;
	}
	
	public boolean solapa(Reserva r){
	  if (r.getRecurrencia() == null) {
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
