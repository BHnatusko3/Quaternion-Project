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
     Matrix  Model   List<Position>
       R      arm          |
                           |
                       Position
                       /   |   \
                      /    |    \
                 Matrix  Model   List<Position>
                  TR     elbow         |
                                       |
                                    Position
                                    /  |   \
                                   /   |    \
                             Matrix  Model   List<Position>
                              TR     wrist         |
                                                   |
                                                Position
                                                /  |   \
                                               /   |    \
                                         Matrix  Model   List<Position>
                                          TR     finger         |
                                                              empty
</pre>
*/
public class RobotArm1
{
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
      Model arm = new Model("shoulder");
      arm.addVertex(new Vertex(0, 0, 0));
      arm.addVertex(new Vertex(shoulderLength, 0, 0));
      arm.addLineSegment(new LineSegment(0, 1));
      ModelShading.setColor(arm, Color.blue);
      // Add the arm Model to the Scene's Position.
      arm_p.model = arm;

      Model elbow = new Model("elbow");
      elbow.addVertex(new Vertex(0, 0, 0));
      elbow.addVertex(new Vertex(elbowLength, 0, 0));
      elbow.addLineSegment(new LineSegment(0, 1));
      ModelShading.setColor(elbow, Color.blue);
      Position elbow_p = new Position(elbow);
      arm_p.addNestedPosition(elbow_p);

      Model wrist = new Model("wrist");
      wrist.addVertex(new Vertex(0, 0, 0));
      wrist.addVertex(new Vertex(wristLength, 0, 0));
      wrist.addLineSegment(new LineSegment(0, 1));
      ModelShading.setColor(wrist, Color.blue);
      Position wrist_p = new Position(wrist);
      elbow_p.addNestedPosition(wrist_p);

      Model finger = new Model("finger");
      finger.addVertex(new Vertex(0, 0, 0));
      finger.addVertex(new Vertex(fingerLength, 0, 0));
      finger.addLineSegment(new LineSegment(0, 1));
      ModelShading.setColor(finger, Color.blue);
      Position finger_p = new Position(finger);
      wrist_p.addNestedPosition(finger_p);

      // Initialize the nested matrices for the sub models.
      elbow_p.matrix  = Matrix.translate(shoulderLength, 0, 0);
      wrist_p.matrix  = Matrix.translate(elbowLength,    0, 0);
      finger_p.matrix = Matrix.translate(wristLength,    0, 0);


      // Define initial dimensions for a FrameBuffer.
      int width  = 512;
      int height = 512;

      // Create an InteractiveFrame containing a FrameBuffer
      // with the given dimensions.
      @SuppressWarnings("serial")
      InteractiveFrame app = new InteractiveFrame("Renderer 8", width, height)
      {
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
            arm_p.matrix.mult(Matrix.translate(0, 0, -1));
            arm_p.matrix.mult(Matrix.rotateZ(shoulderRotation));

            elbow_p.matrix2Identity();
            elbow_p.matrix.mult(Matrix.translate(shoulderLength, 0, 0));
            elbow_p.matrix.mult(Matrix.rotateZ(elbowRotation));

            wrist_p.matrix2Identity();
            wrist_p.matrix.mult(Matrix.translate(elbowLength, 0, 0));
            wrist_p.matrix.mult(Matrix.rotateZ(wristRotation));

            finger_p.matrix2Identity();
            finger_p.matrix.mult(Matrix.translate(wristLength, 0, 0));
            finger_p.matrix.mult(Matrix.rotateZ(fingerRotation));

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
}//RobotArm1
