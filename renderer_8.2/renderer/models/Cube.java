/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a cube with its center
   at the origin, having edge length 2, and with its
   vertices at {@code (±1, ±1, ±1)}.
<p>
   Here is a picture showing how the cube's eight vertices
   are labeled.
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
   See <a href="http://en.wikipedia.org/wiki/Cube" target="_top">
                http://en.wikipedia.org/wiki/Cube</a>

   @see Tetrahedron
   @see Octahedron
   @see Icosahedron
   @see Dodecahedron
*/
public class Cube extends Model
{
   /**
      Create a cube with its center at the origin, having edge
      length 2, and with its vertices at {@code (±1, ±1, ±1)}.
   */
   public Cube( )
   {
      super("Cube");

      // Create the cube's geometry.
      Vertex v0 = new Vertex(-1, -1, -1); // four vertices around the bottom face
      Vertex v1 = new Vertex( 1, -1, -1);
      Vertex v2 = new Vertex( 1, -1,  1);
      Vertex v3 = new Vertex(-1, -1,  1);
      Vertex v4 = new Vertex(-1,  1, -1); // four vertices around the top face
      Vertex v5 = new Vertex( 1,  1, -1);
      Vertex v6 = new Vertex( 1,  1,  1);
      Vertex v7 = new Vertex(-1,  1,  1);

      // Add the cube's vertices to the model.
      addVertex(v0, v1, v2, v3);
      addVertex(v4, v5, v6, v7);

      // Create 12 line segments.
      addLineSegment(new LineSegment(0, 1),  // bottom face
                     new LineSegment(1, 2),
                     new LineSegment(2, 3),
                     new LineSegment(3, 0),
                     new LineSegment(4, 5),  // top face
                     new LineSegment(5, 6),
                     new LineSegment(6, 7),
                     new LineSegment(7, 4),
                     new LineSegment(0, 4),  // back face
                     new LineSegment(1, 5),
                     new LineSegment(2, 6),  // front face
                     new LineSegment(3, 7));
   }
}//Cube
