/*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;
import java.io.File;

/**
   This version gives the camera a more appropriate view volume.
<p>
   Compare with
      http://threejs.org/examples/#webgl_geometries
   or
      https://stemkoski.github.io/Three.js/Shapes.html
   or
      http://www.smartjava.org/ltjs/chapter-02/04-geometries.html
*/
public class Geometries_R7
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

      // Create a horizontal coordinate plane model.
      Position xzPlane = new Position(new PanelXZ(-6, 6, -7, 7));
      ModelShading.setColor(xzPlane.model, Color.darkGray);

      // Add the positions (and their models) to the Scene.
      for (int i = 0; i < position.length; i++)
         for (int j = 0; j < position[i].length; j++)
         {
            scene.addPosition(position[i][j]);
         }
      scene.addPosition(xyzAxes);
      scene.addPosition(xzPlane);

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
         // Place the xz-plane model in front of the camera.
         xzPlane.matrix = Matrix.translate(0, -3, -10);
         // Rotate the plane.
         xzPlane.matrix.mult( Matrix.rotateY(k) );

         // Place the xyz-axes model in front of the camera.
         xyzAxes.matrix = Matrix.translate(0, -3, -10);
         // Rotate the axes.
         xyzAxes.matrix.mult( Matrix.rotateY(k) );

         // Place each model in the rotated xz-plane and
         // also rotate each model on its own axis.
         for (int i = 0; i < position.length; i++)
         {
            for (int j = 0; j < position[i].length; j++)
            {
               // Push the model away from the camera.
               position[i][j].matrix = Matrix.translate(0, -3, -10);
               // Rotate the plane of the models.
               position[i][j].matrix.mult( Matrix.rotateY(k) );
               // Place the model where it belongs in the rotated plane.
               position[i][j].matrix.mult( Matrix.translate(4-4*i, 0, 6-3*j) );
               // Now rotate the model on its own axis.
               position[i][j].matrix.mult( Matrix.rotateX(3*k) );
               position[i][j].matrix.mult( Matrix.rotateY(3*k) );
            }
         }

         // Render
       //Renderer.doAntialiasing = true;
         fb.clearFB(Color.black);
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_Geometries_R7_Frame%03d.ppm", k));
       //fb.dumpFB2File(String.format("PNG_Geometries_R7_Frame%03d.png", k), "png");
      }
      stopTime = System.currentTimeMillis();
      System.out.println("Wall-clock time: " + (stopTime - startTime));
   }
}
