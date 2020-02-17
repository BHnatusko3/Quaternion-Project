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
   Draw an animation of a solar system with a sun, planet, and moon.
<p>
   In this version, the planet orbits around the sun at the same rate as the sun
   rotates on its axis. Similarly, the moon orbits around the planet at the same
   rate as the planet rotates on its axis.
<p>
   But the orbit of the planet should be independent of the rotation of the sun,
   and the orbit of the moon should be independent of the rotation of the planet.
<pre>{@code
              Scene
                |
                |
             Position
            /   |     \
           /    |      \
     Matrix   Model     nested Positions
       R      (sun)          |
                             |
                          Position
                         /   |   \
                        /    |    \
                  Matrix   Model   nested Positions
                    TR    (planet)      |
                                        |
                                     Position
                                    /   |    \
                                   /    |     \
                               Matrix  Model    nested Positions
                                 TR   (moon)        |
                                                    |
                                                  empty
}</pre>
*/
public class SolarSystem
{
   private static int fps = 20;

   private static double planetOrbitRadius = 5.0;
   private static double   moonOrbitRadius = 1.0;

   private static double planetOrbitRot = 0.0;
   private static double   moonOrbitRot = 0.0;

   private static double  moonAxisRot = 0.0;

   private static double ecliptic = 7.0; // angle of the ecliptic plane

   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the f/F keys to slow down or speed up the frame rate.");
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the e/E keys to change the angle of the ecliptic plane.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }

   public static void main(String[] args)
   {
      print_help_message();

      /*
         See the above picture of the tree that this code creates.
      */

      // Create the Scene object that we shall render
      Scene scene = new Scene();  // A Solar System

      // Create the sun.
      scene.addPosition(new Position(new Sphere(1.0, 10, 10)));
      ModelShading.setColor(scene.getPosition(0).model, Color.yellow);
      Position sun = scene.getPosition(0);

      // Create the planet.
      sun.addNestedPosition(new Position(new Sphere(0.5, 10, 10)));
      ModelShading.setColor(sun.getNestedPosition(0).model, Color.blue);
      Position planetMoon = sun.getNestedPosition(0);

      // Create the planet's moon.
      planetMoon.addNestedPosition(new Position(new Sphere(0.2, 10, 10)));
      ModelShading.setColor(planetMoon.getNestedPosition(0).model, Color.green);
      Position moon = planetMoon.getNestedPosition(0);


      // Define initial dimensions for a FrameBuffer.
      int width  = 1024;
      int height = 1024;

      // Create an AnimationFrame containing a FrameBuffer
      // with the given dimensions.
      @SuppressWarnings("serial")
      InteractiveFrame app = new AnimationFrame("Renderer 8", width, height, fps)
      {
         // Implement the ActionListener interface.
         @Override public void actionPerformed(ActionEvent e)
         {
            //System.out.println( e );

            // Push the solar system away from where the camera is.
            sun.matrix2Identity();
            sun.matrix.mult(Matrix.translate(0, 0, -8));
            // Rotate the plane of the ecliptic
            // (rotate the sun's xz-plane about the x-axis).
            sun.matrix.mult(Matrix.rotateX(ecliptic));

            // Rotate the sun on it axis.
            sun.matrix.mult(Matrix.rotateY(planetOrbitRot));

            // Place the planet-moon away from the sun and rotate the planet-moon on its axis.
            planetMoon.matrix2Identity();
            planetMoon.matrix.mult(Matrix.translate(planetOrbitRadius, 0, 0));
            planetMoon.matrix.mult(Matrix.rotateY(moonOrbitRot));

            // Place the moon away from the planet and rotate the moon on its axis.
            moon.matrix2Identity();
            moon.matrix.mult(Matrix.translate(moonOrbitRadius, 0, 0));
            moon.matrix.mult(Matrix.rotateY(moonAxisRot));

            // Update the parameters for the next frame.
            planetOrbitRot += 1.0;
            moonOrbitRot += 5.0;
            moonAxisRot -= 10.0;

            // Render again.
            FrameBuffer fb = this.fbp.getFrameBuffer();
            fb.clearFB(Color.black);
            Pipeline.render(scene, fb.vp);
            fbp.update();
            repaint();
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
            else if ('f' == c)
            {
               fps -= 1;
               if (0 > fps) fps = 0;
               System.out.println("fps = " + fps);
            }
            else if ('F' == c)
            {
               fps += 1;
               System.out.println("fps = " + fps);
            }
            else if ('p' == c)
            {
               scene.camera.perspective = ! scene.camera.perspective;
            }
            else if ('e' == c)
            {
               ecliptic -= 1;
               if (0 == fps) fps = 1;
               System.out.println("ecliptic = " + ecliptic);
            }
            else if ('E' == c)
            {
               ecliptic += 1;
               System.out.println("ecliptic = " + ecliptic);
            }

            setFPS(fps);

            // Set up the camera's view volume.
            if (scene.camera.perspective)
            {
               scene.camera.projPerspective(-1, 1, -1, 1, 1);
            }
            else
            {
               scene.camera.projOrtho(-6, 6, -6, 6);
            }
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
            fb.clearFB(Color.black);
            Pipeline.render(scene, fb.vp);
            fbp.update();
            repaint();
         }
      };
   }
}
