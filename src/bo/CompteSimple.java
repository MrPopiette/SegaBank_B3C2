package bo;

import exceptions.DecouvertDepasseException;

public class CompteSimple extends Compte{
	private double decouvert;
	
	public CompteSimple(int indentifiant, double solde, double decouvert, int id_agence) {
		super(indentifiant, solde, id_agence);
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
		return "CompteSimple [decouvert=" + decouvert + ", indentifiant=" + indentifiant + ", solde=" + solde + "]";
	}

	public double getDecouvert() {
		return decouvert;
	}
}