/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a sector of a ring (an annulus)
   in the xy-plane centered at the origin.
<p>
   See <a href="https://en.wikipedia.org/wiki/Annulus_(mathematics)" target="_top">
                https://en.wikipedia.org/wiki/Annulus_(mathematics)</a>
<p>
   See <a href="https://en.wikipedia.org/wiki/Circular_sector" target="_top">
                https://en.wikipedia.org/wiki/Circular_sector</a>

   @see Ring
*/
public class RingSector extends Model
{
   /**
      Create half a ring (annulus) in the xy-plane
      with outer radius 1, inner radius 0.33, with 7
      spokes coming out of the center, and with 5
      concentric circles.
   */
   public RingSector( )
   {
      this(1.0, 0.33, 0, Math.PI, 5, 7);
   }


   /**
      Create a sector of a ring (annulus) in the xy-plane
      with outer radius {@code r1}, inner radius {@code r2},
      with {@code k} spokes coming out of the center, and
      with {@code n} concentric circles.
   <p>
      If there are {@code k} spokes, then each (partial) circle
      around the center will have {@code k-1} line segments.
      If there are {@code n} concentric circles around the center,
      then each spoke will have {@code n-1} line segments.
   <p>
      There must be at least four spokes and at least two concentric circle.

      @param r1      outer radius of the ring
      @param r2      inner radius of the ring
      @param theta1  beginning angle of the sector
      @param theta2  ending angle of the sector
      @param n       number of concentric circles
      @param k       number of spokes in the ring
   */
   public RingSector(double r1, double r2,
                     double theta1, double theta2,
                     int n, int k)
   {
      super("Ring Sector");

      if (n < 2) n = 2;
      if (k < 4) k = 4;

      // Create the rings's geometry.

      double deltaR = (r1 - r2) / (n - 1);
      double deltaTheta = (theta2 - theta1) / (k - 1);

      // An array of vertices to be used to create line segments.
      Vertex[][] v = new Vertex[n][k];

      // Create all the vertices.
      for (int j = 0; j < k; ++j) // choose a spoke (an angle)
      {
         double c = Math.cos(theta1 + j * deltaTheta);
         double s = Math.sin(theta1 + j * deltaTheta);
         for (int i = 0; i < n; ++i) // move along the spoke
         {
            double ri = r2 + i * deltaR;
            v[i][j] = new Vertex(ri * c,
                                 ri * s,
                                 0);
         }
      }

      // Add all of the vertices to this model.
      for (int i = 0; i < n; ++i)
      {
         for (int j = 0; j < k; ++j)
         {
            addVertex( v[i][j] );
         }
      }

      // Create line segments around each concentric ring.
      for (int i = 0; i < n; ++i)  // choose a ring
      {
         for (int j = 0; j < k - 1; ++j)
         {  //                               v[i][j]       v[i][j+1]
            addLineSegment(new LineSegment( (i * k) + j, (i * k) + (j+1) ));
         }
      }

      // Create the spokes.connecting the inner circle to the outer circle.
      for (int j = 0; j < k; ++j) // choose a spoke
      {
         for (int i = 0; i < n - 1; ++i)
         {  //                                v[i][j]      v[i+1][j]
            addLineSegment(new LineSegment( (i * k) + j, ((i+1) * k) + j ));
         }
      }
   }
}//RingSector
