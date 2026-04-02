package view;

import controller.Controller;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Classe gérant l'interface graphique en mode Swing.
 */
public class GraphiqueView extends JFrame {

    private Controller controller;

    // Couleurs
    private static final Color COULEUR_FOND = new Color(18, 18, 18);
    private static final Color COULEUR_PANEL = new Color(30, 30, 30);
    private static final Color COULEUR_ACCENT = new Color(29, 185, 84); // vert spotify
    private static final Color COULEUR_TEXTE = new Color(255, 255, 255);
    private static final Color COULEUR_TEXTE_GRIS = new Color(179, 179, 179);
    private static final Color COULEUR_BOUTON = new Color(40, 40, 40);

    /**
     * Constructeur de la vue graphique.
     * @param controller le contrôleur
     */
    public GraphiqueView(Controller controller) {
        this.controller = controller;
        configurerFenetre();
        afficherMenuPrincipal();
    }

    /**
     * Configure la fenêtre principale.
     */
    private void configurerFenetre() {
        setTitle("JAVAZIC");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COULEUR_FOND);

        // Sauvegarde automatique à la fermeture
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                controller.sauvegarderDonnees();
                dispose();
                System.exit(0);
            }
        });
    }

    /**
     * Crée un bouton stylisé.
     */
    private JButton creerBouton(String texte, Color couleur) {
        JButton bouton = new JButton(texte);
        bouton.setBackground(couleur);
        bouton.setForeground(COULEUR_TEXTE);
        bouton.setFocusPainted(false);
        bouton.setBorderPainted(false);
        bouton.setFont(new Font("Arial", Font.BOLD, 14));
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bouton.setPreferredSize(new Dimension(250, 45));
        return bouton;
    }

    /**
     * Crée un label titre stylisé.
     */
    private JLabel creerTitre(String texte, int taille) {
        JLabel label = new JLabel(texte, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, taille));
        label.setForeground(COULEUR_ACCENT);
        return label;
    }

    /**
     * Crée un label texte normal.
     */
    private JLabel creerLabel(String texte) {
        JLabel label = new JLabel(texte);
        label.setForeground(COULEUR_TEXTE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    /**
     * Crée un champ de texte stylisé.
     */
    private JTextField creerChamp(int colonnes) {
        JTextField champ = new JTextField(colonnes);
        champ.setBackground(COULEUR_BOUTON);
        champ.setForeground(COULEUR_TEXTE);
        champ.setCaretColor(COULEUR_TEXTE);
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COULEUR_ACCENT, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        return champ;
    }

    /**
     * Crée un champ mot de passe stylisé.
     */
    private JPasswordField creerChampMdp(int colonnes) {
        JPasswordField champ = new JPasswordField(colonnes);
        champ.setBackground(COULEUR_BOUTON);
        champ.setForeground(COULEUR_TEXTE);
        champ.setCaretColor(COULEUR_TEXTE);
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COULEUR_ACCENT, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        return champ;
    }

    /**
     * Affiche le menu principal.
     */
    public void afficherMenuPrincipal() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(COULEUR_FOND);
        panelCentral.setBorder(new EmptyBorder(80, 0, 0, 0));

        // Titre
        JLabel titre = creerTitre(" JAVAZIC ", 42);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(titre);
        panelCentral.add(Box.createVerticalStrut(10));

        JLabel sousTitre = new JLabel("Votre musique, partout.", SwingConstants.CENTER);
        sousTitre.setForeground(COULEUR_TEXTE_GRIS);
        sousTitre.setFont(new Font("Arial", Font.ITALIC, 16));
        sousTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(sousTitre);
        panelCentral.add(Box.createVerticalStrut(50));

        // Boutons
        JButton btnAdmin = creerBouton("Se connecter (Admin)", COULEUR_BOUTON);
        JButton btnClient = creerBouton("Se connecter (Client)", COULEUR_ACCENT);
        JButton btnCreer = creerBouton("Créer un compte", COULEUR_BOUTON);
        JButton btnVisiteur = creerBouton("Continuer en visiteur", COULEUR_BOUTON);
        JButton btnQuitter = creerBouton("Quitter", new Color(180, 50, 50));

        for (JButton btn : new JButton[]{btnAdmin, btnClient, btnCreer, btnVisiteur, btnQuitter}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCentral.add(btn);
            panelCentral.add(Box.createVerticalStrut(12));
        }

        // Actions
        btnAdmin.addActionListener(e -> afficherConnexionAdmin());
        btnClient.addActionListener(e -> afficherConnexionClient());
        btnCreer.addActionListener(e -> afficherCreationCompte());
        btnVisiteur.addActionListener(e -> controller.lancerModeVisiteur(this));
        btnQuitter.addActionListener(e -> {
            controller.sauvegarderDonnees();
            dispose();
            System.exit(0);
        });

        add(panelCentral, BorderLayout.CENTER);
        revalidate();
        repaint();
        setVisible(true);
    }

    /**
     * Affiche le formulaire de connexion admin.
     */
    private void afficherConnexionAdmin() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(COULEUR_PANEL);

        JTextField champId = creerChamp(15);
        JPasswordField champMdp = creerChampMdp(15);

        panel.add(creerLabel("Identifiant :"));
        panel.add(champId);
        panel.add(creerLabel("Mot de passe :"));
        panel.add(champMdp);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Connexion Admin", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String id = champId.getText();
            String mdp = new String(champMdp.getPassword());
            controller.connecterAdmin(id, mdp, this);
        }
    }

    /**
     * Affiche le formulaire de connexion client.
     */
    private void afficherConnexionClient() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBackground(COULEUR_PANEL);

        JTextField champId = creerChamp(15);
        JPasswordField champMdp = creerChampMdp(15);

        panel.add(creerLabel("Identifiant :"));
        panel.add(champId);
        panel.add(creerLabel("Mot de passe :"));
        panel.add(champMdp);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Connexion Client", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String id = champId.getText();
            String mdp = new String(champMdp.getPassword());
            controller.connecterAbonneGraphique(id, mdp, this);
        }
    }

    /**
     * Affiche le formulaire de création de compte.
     */
    private void afficherCreationCompte() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField champId = creerChamp(15);
        JPasswordField champMdp = creerChampMdp(15);
        JTextField champEmail = creerChamp(15);

        panel.add(creerLabel("Identifiant :"));
        panel.add(champId);
        panel.add(creerLabel("Mot de passe :"));
        panel.add(champMdp);
        panel.add(creerLabel("Email :"));
        panel.add(champEmail);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Créer un compte", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String id = champId.getText().trim();
            String mdp = new String(champMdp.getPassword());
            String email = champEmail.getText().trim();
            if (id.isEmpty() || mdp.isEmpty() || email.isEmpty()) {
                afficherErreur("Tous les champs sont obligatoires ");
                return;
            }
            controller.creerCompteGraphique(id, mdp, email, this);
        }
    }

    /**
     * Affiche le menu abonné.
     */
    public void afficherMenuAbonne(Abonne abonne) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COULEUR_PANEL);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titreLabel = creerTitre(" JAVAZIC", 24);
        JLabel userLabel = new JLabel("Connecté : " + abonne.getIdentifiant());
        userLabel.setForeground(COULEUR_TEXTE_GRIS);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        JButton btnDeconnexion = creerBouton("Déconnexion", new Color(180, 50, 50));
        btnDeconnexion.setPreferredSize(new Dimension(120, 35));
        btnDeconnexion.addActionListener(e -> afficherMenuPrincipal());

        header.add(titreLabel, BorderLayout.WEST);
        header.add(userLabel, BorderLayout.CENTER);
        header.add(btnDeconnexion, BorderLayout.EAST);

        // Panel boutons
        JPanel panelBoutons = new JPanel(new GridLayout(4, 2, 10, 10));
        panelBoutons.setBackground(COULEUR_FOND);
        panelBoutons.setBorder(new EmptyBorder(30, 50, 30, 50));

        JButton btnRechercheMorceau = creerBouton(" Rechercher un morceau", COULEUR_BOUTON);
        JButton btnRechercheAlbum = creerBouton(" Rechercher un album", COULEUR_BOUTON);
        JButton btnRechercheArtiste = creerBouton(" Rechercher un artiste", COULEUR_BOUTON);
        JButton btnEcouter = creerBouton(" Écouter un morceau", COULEUR_ACCENT);
        JButton btnPlaylists = creerBouton(" Mes playlists", COULEUR_BOUTON);
        JButton btnHistorique = creerBouton(" Mon historique", COULEUR_BOUTON);
        JButton btnAvis = creerBouton(" Gérer les avis", COULEUR_BOUTON);

        panelBoutons.add(btnRechercheMorceau);
        panelBoutons.add(btnRechercheAlbum);
        panelBoutons.add(btnRechercheArtiste);
        panelBoutons.add(btnEcouter);
        panelBoutons.add(btnPlaylists);
        panelBoutons.add(btnHistorique);
        panelBoutons.add(btnAvis);
        panelBoutons.add(new JLabel());

        // Actions
        btnRechercheMorceau.addActionListener(e -> controller.rechercherMorceauGraphique(this));
        btnRechercheAlbum.addActionListener(e -> controller.rechercherAlbumGraphique(this));
        btnRechercheArtiste.addActionListener(e -> controller.rechercherArtisteGraphique(this));
        btnEcouter.addActionListener(e -> controller.ecouterMorceauAbonneGraphique(abonne, this));
        btnPlaylists.addActionListener(e -> afficherGestionPlaylists(abonne));
        btnHistorique.addActionListener(e -> afficherHistorique(abonne));
        btnAvis.addActionListener(e -> controller.laisserAvisGraphique(abonne, this));

        add(header, BorderLayout.NORTH);
        add(panelBoutons, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Affiche le menu visiteur.
     */
    public void afficherMenuVisiteur(Visiteur visiteur) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COULEUR_PANEL);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titreLabel = creerTitre(" JAVAZIC", 24);
        JLabel userLabel = new JLabel("Mode visiteur — " + visiteur.getNbEcoutesSession() + "/" + visiteur.getLimiteEcoutes() + " écoutes");
        userLabel.setForeground(COULEUR_TEXTE_GRIS);

        JButton btnRetour = creerBouton("Retour", COULEUR_BOUTON);
        btnRetour.setPreferredSize(new Dimension(100, 35));
        btnRetour.addActionListener(e -> afficherMenuPrincipal());

        header.add(titreLabel, BorderLayout.WEST);
        header.add(userLabel, BorderLayout.CENTER);
        header.add(btnRetour, BorderLayout.EAST);

        // Panel boutons
        JPanel panelBoutons = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBoutons.setBackground(COULEUR_FOND);
        panelBoutons.setBorder(new EmptyBorder(60, 100, 60, 100));

        JButton btnRechercheMorceau = creerBouton(" Rechercher un morceau", COULEUR_BOUTON);
        JButton btnRechercheAlbum = creerBouton(" Rechercher un album", COULEUR_BOUTON);
        JButton btnRechercheArtiste = creerBouton(" Rechercher un artiste", COULEUR_BOUTON);
        JButton btnEcouter = creerBouton(" Écouter un morceau", COULEUR_ACCENT);

        panelBoutons.add(btnRechercheMorceau);
        panelBoutons.add(btnRechercheAlbum);
        panelBoutons.add(btnRechercheArtiste);
        panelBoutons.add(btnEcouter);

        btnRechercheMorceau.addActionListener(e -> controller.rechercherMorceauGraphique(this));
        btnRechercheAlbum.addActionListener(e -> controller.rechercherAlbumGraphique(this));
        btnRechercheArtiste.addActionListener(e -> controller.rechercherArtisteGraphique(this));
        btnEcouter.addActionListener(e -> controller.ecouterMorceauVisiteurGraphique(visiteur, this));

        add(header, BorderLayout.NORTH);
        add(panelBoutons, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Affiche le menu administrateur.
     */
    public void afficherMenuAdmin() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COULEUR_PANEL);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titreLabel = creerTitre(" JAVAZIC", 24);
        JLabel userLabel = new JLabel("Administrateur");
        userLabel.setForeground(COULEUR_TEXTE_GRIS);

        JButton btnDeconnexion = creerBouton("Déconnexion", new Color(180, 50, 50));
        btnDeconnexion.setPreferredSize(new Dimension(120, 35));
        btnDeconnexion.addActionListener(e -> afficherMenuPrincipal());

        header.add(titreLabel, BorderLayout.WEST);
        header.add(userLabel, BorderLayout.CENTER);
        header.add(btnDeconnexion, BorderLayout.EAST);

        // Panel boutons
        JPanel panelBoutons = new JPanel(new GridLayout(3, 3, 10, 10));
        panelBoutons.setBackground(COULEUR_FOND);
        panelBoutons.setBorder(new EmptyBorder(30, 50, 30, 50));

        JButton btnAjouterMorceau = creerBouton(" Ajouter morceau", COULEUR_BOUTON);
        JButton btnSupprimerMorceau = creerBouton(" Supprimer morceau", COULEUR_BOUTON);
        JButton btnAjouterAlbum = creerBouton(" Ajouter album", COULEUR_BOUTON);
        JButton btnSupprimerAlbum = creerBouton(" Supprimer album", COULEUR_BOUTON);
        JButton btnAjouterArtiste = creerBouton(" Ajouter artiste", COULEUR_BOUTON);
        JButton btnSupprimerArtiste = creerBouton(" Supprimer artiste", COULEUR_BOUTON);
        JButton btnGererAbonnes = creerBouton(" Gérer abonnés", COULEUR_BOUTON);
        JButton btnStats = creerBouton(" Statistiques", COULEUR_ACCENT);

        panelBoutons.add(btnAjouterMorceau);
        panelBoutons.add(btnSupprimerMorceau);
        panelBoutons.add(btnAjouterAlbum);
        panelBoutons.add(btnSupprimerAlbum);
        panelBoutons.add(btnAjouterArtiste);
        panelBoutons.add(btnSupprimerArtiste);
        panelBoutons.add(btnGererAbonnes);
        panelBoutons.add(btnStats);
        panelBoutons.add(new JLabel());

        btnAjouterMorceau.addActionListener(e -> controller.ajouterMorceauGraphique(this));
        btnSupprimerMorceau.addActionListener(e -> controller.supprimerMorceauGraphique(this));
        btnAjouterAlbum.addActionListener(e -> controller.ajouterAlbumGraphique(this));
        btnSupprimerAlbum.addActionListener(e -> controller.supprimerAlbumGraphique(this));
        btnAjouterArtiste.addActionListener(e -> controller.ajouterArtisteGraphique(this));
        btnSupprimerArtiste.addActionListener(e -> controller.supprimerArtisteGraphique(this));
        btnGererAbonnes.addActionListener(e -> controller.gererAbonnesGraphique(this));
        btnStats.addActionListener(e -> controller.afficherStatistiquesGraphique(this));

        add(header, BorderLayout.NORTH);
        add(panelBoutons, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Affiche la gestion des playlists.
     */
    public void afficherGestionPlaylists(Abonne abonne) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COULEUR_PANEL);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel titreLabel = creerTitre(" Mes Playlists", 24);
        JButton btnRetour = creerBouton("Retour", COULEUR_BOUTON);
        btnRetour.setPreferredSize(new Dimension(100, 35));
        btnRetour.addActionListener(e -> afficherMenuAbonne(abonne));
        header.add(titreLabel, BorderLayout.WEST);
        header.add(btnRetour, BorderLayout.EAST);

        // Liste des playlists
        DefaultListModel<String> modele = new DefaultListModel<>();
        for (Playlist p : abonne.getPlaylists()) {
            modele.addElement(p.getNom() + " (" + p.getNbMorceaux() + " morceaux)");
        }
        JList<String> listePlaylists = new JList<>(modele);
        listePlaylists.setBackground(COULEUR_PANEL);
        listePlaylists.setForeground(COULEUR_TEXTE);
        listePlaylists.setFont(new Font("Arial", Font.PLAIN, 14));
        listePlaylists.setSelectionBackground(COULEUR_ACCENT);

        JScrollPane scrollPane = new JScrollPane(listePlaylists);
        scrollPane.setBackground(COULEUR_FOND);
        scrollPane.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Boutons
        JPanel panelBoutons = new JPanel(new FlowLayout());
        panelBoutons.setBackground(COULEUR_FOND);

        JButton btnCreer = creerBouton(" Créer", COULEUR_ACCENT);
        JButton btnVoir = creerBouton(" Voir morceaux", COULEUR_BOUTON);
        JButton btnRenommer = creerBouton(" Renommer", COULEUR_BOUTON);
        JButton btnAjouter = creerBouton(" Ajouter morceau", COULEUR_BOUTON);
        JButton btnRetirer = creerBouton(" Retirer morceau", COULEUR_BOUTON);
        JButton btnSupprimer = creerBouton(" Supprimer", new Color(180, 50, 50));

        btnCreer.setPreferredSize(new Dimension(130, 40));
        btnVoir.setPreferredSize(new Dimension(150, 40));
        btnRenommer.setPreferredSize(new Dimension(120, 40));
        btnAjouter.setPreferredSize(new Dimension(170, 40));
        btnRetirer.setPreferredSize(new Dimension(170, 40));
        btnSupprimer.setPreferredSize(new Dimension(130, 40));

        panelBoutons.add(btnCreer);
        panelBoutons.add(btnVoir);
        panelBoutons.add(btnRenommer);
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnRetirer);
        panelBoutons.add(btnSupprimer);

        btnCreer.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(this, "Nom de la playlist :");
            if (nom != null && !nom.trim().isEmpty()) {
                abonne.creerPlaylist(nom.trim());
                afficherGestionPlaylists(abonne);
                afficherSucces("Playlist \"" + nom + "\" créée ");
            }
        });

        btnVoir.addActionListener(e -> {
            int idxPlaylist = listePlaylists.getSelectedIndex();
            if (idxPlaylist == -1) { afficherErreur("Sélectionnez une playlist "); return; }
            Playlist playlist = abonne.getPlaylists().get(idxPlaylist);
            if (playlist.getMorceaux().isEmpty()) {
                afficherMessage("Cette playlist est vide ");
                return;
            }
            DefaultListModel<String> modeleMorceaux = new DefaultListModel<>();
            for (Morceau m : playlist.getMorceaux()) {
                modeleMorceaux.addElement(m.toString());
            }
            JList<String> listeMorceaux = new JList<>(modeleMorceaux);
            listeMorceaux.setBackground(COULEUR_PANEL);
            listeMorceaux.setForeground(COULEUR_TEXTE);
            listeMorceaux.setFont(new Font("Arial", Font.PLAIN, 14));
            JScrollPane scroll = new JScrollPane(listeMorceaux);
            scroll.setPreferredSize(new Dimension(400, 250));
            JOptionPane.showMessageDialog(this, scroll,
                    " " + playlist.getNom(), JOptionPane.PLAIN_MESSAGE);
        });

        btnRenommer.addActionListener(e -> {
            int idxPlaylist = listePlaylists.getSelectedIndex();
            if (idxPlaylist == -1) { afficherErreur("Sélectionnez une playlist "); return; }
            String nouveauNom = JOptionPane.showInputDialog(this, "Nouveau nom :",
                    abonne.getPlaylists().get(idxPlaylist).getNom());
            if (nouveauNom == null || nouveauNom.trim().isEmpty()) return;
            abonne.getPlaylists().get(idxPlaylist).setNom(nouveauNom.trim());
            afficherGestionPlaylists(abonne);
            afficherSucces("Playlist renommée ");
        });

        btnAjouter.addActionListener(e -> {
            int idxPlaylist = listePlaylists.getSelectedIndex();
            if (idxPlaylist == -1) { afficherErreur("Sélectionnez une playlist "); return; }
            controller.ajouterMorceauPlaylistGraphique(abonne, idxPlaylist, this);
        });

        btnRetirer.addActionListener(e -> {
            int idxPlaylist = listePlaylists.getSelectedIndex();
            if (idxPlaylist == -1) { afficherErreur("Sélectionnez une playlist "); return; }
            controller.retirerMorceauPlaylistGraphique(abonne, idxPlaylist, this);
        });

        btnSupprimer.addActionListener(e -> {
            int idx = listePlaylists.getSelectedIndex();
            if (idx == -1) { afficherErreur("Sélectionnez une playlist "); return; }
            abonne.supprimerPlaylist(abonne.getPlaylists().get(idx));
            afficherGestionPlaylists(abonne);
            afficherSucces("Playlist supprimée ");
        });

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    /**
     * Affiche l'historique d'écoute.
     */
    public void afficherHistorique(Abonne abonne) {
        DefaultListModel<String> modele = new DefaultListModel<>();
        for (Morceau m : abonne.getHistorique().getMorceaux()) {
            modele.addElement(m.toString());
        }
        JList<String> liste = new JList<>(modele);
        liste.setBackground(COULEUR_PANEL);
        liste.setForeground(COULEUR_TEXTE);
        liste.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(liste);
        scroll.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scroll, " Historique d'écoute", JOptionPane.PLAIN_MESSAGE);
    }

    // ===== Messages =====

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherSucces(String message) {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}