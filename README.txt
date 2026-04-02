================================================================
                        JAVAZIC
         Application de gestion de catalogue musical
================================================================

ÉQUIPE
------
Équipe 7 — TD 7
  - KOZMAN Mathias
  - MARTIN Vahitéo
  - LOUBINOUX Martin
  - SIEBERING Raphael

DESCRIPTION
-----------
JAVAZIC est une application de gestion d'un catalogue musical
et de playlists, inspirée des plateformes de streaming musical
telles que Deezer ou Spotify.

L'application propose deux modes d'affichage :
  - Mode console
  - Mode graphique (Swing)

PRÉREQUIS
---------
  - Java JDK 17 ou supérieur installé sur la machine
  - Le fichier JAVAZIK.jar

LANCEMENT DU PROGRAMME
-----------------------
Option 1 — Avec Java dans le PATH :
  java -jar JAVAZIK.jar

Option 2 — Avec le chemin complet vers Java (Windows) :
  C:\chemin\vers\java.exe -jar JAVAZIK.jar

Au démarrage une fenêtre vous propose de choisir entre
le mode Console et le mode Graphique.

IDENTIFIANTS ADMINISTRATEUR
----------------------------
  Identifiant : admin
  Mot de passe : admin123

FONCTIONNALITÉS
---------------
Visiteur :
  - Consulter le catalogue (morceaux, albums, artistes)
  - Navigation ergonomique dans le catalogue
  - Écouter jusqu'à 5 morceaux par session

Abonné :
  - Toutes les fonctionnalités visiteur
  - Écoutes illimitées
  - Créer, renommer, supprimer des playlists
  - Ajouter/retirer des morceaux dans les playlists
  - Consulter son historique d'écoute
  - Laisser, modifier ou supprimer un avis sur un morceau

Administrateur :
  - Gérer le catalogue (morceaux, albums, artistes)
  - Gérer les comptes abonnés (suspendre, supprimer)
  - Consulter les statistiques évoluées

FONCTIONNALITÉS SUPPLÉMENTAIRES
---------------------------------
  - Système de notation et d'avis sur les morceaux
  - Statistiques évoluées (écoutes par morceau/album/artiste,
    morceaux les plus ajoutés en playlist...)

PERSISTANCE DES DONNÉES
------------------------
Les données sont sauvegardées automatiquement à la fermeture
du programme dans deux fichiers binaires :
  - catalogue.dat : catalogue musical complet
  - abonnes.dat   : comptes abonnés, playlists, historiques

La sauvegarde utilise la sérialisation Java.

STRUCTURE DU PROJET
--------------------
  src/
  ├── model/         Logique métier (classes de données)
  ├── view/          Interfaces (console et graphique)
  ├── controller/    Contrôleur (lien modèle/vue)
  └── Main.java      Point d'entrée du programme

  javadoc/           Documentation générée (ouvrir index.html)

ARCHITECTURE
------------
Le projet respecte l'architecture MVC (Modèle-Vue-Contrôleur) :
  - Model    : logique métier, indépendant de toute interface
  - View     : affichage console et graphique Swing
  - Controller : gestion des interactions utilisateur

NOTES PARTICULIÈRES
--------------------
  - Les fichiers catalogue.dat et abonnes.dat doivent se
    trouver dans le même dossier que le fichier JAVAZIK.jar
  - Au premier lancement, des données de démonstration sont
    chargées automatiquement (Michael Jackson, The Beatles...)
  - La sérialisation Java a été choisie plutôt que les fichiers
    texte pour sa robustesse et sa gestion automatique des
    relations entre objets

================================================================
                    ING3 POO — Java 2025-2026
                       ECE Paris — TD 7
================================================================