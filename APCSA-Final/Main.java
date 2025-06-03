//Import my packages for use
import InputSystem.*;
import RenderEngine.*;

public class Main 
{
    //I defined the screen size here so its easy to change
    static int width = 1920;
    static int height = 1080;
    
    static RenderEngine render;                       //Window object
    
    static InputSystem input = new InputSystem();     //Create input system object
    
    public static void main()
    {
         render = new RenderEngine(width, height, input);          //Create window and add the screen to it
    }
}