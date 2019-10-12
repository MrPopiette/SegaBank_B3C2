package exceptions;

public class SoldeNegatifException extends Exception{
	
	/**
	 * Le compte est en solde negatif
	 */
	public SoldeNegatifException() {
		super();
	}
	
	/**
	 * Le compte est en solde negatif
	 * @param msg
	 */
	public SoldeNegatifException(String msg) {
		super(msg);
	}

}
