/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**
   This program creates a very complex hierarchical scene
   that draws 36 copies of Model_1.
<p>
   The DAG has a top level Position that holds nine nested
   positions. Each of the nine nested positions holds a
   reference to a sub-DAG that is copied from ShowModel_4.java.
   There are 36 paths in the DAG from the root Scene object to
   the single Model_1 object at the bottom of the DAG.
<p>
<pre>{@code
         Scene
         /   \
        /     \
   Camera   List<Position>
             |
             |
         Position
         /  |    \
        /   |     \
  Matrix  Model     List<Position>
    R    (empty)    /             \
                   /               \
                  /                 \
                 /                   \
                /                     \
               /                       \
         Pos Pos Pos Pos Pos Pos Pos Pos Pos
               \                      /
                \                    /
                 \                  /
                  \                /
                   \              /
                    \            /
                       Position
                       /  |    \
                      /   |     \
                Matrix  Model     List<Position>
                  I    (empty)  /                \
                               /                  \
                              /                    \
                       Position                    Position
                      /   |    \                   /   |   \
                     /    |     \                 /    |    \
               Matrix   Model  List<Position> Matrix Model   List<Position>
                 T     (empty)         \       TR   (empty)  /
                                        \                   /
                                         \                 /
                                          \               /
                                           \             /
                                            \           /
                                              Position
                                              /  |    \
                                             /   |     \
                                       Matrix  Model     List<Position>
                                         I    (empty)    /            \
                                                        /              \
                                                       /                \
                                                Position             Position
                                                /      \            /      /
                                               /        \          /      /
                                           Matrix        \     Matrix    /
                                             I            \     TSR     /
                                                           \           /
                                                            \         /
                                                              Model_1
}</pre>
*/
public class ShowNestedModels_36
{
   public static void main(String[] args)
   {
      // Create the Scene object that we shall render.
      Scene scene = new Scene();

      // Create the top level Position.
      Position p = new Position();

      // Add the top level Position to the Scene.
      scene.addPosition(p);

      // Add nine nested Position objects to the top level Position.
      for (int i = 0; i <= 8; i++)
      {
         p.addNestedPosition( new Position() );
      }

      // Create a Position that holds the complex hierarchical
      // structure that draws four copies of Model_1.
      Position complex_p = new Position();

      // Add two nested Positions to the complex position structure.
      Position p1 = new Position();
      Position p2 = new Position();
      complex_p.addNestedPosition(p1, p2);

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

      // Initialize the nested matrices in these Positions.
      p1.matrix.mult(Matrix.translate(-2, -2, 0));
      p2.matrix.mult(Matrix.translate(2, 2, 0));
      p2.matrix.mult(Matrix.rotateZ(180));
      p5.matrix.mult(Matrix.translate(1, -2-Math.sqrt(2), 0));
      p5.matrix.mult(Matrix.scale(0.5, 0.5, 1));
      p5.matrix.mult(Matrix.rotateZ(-45));

      // Add a reference to the complex position structure
      // to each of the nine nested positions in the top
      // level Position object.
      p.getNestedPosition(0).addNestedPosition(complex_p);
      for (int i = 1; i <= 8; i++)
      {
         p.getNestedPosition(i).addNestedPosition(complex_p);
         p.getNestedPosition(i).matrix2Identity();
         p.getNestedPosition(i).matrix.mult(Matrix.rotateZ(i*45));
         p.getNestedPosition(i).matrix.mult(Matrix.translate(0, -11, 0));
         p.getNestedPosition(i).matrix.mult(Matrix.scale(0.5));
      }


      // Create a FrameBuffer to render our Scene into.
      int vp_width  = 512;
      int vp_height = 512;
      FrameBuffer fb = new FrameBuffer(vp_width, vp_height);

      for (int i = 0; i <= 36; i++)
      {
         p.matrix2Identity();
         // Push the models away from where the camera is.
         p.matrix.mult(Matrix.translate(0, 0, -15));

         p.matrix.mult(Matrix.rotateZ(10*i));

         // What does this do?
       //complex_p.matrix2Identity();
       //complex_p.matrix.mult(rotateZ(10*i));

         // Render again.
         fb.clearFB(Color.white);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_ShowNestedModels_36_Frame%02d.ppm", i));
      }
   }
}
