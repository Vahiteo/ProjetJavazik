package controller;

import model.*;
import view.ConsoleView;
import java.util.ArrayList;
import java.util.List;
import model.GestionnaireData;
import view.GraphiqueView;
import javax.swing.*;
import java.awt.*;

/**
 * Classe contrôleur qui fait le lien entre le modèle et la vue.
 * Gère toutes les interactions utilisateur.
 */
public class Controller {

    private ConsoleView vue;
    private Catalogue catalogue;
    private List<Abonne> abonnes;
    private Administrateur admin;
    private Utilisateur utilisateurConnecte;

    /**
     * Constructeur du Controller.
     * Initialise le catalogue, la vue et les données de base.
     */
    public Controller() {
        this.vue = new ConsoleView();
        this.admin = new Administrateur("admin", "admin123");
        this.utilisateurConnecte = null;

        // Chargement des données sauvegardées
        Catalogue catalogueCharge = GestionnaireData.chargerCatalogue();
        if (catalogueCharge != null) {
            this.catalogue = catalogueCharge;
        } else {
            this.catalogue = new Catalogue();
            initialiserDonnees(); // données de base seulement si pas de sauvegarde
        }

        List<Abonne> abonnesCharges = GestionnaireData.chargerAbonnes();
        this.abonnes = abonnesCharges;
    }

    /**
     * Initialise quelques données de base pour les tests.
     */
    private void initialiserDonnees() {
        ArtisteSeul michael = new ArtisteSeul("Jackson", "Michael", "Roi de la pop", "29/08/1958");
        Groupe beatles = new Groupe("The Beatles", "Groupe légendaire britannique", 1960);
        ArtisteSeul john = new ArtisteSeul("Lennon", "John", "Membre des Beatles", "09/10/1940");
        ArtisteSeul paul = new ArtisteSeul("McCartney", "Paul", "Membre des Beatles", "18/06/1942");
        beatles.addMembre(john);
        beatles.addMembre(paul);

        Morceau thriller = new Morceau("Thriller", 358, "Pop", michael);
        Morceau billie = new Morceau("Billie Jean", 294, "Pop", michael);
        Morceau letItBe = new Morceau("Let It Be", 243, "Rock", beatles);

        Album thrillertAlbum = new Album("Thriller", 1982, "studio", michael);
        thrillertAlbum.addMorceau(thriller);
        thrillertAlbum.addMorceau(billie);

        Album beatlesAlbum = new Album("Let It Be", 1970, "studio", beatles);
        beatlesAlbum.addMorceau(letItBe);

        catalogue.addArtiste(michael);
        catalogue.addArtiste(beatles);
        catalogue.addMorceau(thriller);
        catalogue.addMorceau(billie);
        catalogue.addMorceau(letItBe);
        catalogue.addAlbum(thrillertAlbum);
        catalogue.addAlbum(beatlesAlbum);
    }

    // ===== Lancement =====

    /**
     * Lance la boucle principale du programme.
     */
    public void lancerConsole() {
        vue.afficherMessage("Bienvenue sur JAVAZIC ");
        boolean quitter = false;
        while (!quitter) {
            vue.afficherMenuPrincipal();
            int choix = vue.lireInt("Votre choix : ");
            switch (choix) {
                case 1 -> connecterAdmin();
                case 2 -> connecterAbonne();
                case 3 -> creerCompte();
                case 4 -> menuVisiteur(new Visiteur());
                case 5 -> quitter = true;
                default -> vue.afficherErreur("Choix invalide ");
            }
        }
        // Sauvegarde automatique à la fermeture
        GestionnaireData.sauvegarderCatalogue(catalogue);
        GestionnaireData.sauvegarderAbonnes(abonnes);
        vue.afficherMessage("Au revoir ");
    }



    // ===== Connexion =====

    /**
     * Gère la connexion administrateur.
     */
    private void connecterAdmin() {
        String id = vue.lireString("Identifiant : ");
        String mdp = vue.lireString("Mot de passe : ");
        if (admin.getIdentifiant().equals(id) && admin.verifierMotDePasse(mdp)) {
            vue.afficherSucces("Connecté en tant qu'administrateur ");
            utilisateurConnecte = admin;
            menuAdmin();
        } else {
            vue.afficherErreur("Identifiants incorrects ");
        }
    }

    /**
     * Gère la connexion abonné.
     */
    private void connecterAbonne() {
        String id = vue.lireString("Identifiant : ");
        String mdp = vue.lireString("Mot de passe : ");
        for (Abonne a : abonnes) {
            if (a.getIdentifiant().equals(id) && a.verifierMotDePasse(mdp)) {
                if (!a.estActif()) {
                    vue.afficherErreur("Votre compte est suspendu ");
                    return;
                }
                vue.afficherSucces("Connecté en tant que " + a.getIdentifiant() + " ");
                utilisateurConnecte = a;
                menuAbonne(a);
                return;
            }
        }
        vue.afficherErreur("Identifiants incorrects ");
    }

    /**
     * Gère la création d'un compte abonné.
     */
    private void creerCompte() {
        String id = vue.lireString("Choisissez un identifiant : ");
        for (Abonne a : abonnes) {
            if (a.getIdentifiant().equals(id)) {
                vue.afficherErreur("Cet identifiant est déjà pris ");
                return;
            }
        }
        String mdp = vue.lireString("Choisissez un mot de passe : ");
        String email = vue.lireString("Votre email : ");
        Abonne nouvelAbonne = new Abonne(id, mdp, email);
        abonnes.add(nouvelAbonne);
        vue.afficherSucces("Compte créé avec succès \n Bienvenue " + id + " ");
    }

    // ===== Menu Visiteur =====

    /**
     * Gère le menu visiteur.
     */
    private void menuVisiteur(Visiteur visiteur) {
        boolean retour = false;
        while (!retour) {
            vue.afficherMenuVisiteur();
            int choix = vue.lireInt("Votre choix : ");
            switch (choix) {
                case 1 -> rechercherMorceau();
                case 2 -> rechercherAlbum();
                case 3 -> rechercherArtiste();
                case 4 -> ecouterMorceauVisiteur(visiteur);
                case 5 -> retour = true;
                default -> vue.afficherErreur("Choix invalide ");
            }
        }
    }

    // ===== Menu Abonné =====

    /**
     * Gère le menu abonné.
     */
    private void menuAbonne(Abonne abonne) {
        boolean retour = false;
        while (!retour) {
            vue.afficherMenuAbonne();
            int choix = vue.lireInt("Votre choix : ");
            switch (choix) {
                case 1 -> rechercherMorceau();
                case 2 -> rechercherAlbum();
                case 3 -> rechercherArtiste();
                case 4 -> ecouterMorceauAbonne(abonne);
                case 5 -> gererPlaylists(abonne);
                case 6 -> vue.afficherMorceaux(abonne.getHistorique().getMorceaux());
                case 7 -> laisserAvis(abonne);
                case 8 -> retour = true;
                default -> vue.afficherErreur("Choix invalide ");
            }
        }
        utilisateurConnecte = null;
    }

    // ===== Menu Admin =====

    /**
     * Gère le menu administrateur.
     */
    private void menuAdmin() {
        boolean retour = false;
        while (!retour) {
            vue.afficherMenuAdmin();
            int choix = vue.lireInt("Votre choix : ");
            switch (choix) {
                case 1 -> ajouterMorceau();
                case 2 -> supprimerMorceau();
                case 3 -> ajouterAlbum();
                case 4 -> supprimerAlbum();
                case 5 -> ajouterArtiste();
                case 6 -> supprimerArtiste();
                case 7 -> gererAbonnes();
                case 8 -> vue.afficherStatistiquesEvoluees(catalogue, abonnes);                case 9 -> retour = true;
                default -> vue.afficherErreur("Choix invalide ");
            }
        }
        utilisateurConnecte = null;
    }

    // ===== Recherche =====

    private void rechercherMorceau() {
        String titre = vue.lireString("Titre du morceau : ");
        List<Morceau> resultats = catalogue.rechercherMorceauxParTitre(titre);
        vue.afficherMorceaux(resultats);
        if (!resultats.isEmpty()) {
            int choix = vue.lireInt("Voir les détails (numéro) ou 0 pour annuler : ");
            if (choix > 0 && choix <= resultats.size()) {
                naviguerMorceau(resultats.get(choix - 1));
            }
        }
    }

    private void rechercherAlbum() {
        String titre = vue.lireString("Titre de l'album : ");
        List<Album> resultats = catalogue.rechercherAlbumsParTitre(titre);
        vue.afficherAlbums(resultats);
        if (!resultats.isEmpty()) {
            int choix = vue.lireInt("Voir les détails (numéro) ou 0 pour annuler : ");
            if (choix > 0 && choix <= resultats.size()) {
                naviguerAlbum(resultats.get(choix - 1));
            }
        }
    }

    private void rechercherArtiste() {
        String nom = vue.lireString("Nom de l'artiste : ");
        List<Artiste> resultats = catalogue.rechercherArtistesParNom(nom);
        vue.afficherArtistes(resultats);
        if (!resultats.isEmpty()) {
            int choix = vue.lireInt("Voir les détails (numéro) ou 0 pour annuler : ");
            if (choix > 0 && choix <= resultats.size()) {
                naviguerArtiste(resultats.get(choix - 1));
            }
        }
    }

    /**
     * Navigation depuis un morceau.
     */
    private void naviguerMorceau(Morceau morceau) {
        boolean retour = false;
        while (!retour) {
            vue.afficherDetailsMorceau(morceau);
            vue.afficherMessage(
                    "1. Voir la fiche de l'artiste\n" +
                            "2. Voir les albums contenant ce morceau\n" +
                            "3. Retour"
            );
            int choix = vue.lireInt("Votre choix : ");
            switch (choix) {
                case 1 -> naviguerArtiste(morceau.getArtiste());
                case 2 -> {
                    List<Album> albums = catalogue.getAlbumsContenant(morceau);
                    vue.afficherAlbums(albums);
                    if (!albums.isEmpty()) {
                        int choixAlbum = vue.lireInt("Voir les détails (numéro) ou 0 pour annuler : ");
                        if (choixAlbum > 0 && choixAlbum <= albums.size()) {
                            naviguerAlbum(albums.get(choixAlbum - 1));
                        }
                    }
                }
                case 3 -> retour = true;
                default -> vue.afficherErreur("Choix invalide ");
            }
        }
    }

    /**
     * Navigation depuis un album.
     */
    private void naviguerAlbum(Album album) {
        boolean retour = false;
        while (!retour) {
            vue.afficherDetailsAlbum(album);
            vue.afficherMessage(
                    "1. Voir la fiche de l'artiste\n" +
                            "2. Voir les détails d'un morceau\n" +
                            "3. Retour"
            );
            int choix = vue.lireInt("Votre choix : ");
            switch (choix) {
                case 1 -> naviguerArtiste(album.getArtiste());
                case 2 -> {
                    if (album.getMorceaux().isEmpty()) {
                        vue.afficherErreur("Cet album ne contient aucun morceau ");
                        break;
                    }
                    vue.afficherMorceaux(album.getMorceaux());
                    int choixMorceau = vue.lireInt("Voir les détails (numéro) ou 0 pour annuler : ");
                    if (choixMorceau > 0 && choixMorceau <= album.getMorceaux().size()) {
                        naviguerMorceau(album.getMorceaux().get(choixMorceau - 1));
                    }
                }
                case 3 -> retour = true;
                default -> vue.afficherErreur("Choix invalide ");
            }
        }
    }

    /**
     * Navigation depuis un artiste.
     */
    private void naviguerArtiste(Artiste artiste) {
        boolean retour = false;
        while (!retour) {
            vue.afficherMessage(artiste.getDescription());
            vue.afficherMessage(
                    "1. Voir les morceaux de cet artiste\n" +
                            "2. Voir les albums de cet artiste\n" +
                            "3. Retour"
            );
            int choix = vue.lireInt("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    List<Morceau> morceaux = catalogue.getMorceauxParArtiste(artiste);
                    vue.afficherMorceaux(morceaux);
                    if (!morceaux.isEmpty()) {
                        int choixMorceau = vue.lireInt("Voir les détails (numéro) ou 0 pour annuler : ");
                        if (choixMorceau > 0 && choixMorceau <= morceaux.size()) {
                            naviguerMorceau(morceaux.get(choixMorceau - 1));
                        }
                    }
                }
                case 2 -> {
                    List<Album> albums = catalogue.getAlbumsParArtiste(artiste);
                    vue.afficherAlbums(albums);
                    if (!albums.isEmpty()) {
                        int choixAlbum = vue.lireInt("Voir les détails (numéro) ou 0 pour annuler : ");
                        if (choixAlbum > 0 && choixAlbum <= albums.size()) {
                            naviguerAlbum(albums.get(choixAlbum - 1));
                        }
                    }
                }
                case 3 -> retour = true;
                default -> vue.afficherErreur("Choix invalide ");
            }
        }
    }

    // ===== Écoute =====

    private void ecouterMorceauVisiteur(Visiteur visiteur) {
        if (!visiteur.peutEcouter()) {
            vue.afficherErreur("Limite d'écoutes atteinte \n Créez un compte pour écouter plus.");
            return;
        }
        vue.afficherMorceaux(catalogue.getMorceaux());
        int choix = vue.lireInt("Choisissez un morceau (numéro) ou 0 pour annuler : ");
        if (choix > 0 && choix <= catalogue.getMorceaux().size()) {
            Morceau morceau = catalogue.getMorceaux().get(choix - 1);
            visiteur.incrementerEcoutes();
            morceau.incrementerEcoutes();
            vue.simulerEcoute(morceau);
            vue.afficherMessage("Écoutes restantes : " + (visiteur.getLimiteEcoutes() - visiteur.getNbEcoutesSession()));
        }
    }

    private void ecouterMorceauAbonne(Abonne abonne) {
        vue.afficherMorceaux(catalogue.getMorceaux());
        int choix = vue.lireInt("Choisissez un morceau (numéro) ou 0 pour annuler : ");
        if (choix > 0 && choix <= catalogue.getMorceaux().size()) {
            Morceau morceau = catalogue.getMorceaux().get(choix - 1);
            abonne.ecouterMorceau(morceau);
            morceau.incrementerEcoutes();
            vue.simulerEcoute(morceau);
        }
    }

    // ===== Playlists =====

    private void gererPlaylists(Abonne abonne) {
        boolean retour = false;
        while (!retour) {
            vue.afficherMenuPlaylists();
            int choix = vue.lireInt("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String nom = vue.lireString("Nom de la playlist : ");
                    abonne.creerPlaylist(nom);
                    vue.afficherSucces("Playlist \"" + nom + "\" créée ");
                }
                case 2 -> vue.afficherMessage(abonne.getPlaylists().toString());
                case 3 -> {
                    if (abonne.getPlaylists().isEmpty()) {
                        vue.afficherErreur("Vous n'avez aucune playlist ");
                        break;
                    }
                    vue.afficherMessage(abonne.getPlaylists().toString());
                    int choixP = vue.lireInt("Choisissez une playlist (numéro) ou 0 pour annuler : ");
                    if (choixP < 1 || choixP > abonne.getPlaylists().size()) break;
                    Playlist playlist = abonne.getPlaylists().get(choixP - 1);
                    if (playlist.getMorceaux().isEmpty()) {
                        vue.afficherMessage("Cette playlist est vide ");
                    } else {
                        vue.afficherMessage("=== " + playlist.getNom() + " ===");
                        vue.afficherMorceaux(playlist.getMorceaux());
                    }
                }
                case 4 -> ajouterMorceauPlaylist(abonne);
                case 5 -> retirerMorceauPlaylist(abonne);
                case 6 -> renommerPlaylist(abonne);
                case 7 -> supprimerPlaylist(abonne);
                case 8 -> retour = true;
                default -> vue.afficherErreur("Choix invalide ");
            }
        }
    }

    private void ajouterMorceauPlaylist(Abonne abonne) {
        if (abonne.getPlaylists().isEmpty()) {
            vue.afficherErreur("Vous n'avez aucune playlist ");
            return;
        }
        vue.afficherMessage(abonne.getPlaylists().toString());
        int choixPlaylist = vue.lireInt("Choisissez une playlist (numéro) : ");
        if (choixPlaylist < 1 || choixPlaylist > abonne.getPlaylists().size()) {
            vue.afficherErreur("Numéro invalide ");
            return;
        }
        Playlist playlist = abonne.getPlaylists().get(choixPlaylist - 1);

        // Choix de la source
        vue.afficherMessage("Ajouter depuis :\n1. Le catalogue\n2. Une autre playlist");
        int source = vue.lireInt("Votre choix : ");

        if (source == 1) {
            // Depuis le catalogue avec critères de recherche
            vue.afficherMessage("Rechercher par :\n1. Titre\n2. Genre\n3. Artiste\n4. Tous les morceaux");
            int critere = vue.lireInt("Votre choix : ");
            List<Morceau> resultats = new ArrayList<>();
            switch (critere) {
                case 1 -> {
                    String titre = vue.lireString("Titre : ");
                    resultats = catalogue.rechercherMorceauxParTitre(titre);
                }
                case 2 -> {
                    String genre = vue.lireString("Genre : ");
                    resultats = catalogue.rechercherMorceauxParGenre(genre);
                }
                case 3 -> {
                    String nom = vue.lireString("Nom de l'artiste : ");
                    List<Artiste> artistes = catalogue.rechercherArtistesParNom(nom);
                    if (artistes.isEmpty()) {
                        vue.afficherErreur("Aucun artiste trouvé ");
                        return;
                    }
                    // Récupère tous les morceaux de l'artiste
                    for (Morceau m : catalogue.getMorceaux()) {
                        if (artistes.contains(m.getArtiste())) {
                            resultats.add(m);
                        }
                    }
                }
                case 4 -> resultats = catalogue.getMorceaux();
                default -> { vue.afficherErreur("Choix invalide "); return; }
            }

            if (resultats.isEmpty()) {
                vue.afficherMessage("Aucun morceau trouvé.");
                return;
            }
            vue.afficherMorceaux(resultats);
            int choixMorceau = vue.lireInt("Choisissez un morceau (numéro) : ");
            if (choixMorceau < 1 || choixMorceau > resultats.size()) {
                vue.afficherErreur("Numéro invalide ");
                return;
            }
            try {
                playlist.addMorceau(resultats.get(choixMorceau - 1));
                vue.afficherSucces("Morceau ajouté à la playlist ");
            } catch (IllegalArgumentException e) {
                vue.afficherErreur(e.getMessage());
            }

        } else if (source == 2) {
            // Depuis une autre playlist
            List<Playlist> autresPlaylists = new ArrayList<>(abonne.getPlaylists());
            autresPlaylists.remove(playlist);

            if (autresPlaylists.isEmpty()) {
                vue.afficherErreur("Vous n'avez pas d'autre playlist ");
                return;
            }
            vue.afficherMessage(autresPlaylists.toString());
            int choixAutre = vue.lireInt("Choisissez une playlist source (numéro) : ");
            if (choixAutre < 1 || choixAutre > autresPlaylists.size()) {
                vue.afficherErreur("Numéro invalide ");
                return;
            }
            Playlist source2 = autresPlaylists.get(choixAutre - 1);
            if (source2.getMorceaux().isEmpty()) {
                vue.afficherErreur("Cette playlist est vide ");
                return;
            }
            vue.afficherMorceaux(source2.getMorceaux());
            int choixMorceau = vue.lireInt("Choisissez un morceau (numéro) : ");
            if (choixMorceau < 1 || choixMorceau > source2.getMorceaux().size()) {
                vue.afficherErreur("Numéro invalide ");
                return;
            }
            try {
                playlist.addMorceau(source2.getMorceaux().get(choixMorceau - 1));
                vue.afficherSucces("Morceau ajouté à la playlist ");
            } catch (IllegalArgumentException e) {
                vue.afficherErreur(e.getMessage());
            }
        } else {
            vue.afficherErreur("Choix invalide ");
        }
    }

    private void retirerMorceauPlaylist(Abonne abonne) {
        if (abonne.getPlaylists().isEmpty()) {
            vue.afficherErreur("Vous n'avez aucune playlist ");
            return;
        }
        vue.afficherMessage(abonne.getPlaylists().toString());
        int choixPlaylist = vue.lireInt("Choisissez une playlist (numéro) : ");
        if (choixPlaylist < 1 || choixPlaylist > abonne.getPlaylists().size()) {
            vue.afficherErreur("Numéro invalide ");
            return;
        }
        Playlist playlist = abonne.getPlaylists().get(choixPlaylist - 1);
        vue.afficherMorceaux(playlist.getMorceaux());
        int choixMorceau = vue.lireInt("Choisissez un morceau à retirer (numéro) : ");
        if (choixMorceau < 1 || choixMorceau > playlist.getMorceaux().size()) {
            vue.afficherErreur("Numéro invalide ");
            return;
        }
        playlist.removeMorceau(playlist.getMorceaux().get(choixMorceau - 1));
        vue.afficherSucces("Morceau retiré de la playlist ");
    }

    private void supprimerPlaylist(Abonne abonne) {
        if (abonne.getPlaylists().isEmpty()) {
            vue.afficherErreur("Vous n'avez aucune playlist ");
            return;
        }
        vue.afficherMessage(abonne.getPlaylists().toString());
        int choix = vue.lireInt("Choisissez une playlist à supprimer (numéro) : ");
        if (choix < 1 || choix > abonne.getPlaylists().size()) {
            vue.afficherErreur("Numéro invalide ");
            return;
        }
        abonne.supprimerPlaylist(abonne.getPlaylists().get(choix - 1));
        vue.afficherSucces("Playlist supprimée ");
    }

    private void renommerPlaylist(Abonne abonne) {
        if (abonne.getPlaylists().isEmpty()) {
            vue.afficherErreur("Vous n'avez aucune playlist ");
            return;
        }
        vue.afficherMessage(abonne.getPlaylists().toString());
        int choix = vue.lireInt("Choisissez une playlist (numéro) : ");
        if (choix < 1 || choix > abonne.getPlaylists().size()) {
            vue.afficherErreur("Numéro invalide ");
            return;
        }
        String nouveauNom = vue.lireString("Nouveau nom : ");
        if (nouveauNom.trim().isEmpty()) {
            vue.afficherErreur("Le nom ne peut pas être vide ");
            return;
        }
        abonne.getPlaylists().get(choix - 1).setNom(nouveauNom);
        vue.afficherSucces("Playlist renommée en \"" + nouveauNom + "\" !");
    }

    // ===== Avis =====

    private void laisserAvis(Abonne abonne) {
        vue.afficherMorceaux(catalogue.getMorceaux());
        int choix = vue.lireInt("Choisissez un morceau (numéro) ou 0 pour annuler : ");
        if (choix < 1 || choix > catalogue.getMorceaux().size()) return;
        Morceau morceau = catalogue.getMorceaux().get(choix - 1);

        Avis avisExistant = morceau.getAvisDeAbonne(abonne);

        if (avisExistant != null) {
            vue.afficherMessage("Vous avez déjà un avis sur ce morceau : " + avisExistant);
            vue.afficherMessage("1. Modifier  2. Supprimer  3. Annuler");
            int action = vue.lireInt("Votre choix : ");
            switch (action) {
                case 1 -> {
                    int note = vue.lireInt("Nouvelle note (1-5) : ");
                    String commentaire = vue.lireString("Nouveau commentaire : ");
                    try {
                        avisExistant.modifier(note, commentaire);
                        vue.afficherSucces("Avis modifié ");
                    } catch (IllegalArgumentException e) {
                        vue.afficherErreur(e.getMessage());
                    }
                }
                case 2 -> {
                    morceau.supprimerAvisDeAbonne(abonne);
                    vue.afficherSucces("Avis supprimé ");
                }
                default -> vue.afficherMessage("Annulé");
            }
        } else {
            int note = vue.lireInt("Votre note (1-5) : ");
            String commentaire = vue.lireString("Votre commentaire : ");
            try {
                morceau.addAvis(new Avis(note, commentaire, abonne));
                vue.afficherSucces("Avis ajouté ");
            } catch (IllegalArgumentException e) {
                vue.afficherErreur(e.getMessage());
            }
        }
    }

    // ===== Gestion Admin =====

    private void ajouterMorceau() {
        String titre = vue.lireString("Titre du morceau : ");
        int duree = vue.lireInt("Durée (en secondes) : ");
        String genre = vue.lireString("Genre : ");
        vue.afficherArtistes(catalogue.getArtistes());
        int choix = vue.lireInt("Choisissez un artiste (numéro) : ");
        if (choix < 1 || choix > catalogue.getArtistes().size()) {
            vue.afficherErreur("Numéro invalide ");
            return;
        }
        Morceau morceau = new Morceau(titre, duree, genre, catalogue.getArtistes().get(choix - 1));
        admin.ajouterMorceau(catalogue, morceau);
        vue.afficherSucces("Morceau ajouté au catalogue ");
    }

    private void supprimerMorceau() {
        vue.afficherMorceaux(catalogue.getMorceaux());
        int choix = vue.lireInt("Choisissez un morceau à supprimer (numéro) ou 0 pour annuler : ");
        if (choix < 1 || choix > catalogue.getMorceaux().size()) return;
        admin.supprimerMorceau(catalogue, catalogue.getMorceaux().get(choix - 1));
        vue.afficherSucces("Morceau supprimé ");
    }

    private void ajouterAlbum() {
        String titre = vue.lireString("Titre de l'album : ");
        int annee = vue.lireInt("Année de sortie : ");
        String type = vue.lireString("Type (studio/live/compilation) : ");
        vue.afficherArtistes(catalogue.getArtistes());
        int choix = vue.lireInt("Choisissez un artiste (numéro) : ");
        if (choix < 1 || choix > catalogue.getArtistes().size()) {
            vue.afficherErreur("Numéro invalide ");
            return;
        }
        Album album = new Album(titre, annee, type, catalogue.getArtistes().get(choix - 1));
        admin.ajouterAlbum(catalogue, album);
        vue.afficherSucces("Album ajouté au catalogue ");
    }

    private void supprimerAlbum() {
        vue.afficherAlbums(catalogue.getAlbums());
        int choix = vue.lireInt("Choisissez un album à supprimer (numéro) ou 0 pour annuler : ");
        if (choix < 1 || choix > catalogue.getAlbums().size()) return;
        admin.supprimerAlbum(catalogue, catalogue.getAlbums().get(choix - 1));
        vue.afficherSucces("Album supprimé ");
    }

    private void ajouterArtiste() {
        String type = vue.lireString("Type (solo/groupe) : ");
        String nom = vue.lireString("Nom : ");
        String bio = vue.lireString("Biographie : ");
        if (type.equalsIgnoreCase("solo")) {
            String prenom = vue.lireString("Prénom : ");
            String dateNaissance = vue.lireString("Date de naissance (JJ/MM/AAAA) : ");
            ArtisteSeul artiste = new ArtisteSeul(nom, prenom, bio, dateNaissance);
            admin.ajouterArtiste(catalogue, artiste);
        } else {
            int annee = vue.lireInt("Année de formation : ");
            Groupe groupe = new Groupe(nom, bio, annee);
            admin.ajouterArtiste(catalogue, groupe);
        }
        vue.afficherSucces("Artiste ajouté au catalogue ");
    }

    private void supprimerArtiste() {
        vue.afficherArtistes(catalogue.getArtistes());
        int choix = vue.lireInt("Choisissez un artiste à supprimer (numéro) ou 0 pour annuler : ");
        if (choix < 1 || choix > catalogue.getArtistes().size()) return;
        admin.supprimerArtiste(catalogue, catalogue.getArtistes().get(choix - 1));
        vue.afficherSucces("Artiste supprimé ");
    }

    private void gererAbonnes() {
        if (abonnes.isEmpty()) {
            vue.afficherMessage("Aucun abonné enregistré");
            return;
        }
        for (int i = 0; i < abonnes.size(); i++) {
            System.out.println((i + 1) + ". " + abonnes.get(i));
        }
        int choix = vue.lireInt("Choisissez un abonné (numéro) ou 0 pour annuler : ");
        if (choix < 1 || choix > abonnes.size()) return;
        Abonne abonne = abonnes.get(choix - 1);
        vue.afficherMessage("1. Suspendre  2. Réactiver  3. Supprimer");
        int action = vue.lireInt("Votre choix : ");
        switch (action) {
            case 1 -> { admin.suspendreCompte(abonne); vue.afficherSucces("Compte suspendu "); }
            case 2 -> { admin.reactiverCompte(abonne); vue.afficherSucces("Compte réactivé "); }
            case 3 -> { abonnes.remove(abonne); vue.afficherSucces("Compte supprimé "); }
            default -> vue.afficherErreur("Choix invalide ");
        }
    }



    // ===== Méthodes graphiques =====

    /**
     * Sauvegarde les données (appelée à la fermeture de la fenêtre).
     */
    public void sauvegarderDonnees() {
        GestionnaireData.sauvegarderCatalogue(catalogue);
        GestionnaireData.sauvegarderAbonnes(abonnes);
    }

    /**
     * Lance le mode graphique.
     */
    public void lancerGraphique() {
        SwingUtilities.invokeLater(() -> new GraphiqueView(this));
    }

    /**
     * Lance le mode visiteur en graphique.
     */
    public void lancerModeVisiteur(GraphiqueView vue) {
        vue.afficherMenuVisiteur(new Visiteur());
    }

    /**
     * Connecte un admin en mode graphique.
     */
    public void connecterAdmin(String id, String mdp, GraphiqueView vue) {
        if (admin.getIdentifiant().equals(id) && admin.verifierMotDePasse(mdp)) {
            vue.afficherMenuAdmin();
        } else {
            vue.afficherErreur("Identifiants incorrects ");
        }
    }

    /**
     * Connecte un abonné en mode graphique.
     */
    public void connecterAbonneGraphique(String id, String mdp, GraphiqueView vue) {
        for (Abonne a : abonnes) {
            if (a.getIdentifiant().equals(id) && a.verifierMotDePasse(mdp)) {
                if (!a.estActif()) {
                    vue.afficherErreur("Votre compte est suspendu ");
                    return;
                }
                vue.afficherMenuAbonne(a);
                return;
            }
        }
        vue.afficherErreur("Identifiants incorrects ");
    }

    /**
     * Crée un compte abonné en mode graphique.
     */
    public void creerCompteGraphique(String id, String mdp, String email, GraphiqueView vue) {
        for (Abonne a : abonnes) {
            if (a.getIdentifiant().equals(id)) {
                vue.afficherErreur("Cet identifiant est déjà pris ");
                return;
            }
        }
        abonnes.add(new Abonne(id, mdp, email));
        vue.afficherSucces("Compte créé avec succès \n Bienvenue " + id);
    }

    /**
     * Recherche un morceau en mode graphique.
     */
    public void rechercherMorceauGraphique(GraphiqueView vue) {
        String titre = JOptionPane.showInputDialog(vue, "Titre du morceau :");
        if (titre == null || titre.trim().isEmpty()) return;
        List<Morceau> resultats = catalogue.rechercherMorceauxParTitre(titre);
        if (resultats.isEmpty()) { vue.afficherMessage("Aucun morceau trouvé"); return; }
        String[] options = resultats.stream().map(Morceau::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Sélectionnez un morceau :",
                "Résultats", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix >= 0) naviguerMorceauGraphique(resultats.get(choix), vue);
    }

    public void rechercherAlbumGraphique(GraphiqueView vue) {
        String titre = JOptionPane.showInputDialog(vue, "Titre de l'album :");
        if (titre == null || titre.trim().isEmpty()) return;
        List<Album> resultats = catalogue.rechercherAlbumsParTitre(titre);
        if (resultats.isEmpty()) { vue.afficherMessage("Aucun album trouvé"); return; }
        String[] options = resultats.stream().map(Album::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Sélectionnez un album :",
                "Résultats", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix >= 0) naviguerAlbumGraphique(resultats.get(choix), vue);
    }

    public void rechercherArtisteGraphique(GraphiqueView vue) {
        String nom = JOptionPane.showInputDialog(vue, "Nom de l'artiste :");
        if (nom == null || nom.trim().isEmpty()) return;
        List<Artiste> resultats = catalogue.rechercherArtistesParNom(nom);
        if (resultats.isEmpty()) { vue.afficherMessage("Aucun artiste trouvé"); return; }
        String[] options = resultats.stream().map(Artiste::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Sélectionnez un artiste :",
                "Résultats", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix >= 0) naviguerArtisteGraphique(resultats.get(choix), vue);
    }

    /**
     * Navigation depuis un morceau en graphique.
     */
    private void naviguerMorceauGraphique(Morceau morceau, GraphiqueView vue) {
        StringBuilder sb = new StringBuilder();
        sb.append("Titre    : ").append(morceau.getTitre()).append("\n");
        sb.append("Artiste  : ").append(morceau.getArtiste()).append("\n");
        sb.append("Durée    : ").append(morceau.getDureeFormatee()).append("\n");
        sb.append("Genre    : ").append(morceau.getGenre()).append("\n");
        sb.append("Note moy.: ").append(morceau.getNoteMoyenne()).append("/5\n");
        if (!morceau.getAvis().isEmpty()) {
            sb.append("Avis :\n");
            for (Avis a : morceau.getAvis()) {
                sb.append("  - ").append(a).append("\n");
            }
        }

        String[] actions = {"Voir la fiche de l'artiste", "Voir les albums contenant ce morceau", "Fermer"};
        int choix = JOptionPane.showOptionDialog(vue, sb.toString(),
                "🎵 " + morceau.getTitre(), JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, actions, actions[2]);

        switch (choix) {
            case 0 -> naviguerArtisteGraphique(morceau.getArtiste(), vue);
            case 1 -> {
                List<Album> albums = catalogue.getAlbumsContenant(morceau);
                if (albums.isEmpty()) { vue.afficherMessage("Aucun album contenant ce morceau"); return; }
                String[] optionsAlbums = albums.stream().map(Album::toString).toArray(String[]::new);
                int choixAlbum = JOptionPane.showOptionDialog(vue, "Sélectionnez un album :",
                        "Albums", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, optionsAlbums, optionsAlbums[0]);
                if (choixAlbum >= 0) naviguerAlbumGraphique(albums.get(choixAlbum), vue);
            }
        }
    }

    /**
     * Navigation depuis un album en graphique.
     */
    private void naviguerAlbumGraphique(Album album, GraphiqueView vue) {
        StringBuilder sb = new StringBuilder();
        sb.append("Titre    : ").append(album.getTitre()).append("\n");
        sb.append("Artiste  : ").append(album.getArtiste()).append("\n");
        sb.append("Année    : ").append(album.getAnnee()).append("\n");
        sb.append("Type     : ").append(album.getType()).append("\n");
        sb.append("Morceaux :\n");
        for (Morceau m : album.getMorceaux()) {
            sb.append("  - ").append(m.toString()).append("\n");
        }

        String[] actions = {"Voir la fiche de l'artiste", "Voir les détails d'un morceau", "Fermer"};
        int choix = JOptionPane.showOptionDialog(vue, sb.toString(),
                "💿 " + album.getTitre(), JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, actions, actions[2]);

        switch (choix) {
            case 0 -> naviguerArtisteGraphique(album.getArtiste(), vue);
            case 1 -> {
                if (album.getMorceaux().isEmpty()) { vue.afficherErreur("Cet album est vide "); return; }
                String[] optionsMorceaux = album.getMorceaux().stream().map(Morceau::toString).toArray(String[]::new);
                int choixMorceau = JOptionPane.showOptionDialog(vue, "Sélectionnez un morceau :",
                        "Morceaux", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, optionsMorceaux, optionsMorceaux[0]);
                if (choixMorceau >= 0) naviguerMorceauGraphique(album.getMorceaux().get(choixMorceau), vue);
            }
        }
    }

    /**
     * Navigation depuis un artiste en graphique.
     */
    private void naviguerArtisteGraphique(Artiste artiste, GraphiqueView vue) {
        String[] actions = {"Voir les morceaux", "Voir les albums", "Fermer"};
        int choix = JOptionPane.showOptionDialog(vue, artiste.getDescription(),
                "🎤 " + artiste.getNom(), JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, actions, actions[2]);

        switch (choix) {
            case 0 -> {
                List<Morceau> morceaux = catalogue.getMorceauxParArtiste(artiste);
                if (morceaux.isEmpty()) { vue.afficherMessage("Aucun morceau trouvé"); return; }
                String[] optionsMorceaux = morceaux.stream().map(Morceau::toString).toArray(String[]::new);
                int choixMorceau = JOptionPane.showOptionDialog(vue, "Sélectionnez un morceau :",
                        "Morceaux", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, optionsMorceaux, optionsMorceaux[0]);
                if (choixMorceau >= 0) naviguerMorceauGraphique(morceaux.get(choixMorceau), vue);
            }
            case 1 -> {
                List<Album> albums = catalogue.getAlbumsParArtiste(artiste);
                if (albums.isEmpty()) { vue.afficherMessage("Aucun album trouvé"); return; }
                String[] optionsAlbums = albums.stream().map(Album::toString).toArray(String[]::new);
                int choixAlbum = JOptionPane.showOptionDialog(vue, "Sélectionnez un album :",
                        "Albums", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, optionsAlbums, optionsAlbums[0]);
                if (choixAlbum >= 0) naviguerAlbumGraphique(albums.get(choixAlbum), vue);
            }
        }
    }

    /**
     * Écoute un morceau en mode visiteur graphique.
     */
    public void ecouterMorceauVisiteurGraphique(Visiteur visiteur, GraphiqueView vue) {
        if (!visiteur.peutEcouter()) {
            vue.afficherErreur("Limite d'écoutes atteinte \n Créez un compte pour écouter plus");
            return;
        }
        String[] options = catalogue.getMorceaux().stream().map(Morceau::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Choisissez un morceau :",
                "Écouter", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix >= 0) {
            Morceau morceau = catalogue.getMorceaux().get(choix);
            visiteur.incrementerEcoutes();
            morceau.incrementerEcoutes();
            simulerEcouteGraphique(morceau, vue);
            vue.afficherMenuVisiteur(visiteur);
        }
    }

    /**
     * Écoute un morceau en mode abonné graphique.
     */
    public void ecouterMorceauAbonneGraphique(Abonne abonne, GraphiqueView vue) {
        String[] options = catalogue.getMorceaux().stream().map(Morceau::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Choisissez un morceau :",
                "Écouter", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix >= 0) {
            Morceau morceau = catalogue.getMorceaux().get(choix);
            abonne.ecouterMorceau(morceau);
            morceau.incrementerEcoutes();
            simulerEcouteGraphique(morceau, vue);
        }
    }

    /**
     * Simule l'écoute d'un morceau avec une barre de progression graphique.
     */
    private void simulerEcouteGraphique(Morceau morceau, GraphiqueView vue) {
        JDialog dialog = new JDialog(vue, " Lecture en cours", true);
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(vue);
        dialog.setLayout(new BorderLayout());

        JLabel label = new JLabel("Lecture : " + morceau.getTitre(), SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        JProgressBar barre = new JProgressBar(0, 100);
        barre.setStringPainted(true);
        barre.setForeground(new Color(29, 185, 84));

        dialog.add(label, BorderLayout.NORTH);
        dialog.add(barre, BorderLayout.CENTER);

        Timer timer = new Timer(50, null);
        timer.addActionListener(e -> {
            int val = barre.getValue() + 5;
            if (val >= 100) {
                barre.setValue(100);
                timer.stop();
                dialog.dispose();
            } else {
                barre.setValue(val);
            }
        });
        timer.start();
        dialog.setVisible(true);
    }

    /**
     * Laisse un avis en mode graphique.
     */
    public void laisserAvisGraphique(Abonne abonne, GraphiqueView vue) {
        String[] options = catalogue.getMorceaux().stream().map(Morceau::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Choisissez un morceau :",
                "Avis", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix < 0) return;
        Morceau morceau = catalogue.getMorceaux().get(choix);

        Avis avisExistant = morceau.getAvisDeAbonne(abonne);

        if (avisExistant != null) {
            String[] actions = {"Modifier", "Supprimer", "Annuler"};
            int action = JOptionPane.showOptionDialog(vue,
                    "Vous avez déjà un avis : " + avisExistant + "\nQue voulez-vous faire ?",
                    "Avis existant", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, actions, actions[0]);
            switch (action) {
                case 0 -> {
                    String[] notes = {"1", "2", "3", "4", "5"};
                    int noteChoix = JOptionPane.showOptionDialog(vue, "Nouvelle note :",
                            "Note", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, notes, notes[avisExistant.getNote() - 1]);
                    if (noteChoix < 0) return;
                    String commentaire = JOptionPane.showInputDialog(vue, "Nouveau commentaire :", avisExistant.getCommentaire());
                    if (commentaire == null) return;
                    try {
                        avisExistant.modifier(noteChoix + 1, commentaire);
                        vue.afficherSucces("Avis modifié ");
                    } catch (IllegalArgumentException e) {
                        vue.afficherErreur(e.getMessage());
                    }
                }
                case 1 -> {
                    morceau.supprimerAvisDeAbonne(abonne);
                    vue.afficherSucces("Avis supprimé ");
                }
            }
        } else {
            String[] notes = {"1", "2", "3", "4", "5"};
            int noteChoix = JOptionPane.showOptionDialog(vue, "Votre note :",
                    "Note", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, notes, notes[4]);
            if (noteChoix < 0) return;
            String commentaire = JOptionPane.showInputDialog(vue, "Votre commentaire :");
            if (commentaire == null) return;
            try {
                morceau.addAvis(new Avis(noteChoix + 1, commentaire, abonne));
                vue.afficherSucces("Avis ajouté ");
            } catch (IllegalArgumentException e) {
                vue.afficherErreur(e.getMessage());
            }
        }
    }
    /**
     * Ajoute un morceau à une playlist en mode graphique.
     */
    public void ajouterMorceauPlaylistGraphique(Abonne abonne, int idxPlaylist, GraphiqueView vue) {
        Playlist playlist = abonne.getPlaylists().get(idxPlaylist);

        // Choix de la source
        String[] sources = {"Depuis le catalogue", "Depuis une autre playlist"};
        int source = JOptionPane.showOptionDialog(vue, "Ajouter depuis :",
                "Source", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, sources, sources[0]);
        if (source < 0) return;

        if (source == 0) {
            // Depuis le catalogue avec critères
            String[] criteres = {"Titre", "Genre", "Artiste", "Tous les morceaux"};
            int critere = JOptionPane.showOptionDialog(vue, "Rechercher par :",
                    "Critère", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, criteres, criteres[0]);
            if (critere < 0) return;

            List<Morceau> resultats = new ArrayList<>();
            switch (critere) {
                case 0 -> {
                    String titre = JOptionPane.showInputDialog(vue, "Titre :");
                    if (titre == null || titre.trim().isEmpty()) return;
                    resultats = catalogue.rechercherMorceauxParTitre(titre);
                }
                case 1 -> {
                    String genre = JOptionPane.showInputDialog(vue, "Genre :");
                    if (genre == null || genre.trim().isEmpty()) return;
                    resultats = catalogue.rechercherMorceauxParGenre(genre);
                }
                case 2 -> {
                    String nom = JOptionPane.showInputDialog(vue, "Nom de l'artiste :");
                    if (nom == null || nom.trim().isEmpty()) return;
                    List<Artiste> artistes = catalogue.rechercherArtistesParNom(nom);
                    if (artistes.isEmpty()) {
                        vue.afficherErreur("Aucun artiste trouvé ");
                        return;
                    }
                    for (Morceau m : catalogue.getMorceaux()) {
                        if (artistes.contains(m.getArtiste())) {
                            resultats.add(m);
                        }
                    }
                }
                case 3 -> resultats = catalogue.getMorceaux();
            }

            if (resultats.isEmpty()) {
                vue.afficherMessage("Aucun morceau trouvé");
                return;
            }

            String[] options = resultats.stream().map(Morceau::toString).toArray(String[]::new);
            int choix = JOptionPane.showOptionDialog(vue, "Choisissez un morceau :",
                    "Morceau", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            if (choix < 0) return;

            try {
                playlist.addMorceau(resultats.get(choix));
                vue.afficherSucces("Morceau ajouté ");
                vue.afficherGestionPlaylists(abonne);
            } catch (IllegalArgumentException e) {
                vue.afficherErreur(e.getMessage());
            }

        } else {
            // Depuis une autre playlist
            List<Playlist> autresPlaylists = new ArrayList<>(abonne.getPlaylists());
            autresPlaylists.remove(playlist);

            if (autresPlaylists.isEmpty()) {
                vue.afficherErreur("Vous n'avez pas d'autre playlist ");
                return;
            }

            String[] optionsPlaylists = autresPlaylists.stream()
                    .map(p -> p.getNom() + " (" + p.getNbMorceaux() + " morceaux)")
                    .toArray(String[]::new);
            int choixPlaylist = JOptionPane.showOptionDialog(vue, "Choisissez une playlist source :",
                    "Playlist", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, optionsPlaylists, optionsPlaylists[0]);
            if (choixPlaylist < 0) return;

            Playlist sourcePlaylist = autresPlaylists.get(choixPlaylist);
            if (sourcePlaylist.getMorceaux().isEmpty()) {
                vue.afficherErreur("Cette playlist est vide ");
                return;
            }

            String[] optionsMorceaux = sourcePlaylist.getMorceaux().stream()
                    .map(Morceau::toString).toArray(String[]::new);
            int choixMorceau = JOptionPane.showOptionDialog(vue, "Choisissez un morceau :",
                    "Morceau", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, optionsMorceaux, optionsMorceaux[0]);
            if (choixMorceau < 0) return;

            try {
                playlist.addMorceau(sourcePlaylist.getMorceaux().get(choixMorceau));
                vue.afficherSucces("Morceau ajouté ");
                vue.afficherGestionPlaylists(abonne);
            } catch (IllegalArgumentException e) {
                vue.afficherErreur(e.getMessage());
            }
        }
    }

    /**
     * Retire un morceau d'une playlist en mode graphique.
     */
    public void retirerMorceauPlaylistGraphique(Abonne abonne, int idxPlaylist, GraphiqueView vue) {
        Playlist playlist = abonne.getPlaylists().get(idxPlaylist);
        if (playlist.getMorceaux().isEmpty()) {
            vue.afficherErreur("Cette playlist est vide ");
            return;
        }
        String[] options = playlist.getMorceaux().stream().map(Morceau::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Choisissez un morceau à retirer :",
                "Retirer de la playlist", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix >= 0) {
            playlist.removeMorceau(playlist.getMorceaux().get(choix));
            vue.afficherSucces("Morceau retiré ");
            vue.afficherGestionPlaylists(abonne);
        }
    }

    /**
     * Affiche les statistiques en mode graphique.
     */
    public void afficherStatistiquesGraphique(GraphiqueView vue) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Statistiques générales =====\n\n");
        sb.append("Morceaux      : ").append(catalogue.getNbMorceaux()).append("\n");
        sb.append("Albums        : ").append(catalogue.getNbAlbums()).append("\n");
        sb.append("Artistes      : ").append(catalogue.getNbArtistes()).append("\n");
        sb.append("Abonnés       : ").append(abonnes.size()).append("\n");
        sb.append("Total écoutes : ").append(catalogue.getNbTotalEcoutes()).append("\n\n");

        sb.append("===== Top morceaux les plus écoutés =====\n\n");
        List<Morceau> parEcoutes = catalogue.getMorceauxParEcoutes();
        for (int i = 0; i < Math.min(3, parEcoutes.size()); i++) {
            Morceau m = parEcoutes.get(i);
            sb.append((i + 1)).append(". ").append(m.getTitre())
                    .append(" — ").append(m.getNbEcoutes()).append(" écoutes\n");
        }

        sb.append("\n===== Top albums les plus écoutés =====\n\n");
        List<Album> albumsParEcoutes = catalogue.getAlbumsParEcoutes();
        for (int i = 0; i < Math.min(3, albumsParEcoutes.size()); i++) {
            Album a = albumsParEcoutes.get(i);
            sb.append((i + 1)).append(". ").append(a.getTitre())
                    .append(" — ").append(catalogue.getNbEcoutesAlbum(a)).append(" écoutes\n");
        }

        sb.append("\n===== Top artistes les plus écoutés =====\n\n");
        List<Artiste> artistesParEcoutes = catalogue.getArtistesParEcoutes();
        for (int i = 0; i < Math.min(3, artistesParEcoutes.size()); i++) {
            Artiste a = artistesParEcoutes.get(i);
            sb.append((i + 1)).append(". ").append(a.getNom())
                    .append(" — ").append(catalogue.getNbEcoutesArtiste(a)).append(" écoutes\n");
        }

        sb.append("\n===== Top morceaux les mieux notés =====\n\n");
        List<Morceau> parNotes = catalogue.getMorceauxParNotes();
        for (int i = 0; i < Math.min(3, parNotes.size()); i++) {
            Morceau m = parNotes.get(i);
            sb.append((i + 1)).append(". ").append(m.getTitre())
                    .append(" — ").append(m.getNoteMoyenne()).append("/5\n");
        }

        sb.append("\n===== Top morceaux les plus ajoutés en playlist =====\n\n");
        List<Morceau> parPlaylists = catalogue.getMorceauxParAjoutsPlaylist(abonnes);
        for (int i = 0; i < Math.min(3, parPlaylists.size()); i++) {
            Morceau m = parPlaylists.get(i);
            sb.append((i + 1)).append(". ").append(m.getTitre()).append("\n");
        }

        vue.afficherMessage(sb.toString());
    }

    /**
     * Gère les abonnés en mode graphique.
     */
    public void gererAbonnesGraphique(GraphiqueView vue) {
        if (abonnes.isEmpty()) {
            vue.afficherMessage("Aucun abonné enregistré");
            return;
        }
        String[] options = abonnes.stream().map(Abonne::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Choisissez un abonné :",
                "Gérer les abonnés", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix < 0) return;
        Abonne abonne = abonnes.get(choix);
        String[] actions = {"Suspendre", "Réactiver", "Supprimer"};
        int action = JOptionPane.showOptionDialog(vue, "Action pour " + abonne.getIdentifiant() + " :",
                "Action", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, actions, actions[0]);
        switch (action) {
            case 0 -> { admin.suspendreCompte(abonne); vue.afficherSucces("Compte suspendu "); }
            case 1 -> { admin.reactiverCompte(abonne); vue.afficherSucces("Compte réactivé "); }
            case 2 -> { abonnes.remove(abonne); vue.afficherSucces("Compte supprimé "); }
        }
    }

    /**
     * Ajoute un morceau en mode graphique.
     */
    public void ajouterMorceauGraphique(GraphiqueView vue) {
        String titre = JOptionPane.showInputDialog(vue, "Titre du morceau :");
        if (titre == null || titre.trim().isEmpty()) return;
        String dureeStr = JOptionPane.showInputDialog(vue, "Durée (en secondes) :");
        if (dureeStr == null) return;
        int duree;
        try { duree = Integer.parseInt(dureeStr); }
        catch (NumberFormatException e) { vue.afficherErreur("Durée invalide "); return; }
        String genre = JOptionPane.showInputDialog(vue, "Genre :");
        if (genre == null || genre.trim().isEmpty()) return;
        String[] artistesOptions = catalogue.getArtistes().stream().map(Artiste::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Choisissez un artiste :",
                "Artiste", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, artistesOptions, artistesOptions[0]);
        if (choix < 0) return;
        admin.ajouterMorceau(catalogue, new Morceau(titre, duree, genre, catalogue.getArtistes().get(choix)));
        vue.afficherSucces("Morceau ajouté ");
    }

    /**
     * Supprime un morceau en mode graphique.
     */
    public void supprimerMorceauGraphique(GraphiqueView vue) {
        if (catalogue.getMorceaux().isEmpty()) { vue.afficherErreur("Aucun morceau "); return; }
        String[] options = catalogue.getMorceaux().stream().map(Morceau::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Choisissez un morceau à supprimer :",
                "Supprimer morceau", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix >= 0) {
            admin.supprimerMorceau(catalogue, catalogue.getMorceaux().get(choix));
            vue.afficherSucces("Morceau supprimé ");
        }
    }

    /**
     * Ajoute un album en mode graphique.
     */
    public void ajouterAlbumGraphique(GraphiqueView vue) {
        String titre = JOptionPane.showInputDialog(vue, "Titre de l'album :");
        if (titre == null || titre.trim().isEmpty()) return;
        String anneeStr = JOptionPane.showInputDialog(vue, "Année de sortie :");
        if (anneeStr == null) return;
        int annee;
        try { annee = Integer.parseInt(anneeStr); }
        catch (NumberFormatException e) { vue.afficherErreur("Année invalide "); return; }
        String type = JOptionPane.showInputDialog(vue, "Type (studio/live/compilation) :");
        if (type == null || type.trim().isEmpty()) return;
        String[] artistesOptions = catalogue.getArtistes().stream().map(Artiste::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Choisissez un artiste :",
                "Artiste", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, artistesOptions, artistesOptions[0]);
        if (choix < 0) return;
        admin.ajouterAlbum(catalogue, new Album(titre, annee, type, catalogue.getArtistes().get(choix)));
        vue.afficherSucces("Album ajouté ");
    }

    /**
     * Supprime un album en mode graphique.
     */
    public void supprimerAlbumGraphique(GraphiqueView vue) {
        if (catalogue.getAlbums().isEmpty()) { vue.afficherErreur("Aucun album "); return; }
        String[] options = catalogue.getAlbums().stream().map(Album::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Choisissez un album à supprimer :",
                "Supprimer album", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix >= 0) {
            admin.supprimerAlbum(catalogue, catalogue.getAlbums().get(choix));
            vue.afficherSucces("Album supprimé ");
        }
    }

    /**
     * Ajoute un artiste en mode graphique.
     */
    public void ajouterArtisteGraphique(GraphiqueView vue) {
        String[] types = {"Solo", "Groupe"};
        int typeChoix = JOptionPane.showOptionDialog(vue, "Type d'artiste :",
                "Type", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, types, types[0]);
        if (typeChoix < 0) return;
        String nom = JOptionPane.showInputDialog(vue, "Nom :");
        if (nom == null || nom.trim().isEmpty()) return;
        String bio = JOptionPane.showInputDialog(vue, "Biographie :");
        if (bio == null) return;
        if (typeChoix == 0) {
            String prenom = JOptionPane.showInputDialog(vue, "Prénom :");
            if (prenom == null) return;
            String dateNaissance = JOptionPane.showInputDialog(vue, "Date de naissance (JJ/MM/AAAA) :");
            if (dateNaissance == null) return;
            admin.ajouterArtiste(catalogue, new ArtisteSeul(nom, prenom, bio, dateNaissance));
        } else {
            String anneeStr = JOptionPane.showInputDialog(vue, "Année de formation :");
            if (anneeStr == null) return;
            try {
                int annee = Integer.parseInt(anneeStr);
                admin.ajouterArtiste(catalogue, new Groupe(nom, bio, annee));
            } catch (NumberFormatException e) {
                vue.afficherErreur("Année invalide ");
            }
        }
        vue.afficherSucces("Artiste ajouté ");
    }

    /**
     * Supprime un artiste en mode graphique.
     */
    public void supprimerArtisteGraphique(GraphiqueView vue) {
        if (catalogue.getArtistes().isEmpty()) { vue.afficherErreur("Aucun artiste "); return; }
        String[] options = catalogue.getArtistes().stream().map(Artiste::toString).toArray(String[]::new);
        int choix = JOptionPane.showOptionDialog(vue, "Choisissez un artiste à supprimer :",
                "Supprimer artiste", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choix >= 0) {
            admin.supprimerArtiste(catalogue, catalogue.getArtistes().get(choix));
            vue.afficherSucces("Artiste supprimé ");
        }
    }
}