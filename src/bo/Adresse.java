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
	
	/**
	 * Constructeur de la class Adresse utilise par la class Agence
	 * @param numero
	 * @param intutile
	 * @param nameVille
	 * @param codepostal
	 */
	public Adresse(String numero, String intutile, String nameVille, String codepostal) {
		this.numero = numero;
		this.intutile = intutile;
		this.nameVille = nameVille;
		this.codepostal = codepostal;
	}

	/**
	 * 
	 * @return le numero de l'adresse sous forme de chaine de caracteres
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * 
	 * @return le nom de la rue sous forme de chaine de caracteres
	 */
	public String getIntutile() {
		return intutile;
	}

	/**
	 * 
	 * @return le nom de la ville sous forme de chaine de caracteres
	 */
	public String getNameVille() {
		return nameVille;
	}

	/**
	 * 
	 * @return le code postal de la ville sous la forme d'une chaine de caracteres
	 */
	public String getCodepostal() {
		return codepostal;
	}

}
