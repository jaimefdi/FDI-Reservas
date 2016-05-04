package es.fdi.reservas.reserva.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Espacio;

public class EspacioConverter implements Converter<Long, Espacio>{

	private ReservaService reserva_service;
	
	@Autowired
    public EspacioConverter(ReservaService rs) {
		reserva_service = rs;
	} 
	
	@Override
	public Espacio convert(Long idEspacio) {	
		if(idEspacio == null)
			throw new IllegalArgumentException();
		
		return reserva_service.getEspacio(idEspacio);
	}

}
