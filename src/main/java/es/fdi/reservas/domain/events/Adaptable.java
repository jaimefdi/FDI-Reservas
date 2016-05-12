package es.fdi.reservas.domain.events;

public interface Adaptable {
  <T> T getAdapter(Class<T> clazz);
}
