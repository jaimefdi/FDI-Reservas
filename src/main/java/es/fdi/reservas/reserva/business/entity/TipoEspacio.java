package es.fdi.reservas.reserva.business.entity;

public enum TipoEspacio {

	AULA("aula"), SALA("sala"), LABORATORIO("laboratorio");
	
	private String tipo;
	
	private TipoEspacio(String tipo){
		this.setTipo(tipo);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public static TipoEspacio fromTipoEspacio(String tipo){
		if(tipo.equals("Sala"))
			return TipoEspacio.SALA;
		else if(tipo.equals("Aula"))
			return TipoEspacio.AULA;
		else
		    return TipoEspacio.LABORATORIO;
	}
	
	@Override
	public String toString() {
		return tipo;
	}
}
