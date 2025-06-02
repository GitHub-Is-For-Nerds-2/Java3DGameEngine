import InputSystem.*;
import RenderEngine.*;

public class Main 
{
    static public int SCREEN_WIDTH = 1920;
    static public int SCREEN_HEIGHT = 1080;
    
    static Window window;                               //Window object
    
    static Screen screen;                               //Screen object
    
    static InputManager input = new InputManager();     //Input manager object
    
    public static void main()
    {
         screen = new Screen(input);                                        //Create screen and add the input manager to it
         window = new Window(SCREEN_WIDTH, SCREEN_HEIGHT, screen);          //Create window and add the screen to it
    }
}