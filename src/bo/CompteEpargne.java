package bo;

import java.io.IOException;
import java.sql.SQLException;

import dao.CompteDAO;

public class CompteEpargne extends Compte{

	
	private double tauxInteret;
	private Thread t;
	
	/**
	 * Permet de mettre a jour le solde du compte en fonction du taux d'interet toute les minutes
	 */
	private void calculInteretSchedule() {
		t=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
						Thread.sleep(60000);
						calculInteret();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        });
		t.start();
	}
	
	@SuppressWarnings("deprecation")
	protected void finalize() {
	    // free resources consumed by this class
	    // chain upward:
	    try {
	    	t.stop();
			super.finalize() ;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructeur avec l'identifiant unique
	 * @param identifiant
	 * @param solde
	 * @param tauxInteret
	 * @param id_agence
	 */
	public CompteEpargne(int identifiant, double solde, double tauxInteret, int id_agence) {
		super(identifiant, solde, id_agence);
		this.tauxInteret = tauxInteret;
		calculInteretSchedule();
		
	}
	/**
	 * Constructeur sans l'identifiant
	 * @param solde
	 * @param tauxInteret
	 * @param id_agence
	 */
	public CompteEpargne( double solde, double tauxInteret, int id_agence) {
		super( solde, id_agence);
		this.tauxInteret = tauxInteret;
		this.calculInteret();
		calculInteretSchedule();
	}
	
	/**
	 * 
	 * @return le taux d'interet du compte
	 */
	public double getTauxInteret() {
		return tauxInteret;
	}

	/**
	 * Met a jout le solde du compte en fonction du taux d'interet
	 */
	private void calculInteret() {
		
		this.solde = this.solde + this.solde * this.tauxInteret;
		
		try {
			new CompteDAO().update(this);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Permet d'attribuer le taux d'interet du compte
	 * @param tauxInteret
	 */
	public void setTauxInteret(double tauxInteret) {
		this.tauxInteret = tauxInteret;
	}
	@Override
	public String toString() {
		return "CompteEpargne [tauxInteret=" + tauxInteret + ", identifiant=" + identifiant + ", solde=" + solde
				+ ", id_agence=" + id_agence + "]";
	}
	

}
