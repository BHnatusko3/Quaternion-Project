/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a cube with its center
   at the origin, having edge length 2, and with its
   corners at {@code (±1, ±1, ±1)}.
<p>
   This version of the cube model has each face of
   the cube cut up by an n by m grid of lines.
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
   @see Cube3
   @see Cube4
*/
public class Cube2 extends Cube
{
   /**
      Create a cube with its center at the origin, having edge
      length 2, with its corners at {@code (±1, ±1, ±1)}. and
      with two perpendicular grid lines going across the middle
      of each of the cube's faces.
   */
   public Cube2( )
   {
      this(1, 1, 1);
   }


   /**
      Create a cube with its center at the origin, having edge
      length 2, with its corners at {@code (±1, ±1, ±1)}, and
      with each of the cube's faces containing the given number
      of grid lines parallel to the x, y, and z directions.

      @param xGrid  number of grid lines perpendicular to the x-axis
      @param yGrid  number of grid lines perpendicular to the y-axis
      @param zGrid  number of grid lines perpendicular to the z-axis
   */
   public Cube2(int xGrid, int yGrid, int zGrid)
   {
      super();  // create the basic cube with 8 vertices and 12 edges
      this.name = "Cube2";

      int index = 8;

      if (xGrid < 0) xGrid = 0;
      if (yGrid < 0) yGrid = 0;
      if (zGrid < 0) zGrid = 0;

      double xStep = 2.0 / (1 + xGrid);
      double yStep = 2.0 / (1 + yGrid);
      double zStep = 2.0 / (1 + zGrid);

      // Grid lines perpendicular to the x-axis.
      double x = -1.0;
      for (int i = 0; i < xGrid; ++i)
      {
         x += xStep;
         // Start at the top, front edge, go down the front face, and around the cube.
         addVertex(new Vertex(x,  1,  1),
                   new Vertex(x, -1,  1),
                   new Vertex(x, -1, -1),
                   new Vertex(x,  1, -1));
         addLineSegment(new LineSegment(index+0, index+1),
                        new LineSegment(index+1, index+2),
                        new LineSegment(index+2, index+3),
                        new LineSegment(index+3, index+0));
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
      for (int i = 0; i < zGrid; ++i)
      {
         z += zStep;
         // Start at the top, right edge, go left across the top face, and around the cube.
         addVertex(new Vertex( 1,  1, z),
                   new Vertex(-1,  1, z),
                   new Vertex(-1, -1, z),
                   new Vertex( 1, -1, z));
         addLineSegment(new LineSegment(index+0, index+1),
                        new LineSegment(index+1, index+2),
                        new LineSegment(index+2, index+3),
                        new LineSegment(index+3, index+0));
         index += 4;
      }
   }
}//Cube2
