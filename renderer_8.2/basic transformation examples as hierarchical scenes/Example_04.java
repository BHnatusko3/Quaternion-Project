/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**
   Rotate the unit square around its center, with its center at the origin.
<p>
   Create the animation using a hierarchical scene.
   <pre>{@code
       y                           y                           y

       |                           |                           |
       |                           |                           |
     1 +---------+               1 +                         1 +
       |         |                 |                           |
       |         |                 |                           |
       |    +    |            +----|----+                     /|\
       |         |            |    |    |                   /  |  \
       |         |            |    |    |                 /    |    \
  -----+---------+--> x    ---+----+----+----+--> x    --<-----+----->----+-> x
       |         1            |    |    |    1            \    |    /     1
       |                      |    |    |                   \  |  /
       |                      +----+----+                     \|/
       |                           |                           |
   }</pre>
<p>
   See the animation <a href="../Example_04.gif">Example_04.gif</a>.
<p>
   <a href="../Example_04.gif"><img src="../Example_04.gif" alt="Example_04.gif"></a>
<p>
   For the i'th frame of the animation, the square's model matrix is
   <pre>{@code
      M = R(6*i) * T(-0.5, -0.5, 0)
        = R(6) * R(6*(i-1)) * T(-0.5, -0.5, 0).
   }</pre>
<p>
   Create the animation using the following scene graph for the i'th frame
   of the animation.
   <pre>{@code
             Scene
            /     \
           /       \
      Camera        List<Position>
                          |
                          |
                      Position
                      /   |    \
                     /    |     \
                Matrix    |      List<Position>
                R(6i)     |            |
                        null           |
                                       |
                                    Position
                                   /   |    \
                                  /    |     \
                            Matrix     |     null
                         T(-.5 -.5,0)  |
                                       |
                                     Square
   }</pre>
*/
public class Example_04
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

      // Translate the square's center to the origin, (0,0).
      position2.matrix.mult( Matrix.translate(-0.5, -0.5, 0) );

      // Rotate the square around its center, with its center at the origin.
      for (int i = 0; i < 60; i++)
      {
         // Render again.
         fb.clearFB(Color.darkGray);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_Example_04_Frame%03d.ppm", i));

         // Accumulate rotations in position1.
         position1.matrix.mult( Matrix.rotateZ(6) );
      }
   }
}
