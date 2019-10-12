package tools;

import bo.Agence;
import bo.Compte;

public class ConsoleMessageCRUD {

	public ConsoleMessageCRUD() {
				
		
		
	}
	/**
	 * Affiche dans la console le message du menu principal
	 */
	public static void menuCRUD() {
		System.out.println( "======================================" );
		System.out.println( "================ MENU ================" );
		System.out.println( "1 - Ajouter une nouvelle agence" );
		System.out.println( "2 - Modifier une agence existante" );
		System.out.println( "3 - Supprimer une agence existante" );
		System.out.println( "4 - Lister les agences");
		System.out.println( "5 - Sauvegarder votre la liste d'agence" );
		System.out.println( "6 - Ajouter un nouveau compte" );
		System.out.println( "7 - Modifier un compte existant" );
		System.out.println( "8 - Supprimer un compte  existant" );
		System.out.println( "9 - Lister les comptes" );
		System.out.println( "10 - Sauvegarder l'etat des comptes" );
		System.out.println( "11 - Quitter" );
		System.out.println( "12 - Operations bancaires" );
		System.out.println( "13 - Enregister les operations dans un fichier CSV" );
		System.out.print( "\t\tEntrez votre choix : " );
	}
	
	/**
	 * Afficher dans la console le message du menu pour les agences
	 * @param agence
	 */
	public static void menuCRUDModifierAgence(Agence agence) {
		System.out.println("Information sur l'agence : " + agence.toString());
		System.out.println("Choisir la ligne a modifier");
		System.out.println("\t 1 - code");
		System.out.println("\t 2 - numero_adresse");
		System.out.println("\t 3 - adresse");
		System.out.println("\t 4 - code_postal");
		System.out.println("\t 5 - ville");
		System.out.println("\t 6 - sauvegarder");
	}
	
	/**
	 * Affiche dans la console le message du menu des comptes
	 * @param compte
	 */
	public static void menuCRUDModifierCompte(Compte compte) {
		System.out.println("Information sur le compte : " + compte.toString());
		System.out.println("Choisir la ligne a modifier");
		System.out.println("\t 1 - Solde");
		System.out.println("\t 2 - Agence proprietaire");
		System.out.println("\t 3 - Type de compte");
		if(compte.getClass().getSimpleName().equals("CompteSimple"))
			System.out.println("\t 4 - Decouvert");
		else if(compte.getClass().getSimpleName().equals("CompteEpargne"))
			System.out.println("\t 4 - Taux d'interet");
		else
			System.out.println("\t 4 - ---------------");
		System.out.println("\t 5 - sauvegarder");
	}
	
	
	

}
