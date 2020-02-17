/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a regular tetrahedron
   with its center at the origin, having edge length
   {@code 2*sqrt(2)}, and with its vertices at corners
   of the cube with vertices {@code (±1, ±1, ±1)}.
<p>
   See <a href="http://en.wikipedia.org/wiki/Tetrahedron" target="_top">
                http://en.wikipedia.org/wiki/Tetrahedron</a>

   @see Cube
   @see Octahedron
   @see Icosahedron
   @see Dodecahedron
*/
public class Tetrahedron extends Model
{
   /**
      Create a regular tetrahedron with its center at
      the origin, having edge length {@code 2*sqrt(2)},
      and with its vertices at corners of the cube with
      vertices {@code (±1, ±1, ±1)}.
   */
   public Tetrahedron()
   {
      this(false);
   }


   /**
      Create a regular tetrahedron or its dual tetrahedron
      (the dual of a tetrahedron is another tetrahedron).
   <p>
      <a href="https://en.wikipedia.org/wiki/Tetrahedron#Regular_tetrahedron" target="_top">
               https://en.wikipedia.org/wiki/Tetrahedron#Regular_tetrahedron</a>
   <p>
      The combination of these two dual tetrahedrons is a stellated octahedron.
   <p>
      <a href="https://en.wikipedia.org/wiki/Stellated_octahedron" target="_top">
               https://en.wikipedia.org/wiki/Stellated_octahedron</a>

      @param dual  choose between the two dual tetrahedrons
   */
   public Tetrahedron(boolean dual)
   {
      super("Tetrahedron");

      // Create the tetrahedron's geometry.
      // It has 4 vertices and 6 edges.
      Vertex v0, v1, v2, v3;
      if ( ! dual)
      {
         v0 = new Vertex( 1,  1,  1);
         v1 = new Vertex(-1,  1, -1);
         v2 = new Vertex( 1, -1, -1);
         v3 = new Vertex(-1, -1,  1);
      }
      else // create the dual tetrahedron by
      {    // inverting the coordinates given above
         v0 = new Vertex(-1, -1, -1);
         v1 = new Vertex( 1, -1,  1);
         v2 = new Vertex(-1,  1,  1);
         v3 = new Vertex( 1,  1, -1);
      }

      // Add the tetrahedron's vertices to the model.
      addVertex(v0, v1, v2, v3);

      // Create 6 line segments.
      addLineSegment(new LineSegment(0, 1),  //top (bottom) edge
                     new LineSegment(2, 3),  //bottom (top) edge
                     new LineSegment(0, 2),
                     new LineSegment(0, 3),
                     new LineSegment(1, 2),
                     new LineSegment(1, 3));
   }
}//Tetrahedron
