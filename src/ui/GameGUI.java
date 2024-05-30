package ui;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;

import game.*;

/**
 * La classe <code>GameGUI</code> représente l'interface graphique du jeu
 * "Monstre Hunter".
 * Elle gère les différents panneaux du jeu et la boucle de jeu principale.
 */
public class GameGUI {
    private JFrame frame = new JFrame("Monstre Hunter");
    private Game game = null;
    private PanelJeu P1;
    private PanelStart P2;
    private PanelAlerte P3;

    private char input;
    private boolean continuer = false;
    private boolean isStart = true;
    private Complication C;

    /**
     * Constructeur de la classe <code>GameGUI</code>.
     * Initialise le jeu et démarre l'interface graphique.
     */
    public GameGUI() {
        try {
            game = new Game();
        } catch (Complication c) {
            C = c;
        }
        P1 = new PanelJeu(game.getLevel(), game);
        P2 = new PanelStart(game);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        start();
        gameLoop();
        if (C != null)
            displayAlert(C.getMessage(), Color.ORANGE, 20);
        else
            endGame();
    }

    /**
     * Démarre le jeu en affichant l'écran de démarrage.
     * Attend que l'utilisateur appuie sur la touche Entrée pour commencer le jeu.
     */
    public void start() {
        frame.add(P2);
        P2.setFocusable(true);
        P2.requestFocus();
        P2.requestFocusInWindow();
        frame.pack();
        KeyAdapter l = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    isStart = false;
                }
            }
        };
        P2.addKeyListener(l);
        while (isStart == true) {
            try {
                Thread.sleep(100);
                P2.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        P2.removeKeyListener(l);
    }

    /**
     * Boucle principale du jeu.
     * Gère les entrées utilisateur et met à jour l'affichage du jeu.
     */
    public void gameLoop() {
        frame.remove(P2);
        frame.add(P1);
        P1.setFocusable(true);
        P1.requestFocus();
        P1.requestFocusInWindow();
        frame.pack();
        KeyListener l = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                char in = e.getKeyChar();
                input = Character.toLowerCase(in);
                try {
                    game.gameLogique(input);
                    game.checkWin();
                    P1.repaint();
                    frame.revalidate();
                    frame.repaint();
                } catch (Complication c) {
                    C = c;
                    game.setEnd(true);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        P1.addKeyListener(l);
        while (!game.isEnd()) {
            try {
                Thread.sleep(100);
                P1.repaint();
                frame.revalidate();
                frame.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        P1.removeKeyListener(l);
    }

    /**
     * Termine le jeu et affiche un message indiquant si le joueur a gagné ou perdu.
     */
    public void endGame() {
        if (game.isWin())
            displayAlert("Vous avez gagné !", Color.GREEN, 40);
        else
            displayAlert("Vous avez perdu !", Color.ORANGE, 40);
    }

    /**
     * Affiche une alerte avec un message spécifique.
     *
     * @param errorMsg Le message d'erreur à afficher.
     * @param color    La couleur du texte du message.
     * @param size     La taille de la police du message.
     */
    public void displayAlert(String errorMsg, Color color, int size) {
        continuer = false;
        P3 = new PanelAlerte(game, errorMsg, color, size);
        frame.remove(P1);
        frame.add(P3);
        P3.setFocusable(true);
        P3.requestFocus();
        P3.requestFocusInWindow();
        frame.pack();
        frame.revalidate();
        frame.repaint();
        KeyListener t = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    continuer = true;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        P3.addKeyListener(t);
        while (continuer == false) {
            try {
                Thread.sleep(200);
                P3.repaint();
                frame.revalidate();
                frame.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        P3.removeKeyListener(t);
        System.exit(0);
    }
}