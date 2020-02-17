/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;
import java.io.File;

/**
   This version creates a hierarchical Scene.
<p>
   Compare with
      http://threejs.org/examples/#webgl_geometries
   or
      https://stemkoski.github.io/Three.js/Shapes.html
   or
      http://www.smartjava.org/ltjs/chapter-02/04-geometries.html
*/
public class Geometries_R8
{
   public static void main(String[] args)
   {
      // Create the Scene object that we shall render.
      Scene scene = new Scene();

      // Create a two-dimensional array of Positions holding Models.
      Position[][] position = new Position[3][5];

      // row 0
      position[0][0] = new Position(new ObjSimpleModel(new File("assets/great_rhombicosidodecahedron.obj")));
      ModelShading.setColor(position[0][0].model, Color.red);

      position[0][1] = new Position(new ConeFrustum(0.5, 1.0, 1.0, 10, 10));
      ModelShading.setColor(position[0][1].model, Color.orange);

      position[0][2] = new Position(new Box(1.0, 1.0, 1.0));
      ModelShading.setColor(position[0][2].model, Color.lightGray);

      position[0][3] = new Position(new Axes3D(1, 1, 1, Color.red, Color.green, Color.blue));

      position[0][4] = new Position(new Sphere(1.0, 30, 30));
      ModelShading.setColor(position[0][4].model, Color.lightGray);

      // row 1
      position[1][0] = new Position(new Cylinder(0.5, 1.0, 30, 30));
      ModelShading.setColor(position[1][0].model, Color.blue.brighter().brighter());

      position[1][1] = new Position(new ObjSimpleModel(new File("assets/horse.obj")));
      ModelShading.setColor(position[1][1].model, Color.pink);

      position[1][2] = new Position(new GRSModel(new File("assets/grs/vinci.grs")));
      ModelShading.setColor(position[1][2].model, Color.blue);

      position[1][3] = new Position(new Tetrahedron());
      ModelShading.setColor(position[1][3].model, Color.cyan);

      position[1][4] = new Position(new ObjSimpleModel(new File("assets/small_rhombicosidodecahedron.obj")));
      ModelShading.setColor(position[1][4].model, Color.magenta);

      // row 2
      position[2][0] = new Position(new TriangularPrism(1.0, 1.0, 10));
      ModelShading.setColor(position[2][0].model, Color.green.darker().darker());

      position[2][1] = new Position(new GRSModel(new File("assets/grs/bronto.grs")));
      ModelShading.setColor(position[2][1].model, Color.red);

      position[2][2] = new Position(new Torus(0.75, 0.25, 30, 30));
      ModelShading.setRandomColors(position[2][2].model);

      position[2][3] = new Position(new Octahedron());
      ModelShading.setColor(position[2][3].model, Color.blue);

      position[2][4] = new Position(new Cone(0.5, 1.0, 30, 30));
      ModelShading.setColor(position[2][4].model, Color.yellow);

      // Create x, y and z axes
      Position xyzAxes = new Position(new Axes3D(6, -6, 6, 0, 7, -7,  Color.red));

      // Create a "top level" Position that holds a horizontal coordinate plane model.
      Position topLevel_p = new Position(new PanelXZ(-6, 6, -7, 7));
      ModelShading.setColor(topLevel_p.model, Color.darkGray);

      // Add the other Positions as nested Positions of the top level Position.
      for (int i = 0; i < position.length; i++)
         for (int j = 0; j < position[i].length; j++)
         {
            topLevel_p.addNestedPosition(position[i][j]);
         }
      topLevel_p.addNestedPosition(xyzAxes);

      // Add the top level Position to the Scene.
      scene.addPosition( topLevel_p );

      // Place the top level Position in front of the camera.
      topLevel_p.matrix = Matrix.translate(0, -3, -10);

      // Place each model in the xz-plane.
      for (int i = 0; i < position.length; i++)
      {
         for (int j = 0; j < position[i].length; j++)
         {
            // Place the model where it belongs in the xz-plane.
            position[i][j].matrix.mult( Matrix.translate(4-4*i, 0, 6-3*j) );
         }
      }

      // Set up the camera's view frustum.
      double right  = 2.0;
      double left   = -right;
      double top    = 1.0;
      double bottom = -top;
      double near   = 1.0;
      scene.camera.projPerspective(left, right, bottom, top, near);
/*
      double fov    = 90.0;
      double aspect = 2.0;
      double near   = 1.0;
      scene.camera.projPerspective(fov, aspect, near);
*/
      // Create a framebuffer to render our scene into.
      int vp_width  = 1200;
      int vp_height = 600;
      FrameBuffer fb = new FrameBuffer(vp_width, vp_height);

      long startTime, stopTime;
      startTime = System.currentTimeMillis();
      for (int k = 0; k < 360; k++)
      {
         // Rotate the top level Position one degree (accumulate the rotations).
         topLevel_p.matrix.mult( Matrix.rotateY(1) );

         // Rotate each model on its own axis.
         for (int i = 0; i < position.length; i++)
         {
            for (int j = 0; j < position[i].length; j++)
            {
               // Rotate the model on its own axis (accumulate the rotations).
               position[i][j].matrix.mult( Matrix.rotateX(3) );
               position[i][j].matrix.mult( Matrix.rotateY(3) );
            }
         }

         // Render
       //Renderer.doAntialiasing = true;
         fb.clearFB(Color.black);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_Geometries_R8_Frame%03d.ppm", k));
       //fb.dumpFB2File(String.format("PNG_Geometries_R8_Frame%03d.png", k), "png");
      }
      stopTime = System.currentTimeMillis();
      System.out.println("Wall-clock time: " + (stopTime - startTime));
   }
}
