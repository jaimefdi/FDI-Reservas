package es.fdi.reservas.reserva.web;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.fdi.reservas.fileupload.business.boundary.AttachmentManager;
import es.fdi.reservas.fileupload.business.boundary.NewFileCommand;
import es.fdi.reservas.fileupload.business.entity.Attachment;
import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.users.business.boundary.UserService;

@RestController
public class EdificioRestController {

	private ReservaService reserva_service;
	
	private UserService user_service;
	
	private AttachmentManager manager;
	
	@Autowired
	public EdificioRestController(UserService userService, ReservaService reservaservice, AttachmentManager manager){
		user_service = userService;
		reserva_service = reservaservice;
		this.manager = manager;
	}
	
	@RequestMapping(value = "/edificio/{idEdificio}", method = RequestMethod.DELETE)
	public void eliminarEdificio(@PathVariable("idEdificio") long idEdificio) {
		reserva_service.editarEdificioDeleted(idEdificio);
	}
	
	@RequestMapping(value = "/admin/administrar/edificios/editar/{idEdificio}/{edificio.imagen}", method = RequestMethod.PUT)
	public void editarEdificio(@PathVariable("idEdificio") long idEdificio, @RequestBody EdificioDTO edificioActualizado,
			@PathVariable("edificio.imagen") String img){
		
		
		
		//A:\FDI-Reservas\src\main\webapp\img
		
		String imagen = "A:/FDI-Reservas/src/main/webapp/img/" + img;
		File fich = new File(imagen);
		
		if (fich.exists()){
			Attachment attachment = new Attachment("");
			if (reserva_service.getAttachmentByName(img).isEmpty()){
				//si no esta, lo añado
				
				attachment.setAttachmentUrl("/img/" + img);
				attachment.setStorageKey(reserva_service.getEdificio(idEdificio).getNombreEdificio() + "/" + img);
				//reserva_service.addAttachment(attachment);
			}else{
				attachment = reserva_service.getAttachmentByName(img).get(0);
			}
			reserva_service.editarEdificio(edificioActualizado, attachment);
			System.out.println(imagen + " Existe");
		}else{
			System.out.println(imagen + " No existe");
		}

	}
	
	@RequestMapping(value = "/admin/administrar/edificio/{numPag}/restaurar/{idEdificio}", method = RequestMethod.GET)
	public String restaurarEdificio(@PathVariable("numPag") Long numPag, @PathVariable("idEdificio") Long idEdificio){
		reserva_service.restaurarEdificio(idEdificio);
		return "redirect:/administrar/edificio/{numPag}/restaurar";
	}
	
	@RequestMapping(value="/admin/nuevoEdificio", method=RequestMethod.POST)
	public String crearEdificio(Edificio f){
		reserva_service.addNewEdificio(f);
	    return "redirect:/admin/administrar/edificios/1";
	}
}
