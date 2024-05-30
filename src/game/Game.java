package game;

/**
 * Classe représentant le jeu Monstre Hunter.
 */
public class Game {
    private Level level;
    private boolean isEnd = false;
    private boolean isWin = false;

    /**
     * Constructeur par défaut de la classe Game.
     * 
     * @throws Complication Si une complication survient lors de la création du jeu.
     */
    public Game() throws Complication {
        this.level = new Level(1);
    }

    /**
     * Logique du jeu en fonction de l'entrée utilisateur.
     *
     * @param input Le caractère de commande entré par l'utilisateur.
     * @throws Complication Si une complication survient lors de l'exécution de la
     *                      logique du jeu.
     */
    public void gameLogique(char input) throws Complication {
        // Tour du level.getJoueur()
        switch (input) {
            case 'z':
                if (level.getChar(level.getJoueur().getX() - 1, level.getJoueur().getY()) == ' ') {
                    level.getJoueur().avancer(this.level);
                } else {
                    checkNext(level.getJoueur().getX() - 1, level.getJoueur().getY());
                }
                break;
            case 'q':
                if (level.getChar(level.getJoueur().getX(), level.getJoueur().getY() - 1) == ' ') {
                    level.getJoueur().gauche(this.level);
                } else {
                    checkNext(level.getJoueur().getX(), level.getJoueur().getY() - 1);
                }
                break;
            case 's':
                if (level.getChar(level.getJoueur().getX() + 1, level.getJoueur().getY()) == ' ') {
                    level.getJoueur().reculer(this.level);
                } else {
                    checkNext(level.getJoueur().getX() + 1, level.getJoueur().getY());
                }
                break;
            case 'd':
                if (level.getChar(level.getJoueur().getX(), level.getJoueur().getY() + 1) == ' ') {
                    level.getJoueur().droite(this.level);
                } else {
                    checkNext(level.getJoueur().getX(), level.getJoueur().getY() + 1);
                }
                break;
            case 'e':
                level.getJoueur().attaqueSpeciale(level);
                new Thread(() -> {
                    try {
                        Thread.sleep(3500); // Wait for 3.5 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isEnd) {
                        return;
                    }
                    level.getJoueur().finAttaqueSpeciale(level);
                }).start();
                break;
            case 'o':
                System.exit(0);
                break;
            default:
                break;
        }
        deplacerMonstres();
        checkDoors();
    }

    /**
     * Gère le combat entre un joueur et un monstre. (Ils se battent jusq'à ce que
     * l'un des deux meurt.)
     *
     * @param joueur  Le joueur.
     * @param monstre Le monstre.
     */
    public void combat(Player joueur, Monster monstre) {
        if (monstre.estGelé() == true) {
            monstre.setVie(0);
            monstre.setMort(true);
            level.setChar(monstre.getX(), monstre.getY(), ' ');
            level.setNbMonstres(level.getNbMonstres() - 1);
            level.getJoueur().setArgent(level.getJoueur().getArgent() + 20);
        } else {
            while (joueur.getVie() > 0 && monstre.getVie() > 0) {
                joueur.attaquer(monstre);
                if (monstre.getVie() <= 0) {
                    monstre.setMort(true);
                    level.setChar(monstre.getX(), monstre.getY(), ' ');
                    level.setNbMonstres(level.getNbMonstres() - 1);
                    level.getJoueur().setArgent(level.getJoueur().getArgent() + 20);
                }
                monstre.attaquer(joueur);
                if (joueur.getVie() <= 0) {
                    this.isEnd = true;
                    this.isWin = false;
                }
            }
        }
    }

    /**
     * Déplace tous les monstres vers le joueur.
     */
    public void deplacerMonstres() {
        for (int i = 0; i < level.getMonstres().length; i++) {
            Monster monstre = level.getMonstre(i);
            if (monstre != null && !monstre.getMort() && !monstre.estGelé()) {
                int dx = level.getJoueur().getX() - monstre.getX();
                int dy = level.getJoueur().getY() - monstre.getY();

                // Choix de la direction en fonction de la distance
                int newX = monstre.getX();
                int newY = monstre.getY();

                if (Math.abs(dx) > Math.abs(dy)) {
                    newX += Integer.signum(dx);
                } else {
                    newY += Integer.signum(dy);
                }

                // Vérification si la nouvelle position est valide
                if (level.getChar(newX, newY) == ' ') {
                    level.setChar(monstre.getX(), monstre.getY(), ' ');
                    monstre.setPosition(newX, newY);
                    level.setChar(newX, newY, '$');
                } else if (level.getChar(newX, newY) == 'P') {
                    combat(level.getJoueur(), monstre);
                }
            }
        }
    }

    /**
     * Vérifie si les portes dans le niveau peuvent être ouvertes (morts de ses
     * monstres).
     */
    public void checkDoors() {
        for (int i = 0; i < level.getPortes().length; i++) {
            if (level.getPortes()[i] == null) {
                break;
            } else {
                level.getPortes()[i].toutMonstresMort();
                if (level.getPortes()[i].estOuverte()
                        && level.getChar(level.getPortes()[i].getX(), level.getPortes()[i].getY()) == 'D') {
                    level.setChar(level.getPortes()[i].getX(), level.getPortes()[i].getY(), ' ');
                }
            }
        }
    }

    /**
     * Vérifie et effectue les changements de niveau, ainsi que les cases spéciales
     * telles que les combats, les objets, etc.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     * @throws Complication Si une complication survient lors de la vérification des
     *                      cases.
     */
    public void checkNext(int x, int y) throws Complication {
        switch (level.getChar(x, y)) {
            case '_':
                // Chargement du prochain niveau en local
                level.setNumLvl(level.getNumLvl() + 1);
                if (level.getNumLvl() <= 4) {
                    if (level.isShop()) {
                        level.setShop(false);
                        level.loadLvl();
                    } else if ((level.getNumLvl() - 1) % 3 == 0) {
                        level.afficherShop();
                    } else {
                        level.loadLvl();
                    }
                }
                // Création d'un niveau aléatoire
                else {
                    if (level.isShop()) {
                        level.setShop(false);
                        level.createRandomLvl();
                    } else if ((level.getNumLvl() - 1) % 3 == 0) {
                        level.afficherShop();
                    } else {
                        level.createRandomLvl();
                    }
                }
                if (level.getNumLvl() > 15) {
                    this.isEnd = true;
                    this.isWin = true;
                    break;
                }
                level.initializePositions();
                break;

            case '$':
                for (int i = 0; i < level.getMonstres().length; i++) {
                    if (level.getMonstres()[i] == null) {
                        break;
                    }
                    if (level.getMonstres()[i].getX() == x && level.getMonstres()[i].getY() == y) {
                        combat(level.getJoueur(), level.getMonstres()[i]);
                    }
                }
                break;
            case '+':
                Objet temp = new Objet();
                temp = temp.genererObjetAleatoire();
                level.getJoueur().rammasserObj(temp);
                level.setChar(x, y, '.');
                break;
            case 'E':
                if (level.getJoueur().getArgent() >= 50) {
                    level.getJoueur().rammasserObj(new Sword());
                    level.getJoueur().payer(50);
                }
                break;

            case 'B':
                if (level.getJoueur().getArgent() >= 50) {
                    level.getJoueur().rammasserObj(new Life());
                    level.getJoueur().payer(50);
                }
            case 'X':
                if (level.getJoueur().getArgent() >= 120) {
                    level.getJoueur().setSpell(true);
                    level.getJoueur().payer(120);
                }
            default:
                break;
        }
    }

    /**
     * Vérifie si le joueur a réussi le niveau (débloque la porte).
     */
    public void checkWin() {
        if (level.getNbMonstres() == 0 && level.getChar(0, level.getColonnes() / 2) == '#') {
            level.setChar(0, level.getColonnes() / 2, '_');
        }
    }

    /**
     * @return Le niveau actuel.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * @return True si le jeu est terminé, false sinon.
     */
    public boolean isEnd() {
        return this.isEnd;
    }

    /**
     * @return True si le joueur a gagné, false sinon.
     */
    public boolean isWin() {
        return this.isWin;
    }

    /**
     * Définit si le jeu est terminé.
     *
     * @param isEnd True si le jeu est terminé, false sinon.
     */
    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    /**
     * Définit si le joueur a gagné.
     *
     * @param isWin True si le joueur a gagné, false sinon.
     */
    public void setWin(boolean isWin) {
        this.isWin = isWin;
    }
}
