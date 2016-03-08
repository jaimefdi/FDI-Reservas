package es.fdi.reservas.reserva.business.boundary;

public class ReservasException extends RuntimeException {

	/**
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 1L;

	public ReservasException(String message) {
		super(message);
	}
	
	public ReservasException(String message, Throwable cause) {
		super(message, cause);
	}
}
