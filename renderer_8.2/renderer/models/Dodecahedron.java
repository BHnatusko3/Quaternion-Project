/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a regular dodecahedron
   with its center at the origin, having edge length
   <pre>{@code
     2*(sqrt(5)-1)/(1+sqrt(5)) = (1/2)*(sqrt(5)-1)^2 = 0.7639,
   }</pre>
   and with its vertices on a sphere of radius
   <pre>{@code
     2*sqrt(3)/(1+sqrt(5)) = 1.0705.
   }</pre>
<p>
   See <a href="https://en.wikipedia.org/wiki/Regular_dodecahedron" target="_top">
                https://en.wikipedia.org/wiki/Regular_dodecahedron</a>

   @see Tetrahedron
   @see Cube
   @see Octahedron
   @see Icosahedron
*/
public class Dodecahedron extends Model
{
   /**
      Create a regular dodecahedron with its center at
      the origin, having edge length
      <pre>{@code
        2*(sqrt(5)-1)/(1+sqrt(5)) = (1/2)*(sqrt(5)-1)^2 = 0.7639,
      }</pre>
      and with its vertices on a sphere of radius
      <pre>{@code
        2*sqrt(3)/(1+sqrt(5)) = 1.0705.
     }</pre>
   */
   public Dodecahedron()
   {
      super("Dodecahedron");

      // Create the dodecahedron's geometry.
      // It has 20 vertices and 30 edges.
      double t = (1 + Math.sqrt(5))/2;   // golden ratio
      double r = 1/t;
      double r2 = r * r;
      //https://en.wikipedia.org/wiki/Regular_dodecahedron#Cartesian_coordinates
      // (±r, ±r, ±r)
      Vertex v00 = new Vertex(-r, -r, -r);
      Vertex v01 = new Vertex(-r, -r,  r);
      Vertex v02 = new Vertex(-r,  r, -r);
      Vertex v03 = new Vertex(-r,  r,  r);
      Vertex v04 = new Vertex( r, -r, -r);
      Vertex v05 = new Vertex( r, -r,  r);
      Vertex v06 = new Vertex( r,  r, -r);
      Vertex v07 = new Vertex( r,  r,  r);

      // (0, ±r2, ±1)
      Vertex v08 = new Vertex( 0, -r2, -1);
      Vertex v09 = new Vertex( 0, -r2,  1);
      Vertex v10 = new Vertex( 0,  r2, -1);
      Vertex v11 = new Vertex( 0,  r2,  1);

      // (±r2, ±1, 0)
      Vertex v12 = new Vertex(-r2, -1,  0);
      Vertex v13 = new Vertex(-r2,  1,  0);
      Vertex v14 = new Vertex( r2, -1,  0);
      Vertex v15 = new Vertex( r2,  1,  0);

      // (±1, 0, ±r2)
      Vertex v16 = new Vertex(-1,  0, -r2);
      Vertex v17 = new Vertex( 1,  0, -r2);
      Vertex v18 = new Vertex(-1,  0,  r2);
      Vertex v19 = new Vertex( 1,  0,  r2);
/*
      // These vertices create a dodecahedron with vertices
      // on a sphere of radius sqrt(3), and with edge length
      //    2/t = 4/(1 + sqrt(5)) = sqrt(5) - 1 = 1.2361.
      //https://en.wikipedia.org/wiki/Regular_dodecahedron#Cartesian_coordinates
      // (±1, ±1, ±1)
      Vertex v00 = new Vertex(-1, -1, -1);
      Vertex v01 = new Vertex(-1, -1,  1);
      Vertex v02 = new Vertex(-1,  1, -1);
      Vertex v03 = new Vertex(-1,  1,  1);
      Vertex v04 = new Vertex( 1, -1, -1);
      Vertex v05 = new Vertex( 1, -1,  1);
      Vertex v06 = new Vertex( 1,  1, -1);
      Vertex v07 = new Vertex( 1,  1,  1);

      // (0, ±r, ±t)
      Vertex v08 = new Vertex( 0, -r, -t);
      Vertex v09 = new Vertex( 0, -r,  t);
      Vertex v10 = new Vertex( 0,  r, -t);
      Vertex v11 = new Vertex( 0,  r,  t);

      // (±r, ±t, 0)
      Vertex v12 = new Vertex(-r, -t,  0);
      Vertex v13 = new Vertex(-r,  t,  0);
      Vertex v14 = new Vertex( r, -t,  0);
      Vertex v15 = new Vertex( r,  t,  0);

      // (±t, 0, ±r)
      Vertex v16 = new Vertex(-t,  0, -r);
      Vertex v17 = new Vertex( t,  0, -r);
      Vertex v18 = new Vertex(-t,  0,  r);
      Vertex v19 = new Vertex( t,  0,  r);
*/
      // Add the dodecahedron's vertices to the model.
      addVertex(v00, v01, v02, v03, v04, v05, v06, v07);
      addVertex(v08, v09, v10, v11);
      addVertex(v12, v13, v14, v15);
      addVertex(v16, v17, v18, v19);

      // Create 30 line segments (that make up 12 faces).
//https://github.com/mrdoob/three.js/blob/master/src/geometries/DodecahedronGeometry.js
      addLineSegment(new LineSegment( 3, 11));
      addLineSegment(new LineSegment(11,  7));
      addLineSegment(new LineSegment( 7, 15));
      addLineSegment(new LineSegment(15, 13));
      addLineSegment(new LineSegment(13,  3));

      addLineSegment(new LineSegment( 7, 19));
      addLineSegment(new LineSegment(19, 17));
      addLineSegment(new LineSegment(17,  6));
      addLineSegment(new LineSegment( 6, 15));

      addLineSegment(new LineSegment(17,  4));
      addLineSegment(new LineSegment( 4,  8));
      addLineSegment(new LineSegment( 8, 10));
      addLineSegment(new LineSegment(10,  6));

      addLineSegment(new LineSegment( 8,  0));
      addLineSegment(new LineSegment( 0, 16));
      addLineSegment(new LineSegment(16,  2));
      addLineSegment(new LineSegment( 2, 10));

      addLineSegment(new LineSegment( 0, 12));
      addLineSegment(new LineSegment(12,  1));
      addLineSegment(new LineSegment( 1, 18));
      addLineSegment(new LineSegment(18, 16));

      addLineSegment(new LineSegment( 2, 13));

      addLineSegment(new LineSegment(18,  3));

      addLineSegment(new LineSegment( 1,  9));
      addLineSegment(new LineSegment( 9, 11));

      addLineSegment(new LineSegment( 4, 14));
      addLineSegment(new LineSegment(14, 12));

      addLineSegment(new LineSegment( 9,  5));
      addLineSegment(new LineSegment( 5, 19));

      addLineSegment(new LineSegment( 5, 14));
   }
}//Dodecahedron
