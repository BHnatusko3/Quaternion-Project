/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a ring (an annulus)
   in the xy-plane centered at the origin.
<p>
   See <a href="https://en.wikipedia.org/wiki/Annulus_(mathematics)" target="_top">
                https://en.wikipedia.org/wiki/Annulus_(mathematics)</a>

   @see RingSector
*/
public class Ring extends Model
{
   /**
      Create a ring (annulus) in the xy-plane with outer
      radius 1 and with inner radius 0.33, with 12 spokes
      coming out of the center, and with 5 concentric circles.
   */
   public Ring( )
   {
      this(1.0, 0.33, 4, 12);
   }


   /**
      Create a ring (annulus) in the xy-plane with outer
      radius {@code r1} and with inner radius {@code r2},
      with 12 spokes coming out of the center, and with
      5 concentric circles.

      @param r1  outer radius of the ring
      @param r2  inner radius of the ring
   */
   public Ring(double r1, double r2)
   {
      this(r1, r2, 4, 12);
   }


   /**
      Create a ring (annulus) in the xy-plane with outer
      radius {@code r1} and with inner radius {@code r2},
      with {@code k} spokes coming out of the center, and
      with {@code n} concentric circles (not counting the
      inner most circle).
   <p>
      If there are {@code k} spokes, then each circle around
      the center will have {@code k} line segments. If there
      are {@code n} concentric circles around the center (not
      counting the inner most circle), then each spoke will
      have {@code n} line segments.
   <p>
      There must be at least three spokes and at least one concentric circle.

      @param r1  outer radius of the ring
      @param r2  inner radius of the ring
      @param n   number of concentric circles
      @param k   number of spokes in the ring
   */
   public Ring(double r1, double r2, int n, int k)
   {
      super("Ring");

      if (n < 1) n = 1;
      if (k < 3) k = 3;

      // Create the rings's geometry.

      double deltaR = (r1 - r2) / n;
      double deltaTheta = (2 * Math.PI) / k;

      // An array of vertices to be used to create line segments.
      Vertex[][] v = new Vertex[n+1][k];

      // Create all the vertices.
      for (int j = 0; j < k; ++j) // choose a spoke (an angle)
      {
         double c = Math.cos(j * deltaTheta);
         double s = Math.sin(j * deltaTheta);
         for (int i = 0; i < n + 1; ++i) // move along the spoke
         {
            double ri = r2 + i * deltaR;
            v[i][j] = new Vertex(ri * c,
                                 ri * s,
                                 0);
         }
      }

      // Add all of the vertices to this model.
      for (int i = 0; i < n+1; ++i)
      {
         for (int j = 0; j < k; ++j)
         {
            addVertex( v[i][j] );
         }
      }

      // Create line segments around each concentric ring.
      for (int i = 0; i < n + 1; ++i)  // choose a ring
      {
         for (int j = 0; j < k - 1; ++j)
         {  //                                v[i][[j]     v[i][j+1]
            addLineSegment(new LineSegment( (i * k) + j, (i * k) + (j+1) ));
         }
         // close the circle
         addLineSegment(new LineSegment( (i * k) + (k-1), (i * k) + 0 ));
      }  //                               v[i][k-1]         v[i][0]

      // Create the spokes.connecting the inner circle to the outer circle.
      for (int j = 0; j < k; ++j) // choose a spoke
      {
         for (int i = 0; i < n; ++i)
         {  //                                v[i][j]       v[i+1][j]
            addLineSegment(new LineSegment( (i * k) + j, ((i+1) * k) + j ));
         }
      }
   }
}//Ring
