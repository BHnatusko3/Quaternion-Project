/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a sector of a disk
   in the xy-plane centered at the origin.
<p>
   See <a href="https://en.wikipedia.org/wiki/Circular_sector" target="_top">
                https://en.wikipedia.org/wiki/Circular_sector</a>

   @see Disk
*/
public class DiskSector extends Model
{
   /**
      Create half a disk in the xy-plane with radius 1,
      with 7 spokes coming out of the center, and
      with 6 concentric circles around the disk.
   */
   public DiskSector( )
   {
      this(1, 0, Math.PI, 6, 7);
   }


   /**
      Create a sector of a disk in the xy-plane with radius
      {@code r}, with {@code k} spokes coming out of the center,
      and with {@code n} concentric circles around the disk.
   <p>
      If there are {@code k} spokes, then each (partial) circle
      around the center will have {@code k-1} line segments.
      If there are {@code n} concentric circles around the center,
      then each spoke will have {@code n} line segments.
   <p>
      There must be at least four spokes and at least one concentric circle.

      @param r       radius of the disk
      @param theta1  beginning angle of the sector
      @param theta2  ending angle of the sector
      @param n       number of concentric circles
      @param k       number of spokes in the disk
   */
   public DiskSector(double r, double theta1, double theta2, int n, int k)
   {
      super("Disk Sector");

      if (n < 1) n = 1;
      if (k < 4) k = 4;

      // Create the disk's geometry.

      double deltaR = r / n;
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
            double ri = (i + 1) * deltaR;
            v[i][j] = new Vertex( ri * c,
                                  ri * s,
                                  0 );
         }
      }
      Vertex center = new Vertex(0,0,0);

      // Add all of the vertices to this model.
      for (int i = 0; i < n; ++i)
      {
         for (int j = 0; j < k; ++j)
         {
            addVertex( v[i][j] );
         }
      }
      addVertex( center );
      int centerIndex = n * k;

      // Create the spokes connecting the center to the outer circle.
      for (int j = 0; j < k; ++j) // choose a spoke
      {  //                                             v[0][j]
         addLineSegment(new LineSegment( centerIndex, (0 * k) + j ));

         for (int i = 0; i < n - 1; ++i)
         {  //                                 v[i][j]        v[i+1][j]
            addLineSegment(new LineSegment( (i * k) + j, ((i+1) * k) + j ));
         }
      }

      // Create the line segments around each (partial) concentric circle.
      for (int i = 0; i < n; ++i)  // choose a circle
      {
         for (int j = 0; j < k - 1; ++j)
         {  //                                v[i][j]        v[i][j+1]
            addLineSegment(new LineSegment( (i * k) + j, (i * k) + (j + 1) ));
         }
      }
   }
}//DiskSector
