/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;
import renderer.gui.*;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

/**

*/
@SuppressWarnings("serial")
public class InteractiveModelsAll_R7 extends InteractiveFrame
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
   private List<Model> modelArray = new ArrayList<>();
   private int currentModel = 0;

   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
   */
   public InteractiveModelsAll_R7(String title, int fbWidth, int fbHeight)
   {
      super(title, fbWidth, fbHeight);

      // Create the Scene object that we shall render
      scene = new Scene();

      // Create a Position object that will hold one of the Models.
      Position position = new Position();

      // Add the Position object to the scene.
      scene.addPosition(position);

      // Push the position away from where the camera is.
      position.matrix = Matrix.translate(0, 0, -2);
/*
      // Create several Model objects, using default constructors.
      modelArray.add(new Square());
      modelArray.add(new SquareGrid());
      modelArray.add(new Circle());
      modelArray.add(new Disk());
      modelArray.add(new DiskSector());
      modelArray.add(new Ring());
      modelArray.add(new RingSector());
      modelArray.add(new Box());
      modelArray.add(new Cube());
      modelArray.add(new Cube2());
      modelArray.add(new Cube3());
      modelArray.add(new Cube4());
      modelArray.add(new Tetrahedron());
      modelArray.add(new Octahedron());
      modelArray.add(new Icosahedron());
      modelArray.add(new Dodecahedron());
      modelArray.add(new Pyramid());
      modelArray.add(new PyramidFrustum());
      modelArray.add(new TriangularPyramid());
      modelArray.add(new TriangularPrism());
      modelArray.add(new ViewFrustum());
      modelArray.add(new Cone());
      modelArray.add(new ConeSector());
      modelArray.add(new ConeFrustum());
      modelArray.add(new Cylinder());
      modelArray.add(new CylinderSector());
      modelArray.add(new Sphere());
      modelArray.add(new SphereSector());
      modelArray.add(new SphereSubdivided());
      modelArray.add(new Torus());
      modelArray.add(new TorusSector());
      modelArray.add(new ParametricCurve());
      modelArray.add(new ParametricSurface());
      modelArray.add(new SurfaceOfRevolution());
      modelArray.add(new Axes3D());
      modelArray.add(new Axes2D());
      modelArray.add(new PanelXY());
*/
      // Instantiate at least one of every Model class.
      // 2D models
      modelArray.add(new Square(1.0));
      modelArray.add(new SquareGrid(1.0, 11, 15));
      modelArray.add(new Circle(1.0, 16));
      modelArray.add(new Disk(1.0, 4, 16));
      modelArray.add(new DiskSector(1.0, Math.PI/2, 3*Math.PI/2, 4, 8));
      modelArray.add(new Ring(1.0, 0.25, 3, 16));
      modelArray.add(new RingSector(1.0, 0.25, Math.PI/2, 3*Math.PI/2, 3, 8));
      // cubes
      modelArray.add(new Box(1.0, 1.0, 1.0));
      modelArray.add(new Cube( ));
      modelArray.add(new Cube2(4, 5, 6));
      modelArray.add(new Cube3(12, 14, 15));
      modelArray.add(new Cube4(12, 14, 15));
      // polyhedra
      modelArray.add(new Tetrahedron());
      modelArray.add(new Tetrahedron(true));
      modelArray.add(new Octahedron());
      modelArray.add(new Icosahedron());
      modelArray.add(new Dodecahedron());
      // pyramids
      modelArray.add(new Pyramid(2.0, 1.0, 5, 6));
      modelArray.add(new Pyramid(2.0, 1.0, 5, 6, true));
      modelArray.add(new PyramidFrustum(2.0, 1.0, 0.5, 2, 5));
      modelArray.add(new PyramidFrustum(1.0, 2.0, 0.5, 2, 5));
      modelArray.add(new TriangularPyramid(Math.sqrt(3)/Math.sqrt(2)));
      modelArray.add(new TriangularPyramid(1.0, 1.0, 7, 7));
      modelArray.add(new TriangularPrism(0.6, 0.5, 0.5, 3, true));
      modelArray.add(new ViewFrustumModel());
      // cones
      modelArray.add(new Cone(1.0, 1.0, 10, 16));
      modelArray.add(new ConeSector(1.0, 1.0, 0.5, 0, 2*Math.PI, 5, 16));
      modelArray.add(new ConeSector(1.0, 1.0, 0.5, Math.PI/2, 3*Math.PI/2, 5, 8));
      modelArray.add(new ConeFrustum(1.0, 0.5, 0.5, 6, 16));
      modelArray.add(new ConeFrustum(0.5, 1.0, 0.5, 6, 16));
      // cylinders
      modelArray.add(new Cylinder(0.5, 1.0, 11, 12));
      modelArray.add(new CylinderSector(0.5, 1.0, Math.PI/2, 3*Math.PI/2, 11, 6));
      // spheres
      modelArray.add(new Sphere(1.0, 15, 12));
      modelArray.add(new SphereSector(1.0, Math.PI/2, 3*Math.PI/2,
                                           Math.PI/4, 3*Math.PI/4, 7, 6));
      modelArray.add(new SphereSubdivided(4));
      modelArray.add(new SphereSubdivided(6, true, false));
      modelArray.add(new SphereSubdivided(7, false, true));
      // torus
      modelArray.add(new Torus(0.75, 0.25, 12, 16));
      modelArray.add(new TorusSector(0.75, 0.25, Math.PI/2, 3*Math.PI/2, 12, 8));
      modelArray.add(new TorusSector(0.75, 0.25, 0, 2*Math.PI,
                                                 Math.PI, 2*Math.PI, 6, 16));
      modelArray.add(new TorusSector(0.75, 0.25, 0, 2*Math.PI,
                                                -Math.PI/2, Math.PI/2, 6, 16));
      modelArray.add(new TorusSector(0.75, 0.25, Math.PI/2, 3*Math.PI/2,
                                                -Math.PI/2, Math.PI/2, 6, 8));
      // model files
      modelArray.add(new GRSModel(new File("assets/grs/bronto.grs")));
      modelArray.add(new ObjSimpleModel(new File("assets/cow.obj")));
      // parametric curves and surfaces
      modelArray.add(new ParametricCurve());
      modelArray.add(new ParametricSurface());
      modelArray.add(new ParametricSurface((s,t)->s*Math.cos(t*Math.PI),
                                           (s,t)->t,
                                           (s,t)->s*Math.sin(t*Math.PI),
                                           -1, 1, -1, 1, 49, 49));
      modelArray.add(new ParametricSurface(
                (u,v)->0.3*(1-u)*(3+Math.cos(v))*Math.sin(4*Math.PI*u),
                (u,v)->0.3*(3*u+(1-u)*Math.sin(v)),
                (u,v)->0.3*(1-u)*(3+Math.cos(v))*Math.cos(4*Math.PI*u),
                0, 1, 0, 2*Math.PI, 49, 49));
      modelArray.add(new SurfaceOfRevolution());
      modelArray.add(new SurfaceOfRevolution(t->0.5*(1+t*t), -1, 1, 30, 30));
      modelArray.add(new SurfaceOfRevolution(t->t, t->4*t*(1-t), 0, 1, 30, 30));
      // coordinate axes
      modelArray.add(new Axes3D(1, 1, 1));
      modelArray.add(new Axes2D(-1, 1, -1, 1, 8, 8));
      modelArray.add(new PanelXY(-4, 4, -5, 5, -5));
      modelArray.add(new PanelXZ(-1, 1, -6, 1, -0.5));
      modelArray.add(new PanelYZ(-1, 1, -6, 1, -0.5));

      // Give each model a random color.
      for (Model m : modelArray)
      {
         ModelShading.setRandomColor(m);
      }

      // Add a model to the position.
      position.model = modelArray.get(currentModel);
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
      else if ('/' == c)
      {
         currentModel = (currentModel + 1) % modelArray.size();
         scene.positionList.get(0).model = modelArray.get(currentModel);
      }
      else if ('?' == c)
      {
         currentModel = (currentModel - 1);
         if (currentModel < 0) currentModel = modelArray.size() - 1;
         scene.positionList.get(0).model = modelArray.get(currentModel);
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
         // Change the solid random color of the current model.
         ModelShading.setRandomColor(scene.positionList.get(0).model);
      }
      else if ('C' == c)
      {
         // Change each color in the current model to a random color.
         ModelShading.setRandomColors(scene.positionList.get(0).model);
      }
      else if ('e' == c && e.isAltDown())
      {
         // Change the random color of each vertex of the current model.
         ModelShading.setRandomVertexColors(scene.positionList.get(0).model);
      }
      else if ('e' == c)
      {
         // Change the solid random color of each edge of the current model.
         ModelShading.setRandomLineSegmentColors(scene.positionList.get(0).model);
      }
      else if ('E' == c)
      {
         // Change the random color of each end of each edge of the current model.
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
      Position model_p = scene.positionList.get(0);
      // Push the model away from where the camera is.
      model_p.matrix = Matrix.translate(0, 0, -2);
      // Move the model relative to its new position.
      model_p.matrix.mult( Matrix.translate(xTranslation,
                                            yTranslation,
                                            zTranslation) );
      model_p.matrix.mult( Matrix.rotateX(xRotation) );
      model_p.matrix.mult( Matrix.rotateY(yRotation) );
      model_p.matrix.mult( Matrix.rotateZ(zRotation) );
      model_p.matrix.mult( Matrix.scale(scale) );

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
         System.out.print( model_p.matrix );
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
      int width  = 1024;
      int height = 1024;
      // Create an InteractiveFrame containing a FrameBuffer
      // with the given dimensions. NOTE: We need to call the
      // InteractiveModelsAll_R7 constructor in the Java GUI Event
      // Dispatch Thread, otherwise we get a race condition
      // between the constructor (running in the main() thread)
      // and the very first ComponentEvent (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
         new Runnable() // an anonymous inner class constructor
         {
            public void run() // implement the Runnable interface
            {
               // call the constructor that builds the gui
               new InteractiveModelsAll_R7("Renderer 8", width, height);
            }
         }
      );
   }//main()


   private static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off.");
      System.out.println("Use the '/' key to cycle through the models.");
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the x/X, y/Y, z/Z, keys to translate the models along the x, y, z axes.");
      System.out.println("Use the u/U, v/V, w/W, keys to rotate the models around the x, y, z axes.");
      System.out.println("Use the s/S keys to scale the size of the models.");
      System.out.println("Use the 'c' key to change the random solid model color.");
      System.out.println("Use the 'C' key to randomly change model's colors.");
      System.out.println("Use the 'e' key to change the random solid edge colors.");
      System.out.println("Use the 'E' key to change the random edge colors.");
      System.out.println("Use the 'a' key to toggle antialiasing on and off.");
      System.out.println("Use the n/N keys to move the camera's near plane.");
      System.out.println("Use the o/O keys to change the size of the camera's view rectangle.");
      System.out.println("Use the r/R keys to change the aspect ratio of the camera's view rectangle.");
      System.out.println("Use the 'f' key to toggle showing framebufer aspect ratio.");
      System.out.println("Use the 'l' key to toggle letterboxing on and off.");
      System.out.println("Use the 'm' key to toggle showing the Model transformation matrix.");
      System.out.println("Use the 'M' key to toggle showing the Camera normalization matrix.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}
