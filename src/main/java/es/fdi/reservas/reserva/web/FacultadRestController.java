package es.fdi.reservas.reserva.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.fdi.reservas.reserva.business.boundary.FacultadService;
import es.fdi.reservas.reserva.business.entity.Facultad;


@RestController
public class FacultadRestController {
	
	private FacultadService facultad_service;
	
	@Autowired
	public FacultadRestController(FacultadService fs){
		facultad_service = fs;
	}
	

	@RequestMapping(value = "/facultad/{idFacultad}", method = RequestMethod.DELETE)
	public void eliminarFacultad(@PathVariable("idFacultad") long idFacultad) {
		//reserva_service.eliminarFacultad(facultad);
		facultad_service.editarFacultadDeleted(idFacultad);
	}
	
	@RequestMapping(value = "/admin/administrar/facultad/editar/{idFacultad}", method = RequestMethod.PUT)
	public void editarFacultad(@PathVariable("idFacultad") long idFacultad, @RequestBody FacultadDTO facultadActualizado) {
		facultad_service.editarFacultad(facultadActualizado);
	}
	

	@RequestMapping(value = "/admin/administrar/facultad/{numPag}/restaurar/{idFacultad}", method = RequestMethod.GET)
	public String restaurarFacultad(@PathVariable("idFacultad") Long idFacultad, @PathVariable("numPag") Long numPag){
		facultad_service.restaurarFacultad(idFacultad);
		return "redirect:admin/administrar/facultad/{numPag}";
	}
	

	@RequestMapping(value="/admin/nuevaFacultad", method=RequestMethod.POST)
	public String crearFacultad(Facultad f){
		facultad_service.addNewFacultad(f);
	   return "redirect:/admin/administrar/facultad/1";
	}
}
