/*

*/

package renderer.pipeline;
import  renderer.scene.*;
import  renderer.framebuffer.*;

import java.awt.Color;

/**
   Rasterize a clipped {@link LineSegment} into shaded pixels
   in a {@link FrameBuffer}'s viewport and (optionally)
   anti-alias and gamma-encode the line at the same time.
<p>
   This pipeline stage takes a clipped {@link LineSegment}
   with vertices in the {@link Camera}'s view rectangle and
   rasterizezs the line segment into pixels in the
   {@link FrameBuffer}'s viewport. This rasterizer will linearly
   interpolate color from the line segment's two endpoints to
   each rasterized pixel in the line segment.
<p>
   This rasterization algorithm is based on
<pre>
     "Fundamentals of Computer Graphics", 3rd Edition,
      by Peter Shirley, pages 163-165.
</pre>
<p>
   This is a fairly simple anti-aliasing algorithm.
*/
public class RasterizeAntialias
{
   public static boolean debug = false;
   public static boolean doAntialiasing = false;
   public static boolean doGamma = true;

   /**
      Rasterize a clipped {@link LineSegment} into anti-aliased, shaded pixels
      in the {@link FrameBuffer.Viewport}.

      @param model  {@link Model} that the {@link LineSegment} {@code ls} comes from
      @param ls     {@link LineSegment} to rasterize into the {@link FrameBuffer.Viewport}
      @param vp     {@link FrameBuffer.Viewport} to hold rasterized pixels
   */
   public static void rasterize(Model model, LineSegment ls, FrameBuffer.Viewport vp)
   {
      boolean debug = RasterizeAntialias.debug && (Pipeline.debug || model.debug);

      // Get the viewport's background color.
      Color bg = vp.bgColorVP;

      // Make local copies of several values.
      int w = vp.getWidth();
      int h = vp.getHeight();

      Vertex v0 = model.vertexList.get( ls.vIndex[0] );
      Vertex v1 = model.vertexList.get( ls.vIndex[1] );

      float[] c0 = model.colorList.get( ls.cIndex[0] ).getRGBComponents(null);
      float[] c1 = model.colorList.get( ls.cIndex[1] ).getRGBComponents(null);
      float r0 = c0[0],  g0 = c0[1],  b0 = c0[2];
      float r1 = c1[0],  g1 = c1[1],  b1 = c1[2];

      // Transform each vertex to the "pixel plane" coordinate system.
      double x0 = 0.5 + w/2.001 * (v0.x + 1); // x_pp = 0.5 + w/2 * (x_p+1)
      double y0 = 0.5 + h/2.001 * (v0.y + 1); // y_pp = 0.5 + h/2 * (y_p+1)
      double x1 = 0.5 + w/2.001 * (v1.x + 1);
      double y1 = 0.5 + h/2.001 * (v1.y + 1);
      // NOTE: Notice the 2.001 fudge factor in the last two equations.
      // This is explained on page 142 of
      //    "Jim Blinn's Corner: A Trip Down The Graphics Pipeline"
      //     by Jim Blinn, 1996, Morgan Kaufmann Publishers.

      // Round each vertex to the nearest logical pixel.
      // This makes the algorithm a lot simpler, but it can
      // cause a slight, but noticeable, shift of the line segment.
      x0 = Math.round(x0);
      y0 = Math.round(y0);
      x1 = Math.round(x1);
      y1 = Math.round(y1);

      // Rasterize a degenerate line segment (a line segment
      // that projected onto a point) as a single pixel.
      if ( (x0 == x1) && (y0 == y1) )
      {
         if (debug) logPixel((int)x0, y0, r0, g0, b0, w, h);

         // We don't know which endpoint of the line segment
         // is in front, so just pick v0.
         int x0_vp = (int)x0 - 1;  // viewport coordinate
         int y0_vp = h - (int)y0;  // viewport coordinate
         vp.setPixelVP(x0_vp, y0_vp, new Color(r0, g0, b0));

         return;
      }

      // If abs(slope) > 1, then transpose this line so that
      // the transposed line has slope < 1. Remember that the
      // line has been transposed.
      boolean transposedLine = false;
      if (Math.abs(y1 - y0) > Math.abs(x1 - x0)) // if abs(slope) > 1
      {  // swap x0 with y0 and swap x1 with y1
         double temp0 = x0;
         x0 = y0;
         y0 = temp0;
         double temp1 = x1;
         x1 = y1;
         y1 = temp1;
         transposedLine = true; // Remember that this line is transposed.
      }

      if (x1 < x0) // We want to rasterize in the direction of increasing x,
      {            // so, if necessary, swap (x0, y0) with (x1, y1).
         double tempX = x0;
         x0 = x1;
         x1 = tempX;
         double tempY = y0;
         y0 = y1;
         y1 = tempY;
         // swap the colors too
         float tempR = r0;
         r0 = r1;
         r1 = tempR;
         float tempG = g0;
         g0 = g1;
         g1 = tempG;
         float tempB = b0;
         b0 = b1;
         b1 = tempB;
      }

      // Compute this line segment's slopes.
      double      m = (y1 - y0) / (x1 - x0);
      double slopeR = (r1 - r0) / (x1 - x0);
      double slopeG = (g1 - g0) / (x1 - x0);
      double slopeB = (b1 - b0) / (x1 - x0);

      // Rasterize this line segment in the direction of increasing x.
      // In the following loop, as x moves across the logical horizontal
      // (or vertical) pixels, we will compute a y value for each x.
      double y = y0;
      for (int x = (int)x0; x < (int)x1; x += 1, y += m)
      {
         // Interpolate this pixel's color between the two endpoint's colors.
         float r = (float)Math.abs(r0 + slopeR*(x - x0));
         float g = (float)Math.abs(g0 + slopeG*(x - x0));
         float b = (float)Math.abs(b0 + slopeB*(x - x0));
         // We need the Math.abs() because otherwise, we sometimes get -0.0.

         if (doAntialiasing)
         {
            // y must be between two vertical (or horizontal) logical pixel
            //  coordinates. Let y_low and y_hi be the logical pixel coordinates
            // that bracket around y.
            int y_low = (int)y;                      // the integer part of y
            int y_hi  = y_low + 1;
            if (!transposedLine && y == h) y_hi = h; // test for the top edge
            if ( transposedLine && y == w) y_hi = w; // test for the right edge

            // Let weight be the fractional part of y. We will use
            // weight to determine how much emphasis to place on
            // each of the two pixels that bracket y.
            float weight = (float)(y - y_low);

            // Interpolate colors for the low and high pixels.
            // The smaller weight is, the closer y is to the lower
            // pixel, so we give the lower pixel more emphasis when
            // weight is small.
            float r_low = (float)((1 - weight) * r + weight * (bg.getRed()/255.0));
            float g_low = (float)((1 - weight) * g + weight * (bg.getGreen()/255.0));
            float b_low = (float)((1 - weight) * b + weight * (bg.getBlue()/255.0));
            float r_hi  = (float)(weight * r + (1 - weight) * (bg.getRed()/255.0));
            float g_hi  = (float)(weight * g + (1 - weight) * (bg.getGreen()/255.0));
            float b_hi  = (float)(weight * b + (1 - weight) * (bg.getBlue()/255.0));
/*
            // You can try replacing the above anti-aliasing code with this
            // code to see that this simple idea doesn't work here (as it
            // did in the previous renderer). This code just distributes the
            // line's color between two adjacent pixels (instead of blending
            // each pixel's color with the back ground color). This code ends
            // up having pixels fade to black, instead of fading to the back
            // ground color.
            float r_low = (float)((1 - weight) * r);
            float g_low = (float)((1 - weight) * g);
            float b_low = (float)((1 - weight) * b);
            float r_hi  = (float)(weight * r);
            float g_hi  = (float)(weight * g);
            float b_hi  = (float)(weight * b);
*/
            if (doGamma)
            {
               // Apply gamma-encoding (gamma-compression) to the colors.
               // https://www.scratchapixel.com/lessons/digital-imaging/digital-images
               // http://blog.johnnovak.net/2016/09/21/what-every-coder-should-know-about-gamma/
               double gamma = 1/2.2;
               r_low = (float)Math.pow(r_low, gamma);
               r_hi  = (float)Math.pow(r_hi,  gamma);
               g_low = (float)Math.pow(g_low, gamma);
               g_hi  = (float)Math.pow(g_hi,  gamma);
               b_low = (float)Math.pow(b_low, gamma);
               b_hi  = (float)Math.pow(b_hi,  gamma);
            }

            // Set this (antialiased) pixel in the framebuffer.
            if ( ! transposedLine )
            {
               if (debug) logAAPixels(x, y, y_low, y_hi, r_low, g_low, b_low, r_hi, g_hi, b_hi, w, h);

               int x_vp     = x - 1;      // viewport coordinate
               int y_vp_low = h - y_low;  // viewport coordinate
               int y_vp_hi  = h - y_hi;   // viewport coordinate
               vp.setPixelVP(x_vp, y_vp_low, new Color(r_low, g_low, b_low));
               vp.setPixelVP(x_vp, y_vp_hi,  new Color(r_hi,  g_hi,  b_hi));
            }
            else // a transposed line
            {
               if (debug) logAAPixels(y, x, y_low, y_hi, r_low, g_low, b_low, r_hi, g_hi, b_hi, w, h);

               int x_vp_low = y_low - 1;  // viewport coordinate
               int x_vp_hi  = y_hi  - 1;  // viewport coordinate
               int y_vp     = h - x;      // viewport coordinate
               vp.setPixelVP(x_vp_low, y_vp, new Color(r_low, g_low, b_low));
               vp.setPixelVP(x_vp_hi,  y_vp, new Color(r_hi,  g_hi,  b_hi));
            }
         }
         else // no antialiasing
         {
            if (doGamma)
            {
               // Apply gamma-encoding (gamma-compression) to the colors.
               // https://www.scratchapixel.com/lessons/digital-imaging/digital-images
               // http://blog.johnnovak.net/2016/09/21/what-every-coder-should-know-about-gamma/
               double gamma = 1/2.2;
               r = (float)Math.pow(r, gamma);
               g = (float)Math.pow(g, gamma);
               b = (float)Math.pow(b, gamma);
            }

            // The value of y will almost always be between
            // two vertical (or horizontal) pixel coordinates.
            // By rounding off the value of y, we are choosing the
            // nearest logical vertical (or horizontal) pixel coordinate.
            if ( ! transposedLine )
            {
               if (debug) logPixel(x, y, r, g, b, w, h);

               int x_vp = x - 1;                  // viewport coordinate
               int y_vp = h - (int)Math.round(y); // viewport coordinate
               vp.setPixelVP(x_vp, y_vp, new Color(r, g, b));
            }
            else // a transposed line
            {
               if (debug) logPixel(y, x, r, g, b, w, h);

               int x_vp = (int)Math.round(y) - 1; // viewport coordinate
               int y_vp = h - x;                  // viewport coordinate
               vp.setPixelVP(x_vp, y_vp, new Color(r, g, b));
            }
         }
         // Advance (x,y) to the next pixel (delta_x is 1, so delta_y is m).
      }
      // Set the pixel for the (x1,y1) endpoint.
      // We do this separately to avoid roundoff errors.
      if ( ! transposedLine )
      {
         if (debug) logPixel((int)x1, y1, r1, g1, b1, w, h);

         int x_vp = (int)x1 - 1;  // viewport coordinate
         int y_vp = h - (int)y1;  // viewport coordinate
         vp.setPixelVP(x_vp, y_vp, new Color(r1, g1, b1));
      }
      else
      {
         if (debug) logPixel((int)y1, x1, r1, g1, b1, w, h);

         int x_vp = (int)y1 - 1;  // viewport coordinate
         int y_vp = h - (int)x1;  // viewport coordinate
         vp.setPixelVP(x_vp, y_vp, new Color(r1, g1, b1));
      }
   }


   /*
      Log a pixel from a "horizontal" line that is being rasterized along the x-axis.
   */
   private static void logPixel(int x, double y,
                                float r, float g, float b,
                                int w, int h)
   {
      System.err.printf(
         "[w=%d, h=%d]  x=%d, y=%f, r=%f, g=%f, b=%f\n", w, h, x, y, r, g, b);
   }

   /*
      Log a pixel from a "vertical" line that is being rasterized along the y-axis.
   */
   private static void logPixel(double x, int y,
                                float r, float g, float b,
                                int w, int h)
   {
      System.err.printf(
         "[w=%d, h=%d]  x=%f, y=%d, r=%f, g=%f, b=%f\n", w, h, x, y, r, g, b);
   }

   /*
      Log two anti-aliased pixels from a "horizontal" line that is being rasterized along the x-axis.
   */
   private static void logAAPixels(int x, double y, int y1, int y2,
                                   float r1, float g1, float b1,
                                   float r2, float g2, float b2,
                                   int w, int h)
   {
      System.err.printf(
         "[w=%04d, h=%04d]  x=%d, y=%f, y_low=%d, r=%f, g=%f, b=%f\n", w, h, x, y, y1, r1, g1, b1);
      System.err.printf(
         "                  x=%d, y=%f, y_hi =%d, r=%f, g=%f, b=%f\n",       x, y, y2, r2, g2, b2);
   }

   /*
      Log two anti-aliased pixels from a "vertical" line that is being rasterized along the y-axis.
   */
   private static void logAAPixels(double x, int y, int x1, int x2,
                                   float r1, float g1, float b1,
                                   float r2, float g2, float b2,
                                   int w, int h)
   {
      System.err.printf(
         "[w=%04d, h=%04d]  x=%f, y=%d, x_low=%d, r=%f, g=%f, b=%f\n", w, h, x, y, x1, r1, g1, b1);
      System.err.printf(
         "                  x=%f, y=%d, x_hi =%d, r=%f, g=%f, b=%f\n",       x, y, x2, r2, g2, b2);
   }
}
