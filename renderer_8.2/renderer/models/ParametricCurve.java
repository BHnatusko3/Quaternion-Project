/*

*/

package renderer.models;
import  renderer.scene.*;

import java.util.function.DoubleFunction;
import java.util.function.ToDoubleFunction;    // could use this instead
import java.util.function.DoubleUnaryOperator; // could use this instead
//https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html

/**
   Create a wireframe model of a parametric curve in space.
<p>
   See <a href="https://en.wikipedia.org/wiki/Parametric_equation" target="_top">
                https://en.wikipedia.org/wiki/Parametric_equation</a>

   @see ParametricSurface
*/
public class ParametricCurve extends Model
{
   /**
      Create a trefoil knot as a parametric curve in space.
   <p>
      See <a href="https://en.wikipedia.org/wiki/Trefoil_knot#Descriptions" target="_top">
                   https://en.wikipedia.org/wiki/Trefoil_knot#Descriptions</a>
   */
   public ParametricCurve()
   {
      this(t ->  0.5*Math.sin(t) + Math.sin(2*t),
           t ->  0.5*Math.cos(t) - Math.cos(2*t),
           t -> -0.5*Math.sin(3*t),
           0, 2*Math.PI, 60);
   }


   /**
      Create a parametric curve in the xy-plane,
      <pre>{@code
         x = x(t)
         y = y(t)
      }</pre>
      with the parameter {@code  t} having the given parameter
      range and the given number of line segments.

      @param x   component function in the x-direction
      @param y   component function in the y-direction
      @param t1  beginning value of parameter range
      @param t2  ending value of parameter range
      @param n   number of line segments in the curve
   */
   public ParametricCurve(DoubleFunction<Double> x,
                          DoubleFunction<Double> y,
                          double t1, double t2, int n)
   {
      this(x, y, t->0.0, t1, t2, n);
   }


   /**
      Create a parametric curve in space,
      <pre>{@code
         x = x(t)
         y = y(t)
         z = z(t)
      }</pre>
      with the parameter {@code t} having the given parameter
      range and the given number of line segments.

      @param x   component function in the x-direction
      @param y   component function in the y-direction
      @param z   component function in the z-direction
      @param t1  beginning value of parameter range
      @param t2  ending value of parameter range
      @param n   number of line segments in the curve
   */
   public ParametricCurve(DoubleFunction<Double> x,
                          DoubleFunction<Double> y,
                          DoubleFunction<Double> z,
                          double t1, double t2, int n)
   {
      super("Parametric Curve");

      if (n < 1) n = 1;

      // Create the curve's geometry.

      double deltaT = (t2 - t1) / n;

      // An array of vertices to be used to create the line segments.
      Vertex[] v = new Vertex[n+1];

      // Create all the vertices.
      for (int i = 0; i < n + 1; i++)
      {
         v[i] = new Vertex( x.apply(t1 + i * deltaT),
                            y.apply(t1 + i * deltaT),
                            z.apply(t1 + i * deltaT) );
      }

      addVertex(v);

      for (int i = 0; i < n; i++)
      {
         addLineSegment(new LineSegment(i, i+1));
      }
   }
}//ParametricCurve
