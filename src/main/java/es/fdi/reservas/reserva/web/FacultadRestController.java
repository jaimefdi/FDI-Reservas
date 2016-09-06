package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.fdi.reservas.reserva.business.boundary.FacultadService;
import es.fdi.reservas.reserva.business.entity.Facultad;
import es.fdi.reservas.users.business.entity.User;
import es.fdi.reservas.users.web.UserDTO;


@RestController
public class FacultadRestController {
	
	private FacultadService facultad_service;
	
	@Autowired
	public FacultadRestController(FacultadService fs){
		facultad_service = fs;
	}
	

	@RequestMapping(value = "/facultad/{idFacultad}", method = RequestMethod.DELETE)
	public void eliminarFacultad(@PathVariable("idFacultad") long idFacultad) {
		facultad_service.editarFacultadDeleted(idFacultad);
	}
	
	@RequestMapping(value = "/admin/administrar/facultad/editar/{idFacultad}", method = RequestMethod.PUT)
	public void editarFacultad(@PathVariable("idFacultad") long idFacultad, @RequestBody FacultadDTO facultadActualizado) {
		facultad_service.editarFacultad(facultadActualizado);
	}
	

	@RequestMapping(value = "/admin/administrar/facultad/{numPag}/restaurar/{idFacultad}", method = RequestMethod.DELETE)
	public String restaurarFacultad(@PathVariable("idFacultad") Long idFacultad, @PathVariable("numPag") Long numPag){
		facultad_service.restaurarFacultad(idFacultad);
		return "redirect:admin/administrar/facultad/{numPag}";
	}
	

	@RequestMapping(value="/admin/nuevaFacultad", method=RequestMethod.PUT)
	public String crearFacultad(@RequestBody FacultadDTO f){
		facultad_service.addNewFacultad(f);
	   return "redirect:/admin/administrar/facultad/page/1";
	}
	
//	@RequestMapping(value = "/admin/facultad/nombre/tag/{tagName}", method = RequestMethod.GET)
//	public List<FacultadDTO> facultadesFiltroAutocompletar(@PathVariable("tagName") String tagName) {
//
//		List<FacultadDTO> result = new ArrayList<>();
//		List<Facultad> usuarios = new ArrayList<>();
//
//		usuarios = facultad_service.getFacultadesPorTagName(tagName);
//
//		for (Facultad u : usuarios) {
//			result.add(FacultadDTO.fromFacultadDTOAutocompletar(u));
//		}
//
//		return result;
//	}
//	
//	@RequestMapping(value = "/admin/facultad/web/tag/{tagName}", method = RequestMethod.GET)
//	public List<FacultadDTO> emailFiltroAutocompletar(@PathVariable("tagName") String tagName) {
//
//		List<FacultadDTO> result = new ArrayList<>();
//		List<Facultad> usuarios = new ArrayList<>();
//
//		usuarios = facultad_service.getFacultadesPorWeb(tagName);
//
//		for (Facultad u : usuarios) {
//			result.add(FacultadDTO.fromFacultadDTOAutocompletar(u));
//		}
//
//		return result;
//	}
}
