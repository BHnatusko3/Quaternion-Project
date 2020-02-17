/*

*/

package renderer.scene;

import java.util.List;
import java.util.ArrayList;

/**
   A {@code Position} data structure represents a group of geometric objects
   positioned (both location and orientation) in three-dimensional space as
   part of a {@link Scene}. A {@code Position} is a recursive data structure.
   Every {@code Position} object contains a {@link List} of nested
   {@code Position} objects. The list of nested {@code Position}s lets us
   define what are called hierarchical, or nested, scenes. These are scenes
   that are made up of groups of models and each group can be moved around
   in the scene as a single unit while the individual models in the group
   can also be moved around within the group.
<p>
   A {@code Position} object holds references to a {@link Model} object,
   a {@link Matrix} object, and a {@code List} of {@code Position} objects.
   A {@code Position}'s {@code List} of {@code Position} objects creates
   a tree data structure of {@code Position} objects. A {@code Position}'s
   {@link Model} object represents a geometric shape in the {@link Scene}.
   The role of a {@code Position}'s  {@link Matrix} can be understood two
   ways. First, the {@link Matrix} determines the {@link Model}'s location
   and orientation within the local coordinate system determined by the
   {@code Position}'s parent {@code Position} (in the {@link Scene}'s forest
   of {@code Position} objects). Second, the {@link Matrix} determines a new
   local coordinate system within which the {@link Model} (and all the nested
   models lower in the tree) is plotted. The two ways of understanding a
   {@code Position}'s  {@link Matrix} correspond to reading a matrix
   transformation expression
   <pre>{@code
                T * v
   }</pre>
   from either right-to-left or left-to-right.
<p>
   When the renderer renders a {@code Position} object, the renderer
   traverses the tree of {@code Position}s rooted at the given
   {@code Position}. The renderer does a recursive, pre-order
   depth-first-traversal of the tree. As the renderer traverses the tree,
   it accumulates a "current-transformation-matrix" that multiplies each
   {@code Position}'s {@link Matrix} along the path from the tree's root
   {@code Position} to wherever the traversal is in the tree (this is the
   "pre-order" step in the traversal). The {@code ctm} is the current
   model-to-view transformation {@link Matrix}. The first stage of the
   rendering pipeline, {@link renderer.pipeline.Model2View}, multiplies every
   {@link Vertex} in a {@link Model}'s vertex list by this {@code ctm}, which
   converts the coordinates in each {@link Vertex} from the model's own local
   coordinate system to the {@code Camera}'s view coordinate system (which is "shared"
   by all the other models). Multiplication by the {@code ctm} has the effect
   of "placing" the model in view space at an appropriate location (using the
   translation part of the {@code ctm}) and in the appropriate orientation
   (using the rotation part of the {@code ctm}). Notice the difference between
   a {@code Position}'s {@code ctm} and the {@code Position}'s {@link Matrix}.
   At any specific node in the {@link Scene}'s forest of {@code Position} nodes,
   the {@code Position}'s {@link Matrix} places the {@code Position}'s
   {@link Model} within the local coordinate system of the {@code Position}'s
   parent {@code Position}. But the {@code Position}'s {@code ctm} places the
   {@code Position}'s {@link Model} within the {@link Camera}'s view coordinate
   system.
*/
public class Position
{
   public Model  model;
   public Matrix matrix;
   public List<Position> nestedPositions;

   public boolean visible;

   /**
      Construct a default {@code Position} with the identity {@link Matrix},
      no {@link Model} object, and no nested {@code Position}s.
   */
   public Position()
   {
      this.model = null;
      this.matrix = Matrix.identity(); // identity matrix
      this.nestedPositions = new ArrayList<>();
      this.visible = true;
   }


   /**
      Construct a {@code Position} with the identity {@link Matrix},
      the given {@link Model} object, and no nested {@code Position}s.

      @param model  {@link Model} object to place at this {@code Position}
   */
   public Position(Model model)
   {
      this.model = model;
      this.matrix = Matrix.identity(); // identity matrix
      this.nestedPositions = new ArrayList<>();
      this.visible = true;
   }


   /**
      A "copy constructor". This constructor should make a deep copy
      of the given {@code Position}'s {@link Matrix}, {@link Model}
      and {@link List} of nested {@code Position}s.

      @param position  {@code Position} to make a copy of
   */
/*   public Position(Position position) // a "copy constructor"
   {
      if (null != position.model)
      {
         this.model = new Model(position.model); // deep copy of the Model
      }
      else
      {
         this.model = null;
      }
      this.matrix = new Matrix(position.matrix);
      this.nestedPositions = new ArrayList<>();
      for (Position p : position.nestedPositions)
      {
         this.nestedPositions.add( new Position(p) ); // deep copy of each Position
      }
      this.visible = position.visible;
   }
*/

   /**
      Add a nested {@code Position} (or Positions) to this {@code Position}'s
      {@link List} of nested {@code Position}s.

      @param pArray  array of nested {@code Position}s to add to this {@code Position}
   */
   public void addNestedPosition(Position... pArray)
   {
      for (Position p : pArray)
      {
         this.nestedPositions.add(p);
      }
   }


   /**
      Get a reference to the nested {@code Position} at the given index in
      this {@code Position}'s {@link List} of nested {@code Position}s.

      @param index  index of the nested {@code Position} to return
      @return nested {@code Position} at the specified index in the {@link List} of nested {@code Position}s
   */
   public Position getNestedPosition(int index)
   {
      return nestedPositions.get(index);
   }


   /**
      Reset this {@code Position}'s {@link Matrix} to the identity matrix.

      @return a reference to this {@code Position} to facilitate chaining method calls
   */
   public Position matrix2Identity()
   {
      this.matrix = Matrix.identity();
      return this;
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Position} object
   */
   @Override
   public String toString()
   {
      String result = "";
      result += "This Position's visibility is: " + visible + "\n";
      result += "This Position's Matrix is\n";
      result += matrix;
      result += "This Position's Model is\n";
      result += (null == model) ? "null\n" : model;
      result += "This Position has " + nestedPositions.size() + " nested Positions\n";
      for (Position p : this.nestedPositions)
      {
         result += p.toString();
      }
      return result;
   }
}
