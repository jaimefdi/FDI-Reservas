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
	public String editarEspacios(@PathVariable("idEspacio") long idEspacio, @RequestBody EspacioDTO espacioActualizado) {
		
		Attachment attachment = new Attachment("");
		
		if (espacioActualizado.getEdificio() == null){
			espacioActualizado.setEdificio(espacio_service.getEspacio(espacioActualizado.getId()).getEdificio().getNombreEdificio());
		}
		if (espacioActualizado.getImagen().equals("")){
			attachment = espacio_service.getEspacio(espacioActualizado.getId()).getImagen();
		}
		else {
//			String img = "/img/users/" + user_service.getUser(idUser).getUsername();
//			String nombreViejo = user_service.getUser(idUser).getUsername();
//			String nombreNuevo = userActualizado.getUsername();
//			
//			if (!nombreViejo.equalsIgnoreCase(nombreNuevo)){
//				//si el nombre de usuario ha cambiado, hay que renombrar el directorio y las referencias
//				//File dirViejo = new File("../src/main/webapp/img/"  + nombreViejo);
//				File dirNuevo = new File("../../img/"  + nombreNuevo);
//				boolean correcto = dirNuevo.mkdir();
//				
//			}
			
			if (espacio_service.getAttachmentByName(espacioActualizado.getImagen()).isEmpty()){
		
				//si no esta, lo añado
								
				attachment.setAttachmentUrl("/img/" + espacioActualizado.getImagen());
				attachment.setStorageKey(espacio_service.getEspacio(idEspacio).getNombreEspacio() + "/" + espacioActualizado.getImagen());
				//reserva_service.addAttachment(attachment);
			}else{
				attachment = espacio_service.getAttachmentByName(espacioActualizado.getImagen()).get(0);
			}
		}
		espacio_service.editarEspacio(espacioActualizado, attachment);
		//System.out.println(imagen + " Existe");
//	}else{
//		System.out.println(imagen + " No existe");
//	}
		
		//espacio_service.editarEspacio(espacioActualizado);
		return "redirect:/admin/administrar/espacios/1";
	}
	
	
	@RequestMapping(value = "/administrar/espacio/{numPag}/restaurar/{idEspacio}", method = RequestMethod.GET)
	public String restaurarEspacio(@PathVariable("numPag") Long numPag, @PathVariable("idEspacio") Long idEspacio){
		espacio_service.restaurarEspacio(idEspacio);
		return "redirect:/administrar/espacio/{numPag}/restaurar";
	}
	
	@RequestMapping(value="/admin/nuevoEspacio", method=RequestMethod.POST)
	public String crearEspacio(Espacio f){
		espacio_service.addNewEspacio(f);
	   return "redirect:/admin/administrar/espacios/1";
		//return "nuevoUsuario";
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
