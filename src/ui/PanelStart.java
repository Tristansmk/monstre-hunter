package ui;

import game.Game;
import javax.swing.JPanel;
import java.awt.*;

/**
 * La classe <code>PanelStart</code> représente l'écran de démarrage du jeu
 * "Monstre Hunter".
 */
public class PanelStart extends JPanel {
    /**
     * Constructeur de la classe <code>PanelStart</code>.
     *
     * @param g Le jeu en cours.
     */
    public PanelStart(Game g) {
        this.setPreferredSize(new Dimension(528, 432));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
    }

    /**
     * Méthode pour dessiner l'écran de démarrage.
     * 
     * @param g Graphics utilisé pour dessiner.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        Font currentFont = g.getFont();
        Font font = currentFont.deriveFont(Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();

        String[] lines = {
                "Bienvenu dans Monstre Hunter",
                "",
                "Touches :",
                "",
                "z: Avancer",
                "q: Aller à gauche",
                "s: Reculer",
                "d: Aller à droite",
                "e: Utiliser l'attaque spéciale",
                "",
                "o: Quitter le jeu",
                "",
                "Appuyez sur Entrée pour continuer"
        };

        int lineHeight = fm.getHeight();
        int y = (getHeight() - (lines.length * lineHeight)) / 2;

        for (String line : lines) {
            if (line == lines[4] || line == lines[5] || line == lines[6] || line == lines[7] || line == lines[9]) {
                g.drawString(line, ((getWidth() - fm.stringWidth(lines[4])) / 2), y);
            } else {
                int x = (getWidth() - fm.stringWidth(line)) / 2;
                g.drawString(line, x, y);
            }
            y += lineHeight;
        }
    }
}