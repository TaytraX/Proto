
package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Players {
    // Position des raquettes
    private float leftPaddleY = 0.0f;
    private float rightPaddleY = 0.0f;

    // Taille des raquettes
    private final float PADDLE_HEIGHT = 0.3f;
    private final float PADDLE_WIDTH = 0.05f;

    // Vitesse de déplacement des raquettes
    private final float PADDLE_SPEED = 0.025f;

    private final Windows window;

    public Players(Windows window) {
        this.window = window;
    }

    public void updat() {
        // Contrôles de la raquette gauche (touches W/S)
        if (GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_W) == GL_TRUE)
            leftPaddleY += PADDLE_SPEED;

        if (GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_S) == GL_TRUE)
            leftPaddleY -= PADDLE_SPEED;

        // Contrôles de la raquette droite (touches UP/DOWN)
        if (GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_UP) == GL_TRUE)
            rightPaddleY += PADDLE_SPEED;

        if (GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_DOWN) == GL_TRUE)
            rightPaddleY -= PADDLE_SPEED;

        // Limiter le mouvement des raquettes pour qu'elles ne sortent pas de l'écran
        leftPaddleY = clamp(leftPaddleY, -0.95f + PADDLE_HEIGHT, 0.95f - PADDLE_HEIGHT);
        rightPaddleY = clamp(rightPaddleY, -0.95f + PADDLE_HEIGHT, 0.95f - PADDLE_HEIGHT);
    }

    private float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // Dessiner la raquette gauche
        GL11.glBegin(GL_QUADS);
        glVertex2f(-1.7f, PADDLE_HEIGHT + leftPaddleY);
        glVertex2f(-1.7f + PADDLE_WIDTH, PADDLE_HEIGHT + leftPaddleY);
        glVertex2f(-1.7f + PADDLE_WIDTH, -PADDLE_HEIGHT + leftPaddleY);
        glVertex2f(-1.7f, -PADDLE_HEIGHT + leftPaddleY);
        GL11.glEnd();

        // Dessiner la raquette droite
        GL11.glBegin(GL_QUADS);
        glVertex2f(1.7f, PADDLE_HEIGHT + rightPaddleY);
        glVertex2f(1.7f - PADDLE_WIDTH, PADDLE_HEIGHT + rightPaddleY);
        glVertex2f(1.7f - PADDLE_WIDTH, -PADDLE_HEIGHT + rightPaddleY);
        glVertex2f(1.7f, -PADDLE_HEIGHT + rightPaddleY);
        GL11.glEnd();

        // Dessiner une ligne centrale pour le terrain
        GL11.glBegin(GL_LINES);
        GL11.glVertex2f(0.0f, 0.93f);
        GL11.glVertex2f(0.0f, -0.93f);
        GL11.glEnd();
    }

    // Getters pour l'accès aux positions des raquettes depuis la classe Collision
    public float getLeftPaddleY() {
        return leftPaddleY;
    }

    public float getRightPaddleY() {
        return rightPaddleY;
    }
}
