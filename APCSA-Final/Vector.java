public class Vector
{
    double x = 0, y = 0, z = 0;
    
    public Vector(double x, double y, double z)
    {
        //Get the length of the vector 
        double length = Math.sqrt(x*x + y*y + z*z);
        
        if(length > 0)  //Normalize the values to be 1 if length is > 0
        {
            this.x = x/length;
            this.y = y/length;
            this.z = z/length;
        }
    }
    
    Vector crossProduct(Vector vector)
    {
        Vector crossVector = new Vector(y * vector.z - z * vector.y,
                                        z * vector.x - x * vector.z,
                                        x * vector.y - y * vector.x);
        
        return crossVector;
    }
}
