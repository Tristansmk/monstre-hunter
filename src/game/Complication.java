package game;

/**
 * Cette classe représente une exception spécifique appelée Complication.
 * Elle est utilisée pour signaler une complication ou une erreur dans le jeu.
 */
public class Complication extends Exception {

    /**
     * Construit une nouvelle instance de la classe Complication avec le message
     * spécifié.
     *
     * @param message le message détaillant la complication ou l'erreur
     */
    public Complication(String message) {
        super(message);
    }
}