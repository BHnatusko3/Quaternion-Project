/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**
   This file defines a hierarchical scene out of two instances of Model_1.
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
          Matrix   Model     List<Position>
            R     (empty)   /              \
                           /                \
                          /                  \
                   Position              Position
                    /     \             /      \
                   /       \           /        \
               Matrix    Model_1    Matrix     Model_1
                 I                   TSR
}</pre>
*/
public class ShowModel_2
{
   public static void main(String[] args)
   {
      // Create the Scene object that we shall render.
      Scene scene = new Scene();

      // Create the top level Position.
      Position p = new Position();

      // Add the top level Position to the Scene.
      scene.addPosition(p);

      // Add two nested Positions to the top level Position.
      Position p1 = new Position();
      Position p2 = new Position();
      p.addNestedPosition(p1, p2);

      // Add two instances of Model_1 to the Scene.
      Model m1 = new Model_1();
      Model m2 = new Model_1();
      ModelShading.setColor(m1, Color.red);
      ModelShading.setColor(m2, Color.blue);
      // Add references to Models m1 and m2 to Positions p1 and p2.
      p1.model = m1;
      p2.model = m2;

      // Initialize the nested matrices in the Positions.
      p2.matrix.mult(Matrix.translate(1, -2-Math.sqrt(2), 0));
      p2.matrix.mult(Matrix.scale(0.5, 0.5, 1));
      p2.matrix.mult(Matrix.rotateZ(-45));


      // Create a FrameBuffer to render our Scene into.
      int vp_width  = 512;
      int vp_height = 512;
      FrameBuffer fb = new FrameBuffer(vp_width, vp_height);

      for (int i = 0; i <= 36; i++)
      {
         p.matrix2Identity();
         // Push the models away from where the camera is.
         p.matrix.mult(Matrix.translate(0, 0, -5));

         p.matrix.mult(Matrix.rotateZ(10*i));

         // Render again.
         fb.clearFB(Color.white);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_ShowModel_2_Frame%02d.ppm", i));
      }
   }
}
