package model;

/**
 * Classe représentant un visiteur (utilisateur non connecté).
 * Accès limité au catalogue, ne peut pas créer de playlists.
 */
public class Visiteur extends Utilisateur {

    private int nbEcoutesSession;
    private static final int LIMITE_ECOUTES = 5;

    /**
     * Constructeur de la classe Visiteur.
     */
    public Visiteur() {
        super("visiteur", "");
        this.nbEcoutesSession = 0;
    }

    // ===== Getters =====

    public int getNbEcoutesSession() { return nbEcoutesSession; }
    public int getLimiteEcoutes() { return LIMITE_ECOUTES; }

    /**
     * Vérifie si le visiteur peut encore écouter un morceau.
     * @return true si la limite n'est pas atteinte
     */
    public boolean peutEcouter() {
        return nbEcoutesSession < LIMITE_ECOUTES;
    }

    /**
     * Incrémente le compteur d'écoutes de la session.
     * @throws IllegalStateException si la limite d'écoutes est atteinte
     */
    public void incrementerEcoutes() {
        if (!peutEcouter()) {
            throw new IllegalStateException("Limite d'écoutes atteinte pour cette session ");
        }
        nbEcoutesSession++;
    }

    /**
     * Remet le compteur d'écoutes à zéro.
     */
    public void reinitialiserEcoutes() {
        nbEcoutesSession = 0;
    }

    @Override
    public String getRole() {
        return "Visiteur";
    }

    @Override
    public String toString() {
        return "Visiteur (" + nbEcoutesSession + "/" + LIMITE_ECOUTES + " écoutes)";
    }
}