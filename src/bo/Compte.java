package bo;

import exceptions.DecouvertDepasseException;
import exceptions.SoldeNegatifException;

public abstract class Compte {
	protected int indentifiant;
	protected double solde;
	protected int id_agence;
	public Compte(int indentifiant, double solde, int id_agence) {
		this.id_agence=id_agence;
		this.indentifiant = indentifiant;
		this.solde = solde;
	}

	public int getIndentifiant() {
		return indentifiant;
	}

	public double getSolde() {
		return solde;
	}

	public int getAgence() {
		return id_agence;
	}

	public void setIndentifiant(int indentifiant) {
		this.indentifiant = indentifiant;
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
