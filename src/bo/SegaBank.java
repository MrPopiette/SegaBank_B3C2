package bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dao.AgenceDAO;
import dao.CompteDAO;
import exceptions.TypeCompteInvalidException;
import tools.ConsoleMessageCRUD;

public class SegaBank {

	private static List<Agence> agences = new ArrayList<>();
	private static List<Compte> comptes = new ArrayList<>();
	private static Scanner sc = new Scanner(System.in);
	private static AgenceDAO agenceDAO = new AgenceDAO();
	private static CompteDAO compteDAO = new CompteDAO();
	
	private static void supprimerCompte() {
		System.out.println(" - SUPPRESION D'UN COMPTE - ");
		System.out.print('\n'+"Entrer l'identifiant du compte a supprimer :");
		long id;
		Compte compte=null;
		try {
			id = sc.nextLong();
		} catch (InputMismatchException e) {
			System.out.println("/!\\ --- identifiant incorrect ---  /!\\");
			return;
		}
		
		try {
			compte=compteDAO.findById(id);
		} catch (ClassNotFoundException | SQLException | IOException | TypeCompteInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(compte==null) {
			System.out.println("Ce compte n'existe pas");
			return;
		}
		try {
			compteDAO.remove(compte);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Compte tmpDelCompte=null;
		for(Compte delCompte:comptes) {
			if(delCompte.getIdentifiant()==id)
				tmpDelCompte=delCompte;
		}
		if(tmpDelCompte!=null) {
			comptes.remove(tmpDelCompte);
			System.out.println("Le compte a bien ete supprime");
		}else
			System.out.println("Ce compte n'existe pas");
		
	}
	
	private static void listerCompte() {
		try {
			comptes=compteDAO.findAll();
		} catch (ClassNotFoundException | SQLException | IOException | TypeCompteInvalidException e) {
			e.printStackTrace();
		}
		System.out.println("Liste des compte contenus dans la base de donnees : \n");
		for (Compte compte: comptes) {
			System.out.println('\t' + compte.toString());
		}
		System.out.println();
		
	}
	
	private static Compte ajoutCompte() {
		long idAgence=-1;
		Compte compte = null;
		Agence agence=null;
		System.out.println(" - AJOUT D'UN NOUVEAU COMPTE - ");
		System.out.print('\n' + "Entrer l'id de l'agence proprietaire du compte : ");
		
		
		try {
			idAgence = sc.nextLong();
		} catch (InputMismatchException e) {
			System.out.println("/!\\ --- identifiant incorrect ---  /!\\");
			return compte;
		}
		
		try {
			agence=agenceDAO.findById(idAgence);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(agence==null) {
			System.out.println("/!\\ --- Cette agence n'existe pas --- /!\\");
			return compte;
		}
		System.out.print('\n' + "Entrer le solde du compte : ");
		Double solde = sc.nextDouble();
		int typeCompte=0;
		do {
			System.out.println("1 - Compte simple");
			System.out.println("2 - Compte Payant");
			System.out.println("3 - Compte Epargne");
			System.out.print("Choisir le type de compte : ");
			typeCompte=sc.nextInt();
		}while(typeCompte < 1 || typeCompte > 3 );
		Double tauxInteret;
		Double decouvert;
		if(typeCompte==1) {
			System.out.print('\n'+"Choisir le decouvert max (mettre le nombre en negatif)");
			decouvert=sc.nextDouble();
			compte=new CompteSimple(solde, decouvert, (int) idAgence);
		}else if(typeCompte==2) {
			compte=new ComptePayant(solde, (int) idAgence);
		}else if(typeCompte==3) {
			System.out.print('\n'+"Choisir le taux d'interet : ");
			tauxInteret=sc.nextDouble();
			compte=new CompteEpargne(solde, tauxInteret, (int) idAgence);
		}else {
			System.out.println("/!\\ --- Error type compte invalide --- /!\\");
			return compte;
		}

		try {
			compteDAO.create(compte);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int index=0;
		for(Agence tmpAgence:agences) {
			if(tmpAgence.getId()==idAgence)
				agences.get(index).addCompte(compte);
			index++;
		}
		
		return compte;
	}


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
		Agence agence = new Agence(code,  adresse);

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
		if(agence==null)
			return agence;
		boolean fini=false;
		do {
			ConsoleMessageCRUD.menuCRUDModifierAgence(agence);
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
		System.out.print('\n'+"Entrer l'identifiant de l'agence a supprimer :");
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
		if(agence==null) {
			System.out.println("Cette agence n'existe pas");
			return;
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
		if(tmpDelAgence!=null) {
			agences.remove(tmpDelAgence);
			System.out.println("L'agence a bien ete supprimee");
		}else
			System.out.println("Cette agence n'existe pas");
		
		
	}

	public static void main(String[] args) {
		
		
		int response; // Permet de stoquer le choix de l utilisateur
		
		boolean run = true;//Permet de savoir quand l utilisateur veut quitter l application
		
		Agence agence; //Variable pour stocker temporairement  la variable agence affichee
		
		Compte compte; //Variable pour stocker temporairement  la variable compte affichee

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
				}else
					System.out.println("/!\\ --- Cette agence n'existe pas --- /!\\");
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
				compte=ajoutCompte();
				if(compte!=null) {
					System.out.println("Le nouveau compte a bien ete enregistree dans la base de donnees");
					System.out.println("Donnee du nouveau compte : "+compte.toString());
				}else
					System.out.println("/!\\ --- Erreur creation du compte impossible --- /!\\");
				break;
			case 7:
				// TO DO modifier un compte existant
				
				break;
			case 8:
				// TO DO supprimer un compte existant
				supprimerCompte();
				break;
			case 9:
				// TO DO lister les comptes
				listerCompte();
				break;
			case 10:
				// TO DO sauvegarder l'etat des comptes (est gere automatiquement)
				System.out.println("--- Sauvegarder ---");
				break;
			case 11:
				// TO DO quitter
				System.out.println(" - ARRET DU PROGRAMME - ");
				run = false;

			}
		} while (run);
	}
	
}
