
import javax.swing.*;
import java.awt.*;

public class Window
{
    private static JFrame window = new JFrame(); 
    
    public static void window()
    {
        window.setTitle("VantaEngine");
        window.setSize(1920, 1080);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setVisible(true);
        
        System.out.println("Debug: Window Created Successfully!");
    }
}
