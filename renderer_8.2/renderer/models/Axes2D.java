/*

*/

package renderer.models;
import  renderer.scene.*;

import java.awt.Color;

/**
   Create an x and y axis in the xy-plane, along with "tick marks".
*/
public class Axes2D extends Model
{
   /**
      Create an x and y axis from -1 to +1 on each axis.
      The default {@link Color} is black.
   */
   public Axes2D( )
   {
      this(-1, 1, -1, 1, 5, 5);
   }


   /**
      Create an x-axis from {@code xMin} to {@code xMax}
      and a y-axis from {@code yMin} to {@code yMax}.
      The default {@link Color} is black.

      @param xMin    left end point for the x-axis
      @param xMax    right end point for the x-axis
      @param yMin    bottom end point for the y-axis
      @param yMax    top end point for the y-axis
      @param xMarks  number of evenly spaced tick marks on the x-axis
      @param yMarks  number of evenly spaced tick marks on the y-axis
   */
   public Axes2D(double xMin, double xMax, double yMin, double yMax,
                 int xMarks, int yMarks)
   {
      this(xMin, xMax, yMin, yMax, xMarks, yMarks, Color.black);
   }


   /**
      Create an x-axis from {@code xMin} to {@code xMax}
      and a y-axis from {@code yMin} to {@code yMax}.
      Use the given {@link Color} for both axes.

      @param xMin    left end point for the x-axis
      @param xMax    right end point for the x-axis
      @param yMin    bottom end point for the y-axis
      @param yMax    top end point for the y-axis
      @param xMarks  number of evenly spaced tick marks on the x-axis
      @param yMarks  number of evenly spaced tick marks on the y-axis
      @param c       color for both axes
   */
   public Axes2D(double xMin, double xMax, double yMin, double yMax,
                 int xMarks, int yMarks, Color c)
   {
      this(xMin, xMax, yMin, yMax, xMarks, yMarks, c, c);
   }


   /**
      Create an x-axis from {@code xMin} to {@code xMax}
      and a y-axis from {@code yMin} to {@code yMax}.
      Use the given {@link Color} for each axis.

      @param xMin    left end point for the x-axis
      @param xMax    right end point for the x-axis
      @param yMin    bottom end point for the y-axis
      @param yMax    top end point for the y-axis
      @param xMarks  number of evenly spaced tick marks on the x-axis
      @param yMarks  number of evenly spaced tick marks on the y-axis
      @param cX      color for the x-axis
      @param cY      color for the y-axis
   */
   public Axes2D(double xMin, double xMax, double yMin, double yMax,
                 int xMarks, int yMarks, Color cX, Color cY)
   {
      this(xMin, xMax, yMin, yMax, xMarks, yMarks, cX, cY, 0.0);
   }


   /**
      Create an x-axis from {@code xMin} to {@code xMax}
      and a y-axis from {@code yMin} to {@code yMax}.
      Use the given {@link Color} for each axis.
   <p>
      The {@code z} parameter is so that we can put the axis just above
      or just below the xy-plane (say {@code z=0.01} or {@code z=-0.01}).
      This way, the axes can be just in front of or just behind whatever
      is being drawn in the xy-plane.

      @param xMin    left end point for the x-axis
      @param xMax    right end point for the x-axis
      @param yMin    bottom end point for the y-axis
      @param yMax    top end point for the y-axis
      @param xMarks  number of evenly spaced tick marks on the x-axis
      @param yMarks  number of evenly spaced tick marks on the y-axis
      @param cX      color for the x-axis
      @param cY      color for the y-axis
      @param z       offset of the axes away from the xy-plane
   */
   public Axes2D(double xMin, double xMax, double yMin, double yMax,
                 int xMarks, int yMarks, Color cX, Color cY, double z)
   {
      super("Axes 2D");

      // Add the two colors to the model.
      addColor(cX, cY);

      // x-axis
      addVertex(new Vertex(xMin, 0, z),
                new Vertex(xMax, 0, z));
      addLineSegment(new LineSegment(0, 1, 0));  // use color cX

      // y-axis
      addVertex(new Vertex(0, yMin, z),
                new Vertex(0, yMax, z));
      addLineSegment(new LineSegment(2, 3, 1));  // use color cY

      int index = 4;

      // Put evenly spaced tick marks on the x-axis.
      double xDelta = (xMax - xMin)/xMarks;
      double yDelta = (yMax - yMin)/50;
      for (double x = xMin; x <= xMax; x += xDelta)
      {
         addVertex(new Vertex(x,  yDelta/2, z),
                   new Vertex(x, -yDelta/2, z));
         addLineSegment(new LineSegment(index+0, index+1, 0));  // use color cX
         index += 2;
      }

      // Put evenly spaced tick marks on the y-axis.
      yDelta = (yMax - yMin)/yMarks;
      xDelta = (xMax - xMin)/50;
      for (double y = yMin; y <= yMax; y += yDelta)
      {
         addVertex(new Vertex( xDelta/2, y, z),
                   new Vertex(-xDelta/2, y, z));
         addLineSegment(new LineSegment(index+0, index+1, 1));  // use color cY
         index += 2;
      }
   }
}//Axes2D
