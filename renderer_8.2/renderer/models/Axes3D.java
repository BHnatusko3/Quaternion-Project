/*

*/

package renderer.models;
import  renderer.scene.*;

import java.awt.Color;

/**
   Create a positive x, y, and z axis in 3-dimensional space.
*/
public class Axes3D extends Model
{
   /**
      Create a positive x, y, and z axis
      with one unit length for each axis.
      The default {@link Color} is black.
   */
   public Axes3D( )
   {
      this(1.0, 1.0, 1.0);
   }


   /**
      Create a positive x, y, and z axis
      with the given length for each axis.
      The default {@link Color} is black.

      @param xMax  length of the x-axis
      @param yMax  length of the y-axis
      @param zMax  length of the z-axis
   */
   public Axes3D(double xMax, double yMax, double zMax)
   {
      this(xMax, yMax, zMax, Color.black);
   }


   /**
      Create a positive x, y, and z axis
      with the given length for each axis.
      Use the given {@link Color} for all three axes.

      @param xMax  length of the x-axis
      @param yMax  length of the y-axis
      @param zMax  length of the z-axis
      @param c     color for all three axes
   */
   public Axes3D(double xMax, double yMax, double zMax, Color c)
   {
      this(xMax, yMax, zMax, c, c, c);
   }


   /**
      Create a positive x, y, and z axis
      with the given length for each axis.
      Use the given {@link Color} for each axis.

      @param xMax  length of the x-axis
      @param yMax  length of the y-axis
      @param zMax  length of the z-axis
      @param cX    color for the x-axis
      @param cY    color for the y-axis
      @param cZ    color for the z-axis
   */
   public Axes3D(double xMax, double yMax, double zMax,
                 Color cX,    Color cY,    Color cZ)
   {
      this(0.0, xMax, 0.0, yMax, 0.0, zMax, cX, cY, cZ);
   }


   /**
      Create an x, y, and z axis with the
      given endpoints for each axis.
      The default {@link Color} is black.

      @param xMin  left endpoint of the x-axis
      @param xMax  right endpoint of the x-axis
      @param yMin  bottom endpoint of the y-axis
      @param yMax  top endpoint of the y-axis
      @param zMin  back endpoint of the z-axis
      @param zMax  front endpoint of the z-axis
   */
   public Axes3D(double xMin, double xMax,
                 double yMin, double yMax,
                 double zMin, double zMax)
   {
      this(xMin, xMax, yMin, yMax, zMin, zMax, Color.black);
   }


   /**
      Create an x, y, and z axis with the
      given endpoints for each axis.
      Use the given {@link Color} for all three axes.

      @param xMin  left endpoint of the x-axis
      @param xMax  right endpoint of the x-axis
      @param yMin  bottom endpoint of the y-axis
      @param yMax  top endpoint of the y-axis
      @param zMin  back endpoint of the z-axis
      @param zMax  front endpoint of the z-axis
      @param c     color for all three axes
   */
   public Axes3D(double xMin, double xMax,
                 double yMin, double yMax,
                 double zMin, double zMax,
                 Color c)
   {
      this(xMin, xMax, yMin, yMax, zMin, zMax, c, c, c);
   }


   /**
      Create an x, y, and z axis with the
      given endpoints for each axis.
      Use the given {@link Color} for each axis.

      @param xMin  left endpoint of the x-axis
      @param xMax  right endpoint of the x-axis
      @param yMin  bottom endpoint of the y-axis
      @param yMax  top endpoint of the y-axis
      @param zMin  back endpoint of the z-axis
      @param zMax  front endpoint of the z-axis
      @param cX    color for the x-axis
      @param cY    color for the y-axis
      @param cZ    color for the z-axis
   */
   public Axes3D(double xMin, double xMax,
                 double yMin, double yMax,
                 double zMin, double zMax,
                 Color cX, Color cY, Color cZ)
   {
      super("Axes 3D");

      Vertex x0 = new Vertex(xMin, 0,    0);
      Vertex x1 = new Vertex(xMax, 0,    0);
      Vertex y0 = new Vertex( 0,  yMin,  0);
      Vertex y1 = new Vertex( 0,  yMax,  0);
      Vertex z0 = new Vertex( 0,   0,   zMin);
      Vertex z1 = new Vertex( 0,   0,   zMax);

      // Add the vertices to the model.
      addVertex(x0, x1, y0, y1, z0, z1);

      // Add the colors to the model.
      addColor(cX, cY, cZ);

      // Create 3 line segments.
      addLineSegment(new LineSegment(0, 1, 0));  // use color cX
      addLineSegment(new LineSegment(2, 3, 1));  // use color cY
      addLineSegment(new LineSegment(4, 5, 2));  // use color cZ
   }
}//Axes3D
