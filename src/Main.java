import controller.Controller;

import javax.swing.*;

/**
 * Point d'entrée du programme JAVAZIC.
 */
public class Main {
    public static void main(String[] args) {

        String[] options = {"Console", "Graphique"};
        int choix = JOptionPane.showOptionDialog(
                null,
                "Choisissez le mode de lancement :",
                "JAVAZIC",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        Controller controller = new Controller();

        if (choix == 1) {
            controller.lancerGraphique();
        } else {
            controller.lancerConsole();
        }
    }
}