import java.awt.*;

public class DPolygon
{
    double[] x, y, z;

    Color color;

    int poly = 0;   //The polygon number

    public DPolygon(double[] x, double[] y, double[] z, Color color)
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
        poly = Screen.numberOfPolygons;

        Screen.drawablePolygons[Screen.numberOfPolygons] = new PolygonObject(new double[]{}, new double[]{}, color);
        
        updatePolygon();
    }

    void updatePolygon()
    {
        //Offset to center
        double dx = - 50 * Calculator.calculatePositionX(Screen.viewFrom, Screen.viweTo, Screen.viweTo[0], Screen.viweTo[1], Screen.viweTo[2]);
        double dy = - 50 * Calculator.calculatePositionY(Screen.viewFrom, Screen.viweTo, Screen.viweTo[0], Screen.viweTo[1], Screen.viweTo[2]);
        
        //Loop through the x values and get the screen pos of them related to the world pos
        double[] newX = new double[x.length];
        double[] newY = new double[x.length];
        for(int i = 0; i < x.length; i++)
        {
            newX[i] = dx + Main.SCREEN_WIDTH/2 + 50 * Calculator.calculatePositionX(Screen.viewFrom, Screen.viweTo, x[i], y[i], z[i]);
            newY[i] = dy + Main.SCREEN_HEIGHT/2 + 50 * Calculator.calculatePositionY(Screen.viewFrom, Screen.viweTo, x[i], y[i], z[i]);
        }

        Screen.drawablePolygons[poly] = new PolygonObject(newX, newY, color);
        Screen.drawablePolygons[poly].aveDist = getDist();
        Screen.numberOfPolygons --;     //Make sure it doesn't add a polygon when the object is redrawn
    }
    
    double getDist()
    {
        double total = 0;
        for(int i = 0; i < x.length; i++)
        {
            total += getDistanceToP(i);
        }
        
        return total / x.length; 
    }
    
    double getDistanceToP(int i)
    {
        return Math.sqrt((Screen.viewFrom[0] - x[i])*(Screen.viewFrom[0] - x[i]) 
                       + (Screen.viewFrom[1] - y[i])*(Screen.viewFrom[1] - y[i])
                       + (Screen.viewFrom[2] - z[i])*(Screen.viewFrom[2] - z[i]));
    }
}