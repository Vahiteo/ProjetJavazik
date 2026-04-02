package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un groupe musical.
 * Hérite de la classe abstraite Artiste.
 */
public class Groupe extends Artiste {

    private List<ArtisteSeul> membres;
    private int anneeFormation;

    /**
     * Constructeur de la classe Groupe.
     * @param nom le nom du groupe
     * @param biographie courte biographie
     * @param anneeFormation l'année de formation du groupe
     */
    public Groupe(String nom, String biographie, int anneeFormation) {
        super(nom, biographie);
        this.membres = new ArrayList<>();
        this.anneeFormation = anneeFormation;
    }

    // Getters / Setters

    public List<ArtisteSeul> getMembres() { return membres; }

    public void addMembre(ArtisteSeul artiste) { this.membres.add(artiste); }

    public void removeMembre(ArtisteSeul artiste) { this.membres.remove(artiste); }

    public int getAnneeFormation() { return anneeFormation; }
    public void setAnneeFormation(int anneeFormation) { this.anneeFormation = anneeFormation; }

    /**
     * Retourne une description complète du groupe.
     */
    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNom()).append(" - Groupe\n");
        sb.append("Formé en : ").append(anneeFormation).append("\n");
        sb.append("Biographie : ").append(getBiographie()).append("\n");
        sb.append("Membres : ");
        for (ArtisteSeul membre : membres) {
            sb.append(membre.toString()).append(", ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getNom() + " (groupe)";
    }
}