package exceptions;

public class TypeCompteInvalidException extends Exception {
	
	/**
	 * Le type de compte de correspond pas
	 */
	public TypeCompteInvalidException() {
		super();
	}
	
	/**
	 * Le type de compte de correspond pas
	 * @param msg
	 */
	public TypeCompteInvalidException(String msg) {
		super(msg);
	}
	
}
