package model;

import java.io.Serializable;

/**
 * Classe représentant un avis laissé par un abonné sur un morceau.
 */
public class Avis implements Serializable {

    private int note; // entre 1 et 5
    private String commentaire;
    private Abonne auteur;

    /**
     * Constructeur de la classe Avis.
     * @param note la note entre 1 et 5
     * @param commentaire le commentaire laissé
     * @param auteur l'abonné qui a laissé l'avis
     */
    public Avis(int note, String commentaire, Abonne auteur) {
        if (note < 1 || note > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5");
        }
        this.note = note;
        this.commentaire = commentaire;
        this.auteur = auteur;
    }

    // Getters / Setters

    public int getNote() { return note; }
    public void setNote(int note) {
        if (note < 1 || note > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5");
        }
        this.note = note;
    }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public Abonne getAuteur() { return auteur; }

    @Override
    public String toString() {
        return auteur.toString() + " - " + note + "/5 : " + commentaire;
    }

    /**
     * Modifie la note et le commentaire de l'avis.
     * @param note la nouvelle note
     * @param commentaire le nouveau commentaire
     */
    public void modifier(int note, String commentaire) {
        if (note < 1 || note > 5) {
            throw new IllegalArgumentException("La note doit être entre 1 et 5 ");
        }
        this.note = note;
        this.commentaire = commentaire;
    }
}