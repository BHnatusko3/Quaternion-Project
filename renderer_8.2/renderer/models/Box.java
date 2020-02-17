/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a cuboid aligned with
   the x, y, and z axes and with one corner at the
   origin.
<p>
   Here is a picture showing how the cuboid's eight
   vertices are labeled.
<pre>{@code
                  v[4]
                   +-----------------+ v[5]
                  /|                /|
                /  |              /  |
              /    |            /    |
            /      |          /      |
      v[7] +-----------------+ v[6]  |
           |       |         |       |               y
           |       |         |       |               |
           |       |         |       |               |
           |  v[0] +---------|-------+ v[1]          |
           |      /          |      /                |
           |    /            |    /                  +----. x
           |  /              |  /                   /
           |/                |/                    /
           +-----------------+                    /
          v[3]              v[2]                 z
}</pre>
   See <a href="http://en.wikipedia.org/wiki/Cuboid" target="_top">
                http://en.wikipedia.org/wiki/Cuboid</a>

   @see Cube
*/
public class Box extends Model
{
   /**
      Create a {@code Box} with all three sides of length 1.
   */
   public Box( )
   {
      this(1, 1, 1);
   }


   /**
      Create a {@code Box} with the given side lengths.

      @param xs  the size of the {@code Box} along the x-axis
      @param ys  the size of the {@code Box} along the y-axis
      @param zs  the size of the {@code Box} along the z-axis
   */
   public Box(double xs, double ys, double zs)
   {
      super("Box");

      // Create the box's geometry.
      Vertex v0 = new Vertex(0,    0,    0);  // four vertices around the bottom face
      Vertex v1 = new Vertex(0+xs, 0,    0);
      Vertex v2 = new Vertex(0+xs, 0,    0+zs);
      Vertex v3 = new Vertex(0,    0,    0+zs);
      Vertex v4 = new Vertex(0,    0+ys, 0);  // four vertices around the top face
      Vertex v5 = new Vertex(0+xs, 0+ys, 0);
      Vertex v6 = new Vertex(0+xs, 0+ys, 0+zs);
      Vertex v7 = new Vertex(0,    0+ys, 0+zs);

      // Add the box's vertices to the model.
      addVertex(v0, v1, v2, v3, v4, v5, v6, v7);

      // Create 12 line segments.

      addLineSegment(new LineSegment(0, 1), // bottom face
                     new LineSegment(1, 2),
                     new LineSegment(2, 3),
                     new LineSegment(3, 0),
                     new LineSegment(4, 5), // top face
                     new LineSegment(5, 6),
                     new LineSegment(6, 7),
                     new LineSegment(7, 4),
                     new LineSegment(0, 4), // back face
                     new LineSegment(1, 5),
                     new LineSegment(2, 6), // front face
                     new LineSegment(3, 7));
   }
}//Box
