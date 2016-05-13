package es.fdi.reservas.fileupload.business.boundary;

import org.springframework.web.multipart.MultipartFile;

public class NewFileCommand {

	private String description;
	
	private MultipartFile attachment;
	
	public NewFileCommand() {
		this.description = null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getAttachment() {
		return attachment;
	}

	public void setAttachment(MultipartFile attachment) {
		this.attachment = attachment;
	}

}
