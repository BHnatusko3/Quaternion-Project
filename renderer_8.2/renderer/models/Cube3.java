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
   the cube cut up by a triangle fan.
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
   @see Cube4
*/
public class Cube3 extends Cube
{
   /**
      Create a cube with its center at the origin, having edge
      length 2, with its corners at {@code (±1, ±1, ±1)}. and
      with a triangle fan of four triangles in each face.
   */
   public Cube3( )
   {
      this(1, 1, 1);
   }


   /**
      Create a cube with its center at the origin, having edge
      length 2, with its corners at {@code (±1, ±1, ±1)}, and
      with each of the cube's faces containing a triangle fan
      with the given number of triangles along each of the x,
      y, and z directions.
      <p>
      There must be at least one triangle along each direction.

      @param xCount  number of triangles along the x-direction
      @param yCount  number of triangles along the y-direction
      @param zCount  number of triangles along the z-direction
   */
   public Cube3(int xCount, int yCount, int zCount)
   {
      super();  // create the basic cube with 8 vertices and 12 edges
      this.name = "Cube3";

      int index = 8;

      if (xCount < 1) xCount = 1;
      if (yCount < 1) yCount = 1;
      if (zCount < 1) zCount = 1;

      double xStep = 2.0 / xCount;
      double yStep = 2.0 / yCount;
      double zStep = 2.0 / zCount;

      addVertex(new Vertex(0,  0,  1));
      int centerFront = index++;
      addVertex(new Vertex(0,  0, -1));
      int centerBack = index++;
      addVertex(new Vertex(0,  1,  0));
      int centerTop = index++;
      addVertex(new Vertex(0, -1,  0));
      int centerBottom = index++;
      addVertex(new Vertex( 1, 0,  0));
      int centerRight = index++;
      addVertex(new Vertex(-1, 0,  0));
      int centerLeft = index++;

      // Triangles along all four edges parallel to the x-axis.
      double x = -1.0;
      for (int i = 0; i <= xCount; ++i)
      {
         addVertex(new Vertex(x,  1,  1),
                   new Vertex(x, -1,  1),
                   new Vertex(x,  1, -1),
                   new Vertex(x, -1, -1));
         // front face, top and bottom edges
         addLineSegment(new LineSegment(index+0, centerFront),
                        new LineSegment(index+1, centerFront));
         // back face, top and bottom edges
         addLineSegment(new LineSegment(index+2, centerBack),
                        new LineSegment(index+3, centerBack));
         // top face, front and back edges
         addLineSegment(new LineSegment(index+0, centerTop),
                        new LineSegment(index+2, centerTop));
         // bottom face, front and back edges
         addLineSegment(new LineSegment(index+1, centerBottom),
                        new LineSegment(index+3, centerBottom));
         x += xStep;
         index += 4;
      }

      // Triangles along all four edges parallel to the y-axis.
      double y = -1.0;
      for (int i = 0; i <= yCount; ++i)
      {
         addVertex(new Vertex( 1, y,  1),
                   new Vertex(-1, y,  1),
                   new Vertex( 1, y, -1),
                   new Vertex(-1, y, -1));
         // front face, right and left edges
         addLineSegment(new LineSegment(index+0, centerFront),
                        new LineSegment(index+1, centerFront));
         // back face, right and left edges
         addLineSegment(new LineSegment(index+2, centerBack),
                        new LineSegment(index+3, centerBack));
         // right face, front and back edges
         addLineSegment(new LineSegment(index+0, centerRight),
                        new LineSegment(index+2, centerRight));
         // left face, front and back edges
         addLineSegment(new LineSegment(index+1, centerLeft),
                        new LineSegment(index+3, centerLeft));
         y += yStep;
         index += 4;
      }

      // Triangles along all four edges parallel to the z-axis.
      double z = -1.0;
      for (int i = 0; i <= zCount; ++i)
      {
         addVertex(new Vertex( 1,  1, z),
                   new Vertex(-1,  1, z),
                   new Vertex( 1, -1, z),
                   new Vertex(-1, -1, z));
         // top face, right and left edges
         addLineSegment(new LineSegment(index+0, centerTop),
                        new LineSegment(index+1, centerTop));
         // bottom face, right and left edges
         addLineSegment(new LineSegment(index+2, centerBottom),
                        new LineSegment(index+3, centerBottom));
         // right face, top and bottom edges
         addLineSegment(new LineSegment(index+0, centerRight),
                        new LineSegment(index+2, centerRight));
         // left face, top and bottom edges
         addLineSegment(new LineSegment(index+1, centerLeft),
                        new LineSegment(index+3, centerLeft));
         z += zStep;
         index += 4;
      }
   }
}//Cube3
