/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**
   Here is a sketch of this program's scene graph.
   Only the TriangularPrism model holds any geometry.
   All of the other nodes hold only a matrix.
<pre>{@code
                 Scene
                   |
                   |
               Position
              /    |    \
             /     |     \
       Matrix    Model    nested Positions
         R      (empty)      / |  | \
                            /  |  |  \
                          p1  p2  p3  p4
                            \  |  |  /
                             \ |  | /
                          TriangularPrism
}</pre>
*/
public class NestedPrisms_v1
{
   public static void main(String[] args)
   {
      final double sqrt3 = Math.sqrt(3.0);

      // Create the Scene object that we shall render.
      Scene scene = new Scene();

      // Create the top level Position.
      Position top_p = new Position();

      // Add the top level Position to the Scene.
      scene.addPosition(top_p);

      // Create four nested Positions each holding
      // a reference to a shared prism Model.
      Model prism = new TriangularPrism(1.0/sqrt3, 2.0, Math.PI/4.0, 25);
      ModelShading.setColor(prism, Color.red);
      Position p1 = new Position(prism);
      Position p2 = new Position(prism);
      Position p3 = new Position(prism);
      Position p4 = new Position(prism);

      // Put these four nested Positions into the top level Position.
      top_p.addNestedPosition(p1);
      top_p.addNestedPosition(p2);
      top_p.addNestedPosition(p3);
      top_p.addNestedPosition(p4);

      // Place the four nested positions within
      // the top level position.
      // right
      p1.matrix.mult(Matrix.translate(2+0.5/sqrt3, 0, 0));
      // left
      p2.matrix.mult(Matrix.translate(-2-0.5/sqrt3, 0, 0));
      p2.matrix.mult(Matrix.rotateZ(180));
      // top
      p3.matrix.mult(Matrix.rotateZ(90));
      p3.matrix.mult(Matrix.translate(2+0.5/sqrt3, 0, 0));
      // bottom
      p4.matrix.mult(Matrix.rotateZ(-90));
      p4.matrix.mult(Matrix.translate(2+0.5/sqrt3, 0, 0));

      // Create a floor Model.
      Model floor = new PanelXZ(-4, 4, -4, 4);
      ModelShading.setColor(floor, Color.black);
      Position floor_p = new Position(floor);
      floor_p.matrix.mult(Matrix.translate(0, -4, 0));
      // Push this model away from where the camera is.
      floor_p.matrix.mult(Matrix.translate(0, 0, -5));
      // Add the floor to the Scene.
      scene.addPosition(floor_p);


      // Create a framebuffer to render our scene into.
      int vp_width  = 1024;
      int vp_height = 1024;
      FrameBuffer fb = new FrameBuffer(vp_width, vp_height);
      // Give the framebuffer a nice background color.
      fb.clearFB(Color.lightGray);

      // Spin the model 360 degrees arond two axes.
      for (int i = 0; i <= 180; i++)
      {
         top_p.matrix2Identity();
         // Push the model away from where the camera is.
         top_p.matrix.mult(Matrix.translate(0, 0, -8));
         top_p.matrix.mult(Matrix.rotateX(2*i));
         top_p.matrix.mult(Matrix.rotateY(2*i));
         top_p.matrix.mult(Matrix.rotateZ(2*i));

         // Render again.
         fb.clearFB(Color.lightGray);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_NestedPrism_v1_Frame_%03d.ppm", i));
      }
   }
}
