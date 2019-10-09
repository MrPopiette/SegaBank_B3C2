package bo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import tools.ConsoleMessageCRUD;

public class SegaBank {
	
	private static List<Agence> agences=new ArrayList<>();
	private static Scanner sc = new Scanner( System.in );
	public static void main(String[] args) {
		int response;
		boolean first = true;
		
		System.out.println("Bienvenue dans l'application de gestion SegaBank\n");
		
		do {
			if ( !first )
				System.out.println( "Mauvais choix... merci de recommencer !" );
			new ConsoleMessageCRUD();
			try {
				response = sc.nextInt();
			} catch ( InputMismatchException e ) {
				response = -1;
			} finally {
				sc.nextLine();
			}
			first = false;
		}while(response < 1 || response > 11);
		
		switch (response) {
		case 1:
			// TO DO ajouter une nouvelle agence
			Agence agence = ajoutAgence();
			break;
		case 2:
			// TO DO modifier une agence existante
			break;
		case 3:
			// TO DO supprimer une agence existante
			break;
		case 4:
			// TO DO lister les agences
			break;
		case 5:
			// TO DO sauvegarder votre liste d'agence
			break;
		case 6:
			// TO DO ajouter un nouveau compte
			System.out.println( " - AJOUT D'UN NOUVEAU COMPTE - " );

			break;
		case 7:
			// TO DO modifier un compte existant
			break;
		case 8:
			// TO DO supprimer un compte existant
			break;
		case 9:
			// TO DO lister les comptes
			break;
		case 10:
			// TO DO sauvegarder l'etat des comptes
			break;
		case 11:
			// TO DO quitter
			break;
		}
		
		
	}

	public static Agence ajoutAgence() {
		System.out.println( " - AJOUT D'UNE NOUVELLE AGENCE - " );
		System.out.println( "Entrer un ID : " );
		int id = sc.nextInt();
		System.out.println( "Entrer le code de l'agence : " );
		String code = sc.next();
		System.out.println( "Entrer l'adresse de l'agence : " );
		System.out.println( "	Nom de la rue : " );
		String addrRue = sc.next();
		System.out.println( "	Numéro dans la rue : " );
		String addrNum = sc.next();
		System.out.println( "	Code postal : " );
		String addrCPostal = sc.next();
		System.out.println( "	Ville : " );
		String addrVille = sc.next();
		Adresse adresse = new Adresse(addrNum, addrRue, addrVille, addrCPostal);
		Agence agence = new Agence(code, id, adresse);
		return agence;
	}
	

}
