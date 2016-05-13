package es.fdi.reservas.reserva.business.entity;

import java.util.ArrayList;
import java.util.List;

public enum EstadoReserva {

	CONFIRMADA("Confirmada"), PENDIENTE("Pendiente"), DENEGADA("Denegada");
	
	private String estado;
	
	private EstadoReserva(String estado){
		this.setEstado(estado);
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public static EstadoReserva fromEstadoReserva(String estado){
		if(estado.equals("Confirmada"))
			return EstadoReserva.CONFIRMADA;
		else if(estado.equals("Pendiente"))
			return EstadoReserva.PENDIENTE;
		else
		    return EstadoReserva.DENEGADA;
	}
	
	public static List<EstadoReserva> getAll()
	{
		List<EstadoReserva> lista= new ArrayList<EstadoReserva>();
		lista.add(CONFIRMADA);
		lista.add(PENDIENTE);
		lista.add(DENEGADA);
		return lista;
	}
	
	@Override
	public String toString() {
		return estado;
	}
}
