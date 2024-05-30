package game;

/**
 * Classe représentant une porte dans le jeu.
 */
public class Door {
    private int x;
    private int y;
    private boolean estOuverte = false;
    private Monster[] monstres = new Monster[255];
    private int nbMonstres = 0;

    /**
     * Constructeur avec position initiale spécifiée.
     *
     * @param x Position x de la porte.
     * @param y Position y de la porte.
     */
    public Door(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Vérifie si tous les monstres sont morts. Si oui, ouvre la porte.
     */
    public void toutMonstresMort() {
        for (int i = 0; i < this.monstres.length; i++) {
            if (this.monstres[i] != null) {
                if (this.monstres[i].getMort() && this.nbMonstres > 0) {
                    this.monstres[i] = null;
                    if (--this.nbMonstres <= 0) {
                        ouvrir();
                    }
                }
            }
        }
    }

    /**
     * Ouvre la porte.
     */
    public void ouvrir() {
        this.estOuverte = true;
    }

    /**
     * Ajoute un monstre à la porte.
     *
     * @param m Le monstre à ajouter.
     */
    public void ajouterMonstre(Monster m) {
        this.monstres[this.nbMonstres++] = m;
    }

    /**
     * @return True si la porte est ouverte, false sinon.
     */
    public boolean estOuverte() {
        return this.estOuverte;
    }

    /**
     * @return La position x de la porte.
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return La position y de la porte.
     */
    public int getY() {
        return this.y;
    }

    /**
     * @return Le tableau des monstres.
     */
    public Monster[] getMonstres() {
        return this.monstres;
    }

    /**
     * @return Le nombre de monstres.
     */
    public int getNbMonstres() {
        return this.nbMonstres;
    }
}
