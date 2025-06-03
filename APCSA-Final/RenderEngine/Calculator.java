package RenderEngine;

import Math.*;

public class Calculator
{
    //The final position of the polygon in 2D space on the screen
    static double drawX = 0, drawY = 0;
    
    //Get 2D x location from 3D X Y Z location
    static double calculatePositionX(double[] camPos, double[] camRot, double x, double y, double z)
    {
        setStuff(camPos, camRot, x, y, z);
        return drawX;
    }
    
    //Get 2D y location from 3D X Y Z location
    static double calculatePositionY(double[] viewFrom, double[] camRot, double x, double y, double z)
    {
        setStuff(viewFrom, camRot, x, y, z);
        return drawY;
    }
    
    static void setStuff(double[] camPos, double[] camRot, double x, double y, double z)
    {
        //Direction the camera is looking
        Vector viewVector = new Vector(camRot[0] - camPos[0], camRot[1] - camPos[1], camRot[2] - camPos[2]);
    
        //Random direction to get perpendicular vectors used to construct camera plane
        Vector directionVector = new Vector(1, 1, 1);
    
        //Vectors of the camera viewing plane
        Vector planeVector1 = viewVector.crossProduct(directionVector);
        Vector planeVector2 = viewVector.crossProduct(planeVector1);
    
        //Camera rotation
        Vector rotationVector = getRotationVector(camPos, camRot);
        
        //2D plane to cast to 
        Vector weirdVector1 = viewVector.crossProduct(rotationVector);
        Vector weirdVector2 = viewVector.crossProduct(weirdVector1);
    
        //The vector of object relative to camera
        Vector viewToPoint = new Vector(x - camPos[0], y - camPos[1], z - camPos[2]);
    
        //Project the 3D point onto the view vector direction using dot product
        double denominator = viewVector.x * viewToPoint.x + viewVector.y * viewToPoint.y + viewVector.z * viewToPoint.z;
        
        //Distance between the polygon and camera
        double t;
        
        //If the point is too close to the camera, skip rendering it (clippling plane)
        if (Math.abs(denominator) < 1e-2) 
        {
            t = -1;
        } 
        else {
            //Find the distance of the point compared to the camera 
            t = (viewVector.x * camRot[0] + viewVector.y * camRot[1] + viewVector.z * camRot[2]
               - (viewVector.x * camPos[0] + viewVector.y * camPos[1] + viewVector.z * camPos[2]))
               / denominator;
        }
    
        //Offset the polygon on the 2D plane based on the distance
        x = camPos[0] + viewToPoint.x * t;
        y = camPos[1] + viewToPoint.y * t;
        z = camPos[2] + viewToPoint.z * t;
    
        // If point is in front of the camera, compute 2D coordinates
        if (t >= 0) 
        {
            drawX = weirdVector2.x * x + weirdVector2.y * y + weirdVector2.z * z;
            drawY = weirdVector1.x * x + weirdVector1.y * y + weirdVector1.z * z;
        } 
        // Point is behind the camera, set drawX and drawY to zero to ignore it
        else 
        {
            drawX = 0;
            drawY = 0;
        }
    }
    
    //Calculates a rotation vector that helps orient the viewing plane properly.
    //This is a simplified way to determine left/right and up/down directions.
    static Vector getRotationVector(double[] camPos, double[] camRot)
    {
        double dx = Math.abs(camPos[0]-camRot[0]);
        double dy = Math.abs(camPos[1]-camRot[1]);
        double xRot, yRot;
        
        xRot = dy/(dx + dy);
        yRot = dx/(dx + dy);
        
        //Flip directions based on camera orientation
        if(camPos[1] > camRot[1])
        {
            xRot = -xRot;
        }
        
        if(camPos[0] < camRot[0])
        {
            yRot = -yRot;
        }
        
        return new Vector(xRot, yRot, 0);
    }
}
