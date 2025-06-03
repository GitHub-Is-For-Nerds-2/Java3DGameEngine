import InputSystem.*;
import RenderEngine.*;

public class Main 
{
    static int width = 1920;
    static int height = 1080;
    
    static Window window;                               //Window object
    
    static InputManager input = new InputManager();     //Input manager object
    
    public static void main()
    {
         window = new Window(width, height, input);          //Create window and add the screen to it
    }
}