package es.fdi.reservas.fileupload.business.control;

import org.springframework.data.jpa.repository.JpaRepository;

import es.fdi.reservas.fileupload.business.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}