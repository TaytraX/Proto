package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Windows {

    private int width, height;
    private final String title;
    private long windows;
    public Input input;
    private float backgroundR, backgroundG, backgroundB;;


    public Windows(int width, int height, String title){

        this.width = width;
        this.height = height;
        this.title = title;

    }

    public void creat(){

        if(!GLFW.glfwInit()){
            System.err.println("GLFW WAS NOT INITIALISED !");
            return;
        }

        input = new Input();
        windows = GLFW.glfwCreateWindow(width, height, title,0, 0);

        if(windows == 0){
            System.err.println("WINDOWS CANNOT CREAT !");
            return;
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        assert videoMode != null;
        GLFW.glfwSetWindowPos(windows, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        GLFW.glfwMakeContextCurrent(windows);
        GL.createCapabilities();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        float aspect = (float) width / (float) height;
        if (aspect >= 1.0f) {
            GL11.glOrtho(-aspect, aspect, -1, 1, -1, 1);
        } else {
            GL11.glOrtho(-1, 1, -1 / aspect, 1 / aspect, -1, 1);
        }
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        createCallbacks();

        GLFW.glfwShowWindow(windows);
        GLFW.glfwSwapInterval(1);


    }

    private void createCallbacks(){

        GLFWWindowSizeCallback sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
            }
        };

        GLFW.glfwSetKeyCallback(windows, input.getKeyboard());
        GLFW.glfwSetCursorPosCallback(windows, input.getMousseMove());
        GLFW.glfwSetMouseButtonCallback(windows, input.getMouseButton());
        GLFW.glfwSetWindowSizeCallback(windows, sizeCallback);

    }


    public void update(){

        GLFW.glfwPollEvents();

        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(backgroundR, backgroundG, backgroundB, 1.0f);

    }

    public void swapBuffers(){

        GLFW.glfwSwapBuffers(windows);
    }

    public boolean cleanUp(){
        return GLFW.glfwWindowShouldClose(windows);
    }

    public void destroy(){
        Input.destroy();
        GLFW.glfwDestroyWindow(windows);
        GLFW.glfwTerminate();
    }

    public void setBackgroundColor(float r, float g, float b){
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
    }

    public long getWindow(){
        return windows;
    }
}