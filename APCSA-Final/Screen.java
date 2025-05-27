import java.awt.*;
import javax.swing.*;

public class Screen extends JPanel
{
    static double[] viewFrom = new double[]{10, 10, 10};   //Cam pos
    static double[] viweTo = new double[] {5, 0, 0};       //Cam rot
    
    static int numberOfPolygons = 0;                       //Number of objects in scene
    static PolygonObject[] drawablePolygons = new PolygonObject[100];  //Objects in scene
    
    DPolygon DPoly1 = new DPolygon(new double[]{2, 4, 2}, new double[]{2, 4, 6}, new double[]{5, 5, 5}, Color.black);

    //Where objects on screen are initilized
    //Called in main method when Screen object is created
    public Screen()
    {
        
    }

    //Where objects are drawn
    public void paintComponent(Graphics graphics)
    {
        for(int i = 0; i < numberOfPolygons; i++)
        {
            drawablePolygons[i].drawPolygon(graphics);
        }
    }
}