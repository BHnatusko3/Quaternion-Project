/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**
   Rotate the unit square around its lower left-hand corner
   placed on the circumference of the unit circle.
   <pre>{@code
            y

            |
            |
          1 +---------+
            |         |
            |         |
            |         |
            |         |
            |         |
    --------+---------+---> x
            |         1
            |
            |
            |
   }</pre>
<p>
   See the animation <a href="../Example_03.gif">Example_03.gif</a>.
<p>
   <a href="../Example_03.gif"><img src="../Example_03.gif" alt="Example_03.gif"></a>
<p>
   For the i'th frame of the animation, the square's model matrix is
   <pre>{@code
      M = R(6*i) * T(1, 0, 0)
        = R(6) * R(6*(i-1)) * T(1, 0, 0).
   }</pre>
<p>
   Create the animation using the following scene graph for the i'th frame
   of the animation.
   <pre>{@code
             Scene
            /     \
           /       \
      Camera         List<Position>
                           |
                           |
                       Position
                      /    |    \
                     /     |     \
                Matrix     |      List<Position>
                R(6i)      |           |
                          null         |
                                       |
                                    Position
                                   /    |   \
                                  /     |    \
                            Matrix      |     null
                            T(1,0)      |
                                      Square
   }</pre>
   Notice how, when we tranverse the graph and do the matrix
   multiplications, the rotations will accumulate, in a natural
   way, on the left of the translation.
<p>
   When we used Renderer 6 to do this example, we used the following
   scene graph. There was only one Position object, so there was only
   one Matrix object. All of the transformation information needed to
   be in that single matrix. Since the additional transformation
   needed by each new scene, a rotation by 6 degrees, was accumulated
   on the left of the position's matrix, this example was a bit more
   difficult to implement with Renderer 6.
   <pre>{@code
             Scene
            /     \
           /       \
      Camera       List<Position>
                         |
                         |
                      Position
                      /      \
                     /        \
                Matrix         Square
             R(6i)*T(1,0)
   }</pre>
*/
public class Example_03
{
   public static void main(String[] args)
   {
      // Create the Scene object that we shall render.
      Scene scene = new Scene();

      // Create a FrameBuffer to render our scene into.
      int width  = 512;
      int height = 512;
      FrameBuffer fb = new FrameBuffer(width, height);

      // Create a set of x and y axes.
      Model axes = new Axes2D(-2, +2, -2, +2, 8, 8);
      // Color them red.
      ModelShading.setColor(axes, Color.red);
      // Create a Position for the axes.
      Position axes_p = new Position(axes);
      // Add the axes to the Scene.
      scene.addPosition(axes_p);
      // Push the axes away from where the camera is.
      axes_p.matrix = Matrix.translate(0, 0, -2);

      // Create a Model of a square.
      Model square = new Model();
      square.addVertex(new Vertex(0, 0, 0),
                       new Vertex(1, 0, 0),
                       new Vertex(1, 1, 0),
                       new Vertex(0, 1, 0));
      square.addColor(Color.magenta,
                      Color.green,
                      Color.magenta,
                      Color.green);
      square.addLineSegment(new LineSegment(0, 1, 0, 1),
                            new LineSegment(1, 2, 1, 2),
                            new LineSegment(2, 3, 2, 3),
                            new LineSegment(3, 0, 3, 0));

      // Create the top level Position.
      Position position1 = new Position();
      // Add it to the Scene.
      scene.addPosition(position1);

      // Create the next level Position for the square.
      Position position2 = new Position(square);
      // Add it as a nested position in position1.
      position1.addNestedPosition(position2);

      // Push the square away from where the camera is.
      position1.matrix = Matrix.translate(0, 0, -2);

      // Translate the square's lower left-hand corner.
      position2.matrix.mult( Matrix.translate(1, 0, 0) );

      // Rotate the square around its lower left-hand corner
      // placed on the circumference of the unit circle.
      for (int i = 0; i < 60; i++)
      {
         // Render again.
         fb.clearFB(Color.black);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_Example_03_Frame%03d.ppm", i));

         // Accumulate rotations in position1.
         position1.matrix.mult( Matrix.rotateZ(6) );
      }
   }
}
