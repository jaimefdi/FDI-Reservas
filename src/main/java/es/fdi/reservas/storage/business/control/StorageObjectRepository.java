package es.fdi.reservas.storage.business.control;

import org.springframework.data.jpa.repository.JpaRepository;

import es.fdi.reservas.storage.business.entity.StorageObject;
import es.fdi.reservas.storage.business.entity.StorageObjectId;

public interface StorageObjectRepository extends JpaRepository<StorageObject, StorageObjectId> {

}
