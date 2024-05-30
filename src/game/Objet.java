package game;

import java.util.Random;

/**
 * Classe représentant un objet dans le jeu.
 */
public class Objet {
    private String nom;

    /**
     * Constructeur par défaut.
     */
    public Objet() {
    }

    /**
     * Constructeur avec nom spécifié.
     *
     * @param nom Le nom de l'objet.
     */
    public Objet(String nom) {
        this.nom = nom;
    }

    /**
     * @return Le nom de l'objet.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Génère un objet aléatoire parmi les types Coin, Sword, et Life.
     *
     * @return Un objet aléatoire.
     */
    public Objet genererObjetAleatoire() {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        switch (randomNumber) {
            case 0:
                return new Coin();
            case 1:
                return new Sword();
            case 2:
                return new Life();
            default:
                return null;
        }
    }

    /**
     * Applique l'effet de l'objet sur un joueur.
     *
     * @param j Le joueur sur lequel appliquer l'effet.
     */
    public void effet(Player j) {
    }
}

/**
 * Classe représentant une pièce (Coin) dans le jeu.
 */
class Coin extends Objet {
    /**
     * Constructeur par défaut.
     */
    public Coin() {
        super("Coin");
    }

    /**
     * Applique l'effet de l'objet sur un joueur, ajoutant de l'argent.
     *
     * @param j Le joueur sur lequel appliquer l'effet.
     */
    @Override
    public void effet(Player j) {
        j.setArgent(j.getArgent() + 20);
    }
}

/**
 * Classe représentant une épée (Sword) dans le jeu.
 */
class Sword extends Objet {
    /**
     * Constructeur par défaut.
     */
    public Sword() {
        super("Sword");
    }

    /**
     * Applique l'effet de l'objet sur un joueur, augmentant son attaque.
     *
     * @param j Le joueur sur lequel appliquer l'effet.
     */
    @Override
    public void effet(Player j) {
        j.setAttaque(j.getAttaque() + 10);
    }
}

/**
 * Classe représentant une vie (Life) dans le jeu.
 */
class Life extends Objet {
    /**
     * Constructeur par défaut.
     */
    public Life() {
        super("Life");
    }

    /**
     * Applique l'effet de l'objet sur un joueur, augmentant sa vie.
     *
     * @param j Le joueur sur lequel appliquer l'effet.
     */
    @Override
    public void effet(Player j) {
        j.setVie(j.getVie() + 20);
    }
}