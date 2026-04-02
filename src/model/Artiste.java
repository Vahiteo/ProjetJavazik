package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un artiste (seul ou groupe).
 * Ne peut pas être instanciée directement.
 */
public abstract class Artiste implements Serializable {

    private String nom;
    private String biographie;
    private List<Album> albums;

    /**
     * Constructeur de la classe Artiste.
     * @param nom le nom de l'artiste ou du groupe
     * @param biographie courte biographie
     */
    public Artiste(String nom, String biographie) {
        this.nom = nom;
        this.biographie = biographie;
        this.albums = new ArrayList<>();
    }

    // ===== Getters / Setters =====

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getBiographie() { return biographie; }
    public void setBiographie(String biographie) { this.biographie = biographie; }

    public List<Album> getAlbums() { return albums; }
    public void addAlbum(Album album) { this.albums.add(album); }

    /**
     * Méthode abstraite à implémenter dans chaque sous-classe.
     * Retourne une description textuelle de l'artiste.
     */
    public abstract String getDescription();

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Artiste artiste = (Artiste) obj;
        return nom.equals(artiste.nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}