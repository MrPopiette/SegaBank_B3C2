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

	private static List<Agence> agences = new ArrayList<>();
	private static Scanner sc = new Scanner(System.in);
	private static AgenceDAO agenceDAO = new AgenceDAO();

	private static void listerAgence() {
		try {
			agences=agenceDAO.findAll();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("Liste des agences contenues dans la base de donnees : \n");
		for (Agence agence : agences) {
			System.out.println('\t' + agence.toString());
		}
		System.out.println();
	}

	private static Agence ajoutAgence() {
		System.out.println(" - AJOUT D'UNE NOUVELLE AGENCE - ");
		System.out.print('\n' + "Entrer le code de l'agence : ");
		String code = sc.nextLine();
		System.out.print('\n' + "Entrer l'adresse de l'agence : ");
		System.out.print('\n' + "\t Nom de la rue : ");
		String addrRue = sc.nextLine();
		System.out.print('\n' + "\t Numero dans la rue : ");
		String addrNum = sc.nextLine();
		System.out.print('\n' + "\t Code postal : ");
		String addrCPostal = sc.nextLine();
		System.out.print('\n' + "\t Ville : ");
		String addrVille = sc.nextLine();
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
	
	private static Agence modifierAgence() {
		System.out.println(" - MODIFICATION D'UNE AGENCE - ");
		System.out.print('\n'+"Entrer l'identifiant de l'agence a modifier :");
		long id;
		Agence agence=null;
		try {
			id = sc.nextLong();
		} catch (InputMismatchException e) {
			System.out.println("/!\\ --- identifiant incorrect ---  /!\\");
			return agence;
		}
		
		try {
			agence=agenceDAO.findById(id);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean fini=false;
		do {
			System.out.println("Information sur l'agence : " + agence.toString());
			System.out.println("Choisir la ligne a modifier");
			System.out.println("\t 1 - code");
			System.out.println("\t 2 - numero_adresse");
			System.out.println("\t 3 - adresse");
			System.out.println("\t 4 - code_postal");
			System.out.println("\t 5 - ville");
			System.out.println("\t 6 - sauvegarder");
			int response; // Permet de stoquer le choix de l utilisateur
			try {
				response = sc.nextInt();
			} catch (InputMismatchException e) {
				response = -1;
			} finally {
				sc.nextLine();
			}
			switch (response) {
			case 1:
				System.out.println("Saisir le nouveau code :");
				agence.setCode(sc.nextLine());
				try {
					agenceDAO.update(agence);
				} catch (ClassNotFoundException | SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				System.out.println("Saisir le nouveau numero_adresse : ");
				agence.setAdresse(new Adresse(sc.nextLine(),agence.getAdresse().getIntutile(),agence.getAdresse().getNameVille(),agence.getAdresse().getCodepostal()));
				try {
					agenceDAO.update(agence);
				} catch (ClassNotFoundException | SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.println("Saisir la nouvelle adresse : ");
				agence.setAdresse(new Adresse(agence.getAdresse().getNumero(),sc.nextLine(),agence.getAdresse().getNameVille(),agence.getAdresse().getCodepostal()));
				try {
					agenceDAO.update(agence);
				} catch (ClassNotFoundException | SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				System.out.println("Saisir le nouveau code_postal : ");
				agence.setAdresse(new Adresse(agence.getAdresse().getNumero(),agence.getAdresse().getIntutile(),sc.nextLine(),agence.getAdresse().getCodepostal()));
				try {
					agenceDAO.update(agence);
				} catch (ClassNotFoundException | SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 5:
				System.out.println("Saisir la nouvelle ville : ");
				agence.setAdresse(new Adresse(agence.getAdresse().getNumero(),agence.getAdresse().getIntutile(),agence.getAdresse().getNameVille(),sc.nextLine()));
				try {
					agenceDAO.update(agence);
				} catch (ClassNotFoundException | SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 6:
				fini = true;
				break;
			}
			
		}while(!fini);
		return agence;
		
	}
	
	private static void supprimerAgence() {
		System.out.println(" - SUPPRESION D'UNE AGENCE - ");
		System.out.print('\n'+"Entrer l'identifiant de l'agence a modifier :");
		long id;
		Agence agence=null;
		try {
			id = sc.nextLong();
		} catch (InputMismatchException e) {
			System.out.println("/!\\ --- identifiant incorrect ---  /!\\");
			return;
		}
		
		try {
			agence=agenceDAO.findById(id);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			agenceDAO.remove(agence);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Agence tmpDelAgence=null;
		for(Agence delAgence:agences) {
			if(delAgence.getId()==id)
				tmpDelAgence=delAgence;
		}
		if(tmpDelAgence!=null)
			agences.remove(tmpDelAgence);
		
		System.out.println("L'agence a bien ete supprimee");
	}

	public static void main(String[] args) {
		int response; // Permet de stoquer le choix de l utilisateur
		
		boolean run = true;//Permet de savoir quand l utilisateur veut quitter l application
		
		Agence agence; //Variable pour stocker temporairement  la variable agence affichee

		System.out.println("Bienvenue dans l'application de gestion SegaBank\n");
		do {
			do {
				ConsoleMessageCRUD.menuCRUD();
				try {
					response = sc.nextInt();
				} catch (InputMismatchException e) {
					response = -1;
				} finally {
					sc.nextLine();
				}
				
				if (response < 1 || response > 11)
					System.out.println("Mauvais choix... merci de recommencer !");
			} while (response < 1 || response > 11);

			switch (response) {
			case 1:
				// TO DO ajouter une nouvelle agence
				agence = ajoutAgence();
				System.out.println("La nouvelle agence a bien ete enregistree dans la base de donnees");
				System.out.println("Donnee de la nouvelle agence : "+agence.toString());
				break;
			case 2:
				// TO DO modifier une agence existante
				agence = modifierAgence();
				if(agence!=null) {
					System.out.println("Les modifications de l'agence a bien ete enregistree dans la base de donnees");
					System.out.println("Donnee de l'agence : "+agence.toString());
				}
				break;
			case 3:
				// TO DO supprimer une agence existante
				supprimerAgence();
				break;
			case 4:
				// TO DO lister les agences
				listerAgence();
				break;
			case 5:
				// TO DO sauvegarder votre liste d'agence (est gere automatiquement)
				System.out.println("--- Sauvegarder ---");
				break;
			case 6:
				// TO DO ajouter un nouveau compte
				System.out.println(" - AJOUT D'UN NOUVEAU COMPTE - ");

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
				System.out.println(" - ARRET DU PROGRAMME - ");
				run = false;

			}
		} while (run);
	}
}
