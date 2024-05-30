package ui;

import java.awt.*;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.Game;
import game.Level;

/**
 * Classe Panel : représente le panneau principal du jeu.
 */
public class PanelJeu extends JPanel {
    // Attributs

    private final int tailleFinale = 48;
    private final int maxColonnes = 11;
    private final int maxLignes = 9;

    private final int tailleFenetreX = tailleFinale * maxColonnes;
    private final int tailleFenetreY = tailleFinale * maxLignes;

    private Level lvl;
    private Game game;

    // Images
    private Image wall;
    private Image ground;
    private Image player;
    private Image monster;
    private Image chestOpen;
    private Image chestClosed;
    private Image life;
    private Image coin;
    private Image sword;
    private Image door;
    private Image monsterFreeze;
    private Image snowFlake;

    /**
     * Constructeur de la classe Panel.
     * 
     * @param lvl Niveau du jeu.
     * @param g   Instance de la classe Game.
     */
    public PanelJeu(Level lvl, Game g) {
        this.setSize(new Dimension(tailleFenetreX, tailleFenetreY));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.lvl = lvl;
        this.game = g;
        try {
            wall = loadImage("wall.png");
            ground = loadImage("ground.png");
            player = loadImage("player.png");
            monster = loadImage("monster.png");
            chestOpen = loadImage("chest-open.png");
            chestClosed = loadImage("chest-closed.png");
            life = loadImage("life.png");
            coin = loadImage("coin.png");
            sword = loadImage("sword.png");
            door = loadImage("door.png");
            monsterFreeze = loadImage("monster_freez.png");
            snowFlake = loadImage("snowflake.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /**
     * Méthode pour charger une image en fonction de si elle est chargée depuis le
     * jar ou le système de fichiers.
     * 
     * @param path Chemin de l'image.
     * @return Image chargée.
     * @throws IOException
     */
    private Image loadImage(String path) throws IOException {
        try {
            // Essayer de charger l'image depuis le système de fichiers
            return ImageIO.read(new File("src/game/resources/img/" + path));
        } catch (IOException e) {
            // Si cela échoue, essayer de charger l'image depuis le JAR
            return ImageIO.read(getClass().getResourceAsStream("/game/resources/img/" + path));
        }
    }

    /**
     * Méthode pour dessiner le panneau du jeu.
     * 
     * @param g Graphics utilisé pour dessiner.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int offsetX = (getWidth() - maxColonnes * tailleFinale) / 2;
        int offsetY = (getHeight() - maxLignes * tailleFinale) / 2;

        if (!game.isEnd()) {
            for (int i = 0; i < maxLignes; i++) {
                for (int j = 0; j < maxColonnes; j++) {
                    int x = offsetX + j * tailleFinale;
                    int y = offsetY + i * tailleFinale;

                    // Pour même ecrire en dessous des monstre coffre et joueur
                    if (lvl.getChar(i, j) != '#') {
                        g.drawImage(ground, x, y, tailleFinale, tailleFinale, this);
                    }

                    switch (lvl.getChar(i, j)) {
                        case '#':
                            g.drawImage(wall, x, y, tailleFinale, tailleFinale, this);
                            break;
                        case 'P':
                            g.drawImage(player, x, y, tailleFinale, tailleFinale, this);
                            break;
                        case 'D':
                            g.drawImage(door, x, y, tailleFinale, tailleFinale, this);
                            break;
                        case '+':
                            g.drawImage(chestClosed, x, y, tailleFinale, tailleFinale, this);
                            break;
                        case '.':
                            g.drawImage(chestOpen, x, y, tailleFinale, tailleFinale, this);
                            break;
                        case '$':
                            if (lvl.getMonstre(0).estGelé()) {
                                g.drawImage(monsterFreeze, x, y, tailleFinale, tailleFinale, this);
                            } else {
                                g.drawImage(monster, x, y, tailleFinale, tailleFinale, this);
                            }
                            break;
                        case 'E':
                            g.drawImage(sword, x, y, tailleFinale, tailleFinale, this);
                            break;
                        case 'B':
                            g.drawImage(life, x, y, tailleFinale, tailleFinale, this);
                            break;
                        case 'X':
                            g.drawImage(snowFlake, x, y, tailleFinale, tailleFinale, this);
                            break;
                        default:
                            break;
                    }
                }
            }
            if (lvl.isShop()) {
                afficherShop(g);
            }
            afficherObjets(g);
            displayAllInfos(g);
        }
    }

    /**
     * Méthode pour afficher les informations du joueur.
     * 
     * @param g Graphics utilisé pour dessiner.
     */
    public void displayAllInfos(Graphics g) {
        /* Afficher la vie en haut à gauche et en blanc */
        g.setColor(Color.WHITE);
        Font currentFont = g.getFont();
        Font font = currentFont.deriveFont(Font.BOLD);
        g.setFont(font);
        g.drawImage(life, 40, 384, tailleFinale, tailleFinale, this);
        g.drawString("" + lvl.getJoueur().getVie(), 80, 411);

        /* Afficher l'argent en haut à gauche */
        g.drawImage(coin, 231, 384, tailleFinale, tailleFinale, this);
        g.drawString("" + lvl.getJoueur().getArgent(), 271, 411);

        /* Afficher l'attaque en haut à gauche */
        g.drawImage(sword, 424, 384, tailleFinale, tailleFinale, this);
        g.drawString("" + lvl.getJoueur().getAttaque(), 464, 411);

        /* Afficher le niveau, en haut à droite */
        currentFont = g.getFont();
        font = currentFont.deriveFont(Font.BOLD, 24);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Niveau " + lvl.getNumLvl() + "/15", 380, 33);
    }

    /**
     * Méthode pour afficher la boutique.
     * 
     * @param g Graphics utilisé pour dessiner.
     */
    public void afficherShop(Graphics g) {
        Font cf = g.getFont();
        Font temp = cf.deriveFont(Font.BOLD, 15);
        g.setFont(temp);
        g.setColor(Color.WHITE);
        g.drawImage(coin, 160, 138, 26, 26, this);
        g.drawString("50", 150, 154);

        g.drawImage(coin, 160, 227, 26, 26, this);
        g.drawString("50", 150, 243);

        g.drawImage(coin, 360, 138, 26, 26, this);
        g.drawString("120", 340, 154);
    }

    /**
     * Méthode pour afficher les objets.
     * 
     * @param g Graphics utilisé pour dessiner.
     */
    public void afficherObjets(Graphics g) {
        Font currentFont = g.getFont();
        Font TEMP = currentFont.deriveFont(Font.BOLD);
        g.setFont(TEMP);
        g.setColor(Color.WHITE);
        g.drawImage(coin, 0, 0, tailleFinale, tailleFinale, this);
        g.drawString("+" + lvl.getJoueur().getNbCoin(), 25, 33);
        g.drawImage(life, 48, 0, tailleFinale, tailleFinale, this);
        g.drawString("+" + lvl.getJoueur().getNbLife(), 73, 33);
        g.drawImage(sword, 96, 0, tailleFinale, tailleFinale, this);
        g.drawString("+" + lvl.getJoueur().getNbSword(), 121, 33);
        if (lvl.getJoueur().peutAttaqueSpeciale()) {
            g.drawImage(snowFlake, 150, 7, 35, 35, this);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(tailleFenetreX, tailleFenetreY);
    }
}