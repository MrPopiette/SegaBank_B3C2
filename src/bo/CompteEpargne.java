package bo;

public class CompteEpargne extends Compte{

	
	private double tauxInteret;
	
	public CompteEpargne(int identifiant, double solde, double tauxInteret, int id_agence) {
		super(identifiant, solde, id_agence);
		this.tauxInteret = tauxInteret;
	}
	public CompteEpargne( double solde, double tauxInteret, int id_agence) {
		super( solde, id_agence);
		this.tauxInteret = tauxInteret;
	}

	public double getTauxInteret() {
		return tauxInteret;
	}

	private void calculInteret() {
		this.solde=this.solde+this.solde*this.tauxInteret;
	}
	
	public void setTauxInteret(double tauxInteret) {
		this.tauxInteret = tauxInteret;
	}
	@Override
	public String toString() {
		return "CompteEpargne [tauxInteret=" + tauxInteret + ", identifiant=" + identifiant + ", solde=" + solde
				+ ", id_agence=" + id_agence + "]";
	}

}
