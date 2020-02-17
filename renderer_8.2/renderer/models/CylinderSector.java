/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a partial right circular cylinder
   with its axis along the y-axis.
<p>
   By a partial cylinder we mean a cylinder over a circular sector
   of the cylinder's base.
<p>
   See <a href="https://en.wikipedia.org/wiki/Circular_sector" target="_top">
                https://en.wikipedia.org/wiki/Circular_sector</a>

   @see Cylinder
*/
public class CylinderSector extends Model
{
   /**
      Create half of a cylinder with radius 1
      and its axis along the y-axis from
      {@code y = -1} to {@code y = 1}.
   */
   public CylinderSector( )
   {
      this(1, 1, Math.PI/2, 3*Math.PI/2, 15, 8);
   }


   /**
      Create a part of the cylinder with radius {@code r} and its
      axis along the y-axis from {@code y = -h} to {@code y = h}.
   <p>
      The partial cylinder is a cylinder over the circular sector
      from angle {@code theta1} to angle {@code theta2}.
   <p>
      The last two parameters determine the number of lines of longitude
      and the number of (partial) circles of latitude in the model.
   <p>
      If there are {@code n} circles of latitude in the model, then
      each line of longitude will have {@code n-1} line segments.
      If there are {@code k} lines of longitude, then each (partial)
      circle of latitude will have {@code k-1} line segments.
   <p>
      There must be at least four lines of longitude and at least
      two circles of latitude.

      @param r       radius of the cylinder
      @param h       height of the cylinder
      @param theta1  beginning longitude angle of the sector
      @param theta2  ending longitude angle of the sector
      @param n       number of circles of latitude around the cylinder
      @param k       number of lines of longitude
   */
   public CylinderSector(double r, double h,
                         double theta1, double theta2,
                         int n, int k)
   {
      super("Cylinder Sector");

      if (n < 2) n = 2;
      if (k < 4) k = 4;

      // Create the cylinder's geometry.

      double deltaH = (2 * h) / (n - 1);
      double deltaTheta = (theta2 - theta1)/ (k - 1);

      // An array of vertices to be used to create line segments.
      Vertex[][] v = new Vertex[n][k];

      // Create all the vertices.
      for (int j = 0; j < k; ++j) // choose an angle of longitude
      {
         double c = Math.cos(theta1 + j*deltaTheta);
         double s = Math.sin(theta1 + j*deltaTheta);
         for (int i = 0; i < n; ++i) // choose a circle of latitude
         {
            v[i][j] = new Vertex( r * c,
                                 -h + i * deltaH,
                                  r * s );
         }
      }
      Vertex topCenter    = new Vertex(0,  h, 0);
      Vertex bottomCenter = new Vertex(0, -h, 0);

      // Add all of the vertices to this model.
      for (int i = 0; i < n; ++i)
      {
         for (int j = 0; j < k; ++j)
         {
            addVertex( v[i][j] );
         }
      }
      addVertex(topCenter);
      addVertex(bottomCenter);
      int topCenterIndex = n * k;
      int bottomCenterIndex = n * k + 1;


      // Create the horizontal (partial) circles of latitude around the cylinder.
      for (int i = 0; i < n; ++i)
      {
         for (int j = 0; j < k - 1; ++j)
         {  //                v[i][j]      v[i][j+1]
            addLineSegment(new LineSegment( (i * k) + j, (i * k) + (j+1) ));
         }
      }

      // Create the lines of longitude from the bottom to the top.
      for (int j = 0; j < k; ++j)
      {  //                                   v[0][j]
         addLineSegment(new LineSegment( bottomCenterIndex, (0 * k) + j ));

         for (int i = 0; i < n - 1; ++i)
         {  //                v[i][j]       v[i+1][j]
            addLineSegment(new LineSegment( (i * k) + j, ((i+1) * k) + j ));
         }
         //                v[n-1][j]
         addLineSegment(new LineSegment( ((n-1) * k) + j, topCenterIndex ));
      }
   }
}//CylinderSector
