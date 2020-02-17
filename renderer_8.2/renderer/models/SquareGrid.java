/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a square in the xy-plane centered at the origin.
*/
public class SquareGrid extends Model
{
   /**
      Create a square in the xy-plane with corners {@code (±1, ±1, 0)}.
   */
   public SquareGrid( )
   {
      this(1, 0, 0);
   }


   /**
      Create a square in the xy-plane with corners {@code (±1, ±1, 0)} and
      with {@code n} grid lines parallel to each of the x and y axes.

      @param n  number of grid lines parallel to the axes
   */
   public SquareGrid(int n)
   {
      this(1, n, n);
   }


   /**
      Create a square in the xy-plane with corners {@code (±1, ±1, 0)} and
      with {@code n} grid lines parallel to the y-axis and
      with {@code m} grid lines parallel to the x-axis.
   <p>
      If there are {@code n} grid lines parallel to the y-axis, then each
      grid line parallel to the x-axis will have {@code n+1} line segments.
      If there are {@code m} grid lines parallel to the x-axis, then each
      grid line parallel to the y-axis will have {@code m+1} line segments.

      @param n  number of grid lines parallel to the y-axis
      @param m  number of grid lines parallel to the x-axis
   */
   public SquareGrid(int n, int m)
   {
      this(1, n, m);
   }


   /**
      Create a square in the xy-plane with corners {@code (±r, ±r, 0)}.

      @param r  determines the corners of the square
   */
   public SquareGrid(double r)
   {
      this(r, 0, 0);
   }


   /**
      Create a square in the xy-plane with corners {@code (±r, ±r, 0)} and
      with {@code n} grid lines parallel to each of the x and y axes.

      @param r  determines the corners of the square
      @param n  number of grid lines parallel to the axes
   */
   public SquareGrid(double r, int n)
   {
      this(r, n, n);
   }


   /**
      Create a square in the xy-plane with corners {@code (±r, ±r, 0)} and
      with {@code n} grid lines parallel to the y-axis and
      with {@code m} grid lines parallel to the x-axis.
   <p>
      If there are {@code n} grid lines parallel to the y-axis, then each
      grid line parallel to the x-axis will have {@code n+1} line segments.
      If there are {@code m} grid lines parallel to the x-axis, then each
      grid line parallel to the y-axis will have {@code m+1} line segments.

      @param r  determines the corners of the square
      @param n  number of grid lines parallel to the y-axis
      @param m  number of grid lines parallel to the x-axis
   */
   public SquareGrid(double r, int n, int m)
   {
      super("Square Grid");

      if (n < 0) n = 0;
      if (m < 0) m = 0;

      r = Math.abs(r);
      double xStep = (2 * r) / (1 + n);
      double yStep = (2 * r) / (1 + m);

      // Create the square's geometry.

      // An array of vertices to be used to create the line segments.
      Vertex[][] v = new Vertex[m+2][n+2];

      // Create all the vertices.
      for (int i = 0; i <= m+1; ++i)
      {
         for (int j = 0; j <= n+1; ++j)
         {
            v[i][j] = new Vertex(-r + j * xStep, -r + i * yStep, 0);
         }
      }

      // Add all of the vertices to this model.
      for (int i = 0; i < m+2; ++i)
      {
         for (int j = 0; j < n+2; ++j)
         {
            addVertex( v[i][j] );
         }
      }

      // Create the line segments parallel to the x-axis.
      for (int i = 0; i <= m+1; ++i)
      {
         for (int j = 0; j <= n; ++j)
         {  //                                v[i][j]            v[i][j+1]
            addLineSegment(new LineSegment( (i * (n+2)) + j, (i * (n+2)) + (j+1) ));
         }
      }

      // Create the line segments parallel to the y-axis.
      for (int j = 0; j <= n+1; ++j)
      {
         for (int i = 0; i <= m; ++i)
         {  //                                v[i][j]           v[i+1][j]
            addLineSegment(new LineSegment( (i * (n+2)) + j, ((i+1) * (n+2)) + j ));
         }
      }

   }
}//SquareGrid
