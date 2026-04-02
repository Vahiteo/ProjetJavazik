package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un morceau musical.
 */
public class Morceau implements Serializable {

    private String titre;
    private int duree; // en secondes
    private String genre;
    private Artiste artiste;
    private List<Avis> avis;
    private int nbEcoutes;

    /**
     * Constructeur de la classe Morceau.
     * @param titre le titre du morceau
     * @param duree la durée en secondes
     * @param genre le genre musical
     * @param artiste l'artiste ou groupe qui interprète le morceau
     */
    public Morceau(String titre, int duree, String genre, Artiste artiste) {
        this.titre = titre;
        this.duree = duree;
        this.genre = genre;
        this.artiste = artiste;
        this.avis = new ArrayList<>();
        this.nbEcoutes = 0;
    }

    // ===== Getters / Setters =====

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Artiste getArtiste() { return artiste; }
    public void setArtiste(Artiste artiste) { this.artiste = artiste; }

    public List<Avis> getAvis() { return avis; }
    public void addAvis(Avis a) { this.avis.add(a); }
    public void removeAvis(Avis a) { this.avis.remove(a); }

    /**
     * Convertit la durée en secondes en format MM:SS
     * @return la durée formatée
     */
    public String getDureeFormatee() {
        int minutes = duree / 60;
        int secondes = duree % 60;
        return String.format("%d:%02d", minutes, secondes);
    }

    /**
     * Calcule la note moyenne des avis du morceau.
     * @return la moyenne des notes, 0 si aucun avis
     */
    public double getNoteMoyenne() {
        if (avis.isEmpty()) return 0;
        double total = 0;
        for (Avis a : avis) {
            total += a.getNote();
        }
        return total / avis.size();
    }

    @Override
    public String toString() {
        return titre + " - " + artiste.toString() + " (" + getDureeFormatee() + ")";
    }

    /**
     * Incrémente le compteur d'écoutes du morceau.
     */
    public void incrementerEcoutes() {
        nbEcoutes++;
    }

    /**
     * Retourne le nombre total d'écoutes du morceau.
     */
    public int getNbEcoutes() { return nbEcoutes; }

    /**
     * Retourne l'avis d'un abonné sur ce morceau, null si aucun.
     * @param abonne l'abonné
     * @return l'avis de l'abonné ou null
     */
    public Avis getAvisDeAbonne(Abonne abonne) {
        for (Avis a : avis) {
            if (a.getAuteur().equals(abonne)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Supprime l'avis d'un abonné sur ce morceau.
     * @param abonne l'abonné
     */
    public void supprimerAvisDeAbonne(Abonne abonne) {
        avis.removeIf(a -> a.getAuteur().equals(abonne));
    }
}