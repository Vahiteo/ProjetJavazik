package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un album musical.
 */
public class Album implements Serializable {

    private String titre;
    private int annee;
    private String type; // "studio", "live", "compilation"
    private Artiste artiste;
    private List<Morceau> morceaux;

    /**
     * Constructeur de la classe Album.
     * @param titre le titre de l'album
     * @param annee l'année de sortie
     * @param type le type d'album (studio, live, compilation)
     * @param artiste l'artiste ou groupe de l'album
     */
    public Album(String titre, int annee, String type, Artiste artiste) {
        this.titre = titre;
        this.annee = annee;
        this.type = type;
        this.artiste = artiste;
        this.morceaux = new ArrayList<>();
    }

    // Getters / Setters

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Artiste getArtiste() { return artiste; }
    public void setArtiste(Artiste artiste) { this.artiste = artiste; }

    public List<Morceau> getMorceaux() { return morceaux; }

    public void addMorceau(Morceau morceau) {
        if (!morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    public void removeMorceau(Morceau morceau) { morceaux.remove(morceau); }

    /**
     * Calcule la durée totale de l'album en secondes.
     * @return la durée totale
     */
    public int getDureeTotal() {
        int total = 0;
        for (Morceau m : morceaux) {
            total += m.getDuree();
        }
        return total;
    }

    @Override
    public String toString() {
        return titre + " (" + annee + ") - " + artiste.toString();
    }
}