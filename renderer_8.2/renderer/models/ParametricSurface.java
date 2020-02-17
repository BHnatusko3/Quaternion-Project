/*

*/

package renderer.models;
import  renderer.scene.*;

import java.util.function.DoubleBinaryOperator;
import java.util.function.ToDoubleBiFunction; // could use this instead
//https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html

/**
   Create a wireframe model of a parametric surface in space.
<p>
   See <a href="https://en.wikipedia.org/wiki/Parametric_surface" target="_top">
                https://en.wikipedia.org/wiki/Parametric_surface</a>

   @see ParametricCurve
*/
public class ParametricSurface extends Model
{
   /**
      Create a graph of the function with the following formula,
      <pre>{@code
          f(x,z) = sin(PI*x) * sin(PI*z)
      }</pre>
      as a parametric surface.
   */
   public ParametricSurface()
   {
      this((s,t) -> Math.sin(Math.PI*s) * Math.sin(Math.PI*t),
           -1.0, 1.0, -1.0, 1.0,
            49, 49);
   }


   /**
      Create a graph of a function of two variables
      {@code y = f(x, z)} as a parametric surface with
      the given parameter ranges in the {@code x} and
      {@code z} directions.

      @param f   function of x and z
      @param x1  beginning value of x-parameter range
      @param x2  ending value of x-parameter range
      @param z1  beginning value of y-parameter range
      @param z2  ending value of z-parameter range
      @param n   number of mesh lines in x-range
      @param k   number of mesh lines in y-range
   */
   public ParametricSurface(DoubleBinaryOperator f,
                            double x1, double x2,
                            double z1, double z2,
                            int n, int k)
   {
      this((x,z) -> x, f, (x,z) -> z, x1, x2, z1, z2, n, k);
   }


   /**
      Create a parametric surface in space,
      <pre>{@code
         x = x(s,t)
         y = y(s,t)
         z = z(s,t)
      }</pre>
      with the parameters {@code s} and {@code t} having
      the given parameter ranges and the given number of
      mesh lines in each parametric direction.

      @param x   component function in the x-direction
      @param y   component function in the y-direction
      @param z   component function in the z-direction
      @param s1  beginning value of first parameter range
      @param s2  ending value of first parameter range
      @param t1  beginning value of second parameter range
      @param t2  ending value of second parameter range
      @param n   number of mesh lines in first range
      @param k   number of mesh lines in second range
   */
   public ParametricSurface(DoubleBinaryOperator x,
                            DoubleBinaryOperator y,
                            DoubleBinaryOperator z,
                            double s1, double s2,
                            double t1, double t2,
                            int n, int k)
   {
      super("Parametric Surface");

      if (n < 2) n = 2;
      if (k < 2) k = 2;

      // Create the curve's geometry.

      double deltaS = (s2 - s1) / (n - 1);
      double deltaT = (t2 - t1) / (k - 1);

      // An array of vertices to be used to create the line segments.
      Vertex[][] v = new Vertex[n][k];

      // Create all the vertices.
      for (int i = 0; i < n; ++i)
      {
         for (int j = 0; j < k; ++j)
         {
            v[i][j] = new Vertex(x.applyAsDouble(s1 + i*deltaS, t1 + j*deltaT),
                                 y.applyAsDouble(s1 + i*deltaS, t1 + j*deltaT),
                                 z.applyAsDouble(s1 + i*deltaS, t1 + j*deltaT));
         }
      }

      // Add all of the vertices to this model.
      for (int i = 0; i < n; ++i)
      {
         for (int j = 0; j < k; ++j)
         {
            addVertex( v[i][j] );
         }
      }

      // Create the horizontal line segments.
      for (int i = 0; i < n; ++i)
      {
         for (int j = 0; j < k - 1; ++j)
         {
            addLineSegment(new LineSegment( (i * n) + j, (i * n) + (j+1) ));
         }
      }

      // Create the vertical line segments.
      for (int j = 0; j < k; ++j)
      {
         for (int i = 0; i < n - 1; ++i)
         {
            addLineSegment(new LineSegment( (i * n) + j, ((i+1) * n) + j ));
         }
      }
   }
}//ParametricSurface
