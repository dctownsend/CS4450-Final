/*
 * file: FinalProject.java
 * author: Sebastian, Diane, Nghi, Dakota
 * group: Synchronized Broncos
 * class: CS4450 - Computer Graphic
 *
 * assignment: Semester Project
 * date last modified: 3/28/2019
 *
 * purpose: This program display a 3d cube and the user can move around the 
 *     evironment using keyboard wasd or mouse.
 */

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

public class FinalProject {
    private FPCameraController fp;
    private DisplayMode displayMode;
    
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
    
    public FinalProject() {
        
    }
    
    private static final int SIZE = 4;
    private static Vector<Chunk> objects = new Vector<Chunk>();
    private static Vector<SimplexNoise> lights = new Vector<SimplexNoise>();
    private static DisplayMode displayMod;
    
     public static int getSIZE() {
        return SIZE;
    }
    public static void addObject(Chunk chunk){
        objects.add(chunk);
    }
    
    
    public static void addLight(SimplexNoise light){
        lights.add(light);
    }
    public static void Render(){
        updateLights();
        for(Chunk c : objects){
            if(c.isActive){
                c.draw();
            }
        }
    }
    public static void updateLights(){
        for(SimplexNoise l : lights){
            l.setPos(new Vector3f((float)l.getOrbitPoint().x, (float)(l.getOrbitPoint().y - Math.sin(Math.toRadians(l.orbitAngle))*l.getOrbitRadius()),(float)(l.getOrbitPoint().z - Math.cos(Math.toRadians(l.orbitAngle))*l.getOrbitRadius())));
            l.orbitAngle += .30;
            glLight(GL_LIGHT0, GL_POSITION, l.getLightPosition());
            glLight(GL_LIGHT0, GL_SPECULAR, l.getSpecularLight());
            glLight(GL_LIGHT0, GL_DIFFUSE, l.getDiffuseLight());
            glLight(GL_LIGHT0, GL_AMBIENT, l.getAmbientLight());
            glLight(GL_LIGHT0, GL_SPOT_DIRECTION, l.getLightDirection());

        } 
    }

    
    public void start() {
        try {
            createWindow();
            initGL();
            fp = new FPCameraController(0f, 0f, 0f);
            fp.gameLoop();
            updateLights();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        
        for(int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480 && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        
        Display.setDisplayMode(displayMode);
        Display.setTitle("Final Project");
        Display.create();
    }
    
    private void initGL() {
        glClearColor(0.1f, 0.0f, 0.4f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float)displayMode.getWidth()/(float)
        displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnableClientState (GL_TEXTURE_COORD_ARRAY);
        
       initLightArrays();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        updateLights();
    }
    
    private void initLightArrays() {
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();

    }
    
    public static void main(String args[]) {
        System.out.println("hi");
        
        FinalProject basic = new FinalProject();
        basic.start();
    }
}
