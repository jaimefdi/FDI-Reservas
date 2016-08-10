package es.fdi.reservas.fileupload.business.control;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.fdi.reservas.fileupload.business.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

	@Query("from Attachment f where lower(f.attachmentUrl) like lower(concat('%',:img, '%'))")
	List<Attachment> getAttachmentByName(@Param("img") String img);
	
}