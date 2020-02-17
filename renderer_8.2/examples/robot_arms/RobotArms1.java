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
   Draw an interactive robot arm with one shoulder,
   two elbow, two wrist, and two finger joints.
<p>
   Here is a simplified version of this program's scene graph.
<p>
<pre>{@code
              Scene
                |
                |
            Position
            / | \   \
           /  |  \   \--------------------\
     Matrix   |   \                        \
      TRS    /  Position                 Position
            /     / |  \                  /  |  \
           /     /  |   \                /   |   \
          / Matrix  |    \          Matrix   |    \
         /   TRS   /    Position     TRS    /  Position
         \        /      / |  \            /    / |   \
          \      /      /  |   \          /    /  |    \
           \    /  Matrix  |    \        / Matrix |     \
            \   \   TRS   /   Position  |   TRS   /   Position
             \   \       /      /  |    |        /     /  |
              \   \     /      /   |    |       /     /   |
               \   \   /  Matrix   |    |      /  Matrix  |
                \   \  \   TRS     |    |     /    TRS   /
                 \   \  \          |    |    /          /
                  \   \  \-----\   |    /   /          /
                   \   \--------\  |   /---/          /
                    \------------\ |  /--------------/
                                 Model
                               armSegment
</pre>
*/
public class RobotArms1
{
   private static double xTranslation = 0.0;
   private static double yTranslation = 0.0;

   private static double shoulderRotation =  0.0;
   private static double   elbowRotation1 =  15.0;
   private static double   elbowRotation2 = -15.0;
   private static double   wristRotation1 =  0.0;
   private static double   wristRotation2 =  0.0;
   private static double  fingerRotation1 =  0.0;
   private static double  fingerRotation2 =  0.0;

   private static double  shoulderLength = 0.4;
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
      System.out.println("Use the s/S keys to rotate the arm at the shoulder.");
      System.out.println();
      System.out.println("Use the e/E keys to rotate the arm at elbow 1.");
      System.out.println("Use the w/W keys to rotate the arm at wrist 1.");
      System.out.println("Use the f/F keys to rotate the arm at finger 1.");
      System.out.println();
      System.out.println("Use the d/D keys to rotate the arm at elbow 2.");
      System.out.println("Use the a/A keys to rotate the arm at wrist 2.");
      System.out.println("Use the c/C keys to rotate the arm at finger 2.");
      System.out.println();
      System.out.println("Use the ^s/^S keys to extend the length of the arm at the shoulder.");
      System.out.println();
      System.out.println("Use the ^e/^E keys to extend the length of the arm at elbow 1.");
      System.out.println("Use the ^w/^W keys to extend the length of the arm at wrist 1.");
      System.out.println("Use the ^f/^F keys to extend the length of the arm at finger 1.");
      System.out.println();
      System.out.println("Use the ^d/^D keys to extend the length of the arm at elbow 2.");
      System.out.println("Use the ^a/^A keys to extend the length of the arm at wrist 2.");
      System.out.println("Use the ^c/^C keys to extend the length of the arm at finger 2.");
      System.out.println();
      System.out.println("Use the x/X keys to translate the arm along the x-axis.");
      System.out.println("Use the y/Y keys to translate the arm along the y-axis.");
      System.out.println();
      System.out.println("Use the 'h' key to redisplay this help message.");
   }


   public static void main(String[] args)
   {
      print_help_message();

      // Create the Scene object that we shall render
      Scene scene = new Scene();

      // Create a Position object that will hold the robot arm.
      Position arm_p = new Position();

      // Add the Position object to the scene.
      scene.addPosition(arm_p);

      /*
         Create the scene graph.
      */
      // Create one Model that can be used
      // for each part of the robot arms.
      Vertex v0 = new Vertex(0, 0, 0);
      Vertex v1 = new Vertex(1, 0, 0);
      Model armSegment = new Model("arm segment");
      armSegment.addVertex(v0, v1);
      armSegment.addLineSegment(new LineSegment(0, 1));
      ModelShading.setColor(armSegment, Color.blue);

      // Add the armSegment Model to the Scene's Position.
      arm_p.model = armSegment;

      // two elbows
      Position elbow1_p = new Position(armSegment);
      Position elbow2_p = new Position(armSegment);
      arm_p.addNestedPosition(elbow1_p);
      arm_p.addNestedPosition(elbow2_p);

     // two wrists
      Position wrist1_p = new Position(armSegment);
      Position wrist2_p = new Position(armSegment);
      elbow1_p.addNestedPosition(wrist1_p);
      elbow2_p.addNestedPosition(wrist2_p);

      // two fingers
      Position finger1_p = new Position(armSegment);
      Position finger2_p = new Position(armSegment);
      wrist1_p.addNestedPosition(finger1_p);
      wrist2_p.addNestedPosition(finger2_p);

      // Initialize the nested matrices for the sub models.
      arm_p.matrix2Identity();
      arm_p.matrix.mult(Matrix.translate(xTranslation, yTranslation, -1));
      arm_p.matrix.mult(Matrix.rotateZ(shoulderRotation));
      arm_p.matrix.mult(Matrix.scale(shoulderLength, shoulderLength, 1));

      elbow1_p.matrix2Identity();
      elbow1_p.matrix.mult(Matrix.translate(1, 0, 0));
      elbow1_p.matrix.mult(Matrix.rotateZ(elbowRotation1));
      elbow1_p.matrix.mult(Matrix.scale(elbowLength1/shoulderLength, elbowLength1/shoulderLength, 1));

      elbow2_p.matrix2Identity();
      elbow2_p.matrix.mult(Matrix.translate(1, 0, 0));
      elbow2_p.matrix.mult(Matrix.rotateZ(elbowRotation2));
      elbow2_p.matrix.mult(Matrix.scale(elbowLength2/shoulderLength, elbowLength2/shoulderLength, 1));

      wrist1_p.matrix2Identity();
      wrist1_p.matrix.mult(Matrix.translate(1, 0, 0));
      wrist1_p.matrix.mult(Matrix.rotateZ(wristRotation1));
      wrist1_p.matrix.mult(Matrix.scale(wristLength1/elbowLength1, wristLength1/elbowLength1, 1));

      wrist2_p.matrix2Identity();
      wrist2_p.matrix.mult(Matrix.translate(1, 0, 0));
      wrist2_p.matrix.mult(Matrix.rotateZ(wristRotation2));
      wrist2_p.matrix.mult(Matrix.scale(wristLength2/elbowLength2, wristLength2/elbowLength2, 1));

      finger1_p.matrix2Identity();
      finger1_p.matrix.mult(Matrix.translate(1, 0, 0));
      finger1_p.matrix.mult(Matrix.rotateZ(fingerRotation1));
      finger1_p.matrix.mult(Matrix.scale(fingerLength1/wristLength1, fingerLength1/wristLength1, 1));

      finger2_p.matrix2Identity();
      finger2_p.matrix.mult(Matrix.translate(1, 0, 0));
      finger2_p.matrix.mult(Matrix.rotateZ(fingerRotation2));
      finger2_p.matrix.mult(Matrix.scale(fingerLength2/wristLength2, fingerLength2/wristLength2, 1));


      // Define initial dimensions for a FrameBuffer.
      int width  = 512;
      int height = 512;

      // Create an InteractiveFrame containing a FrameBuffer
      // with the given dimensions.
      @SuppressWarnings("serial")
      InteractiveFrame app = new InteractiveFrame("Renderer 8", width, height)
      {
         @Override public void keyPressed(KeyEvent e)
         {
          //System.out.println( e );

            if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 83) // ^S
            {
               shoulderLength -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 83) // ^s
            {
               shoulderLength += 0.02;
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


         // Implement part of the KeyListener interface.
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
            //   Pipeline.debug = ! Pipeline.debug;
            // //Clip.debug = ! Clip.debug;
            // //RasterizeAntialias.debug = ! RasterizeAntialias.debug;
            //}
            //else if ('a' == c)
            //{
            //   RasterizeAntialias.doAntialiasing = ! RasterizeAntialias.doAntialiasing;
            //}
            else if ('x' == c)
            {
               xTranslation += 0.02;
            }
            else if ('X' == c)
            {
               xTranslation -= 0.02;
            }
            else if ('y' == c)
            {
               yTranslation += 0.02;
            }
            else if ('Y' == c)
            {
               yTranslation -= 0.02;
            }
            else if ('s' == c)
            {
               shoulderRotation += 2.0;
            }
            else if ('S' == c)
            {
               shoulderRotation -= 2.0;
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

            // Set the model matrices for the sub models.
            arm_p.matrix2Identity();
            arm_p.matrix.mult(Matrix.translate(xTranslation, yTranslation, -1));
            arm_p.matrix.mult(Matrix.rotateZ(shoulderRotation));
            arm_p.matrix.mult(Matrix.scale(shoulderLength, shoulderLength, 1));

            elbow1_p.matrix2Identity();
            elbow1_p.matrix.mult(Matrix.translate(1, 0, 0));
            elbow1_p.matrix.mult(Matrix.rotateZ(elbowRotation1));
            elbow1_p.matrix.mult(Matrix.scale(elbowLength1/shoulderLength, elbowLength1/shoulderLength, 1));

            elbow2_p.matrix2Identity();
            elbow2_p.matrix.mult(Matrix.translate(1, 0, 0));
            elbow2_p.matrix.mult(Matrix.rotateZ(elbowRotation2));
            elbow2_p.matrix.mult(Matrix.scale(elbowLength2/shoulderLength, elbowLength2/shoulderLength, 1));

            wrist1_p.matrix2Identity();
            wrist1_p.matrix.mult(Matrix.translate(1, 0, 0));
            wrist1_p.matrix.mult(Matrix.rotateZ(wristRotation1));
            wrist1_p.matrix.mult(Matrix.scale(wristLength1/elbowLength1, wristLength1/elbowLength1, 1));

            wrist2_p.matrix2Identity();
            wrist2_p.matrix.mult(Matrix.translate(1, 0, 0));
            wrist2_p.matrix.mult(Matrix.rotateZ(wristRotation2));
            wrist2_p.matrix.mult(Matrix.scale(wristLength2/elbowLength2, wristLength2/elbowLength2, 1));

            finger1_p.matrix2Identity();
            finger1_p.matrix.mult(Matrix.translate(1, 0, 0));
            finger1_p.matrix.mult(Matrix.rotateZ(fingerRotation1));
            finger1_p.matrix.mult(Matrix.scale(fingerLength1/wristLength1, fingerLength1/wristLength1, 1));

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
