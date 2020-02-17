/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a square in the xy-plane centered at the origin.
<p>
   Here is a picture showing how the square's four vertices are labeled.
<pre>{@code
                   y
                   |
                   |
      v1           |            v2
        +----------------------+
        |          |           |
        |          |           |
        |          |           |
        |          |           |
  ------|----------+-----------|-------> x
        |          |           |
        |          |           |
        |          |           |
        +----------------------+
      v0           |            v3
                   |
                   |
}</pre>
*/
public class Square extends Model
{
   /**
      Create a square in the xy-plane with corners {@code (±1, ±1, 0)}.
   */
   public Square( )
   {
      this(1);
   }


   /**
      Create a square in the xy-plane with corners {@code (±r, ±r, 0)}.

      @param r  determines the corners of the square
   */
   public Square(double r)
   {
      super("Square");

      r = Math.abs(r);

      // Create the square's geometry.

      // Create the vertices.
      addVertex(new Vertex(-r, -r, 0));
      addVertex(new Vertex(-r,  r, 0));
      addVertex(new Vertex( r,  r, 0));
      addVertex(new Vertex( r, -r, 0));

      // Create the line segments.
      addLineSegment(new LineSegment(0, 1),
                     new LineSegment(1, 2),
                     new LineSegment(2, 3),
                     new LineSegment(3, 0));
   }
}//Square
