package RenderEngine;

import java.awt.*;

public class Polygon3D
{
    double[] x, y, z;   //Position of polygon 

    Color color;        //Color

    int polygonNumber = 0;       //The polygon identity number

    public Polygon3D(double[] x, double[] y, double[] z, Color color)
    {
        Screen.numberOf3DPolygons++;
        this.x = x;
        this.y = y;
        this.z = z;

        this.color = color;

        createPolygon();
    }

    void createPolygon()
    {
        polygonNumber = Screen.numberOfPolygons;

        Screen.drawablePolygons[Screen.numberOfPolygons] = new PolygonObject(new double[]{}, new double[]{}, color);
        
        updatePolygon();
    }

    void updatePolygon()
    {
        //Offset to center. The -50 multiplier ajust for weird geomatry scaling based on screen location and normalizes it
        double dx = - 50 * Calculator.calculatePositionX(Camera.camPos, Camera.camRot, Camera.camRot[0], Camera.camRot[1], Camera.camRot[2]);
        double dy = - 50 * Calculator.calculatePositionY(Camera.camPos, Camera.camRot, Camera.camRot[0], Camera.camRot[1], Camera.camRot[2]);
        
        //Loop through the x values and get the screen pos of them related to the world pos
        double[] newX = new double[x.length];
        double[] newY = new double[x.length];
        for(int i = 0; i < x.length; i++)
        {
            newX[i] = dx + RenderEngine.screenWidth/2 + 50 * Calculator.calculatePositionX(Camera.camPos, Camera.camRot, x[i], y[i], z[i]);
            newY[i] = dy + RenderEngine.screenHeight/2 + 50 * Calculator.calculatePositionY(Camera.camPos, Camera.camRot, x[i], y[i], z[i]);
        }

        //Replace polygon with new polygon with updated position
        Screen.drawablePolygons[polygonNumber] = new PolygonObject(newX, newY, color);
        Screen.drawablePolygons[polygonNumber].aveDist = getDist();
        Screen.numberOfPolygons --;     //Make sure it doesn't add a polygon when the object is redrawn
    }
    
    //Get the average distance between the polygon position and camera position
    double getDist()
    {
        double total = 0;
        for(int i = 0; i < x.length; i++)
        {
            total += Math.sqrt((Camera.camPos[0] - x[i])*(Camera.camPos[0] - x[i]) 
                       + (Camera.camPos[1] - y[i])*(Camera.camPos[1] - y[i])
                       + (Camera.camPos[2] - z[i])*(Camera.camPos[2] - z[i]));
        }
        
        return total / x.length; 
    }
}
