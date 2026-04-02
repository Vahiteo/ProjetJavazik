package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import model.Abonne;

/**
 * Classe représentant le catalogue musical complet.
 * Contient tous les morceaux, albums et artistes du système.
 */
public class Catalogue implements Serializable {

    private List<Morceau> morceaux;
    private List<Album> albums;
    private List<Artiste> artistes;

    /**
     * Constructeur de la classe Catalogue.
     */
    public Catalogue() {
        this.morceaux = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.artistes = new ArrayList<>();
    }

    // ===== Gestion des morceaux =====

    public List<Morceau> getMorceaux() { return morceaux; }

    public void addMorceau(Morceau morceau) {
        if (!morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    public void removeMorceau(Morceau morceau) { morceaux.remove(morceau); }

    // ===== Gestion des albums =====

    public List<Album> getAlbums() { return albums; }

    public void addAlbum(Album album) {
        if (!albums.contains(album)) {
            albums.add(album);
        }
    }

    public void removeAlbum(Album album) { albums.remove(album); }

    // ===== Gestion des artistes =====

    public List<Artiste> getArtistes() { return artistes; }

    public void addArtiste(Artiste artiste) {
        if (!artistes.contains(artiste)) {
            artistes.add(artiste);
        }
    }

    public void removeArtiste(Artiste artiste) { artistes.remove(artiste); }

    // ===== Recherche =====

    /**
     * Recherche des morceaux par titre (insensible à la casse).
     * @param titre le titre à rechercher
     * @return la liste des morceaux correspondants
     */
    public List<Morceau> rechercherMorceauxParTitre(String titre) {
        List<Morceau> resultats = new ArrayList<>();
        for (Morceau m : morceaux) {
            if (m.getTitre().toLowerCase().contains(titre.toLowerCase())) {
                resultats.add(m);
            }
        }
        return resultats;
    }

    /**
     * Recherche des morceaux par genre (insensible à la casse).
     * @param genre le genre à rechercher
     * @return la liste des morceaux correspondants
     */
    public List<Morceau> rechercherMorceauxParGenre(String genre) {
        List<Morceau> resultats = new ArrayList<>();
        for (Morceau m : morceaux) {
            if (m.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                resultats.add(m);
            }
        }
        return resultats;
    }

    /**
     * Recherche des albums par titre (insensible à la casse).
     * @param titre le titre à rechercher
     * @return la liste des albums correspondants
     */
    public List<Album> rechercherAlbumsParTitre(String titre) {
        List<Album> resultats = new ArrayList<>();
        for (Album a : albums) {
            if (a.getTitre().toLowerCase().contains(titre.toLowerCase())) {
                resultats.add(a);
            }
        }
        return resultats;
    }

    /**
     * Recherche des artistes par nom (insensible à la casse).
     * @param nom le nom à rechercher
     * @return la liste des artistes correspondants
     */
    public List<Artiste> rechercherArtistesParNom(String nom) {
        List<Artiste> resultats = new ArrayList<>();
        for (Artiste a : artistes) {
            if (a.getNom().toLowerCase().contains(nom.toLowerCase())) {
                resultats.add(a);
            }
        }
        return resultats;
    }

    // ===== Statistiques =====

    /**
     * Retourne le nombre total de morceaux dans le catalogue.
     */
    public int getNbMorceaux() { return morceaux.size(); }

    /**
     * Retourne le nombre total d'albums dans le catalogue.
     */
    public int getNbAlbums() { return albums.size(); }

    /**
     * Retourne le nombre total d'artistes dans le catalogue.
     */
    public int getNbArtistes() { return artistes.size(); }

    @Override
    public String toString() {
        return "Catalogue : " + getNbMorceaux() + " morceaux, "
                + getNbAlbums() + " albums, "
                + getNbArtistes() + " artistes";
    }

    /**
     * Retourne le nombre total d'écoutes de tous les morceaux.
     */
    public int getNbTotalEcoutes() {
        int total = 0;
        for (Morceau m : morceaux) {
            total += m.getNbEcoutes();
        }
        return total;
    }

    /**
     * Retourne les morceaux triés par nombre d'écoutes décroissant.
     */
    public List<Morceau> getMorceauxParEcoutes() {
        List<Morceau> tries = new ArrayList<>(morceaux);
        tries.sort((m1, m2) -> m2.getNbEcoutes() - m1.getNbEcoutes());
        return tries;
    }

    /**
     * Retourne les morceaux les mieux notés.
     */
    public List<Morceau> getMorceauxParNotes() {
        List<Morceau> tries = new ArrayList<>(morceaux);
        tries.sort((m1, m2) -> Double.compare(m2.getNoteMoyenne(), m1.getNoteMoyenne()));
        return tries;
    }

    /**
     * Retourne tous les morceaux d'un artiste.
     * @param artiste l'artiste
     * @return la liste des morceaux de l'artiste
     */
    public List<Morceau> getMorceauxParArtiste(Artiste artiste) {
        List<Morceau> resultats = new ArrayList<>();
        for (Morceau m : morceaux) {
            if (m.getArtiste().equals(artiste)) {
                resultats.add(m);
            }
        }
        return resultats;
    }

    /**
     * Retourne tous les albums d'un artiste.
     * @param artiste l'artiste
     * @return la liste des albums de l'artiste
     */
    public List<Album> getAlbumsParArtiste(Artiste artiste) {
        List<Album> resultats = new ArrayList<>();
        for (Album a : albums) {
            if (a.getArtiste().equals(artiste)) {
                resultats.add(a);
            }
        }
        return resultats;
    }

    /**
     * Retourne l'album contenant un morceau donné.
     * @param morceau le morceau
     * @return la liste des albums contenant ce morceau
     */
    public List<Album> getAlbumsContenant(Morceau morceau) {
        List<Album> resultats = new ArrayList<>();
        for (Album a : albums) {
            if (a.getMorceaux().contains(morceau)) {
                resultats.add(a);
            }
        }
        return resultats;
    }

    /**
     * Retourne le nombre d'écoutes total d'un album.
     * @param album l'album
     * @return le nombre d'écoutes
     */
    public int getNbEcoutesAlbum(Album album) {
        int total = 0;
        for (Morceau m : album.getMorceaux()) {
            total += m.getNbEcoutes();
        }
        return total;
    }

    /**
     * Retourne le nombre d'écoutes total d'un artiste.
     * @param artiste l'artiste
     * @return le nombre d'écoutes
     */
    public int getNbEcoutesArtiste(Artiste artiste) {
        int total = 0;
        for (Morceau m : morceaux) {
            if (m.getArtiste().equals(artiste)) {
                total += m.getNbEcoutes();
            }
        }
        return total;
    }

    /**
     * Retourne les albums triés par nombre d'écoutes décroissant.
     */
    public List<Album> getAlbumsParEcoutes() {
        List<Album> tries = new ArrayList<>(albums);
        tries.sort((a1, a2) -> getNbEcoutesAlbum(a2) - getNbEcoutesAlbum(a1));
        return tries;
    }

    /**
     * Retourne les artistes triés par nombre d'écoutes décroissant.
     */
    public List<Artiste> getArtistesParEcoutes() {
        List<Artiste> tries = new ArrayList<>(artistes);
        tries.sort((a1, a2) -> getNbEcoutesArtiste(a2) - getNbEcoutesArtiste(a1));
        return tries;
    }

    /**
     * Retourne les morceaux les plus ajoutés dans les playlists.
     * @param abonnes la liste des abonnés
     */
    public List<Morceau> getMorceauxParAjoutsPlaylist(List<Abonne> abonnes) {
        Map<Morceau, Integer> compteur = new HashMap<>();
        for (Abonne abonne : abonnes) {
            for (Playlist p : abonne.getPlaylists()) {
                for (Morceau m : p.getMorceaux()) {
                    compteur.put(m, compteur.getOrDefault(m, 0) + 1);
                }
            }
        }
        List<Morceau> tries = new ArrayList<>(morceaux);
        tries.sort((m1, m2) -> compteur.getOrDefault(m2, 0) - compteur.getOrDefault(m1, 0));
        return tries;
    }
}