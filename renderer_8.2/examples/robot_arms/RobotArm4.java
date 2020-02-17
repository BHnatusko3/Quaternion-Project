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
   Draw an interactive robot arm with shoulder, elbow, wrist, and finger joints.
   Make each part of the robot arm extendable.
   Make the robot arm translatable in the x and y directions.
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
  Camera   List<Position>
               |
               |
            Position
            /  |    \
           /   |     \
     Matrix    |     List<Position>
      TRS      |           |
               |           |
              /        Position
             /         /   |   \
            /         /    |    \
           /     Matrix   /      List<Position>
          |       TRS    /             |
          |             /              |
          |            /            Position
          |           /             /  |   \
          |          /             /   |    \
          |         /        Matrix    |     List<Position>
          |        /          TRS      |           |
          |       /                   /            |
           \     /                   /          Position
            Model ------------------/           /  |   \
          armSegment                           /   |    \
                    \                    Matrix    |     List<Position>
                     \                    TRS     /             |
                      \                          /            empty
                       \------------------------/

</pre>
*/
public class RobotArm4
{
   private static double xTranslation = 0.0;
   private static double yTranslation = 0.0;

   private static double shoulderRotation = 0.0;
   private static double    elbowRotation = 0.0;
   private static double    wristRotation = 0.0;
   private static double   fingerRotation = 0.0;

   private static double shoulderLength = 0.4;
   private static double    elbowLength = 0.3;
   private static double    wristLength = 0.2;
   private static double   fingerLength = 0.1;

   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the 'a' key to toggle antialiasing on and off.");
      System.out.println("Use the s/S keys to rotate the arm at the shoulder.");
      System.out.println("Use the e/E keys to rotate the arm at the elbow.");
      System.out.println("Use the w/W keys to rotate the arm at the wrist.");
      System.out.println("Use the f/F keys to rotate the arm at the finger.");
      System.out.println("Use the ^s/^S keys to extend the length of the arm at the shoulder.");
      System.out.println("Use the ^e/^E keys to extend the length of the arm at the elbow.");
      System.out.println("Use the ^w/^W keys to extend the length of the arm at the wrist.");
      System.out.println("Use the ^f/^F keys to extend the length of the arm at the finger.");
      System.out.println("Use the x/X keys to translate the arm along the x-axis.");
      System.out.println("Use the y/Y keys to translate the arm along the y-axis.");
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

      // Push the position away from where the camera is.
      arm_p.matrix  = Matrix.translate(0, 0, -1);

      /*
         Create the scene graph.
      */
      // Create one Model that can be used
      // for each part of the robot arm.
      Vertex v0 = new Vertex(0, 0, 0);
      Vertex v1 = new Vertex(1, 0, 0);
      Model armSegment = new Model("arm segment");
      armSegment.addVertex(v0, v1);
      armSegment.addLineSegment(new LineSegment(0, 1));
      ModelShading.setColor(armSegment, Color.blue);

      // Add the armSegment Model to the Scene's Position.
      arm_p.model = armSegment;

      Position elbow_p = new Position(armSegment);
      arm_p.addNestedPosition(elbow_p);

      Position wrist_p = new Position(armSegment);
      elbow_p.addNestedPosition(wrist_p);

      Position finger_p = new Position(armSegment);
      wrist_p.addNestedPosition(finger_p);

      // Initialize the nested matrices for the sub models.
      arm_p.matrix.mult(Matrix.scale(shoulderLength, shoulderLength, 1));

      elbow_p.matrix.mult(Matrix.translate(1, 0, 0));
      elbow_p.matrix.mult(Matrix.scale(elbowLength/shoulderLength, elbowLength/shoulderLength, 1));

      wrist_p.matrix.mult(Matrix.translate(1, 0, 0));
      wrist_p.matrix.mult(Matrix.scale(wristLength/elbowLength, wristLength/elbowLength, 1));

      finger_p.matrix.mult(Matrix.translate(1, 0, 0));
      finger_p.matrix.mult(Matrix.scale(fingerLength/wristLength, fingerLength/wristLength, 1));


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
               shoulderLength -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 83) // ^s
            {
               shoulderLength += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 69) // ^E
            {
               elbowLength -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 69) // ^e
            {
               elbowLength += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 87) // ^W
            {
               wristLength -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 87)  // ^w
            {
               wristLength += 0.02;
            }
            else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == 70) // ^F
            {
               fingerLength -= 0.02;
            }
            else if (e.isControlDown() && e.getKeyCode() == 70) // ^f
            {
               fingerLength += 0.02;
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
            else if ('d' == c)
            {
               Pipeline.debug = ! Pipeline.debug;
             //Clip.debug = ! Clip.debug;
             //RasterizeAntialias.debug = ! RasterizeAntialias.debug;
            }
            else if ('a' == c)
            {
               RasterizeAntialias.doAntialiasing = ! RasterizeAntialias.doAntialiasing;
            }
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
               elbowRotation += 2.0;
            }
            else if ('E' == c)
            {
               elbowRotation -= 2.0;
            }
            else if ('w' == c)
            {
               wristRotation += 2.0;
            }
            else if ('W' == c)
            {
               wristRotation -= 2.0;
            }
            else if ('f' == c)
            {
               fingerRotation += 2.0;
            }
            else if ('F' == c)
            {
               fingerRotation -= 2.0;
            }

            // Update the nested matrices for the sub models.
            arm_p.matrix2Identity();
            arm_p.matrix.mult(Matrix.translate(xTranslation, yTranslation, -1));
            arm_p.matrix.mult(Matrix.rotateZ(shoulderRotation));
            arm_p.matrix.mult(Matrix.scale(shoulderLength, shoulderLength, 1));

            elbow_p.matrix2Identity();
            elbow_p.matrix.mult(Matrix.translate(1, 0, 0));
            elbow_p.matrix.mult(Matrix.rotateZ(elbowRotation));
            elbow_p.matrix.mult(Matrix.scale(elbowLength/shoulderLength, elbowLength/shoulderLength, 1));

            wrist_p.matrix2Identity();
            wrist_p.matrix.mult(Matrix.translate(1, 0, 0));
            wrist_p.matrix.mult(Matrix.rotateZ(wristRotation));
            wrist_p.matrix.mult(Matrix.scale(wristLength/elbowLength, wristLength/elbowLength, 1));

            finger_p.matrix2Identity();
            finger_p.matrix.mult(Matrix.translate(1, 0, 0));
            finger_p.matrix.mult(Matrix.rotateZ(fingerRotation));
            finger_p.matrix.mult(Matrix.scale(fingerLength/wristLength, fingerLength/wristLength, 1));

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
}//RobotArm4
