package exceptions;

public class DecouvertDepasseException extends Exception{

	/**
	 * Le decouvert maxminum du compte est depasse
	 */
	public DecouvertDepasseException() {
		super();
	}
	/**
	 * Le decouvert maxminum du compte est depasse
	 * @param msg
	 */
	public DecouvertDepasseException(String msg) {
		super(msg);
	}

}
