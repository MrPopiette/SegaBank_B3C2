package bo;

public class Adresse {
	@Override
	public String toString() {
		return "Adresse [numero=" + numero + ", intutile=" + intutile + ", nameVille=" + nameVille + ", codepostal="
				+ codepostal + "]";
	}


	private String numero;
	private String intutile;
	private String nameVille;
	private String codepostal;
	
	
	public Adresse(String numero, String intutile, String nameVille, String codepostal) {
		this.numero = numero;
		this.intutile = intutile;
		this.nameVille = nameVille;
		this.codepostal = codepostal;
	}


	public String getNumero() {
		return numero;
	}


	public String getIntutile() {
		return intutile;
	}


	public String getNameVille() {
		return nameVille;
	}


	public String getCodepostal() {
		return codepostal;
	}

}
