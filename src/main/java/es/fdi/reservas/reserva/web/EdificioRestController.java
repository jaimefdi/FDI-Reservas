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

		//A:\FDI-Reservas\src\main\webapp\img
		
//		String imagen = "A:/FDI-Reservas/src/main/webapp/img/" + img;
//		File fich = new File(imagen);
//		
//		if (fich.exists()){
			Attachment attachment = new Attachment("");
			if (edificio_service.getAttachmentByName(edificioActualizado.getImagen()).isEmpty()){
				//si no esta, lo añado
				
				attachment.setAttachmentUrl("/img/" + edificioActualizado.getImagen());
				attachment.setStorageKey(edificio_service.getEdificio(idEdificio).getNombreEdificio() + "/" + edificioActualizado.getImagen());
				//reserva_service.addAttachment(attachment);
			}else{
				attachment = edificio_service.getAttachmentByName(edificioActualizado.getImagen()).get(0);
			}
			edificio_service.editarEdificio(edificioActualizado, attachment);
			//System.out.println(imagen + " Existe");
//		}else{
//			System.out.println(imagen + " No existe");
//		}

	}
	
	@RequestMapping(value = "/admin/administrar/edificio/{numPag}/restaurar/{idEdificio}", method = RequestMethod.GET)
	public String restaurarEdificio(@PathVariable("numPag") Long numPag, @PathVariable("idEdificio") Long idEdificio){
		edificio_service.restaurarEdificio(idEdificio);
		return "redirect:/administrar/edificio/{numPag}/restaurar";
	}
	
	@RequestMapping(value="/admin/nuevoEdificio", method=RequestMethod.POST)
	public String crearEdificio(Edificio f){
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
	
//	@RequestMapping(value = "/admin/edificio/direccion/tag/{tagName}", method = RequestMethod.GET)
//	public List<EdificioDTO> direccionFiltroAutocompletar(@PathVariable("tagName") String tagName) {
//
//		List<EdificioDTO> result = new ArrayList<>();
//		List<Edificio> usuarios = new ArrayList<>();
//
//		usuarios = edificio_service.getEdificiosPorDireccion(tagName);
//
//		for (Edificio u : usuarios) {
//			result.add(EdificioDTO.fromEdificioDTOAutocompletar(u));
//		}
//
//		return result;
//	}
//	
//	@RequestMapping(value = "/admin/edificio/facultad/tag/{tagName}", method = RequestMethod.GET)
//	public List<EdificioDTO> facultadFiltroAutocompletar(@PathVariable("tagName") String tagName) {
//
//		List<EdificioDTO> result = new ArrayList<>();
//		List<Edificio> usuarios = new ArrayList<>();
//
//		usuarios = edificio_service.getEdificiosPorFacultad(tagName);
//
//		for (Edificio u : usuarios) {
//			result.add(EdificioDTO.fromEdificioDTOAutocompletar(u));
//		}
//
//		return result;
//	}
}
