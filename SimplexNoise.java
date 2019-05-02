import java.util.Random;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import java.nio.FloatBuffer;
import org.lwjgl.Sys;
import static org.lwjgl.opengl.GL11.glClearColor;

public class SimplexNoise {

    SimplexNoise_octave[] octaves;
    double[] frequencys;
    double[] amplitudes;

    int largestFeature;
    double persistence;
    int seed;
    
    private FloatBuffer lightPositionBuffer;
    private FloatBuffer ambientLightBuffer;
    private FloatBuffer diffuseLightBuffer;
    private FloatBuffer specularLightBuffer;
    private Vector3f pos;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private Vector3f orbitPoint;
    private FloatBuffer lightDirection;
    public double orbitAngle;
    private float orbitRadius;

    public SimplexNoise() {
        pos = new Vector3f(50,50,100);
        
        ambient = new Vector3f(.3f,.3f,.3f);
        diffuse = new Vector3f(1,1,1);
        specular = new Vector3f(.6f,.6f,.6f);
        //initLightArrays();
        FinalProject.addLight(this);
    }
    
    public SimplexNoise(int largestFeature,double persistence, int seed){
        this.largestFeature=largestFeature;
        this.persistence=persistence;
        this.seed=seed;

        //recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        int numberOfOctaves=(int)Math.ceil(Math.log10(largestFeature)/Math.log10(2));

        octaves=new SimplexNoise_octave[numberOfOctaves];
        frequencys=new double[numberOfOctaves];
        amplitudes=new double[numberOfOctaves];

        Random rnd=new Random(seed);

        for(int i=0;i<numberOfOctaves;i++){
            octaves[i]=new SimplexNoise_octave(rnd.nextInt());

            frequencys[i] = Math.pow(2,i);
            amplitudes[i] = Math.pow(persistence,octaves.length-i);

        }

    }


    public double getNoise(int x, int y){

        double result=0;

        for(int i=0;i<octaves.length;i++){
          //double frequency = Math.pow(2,i);
          //double amplitude = Math.pow(persistence,octaves.length-i);

          result=result+octaves[i].noise(x/frequencys[i], y/frequencys[i])* amplitudes[i];
        }


        return result;

    }

    public double getNoise(int x,int y, int z){

        double result=0;

        for(int i=0;i<octaves.length;i++){
          double frequency = Math.pow(2,i);
          double amplitude = Math.pow(persistence,octaves.length-i);

          result=result+octaves[i].noise(x/frequency, y/frequency,z/frequency)* amplitude;
        }


        return result;

    }
    
    public SimplexNoise(Vector3f pos){
        this.pos = pos;
        ambient = new Vector3f(.3f,.3f,.3f);
        diffuse = new Vector3f(.9f,.9f,.9f);
        specular = new Vector3f(.5f,.5f,.5f);
        //initLightArrays();
        FinalProject.addLight(this);
    }
    public SimplexNoise(Vector3f pos, Vector3f ambient, Vector3f diffuse, Vector3f specular){
        this.pos = pos;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
       // initLightArrays();
        FinalProject.addLight(this);
    }

    public FloatBuffer getLightPosition() {
        return lightPositionBuffer;
    }

    public FloatBuffer getAmbientLight() {
        return ambientLightBuffer;
    }

    public FloatBuffer getDiffuseLight() {
        return diffuseLightBuffer;
    }

    public FloatBuffer getSpecularLight() {
        return specularLightBuffer;
    }

    private void initLightArrays() {
        lightPositionBuffer = BufferUtils.createFloatBuffer(4);
        
        lightPositionBuffer.put(pos.x).put(pos.y).put(pos.z).put(1.0f).flip();
        ambientLightBuffer = BufferUtils.createFloatBuffer(4);
        ambientLightBuffer.put(ambient.x).put(ambient.y).put(ambient.z).put(0.0f).flip();
        diffuseLightBuffer = BufferUtils.createFloatBuffer(4);
        diffuseLightBuffer.put(diffuse.x).put(diffuse.y).put(diffuse.z).put(0f).flip();
        specularLightBuffer = BufferUtils.createFloatBuffer(4);
        specularLightBuffer.put(specular.x).put(specular.y).put(specular.z).put(0f).flip();
        orbitPoint = new Vector3f(Chunk.getCHUNKSIZE() * FinalProject.getSIZE() /2, Chunk.getCHUNKSIZE()/2,Chunk.getCHUNKSIZE() * FinalProject.getSIZE()/2);
        orbitRadius = FinalProject.getSIZE() * Chunk.getCHUNKSIZE()/2 + Chunk.getCHUNKSIZE()*2;
        orbitAngle = 45;
        lightDirection = BufferUtils.createFloatBuffer(4);
        lightDirection.put(orbitPoint.x).put(orbitPoint.y).put(orbitPoint.z).put(1f).flip();
   }

    public FloatBuffer getLightDirection() {
        return lightDirection;
    }

    public void setPos(Vector3f pos) {
        
        lightPositionBuffer = BufferUtils.createFloatBuffer(4);
        lightPositionBuffer.put(pos.x).put(pos.y).put(pos.z).put(1.0f).flip();
    }
    public float getX(){
        return pos.x;
    }
    public float getY(){
        return pos.y;
    }
    public float getZ(){
        return pos.z;
    }

    public Vector3f getOrbitPoint() {
        return orbitPoint;
    }

    public float getOrbitRadius() {
        return orbitRadius;
    }

    public double getOrbitAngle() {
        return orbitAngle;
  }
}
