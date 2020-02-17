/*

*/

package renderer.models;

import java.util.function.DoubleFunction;
import java.util.function.ToDoubleFunction;    // could use this instead
import java.util.function.DoubleUnaryOperator; // could use this instead
//https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html

/**
   Create a wireframe model of a surface of revolution around the y-axis.
<p>
   See <a href="https://en.wikipedia.org/wiki/Surface_of_revolution#Rotating_a_function" target="_top">
                https://en.wikipedia.org/wiki/Surface_of_revolution#Rotating_a_function</a>

   @see ParametricSurface
*/
public class SurfaceOfRevolution extends ParametricSurface
{
   /**
      Create a surface of revolution around the y-axis
      based on a cosine function.
   */
   public SurfaceOfRevolution()
   {
      this(t -> 0.5 * (1 + Math.cos(Math.PI * t)),
           -1.0, 1.0, 49, 49);
   }


   /**
      Create a surface of revolution around the y-axis
      with the given radial function, {@code r = r(y)},
      the given parameter range along the y-axis, and
      the given number of circles of latitude.

      @param r   radius function
      @param y1  beginning value along the y-axis
      @param y2  ending value along the y-axis
      @param n   number of circles of latitude
      @param k   number of lines of longitude
   */
   public SurfaceOfRevolution(DoubleFunction<Double> r,
                              double y1, double y2,
                              int n, int k)
   {
      this(r, y1, y2, 0, 2*Math.PI, n, k);
   }


   /**
      Create a surface of revolution around the y-axis with
      the given radial function, {@code r = r(y)}, the given
      angular range for the sector of revolution, the given
      parameter range along the y-axis, and the given number
      of circles of latitude.

      @param r       radius function
      @param y1      beginning value along the y-axis
      @param y2      ending value along the y-axis
      @param theta1  beginning value of angular parameter range
      @param theta2  ending value of angular parameter range
      @param n       number of circles of latitude
      @param k       number of lines of longitude
   */
   public SurfaceOfRevolution(DoubleFunction<Double> r,
                              double y1, double y2,
                              double theta1, double theta2,
                              int n, int k)
   {
      super( (y,t) -> r.apply(y) * Math.cos(t),
             (y,t) -> y,
             (y,t) -> r.apply(y) * Math.sin(t),
             y1, y2,
             theta1, theta2,
             n, k );
   }


   /**
      Create a surface of revolution around the y-axis
      of the given radial parametric curve.

      @param x   first component function of the parametric curve
      @param y   second component function of the parametric curve
      @param s1  beginning parameter value
      @param s2  ending parameter value
      @param n   number of circles of latitude
      @param k   number of lines of longitude
   */
   public SurfaceOfRevolution(DoubleFunction<Double> x,
                              DoubleFunction<Double> y,
                              double s1, double s2,
                              int n, int k)
   {
      this(x, y, s1, s2, 0, 2*Math.PI, n, k );
   }


   /**
      Create a surface of revolution around the y-axis
      of the given radial parametric curve and the given
      angular range for the sector of revolution.

      @param x       first component function of the parametric curve
      @param y       second component function of the parametric curve
      @param s1      beginning parameter value
      @param s2      ending parameter value
      @param theta1  beginning value of angular parameter range
      @param theta2  ending value of angular parameter range
      @param n       number of circles of latitude
      @param k       number of lines of longitude
   */
   public SurfaceOfRevolution(DoubleFunction<Double> x,
                              DoubleFunction<Double> y,
                              double s1, double s2,
                              double theta1, double theta2,
                              int n, int k)
   {
      super( (s,t) -> x.apply(s) * Math.cos(t),
             (s,t) -> y.apply(s),
             (s,t) -> x.apply(s) * Math.sin(t),
             s1, s2,
             theta1, theta2,
             n, k );
      name = "SurfaceOfRevolution";
   }
}//Surface of Revolution
