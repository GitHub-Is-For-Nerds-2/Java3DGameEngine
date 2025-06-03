package InputSystem;        //The package location of the script so java knows its location within the file

//Java library dependencies
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Input system I created to listen for key presses from the user and keep track of which ones are active and 
//which ones arent. The implementation of the java KeyListener allows for this and all the methods in this 
//class are overrides of methods inherited from that.
public class InputSystem implements KeyListener
{
    public boolean[] key = new boolean[10];     //Array of which keys are active
    
    //Constructor to allow instances of the key listener to be created 
    //as an object with no static references
    public InputSystem()
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
