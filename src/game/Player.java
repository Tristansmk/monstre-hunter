package game;

/**
 * Classe représentant un joueur dans le jeu.
 */
public class Player {
    private int x;
    private int y;
    private int vie = 250;
    private int attaque = 25;
    private int argent = 0;
    private boolean estMort = false;
    private Objet[] obj = new Objet[255];
    private int nbObj = 0;
    private int nbLife = 0;
    private int nbCoin = 0;
    private int nbSword = 0;
    private boolean peutAttaqueSpeciale = false;

    /**
     * Constructeur avec position initiale spécifiée.
     *
     * @param x Position x du joueur.
     * @param y Position y du joueur.
     */
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructeur par défaut, positionne le joueur aux coordonnées (1, 1).
     */
    public Player() {
        this.x = 1;
        this.y = 1;
    }

    /**
     * Attaque un monstre, réduisant sa vie par la valeur d'attaque du joueur.
     *
     * @param monstre Le monstre à attaquer.
     */
    public void attaquer(Monster monstre) {
        monstre.setVie(monstre.getVie() - this.attaque);
        if (monstre.getVie() <= 0) {
            monstre.setMort(true);
        }
    }

    /**
     * Gèle tous les monstres d'un niveau si le joueur a un sort.
     *
     * @param lvl Le niveau contenant les monstres.
     */
    public void attaqueSpeciale(Level lvl) {
        if (peutAttaqueSpeciale == false) {
            return;
        }
        for (int i = 0; i < lvl.getMonstres().length; i++) {
            if (lvl.getMonstre(i) == null) {
                break;
            } else {
                lvl.getMonstre(i).setGel(true);
            }
        }
        peutAttaqueSpeciale = false;
    }

    /**
     * Dégèle tous les monstres d'un niveau.
     *
     * @param lvl Le niveau contenant les monstres.
     */
    public void finAttaqueSpeciale(Level lvl) {
        for (int i = 0; i < lvl.getMonstres().length; i++) {
            if (lvl.getMonstre(i) == null) {
                break;
            } else {
                lvl.getMonstre(i).setGel(false);
            }
        }
    }

    /**
     * Ramasse un objet et applique son effet sur le joueur.
     *
     * @param temp L'objet à ramasser.
     */
    public void rammasserObj(Objet temp) {
        if (temp.getNom().equals("Coin")) {
            nbCoin++;
        } else if (temp.getNom().equals("Life")) {
            nbLife++;
        } else if (temp.getNom().equals("Sword")) {
            nbSword++;
        }
        temp.effet(this);
        obj[nbObj] = temp;
        nbObj++;
    }

    /**
     * Fait avancer le joueur (décrémente sa position x).
     */
    public void avancer(Level level) {
        level.setChar(x, y, ' ');
        level.setChar(x - 1, y, 'P');
        this.x--;
    }

    /**
     * Fait reculer le joueur (incrémente sa position x).
     */
    public void reculer(Level level) {
        level.setChar(x, y, ' ');
        level.setChar(x + 1, y, 'P');
        this.x++;
    }

    /**
     * Déplace le joueur vers la droite (incrémente sa position y).
     */
    public void droite(Level level) {
        level.setChar(x, y, ' ');
        level.setChar(x, y + 1, 'P');
        this.y++;
    }

    /**
     * Déplace le joueur vers la gauche (décrémente sa position y).
     */
    public void gauche(Level level) {
        level.setChar(x, y, ' ');
        level.setChar(x, y - 1, 'P');
        this.y--;
    }

    /**
     * @return La position x du joueur.
     */
    public int getX() {
        return x;
    }

    /**
     * @return La position y du joueur.
     */
    public int getY() {
        return y;
    }

    /**
     * @return Le nombre d'objets ramassés par le joueur.
     */
    public int getNbObj() {
        return nbObj;
    }

    /**
     * @return Le nombre de pièces ramassées par le joueur.
     */
    public int getNbCoin() {
        return nbCoin;
    }

    /**
     * @return Le nombre de vies supplémentaires ramassées par le joueur.
     */
    public int getNbLife() {
        return nbLife;
    }

    /**
     * @return Le nombre d'épées ramassées par le joueur.
     */
    public int getNbSword() {
        return nbSword;
    }

    /**
     * @return True si le joueur a un sort, false sinon.
     */
    public boolean peutAttaqueSpeciale() {
        return peutAttaqueSpeciale;
    }

    /**
     * @return La vie du joueur.
     */
    public int getVie() {
        return vie;
    }

    /**
     * @return L'attaque du joueur.
     */
    public int getAttaque() {
        return attaque;
    }

    /**
     * @return L'argent du joueur.
     */
    public int getArgent() {
        return argent;
    }

    /**
     * @return True si le joueur est mort, false sinon.
     */
    public boolean getMort() {
        return estMort;
    }

    /**
     * @return Faire payer au joueur la somme souhaité.
     */
    public void payer(int somme) {
        this.argent -= somme;
    }

    /**
     * Définit si le joueur a un sort.
     *
     * @param peutAttaqueSpeciale True si le joueur a un sort, false sinon.
     */
    public void setSpell(boolean temp) {
        this.peutAttaqueSpeciale = temp;
    }

    /**
     * Définit la vie du joueur.
     *
     * @param vie La nouvelle valeur de vie du joueur.
     */
    public void setVie(int vie) {
        this.vie = vie;
    }

    /**
     * Définit l'attaque du joueur.
     *
     * @param attaque La nouvelle valeur d'attaque du joueur.
     */
    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    /**
     * Définit si le joueur est mort.
     *
     * @param mort True si le joueur est mort, false sinon.
     */
    public void setMort(boolean mort) {
        this.estMort = mort;
    }

    /**
     * Définit l'argent du joueur.
     *
     * @param argent La nouvelle valeur d'argent du joueur.
     */
    public void setArgent(int argent) {
        this.argent = argent;
    }

    /**
     * Définit la position du joueur.
     *
     * @param x La nouvelle position x du joueur.
     * @param y La nouvelle position y du joueur.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}