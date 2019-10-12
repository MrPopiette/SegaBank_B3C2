package bo;

import java.util.ArrayList;
import java.util.List;

public class Agence {
	private String code;
	private int id;
	private Adresse adresse;
	private List<Compte> comptes;
	
	/**
	 * Constructeur de la class Agence sans l'id
	 * @param code
	 * @param adresse
	 */
	public Agence(String code, Adresse adresse) {
		super();
		comptes=new ArrayList<>();
		this.code = code;
		this.adresse = adresse;
	}
	
	/**
	 * Constructeur de la class Agence avec id
	 * @param code
	 * @param id
	 * @param adresse
	 */
	public Agence(String code,int id,Adresse adresse) {
		super();
		this.id=id;
		comptes=new ArrayList<>();
		this.code = code;
		this.adresse = adresse;
	}
	
	/**
	 * 
	 * @return le code de l'agence sous forme de chaine de caractere
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 
	 * @return un entier (int), l'id unique de l'agence 
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @return L'adresse de l'agence, objet Adresse
	 */
	public Adresse getAdresse() {
		return adresse;
	}
	/**
	 * Permet d'attribuer une liste de comptes a une agence
	 * @param comptes
	 */
	public void setComptes(List<Compte> comptes) {
		this.comptes = comptes;
	}

	/**
	 * 
	 * @return la liste de compte associee a l'agence
	 */
	public List<Compte> getComptes() {
		return comptes;
	}
	
	@Override
	public String toString() {
		return "Agence [code=" + code + ", id=" + id + ", adresse=" + adresse + ", comptes=" + comptes + "]";
	}
	/**
	 * Permet d'attribuer le code de l'agence
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * Permet d'attribuer l'adresse de l'agence
	 * @param adresse
	 */
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	/**
	 * Permet d'ajouter un compte dans la liste de compte deja existante
	 * @param compte
	 */
	public void addCompte(Compte compte) {
		this.comptes.add(compte);
	}
	
	/**
	 * Permet d'attribuer l'id unique de l'agence, methode appelee apres l'insertion de l'agence dans la base de donnees 
	 * @param int1
	 */
	public void setId(int int1) {
		this.id=int1;
	}

	/**
	 * Permet de supprimer un compte dans la liste de compte de l'agence
	 * @param delCompte
	 */
	public void removeCompte(Compte delCompte) {
		comptes.remove(delCompte);
	}
	
	
	

}
