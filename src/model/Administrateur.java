package model;

/**
 * Classe représentant un administrateur du système.
 * Peut gérer le catalogue et les comptes abonnés.
 */
public class Administrateur extends Utilisateur {

    /**
     * Constructeur de la classe Administrateur.
     * @param identifiant l'identifiant de l'administrateur
     * @param motDePasse le mot de passe de l'administrateur
     */
    public Administrateur(String identifiant, String motDePasse) {
        super(identifiant, motDePasse);
    }

    // Gestion du catalogue

    /**
     * Ajoute un morceau au catalogue.
     * @param catalogue le catalogue
     * @param morceau le morceau à ajouter
     */
    public void ajouterMorceau(Catalogue catalogue, Morceau morceau) {
        catalogue.addMorceau(morceau);
    }

    /**
     * Supprime un morceau du catalogue.
     * @param catalogue le catalogue
     * @param morceau le morceau à supprimer
     */
    public void supprimerMorceau(Catalogue catalogue, Morceau morceau) {
        catalogue.removeMorceau(morceau);
    }

    /**
     * Ajoute un album au catalogue.
     * @param catalogue le catalogue
     * @param album l'album à ajouter
     */
    public void ajouterAlbum(Catalogue catalogue, Album album) {
        catalogue.addAlbum(album);
    }

    /**
     * Supprime un album du catalogue.
     * @param catalogue le catalogue
     * @param album l'album à supprimer
     */
    public void supprimerAlbum(Catalogue catalogue, Album album) {
        catalogue.removeAlbum(album);
    }

    /**
     * Ajoute un artiste au catalogue.
     * @param catalogue le catalogue
     * @param artiste l'artiste à ajouter
     */
    public void ajouterArtiste(Catalogue catalogue, Artiste artiste) {
        catalogue.addArtiste(artiste);
    }

    /**
     * Supprime un artiste du catalogue.
     * @param catalogue le catalogue
     * @param artiste l'artiste à supprimer
     */
    public void supprimerArtiste(Catalogue catalogue, Artiste artiste) {
        catalogue.removeArtiste(artiste);
    }

    // Gestion des abonnés

    /**
     * Suspend le compte d'un abonné.
     * @param abonne l'abonné à suspendre
     */
    public void suspendreCompte(Abonne abonne) {
        abonne.setStatut("suspendu");
    }

    /**
     * Réactive le compte d'un abonné.
     * @param abonne l'abonné à réactiver
     */
    public void reactiverCompte(Abonne abonne) {
        abonne.setStatut("actif");
    }

    @Override
    public String getRole() {
        return "Administrateur";
    }

    @Override
    public String toString() {
        return getIdentifiant() + " (Administrateur)";
    }
}