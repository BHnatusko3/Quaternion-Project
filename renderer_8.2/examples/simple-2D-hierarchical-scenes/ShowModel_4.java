/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**
   This file defines a more complex directed acyclic graph where
   Position objects share a nested Position object and Position
   objects also share a Model object.
<p>
   The DAG for this scene is shown below. A single instance of Model_1
   ends up appearing in the scene in four places since there are four
   paths in the DAG from the root Scene object to the Model_1 object
   (make sure you can trace out all four paths in the DAG). Since each
   path through the DAG defines a different "current transformation
   matrix" (ctm), each path through the DAG places the Model_1 instance
   in a different place in camera coordinates.
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
                  Position                    Position
                 /   |    \                   /   |   \
                /    |     \                 /    |    \
          Matrix   Model List<Position>  Matrix Model   List<Position>
            T     (empty)          \      TR   (empty)  /
                                    \                  /
                                     \                /
                                      \              /
                                       \            /
                                        \          /
                                          Position
                                         /  |    \
                                        /   |     \
                                  Matrix  Model    List<Position>
                                    I    (empty)    /           \
                                                   /             \
                                                  /               \
                                           Position            Position
                                           /       \           /     /
                                          /         \         /     /
                                      Matrix         \    Matrix   /
                                        I             \    TSR    /
                                                       \         /
                                                        \       /
                                                         Model_1
}</pre>
*/
public class ShowModel_4
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

      // Add a reference to a Position p3 to each of Positions p1 and p2.
      Position p3 = new Position();
      p1.addNestedPosition(p3);
      p2.addNestedPosition(p3);

      // Add two nested Positions to the Position p3.
      Position p4 = new Position();
      Position p5 = new Position();
      p3.addNestedPosition(p4, p5);

      // Create a single instance of Model_1.
      Model m1 = new Model_1();
      ModelShading.setColor(m1, Color.red);
      // Add a reference to Model m1 to each of Positions p4 and p5.
      p4.model = m1;
      p5.model = m1;


      // Initialize the nested matrices in the Positions.
      p1.matrix.mult(Matrix.translate(-2, -2, 0));
      p2.matrix.mult(Matrix.translate(2, 2, 0));
      p2.matrix.mult(Matrix.rotateZ(180));
      p5.matrix.mult(Matrix.translate(1, -2-Math.sqrt(2), 0));
      p5.matrix.mult(Matrix.scale(0.5, 0.5, 1));
      p5.matrix.mult(Matrix.rotateZ(-45));


      // Create a FrameBuffer to render our Scene into.
      int vp_width  = 512;
      int vp_height = 512;
      FrameBuffer fb = new FrameBuffer(vp_width, vp_height);

      for (int i = 0; i <= 36; i++)
      {
         p.matrix2Identity();
         // Push the models away from where the camera is.
         p.matrix.mult(Matrix.translate(0, 0, -8));

         p.matrix.mult(Matrix.rotateZ(10*i));

         // Render again.
         fb.clearFB(Color.white);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_ShowModel_4_Frame%02d.ppm", i));
      }
   }
}
