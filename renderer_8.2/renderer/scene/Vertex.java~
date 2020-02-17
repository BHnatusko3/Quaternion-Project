/*

*/

package renderer.scene;

/**
   A {@code Vertex} object has four doubles which represent the
   homogeneous coordinates of a point in 3-dimensional space.
   The fourth, homogeneous, coordinate will usually be 1, but in
   some stages of the graphics rendering pipeline it can be some
   other (non-zero) number.
<p>
   When a {@code Vertex} object is created in a client program,
   before the {@code Vertex} object moves down the graphics rendering
   pipeline, the coordinates in the {@code Vertex} will be in some
   model's local coordinate system.
<p>
   As a {@code Vertex} object moves down the graphics rendering
   pipeline, the coordinates in the {@code Vertex} will be transformed
   from one coordinate system to another.
*/
public class Vertex
{
   public double x, y, z, w; // a vertex in homogenous coordinates

   /**
      Construct a default {@code Vertex}.
   */
   public Vertex()
   {
      set(0.0, 0.0, 0.0, 1.0);
   }


   /**
      Construct a new {@code Vertex} (with homogeneous coordinates)
      using the given {@code x}, {@code y}, and {@code z} coordinates.

      @param x  x-coordinate of the new {@code Vertex}
      @param y  y-coordinate of the new {@code Vertex}
      @param z  z-coordinate of the new {@code Vertex}
   */
   public Vertex(double x, double y, double z)
   {
      set(x, y, z, 1.0);
   }


   /**
      Construct a new {@code Vertex} with the given homogeneous coordinates.

      @param x  x-coordinate of the new {@code Vertex}
      @param y  y-coordinate of the new {@code Vertex}
      @param z  z-coordinate of the new {@code Vertex}
      @param w  w-coordinate of the new {@code Vertex}
   */
   public Vertex(double x, double y, double z, double w)
   {
      set(x, y, z, w);
   }


   /**
      Construct a new {@code Vertex} that is a copy of another {@code Vertex}.

      @param v  {@code Vertex} to make a copy of
   */
   public Vertex(Vertex v) // a "copy constructor"
   {
      set(v.x, v.y, v.z, v.w);
   }


   /**
      Copy the coordinates of {@code Vertex} {@code v} to
      this {@code Vertex}.

      @param v  {@code Vertex} whose coordinates are copied to this {@code Vertex}
   */
   public void set(Vertex v)
   {
      set(v.x, v.y, v.z, v.w);
   }


   /**
      Set the {@code x}, {@code y}, and {@code z} coordinates
      of this {@code Vertex}.

      @param x  new x-coordinate for this {@code Vertex}
      @param y  new y-coordinate for this {@code Vertex}
      @param z  new z-coordinate for this {@code Vertex}
   */
   public void set(double x, double y, double z)
   {
      set(x, y, z, 1);
   }


   /**
      Set the homogeneous coordinates of this {@code Vertex}.

      @param x  new x-coordinate for this {@code Vertex}
      @param y  new y-coordinate for this {@code Vertex}
      @param z  new z-coordinate for this {@code Vertex}
      @param w  new w-coordinate for this {@code Vertex}
   */
   public void set(double x, double y, double z, double w)
   {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Vertex} object
   */
   @Override
   public String toString()
   {
      int precision = 5;  // the default precision for the format string
      return toString(precision);
   }


   /**
      For debugging.
      <p>
      Allow the precision of the formatted output to be specified.

      @param precision  precision value for the format string
      @return {@link String} representation of this {@code Vertex} object
   */
   public String toString(int precision)
   {
      int iWidth = 3; // default width of integer part of the format string
      return toString(precision, iWidth);
   }


   /**
      For debugging.
      <p>
      Allow the precision and width of the formatted output to be specified.
      By width, we mean the width of the integer part of each number.

      @param precision  precision value for the format string
      @param iWidth     width of the integer part of the format string
      @return {@link String} representation of this {@code Vertex} object
   */
   public String toString(int precision, int iWidth)
   {
      // Here is one way to get programmable precision and width.
      int p = precision;      // the precision for the following format string
      int t = p + iWidth + 2; // the width for the following format string
      String format = "(x,y,z,w)=(% "+t+"."+p+"f  % "+t+"."+p+"f  % "+t+"."+p+"f  % "+t+"."+p+"f)\n";
      return String.format(format, x, y, z, w);
   }
}
