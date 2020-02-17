/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;
import renderer.gui.*;

import java.awt.Color;
import java.awt.event.*;

/**
   Draw two interactive robot arms with
   shoulder, elbow, wrist, and finger joints.
<p>
   Here is a simplified version of this program's scene graph.
<p>
<pre>{@code
                        Scene
                        /   \
                /------/     \------\
               /                     \
        Position                     Position
        / | \                         / |   \
       /  |  \                       /  |    \
 Matrix   |   \                Matrix   |     \
  TRS    /  Position            TRS    /   Position
        /     / |  \                  /     / |  \
       /     /  |   \                /     /  |   \
      / Matrix  |    \              / Matrix  |    \
     /   TRS   /    Position       |   TRS   /    Position
     \        /      / |  \        |        /      / |  \
      \      /      /  |   \       |       /      /  |   \
       \    /  Matrix  |    \      |      /  Matrix  |    \
        \   \   TRS   /   Position |      |   TRS   /   Position
         \   \       /      /  |   |      |        /      /   /
          \   \     /      /   |   |      |       /      /   /
           \   \   /  Matrix   |   |     /       /  Matrix  /
            \   \  \   TRS     |   |    /       /    TRS   /
             \   \  \          |   |   /       /          /
              \   \  \------\  |   |  /       /          /
               \   \---------\ |   | /-------/          /
                \-------------\|   |/------------------/
                               Model
                             armSegment
</pre>
*/
public class RobotArms2
{
   private static double xTranslation1 = 0.0;
   private static double yTranslation1 = 0.5;
   private static double xTranslation2 =  0.0;
   private static double yTranslation2 = -0.5;

   private static double shoulderRotation1 = 0.0;
   private static double shoulderRotation2 = 0.0;
   private static double    elbowRotation1 = 0.0;
   private static double    elbowRotation2 = 0.0;
   private static double    wristRotation1 = 0.0;
   private static double    wristRotation2 = 0.0;
   private static double   fingerRotation1 = 0.0;
   private static double   fingerRotation2 = 0.0;

   private static double shoulderLength1 = 0.4;
   private static double shoulderLength2 = 0.4;
   private static double    elbowLength1 = 0.3;
   private static double    elbowLength2 = 0.3;
   private static double    wristLength1 = 0.2;
   private static double    wristLength2 = 0.2;
   private static double   fingerLength1 = 0.1;
   private static double   fingerLength2 = 0.1;

   private static void print_help_message()
   {
      //System.out.println("Use the 'd' key to toggle debugging information on and off.");
      //System.out.println("Use the 'a' key to toggle antialiasing on and off.");
      System.out.println("Use the s/S keys to rotate arm 1 at its shoulder.");
      System.out.println("Use the e/E keys to rotate arm 1 at its elbow.");
      System.out.println("Use the w/W keys to rotate arm 1 at its wrist.");
      System.out.println("Use the f/F keys to rotate arm 1 at its finger.");
      System.out.println();
      System.out.println("Use the z/Z keys to rotate arm 2 at its shoulder.");
      System.out.println("Use the d/D keys to rotate arm 2 at its elbow.");
      System.out.println("Use the a/A keys to rotate arm 2 at its wrist.");
      System.out.println("Use the c/C keys to rotate arm 2 at its finger.");
      System.out.println();
      System.out.println("Use the ^s/^S keys to extend the length of arm 1 at its shoulder.");
      System.out.println("Use the ^e/^E keys to extend the length of arm 1 at its elbow.");
      System.out.println("Use the ^w/^W keys to extend the length of arm 1 at its wrist.");
      System.out.println("Use the ^f/^F keys to extend the length of arm 1 at its finger.");
      System.out.println();

      System.out.println("Use the ^z/^Z keys to extend the length of arm 2 at its shoulder.");
      System.out.println("Use the ^d/^D keys to extend the length of arm 2 at its elbow.");
      System.out.println("Use the ^a/^A keys to extend the length of arm 2 at its wrist.");
      System.out.println("Use the ^c/^C keys to extend the length of arm 2 at its finger.");
      System.out.println();

      System.out.println("Use the x/X keys to translate arm 1 along the x-axis.");
      System.out.println("Use the y/Y keys to translate arm 1 along the y-axis.");
      System.out.println("Use the u/U keys to translate arm 2 along the x-axis.");
      System.out.println("Use the v/V keys to translate arm 2 along the y-axis.");
      System.out.println();
      System.out.println("Use the 'h' key to redisplay this help message.");
   }


   public static void main(String[] args)
   {
      print_help_message();

      // Create the Scene object that we shall render
      Scene scene = new Scene();

      /*
         Create the scene graph.
      */

      // Create two Position objects that will each hold a robot arm.
      Position arm1_p = new Position();
      Position arm2_p = new Position();

      // Add the Position objects to the scene.
      scene.addPosition(arm1_p, arm2_p);

      // Push the positions away from where the camera is.
      arm1_p.matrix.mult(Matrix.translate(xTranslation1, yTranslation1, -1));
      arm2_p.matrix.mult(Matrix.translate(xTranslation2, yTranslation2, -1));

      // Create one Model that can be used
      // for each part of the robot arms.
      Vertex v0 = new Vertex(0, 0, 0);
      Vertex v1 = new Vertex(1, 0, 0);
      Model armSegment = new Model();
      armSegment.addVertex(v0, v1);
      armSegment.addLineSegment(new LineSegment(0, 1));
      ModelShading.setColor(armSegment, Color.blue);

      /*
         Create two robot arms.
      */
      // First arm.
      arm1_p.model = armSegment;

      Position elbow1_p = new Position(armSegment);
      arm1_p.addNestedPosition(elbow1_p);

      Position wrist1_p = new Position(armSegment);
      elbow1_p.addNestedPosition(wrist1_p);

      Position finger1_p = new Position(armSegment);
      wrist1_p.addNestedPosition(finger1_p);

      // Second arm.
      arm2_p.model = armSegment;

      Position elbow2_p = new Position(armSegment);
      arm2_p.addNestedPosition(elbow2_p);

      Position wrist2_p = new Position(armSegment);
      elbow2_p.addNestedPosition(wrist2_p);

      Position finger2_p = new Position(armSegment);
      wrist2_p.addNestedPosition(finger2_p);

      // Initialize the nested matrices for the sub models.
      // First arm.
      arm1_p.matrix.mult(Matrix.scale(shoulderLength1, shoulderLength1, 1));

      elbow1_p.matrix.mult(Matrix.translate(1, 0, 0));
      elbow1_p.matrix.mult(Matrix.scale(elbowLength1/shoulderLength1, elbowLength1/shoulderLength1, 1));

      wrist1_p.matrix.mult(Matrix.translate(1, 0, 0));
      wrist1_p.matrix.mult(Matrix.scale(wristLength1/elbowLength1, wristLength1/elbowLength1, 1));

      finger1_p.matrix.mult(Matrix.translate(1, 0, 0));
      finger1_p.matrix.mult(Matrix.scale(fingerLength1/wristLength1, fingerLength1/wristLength1, 1));

      // Second arm.
      arm2_p.matrix.mult(Matrix.scale(shoulderLength2, shoulderLength2, 1));

      elbow2_p.matrix.mult(Matrix.translate(1, 0, 0));
      elbow2_p.matrix.mult(Matrix.scale(elbowLength2/shoulderLength2, elbowLength2/shoulderLength2, 1));

      wrist2_p.matrix.mult(Matrix.translate(1, 0, 0));
      wrist2_p.matrix.mult(Matrix.scale(wristLength2/elbowLength2, wristLength2/elbowLength2, 1));

      finger2_p.matrix.mult(Matrix.translate(1, 0, 0));
      finger2_p.matrix.mult(Matrix.scale(fingerLength2/wristLength2, fingerLength2/wristLength2, 1));


      // Define initial dimensions for a FrameBuffer.
      int width  = 512;
      int height = 512;

      // Create an InteractiveFrame containing a FrameBuffer
      // with the given dimensions.
      @SuppressWarnings("serial")
      InteractiveFrame app = new InteractiveFrame("Renderer 8", width, height)
      {
         // Implement part of the KeyListener interface.
         @Override public void keyPressed(KeyEvent e)
         {
          //System.out.println( e );

            if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 83) // ^S
            {
               shoulderLength1 -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 83) // ^s
            {
               shoulderLength1 += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 69) // ^E
            {
               elbowLength1 -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 69) // ^e
            {
               elbowLength1 += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 87) // ^W
            {
               wristLength1 -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 87)  // ^w
            {
               wristLength1 += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 70) // ^F
            {
               fingerLength1 -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 70) // ^f
            {
               fingerLength1 += 0.02;
            }

            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 90) // ^Z
            {
               shoulderLength2 -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 90) // ^z
            {
               shoulderLength2 += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 68) // ^D
            {
               elbowLength2 -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 68) // ^d
            {
               elbowLength2 += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 65) // ^A
            {
               wristLength2 -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 65)  // ^a
            {
               wristLength2 += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 67) // ^C
            {
               fingerLength2 -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 67) // ^c
            {
               fingerLength2 += 0.02;
            }
         }


         @Override public void keyTyped(KeyEvent e)
         {
          //System.out.println( e );

            char c = e.getKeyChar();
            if ('h' == c)
            {
               print_help_message();
               return;
            }
            //else if ('d' == c)
            //{
            // //Pipeline.debug = ! Pipeline.debug;
            //   Pipeline.debug_visible = ! Pipeline.debug_visible;
            // //Rasterize.debug = ! Rasterize.debug;
            // //RasterizeAntialias.debug = ! RasterizeAntialias.debug;
            //}
            //else if ('a' == c)
            //{
            //   RasterizeAntialias.doAntialiasing = ! RasterizeAntialias.doAntialiasing;
            //}
            else if ('x' == c)
            {
               xTranslation1 += 0.02;
            }
            else if ('X' == c)
            {
               xTranslation1 -= 0.02;
            }
            else if ('y' == c)
            {
               yTranslation1 += 0.02;
            }
            else if ('Y' == c)
            {
               yTranslation1 -= 0.02;
            }
            else if ('u' == c)
            {
               xTranslation2 += 0.02;
            }
            else if ('U' == c)
            {
               xTranslation2 -= 0.02;
            }
            else if ('v' == c)
            {
               yTranslation2 += 0.02;
            }
            else if ('V' == c)
            {
               yTranslation2 -= 0.02;
            }
            else if ('s' == c)
            {
               shoulderRotation1 += 2.0;
            }
            else if ('S' == c)
            {
               shoulderRotation1 -= 2.0;
            }
            else if ('e' == c)
            {
               elbowRotation1 += 2.0;
            }
            else if ('E' == c)
            {
               elbowRotation1 -= 2.0;
            }
            else if ('w' == c)
            {
               wristRotation1 += 2.0;
            }
            else if ('W' == c)
            {
               wristRotation1 -= 2.0;
            }
            else if ('f' == c)
            {
               fingerRotation1 += 2.0;
            }
            else if ('F' == c)
            {
               fingerRotation1 -= 2.0;
            }
            else if ('z' == c)
            {
               shoulderRotation2 += 2.0;
            }
            else if ('Z' == c)
            {
               shoulderRotation2 -= 2.0;
            }
            else if ('d' == c)
            {
               elbowRotation2 += 2.0;
            }
            else if ('D' == c)
            {
               elbowRotation2 -= 2.0;
            }
            else if ('a' == c)
            {
               wristRotation2 += 2.0;
            }
            else if ('A' == c)
            {
               wristRotation2 -= 2.0;
            }
            else if ('c' == c)
            {
               fingerRotation2 += 2.0;
            }
            else if ('C' == c)
            {
               fingerRotation2 -= 2.0;
            }

            // Update the nested matrices for the sub models.
            arm1_p.matrix2Identity();
            arm1_p.matrix.mult(Matrix.translate(xTranslation1, yTranslation1, -1));
            arm1_p.matrix.mult(Matrix.rotateZ(shoulderRotation1));
            arm1_p.matrix.mult(Matrix.scale(shoulderLength1, shoulderLength1, 1));

            elbow1_p.matrix2Identity();
            elbow1_p.matrix.mult(Matrix.translate(1, 0, 0));
            elbow1_p.matrix.mult(Matrix.rotateZ(elbowRotation1));
            elbow1_p.matrix.mult(Matrix.scale(elbowLength1/shoulderLength1, elbowLength1/shoulderLength1, 1));

            wrist1_p.matrix2Identity();
            wrist1_p.matrix.mult(Matrix.translate(1, 0, 0));
            wrist1_p.matrix.mult(Matrix.rotateZ(wristRotation1));
            wrist1_p.matrix.mult(Matrix.scale(wristLength1/elbowLength1, wristLength1/elbowLength1, 1));

            finger1_p.matrix2Identity();
            finger1_p.matrix.mult(Matrix.translate(1, 0, 0));
            finger1_p.matrix.mult(Matrix.rotateZ(fingerRotation1));
            finger1_p.matrix.mult(Matrix.scale(fingerLength1/wristLength1, fingerLength1/wristLength1, 1));

            arm2_p.matrix2Identity();
            arm2_p.matrix.mult(Matrix.translate(xTranslation2, yTranslation2, -1));
            arm2_p.matrix.mult(Matrix.rotateZ(shoulderRotation2));
            arm2_p.matrix.mult(Matrix.scale(shoulderLength2, shoulderLength2, 1));

            elbow2_p.matrix2Identity();
            elbow2_p.matrix.mult(Matrix.translate(1, 0, 0));
            elbow2_p.matrix.mult(Matrix.rotateZ(elbowRotation2));
            elbow2_p.matrix.mult(Matrix.scale(elbowLength2/shoulderLength2, elbowLength2/shoulderLength2, 1));

            wrist2_p.matrix2Identity();
            wrist2_p.matrix.mult(Matrix.translate(1, 0, 0));
            wrist2_p.matrix.mult(Matrix.rotateZ(wristRotation2));
            wrist2_p.matrix.mult(Matrix.scale(wristLength2/elbowLength2, wristLength2/elbowLength2, 1));

            finger2_p.matrix2Identity();
            finger2_p.matrix.mult(Matrix.translate(1, 0, 0));
            finger2_p.matrix.mult(Matrix.rotateZ(fingerRotation2));
            finger2_p.matrix.mult(Matrix.scale(fingerLength2/wristLength2, fingerLength2/wristLength2, 1));

            // Render again.
            FrameBuffer fb = this.fbp.getFrameBuffer();
            fb.clearFB(Color.green);
            Pipeline.render(scene, fb.vp);
            fbp.update();
            repaint();
         }

         // Implement part of the ComponentListener interface.
         @Override public void componentResized(ComponentEvent e)
         {
          //System.out.println( e );

            // Get the new size of the FrameBufferPanel.
            int w = this.fbp.getWidth();
            int h = this.fbp.getHeight();

            // Create a new FrameBuffer that fits the new window size.
            FrameBuffer fb = new FrameBuffer(w, h);
            this.fbp.setFrameBuffer(fb);
            fb.clearFB(Color.green);
            Pipeline.render(scene, fb.vp);
            fbp.update();
            repaint();
         }
      };
   }
}
