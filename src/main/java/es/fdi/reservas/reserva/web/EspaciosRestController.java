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
import es.fdi.reservas.reserva.business.boundary.EspacioService;
import es.fdi.reservas.reserva.business.entity.Edificio;
import es.fdi.reservas.reserva.business.entity.Espacio;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.reserva.business.boundary.ReservaService;
import es.fdi.reservas.reserva.business.entity.Espacio;
import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;
import es.fdi.reservas.users.web.UserDTO;

@RestController
public class EspaciosRestController {

	private EspacioService espacio_service;
	
	@Autowired
	public EspaciosRestController(EspacioService es){
		espacio_service = es;
	}
	
	@RequestMapping(value = "/espacio/{idEspacio}", method = RequestMethod.GET)
	public EspacioDTO espacio(@PathVariable("idEspacio") long idEspacio) {
		Espacio e = espacio_service.getEspacio(idEspacio);
		
		return EspacioDTO.fromEspacioDTO(e);
	}
	
	@RequestMapping(value = "/espacio/{idEspacio}", method = RequestMethod.DELETE)
	public void eliminarEspacios(@PathVariable("idEspacio") long idEspacio) {
		espacio_service.editarEspacioDeleted(idEspacio);
	}
	
	@RequestMapping(value = "/admin/administrar/espacio/editar/{idEspacio}", method = RequestMethod.PUT)
	public void editarEspacios(@PathVariable("idEspacio") long idEspacio, @RequestBody EspacioDTO espacioDTO) {
		
		Attachment attachment = new Attachment("");
		
//		if (espacioDTO.getEdificio() == null){
//			espacioDTO.setEdificio(espacio_service.getEspacio(espacioDTO.getId()).getEdificio().getNombreEdificio());
//		}
		
		if (espacioDTO.getImagen().equals("")){
			attachment = espacio_service.getEspacio(espacioDTO.getId()).getImagen();
		}
		else {

			
			if (espacio_service.getAttachmentByName(espacioDTO.getImagen()).isEmpty()){
		
				//si no esta, lo añado
				String img = espacioDTO.getImagen();
				int pos = img.lastIndexOf(".");
				String punto = img.substring(0, pos);
				String fin = img.substring(pos+1, img.length());
				String nom = punto + "-" + idEspacio + "." + fin;
				nom = nom.replace(nom, "/img/" + nom);
				
				
				attachment.setAttachmentUrl("/img/" + espacioDTO.getImagen());
				attachment.setStorageKey(nom);
				//reserva_service.addAttachment(attachment);
			}else{
				attachment = espacio_service.getAttachmentByName(espacioDTO.getImagen()).get(0);
			}
		}
		espacio_service.editarEspacio(espacioDTO, attachment);

		//return "redirect:/admin/administrar/espacios/page/1";
	}
	
	
	@RequestMapping(value = "/admin/administrar/restaurar/espacio/{idEspacio}", method = RequestMethod.DELETE)
	public void restaurarEspacio(@PathVariable("idEspacio") Long idEspacio){
		espacio_service.restaurarEspacio(idEspacio);
	
	}
	
	@RequestMapping(value="/admin/nuevoEspacio", method=RequestMethod.POST)
	public void crearEspacio(@RequestBody EspacioDTO f){
		 espacio_service.addNewEspacio(f);
	}
	
	@RequestMapping(value = "/admin/edificio/tag/{tagName}", method = RequestMethod.GET)
	public List<EdificioDTO> edificioFiltroAutocompletar(@PathVariable("tagName") String tagName) {

		List<EdificioDTO> result = new ArrayList<>();
		List<Edificio> usuarios = new ArrayList<>();

		usuarios = espacio_service.getEdificiosPorTagName(tagName);

		for (Edificio u : usuarios) {
			result.add(EdificioDTO.fromEdificioDTOAutocompletar(u));
		}

		return result;
	}
	
//	@RequestMapping(value = "/admin/espacio/tag/{tagName}", method = RequestMethod.GET)
//	public List<EspacioDTO> espacioFiltroAutocompletar(@PathVariable("tagName") String tagName) {
//
//		List<EspacioDTO> result = new ArrayList<>();
//		List<Espacio> usuarios = new ArrayList<>();
//
//		usuarios = espacio_service.getEspaciosPorTagName(tagName);
//
//		for (Espacio u : usuarios) {
//			result.add(EspacioDTO.fromEspacioDTOAutocompletar(u));
//		}
//
//		return result;
//	}
//	
//	@RequestMapping(value = "/admin/espacio/edificio/tag/{tagName}", method = RequestMethod.GET)
//	public List<EspacioDTO> espacioEdificioFiltroAutocompletar(@PathVariable("tagName") String tagName) {
//
//		List<EspacioDTO> result = new ArrayList<>();
//		List<Espacio> usuarios = new ArrayList<>();
//
//		usuarios = espacio_service.getEspaciosPorEdificio(tagName);
//
//		for (Espacio u : usuarios) {
//			result.add(EspacioDTO.fromEspacioDTOAutocompletar(u));
//		}
//
//		return result;
//	}
}
