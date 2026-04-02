package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant l'historique d'écoute d'un abonné.
 */
public class HistoriqueEcoute implements Serializable {

    private List<Morceau> morceaux;
    private List<String> dates;

    /**
     * Constructeur de la classe HistoriqueEcoute.
     */
    public HistoriqueEcoute() {
        this.morceaux = new ArrayList<>();
        this.dates = new ArrayList<>();
    }

    // ===== Getters =====

    public List<Morceau> getMorceaux() { return morceaux; }
    public List<String> getDates() { return dates; }

    /**
     * Ajoute un morceau à l'historique avec la date actuelle.
     * @param morceau le morceau écouté
     */
    public void ajouterMorceau(Morceau morceau) {
        morceaux.add(morceau);
        dates.add(new java.util.Date().toString());
    }

    /**
     * Retourne le nombre total d'écoutes.
     */
    public int getNbEcoutes() { return morceaux.size(); }

    /**
     * Vide l'historique d'écoute.
     */
    public void vider() {
        morceaux.clear();
        dates.clear();
    }

    @Override
    public String toString() {
        return "Historique : " + getNbEcoutes() + " écoutes";
    }
}