package view;

import model.*;
import java.util.List;
import java.util.Scanner;


/**
 * Classe gérant l'affichage en mode console.
 * Ne contient aucune logique métier.
 */
public class ConsoleView {

    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    // Lecture des entrées

    /**
     * Lit une saisie texte de l'utilisateur.
     * @param message le message à afficher
     * @return la saisie de l'utilisateur
     */
    public String lireString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Lit un entier saisi par l'utilisateur.
     * @param message le message à afficher
     * @return l'entier saisi
     */
    public int lireInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int valeur = Integer.parseInt(scanner.nextLine());
                return valeur;
            } catch (NumberFormatException e) {
                afficherErreur("Veuillez entrer un nombre valide ");
            }
        }
    }

    // Affichage des menus

    /**
     * Affiche le menu principal.
     */
    public void afficherMenuPrincipal() {
        System.out.println("\n==============================");
        System.out.println("            JAVAZIC           ");
        System.out.println("");
        System.out.println("  1. Se connecter (Admin)     ");
        System.out.println("  2. Se connecter (Client)    ");
        System.out.println("  3. Créer un compte          ");
        System.out.println("  4. Continuer en visiteur    ");
        System.out.println("  5. Quitter                  ");
        System.out.println("==============================");
    }

    /**
     * Affiche le menu visiteur.
     */
    public void afficherMenuVisiteur() {
        System.out.println("\n==============================");
        System.out.println("       MENU VISITEUR          ");
        System.out.println("");
        System.out.println("  1. Rechercher un morceau    ");
        System.out.println("  2. Rechercher un album      ");
        System.out.println("  3. Rechercher un artiste    ");
        System.out.println("  4. Écouter un morceau       ");
        System.out.println("  5. Retour                   ");
        System.out.println("==============================");
    }

    /**
     * Affiche le menu abonné.
     */
    public void afficherMenuAbonne() {
        System.out.println("\n==============================");
        System.out.println("        MENU ABONNÉ           ");
        System.out.println("");
        System.out.println("  1. Rechercher un morceau    ");
        System.out.println("  2. Rechercher un album      ");
        System.out.println("  3. Rechercher un artiste    ");
        System.out.println("  4. Écouter un morceau       ");
        System.out.println("  5. Gérer mes playlists      ");
        System.out.println("  6. Voir mon historique      ");
        System.out.println("  7. Gérer les avis           ");
        System.out.println("  8. Se déconnecter           ");
        System.out.println("==============================");
    }

    /**
     * Affiche le menu administrateur.
     */
    public void afficherMenuAdmin() {
        System.out.println("\n==============================");
        System.out.println("      MENU ADMINISTRATEUR     ");
        System.out.println("");
        System.out.println("  1. Ajouter un morceau       ");
        System.out.println("  2. Supprimer un morceau     ");
        System.out.println("  3. Ajouter un album         ");
        System.out.println("  4. Supprimer un album       ");
        System.out.println("  5. Ajouter un artiste       ");
        System.out.println("  6. Supprimer un artiste     ");
        System.out.println("  7. Gérer les abonnés        ");
        System.out.println("  8. Voir les statistiques    ");
        System.out.println("  9. Se déconnecter           ");
        System.out.println("==============================");
    }

    /**
     * Affiche le menu gestion des playlists.
     */
    public void afficherMenuPlaylists() {
        System.out.println("\n==============================");
        System.out.println("      GESTION PLAYLISTS       ");
        System.out.println("");
        System.out.println("  1. Créer une playlist       ");
        System.out.println("  2. Voir mes playlists       ");
        System.out.println("  3. Voir morceaux playlist   ");
        System.out.println("  4. Ajouter un morceau       ");
        System.out.println("  5. Retirer un morceau       ");
        System.out.println("  6. Renommer une playlist    ");
        System.out.println("  7. Supprimer une playlist   ");
        System.out.println("  8. Retour                   ");
        System.out.println("==============================");
    }

    // Affichage des données

    /**
     * Affiche une liste de morceaux numérotée.
     * @param morceaux la liste des morceaux à afficher
     */
    public void afficherMorceaux(List<Morceau> morceaux) {
        if (morceaux.isEmpty()) {
            afficherMessage("Aucun morceau trouvé");
            return;
        }
        System.out.println("\n----- Morceaux -----");
        for (int i = 0; i < morceaux.size(); i++) {
            System.out.println((i + 1) + ". " + morceaux.get(i));
        }
    }

    /**
     * Affiche une liste d'albums numérotée.
     * @param albums la liste des albums à afficher
     */
    public void afficherAlbums(List<Album> albums) {
        if (albums.isEmpty()) {
            afficherMessage("Aucun album trouvé");
            return;
        }
        System.out.println("\n----- Albums -----");
        for (int i = 0; i < albums.size(); i++) {
            System.out.println((i + 1) + ". " + albums.get(i));
        }
    }

    /**
     * Affiche une liste d'artistes numérotée.
     * @param artistes la liste des artistes à afficher
     */
    public void afficherArtistes(List<Artiste> artistes) {
        if (artistes.isEmpty()) {
            afficherMessage("Aucun artiste trouvé");
            return;
        }
        System.out.println("\n----- Artistes -----");
        for (int i = 0; i < artistes.size(); i++) {
            System.out.println((i + 1) + ". " + artistes.get(i));
        }
    }

    /**
     * Affiche les détails d'un morceau.
     * @param morceau le morceau à afficher
     */
    public void afficherDetailsMorceau(Morceau morceau) {
        System.out.println("\n----- Détails du morceau -----");
        System.out.println("Titre    : " + morceau.getTitre());
        System.out.println("Artiste  : " + morceau.getArtiste());
        System.out.println("Durée    : " + morceau.getDureeFormatee());
        System.out.println("Genre    : " + morceau.getGenre());
        System.out.println("Note moy.: " + morceau.getNoteMoyenne() + "/5");
        if (!morceau.getAvis().isEmpty()) {
            System.out.println("Avis :");
            for (Avis a : morceau.getAvis()) {
                System.out.println("  - " + a);
            }
        }
    }

    /**
     * Affiche les détails d'un album.
     * @param album l'album à afficher
     */
    public void afficherDetailsAlbum(Album album) {
        System.out.println("\n----- Détails de l'album -----");
        System.out.println("Titre    : " + album.getTitre());
        System.out.println("Artiste  : " + album.getArtiste());
        System.out.println("Année    : " + album.getAnnee());
        System.out.println("Type     : " + album.getType());
        System.out.println("Morceaux :");
        for (Morceau m : album.getMorceaux()) {
            System.out.println("  - " + m);
        }
    }

    /**
     * Simule l'écoute d'un morceau avec une barre de progression.
     * @param morceau le morceau à écouter
     */
    public void simulerEcoute(Morceau morceau) {
        System.out.println("\n Lecture : " + morceau.getTitre());
        System.out.print("[");
        for (int i = 0; i < 20; i++) {
            System.out.print("=");
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        System.out.println("] ");
        System.out.println("Lecture terminée ");
    }

    /**
     * Affiche les statistiques générales.
     * @param catalogue le catalogue
     * @param abonnes la liste des abonnés
     */
    public void afficherStatistiques(Catalogue catalogue, List<Abonne> abonnes) {
        System.out.println("\n----- Statistiques -----");
        System.out.println("Morceaux  : " + catalogue.getNbMorceaux());
        System.out.println("Albums    : " + catalogue.getNbAlbums());
        System.out.println("Artistes  : " + catalogue.getNbArtistes());
        System.out.println("Abonnés   : " + abonnes.size());
    }

    // Messages

    /**
     * Affiche un message simple.
     * @param message le message à afficher
     */
    public void afficherMessage(String message) {
        System.out.println("\n" + message);
    }

    /**
     * Affiche un message d'erreur.
     * @param message le message d'erreur
     */
    public void afficherErreur(String message) {
        System.out.println("\n Erreur : " + message);
    }

    /**
     * Affiche un message de succès.
     * @param message le message de succès
     */
    public void afficherSucces(String message) {
        System.out.println("\n " + message);
    }

    /**
     * Affiche les statistiques évoluées.
     * @param catalogue le catalogue
     * @param abonnes la liste des abonnés
     */
    public void afficherStatistiquesEvoluees(Catalogue catalogue, List<Abonne> abonnes) {
        System.out.println("\n----- Statistiques générales -----");
        System.out.println("Morceaux       : " + catalogue.getNbMorceaux());
        System.out.println("Albums         : " + catalogue.getNbAlbums());
        System.out.println("Artistes       : " + catalogue.getNbArtistes());
        System.out.println("Abonnés        : " + abonnes.size());
        System.out.println("Total écoutes  : " + catalogue.getNbTotalEcoutes());

        System.out.println("\n----- Top morceaux les plus écoutés -----");
        List<Morceau> parEcoutes = catalogue.getMorceauxParEcoutes();
        for (int i = 0; i < Math.min(3, parEcoutes.size()); i++) {
            Morceau m = parEcoutes.get(i);
            System.out.println((i + 1) + ". " + m.getTitre()
                    + " — " + m.getNbEcoutes() + " écoutes");
        }

        System.out.println("\n----- Top albums les plus écoutés -----");
        List<Album> albumsParEcoutes = catalogue.getAlbumsParEcoutes();
        for (int i = 0; i < Math.min(3, albumsParEcoutes.size()); i++) {
            Album a = albumsParEcoutes.get(i);
            System.out.println((i + 1) + ". " + a.getTitre()
                    + " — " + catalogue.getNbEcoutesAlbum(a) + " écoutes");
        }

        System.out.println("\n----- Top artistes les plus écoutés -----");
        List<Artiste> artistesParEcoutes = catalogue.getArtistesParEcoutes();
        for (int i = 0; i < Math.min(3, artistesParEcoutes.size()); i++) {
            Artiste a = artistesParEcoutes.get(i);
            System.out.println((i + 1) + ". " + a.getNom()
                    + " — " + catalogue.getNbEcoutesArtiste(a) + " écoutes");
        }

        System.out.println("\n----- Top morceaux les mieux notés -----");
        List<Morceau> parNotes = catalogue.getMorceauxParNotes();
        for (int i = 0; i < Math.min(3, parNotes.size()); i++) {
            Morceau m = parNotes.get(i);
            System.out.println((i + 1) + ". " + m.getTitre()
                    + " — " + m.getNoteMoyenne() + "/5");
        }

        System.out.println("\n----- Top morceaux les plus ajoutés en playlist -----");
        List<Morceau> parPlaylists = catalogue.getMorceauxParAjoutsPlaylist(abonnes);
        for (int i = 0; i < Math.min(3, parPlaylists.size()); i++) {
            Morceau m = parPlaylists.get(i);
            System.out.println((i + 1) + ". " + m.getTitre());
        }
    }
}