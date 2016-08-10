package es.fdi.reservas.reserva.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import es.fdi.reservas.reserva.business.boundary.EspacioService;
import es.fdi.reservas.reserva.business.entity.Espacio;

public class EspacioConverter implements Converter<Long, Espacio>{

	private EspacioService espacio_service;
	
	@Autowired
    public EspacioConverter(EspacioService es) {
		espacio_service = es;
	} 
	
	@Override
	public Espacio convert(Long idEspacio) {	
		if(idEspacio == null)
			throw new IllegalArgumentException();
		
		return espacio_service.getEspacio(idEspacio);
	}

}
