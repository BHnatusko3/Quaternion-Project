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
   Draw two interactive robot arms each with one shoulder,
   two elbow, two wrist, and two finger joints.
<p>
   Here is a simplified version of this program's scene graph.
<p>
<pre>{@code
                               Scene
                               /   \
                 /------------/     \-----------------------\
                /                                            \
        Position                                              Position
        / | \   \                                             / | \   \
       /  |  \   \--------------------\                      /  |  \   \--------------------\
 Matrix   /   \                        \               Matrix  /    \                        \
  TRS    /  Position                 Position           TRS   /  Position                 Position
        /     / |  \                  /  |  \                /     / |  \                  /  |  \
       /     /  |   \                /   |   \              /     /  |   \                /   |   \
      / Matrix  |    \          Matrix   |    \            / Matrix  |    \          Matrix   |    \
     |   TRS   /    Position     TRS    /  Position       |   TRS   /    Position     TRS    /  Position
     |        /      / |  \            /    / |   \       |        /      / |  \            /    / |   \
     |       /      /  |   \          /    /  |    \      |       /      /  |   \          /    /  |    \
      \     /  Matrix  |    \        / Matrix |     \     |      |  Matrix  |    \        / Matrix |     \
       \   |    TRS   /   Position  |   TRS   /  Position |      |   TRS   /   Position  |   TRS   /  Position
        \   \        /      /  |    |        /     /    | |      |        /      /  |    |        /     /  |
         \   \      /      /   |    |       |     /     | |      |       /      /   |    |       /     /   |
          \   \    |  Matrix   |    |       | Matrix    | |      |      /  Matrix   |    |      /  Matrix  |
           \   \    \  TRS     |    |       |  TRS      | |      |     /    TRS     |    |     /    TRS   /
            \   \    \         |    |       |           | |      |    /             /    |    /          /
             \   \    \        |    |       \----\      | |      /   /             /     /   /          /
              \   \    \       |    \-------------\     | |     /---/             /     /   /          /
               \   \    \      \-------------------\    | |    /-----------------/     /   /          /
                \   \    \--------------------------\   | |   /-----------------------/   /          /
                 \   \-------------------------------\  | |  /---------------------------/          /
                  \-----------------------------------\ | | /--------------------------------------/
                                                       Model
                                                     armSegment
</pre>
*/
public class RobotArms3
{
   private static double[] xTranslation = {0.0,  0.0};
   private static double[] yTranslation = {0.5, -0.5};

   private static double[] shoulderRotation = {0.0, 0.0};
   private static double[]   elbowRotation1 = { 15,  15};
   private static double[]   elbowRotation2 = {-15, -15};
   private static double[]   wristRotation1 = {0.0, 0.0};
   private static double[]   wristRotation2 = {0.0, 0.0};
   private static double[]  fingerRotation1 = {0.0, 0.0};
   private static double[]  fingerRotation2 = {0.0, 0.0};

   private static double[] shoulderLength = {0.4, 0.4};
   private static double[]   elbowLength1 = {0.3, 0.3};
   private static double[]   elbowLength2 = {0.3, 0.3};
   private static double[]   wristLength1 = {0.2, 0.2};
   private static double[]   wristLength2 = {0.2, 0.2};
   private static double[]  fingerLength1 = {0.1, 0.1};
   private static double[]  fingerLength2 = {0.1, 0.1};

   private static int currentArm = 0;
   private static Position[] arm_p;
   private static Position[] elbow1_p;
   private static Position[] elbow2_p;
   private static Position[] wrist1_p;
   private static Position[] wrist2_p;
   private static Position[] finger1_p;
   private static Position[] finger2_p;

   private static void print_help_message()
   {
      //System.out.println("Use the 'd' key to toggle debugging information on and off.");
      //System.out.println("Use the 'a' key to toggle antialiasing on and off.");
      System.out.println("Use the '/' key to toggle between the the two robot arms.");
      System.out.println();
      System.out.println("Use the s/S keys to rotate an arm at the shoulder.");
      System.out.println();
      System.out.println("Use the e/E keys to rotate an arm at elbow 1.");
      System.out.println("Use the w/W keys to rotate an arm at wrist 1.");
      System.out.println("Use the f/F keys to rotate an arm at finger 1.");
      System.out.println();
      System.out.println("Use the d/D keys to rotate an arm at elbow 2.");
      System.out.println("Use the a/A keys to rotate an arm at wrist 2.");
      System.out.println("Use the c/C keys to rotate an arm at finger 2.");
      System.out.println();
      System.out.println("Use the ^s/^S keys to extend the length of an arm at the shoulder.");
      System.out.println();
      System.out.println("Use the ^e/^E keys to extend the length of an arm at elbow 1.");
      System.out.println("Use the ^w/^W keys to extend the length of an arm at wrist 1.");
      System.out.println("Use the ^f/^F keys to extend the length of an arm at finger 1.");
      System.out.println();
      System.out.println("Use the ^d/^D keys to extend the length of an arm at elbow 2.");
      System.out.println("Use the ^a/^A keys to extend the length of an arm at wrist 2.");
      System.out.println("Use the ^c/^C keys to extend the length of an arm at finger 2.");
      System.out.println();
      System.out.println("Use the x/X keys to translate an arm along the x-axis.");
      System.out.println("Use the y/Y keys to translate an arm along the y-axis.");
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
      Position arm1 = new Position();
      Position arm2 = new Position();

      // Add the Position objects to the scene.
      scene.addPosition(arm1, arm2);

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
      arm1.model = armSegment;

      // two elbows
      Position arm1_e1 = new Position(armSegment);
      Position arm1_e2 = new Position(armSegment);
      arm1.addNestedPosition(arm1_e1);
      arm1.addNestedPosition(arm1_e2);

     // two wrists
      Position arm1_w1 = new Position(armSegment);
      Position arm1_w2 = new Position(armSegment);
      arm1_e1.addNestedPosition(arm1_w1);
      arm1_e2.addNestedPosition(arm1_w2);

      // two fingers
      Position arm1_f1 = new Position(armSegment);
      Position arm1_f2 = new Position(armSegment);
      arm1_w1.addNestedPosition(arm1_f1);
      arm1_w2.addNestedPosition(arm1_f2);

      // Second arm.
      arm2.model = armSegment;

      // two elbows
      Position arm2_e1 = new Position(armSegment);
      Position arm2_e2 = new Position(armSegment);
      arm2.addNestedPosition(arm2_e1);
      arm2.addNestedPosition(arm2_e2);

     // two wrists
      Position arm2_w1 = new Position(armSegment);
      Position arm2_w2 = new Position(armSegment);
      arm2_e1.addNestedPosition(arm2_w1);
      arm2_e2.addNestedPosition(arm2_w2);

      // two fingers
      Position arm2_f1 = new Position(armSegment);
      Position arm2_f2 = new Position(armSegment);
      arm2_w1.addNestedPosition(arm2_f1);
      arm2_w2.addNestedPosition(arm2_f2);

      arm_p     = new Position[] {arm1,    arm2};
      elbow1_p  = new Position[] {arm1_e1, arm2_e1};
      elbow2_p  = new Position[] {arm1_e2, arm2_e2};
      wrist1_p  = new Position[] {arm1_w1, arm2_w1};
      wrist2_p  = new Position[] {arm1_w2, arm2_w2};
      finger1_p = new Position[] {arm1_f1, arm2_f1};
      finger2_p = new Position[] {arm1_f2, arm2_f2};

      // Initialize the nested matrices for the sub models.
      // First arm.
      arm_p[0].matrix.mult(Matrix.translate(xTranslation[0], yTranslation[0], -1));
      arm_p[0].matrix.mult(Matrix.scale(shoulderLength[0], shoulderLength[0], 1));

      elbow1_p[0].matrix.mult(Matrix.translate(1, 0, 0));
      elbow1_p[0].matrix.mult(Matrix.rotateZ(elbowRotation1[0]));
      elbow1_p[0].matrix.mult(Matrix.scale(elbowLength1[0]/shoulderLength[0],
                                           elbowLength1[0]/shoulderLength[0],
                                           1));

      elbow2_p[0].matrix.mult(Matrix.translate(1, 0, 0));
      elbow2_p[0].matrix.mult(Matrix.rotateZ(elbowRotation2[0]));
      elbow2_p[0].matrix.mult(Matrix.scale(elbowLength2[0]/shoulderLength[0],
                                           elbowLength2[0]/shoulderLength[0],
                                           1));

      wrist1_p[0].matrix.mult(Matrix.translate(1, 0, 0));
      wrist1_p[0].matrix.mult(Matrix.scale(wristLength1[0]/elbowLength1[0],
                                           wristLength1[0]/elbowLength1[0],
                                           1));

      wrist2_p[0].matrix.mult(Matrix.translate(1, 0, 0));
      wrist2_p[0].matrix.mult(Matrix.scale(wristLength2[0]/elbowLength2[0],
                                           wristLength2[0]/elbowLength2[0],
                                           1));

      finger1_p[0].matrix.mult(Matrix.translate(1, 0, 0));
      finger1_p[0].matrix.mult(Matrix.scale(fingerLength1[0]/wristLength1[0],
                                            fingerLength1[0]/wristLength1[0],
                                            1));

      finger2_p[0].matrix.mult(Matrix.translate(1, 0, 0));
      finger2_p[0].matrix.mult(Matrix.scale(fingerLength2[0]/wristLength2[0],
                                            fingerLength2[0]/wristLength2[0],
                                            1));

      // Second arm.
      arm_p[1].matrix.mult(Matrix.translate(xTranslation[1], yTranslation[1], -1));
      arm_p[1].matrix.mult(Matrix.scale(shoulderLength[1], shoulderLength[1], 1));

      elbow1_p[1].matrix.mult(Matrix.translate(1, 0, 0));
      elbow1_p[1].matrix.mult(Matrix.rotateZ(elbowRotation1[1]));
      elbow1_p[1].matrix.mult(Matrix.scale(elbowLength1[1]/shoulderLength[1],
                                           elbowLength1[1]/shoulderLength[1],
                                           1));

      elbow2_p[1].matrix.mult(Matrix.translate(1, 0, 0));
      elbow2_p[1].matrix.mult(Matrix.rotateZ(elbowRotation2[1]));
      elbow2_p[1].matrix.mult(Matrix.scale(elbowLength2[1]/shoulderLength[1],
                                           elbowLength2[1]/shoulderLength[1],
                                           1));

      wrist1_p[1].matrix.mult(Matrix.translate(1, 0, 0));
      wrist1_p[1].matrix.mult(Matrix.scale(wristLength1[1]/elbowLength1[1],
                                           wristLength1[1]/elbowLength1[1],
                                           1));

      wrist2_p[1].matrix.mult(Matrix.translate(1, 0, 0));
      wrist2_p[1].matrix.mult(Matrix.scale(wristLength2[1]/elbowLength2[1],
                                           wristLength2[1]/elbowLength2[1],
                                           1));

      finger1_p[1].matrix.mult(Matrix.translate(1, 0, 0));
      finger1_p[1].matrix.mult(Matrix.scale(fingerLength1[1]/wristLength1[1],
                                            fingerLength1[1]/wristLength1[1],
                                            1));

      finger2_p[1].matrix.mult(Matrix.translate(1, 0, 0));
      finger2_p[1].matrix.mult(Matrix.scale(fingerLength2[1]/wristLength2[1],
                                            fingerLength2[1]/wristLength2[1],
                                            1));


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
               shoulderLength[currentArm] -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 83) // ^s
            {
               shoulderLength[currentArm] += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 69) // ^E
            {
               elbowLength1[currentArm] -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 69) // ^e
            {
               elbowLength1[currentArm] += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 87) // ^W
            {
               wristLength1[currentArm] -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 87)  // ^w
            {
               wristLength1[currentArm] += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 70) // ^F
            {
               fingerLength1[currentArm] -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 70) // ^f
            {
               fingerLength1[currentArm] += 0.02;
            }

            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 68) // ^D
            {
               elbowLength2[currentArm] -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 68) // ^d
            {
               elbowLength2[currentArm] += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 65) // ^A
            {
               wristLength2[currentArm] -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 65)  // ^a
            {
               wristLength2[currentArm] += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 67) // ^C
            {
               fingerLength2[currentArm] -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 67) // ^c
            {
               fingerLength2[currentArm] += 0.02;
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
            else if ('/' == c)
            {
               currentArm = (currentArm + 1) % 2;
            }
            else if ('x' == c)
            {
               xTranslation[currentArm] += 0.02;
            }
            else if ('X' == c)
            {
               xTranslation[currentArm] -= 0.02;
            }
            else if ('y' == c)
            {
               yTranslation[currentArm] += 0.02;
            }
            else if ('Y' == c)
            {
               yTranslation[currentArm] -= 0.02;
            }
            else if ('s' == c)
            {
               shoulderRotation[currentArm] += 2.0;
            }
            else if ('S' == c)
            {
               shoulderRotation[currentArm] -= 2.0;
            }
            else if ('e' == c)
            {
               elbowRotation1[currentArm] += 2.0;
            }
            else if ('E' == c)
            {
               elbowRotation1[currentArm] -= 2.0;
            }
            else if ('w' == c)
            {
               wristRotation1[currentArm] += 2.0;
            }
            else if ('W' == c)
            {
               wristRotation1[currentArm] -= 2.0;
            }
            else if ('f' == c)
            {
               fingerRotation1[currentArm] += 2.0;
            }
            else if ('F' == c)
            {
               fingerRotation1[currentArm] -= 2.0;
            }
            else if ('d' == c)
            {
               elbowRotation2[currentArm] += 2.0;
            }
            else if ('D' == c)
            {
               elbowRotation2[currentArm] -= 2.0;
            }
            else if ('a' == c)
            {
               wristRotation2[currentArm] += 2.0;
            }
            else if ('A' == c)
            {
               wristRotation2[currentArm] -= 2.0;
            }
            else if ('c' == c)
            {
               fingerRotation2[currentArm] += 2.0;
            }
            else if ('C' == c)
            {
               fingerRotation2[currentArm] -= 2.0;
            }

            // Update the nested matrices for the sub models.
            arm_p[currentArm].matrix2Identity();
            arm_p[currentArm].matrix.mult(Matrix.translate(xTranslation[currentArm],
                                                           yTranslation[currentArm],
                                                           -1));
            arm_p[currentArm].matrix.mult(Matrix.rotateZ(shoulderRotation[currentArm]));
            arm_p[currentArm].matrix.mult(Matrix.scale(shoulderLength[currentArm],
                                                       shoulderLength[currentArm],
                                                       1));

            elbow1_p[currentArm].matrix2Identity();
            elbow1_p[currentArm].matrix.mult(Matrix.translate(1, 0, 0));
            elbow1_p[currentArm].matrix.mult(Matrix.rotateZ(elbowRotation1[currentArm]));
            elbow1_p[currentArm].matrix.mult(Matrix.scale(elbowLength1[currentArm]/shoulderLength[currentArm],
                                                          elbowLength1[currentArm]/shoulderLength[currentArm],
                                                          1));

            elbow2_p[currentArm].matrix2Identity();
            elbow2_p[currentArm].matrix.mult(Matrix.translate(1, 0, 0));
            elbow2_p[currentArm].matrix.mult(Matrix.rotateZ(elbowRotation2[currentArm]));
            elbow2_p[currentArm].matrix.mult(Matrix.scale(elbowLength2[currentArm]/shoulderLength[currentArm],
                                                          elbowLength2[currentArm]/shoulderLength[currentArm],
                                                          1));

            wrist1_p[currentArm].matrix2Identity();
            wrist1_p[currentArm].matrix.mult(Matrix.translate(1, 0, 0));
            wrist1_p[currentArm].matrix.mult(Matrix.rotateZ(wristRotation1[currentArm]));
            wrist1_p[currentArm].matrix.mult(Matrix.scale(wristLength1[currentArm]/elbowLength1[currentArm],
                                                          wristLength1[currentArm]/elbowLength1[currentArm],
                                                          1));

            wrist2_p[currentArm].matrix2Identity();
            wrist2_p[currentArm].matrix.mult(Matrix.translate(1, 0, 0));
            wrist2_p[currentArm].matrix.mult(Matrix.rotateZ(wristRotation2[currentArm]));
            wrist2_p[currentArm].matrix.mult(Matrix.scale(wristLength2[currentArm]/elbowLength2[currentArm],
                                                          wristLength2[currentArm]/elbowLength2[currentArm],
                                                          1));

            finger1_p[currentArm].matrix2Identity();
            finger1_p[currentArm].matrix.mult(Matrix.translate(1, 0, 0));
            finger1_p[currentArm].matrix.mult(Matrix.rotateZ(fingerRotation1[currentArm]));
            finger1_p[currentArm].matrix.mult(Matrix.scale(fingerLength1[currentArm]/wristLength1[currentArm],
                                                           fingerLength1[currentArm]/wristLength1[currentArm],
                                                           1));

            finger2_p[currentArm].matrix2Identity();
            finger2_p[currentArm].matrix.mult(Matrix.translate(1, 0, 0));
            finger2_p[currentArm].matrix.mult(Matrix.rotateZ(fingerRotation2[currentArm]));
            finger2_p[currentArm].matrix.mult(Matrix.scale(fingerLength2[currentArm]/wristLength2[currentArm],
                                                           fingerLength2[currentArm]/wristLength2[currentArm],
                                                           1));

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
   }//main()
}//RobotArms3
