public class Calculator
{
    static double drawX = 0, drawY = 0;
    
    static double calculatePositionX(double[] viewFrom, double[] viewTo, double x, double y, double z)
    {
        setStuff(viewFrom, viewTo, x, y, z);
        return drawX;
    }
    
    static double calculatePositionY(double[] viewFrom, double[] viewTo, double x, double y, double z)
    {
        setStuff(viewFrom, viewTo, x, y, z);
        return drawY;
    }
    
    static void setStuff(double[] viewFrom, double[] viewTo, double x, double y, double z)
{
    Vector viewVector = new Vector(viewTo[0] - viewFrom[0], viewTo[1] - viewFrom[1], viewTo[2] - viewFrom[2]);
    
    Vector directionVector = new Vector(1, 1, 1);
    
    Vector planeVector1 = viewVector.crossProduct(directionVector);
    Vector planeVector2 = viewVector.crossProduct(planeVector1);
    
    Vector rotationVector = getRotationVector(viewFrom, viewTo);
    Vector weirdVector1 = viewVector.crossProduct(rotationVector);
    Vector weirdVector2 = viewVector.crossProduct(weirdVector1);
    
    Vector viewToPoint = new Vector(x - viewFrom[0], y - viewFrom[1], z - viewFrom[2]);
    
    // Avoid division by zero
    double denominator = viewVector.x * viewToPoint.x + viewVector.y * viewToPoint.y + viewVector.z * viewToPoint.z;
    double t;
    if (Math.abs(denominator) < 1e-6) {
        t = -1; // Point is on the camera plane or invalid, skip rendering
    } else {
        t = (viewVector.x * viewTo[0] + viewVector.y * viewTo[1] + viewVector.z * viewTo[2]
           - (viewVector.x * viewFrom[0] + viewVector.y * viewFrom[1] + viewVector.z * viewFrom[2]))
           / denominator;
    }
    
    x = viewFrom[0] + viewToPoint.x * t;
    y = viewFrom[1] + viewToPoint.y * t;
    z = viewFrom[2] + viewToPoint.z * t;
    
    if (t >= 0) {
        drawX = weirdVector2.x * x + weirdVector2.y * y + weirdVector2.z * z;
        drawY = weirdVector1.x * x + weirdVector1.y * y + weirdVector1.z * z;
    } else {
        drawX = 0;
        drawY = 0; // Ensure points behind camera are not drawn
    }
}
    
    static Vector getRotationVector(double[] viewFrom, double[] viewTo)
    {
        double dx = Math.abs(viewFrom[0]-viewTo[0]);
        double dy = Math.abs(viewFrom[1]-viewTo[1]);
        double xRot, yRot;
        
        xRot = dy/(dx + dy);
        yRot = dx/(dx + dy);
        
        if(viewFrom[1] > viewTo[1])
        {
            xRot = -xRot;
        }
        
        if(viewFrom[0] < viewTo[0])
        {
            yRot = -yRot;
        }
        
        Vector v = new Vector(xRot, yRot, 0);
        
        return v;
    }
}
