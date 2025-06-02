package RenderEngine;

import java.awt.*;

public class PolygonObject
{
    Polygon polygon;
    Color color;
    
    double aveDist = 0;

    public PolygonObject(double[] x, double[] y, Color color)
    {
        Screen.numberOfPolygons++;  //Increase the scene count in screen class each time object is created
        polygon = new Polygon();

        //Loop through x and y and add them to shape
        for(int i = 0; i < x.length; i++)
        {
            polygon.addPoint((int)x[i], (int)y[i]);
        }

        this.color = color;
    }

    void drawPolygon(Graphics graphics)
    {
        //Color variable isn't working. REMOVE
        graphics.setColor(color);
        graphics.fillPolygon(polygon);
        
        graphics.setColor(Color.black);
        graphics.drawPolygon(polygon);
    }
}