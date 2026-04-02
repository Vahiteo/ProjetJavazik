package model;

import java.io.Serializable;

/**
 * Classe abstraite représentant un utilisateur du système.
 * Ne peut pas être instanciée directement.
 */
public abstract class Utilisateur implements Serializable {

    private String identifiant;
    private String motDePasse;

    /**
     * Constructeur de la classe Utilisateur.
     * @param identifiant l'identifiant unique de l'utilisateur
     * @param motDePasse le mot de passe de l'utilisateur
     */
    public Utilisateur(String identifiant, String motDePasse) {
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
    }

    // Getters / Setters

    public String getIdentifiant() { return identifiant; }
    public void setIdentifiant(String identifiant) { this.identifiant = identifiant; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utilisateur utilisateur = (Utilisateur) obj;
        return identifiant.equals(utilisateur.identifiant);
    }

    @Override
    public int hashCode() {
        return identifiant.hashCode();
    }

    /**
     * Vérifie si le mot de passe fourni est correct.
     * @param motDePasse le mot de passe à vérifier
     * @return true si le mot de passe est correct
     */


    public boolean verifierMotDePasse(String motDePasse) {
        return this.motDePasse.equals(motDePasse);
    }

    /**
     * Méthode abstraite à implémenter dans chaque sous-classe.
     * Retourne le rôle de l'utilisateur.
     */
    public abstract String getRole();

    @Override
    public String toString() {
        return identifiant + " (" + getRole() + ")";
    }
}