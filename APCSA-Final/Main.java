//https://www.youtube.com/watch?v=sQmQVowEY7o&list=PLsRmsZm0xMNogPyRn6gNWq4OM5j22FkAU&index=9

import java.awt.*;
import javax.swing.*;

public class Main extends JFrame
{
    static JFrame frame = new Main();
    Screen screen = new Screen();

    public Main()
    {
        //Creating window
        //TODO: Move to own class/method
        setTitle("VantaEngine");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Move to own method because this adds screen to window
        add(screen);

        setVisible(true);
    }

    public static void main()
    {

    }
}