package es.fdi.reservas.reserva.business.entity;

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
	
	@Override
	public String toString() {
		return estado;
	}
}
