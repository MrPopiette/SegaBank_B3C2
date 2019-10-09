package bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dao.AgenceDAO;
import tools.ConsoleMessageCRUD;

public class SegaBank {
	
	private static List<Agence> agences=new ArrayList<>();
	private static Scanner sc = new Scanner( System.in );
	private static AgenceDAO agenceDAO=new AgenceDAO();
	
	private static void listerAgence() {
		for(Agence agence : agences ) {
			System.out.println(agence.toString());
		}
	}
	
	private static String InputString() {
		String response;
		boolean first = true;
		do {
			if ( !first )
				System.out.println( "Entrée invalide... merci de recommencer !" );
			try {
				response = sc.next();
			} catch ( InputMismatchException e ) {
				response = null;
			} finally {
				sc.nextLine();
			}
			first = false;
		}while(response == null || response == "");
		return response;
	}

	private static Agence ajoutAgence() {
		System.out.println( " - AJOUT D'UNE NOUVELLE AGENCE - " );
		System.out.print('\n'+"Entrer le code de l'agence : " );
		String code = InputString();
		System.out.print('\n'+"Entrer l'adresse de l'agence : " );
		System.out.print('\n'+ "\t Nom de la rue : " );
		String addrRue = InputString();
		System.out.print('\n'+"\t Numero dans la rue : " );
		String addrNum = InputString();
		System.out.print('\n'+"\t Code postal : " );
		String addrCPostal = InputString();
		System.out.print('\n'+"\t Ville : " );
		String addrVille = InputString();
		Adresse adresse = new Adresse(addrNum, addrRue, addrVille, addrCPostal);
		Agence agence = new Agence(code, 0, adresse);
		
		try {
			agenceDAO.create(agence);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		agences.add(agence);
		return agence;
	}
	
	
	
	public static void main(String[] args) {
		int response;
		boolean first;
		boolean run = true;
		
		System.out.println("Bienvenue dans l'application de gestion SegaBank\n");
		do{
			first = true;
			do {
				if ( !first )
					System.out.println( "Entrée invalide... merci de recommencer !" );
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
					System.out.println( " - ARRET DU PROGRAMME - " );
					run = false;
			}
		}while(run == true);
	}
}
