package RenderEngine;

import java.awt.*;
import javax.swing.*;

import InputSystem.*;

public class RenderEngine extends JFrame
{   
    public static int screenWidth;
    public static int screenHeight;
    
    Screen screen;
    
    public RenderEngine(int width, int height, InputSystem input)
    {
        screenWidth = width;            //Initilize package variables
        screenHeight = height;

        createWindow();                 //Create window
        
        screen = new Screen(input);     //Create screen and send inputs for creation

        add(screen);                    //Add screen to window

        setVisible(true);               //Make it visible 
    }
    
    public void createWindow()
    {
        setTitle("VantaEngine");                        //Set window title
        setSize(screenWidth, screenHeight);             //Set window size
        setLocationRelativeTo(null);                    //Set window location (null is center of screen)
        setResizable(false);                            //Resizable is false because of static srceen size on the frame 
                                                        //redraw (painComponent() clearRect())
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //App close when 'x' is hit
    }
}
