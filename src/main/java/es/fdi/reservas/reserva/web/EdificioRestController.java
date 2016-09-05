package es.fdi.reservas.reserva.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.fdi.reservas.fileupload.business.entity.Attachment;
import es.fdi.reservas.reserva.business.boundary.EdificioService;
import es.fdi.reservas.reserva.business.entity.Edificio;

@RestController
public class EdificioRestController {

	private EdificioService edificio_service;
	
	@Autowired
	public EdificioRestController(EdificioService es){
		edificio_service = es;
	}
	
	@RequestMapping(value = "/edificio/{idEdificio}", method = RequestMethod.DELETE)
	public void eliminarEdificio(@PathVariable("idEdificio") long idEdificio) {
		edificio_service.editarEdificioDeleted(idEdificio);
	}
	
	@RequestMapping(value = "/admin/administrar/edificios/editar/{idEdificio}", method = RequestMethod.PUT)
	public void editarEdificio(@PathVariable("idEdificio") long idEdificio, @RequestBody EdificioDTO edificioActualizado){

			Attachment attachment = new Attachment("");
			if (edificio_service.getAttachmentByName(edificioActualizado.getImagen()).isEmpty()){
				//si no esta, lo añado
				String img = edificioActualizado.getImagen();
				int pos = img.lastIndexOf(".");
				String punto = img.substring(0, pos);
				String fin = img.substring(pos+1, img.length());
				String nom = punto + "-" + idEdificio + "." + fin;
				nom = nom.replace(nom, "/img/" + nom);
				
				
				attachment.setAttachmentUrl("/img/" + edificioActualizado.getImagen());
				attachment.setStorageKey(nom);
			}else{
				attachment = edificio_service.getAttachmentByName(edificioActualizado.getImagen()).get(0);
			}
			edificio_service.editarEdificio(edificioActualizado, attachment);

	}
	
	@RequestMapping(value = "/admin/administrar/edificio/{numPag}/restaurar/{idEdificio}", method = RequestMethod.DELETE)
	public String restaurarEdificio(@PathVariable("numPag") Long numPag, @PathVariable("idEdificio") Long idEdificio){
		edificio_service.restaurarEdificio(idEdificio);
		return "redirect:/administrar/edificio/{numPag}/restaurar";
	}
	
	@RequestMapping(value="/admin/nuevoEdificio", method=RequestMethod.POST)
	public String crearEdificio(@RequestBody EdificioDTO f){
		edificio_service.addNewEdificio(f);
	    return "redirect:/admin/administrar/edificios/1";
	}
	
	@RequestMapping(value = "/admin/edificio/usuarios/tag/{tagName}", method = RequestMethod.GET)
	public List<EdificioDTO> usuariosFiltroAutocompletar(@PathVariable("tagName") String tagName) {

		List<EdificioDTO> result = new ArrayList<>();
		List<Edificio> usuarios = new ArrayList<>();

		usuarios = edificio_service.getEdificiosPorTagName(tagName);

		for (Edificio u : usuarios) {
			result.add(EdificioDTO.fromEdificioDTOAutocompletar(u));
		}

		return result;
	}
	
	@RequestMapping(value = "/admin/edificio/tag/{tagName}/{facultad}", method = RequestMethod.GET)
	public List<EdificioDTO> edificiosFiltroAutocompletar(@PathVariable("tagName") String tagName, @PathVariable("facultad") String facultad) {

		List<EdificioDTO> result = new ArrayList<>();
		List<Edificio> usuarios = new ArrayList<>();

		usuarios = edificio_service.getEdificiosPorFacultad(facultad);

		for (Edificio u : usuarios) {
			result.add(EdificioDTO.fromEdificioDTOAutocompletar(u));
		}

		return result;
	}
	
}
