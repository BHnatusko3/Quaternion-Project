/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a wireframe model of a camera's perspective view frustum.
   That is, create a frustum of a pyramid along the negative z-axis
   with its apex at the origin.
<p>
   This class has two constructors that mimic the projPerspective()
   methods from the {@link Camera} class.
<p>
   See <a href="https://en.wikipedia.org/wiki/Viewing_frustum" target="_top">
                https://en.wikipedia.org/wiki/Viewing_frustum</a>
*/
public class ViewFrustumModel extends Model
{
   /**
      Create a frustum of a pyramid along the negative z-axis with
      a front face at {@code z = -0.25}, a back face at {@code z = -1},
      and the front face bounded by {@code -0.25 <= x <= 0.25} and
      {@code -0.25 <= y <= 0.25}.
   */
   public ViewFrustumModel( )
   {
      this(-0.25, 0.25, -0.25, 0.25, 0.25, 1);
   }


   /**
      Create a frustum of a pyramid along the negative z-axis that
      mimics a {@link Camera}'s perspective view volume.

      @param left    left edge of the front face in the plane z = -near
      @param right   right edge of the front face in the plane z = -near
      @param bottom  bottom edge of the front face in the plane z = -near
      @param top     top edge of the front face in the plane z = -near
      @param near    distance from the origin to the front face
      @param far     distance from the origin to the back face
   */
   public ViewFrustumModel(double left, double right, double bottom, double top, double near, double far)
   {
      super("View Frustum Model");

      Vertex v0 = new Vertex(left,  top,    -near);
      Vertex v1 = new Vertex(right, top,    -near);
      Vertex v2 = new Vertex(right, bottom, -near);
      Vertex v3 = new Vertex(left,  bottom, -near);
      Vertex v4 = new Vertex( (left/near)*far,    (top/near)*far, -far);
      Vertex v5 = new Vertex((right/near)*far,    (top/near)*far, -far);
      Vertex v6 = new Vertex((right/near)*far, (bottom/near)*far, -far);
      Vertex v7 = new Vertex( (left/near)*far, (bottom/near)*far, -far);

      addVertex(v0, v1, v2, v3, v4, v5, v6, v7);

      // front (near) face
      addLineSegment(new LineSegment(0, 1),
                     new LineSegment(1, 2),
                     new LineSegment(2, 3),
                     new LineSegment(3, 0));
      // back (far) face
      addLineSegment(new LineSegment(4, 5),
                     new LineSegment(5, 6),
                     new LineSegment(6, 7),
                     new LineSegment(7, 4));
      // lines from front to back
      addLineSegment(new LineSegment(0, 4),
                     new LineSegment(1, 5),
                     new LineSegment(2, 6),
                     new LineSegment(3, 7));
   }


   /**
      Here, the frustum is determined by a vertical "field of view"
      angle and an aspect ratio for the front face.

      @param fovy    angle in the y-direction subtended by the front face
      @param aspect  aspect ratio of the front face
      @param near    distance from the origin to the front face
      @param far     distance from the origin to the back face
   */
   public ViewFrustumModel(double fovy, double aspect, double near, double far)
   {
      // top    =  near * Math.tan((Math.PI/180.0)*fovy/2.0)
      // left   = -top * aspect
      // right  =  top * aspect
      // bottom = -top
      this(-near * Math.tan((Math.PI/180.0)*fovy/2.0) * aspect,
            near * Math.tan((Math.PI/180.0)*fovy/2.0) * aspect,
           -near * Math.tan((Math.PI/180.0)*fovy/2.0),
            near * Math.tan((Math.PI/180.0)*fovy/2.0),
            near,
            far);
   }
}//ViewFrustumModel
