import java.awt.*;
import javax.swing.*;

public class Main 
{
    static public int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    static public int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    static Window window;                               //Window object
    
    static Screen screen;                               //Screen object
    
    static InputManager input = new InputManager();     //Input manager object
    
    public static void main()
    {
         screen = new Screen(input);         //Create screen and add the input manager to it
         window = new Window(screen);        //Create window and add the screen to it
    }
}