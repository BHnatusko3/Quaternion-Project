/*

*/

package renderer.models;

/**
   Create a wireframe model of a right circular cone with its base
   parallel to the xz-plane and its apex on the positive y-axis.
<p>
   See <a href="https://en.wikipedia.org/wiki/Cone" target="_top">
                https://en.wikipedia.org/wiki/Cone</a>
<p>
   This model can also be used to create right k-sided polygonal pyramids.
<p>
   See <a href="https://en.wikipedia.org/wiki/Pyramid_(geometry)" target="_top">
                https://en.wikipedia.org/wiki/Pyramid_(geometry)</a>

   @see ConeFrustum
*/
public class Cone extends ConeSector
{
   /**
      Create a right circular cone with its base in the xz-plane,
      a base radius of 1, height 1, and apex on the positve y-axis.
   */
   public Cone( )
   {
      this(1, 1, 15, 16);
   }


   /**
      Create a right circular cone with its base in the xz-plane,
      a base radius of {@code r}, height {@code h}, and apex on
      the y-axis.

      @param r  radius of the base in the xz-plane
      @param h  height of the apex on the y-axis
   */
   public Cone(double r, double h)
   {
      this(r, h, 15, 16);
   }


   /**
      Create a right circular cone with its base in the xz-plane,
      a base radius of {@code r}, height {@code h}, and apex on
      the y-axis.
   <p>
      The last two parameters determine the number of lines of longitude
      and the number of circles of latitude in the model.
   <p>
      If there are {@code n} circles of latitude in the model (including
      the bottom edge), then each line of longitude will have {@code n+1}
      line segments. If there are {@code k} lines of longitude, then each
      circle of latitude will have {@code k} line segments.
   <p>
      There must be at least three lines of longitude and at least
      one circle of latitude.
   <p>
      By setting {@code k} to be a small integer, this model can also
      be used to create k-sided polygonal pyramids.

      @param r  radius of the base in the xz-plane
      @param h  height of the apex on the y-axis
      @param n  number of circles of latitude around the cone
      @param k  number of lines of longitude
   */
   public Cone(double r, double h, int n, int k)
   {
      super(r, h, h, 0, 2*Math.PI, n, k);
      name = "Cone";
   }
}//Cone
