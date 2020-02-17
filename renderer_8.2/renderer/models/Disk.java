/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a disk
   in the xy-plane centered at the origin.
<p>
   See <a href="https://en.wikipedia.org/wiki/Disk_(mathematics)" target="_top">
                https://en.wikipedia.org/wiki/Disk_(mathematics)</a>

   @see DiskSector
*/
public class Disk extends Model
{
   /**
      Create a disk in the xy-plane with radius 1,
      with 12 spokes coming out of the center, and
      with 6 concentric circles around the disk.
   */
   public Disk( )
   {
      this(1, 6, 12);
   }


   /**
      Create a disk in the xy-plane with radius
      {@code r}, with 12 spokes coming out of the
      center, and with 6 concentric circles around
      the disk.

      @param r  radius of the disk
   */
   public Disk(double r)
   {
      this(r, 6, 12);
   }


   /**
      Create a disk in the xy-plane with radius
      {@code r}, with {@code k} spokes coming out
      of the center, and with {@code n} concentric
      circles around the disk.
   <p>
      If there are {@code k} spokes, then each circle around the
      center will have {@code k} line segments.
      If there are {@code n} concentric circles around the
      center, then each spoke will have {@code n} line segments.
   <p>
      There must be at least three spokes and at least
      one concentric circle.

      @param r  radius of the disk
      @param n  number of concentric circles
      @param k  number of spokes in the disk
   */
   public Disk(double r, int n, int k)
   {
      super("Disk");

      if (n < 1) n = 1;
      if (k < 3) k = 3;

      // Create the disk's geometry.

      double deltaR = r / n;
      double deltaTheta = 2 * Math.PI / k;

      // An array of vertices to be used to create line segments.
      Vertex[][] v = new Vertex[n][k];

      // Create all the vertices.
      for (int j = 0; j < k; ++j) // choose a spoke (an angle)
      {
         double c = Math.cos(j * deltaTheta);
         double s = Math.sin(j * deltaTheta);
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
         for (int i = 0; i < n - 1; i++)
         {  //                                v[i][j]         v[i+1][j]
            addLineSegment(new LineSegment( (i * k) + j, ((i+1) * k) + j ));
         }
      }

      // Create the line segments around each concentric circle.
      for (int i = 0; i < n; ++i)  // choose a circle
      {
         for (int j = 0; j < k - 1; j++)
         {  //                               v[i][j]         v[i][j+1]
            addLineSegment(new LineSegment( (i * k) + j, (i * k) + (j + 1) ));
         }
         // close the circle
         addLineSegment(new LineSegment( (i * k) + (k-1), (i * k) + 0 ));
      }  //                                v[i][k-1]        v[i][0]
   }
}//Disk
