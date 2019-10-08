package bo;

import exceptions.DecouvertDepasseException;
import exceptions.SoldeNegatifException;

public abstract class Compte {
	protected String indentifiant;
	protected double solde;
	protected Agence agence;
	public Compte(String indentifiant, double solde, Agence agence) {
		this.agence=agence;
		this.indentifiant = indentifiant;
		this.solde = solde;
	}

	public void versement(double montant) {
		this.solde+=montant;
	}
	
	public void retrait(double montant) throws SoldeNegatifException, DecouvertDepasseException {
		if(this.solde-montant<0)
			throw new SoldeNegatifException("/!\\ \t --- Operation interrompue : Solde insufisant --- \t /!\\");
		this.solde-=montant;
	}
	
	@Override
	public String toString() {
		return "Compte [indentifiant=" + indentifiant + ", solde=" + solde + "]";
	}
	
	

}
