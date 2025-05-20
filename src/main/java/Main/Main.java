package Main;

import engine.io.Input;
import engine.io.Players;
import engine.io.Windows;
import physical.Collision;

import org.lwjgl.glfw.GLFW;

public class Main implements Runnable {

    public Thread game;
    public static Windows window;
    public static Players players;
    public static Collision collision;
    public static final int WIDTH = 1200, HEIGHT = 720;

    public void start(){
        game = new Thread(this, "Game");
        game.start();
    }

    public static void init(){
        System.out.println("Starting Game...");
        window = new Windows(WIDTH, HEIGHT, "Proto(1)_Pong");
        players = new Players(window);
        collision = new Collision();
        window.setBackgroundColor(0.0f, 0.0f, 0.0f);
        window.creat();
    }

    public void run() {
        init();

        while(!window.cleanUp() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)){
            update();
            render();
        }
        window.destroy();
    }

    private void update(){
        window.update();
        players.updat();
        collision.updatePhysics();
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)){
            System.out.println("X:" + Input.getMouseX() + ", y:" + Input.getMouseY());
        }
    }

    private void render(){

        window.swapBuffers();
        players.render();

    }

    public static void main(String[] args){
        new Main().start();
    }
}
