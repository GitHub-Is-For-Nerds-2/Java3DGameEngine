import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Screen extends JPanel
{
    double sleepTime = 1000/30, lastRefresh = 0; //FPS Cap (30), last frame

    static double[] viewFrom = new double[]{10, 10, 10};   //Cam pos
    static double[] viweTo = new double[] {1, 1, 1.5};       //Cam rot

    static int numberOfPolygons = 0, numberOf3DPolygons = 0;               //Number of objects in scene
    static PolygonObject[] drawablePolygons = new PolygonObject[100];      //Objects in scene

    static DPolygon[] DPolygons = new DPolygon[100];
    
    int[] newOrder;
    
    InputManager inputManager;

    //Where objects on screen are initilized
    //Called in main method when Screen object is created
    public Screen(InputManager inputManager)
    {
        //Generate cube
        DPolygons[numberOf3DPolygons] = new DPolygon(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2}, new double[]{0, 0, 0, 0}, Color.gray);
        DPolygons[numberOf3DPolygons] = new DPolygon(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2}, new double[]{3, 3, 3, 3}, Color.gray);
        DPolygons[numberOf3DPolygons] = new DPolygon(new double[]{0, 2, 2, 0}, new double[]{0, 0, 0, 0}, new double[]{0, 0, 3, 3}, Color.gray);
        DPolygons[numberOf3DPolygons] = new DPolygon(new double[]{0, 2, 2, 0}, new double[]{2, 2, 2, 2}, new double[]{0, 0, 3, 3}, Color.gray);
        DPolygons[numberOf3DPolygons] = new DPolygon(new double[]{0, 0, 0, 0}, new double[]{0, 2, 2, 0}, new double[]{0, 0, 3, 3}, Color.gray);
        DPolygons[numberOf3DPolygons] = new DPolygon(new double[]{2, 2, 2, 2}, new double[]{0, 2, 2, 0}, new double[]{0, 0, 3, 3}, Color.gray);
        
        //Generate Floor
        for(int i = -4; i < 5; i++)
        {
            for(int j = -4; j < 5; j++)
            {
                DPolygons[numberOf3DPolygons] = new DPolygon(new double[]{i, i, i + 1, i + 1}, new double[]{j, j + 1, j + 1, j}, new double[]{0, 0, 0, 0}, Color.green);
            }
        }
        
        addKeyListener(inputManager);   //Add the key listener to this instance
        
        this.inputManager = inputManager;
        
        setFocusable(true);     //Make the window be the main window
    }

    //Where objects are drawn
    public void paintComponent(Graphics graphics)
    {
        control();
        
        graphics.clearRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);         //Clear screen for redraw (x, y, screen size x, screen size y)

        graphics.drawString(System.currentTimeMillis() + "", 20, 20);

        for(int i = 0; i < numberOf3DPolygons; i++)
        {
            DPolygons[i].updatePolygon();
        }
        
        setOrder();

        for(int i = 0; i < numberOfPolygons; i++)
        {
            drawablePolygons[newOrder[i]].drawPolygon(graphics);
        }

        sleepAndRefresh();  //Update
    }
    
    void setOrder()
    {
        double[] k = new double[numberOfPolygons];
        newOrder = new int[numberOfPolygons];
        
        for(int i = 0; i < numberOfPolygons; i++)
        {
            k[i] = drawablePolygons[i].aveDist;
            newOrder[i] = i;
        }
        
        double temp;
        int tempr;
        for(int a = 0; a < k.length - 1; a++)
        {
            for(int b = 0; b < k.length - 1; b++)
            {
                if(k[b] < k[b + 1])
                {
                    temp = k[b];
                    tempr = newOrder[b];
                    newOrder[b] = newOrder[b + 1];
                    k[b] = k[b + 1];
                    
                    newOrder[b + 1] = tempr;
                    k[b + 1] = temp;
                }
            }
        }
    }

    //Update screen
    void sleepAndRefresh()
    {
        while(true)
        {
            //Calculate delta time based on current system time compared to last frame time. If it is larger than
            //the FPS cap then run
            if((System.currentTimeMillis() - lastRefresh) > sleepTime)
            {
                lastRefresh = System.currentTimeMillis();
                repaint();                                  //Refresh screen (call paintComponent() again)
                break;                                      //Get out of loop
            }
            else
            {
                //This is a try catch. It basically is a built in error check that if the code it trys to run fails, it will catch the 
                //error and run the code in the catch instead of crashing
                try
                {
                    Thread.sleep((long)(System.currentTimeMillis() - lastRefresh));           //Try to sleep the program till next frame
                }
                catch(Exception e)
                {

                }
            }
        }
    }
    
    void control()
{
    Vector viewVector = new Vector(viweTo[0] - viewFrom[0], viweTo[1] - viewFrom[1], viweTo[2] - viewFrom[2]);
    
    // Up/Down movement (W/S)
    if(inputManager.key[4]) // W
    {
        viewFrom[0] += viewVector.x;
        viewFrom[1] += viewVector.y;
        viewFrom[2] += viewVector.z;
        
        viweTo[0] += viewVector.x;
        viweTo[1] += viewVector.y;
        viweTo[2] += viewVector.z;
    }
    
    if(inputManager.key[6]) // S
    {
        viewFrom[0] -= viewVector.x;
        viewFrom[1] -= viewVector.y;
        viewFrom[2] -= viewVector.z;
        
        viweTo[0] -= viewVector.x;
        viweTo[1] -= viewVector.y;
        viweTo[2] -= viewVector.z;
    }
    
    // Left/Right movement (A/D)
    Vector verticalVector = new Vector(0, 0, 1);
    Vector sideViewVector = viewVector.crossProduct(verticalVector);
    
    if(inputManager.key[5]) // A
    {
        viewFrom[0] += sideViewVector.x;
        viewFrom[1] += sideViewVector.y;
        viewFrom[2] += sideViewVector.z;
        
        viweTo[0] += sideViewVector.x;
        viweTo[1] += sideViewVector.y;
        viweTo[2] += sideViewVector.z;
    }
    
    if(inputManager.key[7]) // D
    {
        viewFrom[0] -= sideViewVector.x;
        viewFrom[1] -= sideViewVector.y;
        viewFrom[2] -= sideViewVector.z;
        
        viweTo[0] -= sideViewVector.x;
        viweTo[1] -= sideViewVector.y;
        viweTo[2] -= sideViewVector.z;
    }

    // Camera rotation with arrow keys
    double rotationSpeed = 0.05; // Radians per frame
    double dist = Math.sqrt(
        (viweTo[0] - viewFrom[0]) * (viweTo[0] - viewFrom[0]) +
        (viweTo[1] - viewFrom[1]) * (viweTo[1] - viewFrom[1]) +
        (viweTo[2] - viewFrom[2]) * (viweTo[2] - viewFrom[2])
    );

    if(inputManager.key[0] || inputManager.key[1]) // Left/Right arrow (yaw)
    {
        double yaw = inputManager.key[0] ? -rotationSpeed : rotationSpeed; // Left: negative, Right: positive
        // Rotate around global Z-axis
        double dx = viweTo[0] - viewFrom[0];
        double dy = viweTo[1] - viewFrom[1];
        // Apply 2D rotation in XY plane
        viweTo[0] = viewFrom[0] + dx * Math.cos(yaw) - dy * Math.sin(yaw);
        viweTo[1] = viewFrom[1] + dx * Math.sin(yaw) + dy * Math.cos(yaw);
        // Z remains unchanged for yaw
    }

    if(inputManager.key[2] || inputManager.key[3]) // Up/Down arrow (pitch)
    {
        double pitch = inputManager.key[2] ? -rotationSpeed : rotationSpeed; // Up: negative, Down: positive
        // Compute local right vector (cross product of viewVector and global up)
        Vector upVector = new Vector(0, 0, 1);
        Vector rightVector = viewVector.crossProduct(upVector);
        // Handle degenerate case when viewVector is parallel to upVector
        if (rightVector.x == 0 && rightVector.y == 0 && rightVector.z == 0) {
            rightVector = new Vector(1, 0, 0); // Fallback to X-axis
        }
        // Normalize rightVector
        double rightLength = Math.sqrt(rightVector.x * rightVector.x + rightVector.y * rightVector.y + rightVector.z * rightVector.z);
        if (rightLength > 0) {
            rightVector.x /= rightLength;
            rightVector.y /= rightLength;
            rightVector.z /= rightLength;
        }
        // Rotate viewVector around rightVector using Rodrigues' rotation formula
        double dx = viweTo[0] - viewFrom[0];
        double dy = viweTo[1] - viewFrom[1];
        double dz = viweTo[2] - viewFrom[2];
        double cosTheta = Math.cos(pitch);
        double sinTheta = Math.sin(pitch);
        // Rodrigues' rotation: v' = v * cosθ + (k × v) * sinθ + k * (k · v) * (1 - cosθ)
        double kDotV = rightVector.x * dx + rightVector.y * dy + rightVector.z * dz;
        double crossX = rightVector.y * dz - rightVector.z * dy;
        double crossY = rightVector.z * dx - rightVector.x * dz;
        double crossZ = rightVector.x * dy - rightVector.y * dx;
        double newDx = dx * cosTheta + crossX * sinTheta + rightVector.x * kDotV * (1 - cosTheta);
        double newDy = dy * cosTheta + crossY * sinTheta + rightVector.y * kDotV * (1 - cosTheta);
        double newDz = dz * cosTheta + crossZ * sinTheta + rightVector.z * kDotV * (1 - cosTheta);
        // Update viweTo, preserving distance
        double newDist = Math.sqrt(newDx * newDx + newDy * newDy + newDz * newDz);
        if (newDist > 0) {
            newDx = newDx * dist / newDist;
            newDy = newDy * dist / newDist;
            newDz = newDz * dist / newDist;
        }
        viweTo[0] = viewFrom[0] + newDx;
        viweTo[1] = viewFrom[1] + newDy;
        viweTo[2] = viewFrom[2] + newDz;
        // Clamp pitch to prevent flipping
        if (Math.abs(viweTo[2] - viewFrom[2]) > dist * 0.99) {
            viweTo[2] = viewFrom[2] + (viweTo[2] > viewFrom[2] ? dist * 0.99 : -dist * 0.99);
            // Adjust X and Y to maintain distance
            double remainingDist = Math.sqrt(dist * dist - (viweTo[2] - viewFrom[2]) * (viweTo[2] - viewFrom[2]));
            double oldAngle = Math.atan2(dy, dx);
            viweTo[0] = viewFrom[0] + remainingDist * Math.cos(oldAngle);
            viweTo[1] = viewFrom[1] + remainingDist * Math.sin(oldAngle);
        }
    }
}
}