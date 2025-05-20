package physical;

import Main.Main;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Collision {

    // Position de la balle
    private float ballX = 0.0f;
    private float ballY = 0.0f;

    // Rayon de la balle
    private final float ballRadius = 0.02f;

    // Vitesse de la balle - MODIFIEZ CES VALEURS POUR CHANGER LA VITESSE
    private float ballVX = 0.025f;
    private float ballVY = 0.025f;

    // Vitesse de base - pour pouvoir réinitialiser à la vitesse d'origine
    private final float BASE_SPEED_X;
    private final float BASE_SPEED_Y;

    // Limites du terrain
    private static final float TOP_BOUND = 0.93f;
    private static final float BOTTOM_BOUND = -0.93f;
    private static final float LEFT_BOUND = -1.65f;
    private static final float RIGHT_BOUND = 1.65f;

    // Pour le démarrage
    private boolean gameStarted = false;
    private Random random = new Random();
    private int scoreLeft = 0;
    private int scoreRight = 0;

    static int score;

    public Collision() {
        // Sauvegarde des valeurs initiales
        BASE_SPEED_X = ballVX;
        BASE_SPEED_Y = ballVY;

        // Initialisation avec une direction aléatoire
        initRandomDirection();
    }

    private void initRandomDirection() {
        // Choisir une direction aléatoire au début
        int direction = random.nextInt(4);

        // Réinitialiser la position de la balle
        ballX = 0.0f;
        ballY = 0.0f;

        // Réinitialiser les vitesses à leur valeur de base
        ballVX = BASE_SPEED_X;
        ballVY = BASE_SPEED_Y;

        // Modifier les signes selon la direction choisie
        switch (direction) {
            case 0 -> { /* Rien à changer - les deux sont positifs */ }
            case 1 -> { ballVY = -ballVY; } // Inverser Y
            case 2 -> { ballVX = -ballVX; } // Inverser X
            case 3 -> { ballVX = -ballVX; ballVY = -ballVY; } // Inverser les deux
        }

        gameStarted = true;
    }

    public void updatePhysics() {
        if (!gameStarted) {
            initRandomDirection();
        }

        // Mise à jour de la position de la balle
        // Utilise spécifiquement ballVX pour X et ballVY pour Y
        ballX += ballVX;
        ballY += ballVY;

        // Collision avec les murs (haut et bas)
        if (ballY + ballRadius >= TOP_BOUND) {
            ballY = TOP_BOUND - ballRadius; // Correction pour éviter de rester coincé
            ballVY = -ballVY; // Inversion de la vitesse verticale
        }
        else if (ballY - ballRadius <= BOTTOM_BOUND) {
            ballY = BOTTOM_BOUND + ballRadius; // Correction pour éviter de rester coincé
            ballVY = -ballVY; // Inversion de la vitesse verticale
        }

        // Collision avec les raquettes
        checkPaddleCollisions();

        // Vérifier si la balle sort du terrain (gauche ou droite)
        if (ballX + ballRadius < LEFT_BOUND || ballX - ballRadius > RIGHT_BOUND) {
            // La balle est sortie, réinitialiser
            initRandomDirection();
        }

        // Vérifier si la balle sort du terrain (gauche ou droite)
        if (ballX + ballRadius < LEFT_BOUND) {
            // Point pour le joueur de droite
            scoreRight++;
            System.out.println("Score: " + scoreLeft + " - " + scoreRight);
            initRandomDirection();
        } else if (ballX - ballRadius > RIGHT_BOUND) {
            // Point pour le joueur de gauche
            scoreLeft++;
            System.out.println("Score: " + scoreLeft + " - " + scoreRight);
            initRandomDirection();
        }

        // Dessiner la balle à sa position actuelle
        drawDisk(ballX, ballY, ballRadius, 30);
    }

    private void checkPaddleCollisions() {
        // Raquette gauche
        if (ballX - ballRadius <= -1.65f && ballX + ballRadius >= -1.7f && ballVX < 0) { // ballVX < 0 pour éviter les rebonds multiples
            // Vérifier si la balle est au niveau de la raquette verticalement
            float leftPaddleY = Main.players.getLeftPaddleY();
            if (ballY + ballRadius >= leftPaddleY - 0.3f && ballY - ballRadius <= leftPaddleY + 0.3f) {
                // Repositionner la balle pour éviter qu'elle reste coincée
                ballX = -1.65f + ballRadius;

                // Rebond horizontal
                ballVX = -ballVX;

                // Modifier légèrement l'angle selon où la balle frappe la raquette
                float relativeIntersectY = (leftPaddleY - ballY) / 0.3f;
                // Plus la balle frappe loin du centre, plus l'angle est prononcé
                ballVY = -relativeIntersectY * Math.abs(ballVX) * 1.5f;

                // Augmenter légèrement la vitesse à chaque rebond
                ballVX *= 1.05f;
            }
        }

        // Raquette droite
        if (ballX + ballRadius >= 1.65f && ballX - ballRadius <= 1.7f && ballVX > 0) { // ballVX > 0 pour éviter les rebonds multiples
            // Vérifier si la balle est au niveau de la raquette verticalement
            float rightPaddleY = Main.players.getRightPaddleY();
            if (ballY + ballRadius >= rightPaddleY - 0.3f && ballY - ballRadius <= rightPaddleY + 0.3f) {
                // Repositionner la balle pour éviter qu'elle reste coincée
                ballX = 1.65f - ballRadius;

                // Rebond horizontal
                ballVX = -ballVX;

                // Modifier légèrement l'angle selon où la balle frappe la raquette
                float relativeIntersectY = (rightPaddleY - ballY) / 0.3f;
                // Plus la balle frappe loin du centre, plus l'angle est prononcé
                ballVY = -relativeIntersectY * Math.abs(ballVX) * 1.5f;

                // Augmenter légèrement la vitesse à chaque rebond
                ballVX *= 1.05f;
            }
        }
    }

    public static void drawDisk(float cx, float cy, float r, int num_segments) {
        GL11.glBegin(GL11.GL_POLYGON);
        for (int i = 0; i < num_segments; i++) {
            double theta = 2.0 * Math.PI * i / num_segments;
            float x = (float) (r * Math.cos(theta));
            float y = (float) (r * Math.sin(theta));
            GL11.glVertex2f(cx + x, cy + y);
        }
        GL11.glEnd();
    }

    // Méthodes pour modifier la vitesse de la balle dynamiquement
    public void setBallSpeed(float vx, float vy) {
        // Conserver le signe (direction) mais changer la magnitude (vitesse)
        this.ballVX = vx * Math.signum(this.ballVX);
        this.ballVY = vy * Math.signum(this.ballVY);
    }

    // Getters pour la position de la balle (pour usage externe)
    public float getBallX() {
        return ballX;
    }

    public float getBallY() {
        return ballY;
    }

    public float getBallRadius() {
        return ballRadius;
    }

    public float getBallVX() {
        return ballVX;
    }

    public float getBallVY() {
        return ballVY;
    }
}