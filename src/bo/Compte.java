package bo;

import exceptions.DecouvertDepasseException;
import exceptions.SoldeNegatifException;

public abstract class Compte {
	protected int identifiant;
	protected double solde;
	protected int id_agence;
	
	/**
	 * Constructeur de la classe Compte avec l'identifiant unique
	 * @param identifiant
	 * @param solde
	 * @param id_agence
	 */
	public Compte(int identifiant, double solde, int id_agence) {
		this.id_agence=id_agence;
		this.identifiant = identifiant;
		this.solde = solde;
	}
	/**
	 * Constructeur de la classe compte sans l'identifiant
	 * @param solde
	 * @param id_agence
	 */
	public Compte(double solde, int id_agence) {
		this.id_agence=id_agence;
		this.solde = solde;
	}

	/**
	 * 
	 * @return l'identifiant unique du compte
	 */
	public int getIdentifiant() {
		return identifiant;
	}

	/**
	 * 
	 * @return le solde actuelle du compte
	 */
	public double getSolde() {
		return solde;
	}

	/**
	 * Permet d'attribuer le solde du compte
	 * @param solde
	 */
	public void setSolde(double solde) {
		this.solde = solde;
	}
	
	/**
	 * Permet d'attribuer un id de l'agence proprietaire
	 * @param id_agence
	 */
	public void setId_agence(int id_agence) {
		this.id_agence = id_agence;
	}
	
	/**
	 * 
	 * @return un entier qui represente l'id de l'agence proprietaire du compte
	 */
	public int getAgence() {
		return id_agence;
	}

	/**
	 * Permet d'attibuer l'identifiant unique du compte
	 * @param indentifiant
	 */
	public void setIdentifiant(int indentifiant) {
		this.identifiant = indentifiant;
	}
	/**
	 * Credite le compte du montant passe en parametre
	 * @param montant
	 */
	public void versement(double montant) {
		this.solde+=montant;
	}
	
	/**
	 * Debite le compte du montant passe en parametre
	 * @param montant
	 * @throws SoldeNegatifException
	 * @throws DecouvertDepasseException
	 */
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
