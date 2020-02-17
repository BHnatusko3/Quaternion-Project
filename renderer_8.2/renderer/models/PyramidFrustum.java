/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a frustum of a right square pyramid
   with its base in the xz-plane.
<p>
   See <a href="https://en.wikipedia.org/wiki/Frustum" target="_top">
                https://en.wikipedia.org/wiki/Frustum</a>

   @see Pyramid
*/
public class PyramidFrustum extends Model
{
   /**
      Create a frustum of a right square pyramid with its base in the
      xz-plane, a base side length of 2, top side length of 1, and height 1/2.
   */
   public PyramidFrustum( )
   {
      this(2.0, 1.0, 0.5, 7, 4);
   }


   /**
      Create a frustum of a right square pyramid with its base in the
      xz-plane, a base side length of {@code s1}, top side length of
      {@code s2}, and height {@code h}.
   <p>
      This model works with either {@code s1 > s2} or {@code s1 < s2}.
      In other words, the frustum can have its "apex" either above or
      below the xz-plane.

      @param s1  side length of the base of the frustum
      @param s2  side length of the top of the frustum
      @param h   height of the frustum
   */
   public PyramidFrustum(double s1, double s2, double h)
   {
      super();

      // Create the pyramid's geometry.
      Vertex v0 = new Vertex(-s1/2, 0, -s1/2);  // base
      Vertex v1 = new Vertex(-s1/2, 0,  s1/2);
      Vertex v2 = new Vertex( s1/2, 0,  s1/2);
      Vertex v3 = new Vertex( s1/2, 0, -s1/2);
      Vertex v4 = new Vertex(-s2/2, h, -s2/2);  // top
      Vertex v5 = new Vertex(-s2/2, h,  s2/2);
      Vertex v6 = new Vertex( s2/2, h,  s2/2);
      Vertex v7 = new Vertex( s2/2, h, -s2/2);
      addVertex(v0, v1, v2, v3, v4, v5, v6, v7);

      // Create 6 faces.
      addLineSegment(new LineSegment(0, 1)); // base
      addLineSegment(new LineSegment(1, 2));
      addLineSegment(new LineSegment(2, 3));
      addLineSegment(new LineSegment(3, 0));
      addLineSegment(new LineSegment(0, 4)); // 4 sides
      addLineSegment(new LineSegment(1, 5));
      addLineSegment(new LineSegment(2, 6));
      addLineSegment(new LineSegment(3, 7));
      addLineSegment(new LineSegment(4, 5)); // top
      addLineSegment(new LineSegment(5, 6));
      addLineSegment(new LineSegment(6, 7));
      addLineSegment(new LineSegment(7, 4));
   }


   /**
      Create a frustum of a right square pyramid with its base in the
      xz-plane, a base side length of {@code s}, top of the frustum at
      height {@code h}, and with the pyramid's apex at on the y-axis at
      height {@code a}.

      @param n  number of lines of latitude
      @param k  number of lines of longitude
      @param s  side length of the base of the frustum
      @param h  height of the frustum
      @param a  height of the apex of the pyramid
   */
   public PyramidFrustum(int n, int k, double s, double h, double a)
   {
      this(s, (1 - h/a)*s, h, n, k);
   }


   /**
      Create a frustum of a right square pyramid with its base in the
      xz-plane, a base side length of {@code s1}, top side length of
      {@code s2}, and height {@code h}.
   <p>
      This model works with either {@code s1 > s2} or {@code s1 < s2}.
      In other words, the frustum can have its "apex" either above or
      below the xz-plane.

      @param s1  side length of the base of the frustum
      @param s2  side length of the top of the frustum
      @param h   height of the frustum
      @param n   number of lines of latitude
      @param k   number of lines of longitude
   */
   public PyramidFrustum(double s1, double s2, double h, int n, int k)
   {
      super("Pyramid Frustum");

      if (n < 0) n = 0;
      if (k < 1) k = 1;

      // Create the frustum's geometry.
      int index = 0;

      // Create all the lines of longitude from the top, down to the base,
      // across the base, then back up to the top, and across the top.
      s1 = s1/2;
      s2 = s2/2;
      double delta1 = (2 * s1) / k;
      double delta2 = (2 * s2) / k;
      // lines of "longitude" perpendicular to the x-axis
      for (int j = 0; j <= k; ++j)
      {
         double d1 = j * delta1;
         double d2 = j * delta2;
         addVertex(new Vertex(-s2+d2, h, -s2),
                   new Vertex(-s1+d1, 0, -s1),
                   new Vertex(-s1+d1, 0,  s1),
                   new Vertex(-s2+d2, h,  s2));
         addLineSegment(new LineSegment(index+0, index+1),
                        new LineSegment(index+1, index+2),
                        new LineSegment(index+2, index+3),
                        new LineSegment(index+3, index+0));
         index += 4;
      }
      // lines of "longitude" perpendicular to the z-axis
      for (int j = 0; j <= k; ++j)
      {
         double d1 = j * delta1;
         double d2 = j * delta2;
         addVertex(new Vertex( s2, h, -s2+d2),
                   new Vertex( s1, 0, -s1+d1),
                   new Vertex(-s1, 0, -s1+d1),
                   new Vertex(-s2, h, -s2+d2));
         addLineSegment(new LineSegment(index+0, index+1),
                        new LineSegment(index+1, index+2),
                        new LineSegment(index+2, index+3),
                        new LineSegment(index+3, index+0));
         index += 4;
      }
      // Create all the lines of "latitude" around the pyramid, starting
      // from the base and working up to the top.
      double deltaH = h / (n + 1);
      double deltaS = (s1 - s2) / (n + 1);
      double s = s1;
      for (int i = 0; i <= n; ++i)
      {
         h = i * deltaH;
         addVertex(new Vertex( s, h,  s),
                   new Vertex( s, h, -s),
                   new Vertex(-s, h, -s),
                   new Vertex(-s, h,  s));
         addLineSegment(new LineSegment(index+0, index+1),
                        new LineSegment(index+1, index+2),
                        new LineSegment(index+2, index+3),
                        new LineSegment(index+3, index+0));
         s -= deltaS;
         index += 4;
      }
   }
}//PyramidFrustum
