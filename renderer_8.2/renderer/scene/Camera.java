/*

*/

package renderer.scene;

/**
   This {@code Camera} data structure represents a camera
   located at the origin, looking down the negative z-axis.
<p>
   This {@code Camera} has a {@link normalizeMatrix} which
   associates to this camera a "view volume" that determines
   what part of space the camera "sees" when we use the camera
   to take a picture (that is, when we render a {@link Scene}).
<p>
   This {@code Camera} can "take a picture" two ways, using
   a perspective projection or a parallel (orthographic)
   projection. Each way of taking a picture has a different
   shape for its view volume. The data in this data structure
   determines the shape of each of the two view volumes.
<p>
   For the perspective projection, the view volume (in view
   coordinates!) is an infinitely long pyramid that is formed
   by the pyramid with its apex at the origin and its base in
   the plane {@code z = -near} with edges {@code x = left},
   {@code x = right}, {@code y = top}, and {@code y = bottom}.
   The perspective view volume's shape is set by the
   {@link projPerspective} methods which instantiate a
   {@link PerspectiveNormalizeMatrix} as the value of this
   {@code Camera}'s (@link normalizeMatrix}.
<p>
   For the orthographic projection, the view volume (in view
   coordinates!) is an infinitely long rectangular cylinder
   parallel to the z-axis and with sides {@code x = left},
   {@code x = right}, {@code y = top}, and {@code y = bottom}
   (an infinite parallelepiped). The orthographic view volume's
   shape is set by the {@link projOrtho} method which instantiates
   a {@link OrthographicNormalizeMatrix} as the value of this
   {@code Camera}'s (@link normalizeMatrix}.
<p>
   When the graphics rendering {@link renderer.pipeline.Pipeline} uses
   this {@code Camera} to render a {@link Scene}, the renderer only
   "sees" the geometry from the scene that is contained in this camera's
   view volume. (Notice that this means the orthographic camera will see
   geometry that is behind the camera. In fact, the perspective camera
   also sees geometry that is behind the camera.) The renderer's
   {@link renderer.pipeline.Clip} pipeline stage is responsible for
   making sure that the scene's geometry that is outside of this
   camera's view volume is not visible.
<p>
   The plane {@code z = -near} (in view coordinates) is the camera's
   image plane. The rectangle in the image plane with corners
   {@code (left, bottom, -near)} and {@code (right, top, -near)} is
   the camera's view rectangle. The view rectangle is like the film
   in a real camera, it is where the camera's image appears when you
   take a picture. The contents of the camera's view rectangle (after
   it gets "normalized" by the renderer's {@link renderer.pipeline.View2Camera}
   stage) is what gets rasterized, by the renderer's
   {@link renderer.pipeline.RasterizeAntialias} pipeline stage,
   into a {@link renderer.framebuffer.FrameBuffer}.
*/
public class Camera
{
   // Choose either perspective or parallel projection.
   public boolean perspective;

   public Matrix normalizeMatrix;

   public double left;   // These five numbers define the camera's view volume.
   public double right;  // These numbers are encoded into the camera's
   public double bottom; // normalization matrix.
   public double top;
   public double n;


   /**
      The default {@code Camera} has the standard (normalized)
      perspective view volume.
   */
   public Camera()
   {
      this.projPerspective();
   }


   /**
      Set up this {@code Camera}'s view volume as a perspective projection
      of the normalized infinite view pyramid extending along the
      negative z-axis.
   */
   public void projPerspective()
   {
      projPerspective(-1.0, +1.0, -1.0, +1.0, 1.0);
   }


   /**
      Set up this {@code Camera}'s view volume as a perspective projection
      of an infinite view pyramid extending along the negative z-axis.

      @param left    left edge of view rectangle in the near plane
      @param right   right edge of view rectangle in the near plane
      @param bottom  bottom edge of view rectangle in the near plane
      @param top     top edge of view rectangle in the near plane
      @param near    distance from the orgin to the near plane
   */
   public void projPerspective(double left, double right, double bottom, double top, double near)
   {
      this.left   = left;   // These five numbers define the camera's view volume.
      this.right  = right;  // These numbers are encoded into the camera's
      this.bottom = bottom; // normalization matrix.
      this.top    = top;
      this.n      = -near;

      this.normalizeMatrix = PerspectiveNormalizeMatrix.build(left, right, bottom, top, near);

      this.perspective = true;
   }


   /**
      An alternative way to determine this {@code Camera}'s perspective
      view volume.
      <p>
      Here, the view volume is determined by a vertical "field of view"
      angle and an aspect ratio for the view rectangle in the near plane.

      @param fovy    angle in the y-direction subtended by the view rectangle in the near plane
      @param aspect  aspect ratio of the view rectangle in the near plane
      @param near    distance from the origin to the near plane
   */
   public void projPerspective(double fovy, double aspect, double near)
   {
      this.top    =  near * Math.tan((Math.PI/180.0)*fovy/2.0);
      this.bottom = -top;
      this.right  =  top * aspect;
      this.left   = -right;
      this.n      = -near;

      projPerspective(left, right, bottom, top, near);
   }


   /**
      Set up this {@code Camera}'s view volume as a parallel (orthographic)
      projection of the normalized infinite view parallelepiped extending
      along the z-axis.
   */
   public void projOrtho()
   {
      projOrtho(-1.0, +1.0, -1.0, +1.0);
   }


   /**
      Set up this {@code Camera}'s view volume as a parallel (orthographic)
      projection of an infinite view parallelepiped extending along the
      z-axis.

      @param left    left edge of view rectangle in the xy-plane
      @param right   right edge of view rectangle in the xy-plane
      @param bottom  bottom edge of view rectangle in the xy-plane
      @param top     top edge of view rectangle in the xy-plane
   */
   public void projOrtho(double left, double right, double bottom, double top)
   {
      this.left   = left;   // These five numbers define the camera's view volume.
      this.right  = right;  // These numbers are encoded into the camera's
      this.bottom = bottom; // normalization matrix.
      this.top    = top;
      this.n      = 0;

      this.normalizeMatrix = OrthographicNormalizeMatrix.build(left, right, bottom, top);

      this.perspective = false;
   }


   /**
      An alternative way to determine this {@code Camera}'s orthographic
      view volume.
      <p>
      Here, the view volume is determined by a vertical "field of view"
      angle and an aspect ratio for the view rectangle in the near plane.

      @param fovy    angle in the y-direction subtended by the view rectangle in the near plane
      @param aspect  aspect ratio of the view rectangle in the near plane
      @param near    distance from the origin to the near plane
   */
   public void projOrtho(double fovy, double aspect, double near)
   {
      this.top    =  near * Math.tan((Math.PI/180.0)*fovy/2.0);
      this.bottom = -top;
      this.right  =  top * aspect;
      this.left   = -right;

      projOrtho(left, right, bottom, top);
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Camera} object
   */
   public String toString()
   {
      double fovy = 2.0 * (180./Math.PI) * Math.atan(top/(-n));
      String result = "";
      result += "Camera: \n";
      result += "perspective = " + perspective + "\n";
      result += "left = "   + left + ", "
             +  "right = "  + right + "\n"
             +  "bottom = " + bottom + ", "
             +  "top = "    + top + "\n"
             +  "near = "   + -n + "\n"
             +  "(fovy = " + fovy + ", aspect = " + right/top + ")\n"
             +  "Normalization Matrix\n"
             +  normalizeMatrix;
      return result;
   }
}
