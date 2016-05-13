package es.fdi.reservas.domain.events;

public interface Action1<E> {
	void execute(E obj);
}
