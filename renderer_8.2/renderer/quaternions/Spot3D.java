/**
 *A parody of the Point3D class. 
 *Represents both a point in 3D space and a 3D vector.
 */
package renderer.quaternions;
public class Spot3D {
  
  private double x;
  private double y;
  private double z;
  
  //Spot at the origin
  public Spot3D()
  {
    x = 0;
    y = 0;
    z = 0;
  }
  
  //Spot at a specified location
  public Spot3D(double newX, double newY, double newZ)
  {
    x = newX;
    y = newY;
    z = newZ;
  }
  
  //Cross-product of the vectors two Spots represent
  public static Spot3D crossProduct(Spot3D a, Spot3D b)
  {
    return new Spot3D(a.y * b.z - a.z * b.y,
                      a.z * b.x - a.x * b.z,
                      a.x * b.y - a.y * b.x);
  }
  
  //Differences of each coordinate between a and b
  public static Spot3D difference(Spot3D a, Spot3D b)
  {
    return new Spot3D(b.x - a.x,
                      b.y - a.y,
                      b.z - a.z);
  }
  
  //Set all 3 coordinates.
  public void set(double newX, double newY, double newZ)
  {
    x = newX;
    y = newY;
    z = newZ;
  }
  
  //Get distance from the origin, or magnitude.
  public double getDistance()
  {
    return Math.sqrt(x*x + y*y + z*z);
  }
  
  //Get distance from the origin, or magnitude.
  public static double dotProduct(Spot3D a, Spot3D b)
  {
    return a.x * b.x + a.y * b.y + a.z * b.z;
  }
  
  //Provide the angle, in radiance, between the vectors two Spot3Ds represent.
  public static double angleBetween(Spot3D a, Spot3D b)
  {
    double cosAngle = dotProduct(a,b)/(a.getDistance() * b.getDistance());
    return Math.acos(cosAngle);
  }
  
  //Normalize the vector the Spot represents.
  public void normalize()
  {
    double distance = getDistance();
    x /= distance;
    y /= distance;
    z /= distance;
  }
  
  //Set X only
  public void setX(double newX)
  {
    x = newX;
  }
  
  //Set Y only
  public void setY(double newY)
  {
    y = newY;
  }
  
  //Set Z only
  public void setZ(double newZ)
  {
    z = newZ;
  }
  
  //Get X
  public double getX() {return x;}
  
  //Get Y
  public double getY() {return y;}
    
  //Get Z
  public double getZ() {return z;}
  
  //String representation
  public String toString()
  {
    return "(" + x + ", " + y + ", " + z + ")";
  }
  
}
