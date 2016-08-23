package es.fdi.reservas.users.business.boundary;

public class UserException extends RuntimeException {

	/**
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	public UserException(String msg){
		super(msg);
	}
	
	public UserException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
