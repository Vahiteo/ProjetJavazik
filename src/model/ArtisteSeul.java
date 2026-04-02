package model;

/**
 * Classe représentant un artiste solo.
 * Hérite de la classe abstraite Artiste.
 */
public class ArtisteSeul extends Artiste {

    private String prenom;
    private String dateNaissance;

    /**
     * Constructeur de la classe ArtisteSeul.
     * @param nom le nom de famille de l'artiste
     * @param prenom le prénom de l'artiste
     * @param biographie courte biographie
     * @param dateNaissance date de naissance (format JJ/MM/AAAA)
     */
    public ArtisteSeul(String nom, String prenom, String biographie, String dateNaissance) {
        super(nom, biographie);
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    // Getters / Setters

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }

    /**
     * Retourne une description complète de l'artiste solo.
     */
    @Override
    public String getDescription() {
        return prenom + " " + getNom() + " - Artiste solo\n"
                + "Né(e) le : " + dateNaissance + "\n"
                + "Biographie : " + getBiographie();
    }

    @Override
    public String toString() {
        return prenom + " " + getNom();
    }
}