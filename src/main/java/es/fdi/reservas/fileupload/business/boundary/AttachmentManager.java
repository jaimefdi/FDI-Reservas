package es.fdi.reservas.fileupload.business.boundary;

import java.io.IOException;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.fdi.reservas.fileupload.business.control.*;
import es.fdi.reservas.fileupload.business.entity.Attachment;
import es.fdi.reservas.storage.business.boundary.*;
import es.fdi.reservas.storage.business.entity.StorageObjectId;

@Service
@Transactional
public class AttachmentManager {

	private static final String ATTACHMENT_PREFIX = "attachment/";

	private StorageManager storageManager;

	private AttachmentRepository attachments;

	private String bucket;

	@Autowired
	public AttachmentManager(StorageManager storageManager, AttachmentRepository attachments,
			@Value("#{examplePrefs[bucket]}") String bucket) {
		this.storageManager = storageManager;
		this.attachments = attachments;
		this.bucket = bucket;
	}

	public Attachment addAttachment(NewFileCommand command) throws IOException {

		Attachment attachment = attachments.save(new Attachment(command.getDescription()));

		MultipartFile attachmentFile = command.getAttachment();
		String key = getStorageKey(attachment.getId());
		attachment.setStorageKey(key);
		String mimeType = attachmentFile.getContentType();
		storageManager.putObject(bucket, key, mimeType, attachmentFile.getInputStream());
		attachment.setAttachmentUrl(storageManager.getUrl(bucket, key));
		attachments.save(attachment);
		return attachment;
	}

	private String getStorageKey(Long id) {
		return ATTACHMENT_PREFIX + Long.toString(id);
	}

	public Collection<Attachment> getAttachments() {
		return attachments.findAll();
	}

	public void deleteAttachment(long id) {
		Attachment attachment = attachments.findOne(id);
		StorageObjectId storageId = new StorageObjectId(bucket, attachment.getStorageKey());
		try {
			storageManager.removeObject(storageId);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		attachments.delete(attachment);
	}

}