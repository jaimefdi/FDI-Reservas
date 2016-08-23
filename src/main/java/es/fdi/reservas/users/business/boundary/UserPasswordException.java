package es.fdi.reservas.users.business.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserPasswordException extends UserException{

	/**
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	public UserPasswordException(String msg) {
		super(msg);
	}
	
	public UserPasswordException(String msg, Throwable cause) {
		super(msg, cause);
	}
	

}
