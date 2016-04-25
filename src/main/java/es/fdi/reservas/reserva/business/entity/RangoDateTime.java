package es.fdi.reservas.reserva.business.entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class RangoDateTime implements Comparable<RangoDateTime>{

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private DateTime comienzo,fin;
	
	public RangoDateTime(DateTime comienzo, DateTime fin){
		this.comienzo = comienzo;
		this.fin = fin;
	}
	
	@Override
	public int compareTo(RangoDateTime rango) {
		return this.compareTo(rango);
	}

	public DateTime getComienzo() {
		return comienzo;
	}

	public void setComienzo(DateTime comienzo) {
		this.comienzo = comienzo;
	}

	public DateTime getFin() {
		return fin;
	}

	public void setFin(DateTime fin) {
		this.fin = fin;
	}

	

}
