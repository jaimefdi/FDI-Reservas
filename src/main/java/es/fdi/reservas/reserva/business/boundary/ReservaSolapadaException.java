package es.fdi.reservas.reserva.business.boundary;

public class ReservaSolapadaException extends ReservasException {

	/**
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 1L;

	public ReservaSolapadaException(String message) {
		super(message);
	}
	
	public ReservaSolapadaException(String message, Throwable cause) {
		super(message, cause);
	}
}
