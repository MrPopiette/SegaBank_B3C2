package bo;

import java.util.ArrayList;
import java.util.List;

public class Agence {
	private String code;
	private int id;
	private Adresse adresse;
	public String getCode() {
		return code;
	}
	public int getId() {
		return id;
	}
	public Adresse getAdresse() {
		return adresse;
	}
	public List<Compte> getComptes() {
		return comptes;
	}

	private List<Compte> comptes;
	
	public Agence(String code, Adresse adresse) {
		super();
		comptes=new ArrayList<>();
		this.code = code;
		this.adresse = adresse;
	}
	
	public Agence(String code,int id,Adresse adresse) {
		super();
		this.id=id;
		comptes=new ArrayList<>();
		this.code = code;
		this.adresse = adresse;
	}
	
	@Override
	public String toString() {
		return "Agence [code=" + code + ", id=" + id + ", adresse=" + adresse + ", comptes=" + comptes + "]";
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	public List<Compte> getCompte() {
		return comptes;
	}
	
	public void addCompte(Compte compte) {
		this.comptes.add(compte);
	}
	public void setId(int int1) {
		this.id=int1;
	}
	
	
	

}
