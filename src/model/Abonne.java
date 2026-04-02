package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un abonné du système.
 * Peut créer des playlists et écouter un nombre illimité de morceaux.
 */
public class Abonne extends Utilisateur {

    private String email;
    private List<Playlist> playlists;
    private HistoriqueEcoute historique;
    private String statut; // "actif" ou "suspendu"

    /**
     * Constructeur de la classe Abonne.
     * @param identifiant l'identifiant de l'abonné
     * @param motDePasse le mot de passe de l'abonné
     * @param email l'adresse email de l'abonné
     */
    public Abonne(String identifiant, String motDePasse, String email) {
        super(identifiant, motDePasse);
        this.email = email;
        this.playlists = new ArrayList<>();
        this.historique = new HistoriqueEcoute();
        this.statut = "actif";
    }

    // ===== Getters / Setters =====

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Playlist> getPlaylists() { return playlists; }

    public HistoriqueEcoute getHistorique() { return historique; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    // ===== Gestion des playlists =====

    /**
     * Crée une nouvelle playlist et l'ajoute à la liste.
     * @param nom le nom de la playlist
     * @return la playlist créée
     */
    public Playlist creerPlaylist(String nom) {
        Playlist playlist = new Playlist(nom, this);
        playlists.add(playlist);
        return playlist;
    }

    /**
     * Supprime une playlist de la liste.
     * @param playlist la playlist à supprimer
     */
    public void supprimerPlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    /**
     * Vérifie si l'abonné est actif.
     * @return true si le statut est "actif"
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Abonne abonne = (Abonne) obj;
        return getIdentifiant().equals(abonne.getIdentifiant());
    }

    @Override
    public int hashCode() {
        return getIdentifiant().hashCode();
    }

    public boolean estActif() {
        return statut.equals("actif");
    }

    /**
     * Enregistre l'écoute d'un morceau dans l'historique.
     * @param morceau le morceau écouté
     */
    public void ecouterMorceau(Morceau morceau) {
        if (!estActif()) {
            throw new IllegalStateException("Compte suspendu impossible d'écouter des morceaux ");
        }
        historique.ajouterMorceau(morceau);
    }

    @Override
    public String getRole() {
        return "Abonné";
    }

    @Override
    public String toString() {
        return getIdentifiant() + " - " + email + " (" + statut + ")";
    }
}