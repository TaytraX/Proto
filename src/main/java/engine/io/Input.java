package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Input {

    private static final boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static final boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX, mouseY;

    private static GLFWKeyCallback keyboard;
    private static GLFWCursorPosCallback mousseMove;
    private static GLFWMouseButtonCallback mouseButton;

    public Input() {

        keyboard = new GLFWKeyCallback(){
            @Override
            public void invoke(long window, int key, int scandade, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };

        mousseMove = new GLFWCursorPosCallback(){
            @Override
            public void invoke(long window, double Xpos, double Ypos) {
                mouseX = Xpos;
                mouseY = Ypos;
            }
        };

        mouseButton = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };

    }

    public static boolean isKeyDown(int key){
        return keys[key];
    }

    public static boolean isButtonDown(int button){
        return buttons[button];
    }

    public static void destroy(){
        keyboard.free();
        mousseMove.free();
        mouseButton.free();
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public GLFWKeyCallback getKeyboard() {
        return keyboard;
    }

    public GLFWCursorPosCallback getMousseMove() {
        return mousseMove;
    }

    public GLFWMouseButtonCallback getMouseButton() {
        return mouseButton;
    }
}