package Main;

import ui.GameGUI;

/**
 * La classe Main est la classe principale du programme.
 * Elle contient la méthode main qui lance l'application.
 */
public class Main {

    /**
     * La méthode main est la méthode principale du programme.
     * Elle crée une instance de la classe GameGUI pour lancer l'application.
     * 
     * @param args les arguments de la ligne de commande (non utilisés dans ce
     *             programme)
     */
    public static void main(String[] args) {
        new GameGUI();
    }
}