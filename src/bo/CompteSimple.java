package bo;

import exceptions.DecouvertDepasseException;

public class CompteSimple extends Compte{
	private double decouvert;
	/**
	 * Constructeur avec identifiant unique
	 * @param identifiant
	 * @param solde
	 * @param decouvert
	 * @param id_agence
	 */
	public CompteSimple(int identifiant, double solde, double decouvert, int id_agence) {
		super(identifiant, solde, id_agence);
		this.decouvert = decouvert;
	}
	/**
	 * Constructeur sans identifiant
	 * @param solde
	 * @param decouvert
	 * @param id_agence
	 */
	public CompteSimple( double solde, double decouvert, int id_agence) {
		super(solde, id_agence);
		this.decouvert = decouvert;
	}

	@Override
	public void retrait(double montant) throws DecouvertDepasseException {
		if(this.solde-montant<decouvert)
			throw new DecouvertDepasseException("/!\\ \t --- Operation interrompue : Decouvert depasse --- \t /!\\");
		this.solde-=montant;
	}

	@Override
	public String toString() {
		return "CompteSimple [decouvert=" + decouvert + ", identifiant=" + identifiant + ", solde=" + solde
				+ ", id_agence=" + id_agence + "]";
	}
	/**
	 * 
	 * @return le decouvert maximum du compte
	 */
	public double getDecouvert() {
		return decouvert;
	}
	/**
	 * Permet d'attribuer le decouvert maximum du compte
	 * @param decouvert
	 */
	public void setDecouvert(double decouvert) {
		this.decouvert = decouvert;
	}
}
