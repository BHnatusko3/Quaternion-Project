/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a circle in the xy-plane centered at the origin.
*/
public class Circle extends Model
{
   /**
      Create a circle in the xy-plane with radius 1 and
      with 12 line segments around the circumference.
   */
   public Circle( )
   {
      this(1, 12);
   }


   /**
      Create a circle in the xy-plane with radius {@code r}
      and with 12 line segments around the circumference.

      @param r  radius of the circle
   */
   public Circle(double r)
   {
      this(r, 12);
   }


   /**
      Create a circle in the xy-plane with radius {@code r}
      and with {@code n} line segments around the circumference.

      @param r  radius of the circle
      @param n  number of line segments in the circle's circumference
   */
   public Circle(double r, int n)
   {
      super("Circle");

      if (n < 3) n = 3;

      // Create the circle's geometry.

      // An array of vertices to be used to create the geometry.
      Vertex[] v = new Vertex[n];

      // Create all the vertices.
      for (int i = 0; i < n; ++i)
      {
         double c = Math.cos(i*(2.0*Math.PI)/n);
         double s = Math.sin(i*(2.0*Math.PI)/n);
         v[i] = new Vertex(r * c, r * s, 0);
      }

      // Add the circle's vertices to the model.
      addVertex(v);

      // Create the line segments around the circle.
      for (int i = 0; i < n - 1; ++i)
      {
         addLineSegment(new LineSegment(i, i+1));
      }
      addLineSegment(new LineSegment(n-1, 0));
   }
}//Circle
