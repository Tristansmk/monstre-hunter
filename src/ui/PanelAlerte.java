package ui;

import javax.swing.JPanel;
import java.awt.*;
import game.Game;

/**
 * La classe <code>PanelAlerte</code> représente un panneau d'alerte affichant
 * un message spécifique à l'utilisateur.
 */
public class PanelAlerte extends JPanel {
    private String str;
    private Color c;
    private int taille;

    private final int tailleFinale = 48;
    private final int maxColonnes = 11;
    private final int maxLignes = 9;

    private final int tailleFenetreX = tailleFinale * maxColonnes;
    private final int tailleFenetreY = tailleFinale * maxLignes;

    /**
     * Constructeur de la classe <code>PanelAlerte</code>.
     *
     * @param g      Le jeu en cours.
     * @param x      Le message à afficher.
     * @param c      La couleur du texte du message.
     * @param taille La taille de la police du message.
     */
    public PanelAlerte(Game g, String x, Color c, int taille) {
        this.setPreferredSize(new Dimension(528, 432));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        this.str = x;
        this.c = c;
        this.taille = taille;
    }

    /**
     * Surcharge de la méthode <code>paintComponent</code> pour dessiner le message
     * d'alerte.
     *
     * @param g L'objet <code>Graphics</code> utilisé pour dessiner le composant.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        Font currentFont = g.getFont();
        Font font = currentFont.deriveFont(Font.BOLD, taille);
        g.setFont(font);
        g.setColor(c);
        FontMetrics fm = g.getFontMetrics();
        int largeurTexte = fm.stringWidth(this.str);
        int hauteurTexte = fm.getHeight();
        int x = (tailleFenetreX - largeurTexte) / 2;
        int y = (tailleFenetreY - hauteurTexte) / 2 + fm.getAscent();
        g.drawString(str, x, y);
        g.setFont((g.getFont()).deriveFont(Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Appuyez sur entrer pour quitter", x, y + 50);
    }
}
