/*

*/

package renderer.models;
import  renderer.scene.*;

/**
   Create a flat wireframe checkerboard panel in the yz-plane.
*/
public class PanelYZ extends Model
{
   /**
      Create a flat checkerboard panel in the yz-plane that runs
      from -1 to 1 in the y-direction and -1 to 1 in the z-direction.
   */
   public PanelYZ( )
   {
      this(-1, 1, -1, 1);
   }


   /**
      Create a flat checkerboard panel in the xz-plane with the given dimensions.

      @param yMin  location of bottom edge
      @param yMax  location of top edge
      @param zMin  location of back edge
      @param zMax  location of front edge
   */
   public PanelYZ(int yMin, int yMax, int zMin, int zMax)
   {
      this(yMin, yMax, zMin, zMax, 0.0);
   }


   /**
      Create a flat checkerboard panel parallel to the yz-plane with the given dimensions.

      @param yMin  location of bottom edge
      @param yMax  location of top edge
      @param zMin  location of back edge
      @param zMax  location of front edge
      @param x     x-plane that holds the panel
   */
   public PanelYZ(int yMin, int yMax, int zMin, int zMax, double x)
   {
      super("PanelYZ");

      // Create the checkerboard panel's geometry.

      // An array of indexes to be used to create line segments.
      int[][] index = new int[(yMax-yMin)+1][(zMax-zMin)+1];

      // Create the checkerboard of vertices.
      int i = 0;
      for (int y = yMin; y <= yMax; ++y)
      {
         for (int z = zMin; z <= zMax; ++z)
         {
            addVertex(new Vertex(x, y, z));
            index[y-yMin][z-zMin] = i++;
         }
      }

      // Create the line segments that run in the z-direction.
      for (int y = 0; y <= yMax-yMin; ++y)
      {
         for (int z = 0; z < zMax-zMin; ++z)
         {
            addLineSegment(new LineSegment(index[y][z], index[y][z+1]));
         }
      }

      // Create the line segments that run in the y-direction.
      for (int z = 0; z <= zMax-zMin; z++)
      {
         for (int y = 0; y < yMax-yMin; ++y)
         {
            addLineSegment(new LineSegment(index[y][z], index[y+1][z]));
         }
      }
   }
}//PanelYZ
