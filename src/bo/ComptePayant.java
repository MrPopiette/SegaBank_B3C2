package bo;

import exceptions.SoldeNegatifException;

public class ComptePayant extends Compte{
	
	
	public ComptePayant(int identifiant, double solde, int id_agence) {
		super(identifiant, solde, id_agence);
		// TODO Auto-generated constructor stub
	}
	public ComptePayant( double solde, int id_agence) {
		super( solde, id_agence);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void versement(double montant) {
		this.solde+=montant;
		this.solde-=montant*0.05;
	}
	
	@Override
	public void retrait(double montant) throws SoldeNegatifException {
		if(this.solde-montant-montant*0.05<0)
			throw new SoldeNegatifException("/!\\ \t --- Operation interrompue : Solde insufisant --- \t /!\\");
		this.solde-=montant;
		this.solde-=montant*0.05;
	}

}
