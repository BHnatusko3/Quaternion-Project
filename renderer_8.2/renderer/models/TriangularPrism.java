/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a right equilateral triangular prism
   with the y-axis as its central axis.
<p>
   See <a href="https://en.wikipedia.org/wiki/Triangular_prism" target="_top">
                https://en.wikipedia.org/wiki/Triangular_prism</a>
<p>
   See <a href="https://en.wikipedia.org/wiki/Prism_(geometry)" target="_top">
                https://en.wikipedia.org/wiki/Prism_(geometry)</a>
<p>
   Attach to each triangular end of the prism a tetrahedron.
*/
public class TriangularPrism extends Model
{
   /**
      Create a right equilateral triangular prism with a
      regular tetrahedrons attached to each end so that
      the total length runs from -1 to 1 along the y-axis.
   */
   public TriangularPrism( )
   {
      this(0.5, 0.6);
   }


   /**
      Create an equilateral triangular prism that runs
      from {@code -h} to {@code h} along the y-axis, has
      triangle side length {@code s}, and has a regular
      tetrahedron attached to each end.

      @param s  the length of the triangle's sides
      @param h  the body of the prism runs from -h to h along the y-axis
   */
   public TriangularPrism(double s, double h)
   {
      this(s, h, 0);
   }


   /**
      Create an equilateral triangular prism that runs
      from {@code -h} to {@code h} along the y-axis, has
      triangle side length {@code s}, has a regular
      tetrahedron attached to each end, and has {@code n}
      lines of latitude around the body of the prism.

      @param s  the length of the triangle's sides
      @param h  the body of the prism runs from -h to h along the y-axis
      @param n  number of lines of latitude around the body of the prism
   */
   public TriangularPrism(double s, double h, int n)
   {
      this(s/Math.sqrt(3), h, Math.atan(Math.sqrt(2)), n);
   }


   /**
      Create an equilateral triangular prism that runs
      from {@code -h} to {@code h} along the y-axis, with
      the triangle inscribed in a circle of radius {@code r},
      has a tetrahedron attached to each end where the
      face-edge-face angle of each tetrahedron is {@code theta}
      (with theta in radians!), and has {@code n} lines of
      latitude around the body of the prism.
   <p>
      If {@code theta = 0}, then there are no tetrahedrons at the ends of the prism.
   <p>
      If {@code theta = arctan(sqrt(2)) = 54.736°}, then the tetrahedrons are regular.

      @param r      radius of circle in xz-plane that the equilateral triangle is inscribed in
      @param h      the body of the prism runs from -h to h along the y-axis
      @param theta  slant angle of each tetrahedron at the ends of the prism
      @param n      number of lines of latitude around the body of the prism
   */
   public TriangularPrism(double r, double h, double theta, int n)
   {
      this(r, h, r*Math.tan(theta), n, true);
   }


   /**
      Create an equilateral triangular prism that runs
      from {@code -h} to {@code h} along the y-axis, with
      the triangle inscribed in a circle of radius {@code r},
      has a tetrahedron attached to each end where the height
      of each tetrahedron is {@code h2}, and has {@code n} lines
      of latitude around the body of the prism.
   <p>
      So the total height is {@code 2*(h + h2)}.

      @param r   radius of circle in xz-plane that the equilateral triangle is inscribed in
      @param h   the body of the prism runs from h to -h in the y-direction
      @param h2  height of each tetrahedron at the ends of the prism
      @param n   number of lines of latitude around the body of the prism
      @param bothHalves  determines if both halves or only the top half gets created
   */
   public TriangularPrism(double r, double h, double h2, int n, boolean bothHalves)
   {
      super("Triangular Prism");

      if (n < 0) n = 0;

      // Create the prism's geometry.
      double sqrt3 = Math.sqrt(3.0);
      Vertex v0, v1, v2, v3, v4, v5, v6, v7;

      // three vertices around the top
      v0 = new Vertex( r,    h,    0);
      v1 = new Vertex(-r/2,  h,  r*0.5*sqrt3);
      v2 = new Vertex(-r/2,  h, -r*0.5*sqrt3);

      // three vertices around the bottom
      v3 = new Vertex( r,   -h,    0);
      v4 = new Vertex(-r/2, -h,  r*0.5*sqrt3);
      v5 = new Vertex(-r/2, -h, -r*0.5*sqrt3);
      if (! bothHalves)  // cut off the bottom half
      {
         v3 = new Vertex( r,    0,    0);
         v4 = new Vertex(-r/2,  0,  r*0.5*sqrt3);
         v5 = new Vertex(-r/2,  0, -r*0.5*sqrt3);
      }

      v6 = new Vertex(0,  h+h2, 0);  // vertex at the top
      v7 = new Vertex(0, -h-h2, 0);  // vertex at the bottom
      if (! bothHalves)  // cut off the bottom half
      {
         v7 = new Vertex(0, 0, 0);   // vertex at the bottom
      }

      addVertex(v0, v1, v2, v3, v4, v5, v6, v7);
      int index = 8;

      // Create 15 line segments.
      // 3 top faces
      addLineSegment(new LineSegment(6, 0),
                     new LineSegment(6, 1),
                     new LineSegment(6, 2));
      // the top edge
      addLineSegment(new LineSegment(0, 1),
                     new LineSegment(1, 2),
                     new LineSegment(2, 0));
      // three vertical edges
      addLineSegment(new LineSegment(0, 3),
                     new LineSegment(1, 4),
                     new LineSegment(2, 5));
      // the bottom edge
      addLineSegment(new LineSegment(3, 4),
                     new LineSegment(4, 5),
                     new LineSegment(5, 3));
      // 3 bottom faces
      addLineSegment(new LineSegment(7, 3),
                     new LineSegment(7, 4),
                     new LineSegment(7, 5));

      // Create n lines of latitude around the prism.
      if (n > 0)
      {
         double delta_y = 2.0*h/(n+1);
         if (! bothHalves)  // cut off the bottom half
         {
            delta_y = h/(n+1);
         }

         for (int j = 0; j < n; ++j)
         {
            double y = -h + (j+1) * delta_y;
            if (! bothHalves)  // cut off the bottom half
            {
               y = (j+1) * delta_y;
            }
            Vertex v_0 = new Vertex( r,    y,    0);
            Vertex v_1 = new Vertex(-r/2,  y,  r*0.5*sqrt3);
            Vertex v_2 = new Vertex(-r/2,  y, -r*0.5*sqrt3);

            addVertex(v_0, v_1, v_2);

            addLineSegment(new LineSegment(index+0, index+1),
                           new LineSegment(index+1, index+2),
                           new LineSegment(index+2, index+0));
            index += 3;
         }
      }
   }
}//TriangularPrism
