package bo;

import exceptions.SoldeNegatifException;

public class ComptePayant extends Compte{
	
	
	public ComptePayant(String indentifiant, double solde, Agence agence) {
		super(indentifiant, solde, agence);
		// TODO Auto-generated constructor stub
	}

	
	public void versement(double montant) {
		this.solde+=montant;
		this.solde-=montant*0.05;
	}
	
	public void retrait(double montant) throws SoldeNegatifException {
		if(this.solde-montant-montant*0.05<0)
			throw new SoldeNegatifException("/!\\ \t --- Operation interrompue : Solde insufisant --- \t /!\\\\");
		this.solde-=montant;
		this.solde-=montant*0.05;
	}

}
