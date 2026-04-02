package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une playlist d'un abonné.
 */
public class Playlist implements Serializable {

    private String nom;
    private Abonne proprietaire;
    private List<Morceau> morceaux;
    private String dateCreation;

    /**
     * Constructeur de la classe Playlist.
     * @param nom le nom de la playlist
     * @param proprietaire l'abonné propriétaire de la playlist
     */
    public Playlist(String nom, Abonne proprietaire) {
        this.nom = nom;
        this.proprietaire = proprietaire;
        this.morceaux = new ArrayList<>();
        this.dateCreation = new java.util.Date().toString();
    }

    // ===== Getters / Setters =====

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Abonne getProprietaire() { return proprietaire; }

    public List<Morceau> getMorceaux() { return morceaux; }

    public String getDateCreation() { return dateCreation; }

    // ===== Gestion des morceaux =====

    /**
     * Ajoute un morceau à la playlist.
     * @param morceau le morceau à ajouter
     * @throws IllegalArgumentException si le morceau est déjà dans la playlist
     */
    public void addMorceau(Morceau morceau) {
        if (morceaux.contains(morceau)) {
            throw new IllegalArgumentException("Ce morceau est déjà dans la playlist");
        }
        morceaux.add(morceau);
    }

    /**
     * Retire un morceau de la playlist.
     * @param morceau le morceau à retirer
     */
    public void removeMorceau(Morceau morceau) { morceaux.remove(morceau); }

    /**
     * Retourne le nombre de morceaux dans la playlist.
     */
    public int getNbMorceaux() { return morceaux.size(); }

    @Override
    public String toString() {
        return nom + " (" + getNbMorceaux() + " morceaux)";
    }
}