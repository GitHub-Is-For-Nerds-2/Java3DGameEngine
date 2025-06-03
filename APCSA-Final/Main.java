import InputSystem.*;
import RenderEngine.*;

public class Main 
{
    static int width = 1920;
    static int height = 1080;
    
    static RenderEngine render;                         //Window object
    
    static InputSystem input = new InputSystem();     //Input manager object
    
    public static void main()
    {
         render = new RenderEngine(width, height, input);          //Create window and add the screen to it
    }
}