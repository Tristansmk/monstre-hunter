package game;

/**
 * Classe représentant un monstre dans le jeu.
 */
public class Monster {
    private int x = 1;
    private int y = 1;
    private int vie = 50;
    private int attaque = 10;
    private boolean estMort = false;
    private boolean estGelé = false; // Est pris dans l'attaque spéciale du joueur

    /**
     * Constructeur avec position initiale spécifiée.
     *
     * @param x Position x du monstre.
     * @param y Position y du monstre.
     */
    public Monster(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Attaque un joueur, réduisant sa vie par la valeur d'attaque du monstre.
     *
     * @param joueur Le joueur à attaquer.
     */
    public void attaquer(Player joueur) {
        joueur.setVie(joueur.getVie() - this.attaque);
        if (joueur.getVie() <= 0) {
            joueur.setMort(true);
        }
    }

    /**
     * @return True si le monstre est gelé, false sinon.
     */
    public boolean estGelé() {
        return estGelé;
    }

    /**
     * Définit l'état de gel du monstre.
     *
     * @param estGelé True si le monstre est gelé, false sinon.
     */
    public void setGel(boolean estGelé) {
        this.estGelé = estGelé;
    }

    /**
     * @return La position x du monstre.
     */
    public int getX() {
        return x;
    }

    /**
     * @return La position y du monstre.
     */
    public int getY() {
        return y;
    }

    /**
     * @return La vie du monstre.
     */
    public int getVie() {
        return vie;
    }

    /**
     * @return True si le monstre est mort, false sinon.
     */
    public boolean getMort() {
        return estMort;
    }

    /**
     * Définit la vie du monstre.
     *
     * @param vie La nouvelle valeur de vie du monstre.
     */
    public void setVie(int vie) {
        this.vie = vie;
    }

    /**
     * Définit si le monstre est mort.
     *
     * @param mort True si le monstre est mort, false sinon.
     */
    public void setMort(boolean mort) {
        this.estMort = mort;
    }

    /**
     * Définit l'attaque du monstre.
     *
     * @param attaque La nouvelle valeur d'attaque du monstre.
     */
    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    /**
     * Définit la position du monstre.
     *
     * @param x La nouvelle position x du monstre.
     * @param y La nouvelle position y du monstre.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}