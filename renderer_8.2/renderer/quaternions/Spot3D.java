/**
 *A parody of the Point3D class. 
 *Represents both a point in 3D space and a 3D vector.
 */
package renderer.quaternions;
public class Spot3D {
  
  /* ADD YOUR CODE HERE */
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
  
  public Spot3D(double newX, double newY, double newZ)
  {
    x = newX;
    y = newY;
    z = newZ;
  }
  
  public Spot3D crossProduct(Spot3D a, Spot3D b)
  {
    return new Spot3D(a.y * b.z - a.z * b.y,
                      a.z * b.x - a.x * b.z,
                      a.x * b.y - a.y * b.x);
  }
  
  public void set(double newX, double newY, double newZ)
  {
    x = newX;
    y = newY;
    z = newZ;
  }
  
  public double getDistance()
  {
    return Math.sqrt(x*x + y*y + z*z);
  }
  
  public void normalize()
  {
    double distance = getDistance();
    x /= distance;
    y /= distance;
    z /= distance;
  }
  
  public void setX(double newX)
  {
    x = newX;
  }
  
  public void setY(double newY)
  {
    y = newY;
  }
  
  public void setZ(double newZ)
  {
    z = newZ;
  }
  
}
