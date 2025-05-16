package physical;

import engine.io.Windows;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.World;
import org.lwjgl.opengl.GL11;

public class Collision {

    private static World world;
    private static Body circle;
    static float cx = 0.0f;
    static float r = 0.02f;
    static float cy = 0.8f;

    public static void init(){
        world = new World();
        world.setGravity(new Vector2(0, -9.81));

        circle = new Body();
        circle.addFixture(Geometry.createCircle(20.0));
        circle.setMass(MassType.NORMAL);
        circle.translate(400, 500); // position initiale
        world.addBody(circle);
    }

    public static void updatePhysics() {
        Vector2 pos = circle.getWorldCenter();
        drawDisk(cx, cy, r, 100000);
        double timeStep = 1.0 / 60.0;
        world.update(timeStep);
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

}
