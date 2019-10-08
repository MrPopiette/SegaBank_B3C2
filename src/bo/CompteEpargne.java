package bo;

public class CompteEpargne extends Compte{

	
	private double tauxInteret;
	
	public CompteEpargne(String indentifiant, double solde, double tauxInteret, Agence agence) {
		super(indentifiant, solde, agence);
		this.tauxInteret = tauxInteret;
	}

	private void calculInteret() {
		this.solde=this.solde+this.solde*this.tauxInteret;
	}
	
	@Override
	public String toString() {
		return "CompteEpargne [tauxInteret=" + tauxInteret + ", indentifiant=" + indentifiant + ", solde=" + solde
				+ "]";
	}

}
