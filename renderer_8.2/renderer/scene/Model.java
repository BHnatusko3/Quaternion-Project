/*

*/

package renderer.scene;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

/**
   A {@code Model} data structure represents a distinct geometric object
   in a {@link Scene}. A {@code Model} data structure is mainly a {@link List}
   of {@link Vertex} objects, a list of {@link LineSegment} objects, and a
   list of {@link Color} objects. Each {@link LineSegment} object contains
   four integers that are the indices of two {@link Vertex} objects from the
   {@code Model}'s vertex list and two {@link Color} objects from the
   {@code Model}'s color list. The two {@link Vertex} objects contain the
   coordinates, in the model's local coordinate system, for each of the line
   segment's two endpoints. The two {@link Color} objects contain the rgb
   values for each of the line segment's two endpoints.
<p>
   A {@code Model} represent the geometric object as a "wire-frame" of line
   segments, that is, the geometric object is drawn as a collection of "edges".
   This is a fairly simplistic way of doing 3D graphics and we will
   improve this in later renderers.
<p>
   See
<br> <a href="http://en.wikipedia.org/wiki/Wire-frame_model" target="_top">
              http://en.wikipedia.org/wiki/Wire-frame_model</a>
<br>or
<br> <a href="https://www.google.com/search?q=graphics+wireframe&tbm=isch" target="_top">
              https://www.google.com/search?q=graphics+wireframe&tbm=isch</a>
*/
public class Model
{
   public List<Vertex> vertexList = new ArrayList<>();
   public List<LineSegment> lineSegmentList = new ArrayList<>();
   public List<Color> colorList = new ArrayList<>();

   public String  name;
   public boolean visible;
   public boolean debug;


   /**
      Construct an empty {@code Model} object.
   */
   public Model()
   {
      this.name = "";
      this.visible = true;
      this.debug = false;
   }


   /**
      Construct an empty {@code Model} object with the given name.

      @param name  a {link String} that is a name for this {@code Model}
   */
   public Model(String name)
   {
      this();
      this.name = name;
   }


   /**
      A "copy constructor". This constructor should make a deep copy
      of the given {@code Model}'s {@link Vertex} list,
      {@link LineSegment} list, and {@link Color} list.

      @param model  {@code Model} to make a copy of
   */
   public Model(Model model) // a "copy constructor"
   {
      super();

      this.name    = model.name;
      this.visible = model.visible;
      this.debug   = model.debug;
      for (Vertex v : model.vertexList)
      {
         this.vertexList.add(new Vertex(v)); // deep copy of each Vertex
      }
      for (LineSegment ls : model.lineSegmentList)
      {
         this.lineSegmentList.add(new LineSegment(ls)); // deep copy of each LineSgement
      }
      for (Color c : model.colorList)
      {
         this.colorList.add(c); // Color objects are immutable
      }
   }


   /**
      Add a {@link Vertex} (or vertices) to this {@code Model}'s
      {@link List} of vertices.

      @param vArray  array of {@link Vertex} objects to add to this {@code Model}
   */
   public void addVertex(Vertex... vArray)
   {
      for (Vertex v : vArray)
      {
         vertexList.add(new Vertex(v)); // NOTE: deep copy!
      }
   }


   /**
      Get a {@link LineSegment} from this {@code Model}'s
      {@link List} of line segments.

      @param index  integer index of a {@link LineSegment} from this {@code Model}
      @return the {@link LineSegment} object at the given index
   */
   public LineSegment getLineSegment(int index)
   {
      return lineSegmentList.get(index);
   }


   /**
      Add a {@link LineSegment} (or LineSegments) to this {@code Model}'s
      {@link List} of line segments.
      <p>
      NOTE: This method does not add any vertices to the {@code Model}'s
      {@link Vertex} list. This method assumes that the appropriate vertices
      have been added to the {@code Model}'s {@link Vertex} list.

      @param lsArray  array of {@link LineSegment} objects to add to this {@code Model}
   */
   public void addLineSegment(LineSegment... lsArray)
   {
      for (LineSegment ls : lsArray)
      {
         lineSegmentList.add(ls);
      }
   }


   /**
      Add a {@link Color} (or colors) to this {@code Model}'s
      {@link List} of colors.

      @param cArray  array of {@link Color} objects to add to this {@code Model}
   */
   public void addColor(Color... cArray)
   {
      for (Color c : cArray)
      {
         this.colorList.add(c);
      }
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code Model} object
   */
   @Override
   public String toString()
   {
      String result = "";
      result += "Model: " + name + "\n";
      result += "This Model's visibility is: " + visible + "\n";
      result += "Model has " + vertexList.size() + " vertices.\n";
      result += "Model has " + colorList.size() + " colors.\n";
      result += "Model has " + lineSegmentList.size() + " line segments.\n";
      int i = 0;
      for (Vertex v : this.vertexList)
      {
         result += i + ": " + v.toString();
         i++;
      }
      //result = "Printing out this Model's " + colortList.size() + " colors:\n";
      i = 0;
      for (Color c : this.colorList)
      {
         result += i + ": " + c.toString();
         i++;
      }
      //result = "Printing out this Model's " + lineSegmentList.size() + " Line segments:\n";
      for (LineSegment ls : this.lineSegmentList)
      {
         result += ls.toString();
      }
      //result += "Done printing out Model\n";
      return result;
   }
}
