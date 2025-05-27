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
        
        //Get point we are looking at (trying to calculate)
        Vector viewToPoint = new Vector(x - viewFrom[0], y - viewFrom[1], z - viewFrom[2]);
        
        //Find where vector meets plane
        double t = (viewVector.x*viewTo[0] + viewVector.y*viewTo[1] + viewVector.z*viewTo[2]
                 - (viewVector.x*viewFrom[0] + viewVector.y*viewFrom[1] + viewVector.z*viewFrom[2]))
                 / (viewVector.x*viewToPoint.x + viewVector.y*viewToPoint.y + viewVector.z*viewToPoint.z);
        
        
        x = viewFrom[0] + viewToPoint.x * t;
        y = viewFrom[1] + viewToPoint.y * t;
        z = viewFrom[2] + viewToPoint.z * t;
        
        if(t >= 0)
        {
            drawX = planeVector2.x * x + planeVector2.y * y + planeVector2.z * z;
            drawY = planeVector1.x * x + planeVector1.y * y + planeVector1.z * z;
        }
    }
}
