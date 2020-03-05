/*
Course: CS 45500
      Name: Bruno Hnatusko III
      Email: bhnatusk@pnw.edu
      Assignment: 5

      A program for applying 3D transformations to a
      P, N, and W collectively and individually.
*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.quaternions.*;
import renderer.framebuffer.*;
import renderer.gui.*;

import java.awt.Color;
import java.awt.event.*;
import javafx.geometry.*;
/**

*/
public class QuaternionModelRotate3
{
   private FrameBufferFrame fbf; // The event handlers need
   private Scene scene;          // access to these fields.

   private double fovy = 90.0;

   private boolean letterbox = false;
   private double aspectRatio = 1.78; //  1920 / 1080
   private double near   =  1.0;
   private double left   = -1;
   private double right  =  1;
   private double bottom = -1.0;
   private double top    =  1.0;
   private boolean showCamera = false;
   private boolean showFBaspectRatio = false;

   private boolean showMatrix = false;
   private double distanceToCamera = -3;

   // Change this so that you can remember the state of each model.
   private double xTranslation = 0;
   private double yTranslation = 0;
   private double zTranslation = distanceToCamera;
   private Quaternion rotation = new Quaternion(1,0,0,0);
   private double scale = 1;

   private int modelIndex = 0; // 0==PNW, 1==P, 2==N, 3==W

   //Image size
   private int imageWidth = 960;
   private int imageHeight = 960;
   private int xOffset = 0;
   private int yOffset = 0;
   //Mouse position data
   private Point3D lastMousePos = new Point3D(0,0,0);
   private Point3D currentMousePos = new Point3D(0,0,0);

   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */
   public QuaternionModelRotate3()
   {
      // Define initial dimensions for a FrameBuffer.


      // Create a FrameBufferFrame holding a FrameBufferPanel.
      fbf = new FrameBufferFrame("Renderer 8", imageWidth, imageHeight);

      // Create (inner) event handler objects
      // for events from the FrameBufferFrame.
      
      /*
      JFrame jf = new JFrame("Interactive Frame with FrameBuffer");
      jf.add(fbf.fbp);
      jf.pack();
      jf.setVisible(true);
      jf.addMouseListener(new MListener_v0());
      jf.addMouseMotionListener(new MMoListener_v0());
      */
      
      fbf.addKeyListener(new KeyHandler());
      fbf.addComponentListener(new ComponentHandler());
      fbf.addMouseListener(new MListener_v0());
      fbf.addMouseMotionListener(new MMoListener_v0());
      // Create the Scene object that we shall render.
      scene = new Scene();

      // Set up the camera's view volume.
      scene.camera.projPerspective(left, right, bottom, top, near);

      // Add a model of the xy-plane.
      Model wall  = new PanelXY(-6, 6, -3, 3);
      ModelShading.setColor(wall, Color.darkGray.darker());
      scene.addPosition(new Position(wall));
      scene.getPosition(0).matrix = Matrix.translate(0, 0, distanceToCamera);


      // Change this code to build the appropriate scene graph.
      scene.addPosition(new Position( new P() ));
      ModelShading.setRandomColor(scene.getPosition(1).model);
      
      scene.getPosition(1).matrix = Matrix.translate(
                                               xTranslation,
                                               yTranslation,
                                               zTranslation);

      updateLetters();
   }



   // Define an inner KeyListener class.
   class KeyHandler extends KeyAdapter {
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
       //Rasterize.debug = ! Rasterize.debug;
      }
      /*
      else if ('/' == c)
      {
         modelIndex = (modelIndex + 1) % 4;
         if (0 == modelIndex)
         {
            currentPosition = null;
            System.out.println("Working on PNW.");

         }
         else if (1 == modelIndex)
         {
            System.out.println("Working on P.");
            currentPosition = scene.getPosition(1);
         }
      }
      */
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
         // Change the solid random color of the current model.

            ModelShading.setRandomColor(scene.getPosition(1).model);
      }
      else if ('C' == c)
      {
         // Change each color in the current model to a random color.
          ModelShading.setRandomColors(scene.getPosition(1).model);
      }
      else if ('e' == c && e.isAltDown())
      {
         // Change the random color of each vertex of the current model.

         ModelShading.setRandomVertexColors(scene.getPosition(1).model);
 
      }
      else if ('e' == c)
      {
         // Change the solid random color of each edge of the current model.

            ModelShading.setRandomLineSegmentColors(scene.getPosition(1).model);

      }
      else if ('E' == c)
      {
         // Change the random color of each end of each edge of the current model.

            ModelShading.setRainbowLineSegmentColors(scene.getPosition(1).model);
 
      }
      else if ('f' == c)
      {
         showFBaspectRatio = ! showFBaspectRatio;
         if (showFBaspectRatio)
         {
            // Get the new size of the FrameBufferPanel.
            int w = fbf.fbp.getWidth();
            int h = fbf.fbp.getHeight();
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
         xTranslation = 0.0;
         yTranslation = 0.0;
         zTranslation = distanceToCamera;
         rotation = new Quaternion(1,0,0,0);
         scale = 1.0;
      }
      else if ('s' == c) // Scale the model 10% smaller.
      {
         scale /= 1.1;
      }
      else if ('S' == c) // Scale the model 10% larger.
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
         rotation = Quaternion.rotateX(-2).times(rotation);
      }
      else if ('U' == c)
      {
        
         rotation = Quaternion.rotateX(2).times(rotation);
      }
      else if ('v' == c)
      {
         rotation = Quaternion.rotateY(-2).times(rotation);
      }
      else if ('V' == c)
      {
         rotation = Quaternion.rotateY(2).times(rotation);
      }
      else if ('w' == c)
      {
         rotation = Quaternion.rotateZ(-2).times(rotation);
      }
      else if ('W' == c)
      {
         rotation = Quaternion.rotateZ(2).times(rotation);
      }
      
      updateLetters();
      //System.out.println("Rotation: " + rotation);
      
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

      if (showMatrix && ('m'==c||'/'==c||'?'==c||'='==c
           ||'s'==c||'x'==c||'y'==c||'z'==c||'u'==c||'v'==c||'w'==c
           ||'S'==c||'X'==c||'Y'==c||'Z'==c||'U'==c||'V'==c||'W'==c))
      {
         System.out.println("rot = " + rotation);
         
      }

      // Render again.
      setupViewport();
   }}


   // Define an inner ComponentListener class.
   class ComponentHandler extends ComponentAdapter {
   @Override public void componentResized(ComponentEvent e)
   {
      //System.out.println( e );

      // Get the new size of the FrameBufferPanel.
      int w = fbf.fbp.getWidth();
      int h = fbf.fbp.getHeight();

      // Create a new FrameBuffer that fits the new window size.
      FrameBuffer fb = new FrameBuffer(w, h);
      fbf.fbp.setFrameBuffer(fb);

      if (showFBaspectRatio)
         System.out.printf("Aspect ratio (of framebuffer) = %.2f\n", (double)w/(double)h);

      // Render again.
      setupViewport();
   }}


   // Get in one place the code to set up the viewport.
   private void setupViewport()
   {
      // Render again.
      // Get the size of the FrameBuffer.
      FrameBuffer fb = fbf.fbp.getFrameBuffer();
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
      fbf.fbp.update();
      fbf.repaint();
   }


   /**
      Create an instance of this class which has
      the affect of creating the GUI application.
   */
   public static void main(String[] args)
   {
      print_help_message();

      // We need to call the program's constructor in the
      // Java GUI Event Dispatch Thread, otherwise we get a
      // race condition between the constructor (running in
      // the main() thread) and the very first ComponentEvent
      // (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
         () -> {new QuaternionModelRotate2();}
      );
   }//main()
   
   //Update all the letter positions 
   private void updateLetters()
   {
      //System.out.println("Updating letters");
      //Create the matrix of the entire scene.
      Matrix basePosMtx = Matrix.identity();
      basePosMtx.mult(Matrix.translate(
                                              xTranslation,
                                              yTranslation,
                                              zTranslation));
      basePosMtx.mult(rotation.toRotationMatrix());
      basePosMtx.mult(Matrix.scale(scale));
      scene.getPosition(1).matrix = new Matrix(basePosMtx);

   }
   

   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the '/' key to cycle between PNW, P, N, W.");
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the x/X, y/Y, z/Z, keys to translate a model along the x, y, z axes.");
      System.out.println("Use the u/U, v/V, w/W, keys to rotate a model around the x, y, z axes.");
      System.out.println("Use the s/S keys to scale the size of a model.");
      System.out.println("Use the 'c' key to change a model's random solid color.");
      System.out.println("Use the 'C' key to randomly change a model's colors.");
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
      System.out.println("Use the 'M' key to toggle showing the Camera matrix.");
      System.out.println("Use the '=' key to reset the current transformation matrix to the identity.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
   
   //Update the position of the mouse.
   public void updateMousePos(double x, double y)
   {
      x -= xOffset;
      y -= yOffset;
      double aspectRatio = (double) imageWidth / (double) imageHeight;
      double alphaHalf = Math.atan2(1,1);
      double pX = (2 * ((x + 0.5) / imageWidth) - 1) * Math.tan(alphaHalf) * aspectRatio;
      double pY = (1 - 2 * (y + 0.5) / imageHeight) * Math.tan(alphaHalf); 
      lastMousePos = currentMousePos;
      currentMousePos = new Point3D(10*pX,10*pY,-10);
   }
   
      //Mouse Listener Inner Class
   
   class MListener_v0 implements MouseListener
   {
      @Override public void mouseExited(MouseEvent e){}
      @Override public void mouseEntered(MouseEvent e){}
      @Override public void mouseReleased(MouseEvent e){}
      @Override public void mousePressed(MouseEvent e)
      {
         updateMousePos(e.getX(),e.getY());
      }
      @Override public void mouseClicked(MouseEvent e){}
   }
  
   //Mouse Motion Listener Inner Class
   class MMoListener_v0 implements MouseMotionListener
   {
      @Override public void mouseMoved(MouseEvent e)
      {
         
      //System.out.println(e.getX() + " " + e.getY());
      }
      @Override public void mouseDragged(MouseEvent e)
      {
         updateMousePos(e.getX(),e.getY());
         System.out.println("Dragging");
         System.out.println(currentMousePos.getX() + " " + lastMousePos.getX());
      }
   }
   
}
