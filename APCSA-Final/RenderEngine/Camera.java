package RenderEngine;

import Math.*;
import InputSystem.*;

public class Camera
{   
    static double[] camPos = new double[]{10, 10, 10};  //Camera position
    static double[] camRot = new double[]{0, 0, 0};     //Camera rotation
                                                        //Array element: 
                                                        //[0] = x (Left/Right)
                                                        //[1] = y (Forward/Back)
                                                        //[3] = z (Up/Down)
    
    InputSystem inputManager;
    
    public Camera(InputSystem inputs)
    {
        this.inputManager = inputs;
    }
    
    void control()
    {
        //Find the vector of the camera location
        Vector viewVector = new Vector(camRot[0] - camPos[0], camRot[1] - camPos[1], camRot[2] - camPos[2]);
    
        //Up (W)
        if(inputManager.key[4])
        {
            camPos[0] += viewVector.x;
            camPos[1] += viewVector.y;
            camPos[2] += viewVector.z;
        
            camRot[0] += viewVector.x;
            camRot[1] += viewVector.y;
            camRot[2] += viewVector.z;
        }
        
        //Down (S)
        if(inputManager.key[6])
        {
            camPos[0] -= viewVector.x;
            camPos[1] -= viewVector.y;
            camPos[2] -= viewVector.z;
        
            camRot[0] -= viewVector.x;
            camRot[1] -= viewVector.y;
            camRot[2] -= viewVector.z;
        }   
    
        Vector verticalVector = new Vector(0, 0, 1);                        //The global up vector
        Vector sideViewVector = viewVector.crossProduct(verticalVector);    //The clobal horizontal vector
    
        //Left (A)
        if(inputManager.key[5])
        {
            camPos[0] += sideViewVector.x;
            camPos[1] += sideViewVector.y;
            camPos[2] += sideViewVector.z;
        
            camRot[0] += sideViewVector.x;
            camRot[1] += sideViewVector.y;
            camRot[2] += sideViewVector.z;
        }
    
        //Right (D)
        if(inputManager.key[7])
        {
            camPos[0] -= sideViewVector.x;
            camPos[1] -= sideViewVector.y;
            camPos[2] -= sideViewVector.z;
        
            camRot[0] -= sideViewVector.x;
            camRot[1] -= sideViewVector.y;
            camRot[2] -= sideViewVector.z;
        }

        ///////////////DANGER///////////////
        //The unholy wall of text beyond this point was created with the help
        //of my friend and several internet sources because I could not figure
        //out how to make the camera turn based on player input. I have yet to
        //figure out what any of it means.
        
        // Camera rotation with arrow keys
        double rotationSpeed = 0.05; // Radians per frame
        double dist = Math.sqrt(
            (camRot[0] - camPos[0]) * (camRot[0] - camPos[0]) +
            (camRot[1] - camPos[1]) * (camRot[1] - camPos[1]) +
            (camRot[2] - camPos[2]) * (camRot[2] - camPos[2])
        );

        if(inputManager.key[0] || inputManager.key[1]) // Left/Right arrow (yaw)
        {
            double yaw = inputManager.key[0] ? -rotationSpeed : rotationSpeed; // Left: negative, Right: positive
            // Rotate around global Z-axis
            double dx = camRot[0] - camPos[0];
            double dy = camRot[1] - camPos[1];
            // Apply 2D rotation in XY plane
            camRot[0] = camPos[0] + dx * Math.cos(yaw) - dy * Math.sin(yaw);
            camRot[1] = camPos[1] + dx * Math.sin(yaw) + dy * Math.cos(yaw);
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
            double dx = camRot[0] - camPos[0];
            double dy = camRot[1] - camPos[1];
            double dz = camRot[2] - camPos[2];
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
            camRot[0] = camPos[0] + newDx;
            camRot[1] = camPos[1] + newDy;
            camRot[2] = camPos[2] + newDz;
            // Clamp pitch to prevent flipping
            if (Math.abs(camRot[2] - camPos[2]) > dist * 0.99) {
                camRot[2] = camPos[2] + (camRot[2] > camPos[2] ? dist * 0.99 : -dist * 0.99);
                // Adjust X and Y to maintain distance
                double remainingDist = Math.sqrt(dist * dist - (camRot[2] - camPos[2]) * (camRot[2] - camPos[2]));
                double oldAngle = Math.atan2(dy, dx);
                camRot[0] = camPos[0] + remainingDist * Math.cos(oldAngle);
                camRot[1] = camPos[1] + remainingDist * Math.sin(oldAngle);
            }
        }
    }
}
