import java.awt.*;
import javax.swing.*;

public class Window extends JFrame
{   
    public Window(Screen screen)
    {
        setTitle("VantaEngine");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);                            //Resizable is false because of static srceen size on the frame 
                                                        //redraw (painComponent() clearRect())
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(screen);                                    //Add screen to window

        setVisible(true);
    }
}
