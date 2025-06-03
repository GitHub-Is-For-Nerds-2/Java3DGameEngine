package RenderEngine;

//Java dependencies
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//My package dependencies
import Math.*;
import InputSystem.*;

public class Screen extends JPanel
{
    double fpsCap = 1000/30, deltaTime = 0;                                 //FPS Cap (30), time since last frame

    static int numberOfPolygons = 0, numberOf3DPolygons = 0;                //Number of objects in scene
    
    
    static PolygonObject[] drawablePolygons = new PolygonObject[100];       //Objects in scene
    static Polygon3D[] DPolygons = new Polygon3D[100];                
    
    int[] newOrder;                                                         //Order of objects in the scene relative
                                                                            //to camera view (furthest to closest)
    
    Camera camera;                                                          //Camera

    public Screen(InputSystem inputManager)
    {
        //Generate cube
        DPolygons[numberOf3DPolygons] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2}, new double[]{0, 0, 0, 0}, Color.gray);
        DPolygons[numberOf3DPolygons] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2}, new double[]{3, 3, 3, 3}, Color.gray);
        DPolygons[numberOf3DPolygons] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 0, 0}, new double[]{0, 0, 3, 3}, Color.gray);
        DPolygons[numberOf3DPolygons] = new Polygon3D(new double[]{0, 2, 2, 0}, new double[]{2, 2, 2, 2}, new double[]{0, 0, 3, 3}, Color.gray);
        DPolygons[numberOf3DPolygons] = new Polygon3D(new double[]{0, 0, 0, 0}, new double[]{0, 2, 2, 0}, new double[]{0, 0, 3, 3}, Color.gray);
        DPolygons[numberOf3DPolygons] = new Polygon3D(new double[]{2, 2, 2, 2}, new double[]{0, 2, 2, 0}, new double[]{0, 0, 3, 3}, Color.gray);
        
        //Generate Floor
        for(int i = -4; i < 5; i++)
        {
            for(int j = -4; j < 5; j++)
            {
                DPolygons[numberOf3DPolygons] = new Polygon3D(new double[]{i, i, i + 1, i + 1}, new double[]{j, j + 1, j + 1, j}, new double[]{0, 0, 0, 0}, Color.green);
            }
        }
        
        addKeyListener(inputManager);       //Add input listener to the window
        
        camera = new Camera(inputManager);  //Send inputs to camera
        
        setFocusable(true);                 //Make the window be the main window
    }

    //Where objects are drawn. Called every frame when repaint() is called in void update()
    public void paintComponent(Graphics graphics)
    {
        camera.control();                                                                       //Check for camera input to move
        
        graphics.clearRect(0, 0, RenderEngine.screenWidth, RenderEngine.screenHeight);          //Clear screen for redraw (x, y, screen size x, screen size y)

        graphics.drawString(System.currentTimeMillis() + "", 20, 20);                           //Draw system time in top right for debug that screen is redrawing

        //Update the polygons (explained further in script)
        for(int i = 0; i < numberOf3DPolygons; i++)
        {
            DPolygons[i].updatePolygon();
        }
        
        setOrder();         //Set the order of the polygons

        //Draw all the polygons in the scene
        for(int i = 0; i < numberOfPolygons; i++)
        {
            drawablePolygons[newOrder[i]].drawPolygon(graphics);
        }

        sleep();           //Wait for next frame to update
    }
    
    //Very basic sorting algorithm to find the order of polygons in the scene from furthest
    //from the camera to closest
    void setOrder()
    {
        double[] k = new double[numberOfPolygons];
        newOrder = new int[numberOfPolygons];
        
        for(int i = 0; i < numberOfPolygons; i++)
        {
            k[i] = drawablePolygons[i].aveDist;
            newOrder[i] = i;
        }
        
        double temp;
        int tempr;
        for(int a = 0; a < k.length - 1; a++)
        {
            for(int b = 0; b < k.length - 1; b++)
            {
                if(k[b] < k[b + 1])
                {
                    temp = k[b];
                    tempr = newOrder[b];
                    newOrder[b] = newOrder[b + 1];
                    k[b] = k[b + 1];
                    
                    newOrder[b + 1] = tempr;
                    k[b + 1] = temp;
                }
            }
        }
    }

    //Sleep till next screen
    void sleep()
    {
        while(true)
        {
            //Calculate delta time based on current system time compared to last frame time. If it is larger than
            //the FPS cap then run
            if((System.currentTimeMillis() - deltaTime) > fpsCap)
            {
                deltaTime = System.currentTimeMillis();     //Update delta time
                repaint();                                  //Refresh screen (call paintComponent() again)
                break;                                      //Get out of loop
            }
            else
            {
                //This is a try catch. It basically is a built in error check that if the code it trys to run fails, it will catch the 
                //error and run the code in the catch instead of crashing
                try
                {
                    Thread.sleep((long)(System.currentTimeMillis() - deltaTime));           //Try to sleep the program till next frame
                }
                catch(Exception e)
                {

                }
            }
        }
    }
}