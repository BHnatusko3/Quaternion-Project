/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a cube with its center
   at the origin, having edge length 2, and with its
   corners at {@code (±1, ±1, ±1)}.
<p>
   This version of the cube model has the top and bottom
   faces of the cube cut up by a triangle fan and the
   front, back, right, and left faces cut up by a grid
   of perpendicular lines.
<p>
   Here is a picture showing how the cube's eight corners
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

   @see Cube
   @see Cube2
   @see Cube3
*/
public class Cube4 extends Cube
{
   /**
      Create a cube with its center at the origin, having edge
      length 2, with its corners at {@code (±1, ±1, ±1)}. with
      a triangle fan of eight triangles in top and bottom faces,
      and two perpendicular lines cutting each of the front,
      back, right, and left faces.
   */
   public Cube4( )
   {
      this(2, 1, 2);
   }


   /**
      Create a cube with its center at the origin, having edge
      length 2, with its corners at {@code (±1, ±1, ±1)}, and
      with the top and bottom faces containing a triangle fan
      with the given number of triangles along each of the x,
      and z directions.
      <p>
      There must be at least one triangle along the x and z directions.

      @param xCount  number of triangles along the x-direction
      @param yGrid   number of grid lines perpendicular to the y-axis
      @param zCount  number of triangles along the z-direction
   */
   public Cube4(int xCount, int yGrid, int zCount)
   {
      super();  // create the basic cube with 8 vertices and 12 edges
      this.name = "Cube4";

      int index = 8;

      if (xCount < 1) xCount = 1;
      if (yGrid  < 0) yGrid  = 0;
      if (zCount < 1) zCount = 1;

      double xStep = 2.0 / xCount;
      double yStep = 2.0 / (1 + yGrid);
      double zStep = 2.0 / zCount;

      addVertex(new Vertex(0,  1,  0));
      int centerTop = index++;
      addVertex(new Vertex(0, -1,  0));
      int centerBottom = index++;

      // Triangles along all four edges parallel to the x-axis.
      double x = -1.0;
      for (int i = 0; i <= xCount; ++i)
      {
         // top face, front and back edges
         addVertex(new Vertex(x,  1,  1),
                   new Vertex(x,  1, -1));
         addLineSegment(new LineSegment(index+0, centerTop),
                        new LineSegment(index+1, centerTop));
         // bottom face, front and back edges
         addVertex(new Vertex(x, -1,  1),
                   new Vertex(x, -1, -1));
         addLineSegment(new LineSegment(index+2, centerBottom),
                        new LineSegment(index+3, centerBottom));
         index += 4;
         x += xStep;
      }

      // Grid lines perpendicular to the x-axis.
      x = -1.0;
      for (int i = 0; i < xCount; ++i)
      {
         x += xStep;
         // front and back faces only
         addVertex(new Vertex(x,  1,  1),
                   new Vertex(x, -1,  1));
         addLineSegment(new LineSegment(index+0, index+1));
         addVertex(new Vertex(x, -1, -1),
                   new Vertex(x,  1, -1));
         addLineSegment(new LineSegment(index+2, index+3));
         index += 4;
      }

      // Grid lines perpendicular to the y-axis.
      double y = -1.0;
      for (int i = 0; i < yGrid; ++i)
      {
         y += yStep;
         // Start at the front, right edge, go left across the front face, and around the cube.
         addVertex(new Vertex( 1, y,  1),
                   new Vertex(-1, y,  1),
                   new Vertex(-1, y, -1),
                   new Vertex( 1, y, -1));
         addLineSegment(new LineSegment(index+0, index+1),
                        new LineSegment(index+1, index+2),
                        new LineSegment(index+2, index+3),
                        new LineSegment(index+3, index+0));
         index += 4;
      }

      // Grid lines perpendicular to the z-axis.
      double z = -1.0;
      for (int i = 0; i < zCount; ++i)
      {
         z += zStep;
         // left and right faces only
         addVertex(new Vertex(-1,  1, z),
                   new Vertex(-1, -1, z));
         addLineSegment(new LineSegment(index+0, index+1));
         addVertex(new Vertex( 1, -1, z),
                   new Vertex( 1,  1, z));
         addLineSegment(new LineSegment(index+2, index+3));
         index += 4;
      }

      // Triangles along all four edges parallel to the z-axis.
      z = -1.0;
      for (int i = 0; i <= zCount; ++i)
      {
         // top face, right and left edges
         addVertex(new Vertex( 1,  1, z),
                   new Vertex(-1,  1, z));
         addLineSegment(new LineSegment(index+0, centerTop),
                        new LineSegment(index+1, centerTop));
         // bottom face, right and left edges
         addVertex(new Vertex( 1, -1, z),
                   new Vertex(-1, -1, z));
         addLineSegment(new LineSegment(index+2, centerBottom),
                        new LineSegment(index+3, centerBottom));
         index += 4;
         z += zStep;
      }
   }
}//Cube4
