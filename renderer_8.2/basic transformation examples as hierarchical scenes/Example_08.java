/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**
   Rotate the unit square around the point (1, 1) in the
   square's model coordinate system, but with the center of
   rotation at the point (0, 1/2) in the camera's view
   coordinate system.
<p>
   Create the animation using a hierarchical scene.
   <pre>{@code
            y                             y

            |                             |
            |                             |
          1 +---------+                 1 +---------+
            |         |                   |         |
            |         |                   |         |
            |         |                   +         |
            |         |                  /|\        |
            |         |                /  |  \      |
    --------+---------+---> x     ---/----+----\----+---> x
            |         1             <     |     >   1
            |                        \    |    /
            |                          \  |  /
            |                            \|/
            |                             |
   }</pre>
<p>
   See the animation <a href="../Example_08.gif">Example_08.gif</a>.
<p>
   <a href="../Example_08.gif"><img src="../Example_08.gif" alt="Example_08.gif"></a>
<p>
   For the i'th frame of the animation, the square's model matrix is
   <pre>{@code
      M = T(0, 1/2, 0) * R(6*i) * T(-1, -1, 0)
   }</pre>
<p>
   Create the animation using the following scene graph for the i'th frame
   of the animation.
   <pre>{@code
             Scene
            /     \
           /       \
      Camera      List<Position>
                         |
                         |
                      Position
                      /   |   \
                     /    |    \
               Matrix     |    List<Position>
             T(0,1/2,0)   |           |
                         null         |
                                      |
                                   Position
                                   /   |   \
                                  /    |    \
                            Matrix     |    List<Position>
                            R(6i)      |            |
                                      null          |
                                                 Position
                                                 /   |   \
                                                /    |    \
                                          Matrix     |     null
                                        T(-1,-1,0)   |
                                                   Square
   }</pre>
*/
public class Example_08
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

      // Create the next level Position.
      Position position2 = new Position();
      // Add it as a nested position in position1.
      position1.addNestedPosition(position2);

      // Create a third Position for the square.
      Position position3 = new Position(square);
      // Add the square as a nested position in position2.
      position2.addNestedPosition(position3);

      // Push the square away from where the camera is.
      position1.matrix = Matrix.translate(0, 0, -2);

      // Translate the square's upper right-hand corner to the origin.
      position3.matrix = Matrix.translate(-1, -1, 0);

      // Translate it back.
      position1.matrix.mult( Matrix.translate(0, 0.5, 0) );

      // Rotate the square around its upper right hand corner.
      for (int i = 0; i < 60; i++)
      {
         // Render again.
         fb.clearFB(Color.darkGray);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_Example_08_Frame%03d.ppm", i));

         // Accumulate rotations in position2.
         position2.matrix.mult( Matrix.rotateZ(6) );
      }
   }
}
