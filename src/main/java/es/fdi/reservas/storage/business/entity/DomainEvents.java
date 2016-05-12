package es.fdi.reservas.storage.business.entity;

import es.fdi.reservas.domain.events.DomainEvent;

public final class DomainEvents {

	public static final DomainEvent<StorageObject> OBJECT_DELETED = new DomainEvent<StorageObject>();
	
	private DomainEvents() {
		// Avoid instantiation
	}
}
