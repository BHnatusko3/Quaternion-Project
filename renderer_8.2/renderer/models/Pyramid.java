/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a right square pyramid with its
   base in the xz-plane and its apex on the positive y-axis.
<p>
   See <a href="https://en.wikipedia.org/wiki/Pyramid_(geometry)" target="_top">
                https://en.wikipedia.org/wiki/Pyramid_(geometry)</a>

   @see PyramidFrustum
*/
public class Pyramid extends Model
{
   /**
      Create a right square pyramid with its base in the xz-plane,
      a base side length of 2, height 1, and apex on the positive y-axis.
   */
   public Pyramid( )
   {
      this(2.0, 1.0, 15, 4);
   }


   /**
      Create a right square pyramid with its base in the xz-plane,
      a base length of {@code s}, height {@code h}, and apex on the
      positive y-axis.

      @param s  side length of the base in the xz-plane
      @param h  height of the apex on the y-axis
   */
   public Pyramid(double s, double h)
   {
      super();

      // Create the pyramid's geometry.
      Vertex v0 = new Vertex(-s/2, 0, -s/2);  // base
      Vertex v1 = new Vertex(-s/2, 0,  s/2);
      Vertex v2 = new Vertex( s/2, 0,  s/2);
      Vertex v3 = new Vertex( s/2, 0, -s/2);
      Vertex v4 = new Vertex(  0,  h,   0);   // apex

      addVertex(v0, v1, v2, v3, v4);

      // Create 8 line segments for 5 faces.
      addLineSegment(new LineSegment(0, 1));  // base
      addLineSegment(new LineSegment(1, 2));
      addLineSegment(new LineSegment(2, 3));
      addLineSegment(new LineSegment(3, 0));
      addLineSegment(new LineSegment(4, 0));  // 4 sides
      addLineSegment(new LineSegment(4, 1));
      addLineSegment(new LineSegment(4, 2));
      addLineSegment(new LineSegment(4, 3));
   }


   /**
      Create a right square pyramid with its base in the xz-plane,
      a base length of {@code s}, height {@code h}, and apex on the
      positive y-axis.

      @param s  side length of the base in the xz-plane
      @param h  height of the apex on the y-axis
      @param n  number of lines of latitude around the body of the pyramid
      @param k  number of triangles in the triangle fan at the top of each side
   */
   public Pyramid(double s, double h, int n, int k)
   {
      this(s, h, n, k, false);
   }


   /**
      Create a right square pyramid with its base in the xz-plane,
      a base length of {@code s}, height {@code h}, and apex on the
      positive y-axis.
   <p>
      The last parameter provides a choice between having a square
      grid of lines or a line fan in the base of the pyramid.

      @param s  side length of the base in the xz-plane
      @param h  height of the apex on the y-axis
      @param n  number of lines of latitude around the body of the pyramid
      @param k  number of triangles in the triangle fan at the top of each side
      @param grid  choose either a square grid or a line fan in the base
   */
   public Pyramid(double s, double h, int n, int k, boolean grid)
   {
      super("Pyramid");

      if (n < 1) n = 1;
      if (k < 1) k = 1;

      // Create the pyramid's geometry.
      addVertex(new Vertex(0, h, 0));
      int apexIndex = 0;
      int index = 1;

      // Create all the lines of "longitude" from the apex, down
      // to the base, across the base, and then back up to the apex.
      s = s/2;
      double delta = (2 * s) / k;
      // lines of "longitude" perpendicular to the x-axis
      for (int j = 0; j < k + 1; ++j)
      {
         double d = j * delta;
         if (grid)
         {
            addVertex(new Vertex(-s+d, 0, -s),
                      new Vertex(-s+d, 0,  s));
         }
         else // a fan in the base
         {
            addVertex(new Vertex(-s+d, 0, -s),
                      new Vertex( s-d, 0,  s));
         }
         addLineSegment(new LineSegment(apexIndex, index+0),
                        new LineSegment(index+0, index+1),
                        new LineSegment(index+1, apexIndex));
         index += 2;
      }
      // lines of "longitude" perpendicular to the z-axis
      for (int j = 1; j < k; ++j)
      {
         double d = j * delta;
         if (grid)
         {
            addVertex(new Vertex( s, 0, -s+d),
                      new Vertex(-s, 0, -s+d));
         }
         else // a fan in the base
         {
            addVertex(new Vertex( s, 0, -s+d),
                      new Vertex(-s, 0,  s-d));
         }
         addLineSegment(new LineSegment(apexIndex, index+0),
                        new LineSegment(index+0, index+1),
                        new LineSegment(index+1, apexIndex));
         index += 2;
      }
      // Create all the lines of "latitude" around the pyramid, starting
      // from the base and working upwards.
      double deltaH = h / n;
      double deltaS = s / n;
      for (int i = 0; i < n; ++i)
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
}//Pyramid
