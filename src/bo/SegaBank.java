package bo;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dao.AgenceDAO;
import dao.CompteDAO;
import dao.OperationsDAO;
import exceptions.DecouvertDepasseException;
import exceptions.SoldeNegatifException;
import exceptions.TypeCompteInvalidException;
import tools.ConsoleMessageCRUD;
import tools.SaveAsCSV;


public class SegaBank {

	
	private static Scanner sc = new Scanner(System.in);
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private static AgenceDAO agenceDAO = new AgenceDAO();
	private static OperationsDAO operationsDAO = new OperationsDAO();
	private static CompteDAO compteDAO = new CompteDAO();
	private static List<Agence> agences = new ArrayList<>();
	private static List<Compte> comptes = new ArrayList<>();
	private static List<String> listTitleNameCSV = List.of("Type Operation", "Date","IdAgence", "IdCompte", "Montant", "CompteStatus");
	//private static List<List<String>> listOperationCSV = Arrays.asList();
	private static List<List<String>> listOperationCSV;

    private static Compte modifierCompte() {
        System.out.println(" - MODIFICATION D'UN COMPTE - ");
        System.out.print('\n' + "Entrer l'identifiant du compte a modifier :");
        long id;
        Compte compte = null;
        try {
            id = sc.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("/!\\ --- identifiant incorrect ---  /!\\");
            return compte;
        }

        for (Compte unCompte : comptes)
            if (unCompte.getIdentifiant() == id)
                compte = unCompte;

        if (compte == null) {
            System.out.println("--- Il n'existe pas de compte avec cet id ---");
            return compte;
        }

        boolean fini = false;
        do {
            ConsoleMessageCRUD.menuCRUDModifierCompte(compte);
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
                    System.out.println("Saisir le nouveau solde :");
                    compte.setSolde(sc.nextDouble());
                    break;
                case 2:
                    System.out.println("Saisir l'id de la nouvelle agence proprietaire : ");
                {
                    Agence agence = null;
                    int idAgence = sc.nextInt();
                    for (Agence uneAgence : agences)
                        if (uneAgence.getId() == idAgence)
                            agence = uneAgence;

                    if (agence == null) {
                        System.out.println("Cette agence n'existe pas");
                        return null;
                    }
                    for (Agence uneAgence : agences)
                        if (uneAgence.getId() == compte.getAgence())
                            uneAgence.removeCompte(compte);
                    compte.setId_agence(idAgence);

                    agence.addCompte(compte);
                }
                break;
                case 3:
                    System.out.println("Saisir le nouveau type de compte : ");
                    //Changer le type de compte
                    int typeCompte = 0;
                    do {
                        System.out.println("1 - Compte simple");
                        System.out.println("2 - Compte Payant");
                        System.out.println("3 - Compte Epargne");
                        System.out.print("Choisir le type de compte : ");
                        typeCompte = sc.nextInt();
                    } while (typeCompte < 1 || typeCompte > 3);
                    comptes.remove(compte);
                    Double tauxInteret;
                    Double decouvert;
                    if (typeCompte == 1) {

                        System.out.print('\n' + "Choisir le decouvert max (mettre le nombre en negatif)");
                        decouvert = sc.nextDouble();
                        compte = new CompteSimple(compte.getIdentifiant(), compte.getSolde(), decouvert, (int) compte.getAgence());

                    } else if (typeCompte == 2) {

                        compte = new ComptePayant(compte.getIdentifiant(), compte.getSolde(), (int) compte.getAgence());

                    } else if (typeCompte == 3) {

                        System.out.print('\n' + "Choisir le taux d'interet : ");
                        tauxInteret = sc.nextDouble();
                        compte = new CompteEpargne(compte.getIdentifiant(), compte.getSolde(), tauxInteret, (int) compte.getAgence());

                    } else {
                        System.out.println("/!\\ --- Error type compte invalide --- /!\\");
                        break;
                    }

                {
                    for (Agence uneAgence : agences) {
                        int position = -1;
                        if (uneAgence.getId() == compte.getAgence()) {
                            int index = 0;
                            for (Compte unCompte : uneAgence.getComptes()) {
                                if (unCompte.getIdentifiant() == compte.getIdentifiant())
                                    position = index;
                                index++;
                            }
                            if (position != -1) {
                                List<Compte> listCompte = uneAgence.getComptes();
                                listCompte.set(position, compte);
                                uneAgence.setComptes(listCompte);
                                comptes.add(compte);
                            }

                        }
                    }
                }


                break;
                case 4:
                    //modification du decouvert ou taux interet
                    if (compte.getClass().getSimpleName().equals("CompteSimple")) {
                        System.out.println("Saisir le nouveau decouvert (nombre negatif) :");
                        ((CompteSimple) compte).setDecouvert(sc.nextDouble());
                    } else if (compte.getClass().getSimpleName().equals("CompteEpargne")) {
                        System.out.println("Saisir le nouveau taux d'interet :");
                        ((CompteEpargne) compte).setTauxInteret(sc.nextDouble());
                    } else {
                        System.out.println("Operation inconnue");
                        break;
                    }
                    break;
                case 5:
                    fini = true;
                    break;
            }
            try {
                compteDAO.update(compte);
            } catch (ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            }
        } while (!fini);
        return compte;
    }

    private static void supprimerCompte() {
        System.out.println(" - SUPPRESION D'UN COMPTE - ");
        System.out.print('\n' + "Entrer l'identifiant du compte a supprimer :");
        long id;
        Compte compte = null;
        try {
            id = sc.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("/!\\ --- identifiant incorrect ---  /!\\");
            return;
        }

        try {
            compte = compteDAO.findById(id);
        } catch (ClassNotFoundException | SQLException | IOException | TypeCompteInvalidException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        if (compte == null) {
            System.out.println("Ce compte n'existe pas");
            return;
        }
        try {
            compteDAO.remove(compte);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        Compte tmpDelCompte = null;
        for (Compte delCompte : comptes) {
            if (delCompte.getIdentifiant() == id)
                tmpDelCompte = delCompte;
        }
        if (tmpDelCompte != null) {
            comptes.remove(tmpDelCompte);
            for (Agence tmpAgence : agences)
                if (tmpAgence.getId() == compte.getAgence())
                    tmpAgence.removeCompte(compte);
            System.out.println("Le compte a bien ete supprime");
        } else
            System.out.println("Ce compte n'existe pas");
    }

    private static void listerCompte() {
        System.out.println("Liste des compte contenus dans la base de donnees : \n");
        for (Compte compte : comptes) {
            System.out.println('\t' + compte.toString());
        }
        System.out.println();
    }

    private static Compte ajoutCompte() {
        long idAgence = -1;
        Compte compte = null;
        Agence agence = null;
        System.out.println(" - AJOUT D'UN NOUVEAU COMPTE - ");
        System.out.print('\n' + "Entrer l'id de l'agence proprietaire du compte : ");

        try {
            idAgence = sc.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("/!\\ --- identifiant incorrect ---  /!\\");
            return compte;
        }

        try {
            agence = agenceDAO.findById(idAgence);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        if (agence == null) {
            System.out.println("/!\\ --- Cette agence n'existe pas --- /!\\");
            return compte;
        }
        System.out.print('\n' + "Entrer le solde du compte : ");
        Double solde = sc.nextDouble();
        int typeCompte = 0;
        do {
            System.out.println("1 - Compte simple");
            System.out.println("2 - Compte Payant");
            System.out.println("3 - Compte Epargne");
            System.out.print("Choisir le type de compte : ");
            typeCompte = sc.nextInt();
        } while (typeCompte < 1 || typeCompte > 3);
        Double tauxInteret;
        Double decouvert;
        if (typeCompte == 1) {
            System.out.print('\n' + "Choisir le decouvert max (mettre le nombre en negatif)");
            decouvert = sc.nextDouble();
            compte = new CompteSimple(solde, decouvert, (int) idAgence);
        } else if (typeCompte == 2) {
            compte = new ComptePayant(solde, (int) idAgence);
        } else if (typeCompte == 3) {
            System.out.print('\n' + "Choisir le taux d'interet : ");
            tauxInteret = sc.nextDouble();
            compte = new CompteEpargne(solde, tauxInteret, (int) idAgence);
        } else {
            System.out.println("/!\\ --- Error type compte invalide --- /!\\");
            return compte;
        }

        try {
            compteDAO.create(compte);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }


        // TODO verifier si ça marche
        for (Agence uneAgence : agences)
            if (uneAgence.getId() == idAgence)
                uneAgence.addCompte(compte);
        comptes.add(compte);

        return compte;
    }


    private static void listerAgence() {
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

        while (code.length() > 5 || code.length() < 2) {
            System.out.print('\n' + "Entrez un code d'agence valide ");
            code = sc.nextLine();
        }
        System.out.print('\n' + "Entrer l'adresse de l'agence : ");
        System.out.print('\n' + "\t Nom de la rue : ");
        String addrRue = sc.nextLine();

        while (addrRue.length() > 50 || addrRue.length() < 1) {
            System.out.print('\n' + "Entrez un nom de rue valide ");
            addrRue = sc.nextLine();
        }

        System.out.print('\n' + "\t Numero dans la rue : ");
        String addrNum = sc.nextLine();

        while (addrNum.length() > 10 || addrNum.length() < 1) {
            System.out.print('\n' + "Entrez un numero de rue valide ");
            addrNum = sc.nextLine();
        }

        System.out.print('\n' + "\t Code postal : ");
        String addrCPostal = sc.nextLine();

        while (addrCPostal.length() > 5 || addrCPostal.length() < 5) {
            System.out.print('\n' + "Entrez un code postal valide ");
            addrCPostal = sc.nextLine();
        }

        System.out.print('\n' + "\t Ville : ");
        String addrVille = sc.nextLine();

        while (addrVille.length() > 50 || addrVille.length() < 1) {
            System.out.print('\n' + "Entrez un nom de ville valide ");
            addrVille = sc.nextLine();
        }

        Adresse adresse = new Adresse(addrNum, addrRue, addrVille, addrCPostal);
        Agence agence = new Agence(code, adresse);

        try {
            agenceDAO.create(agence);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }

        agences.add(agence);
        return agence;
    }

    private static Agence modifierAgence() {
        System.out.println(" - MODIFICATION D'UNE AGENCE - ");
        System.out.print('\n' + "Entrer l'identifiant de l'agence a modifier :");
        long id;
        Agence agence = null;
        try {
            id = sc.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("/!\\ --- identifiant incorrect ---  /!\\");
            return agence;
        }

        for (Agence uneAgence : agences)
            if (uneAgence.getId() == id)
                agence = uneAgence;

        if (agence == null) {
            System.out.println("Il n'existe pas d'agence pour cet id");
            return agence;
        }

        boolean fini = false;
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
                    break;
                case 2:
                    System.out.println("Saisir le nouveau numero_adresse : ");
                    agence.setAdresse(new Adresse(sc.nextLine(), agence.getAdresse().getIntutile(), agence.getAdresse().getNameVille(), agence.getAdresse().getCodepostal()));
                    break;
                case 3:
                    System.out.println("Saisir la nouvelle adresse : ");
                    agence.setAdresse(new Adresse(agence.getAdresse().getNumero(), sc.nextLine(), agence.getAdresse().getNameVille(), agence.getAdresse().getCodepostal()));
                    break;
                case 4:
                    String code_postal = "";
                    do {
                        System.out.println("Saisir le nouveau code_postal (5 chiffres): ");
                        code_postal = sc.nextLine();
                    } while (code_postal.length() != 5);

                    agence.setAdresse(new Adresse(agence.getAdresse().getNumero(), agence.getAdresse().getIntutile(), agence.getAdresse().getNameVille(), code_postal));
                    break;
                case 5:
                    System.out.println("Saisir la nouvelle ville : ");
                    agence.setAdresse(new Adresse(agence.getAdresse().getNumero(), agence.getAdresse().getIntutile(), sc.nextLine(), agence.getAdresse().getCodepostal()));
                    break;
                case 6:
                    fini = true;
                    break;
            }
            try {
                agenceDAO.update(agence);
            } catch (ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            }

        } while (!fini);
        return agence;

    }

    private static void supprimerAgence() {
        System.out.println(" - SUPPRESION D'UNE AGENCE - ");
        System.out.print('\n' + "Entrer l'identifiant de l'agence a supprimer :");
        long id;
        Agence agence = null;
        try {
            id = sc.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("/!\\ --- identifiant incorrect ---  /!\\");
            return;
        }

        try {
            agence = agenceDAO.findById(id);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        if (agence == null) {
            System.out.println("Cette agence n'existe pas");
            return;
        }
        try {
            agenceDAO.remove(agence);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        Agence tmpDelAgence = null;
        for (Agence delAgence : agences) {
            if (delAgence.getId() == id)
                tmpDelAgence = delAgence;
        }
        if (tmpDelAgence != null) {
            agences.remove(tmpDelAgence);
            comptes.removeAll(tmpDelAgence.getComptes());
            for (Compte unCompte : tmpDelAgence.getComptes())
                try {
                    compteDAO.remove(unCompte);
                } catch (ClassNotFoundException | SQLException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            System.out.println("L'agence et ces comptes ont bien ete supprimes");
        } else
            System.out.println("Cette agence n'existe pas");


    }

    public static void main(String[] args) {


        try {
            comptes = compteDAO.findAll();
        } catch (ClassNotFoundException | SQLException | IOException | TypeCompteInvalidException e1) {
            e1.printStackTrace();
            System.out.println(e1.getMessage());
        }
        try {
            agences = agenceDAO.findAll();
        } catch (ClassNotFoundException | SQLException | IOException e1) {
            e1.printStackTrace();
            System.out.println(e1.getMessage());
        }

        try {
			listOperationCSV=operationsDAO.findAll();
		} catch (ClassNotFoundException | SQLException | IOException | TypeCompteInvalidException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
        for (Compte unCompte : comptes)
            for (Agence uneAgence : agences)
                if (unCompte.getAgence() == uneAgence.getId())
                    uneAgence.addCompte(unCompte);


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

                if (response < 1 || response > 13)
                    System.out.println("Mauvais choix... merci de recommencer !");
            } while (response < 1 || response > 13);

            switch (response) {
                case 1:
                    // TODO ajouter une nouvelle agence
                    agence = ajoutAgence();
                    System.out.println("La nouvelle agence a bien ete enregistree dans la base de donnees");
                    System.out.println("Donnee de la nouvelle agence : " + agence.toString());
                    break;
                case 2:
                    // TODO modifier une agence existante
                    agence = modifierAgence();
                    if (agence != null) {
                        System.out.println("Les modifications de l'agence ont bien et enregistrees dans la base de donnees");
                        System.out.println("Donnee de l'agence : " + agence.toString());
                    } else
                        System.out.println("/!\\ --- Cette agence n'existe pas --- /!\\");
                    break;
                case 3:
                    // TODO supprimer une agence existante
                    supprimerAgence();
                    break;
                case 4:
                    // TODO lister les agences
                    listerAgence();
                    break;
                case 5:
                    // TODO sauvegarder votre liste d'agence (est gere automatiquement)
                    System.out.println("--- Sauvegarder ---");
                    break;
                case 6:
                    // TODO ajouter un nouveau compte
                    compte = ajoutCompte();
                    if (compte != null) {
                        System.out.println("Le nouveau compte a bien ete enregistre dans la base de donnees");
                        System.out.println("Donnee du nouveau compte : " + compte.toString());
                        listOperationCSV.add(Arrays.asList("Creation Compte", formatter.format(new Date()).toString(), new Integer(compte.getAgence()).toString(), new Integer(compte.getIdentifiant()).toString(), "", compte.toString()));
                        try {
							operationsDAO.create(Arrays.asList("Creation Compte", formatter.format(new Date()).toString(), new Integer(compte.getAgence()).toString(), new Integer(compte.getIdentifiant()).toString(), "", compte.toString()));
						} catch (ClassNotFoundException | SQLException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    } else
                        System.out.println("/!\\ --- Erreur creation du compte impossible --- /!\\");
                    break;
                case 7:
                    // TODO modifier un compte existant
                    compte = modifierCompte();
                    if (compte != null) {
                        System.out.println("Les modifications du compte  bien ete enregistrees dans la base de donnees");
                        System.out.println("Donnee du compte: " + compte.toString());
                    } else
                        System.out.println("/!\\ --- Abandon de l'operation : le compte ou l'agence n'existe pas --- /!\\");
                    break;
                case 8:
                    // TODO supprimer un compte existant
                    supprimerCompte();
                    break;
                case 9:
                    // TODO lister les comptes
                    listerCompte();
                    break;
                case 10:
                    // TODO sauvegarder l'etat des comptes (est gere automatiquement)
                    System.out.println("--- Sauvegarder ---");
                    break;
                case 11:
                    // TODO quitter
                    System.out.println(" - ARRET DU PROGRAMME - ");
                    run = false;
                    break;
                case 12:
                    System.out.println("Operation bancaire");
                    System.out.println("Saisir l'identifiant du compte");
                    int id = sc.nextInt();
                    for (Compte unCompte : comptes) {
                        if (unCompte.getIdentifiant() == id) {
                            System.out.println("Info compte " + unCompte.toString());
                            int response2 = 0;
                            do {
                                System.out.println("Choisir l'operation :");
                                System.out.println("1 - Virement");
                                System.out.println("2 - Retrait");
                                response2 = sc.nextInt();
                            } while (response2 != 1 && response2 != 2);
                            if (response2 == 1) {

                                System.out.println("Saisir l'identifiant du compte a crediter");
                                int idCredite = sc.nextInt();
                                for (Compte compteACrediter : comptes) {
                                    if (compteACrediter.getIdentifiant() == idCredite) {
                                        System.out.println("Saisir le montant du virement :");
                                        Double montant = sc.nextDouble();
                                        while (montant < 1) {
                                            System.out.print('\n' + "Entrez une valeur correcte");
                                            montant = sc.nextDouble();
                                        }
                                        unCompte.versement(montant);
                                        try {
                                            compteACrediter.retrait(montant);
                                        } catch (SoldeNegatifException e) {
                                            e.printStackTrace();
                                        } catch (DecouvertDepasseException e) {
                                            e.printStackTrace();
                                        }
                                        listOperationCSV.add(Arrays.asList("Versement", formatter.format(new Date()).toString(), new Integer(unCompte.getAgence()).toString(), new Integer(unCompte.getIdentifiant()).toString(), montant.toString(), unCompte.toString()));
                                        
                                        try {
											operationsDAO.create(Arrays.asList("Versement", formatter.format(new Date()).toString(), new Integer(unCompte.getAgence()).toString(), new Integer(unCompte.getIdentifiant()).toString(), montant.toString(), unCompte.toString()));
										} catch (ClassNotFoundException | SQLException | IOException e) {
											e.printStackTrace();
										}
                                    }
                                }
                            } else if (response2 == 2) {
                                System.out.println("Saisir le montant du retrait:");
                                Double montant = sc.nextDouble();
                                while (montant < 1) {
                                    System.out.print('\n' + "Entrez une valeur correcte");
                                    montant = sc.nextDouble();
                                }
                                try {
                                    unCompte.retrait(montant);
                                } catch (SoldeNegatifException | DecouvertDepasseException e) {
                                    System.out.println(e.getMessage());
                                }
                                listOperationCSV.add(Arrays.asList("Retrait", formatter.format(new Date()).toString(), new Integer(unCompte.getAgence()).toString(), new Integer(unCompte.getIdentifiant()).toString(), montant.toString(), unCompte.toString()));
                                
                                try {
									operationsDAO.create(Arrays.asList("Retrait", formatter.format(new Date()).toString(), new Integer(unCompte.getAgence()).toString(), new Integer(unCompte.getIdentifiant()).toString(), montant.toString(), unCompte.toString()));
								} catch (ClassNotFoundException | SQLException | IOException e) {
									e.printStackTrace();
								}
                            } else
                                System.out.println("Erreur operation inconnue");
                            try {
                                compteDAO.update(unCompte);
                            } catch (ClassNotFoundException | SQLException | IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 13:
                	System.out.println("Choisir le type de fichier");
                	System.out.println("1 - Toutes les operations");
                	System.out.println("2 - Operations d'un compte uniquement");
                	String responseCSV=sc.nextLine();
                	if(responseCSV.equals("1")) {
                		System.out.println("Le fichier CSV a bien ete cree dans le repertoire racine");
                        try {
                            new SaveAsCSV(listTitleNameCSV, listOperationCSV);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                	}else if(responseCSV.equals("2")) {
                		List<List<String>> listOperationCSVCompte = new ArrayList<List<String>>();
                		System.out.println("Saisir l'identifiant du compte");
                        String idCompteCSV = sc.nextLine();
                        for(List<String> operationCompte:listOperationCSV)
                        	if(operationCompte.get(3).equals(idCompteCSV))
                        		listOperationCSVCompte.add(operationCompte);
                		
                		System.out.println("Le fichier CSV a bien ete cree dans le repertoire racine");
                        try {
                            new SaveAsCSV(listTitleNameCSV, listOperationCSVCompte);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                	}else
                		System.out.println("Saisie incorrecte");
                    break;
            }
        } while (run);
    }



}
