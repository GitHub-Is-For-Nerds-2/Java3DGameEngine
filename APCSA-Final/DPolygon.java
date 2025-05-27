import java.awt.*;

public class DPolygon
{   
    double[] x, y, z;
    
    Color color;
    
    public DPolygon(double[] x, double[] y, double[] z, Color color)
    {
        this.x = x;
        this.y = y;
        this.z = z;

        color = color;
        
        createPolygon();
    }
    
    void createPolygon()
    {
        //Loop through the x values and get the screen pos of them related to the world pos
        double[] newX = new double[x.length];
        double[] newY = new double[x.length];
        for(int i = 0; i < x.length; i++)
        {
            newX[i] = 200 * Calculator.calculatePositionX(Screen.viewFrom, Screen.viweTo, x[i], y[i], z[i]);
            newY[i] = 200 * Calculator.calculatePositionY(Screen.viewFrom, Screen.viweTo, x[i], y[i], z[i]);
        }
        
        Screen.drawablePolygons[Screen.numberOfPolygons] = new PolygonObject(newX, newY, color);
    }
}
