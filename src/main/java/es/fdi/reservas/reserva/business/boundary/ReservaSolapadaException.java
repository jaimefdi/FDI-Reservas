package es.fdi.reservas.reserva.business.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
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
