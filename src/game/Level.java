package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * La classe <code>Level</code> représente un niveau dans le jeu "Monstre
 * Hunter".
 * Elle contient les informations et les opérations nécessaires pour gérer les
 * niveaux.
 */
public class Level {
    private char[][] lvl;
    private int nombreDeLignes;
    private int nombreDeColonnes;
    private int numLvl;
    private boolean shop = false;
    private Player joueur;
    private Monster[] monstres;
    private Door[] portes;
    private int nbMonstres;
    private int nbPortes;

    // Constantes pour la taille maximale des monstres et des portes
    private static final int MAX_MONSTRES = 10;
    private static final int MAX_PORTES = 5;

    /**
     * Constructeur de la classe <code>Level</code>.
     *
     * @param num Le numéro du niveau.
     * @throws Complication Si une complication survient lors du chargement du
     *                      niveau.
     */
    public Level(int num) throws Complication {
        this.numLvl = num;
        loadLvl();
        this.joueur = new Player();
        initializePositions();
    }

    /**
     * Charge le niveau depuis un fichier.
     *
     * @throws Complication Si une complication survient lors du chargement du
     *                      niveau.
     */
    public void loadLvl() throws Complication {
        BufferedReader lecteurBuffer = null;
        try {
            nombreDeColonnes = 0;
            nombreDeLignes = 0;
            File fichier = new File("src/game/resources/levels" + getResourcePath());
            if (fichier.exists()) {
                // Lire depuis le système de fichiers (en développement)
                lecteurBuffer = new BufferedReader(new FileReader(fichier));
            } else {
                // Lire depuis les ressources dans le JAR (en production)
                InputStream inputStream = getClass()
                        .getResourceAsStream("/game/resources/levels" + getResourcePath());
                if (inputStream != null) {
                    lecteurBuffer = new BufferedReader(new InputStreamReader(inputStream));
                } else {
                    throw new IOException("Le fichier de niveau n'a pas été trouvé.");
                }
            }

            calculateDimensions(lecteurBuffer);
            try {
                lecteurBuffer.close();
            } catch (IOException e) {
                throw new Complication("Erreur lors de la fermeture du fichier de niveau.");
            }

            // Réinitialisation du lecteur pour lire à nouveau le fichier
            if (fichier.exists()) {
                lecteurBuffer = new BufferedReader(new FileReader(fichier));
            } else {
                InputStream inputStream = getClass()
                        .getResourceAsStream("/game/resources/levels" + getResourcePath());
                if (inputStream != null) {
                    lecteurBuffer = new BufferedReader(new InputStreamReader(inputStream));
                }
            }
            initializeLevel(lecteurBuffer);
        } catch (IOException e) {
            createRandomLvl();
        } finally {
            if (lecteurBuffer != null) {
                try {
                    lecteurBuffer.close();
                } catch (IOException e) {
                    throw new Complication("Erreur lors de la fermeture du fichier de niveau.");
                }
            }
        }
    }

    /**
     * Initialise le niveau à partir du fichier.
     *
     * @param lecteurBuffer Le buffer de lecture utilisé pour lire le fichier de
     *                      niveau.
     * @throws IOException Si une erreur survient lors de la lecture du fichier.
     */
    private void initializeLevel(BufferedReader lecteurBuffer) throws IOException {
        lvl = new char[nombreDeLignes][nombreDeColonnes];
        String ligne;
        int ligneActuelle = 0;
        while ((ligne = lecteurBuffer.readLine()) != null) {
            lvl[ligneActuelle] = ligne.toCharArray();
            ligneActuelle++;
        }
    }

    /**
     * Obtient le chemin de la ressource de niveau.
     *
     * @return Le chemin de la ressource de niveau.
     */
    private String getResourcePath() {
        if (shop) {
            return "/shop.txt";
        } else {
            return "/level_" + numLvl + ".txt";
        }
    }

    /**
     * Calcule les dimensions du niveau.
     *
     * @param lecteurBuffer Le buffer de lecture utilisé pour lire le fichier de
     *                      niveau.
     * @throws IOException Si une erreur survient lors de la lecture du fichier.
     */
    private void calculateDimensions(BufferedReader lecteurBuffer) throws IOException {
        String ligne;
        while ((ligne = lecteurBuffer.readLine()) != null) {
            nombreDeLignes++;
            if (nombreDeColonnes == 0) {
                nombreDeColonnes = ligne.length();
            }
        }
    }

    /**
     * Affiche le shop.
     *
     * @throws Complication Si une complication survient lors du chargement du shop.
     */
    public void afficherShop() throws Complication {
        shop = true;
        loadLvl();
    }

    /**
     * Initialise les positions du joueur, des monstres et des portes.
     */
    public void initializePositions() {
        getPlayerPosition();
        getMonstersPosition();
        getDoorsPosition();
    }

    /**
     * Trouve la position du joueur dans le niveau.
     */
    public void getPlayerPosition() {
        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < lvl[i].length; j++) {
                if (lvl[i][j] == 'P') {
                    joueur.setPosition(i, j);
                }
            }
        }
    }

    /**
     * Trouve les positions des monstres dans le niveau.
     */
    public void getMonstersPosition() {
        monstres = new Monster[MAX_MONSTRES];
        nbMonstres = 0;
        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < lvl[i].length; j++) {
                if (lvl[i][j] == '$') {
                    monstres[nbMonstres] = new Monster(i, j);
                    nbMonstres++;
                }
            }
        }
    }

    /**
     * Trouve les positions des portes dans le niveau.
     */
    public void getDoorsPosition() {
        portes = new Door[MAX_PORTES];
        nbPortes = 0;
        for (int i = 0; i < lvl.length; i++) {
            for (int j = 0; j < lvl[i].length; j++) {
                if (lvl[i][j] == 'D') {
                    portes[nbPortes] = new Door(i, j);
                    assignMonstersToDoor(portes[nbPortes]);
                    nbPortes++;
                }
            }
        }
    }

    /**
     * Assigne les monstres à une porte.
     *
     * @param porte La porte à laquelle les monstres doivent être assignés.
     */
    private void assignMonstersToDoor(Door porte) {
        for (Monster monstre : monstres) {
            if (monstre != null && monstre.getX() == porte.getX() + 1) {
                porte.ajouterMonstre(monstre);
            }
        }
    }

    /**
     * Crée un niveau aléatoire.
     */
    public void createRandomLvl() {
        Random random = new Random();
        int rng = random.nextInt(3);
        switch (rng) {
            case 0:
                createlvl1();
                break;

            case 1:
                createlvl2();
                break;

            case 2:
                createlvl3();
                break;
        }
    }

    /**
     * Crée le niveau 1.
     */
    public void createlvl1() {
        for (int i = 0; i < nombreDeLignes; i++) {
            for (int j = 0; j < nombreDeColonnes; j++) {
                this.lvl[i][j] = ' ';
            }
        }
        for (int j = 0; j < nombreDeColonnes; j++) {
            this.lvl[0][j] = '#';
            this.lvl[nombreDeLignes - 1][j] = '#';
        }
        for (int i = 0; i < nombreDeLignes; i++) {
            this.lvl[i][0] = '#';
            this.lvl[i][nombreDeColonnes - 1] = '#';
        }
        this.lvl[7][5] = 'P';
        Random random = new Random();
        int nbMonstres = random.nextInt(3) + 1;
        for (int i = 0; i < nbMonstres; i++) {
            int x = random.nextInt(nombreDeLignes - 3) + 1;
            this.lvl[1][x] = '$';
        }
        int nbcoffre = random.nextInt(3);
        for (int i = 0; i < nbcoffre; i++) {
            int x = random.nextInt(nombreDeLignes - 4) + 1;
            int y = random.nextInt(nombreDeColonnes - 3) + 1;
            if (x != 1 && y != 5) {
                this.lvl[x][y] = '+';
            }
        }
    }

    /**
     * Crée le niveau 2.
     */
    public void createlvl2() {
        for (int i = 0; i < nombreDeLignes; i++) {
            for (int j = 0; j < nombreDeColonnes; j++) {
                this.lvl[i][j] = ' ';
            }
        }
        for (int j = 0; j < nombreDeColonnes; j++) {
            this.lvl[0][j] = '#';
            this.lvl[2][j] = '#';
            this.lvl[4][j] = '#';
            this.lvl[6][j] = '#';
            this.lvl[nombreDeLignes - 1][j] = '#';
        }
        for (int i = 0; i < nombreDeLignes; i++) {
            this.lvl[i][0] = '#';
            this.lvl[i][nombreDeColonnes - 1] = '#';
        }
        this.lvl[7][5] = 'P';
        Random random = new Random();
        int y = random.nextInt(nombreDeColonnes - 3) + 1;
        this.lvl[1][y] = '$';

        int rngdoor1 = random.nextInt(nombreDeColonnes - 3) + 1;
        int rngdoor2 = random.nextInt(nombreDeColonnes - 3) + 1;
        int rngdoor3 = random.nextInt(nombreDeColonnes - 3) + 1;

        this.lvl[2][rngdoor1] = 'D';
        this.lvl[4][rngdoor2] = 'D';
        this.lvl[6][rngdoor3] = ' ';
        int rngMonstre1 = random.nextInt(2) + 1;

        for (int i = 0; i < rngMonstre1; i++) {
            int ym = random.nextInt(nombreDeColonnes - 2) + 1;
            this.lvl[3][ym] = '$';
        }

        int rngMonstre2 = random.nextInt(2) + 1;

        for (int i = 0; i < rngMonstre2; i++) {
            int ym = random.nextInt(nombreDeColonnes - 2) + 1;
            this.lvl[5][ym] = '$';
        }
    }

    /**
     * Crée le niveau 3.
     */
    public void createlvl3() {
        for (int i = 0; i < nombreDeLignes; i++) {
            for (int j = 0; j < nombreDeColonnes; j++) {
                this.lvl[i][j] = ' ';
            }
        }
        for (int j = 0; j < nombreDeColonnes; j++) {
            this.lvl[0][j] = '#';
            this.lvl[2][j] = '#';
            this.lvl[4][j] = '#';
            this.lvl[5][j] = '#';
            this.lvl[6][j] = '#';
            this.lvl[nombreDeLignes - 1][j] = '#';
        }
        for (int i = 0; i < nombreDeLignes; i++) {
            this.lvl[i][0] = '#';
            this.lvl[i][nombreDeColonnes - 1] = '#';
        }
        this.lvl[7][5] = 'P';
        Random random = new Random();
        int nbcoffre = random.nextInt(2);
        if (nbcoffre == 1) {
            int LeftRight = random.nextInt(2);
            if (LeftRight == 0) {
                this.lvl[7][1] = '+';
            } else {
                this.lvl[7][9] = '+';
            }
        }
        int rngdoor = random.nextInt(nombreDeColonnes - 3) + 1;
        this.lvl[2][rngdoor] = 'D';
        int rngMonstre1 = random.nextInt(2);
        if (rngMonstre1 == 1) {
            int ym = random.nextInt(nombreDeColonnes - 2) + 1;
            this.lvl[1][ym] = '$';

        }
        int rngMonstre2 = random.nextInt(2) + 1;
        for (int i = 0; i < rngMonstre2; i++) {
            int ym = random.nextInt(nombreDeColonnes - 2) + 1;
            this.lvl[3][ym] = '$';
        }
        int passage = random.nextInt(nombreDeColonnes - 3) + 1;
        this.lvl[4][passage] = ' ';
        this.lvl[5][passage] = ' ';
        this.lvl[6][passage] = ' ';
    }

    // Getters
    public char getChar(int x, int y) {
        return lvl[x][y];
    }

    public Monster getMonstre(int i) {
        return monstres[i];
    }

    public int getNumLvl() {
        return numLvl;
    }

    public int getNbMonstres() {
        return nbMonstres;
    }

    public int getColonnes() {
        return nombreDeColonnes;
    }

    public int getLignes() {
        return nombreDeLignes;
    }

    public boolean isShop() {
        return shop;
    }

    public Monster[] getMonstres() {
        return monstres;
    }

    public Door[] getPortes() {
        return portes;
    }

    public int getNbPortes() {
        return nbPortes;
    }

    public Player getJoueur() {
        return joueur;
    }

    // Setters
    public void setShop(boolean shop) {
        this.shop = shop;
    }

    public void setNumLvl(int numLvl) {
        this.numLvl = numLvl;
    }

    public void setNbMonstres(int nbMonstres) {
        this.nbMonstres = nbMonstres;
    }

    public void setChar(int x, int y, char c) {
        lvl[x][y] = c;
    }
}
