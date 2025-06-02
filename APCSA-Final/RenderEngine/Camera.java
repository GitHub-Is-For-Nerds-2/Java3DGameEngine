package RenderEngine;

import Math.*;
import InputSystem.*;

public class Camera
{   
    static double[] viewFrom = new double[]{10, 10, 10};        //Cam pos
    static double[] viweTo = new double[] {1, 1, 1.5};          //Cam rot
    
    InputManager inputManager;
    
    public Camera(InputManager inputs)
    {
        this.inputManager = inputs;
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
