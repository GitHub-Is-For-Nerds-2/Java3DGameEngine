package InputSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InputManager extends JPanel implements KeyListener
{
    public boolean[] key = new boolean[10];
    
    public InputManager()
    {
        
    }
    
    //Override of the KeyListener implementation
    public void keyPressed(KeyEvent keyEvent)
    {
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
        {
            key[0] = true;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            key[1] = true;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_UP)
        {
            key[2] = true;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
        {
            key[3] = true;
        }
        
        if(keyEvent.getKeyCode() == KeyEvent.VK_W)
        {
            key[4] = true;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_A)
        {
            key[5] = true;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_S)
        {
            key[6] = true;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_D)
        {
            key[7] = true;
        }
    }
    
    //Override of the KeyListener implementation
    public void keyReleased(KeyEvent keyEvent)
    {
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
        {
            key[0] = false;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            key[1] = false;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_UP)
        {
            key[2] = false;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
        {
            key[3] = false;
        }
        
        if(keyEvent.getKeyCode() == KeyEvent.VK_W)
        {
            key[4] = false;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_A)
        {
            key[5] = false;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_S)
        {
            key[6] = false;
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_D)
        {
            key[7] = false;
        }
    }
    
    //Override of the KeyListener implementation
    public void keyTyped(KeyEvent keyEvent)
    {

    }
}
