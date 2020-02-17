/*

*/

import renderer.scene.*;
import renderer.models.ModelShading;
import renderer.pipeline.*;
import renderer.framebuffer.*;
import renderer.gui.*;

import java.awt.Color;
import java.awt.event.*;

/**
<pre>{@code
                   y
                   |
                   |
                   | v[4]
                 1 +-----------------+ v[5]=(1,1,0)
                  /|                /|
                /  |              /  |
              /    |            /    |
            /      |          /      |
      v[7] +-----------------+ v[6]  |
           |       |         |       |                    y
           |       |         |       |                    |
           |       |         |       | v[1]               |
           |  v[0] +---------|-------+------> x           |
           |      /          |      /1                    |
           |    /            |    /                       +-----> x
           |  /              |  /                        /
           |/                |/                         /
         1 +-----------------+                         /
         / v[3]=(0,0,1)      v[2]=(1,0,1)            z
       /
     /
    z
}</pre>
   Render a single colored wireframe cube.
<p>
   Each corner of the cube is represented by a single Vertex
   object. The three LineSegment objects that meet at each
   corner share the corner's single Vertex object. But we
   may not want the three line segments to have the same color
   at that corner. This cube model initially has the top
   four edges sharing one color object, the bottom four edges
   sharing another color object, and the four vertical edges
   sharing a third color object.
*/
@SuppressWarnings("serial")
public class InteractiveCube_R7 extends InteractiveFrame
{
   private boolean letterbox = false;
   private double aspectRatio = 1.0;
   private double near   =  1.0;
   private double left   = -1.0;
   private double right  =  1.0;
   private double bottom = -1.0;
   private double top    =  1.0;
   private boolean showCamera = false;
   private boolean showFBaspectRatio = false;

   private boolean showMatrix = false;
   private double xTranslation = 0.0;
   private double yTranslation = 0.0;
   private double zTranslation = 0.0;
   private double xRotation = 0.0;
   private double yRotation = 0.0;
   private double zRotation = 0.0;
   private double scale = 1.0;

   private Scene scene;

   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */
   public InteractiveCube_R7(String title, int fbWidth, int fbHeight)
   {
      super(title, fbWidth, fbHeight);

      // Create the Scene object that we shall render.
      scene = new Scene();

      // Create a Model object to hold the geometry.
      Model model = new Model("cube");

      // Create a Position for the Model.
      Position position = new Position(model);

      // Add the Position (and its Model) to the Scene.
      scene.addPosition(position);

      // Push the model away from where the camera is.
      position.matrix = Matrix.translate(0, 0, -2);

      // Create the vertices for the Model.
      model.addVertex(new Vertex(0.0, 0.0, 0.0), // four vertices around the bottom face
                      new Vertex(1.0, 0.0, 0.0),
                      new Vertex(1.0, 0.0, 1.0),
                      new Vertex(0.0, 0.0, 1.0),
                      new Vertex(0.0, 1.0, 0.0), // four vertices around the top face
                      new Vertex(1.0, 1.0, 0.0),
                      new Vertex(1.0, 1.0, 1.0),
                      new Vertex(0.0, 1.0, 1.0));

      // Create three colors, one color for the top edges,
      // one color for the bottom edges, and
      // one color for the vertical edges.
      model.addColor(new Color(255,  0,   0 ),  // red, bottom
                     new Color( 0,  255,  0 ),  // green, top
                     new Color( 0,   0,  255)); // blue, vertical

      // Add the geometry with colors to the model.
      model.addLineSegment(new LineSegment(0, 1, 0),  // bottom face
                           new LineSegment(1, 2, 0),  // bottom face
                           new LineSegment(2, 3, 0),  // bottom face
                           new LineSegment(3, 0, 0),  // bottom face
                           new LineSegment(4, 5, 1),  // top face
                           new LineSegment(5, 6, 1),  // top face
                           new LineSegment(6, 7, 1),  // top face
                           new LineSegment(7, 4, 1),  // top face
                           new LineSegment(0, 4, 2),  // back face
                           new LineSegment(1, 5, 2),  // back face
                           new LineSegment(3, 7, 2),  // front face
                           new LineSegment(2, 6, 2)); // front face
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
         System.out.print("Anti-aliasing is turned ");
         System.out.println(RasterizeAntialias.doAntialiasing ? "On" : "Off");
      }
      else if ('g' == c)
      {
         RasterizeAntialias.doGamma = ! RasterizeAntialias.doGamma;
         System.out.print("Gamma correction is turned ");
         System.out.println(RasterizeAntialias.doGamma ? "On" : "Off");
      }
      else if ('p' == c)
      {
         scene.camera.perspective = ! scene.camera.perspective;
         String p = scene.camera.perspective ? "perspective" : "orthographic";
         System.out.println("Using " + p + " projection");
      }
      else if ('l' == c)
      {
         letterbox = ! letterbox;
         System.out.print("Letter boxing is turned ");
         System.out.println(letterbox ? "On" : "Off");
      }
      else if ('n' == c || 'N' == c)
      {
         // Move the camera's near plane.
         if ('n' == c)
         {
            near -= 0.01;
         }
         else
         {
            near += 0.01;
         }
      }
      else if ('r' == c || 'R' == c)
      {
         // Change the aspect ratio of the camera's view rectangle.
         if ('r' == c)
         {
            aspectRatio -= 0.1;
         }
         else
         {
            aspectRatio += 0.1;
         }

         // Adjust right and left.
         // (Keep the vertical field-of-view fixed.)
         right =  top * aspectRatio;
         left  = -right;
         System.out.printf("Aspect ratio (of camera's image rectangle) = %.2f\n", aspectRatio);
      }
      else if ('o' == c || 'O' == c)
      {
         // Change left, right, bottom, and top.
         // (Keep the aspect ratio fixed.)
         if ('o' == c)
         {
            left   += 0.1 * aspectRatio;
            right  -= 0.1 * aspectRatio;
            bottom += 0.1;
            top    -= 0.1;
         }
         else
         {
            left   -= 0.1 * aspectRatio;
            right  += 0.1 * aspectRatio;
            bottom -= 0.1;
            top    += 0.1;
         }
      }
      else if ('c' == c)
      {
         // Change the solid random color of the cube.
         ModelShading.setRandomColor(scene.positionList.get(0).model);
      }
      else if ('C' == c)
      {
         // Change each color in the cube to a random color.
         ModelShading.setRandomColors(scene.positionList.get(0).model);
      }
      else if ('e' == c && e.isAltDown())
      {
         // Change the random color of each vertex of the cube.
         ModelShading.setRandomVertexColors(scene.positionList.get(0).model);
      }
      else if ('e' == c)
      {
         // Change the solid random color of each edge of the cube.
         ModelShading.setRandomLineSegmentColors(scene.positionList.get(0).model);
      }
      else if ('E' == c)
      {
         // Change the random color of each end of each edge of the cube.
         ModelShading.setRainbowLineSegmentColors(scene.positionList.get(0).model);
      }
      else if ('f' == c)
      {
         showFBaspectRatio = ! showFBaspectRatio;
         if (showFBaspectRatio)
         {
            // Get the new size of the FrameBufferPanel.
            int w = this.fbp.getWidth();
            int h = this.fbp.getHeight();
            System.out.printf("Aspect ratio (of framebuffer) = %.2f\n", (double)w/(double)h);
         }
      }
      else if ('m' == c)
      {
         showMatrix = ! showMatrix;
      }
      else if ('M' == c)
      {
         showCamera = ! showCamera;
      }
      else if ('=' == c)
      {
         scale = 1.0;
         xTranslation = 0.0;
         yTranslation = 0.0;
         zTranslation = 0.0;
         xRotation = 0.0;
         yRotation = 0.0;
         zRotation = 0.0;
      }
      else if ('s' == c) // Scale the cube 10% smaller.
      {
         scale /= 1.1;
      }
      else if ('S' == c) // Scale the cube 10% larger.
      {
         scale *= 1.1;
      }
      else if ('x' == c)
      {
         xTranslation -= 0.1;
      }
      else if ('X' == c)
      {
         xTranslation += 0.1;
      }
      else if ('y' == c)
      {
         yTranslation -= 0.1;
      }
      else if ('Y' == c)
      {
         yTranslation += 0.1;
      }
      else if ('z' == c)
      {
         zTranslation -= 0.1;
      }
      else if ('Z' == c)
      {
         zTranslation += 0.1;
      }
      else if ('u' == c)
      {
         xRotation -= 2.0;
      }
      else if ('U' == c)
      {
         xRotation += 2.0;
      }
      else if ('v' == c)
      {
         yRotation -= 2.0;
      }
      else if ('V' == c)
      {
         yRotation += 2.0;
      }
      else if ('w' == c)
      {
         zRotation -= 2.0;
      }
      else if ('W' == c)
      {
         zRotation += 2.0;
      }

      // Set the model-to-view transformation matrix.
      // The order of the transformations is very important!
      Position cube_p = scene.positionList.get(0);
      // Push the cube away from where the camera is.
      cube_p.matrix = Matrix.translate(0, 0, -2);
      // Move the cube relative to its new position.
      cube_p.matrix.mult( Matrix.translate(xTranslation,
                                           yTranslation,
                                           zTranslation) );
      cube_p.matrix.mult( Matrix.rotateX(xRotation) );
      cube_p.matrix.mult( Matrix.rotateY(yRotation) );
      cube_p.matrix.mult( Matrix.rotateZ(zRotation) );
      cube_p.matrix.mult( Matrix.scale(scale) );

      // Set up the camera's view volume.
      if (scene.camera.perspective)
      {
         scene.camera.projPerspective(left, right, bottom, top, near);
      }
      else
      {
         scene.camera.projOrtho(left, right, bottom, top);
      }

      if (showCamera && ('M'==c
           ||'n'==c||'N'==c||'o'==c||'O'==c||'r'==c||'R'==c||'p'==c))
      {
         System.out.print( scene.camera );
      }

      if (showMatrix && ('m'==c||'='==c
           ||'s'==c||'x'==c||'y'==c||'z'==c||'u'==c||'v'==c||'w'==c
           ||'S'==c||'X'==c||'Y'==c||'Z'==c||'U'==c||'V'==c||'W'==c))
      {
         System.out.println("xRot = " + xRotation
                        + ", yRot = " + yRotation
                        + ", zRot = " + zRotation);
         System.out.print( cube_p.matrix );
      }

      // Render again.
      setupViewport();
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

      if (showFBaspectRatio)
         System.out.printf("Aspect ratio (of framebuffer) = %.2f\n", (double)w/(double)h);

      // Render again.
      setupViewport();
   }


   // Get in one place the code to set up the viewport.
   private void setupViewport()
   {
      // Render again.
      // Get the size of the FrameBuffer.
      FrameBuffer fb = this.fbp.getFrameBuffer();
      int w = fb.width;
      int h = fb.height;
      // Create a viewport with the correct aspect ratio.
      if ( letterbox )
      {
         if ( aspectRatio <= w/(double)h )
         {
            int width = (int)(h*aspectRatio);
            int xOffset = (w - width)/2;
            fb.setViewport(xOffset, 0, width, h);
         }
         else
         {
            int height = (int)(w/aspectRatio);
            int yOffset = (h - height)/2;
            fb.setViewport(0, yOffset, w, height);
         }
         fb.clearFB(Color.darkGray);
         fb.vp.clearVP(Color.black);
      }
      else // the viewport is the whole framebuffer
      {
         fb.setViewport();
         fb.vp.clearVP(Color.black);
      }
      Pipeline.render(scene, fb.vp);
      fbp.update();
      repaint();
   }


   /**
      Create an instance of this class which has
      the affect of creating the GUI application.
   */
   public static void main(String[] args)
   {
      print_help_message();

      // Define initial dimensions for a FrameBuffer.
      int width  = 512;
      int height = 512;
      // Create an InteractiveFrame containing a FrameBuffer
      // with the given dimensions. NOTE: We need to call the
      // InteractiveCube_R7 constructor in the Java GUI Event
      // Dispatch Thread, otherwise we get a race condition
      // between the constructor (running in the main() thread)
      // and the very first ComponentEvent (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
         new Runnable() // an anonymous inner class constructor
         {
            public void run() // implement the Runnable interface
            {
               // call the constructor that builds the gui
               new InteractiveCube_R7("Renderer 8", width, height);
            }
         }
      );
   }//main()


   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the x/X, y/Y, z/Z, keys to translate the cube along the x, y, z axes.");
      System.out.println("Use the u/U, v/V, w/W, keys to rotate the cube around the x, y, z axes.");
      System.out.println("Use the s/S keys to scale the size of the cube.");
      System.out.println("Use the 'c' key to change the random solid cube color.");
      System.out.println("Use the 'C' key to randomly change cube's colors.");
    //System.out.println("Use the 'e' key to change the random vertex colors.");
      System.out.println("Use the 'e' key to change the random solid edge colors.");
      System.out.println("Use the 'E' key to change the random edge colors.");
      System.out.println("Use the 'a' key to toggle antialiasing on and off.");
      System.out.println("Use the 'g' key to toggle gamma correction on and off.");
      System.out.println("Use the n/N keys to move the camera's near plane.");
      System.out.println("Use the o/O keys to change the size of the camera's view rectangle.");
      System.out.println("Use the r/R keys to change the aspect ratio of the camera's view rectangle.");
      System.out.println("Use the 'f' key to toggle showing framebufer aspect ratio.");
      System.out.println("Use the 'l' key to toggle letterboxing on and off.");
      System.out.println("Use the 'm' key to toggle showing the Model transformation matrix.");
      System.out.println("Use the 'M' key to toggle showing the Camera normalization matrix.");
      System.out.println("Use the '=' key to reset the transformation matrix to the identity.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}
