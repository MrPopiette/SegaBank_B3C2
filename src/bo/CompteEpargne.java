package bo;

public class CompteEpargne extends Compte{

	
	private double tauxInteret;
	
	public CompteEpargne(int indentifiant, double solde, double tauxInteret, int id_agence) {
		super(indentifiant, solde, id_agence);
		this.tauxInteret = tauxInteret;
	}

	public double getTauxInteret() {
		return tauxInteret;
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
