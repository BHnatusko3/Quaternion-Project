/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a frustum of a right circular cone
   with its base in the xz-plane.
<p>
   See <a href="https://en.wikipedia.org/wiki/Frustum" target="_top">
                https://en.wikipedia.org/wiki/Frustum</a>

   @see Cone
   @see ConeSector
*/
public class ConeFrustum extends Model
{
   /**
      Create a frustum of a right circular cone with its base in the
      xz-plane, a base radius of 1, top radius of 1/2, and height 1/2.
   */
   public ConeFrustum( )
   {
      this(1.0, 0.5, 0.5, 7, 16);
   }


   /**
      Create a frustum of a right circular cone with its base in the
      xz-plane, a base radius of {@code r}, top of the frustum at
      height {@code h}, and with the cone's apex on the y-axis at
      height {@code a}.
   <p>
      There must be at least three lines of longitude and at least
      two circles of latitude.

      @param n  number of circles of latitude
      @param k  number of lines of longitude
      @param r  radius of the base in the xz-plane
      @param h  height of the frustum
      @param a  height of the apex of the cone
   */
   public ConeFrustum(int n, int k, double r, double h, double a)
   {
      this(r, (1 - h/a)*r, h, n, k);
   }


   /**
      Create a frustum of a right circular cone with its base in the
      xz-plane, a base radius of {@code r1}, top radius of {@code r2},
      and height {@code h}.
   <p>
      This model works with either {@code r1 > r2} or {@code r1 < r2}.
      In other words, the frustum can have its "apex" either above or
      below the xz-plane.
   <p>
      There must be at least three lines of longitude and at least
      two circles of latitude.

      @param r1  radius of the base of the frustum
      @param r2  radius of the top of the frustum
      @param h   height of the frustum
      @param n   number of circles of latitude
      @param k   number of lines of longitude
   */
   public ConeFrustum(double r1, double r2, double h, int n, int k)
   {
      super("Cone Frustum");

      if (n < 2) n = 2;
      if (k < 3) k = 3;

      // Create the frustum's geometry.

      double deltaTheta = (2 * Math.PI) / k;

      // An array of indexes to be used to create line segments.
      int[][] indexes = new int[n][k];

      // Create all the vertices.
      int index = 0;
      for (int j = 0; j < k; ++j) // choose an angle of longitude
      {
         double c = Math.cos(j * deltaTheta);
         double s = Math.sin(j * deltaTheta);
         for (int i = 0; i < n; ++i) // choose a circle of latitude
         {
            double slantRadius = (i/(double)(n-1)) * r1 + ((n-1-i)/(double)(n-1)) * r2;
            addVertex( new Vertex(slantRadius * c,
                                  h - (i*h)/(n-1),
                                  slantRadius * s) );
            indexes[i][j] = index++;
         }
      }
      addVertex( new Vertex(0, h, 0) );  // top center
      int topCenterIndex = index++;
      addVertex( new Vertex(0, 0, 0) );  // bottom center
      int bottomCenterIndex = index++;

      // Create all the horizontal circles of latitude around the frustum wall.
      for (int i = 0; i < n; ++i)
      {
         for (int j = 0; j < k-1; ++j)
         {
            addLineSegment(new LineSegment(indexes[i][j], indexes[i][j+1]));
         }
         // close the circle
         addLineSegment(new LineSegment(indexes[i][k-1], indexes[i][0]));
      }

      // Create the vertical half-trapazoids of longitude from north to south pole.
      for (int j = 0; j < k; ++j)
      {
         // Create the triangle fan at the top.
         addLineSegment(new LineSegment(topCenterIndex, indexes[0][j]));
         // Create the slant lines from the top to the base.
         addLineSegment(new LineSegment(indexes[0][j], indexes[n-1][j]));
         // Create the triangle fan at the base.
         addLineSegment(new LineSegment(indexes[n-1][j], bottomCenterIndex));
      }
   }
}//ConeFrustum
