package bo;

import exceptions.DecouvertDepasseException;
import exceptions.SoldeNegatifException;

public abstract class Compte {
	protected int identifiant;
	protected double solde;
	protected int id_agence;
	
	public Compte(int identifiant, double solde, int id_agence) {
		this.id_agence=id_agence;
		this.identifiant = identifiant;
		this.solde = solde;
	}
	public Compte(double solde, int id_agence) {
		this.id_agence=id_agence;
		this.solde = solde;
	}

	public int getIdentifiant() {
		return identifiant;
	}

	public double getSolde() {
		return solde;
	}

	public int getAgence() {
		return id_agence;
	}

	public void setIdentifiant(int indentifiant) {
		this.identifiant = indentifiant;
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
		return "Compte [identifiant=" + identifiant + ", solde=" + solde + ", id_agence=" + id_agence + "]";
	}
	
	

}
