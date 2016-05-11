package es.fdi.reservas.reserva.business.entity;

public enum Autorizacion {

	NECESARIA("Necesaria"), PORHORAS("Por horas"), INNECESARIA("Innecesaria");
	
	private String tipoAutorizacion;
	
	private Autorizacion(String estado){
		this.setTipoAutorizacion(estado);
	}

	public String getTipoAutorizacion() {
		return tipoAutorizacion;
	}

	public void setTipoAutorizacion(String estado) {
		this.tipoAutorizacion = estado;
	}
	
	public static Autorizacion fromEstadoReserva(String tipoAutorizacion){
		if(tipoAutorizacion.equals("Necesaria"))
			return Autorizacion.NECESARIA;
		else if(tipoAutorizacion.equals("Por horas"))
			return Autorizacion.PORHORAS;
		else
		    return Autorizacion.INNECESARIA;
	}
	
	@Override
	public String toString() {
		return tipoAutorizacion;
	}
}
