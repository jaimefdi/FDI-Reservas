package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import es.fdi.reservas.reserva.business.entity.Reserva;

public class ReservaFullCalendarDTO {

	private Long id;
	private String title;
	private DateTime start;
	private DateTime end;
	private String nombreEspacio;
	private Long idEspacio;
	private String[] recurrencia;

	public ReservaFullCalendarDTO(){
		
	}
	
	public ReservaFullCalendarDTO(Long id, String title, DateTime start, DateTime end, String nombre_espacio, Long idEspacio) {
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
		this.nombreEspacio = nombre_espacio;
		this.idEspacio = idEspacio;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public DateTime getStart() {
		return this.start;
	}

	public DateTime getEnd() {
		return this.end;
	}


	public String getNombreEspacio() {
		return nombreEspacio;
	}


	public void setNombreEspacio(String nombreEspacio) {
		this.nombreEspacio = nombreEspacio;
	}


	public void setStart(DateTime start) {
		this.start = start;
	}


	public void setEnd(DateTime end) {
		this.end = end;
	}

  
	public Long getIdEspacio() {
		return idEspacio;
	}


	public void setIdEspacio(Long idEspacio) {
		this.idEspacio = idEspacio;
	}


	public String[] getRecurrencia() {
		return recurrencia;
	}

	public void setRecurrencia(String[] recurrencia) {
		this.recurrencia = recurrencia;
	}

	public static List<ReservaFullCalendarDTO> fromReservaRecrrente(Reserva reserva){
		List<ReservaFullCalendarDTO> result = new ArrayList<ReservaFullCalendarDTO>();
		int freq = 1;
		int interval = 1;
		List<String> byday = new ArrayList<String>();
		byday.add("D");
		byday.add("L");
		byday.add("M");
		byday.add("X");
		byday.add("J");
		byday.add("V");
		byday.add("S");
		
		int[] dow = {};
		//DateTime until = fecha hoy + 1 año
		int count = Integer.MAX_VALUE;
		
		String recurrencia = reserva.getRecurrencia();
		//Iterator it = recurrencia.iterator();
		//int i = 0;
		
		//while(it.hasNext()){
			String[] w = recurrencia.split(":");
			String[] v = w[1].split(";");
			int j = 0;
			switch(w[0]){
			    
				case "RRULE": while(j < v.length){
								String[] f = v[j].split("=");
								switch(f[0]){
									case "FREQ": if(f[1].equals("DAILY")){
													freq = 1;
									             }
												 else if(f[1].equals("WEEKLY")){
													 freq = 7;
												 }
												 else if(f[1].equals("MONTHLY")){
													 freq = 30;
												 }
												break;
									case "INTERVAL": interval = Integer.valueOf(f[1]); 
												    break;
									case "COUNT": count = Integer.valueOf(f[1]);
												break;
									case "UNTIL": break;
									case "BYDAY": String[] d = f[1].split(",");
									              int k = 0;
									              while(k < d.length){
									            	  dow[k] = byday.indexOf(d[k]);
									            	  k++;
									              }
												break;
								}
								
								j++;
							}
				
							// hacer el bucle que calcule todas las reservas
								while(count-1 > 0){
									Reserva newReserva = reserva;
									newReserva.setComienzo(reserva.getComienzo().plusDays(freq*interval));
									newReserva.setFin(reserva.getFin().plusDays(freq*interval));
									
									result.add(new ReservaFullCalendarDTO(reserva.getId(), reserva.getAsunto(),
																			reserva.getComienzo(), reserva.getFin(),
																			reserva.getEspacio().getNombreEspacio(),
																			reserva.getEspacio().getId()));
									count--;
								}
				
							break;
				
				case "RDATE": break;
				case "EXDATE": break;
			}
			
			//i++;
		//}
		
		
		return result;
	}
	
	
	public static ReservaFullCalendarDTO fromReserva(Reserva reserva) {
		return new ReservaFullCalendarDTO(reserva.getId(), reserva.getAsunto(), reserva.getComienzo(), reserva.getFin(), reserva.getEspacio().getNombreEspacio(), reserva.getEspacio().getId());
	}
}
