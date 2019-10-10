package bo;

import exceptions.DecouvertDepasseException;

public class CompteSimple extends Compte{
	private double decouvert;
	
	public CompteSimple(int identifiant, double solde, double decouvert, int id_agence) {
		super(identifiant, solde, id_agence);
		this.decouvert = decouvert;
	}
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

	public double getDecouvert() {
		return decouvert;
	}
	public void setDecouvert(double decouvert) {
		this.decouvert = decouvert;
	}
}
