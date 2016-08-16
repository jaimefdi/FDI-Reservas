package es.fdi.reservas.reserva.web;

import java.io.File;

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
	
	@RequestMapping(value = "/admin/administrar/edificios/editar/{idEdificio}/{edificio.imagen}", method = RequestMethod.PUT)
	public void editarEdificio(@PathVariable("idEdificio") long idEdificio, @RequestBody EdificioDTO edificioActualizado,
			@PathVariable("edificio.imagen") String img){

		//A:\FDI-Reservas\src\main\webapp\img
		
		String imagen = "A:/FDI-Reservas/src/main/webapp/img/" + img;
		File fich = new File(imagen);
		
		if (fich.exists()){
			Attachment attachment = new Attachment("");
			if (edificio_service.getAttachmentByName(img).isEmpty()){
				//si no esta, lo añado
				
				attachment.setAttachmentUrl("/img/" + img);
				attachment.setStorageKey(edificio_service.getEdificio(idEdificio).getNombreEdificio() + "/" + img);
				//reserva_service.addAttachment(attachment);
			}else{
				attachment = edificio_service.getAttachmentByName(img).get(0);
			}
			edificio_service.editarEdificio(edificioActualizado, attachment);
			System.out.println(imagen + " Existe");
		}else{
			System.out.println(imagen + " No existe");
		}

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
}
