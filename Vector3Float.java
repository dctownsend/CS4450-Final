/*
 * file: FinalProject.java
 * author: Sebastian, Diane, Nghi, Dakota
 * group: Synchronized Broncos
 * class: CS4450 - Computer Graphic
 *
 * assignment: Semester Project
 * date last modified: 3/28/2019
 *
 * purpose: This is a vector class contain variable of x,y, and z
 */

public class Vector3Float {

    float x;
    float y;
    float z;
    
    public Vector3Float(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public float getX() {
        return x;
    }
    
    public void setX(float x) {
        this.x = x;
    }
    
    public float getY() {
        return y;
    }
    
    public void setY(float y) {
        this.y = y;
    }
    
    public float getZ() {
        return z;
    }
    
    public void setZ(float z) {
        this.z = z;
    }
    
}