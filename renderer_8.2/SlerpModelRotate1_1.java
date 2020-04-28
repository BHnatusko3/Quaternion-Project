/*
Course: CS 42000
      Name: Bruno Hnatusko III
      Email: bhnatusk@pnw.edu

      A program that rotates a 3D model.
      Left click and drag: XY rotation
      Right click and drag: Z rotation
*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.quaternions.*;
import renderer.framebuffer.*;
import renderer.gui.*;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.awt.geom.Point2D;
import java.awt.Point;

import java.io.File;
//import javafx.geometry.*;
/**

*/
public class SlerpModelRotate1_1
{
   //private FrameBufferFrame fbf; // The event handlers need
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
   
   Slerp currentSlerp;
   
   int stepCount = 4;
   int currentStep = stepCount;
   
   private Point2D lastMousePos = new Point2D.Double(0,0);
   private Point2D currentMousePos = new Point2D.Double(0,0);
   
   private int mouseState = 0;
   
   private boolean rotating = false;
   
   // Define initial dimensions for a FrameBuffer.
   public int width  = 600;
   public int height = 600;
   public int fps = 60;
   
   private int yOffset = 31; //Without taking this into account, the top pixel is at y = 31. 
   private int xOffset = 8; //Without taking this into account, the leftmost pixel is at x = 8.
    

   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */
   public SlerpModelRotate1_1()
   {
    

      // Create a FrameBufferFrame holding a FrameBufferPanel.
      //fbf = new FrameBufferFrame("Renderer 8", width, height);

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
      
      //fbf.addKeyListener(new KeyHandler());
      //fbf.addComponentListener(new ComponentHandler());


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
      //scene.addPosition(new Position( new P() ));
      scene.addPosition(new Position( new ObjSimpleModel(new File("assets/cessna.obj")) ));
      ModelShading.setRandomColor(scene.getPosition(1).model);
      
      scene.getPosition(1).matrix = Matrix.translate(
                                               xTranslation,
                                               yTranslation,
                                               zTranslation);

      
      updateLetters();
      
      // Create an AnimationFrame containing a FrameBuffer
      // with the given dimensions.
      @SuppressWarnings("serial")
      InteractiveFrame app = new AnimationFrame("Renderer 2", width, height, fps)
      {
         // Implement the ActionListener interface.
         @Override public void actionPerformed(ActionEvent e)
         {
            if (currentSlerp != null && currentStep < currentSlerp.getSteps())
            {
               rotating = true;
               rotation = currentSlerp.get(currentStep);
               //System.out.println("current rotation is " + rotation);
               currentStep++;           
                          
            }
            else {rotating = false;}
   
            updateLetters();
            // Render again.
            FrameBuffer fb = this.fbp.getFrameBuffer();
            fb.clearFB(Color.black);
            Pipeline.render(scene, fb.vp);
            fbp.update();
            repaint();
         }

         // Implement part of the KeyListener interface.
         // This can be used to change the frame rate.
         
   
         @Override public void mouseExited(MouseEvent e){}
         @Override public void mouseEntered(MouseEvent e){}
         @Override public void mouseReleased(MouseEvent e)
         {
           mouseState = 0;
           System.out.println("Mouse State is " + mouseState);
         }
         @Override public void mousePressed(MouseEvent e)
         {
           if (SwingUtilities.isLeftMouseButton(e)) {mouseState = 1;}
           else if (SwingUtilities.isRightMouseButton(e)) {mouseState = 2;}
             
           System.out.println("Mouse State is " + mouseState);
           updateMousePos(e.getX(),e.getY());        
           System.out.println(e.getY() + " " + currentMousePos.getY());
         }
         @Override public void mouseClicked(MouseEvent e){}
         @Override public void mouseDragged(MouseEvent e)
         {
           if (mouseState == 0){return;}
           if (rotating){return;} //Ensures the program will catch up.
           updateMousePos(e.getX(),e.getY());
           //System.out.println("Last mouse pos: " + lastMousePos);
           if (mouseState == 1) {rotateXY();}  
           else if (mouseState == 2) {rotateZ();}
         }
         
         @Override public void keyTyped(KeyEvent e)
         {
            // update the scene
            //System.out.println( e );
            if (currentStep < currentSlerp.getSteps()) 
            {
              return;
            }
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
                  //int w = fbf.fbp.getWidth();
                  //int h = fbf.fbp.getHeight();
                  //System.out.printf("Aspect ratio (of framebuffer) = %.2f\n", (double)w/(double)h);
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
            /*
            else if ('u' == c)
            {
               rotateLetters(Quaternion.rotateX(-5),stepCount);
            }
            else if ('U' == c)
            {       
               rotateLetters(Quaternion.rotateX(5),stepCount);
            }
            else if ('v' == c)
            {
               rotateLetters(Quaternion.rotateY(-5),stepCount);
            }
            else if ('V' == c)
            {
               rotateLetters(Quaternion.rotateY(5),stepCount);
            }
            else if ('w' == c)
            {
               rotateLetters(Quaternion.rotateZ(-5),stepCount);
            }
            else if ('W' == c)
            {
               rotateLetters(Quaternion.rotateZ(5),stepCount);
            }
            */
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
               //System.out.println("rot = " + rotation);
               
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
            fb.clearFB(Color.black);
            Pipeline.render(scene, fb.vp);
            fbp.update();
            repaint();
         }
      };
      
   }
   
   
   
   // Define an inner KeyListener class.
   class KeyHandler extends KeyAdapter {
      @Override public void keyTyped(KeyEvent e)
      {
         
      }}
   
   
   // Define an inner ComponentListener class.
   class ComponentHandler extends ComponentAdapter {
      @Override public void componentResized(ComponentEvent e)
      {
         //System.out.println( e );
         
         // Get the new size of the FrameBufferPanel.
         //int w = fbf.fbp.getWidth();
         //int h = fbf.fbp.getHeight();
         
         // Create a new FrameBuffer that fits the new window size.
         //FrameBuffer fb = new FrameBuffer(w, h);
         //fbf.fbp.setFrameBuffer(fb);
         
         //if (showFBaspectRatio)
            //System.out.printf("Aspect ratio (of framebuffer) = %.2f\n", (double)w/(double)h);
         
         // Render again.
         //setupViewport();
      }}
   
   
   // Get in one place the code to set up the viewport.
   
   
   private void setupViewport()
   {
      //System.out.println("Rendering again.");
      // Render again.
      // Get the size of the FrameBuffer.
      
      /*
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
      */
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
                                             () -> {new SlerpModelRotate1_1();}
      );
   }//main()
   
   
   private void rotateLetters(Quaternion q, int steps)
   {
     
      if (steps > 0)
      {
         Slerp s = new Slerp(rotation, q.times(rotation), steps);
         currentSlerp = s;
         currentStep = 1;    
      }
 
   }
   
   private void updateMousePos(double x, double y)
   {
     
      x -= xOffset;
      y -= yOffset;
      //double aspectRatio = (double) width / (double) height;
      //double alphaHalf = Math.atan2(1,1);
      //double pX = (2 * ((x + 0.5) / width) - 1) * Math.tan(alphaHalf) * aspectRatio;
      //double pY = (1 - 2 * (y + 0.5) / height) * Math.tan(alphaHalf); 
      double pX = 2 * (x/width - 0.5); 
      double pY = 2 * (0.5 - y/(height  )); 
     
      lastMousePos = currentMousePos;
      currentMousePos = new Point2D.Double(pX,pY);
   }
   
   private void rotateXY()
   {
     if (rotating == true){return;}
     double xDif = currentMousePos.getX() - lastMousePos.getX();
     double yDif = currentMousePos.getY() - lastMousePos.getY();
     
     double yDeg = 180 * 10 * xDif;
     double xDeg = -1 * 180 * 10 * yDif;
     
     int frames = 2 + (int) Math.max(Math.abs(xDif),Math.abs(yDif))*50;
     //System.out.println("xDeg: " + xDeg + " yDeg: " + yDeg);
     //System.out.println(frames);
     
     rotateLetters(Quaternion.rotateY(yDeg).times(Quaternion.rotateX(xDeg)),frames);   
   }
   
   private void rotateZ()
   {
     if (rotating == true){return;}
     double ang1 = Math.atan2(lastMousePos.getY(),lastMousePos.getX());
     double ang2 = Math.atan2(currentMousePos.getY(),currentMousePos.getX());
       
     double angDif = 4 * Math.toDegrees(ang2 - ang1);
    
     int frames = 2 + (int) angDif/90;
     //System.out.println("xDeg: " + xDeg + " yDeg: " + yDeg);
     //System.out.println(frames);
     
     rotateLetters(Quaternion.rotateZ(angDif),frames);        
   }
   
   //Update all the letter positions 
   private void updateLetters()
   {
      //System.out.println("Updating letters.");
      //Create the matrix of the entire scene.
      Matrix basePosMtx = Matrix.identity();
      //System.out.println(rotation);
      basePosMtx.mult(Matrix.translate(
                                       xTranslation,
                                       yTranslation,
                                       zTranslation));
      
      basePosMtx.mult(rotation.toRotationMatrix());         
      basePosMtx.mult(Matrix.scale(scale));
      scene.getPosition(1).matrix = new Matrix(basePosMtx);
      setupViewport();
   }
   
 
   private static void print_help_message()
   {
      System.out.println("Left click and drag horizontally to rotate along the Y axis.");
      System.out.println("Left click and drag vertically to rotate along the X axis.");
      System.out.println("Right click and drag, preferrably in a circle, to rotate along the Z axis.");
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the '/' key to cycle between PNW, P, N, W.");
      /*
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the x/X, y/Y, z/Z, keys to translate a model along the x, y, z axes.");
      System.out.println("Use the u/U, v/V, w/W, keys to rotate a model around the x, y, z axes.");
      */
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
   
}
