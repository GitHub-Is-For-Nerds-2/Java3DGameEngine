package RenderEngine;

import java.awt.*;
import javax.swing.*;

import InputSystem.*;

public class Window extends JFrame
{   
    public static int screenWidth;
    public static int screenHeight;
    
    Screen screen;
    
    public Window(int width, int height, InputManager input)
    {
        screenWidth = width;
        screenHeight = height;
        
        setTitle("VantaEngine");
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setResizable(false);                            //Resizable is false because of static srceen size on the frame 
                                                        //redraw (painComponent() clearRect())
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        screen = new Screen(input);

        add(screen);                                    //Add screen to window

        setVisible(true);
    }
}
