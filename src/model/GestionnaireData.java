package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe gérant la sauvegarde et le chargement des données par sérialisation.
 */
public class GestionnaireData {

    private static final String FICHIER_CATALOGUE = "catalogue.dat";
    private static final String FICHIER_ABONNES = "abonnes.dat";

    /**
     * Sauvegarde le catalogue dans un fichier.
     * @param catalogue le catalogue à sauvegarder
     */
    public static void sauvegarderCatalogue(Catalogue catalogue) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FICHIER_CATALOGUE))) {
            oos.writeObject(catalogue);
            System.out.println("Catalogue sauvegardé ");
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde catalogue : " + e.getMessage());
        }
    }

    /**
     * Charge le catalogue depuis un fichier.
     * @return le catalogue chargé, ou un nouveau catalogue si le fichier n'existe pas
     */
    public static Catalogue chargerCatalogue() {
        File fichier = new File(FICHIER_CATALOGUE);
        if (!fichier.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FICHIER_CATALOGUE))) {
            return (Catalogue) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur chargement catalogue : " + e.getMessage());
            return null;
        }
    }

    /**
     * Sauvegarde la liste des abonnés dans un fichier.
     * @param abonnes la liste des abonnés à sauvegarder
     */
    public static void sauvegarderAbonnes(List<Abonne> abonnes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FICHIER_ABONNES))) {
            oos.writeObject(abonnes);
            System.out.println("Abonnés sauvegardés ");
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde abonnés : " + e.getMessage());
        }
    }

    /**
     * Charge la liste des abonnés depuis un fichier.
     * @return la liste des abonnés chargée, ou une liste vide si le fichier n'existe pas
     */
    public static List<Abonne> chargerAbonnes() {
        File fichier = new File(FICHIER_ABONNES);
        if (!fichier.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FICHIER_ABONNES))) {
            return (List<Abonne>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur chargement abonnés : " + e.getMessage());
            return new ArrayList<>();
        }
    }
}