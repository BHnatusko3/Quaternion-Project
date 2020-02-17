/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**
   This file just shows what Model_1 looks like.
<p>
   The tree for this scene is shown below.
<p>
   Remember that every position node in the tree contains a matrix,
   a model and a list of nested positions. The model may be empty,
   and the list of nested positions may also be empty, but the matrix
   cannot be "empty" (if you don't give it a value, then it is the
   identity matrix, I).
<p>
<pre>{@code
           Scene
          /     \
         /       \
    Camera     List<Position>
                     |
                     |
                  Position
                 /   |    \
                /    |     \
          Matrix   Model_1   List<Position>
            R                      |
                                 empty
}</pre>
*/
public class ShowModel_1
{
   public static void main(String[] args)
   {
      // Create the Scene object that we shall render.
      Scene scene = new Scene();

      // Create an instance of Model_1.
      Model m = new Model_1();
      // Set the model's color.
      ModelShading.setColor(m, Color.black);
      // Add the model to the Scene.
      scene.addPosition(new Position(m));


      // Create a FrameBuffer to render our Scene into.
      int vp_width  = 512;
      int vp_height = 512;
      FrameBuffer fb = new FrameBuffer(vp_width, vp_height);

      for (int i = 0; i <= 36; i++)
      {
         scene.getPosition(0).matrix2Identity();
         // Push the model away from where the camera is.
         scene.getPosition(0).matrix.mult(Matrix.translate(0, 0, -3));

         scene.getPosition(0).matrix.mult(Matrix.rotateZ(10*i));

         // Render again.
         fb.clearFB(Color.white);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_ShowModel_1_Frame%02d.ppm", i));
      }
   }
}
