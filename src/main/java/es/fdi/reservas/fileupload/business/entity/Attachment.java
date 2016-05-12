package es.fdi.reservas.fileupload.business.entity;

import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Attachment {

	@Id
	@GeneratedValue
	private Long id;
	
	private String description;

	private URL attachmentUrl;
	
	private String storageKey;
	
	Attachment ( ){
		
	}
	
	public Attachment(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public URL getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(URL attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public String getStorageKey() {
		return storageKey;
	}

	public void setStorageKey(String storageKey) {
		this.storageKey = storageKey;
	}

}
