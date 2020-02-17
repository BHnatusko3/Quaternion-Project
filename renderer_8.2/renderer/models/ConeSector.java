/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a partial right circular cone with its
   base parallel to the xz-plane and its apex on the positive y-axis.
<p>
   By a partial cone we mean a cone over a circular sector of the
   cone's base and also cutting off the top part of the cone (the
   part between the apex and a circle of latitude) leaving a frustum
   of the (partial) cone.

   @see Cone
   @see ConeFrustum
*/
public class ConeSector extends Model
{
   /**
      Create half of a right circular cone with its base in the xz-plane,
      a base radius of 1, height 1, and apex on the positive y-axis.
   */
   public ConeSector( )
   {
      this(1, 1, Math.PI/2, 3*Math.PI/2, 15, 8);
   }


   /**
      Create a part of the cone with its base in the xz-plane,
      a base radius of {@code r}, height {@code h}, and apex
      on the y-axis.
   <p>
      If {@code theta1 > 0} or {@code theta2 < 2pi},then the partial
      cone is a cone over the circular sector from angle {@code theta1}
      to angle {@code theta2}. In other words, the (partial) circles of
      latitude in the model extend from angle {@code theta1} to angle
      {@code theta2}.
   <p>
      The last two parameters determine the number of lines of longitude
      and the number of (partial) circles of latitude in the model.
   <p>
      If there are {@code n} circles of latitude in the model (including
      the bottom edge), then each line of longitude will have {@code n}
      line segments. If there are {@code k} lines of longitude, then each
      (partial) circle of latitude will have {@code k-1} line segments.
   <p>
      There must be at least four lines of longitude and at least
      one circle of latitude.

      @param r       radius of the base in the xz-plane
      @param h       height of the apex on the y-axis
      @param theta1  beginning longitude angle of the sector
      @param theta2  ending longitude angle of the sector
      @param n       number of circles of latitude around the cone
      @param k       number of lines of longitude
   */
   public ConeSector(double r,
                     double h,
                     double theta1, double theta2,
                     int n, int k)
   {
      this(r, h, h, theta1, theta2, n+1, k);
   }


   /**
      Create a part of the cone with its base in the xz-plane,
      a base radius of {@code r}, height {@code  h}, and apex
      on the y-axis.
   <p>
      If {@code 0 < t < h}, then the partial cone is a frustum
      with its base in the xz-plane and the top of the frustum at
      {@code y = t}.
   <p>
      If {@code theta1 > 0} or {@code theta2 < 2pi},then the partial
      cone is a cone over the circular sector from angle {@code theta1}
      to angle {@code theta2}. In other words, the (partial) circles of
      latitude in the model extend from angle {@code theta1} to angle
      {@code theta2}.
   <p>
      The last two parameters determine the number of lines of longitude
      (not counting one edge of any removed sector) and the number of
      (partial) circles of latitude (not counting the top edge of the
      frustum) in the model.
   <p>
      If there are {@code n} circles of latitude in the model (including
      the bottom edge but not the top edge of the frustum), then each
      line of longitude will have {@code n+1} line segments. If there are
      {@code k} lines of longitude (not counting one edge of any removed
      sector), then each (partial) circle of latitude will have {@code k}
      line segments.
   <p>
      There must be at least four lines of longitude and at least
      two circles of latitude.

      @param r       radius of the base in the xz-plane
      @param h       height of the apex on the y-axis
      @param t       top of the frustum of the come
      @param theta1  beginning longitude angle of the sector
      @param theta2  ending longitude angle of the sector
      @param n       number of circles of latitude around the cone
      @param k       number of lines of longitude
   */
   public ConeSector(double r,
                     double h,
                     double t,
                     double theta1, double theta2,
                     int n, int k)
   {
      super("Cone Sector");

      if (n < 2) n = 2;
      if (k < 4) k = 4;
      if (t > h) t = h;

      // Create the cone's geometry.

      double deltaH = h / (n - 1);
      double deltaTheta = (theta2 - theta1) / (k - 1);

      // An array of indexes to be used to create line segments.
      int[][] indexes = new int[n][k];

      // Create all the vertices.
      int index = 0;
      for (int j = 0; j < k; ++j) // choose an angle of longitude
      {
         double c = Math.cos(theta1 + j * deltaTheta);
         double s = Math.sin(theta1 + j * deltaTheta);
         for (int i = 0; i < n; ++i) // choose a circle of latitude
         {
            double slantRadius = r * (1 - i * deltaH / h);
            addVertex( new Vertex(slantRadius * c,
                                  i * deltaH,
                                  slantRadius * s) );
            indexes[i][j] = index++;
         }
      }
      addVertex( new Vertex(0, h, 0) ); // apex
      int apexIndex = index++;
      addVertex( new Vertex(0, 0, 0) ); // bottom center
      int bottomCenterIndex = index++;

      // Create the horizontal (partial) circles of latitude around the cone.
      for (int i = 0; i < n; ++i)
      {
         for (int j = 0; j < k - 1; ++j)
         {
            addLineSegment(new LineSegment(indexes[i][j], indexes[i][j+1]));
         }
      }

      // Create the slanted lines of longitude from the base to the
      // top circle of latitude, and the triangle fan in the base.
      for (int j = 0; j < k; ++j)
      {
         addLineSegment(new LineSegment(bottomCenterIndex, indexes[0][j]));

         for (int i = 0; i < n - 1; ++i)
         {
            addLineSegment(new LineSegment(indexes[i][j], indexes[i+1][j]));
         }
      }
   }
}//ConeSector
