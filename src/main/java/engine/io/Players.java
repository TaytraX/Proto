package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Players {
    float x, z;
    private final Windows window;

    public Players(Windows window) {

        this.window = window;

    }

    public void updat(){

        if(GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_UP) == GL_TRUE) z += 0.01f;
        if(GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_W) == GL_TRUE) x += 0.01f;

        if(GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_DOWN) == GL_TRUE) z -= 0.01f;
        if(GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_S) == GL_TRUE) x -= 0.01f;

    }

    public void render(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glBegin(GL_QUADS);
        glVertex2f(-1.7f, 0.3f+x);
        glVertex2f(-1.65f, 0.3f+x);
        glVertex2f(-1.65f, -0.3f+x);
        glVertex2f(-1.7f, -0.3f+x);
        glVertex2f(1.7f, 0.3f+z);
        glVertex2f(1.65f, 0.3f+z);
        glVertex2f(1.65f, -0.3f+z);
        glVertex2f(1.7f, -0.3f+z);
        GL11.glEnd();
    }
}
