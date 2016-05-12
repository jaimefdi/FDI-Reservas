package es.fdi.reservas.fileupload.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.fdi.reservas.fileupload.business.boundary.*;

@Controller
public class FileUploadController {

	private AttachmentManager manager;

	@Autowired
	public FileUploadController(AttachmentManager manager) {
		this.manager = manager;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/files")
	public ModelAndView files() {
		ModelAndView view = new ModelAndView("files");
		view.addObject("files", manager.getAttachments());
		view.addObject("command", new NewFileCommand());
		return view;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/files")
	public ModelAndView addAttachment(@ModelAttribute("command") @Validated NewFileCommand command,
			BindingResult result) throws IOException {
		

		if (result.hasErrors()) {
			ModelAndView view = new ModelAndView("files");
			view.addObject("files", manager.getAttachments());
			view.addObject("command", command);
			return view;
		}

		manager.addAttachment(command);

		return new ModelAndView("redirect:/files");
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/files/{id}")
	public ModelAndView deleteAttachment(@PathVariable("id") long id) {
		manager.deleteAttachment(id);
		return new ModelAndView("redirect:/files");
	}
}
