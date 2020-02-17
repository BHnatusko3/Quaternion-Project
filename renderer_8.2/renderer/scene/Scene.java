/*

*/

package renderer.scene;

import java.util.List;
import java.util.ArrayList;

/**
   A {@code Scene} data structure is a list of {@link Position} data
   structures and a {@link Camera} data structure.
<p>
   Each {@link Position} object represents a {@link Model} object in a
   distict position in three-dimensional camera space. Each {@link Model}
   object represents a distinct geometric object in the scene.
<p>
   The {@link Camera} object determines a "view volume", which
   determines how much of the scene is actually visible (to the
   camera) and gets rendered into the framebuffer.
*/
public class Scene
{
   public List<Position> positionList = new ArrayList<>();

   public Camera camera;


   /**
      Construct a {@code Scene} with a default {@link Camera} object.
   */
   public Scene()
   {
      this.camera = new Camera();
   }


   /**
      Construct a {@code Scene} with the given {@link Camera} object.

      @param camera  {@link Camera} object for this {@code Scene}
   */
   public Scene(Camera camera)
   {
      this.camera = camera;
   }


   /**
      Change this {@code Scene}'s {@link Camera} to the given {@link Camera} object.

      @param camera  new {@link Camera} object for this {@code Scene}
   */
   public void setCamera(Camera camera)
   {
      this.camera = camera;
   }


   /**
      Add a {@link Position} (or Positions) to this {@code Scene}.

      @param pArray  array of {@link Position}s to add to this {@code Scene}
   */
   public void addPosition(Position... pArray)
   {
      for (Position position : pArray)
      {
         positionList.add(position);
      }
   }


   /**
      Get a reference to the {@link Position} at the given index in this {@code Scene}'s
      {@link List} of {@link Position}s.

      @param index  index of the {@link Position} to return
      @return {@link Position} at the specified index in the {@link List} of {@link Position}s
      @throws IndexOutOfBoundsException if the index is out of range
              {@code (index < 0 || index >= size())}
   */
   public Position getPosition(int index)
   {
      return positionList.get(index);
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Scene} object
   */
   @Override
   public String toString()
   {
      String result = "";
      result += camera.toString();
      result += "This Scene has " + positionList.size() + " models\n";
      int i = 0;
      for (Position p : positionList)
      {
         result += "Position " + (i++) + "\n";
         result += p.toString();
      }
      return result;
   }
}
