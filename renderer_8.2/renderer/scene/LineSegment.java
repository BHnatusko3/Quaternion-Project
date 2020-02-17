/*

*/

package renderer.scene;

import java.awt.Color;

/**
   A {@code LineSegment} object has four integers that
   represent the endpoints of the line segment and the
   color at each endpoint. Two of the integers are indices
   into the {@link Vertex} list of a {@link Model} object
   and the other two integers are indices into the {@link Color}
   list of that {@link Model} object.
*/
public class LineSegment
{
   public int[] vIndex = new int[2]; // indices for this line segment's vertices
   public int[] cIndex = new int[2]; // indices for this line segment's colors

   /**
      Construct a {@code LineSegment} object using two integer indices.
      Use the given indices for both the vertex and the color lists.

      @param i0  index of 1st endpoint vertex of the new {@code LineSegment}
      @param i1  index of 2nd endpoint vertex of the new {@code LineSegment}
   */
   public LineSegment(int i0, int i1)
   {
      this(i0, i1, i0, i1);
   }


   /**
      Construct a {@code LineSegment} object using two integer indices
      for the vertices and one integer index for the colors.

      @param i0  index of 1st endpoint vertex of the new {@code LineSegment}
      @param i1  index of 2nd endpoint vertex of the new {@code LineSegment}
      @param c   index of the color of the new {@code LineSegment}
   */
   public LineSegment(int i0, int i1, int c)
   {
      this(i0, i1, c, c);
   }


   /**
      Construct a {@code LineSegment} object using two integer indices
      for the vertices and two integer indices for the colors.
      <p>
      NOTE: This method does not put any Vertex or Color objects into
      this LineSegment's Model. This method assumes that the given indices
      are valid (or will be valid by the time this LineSegment gets rendered).

      @param i0  index of 1st endpoint vertex of the new {@code LineSegment}
      @param i1  index of 2nd endpoint vertex of the new {@code LineSegment}
      @param c0  index of 1st endpoint color of the new {@code LineSegment}
      @param c1  index of 2nd endpoint color of the new {@code LineSegment}
   */
   public LineSegment(int i0, int i1, int c0, int c1)
   {
      vIndex[0] = i0;
      vIndex[1] = i1;
      cIndex[0] = c0;
      cIndex[1] = c1;
   }


   /**
      Construct a {@code LineSegment} object with the same two vertex indices
      and the same two color indices from the given {@code LineSegment} object.

      @param ls  {@code LineSegment} to make a copy of
   */
   public LineSegment(LineSegment ls) // a "copy constructor"
   {
      this(ls.vIndex[0], ls.vIndex[1], ls.cIndex[0], ls.cIndex[1]);
   }


   /**
      Give this {@code LineSegment} the given uniform {@code Color}.
      <p>
      This method adds the given color object to the model's color list.
      <p>
      Warning: Repeatedly calling this method will leave the Model's color
      list bloated with unused Color objects. To repeatedly change this
      primitive's color, use {@link #changeColor}.

      @param model  {@link Model} object that this {@code LineSegment} is part of
      @param color  new {@link Color} object for this {@code LineSegment}
   */
   public void addColor(Model model, Color color)
   {
      int index = model.colorList.size();
      model.colorList.add(color);
      this.cIndex[0] = index;
      this.cIndex[1] = index;
   }


   /**
      Give this {@code LineSegment} the given {@code Color}s at its two endpoints.
      <p>
      This method adds the given color objects to the model's color list.
      <p>
      Warning: Repeatedly calling this method will leave the Model's color
      list bloated with unused Color objects. To repeatedly change this line
      segment's colors, use {@link #changeColors}.

      @param model   {@link Model} object that this {@code LineSegment} is part of
      @param color0  new {@link Color} object for 1st endpoint of this {@code LineSegment}
      @param color1  new {@link Color} object for 2nd endpoint of this {@code LineSegment}
   */
   public void addColors(Model model, Color color0, Color color1)
   {
      int index = model.colorList.size();
      model.colorList.add(color0);
      model.colorList.add(color1);
      this.cIndex[0] = index;
      this.cIndex[1] = index + 1;
   }


   /**
      Give this {@code LineSegment} the given uniform {@code Color}.
      <p>
      Warning: This method replaces the color objects this llne segment
      is using in the model's color list. So calling this method will
      also change the color of any other line segment that shares a
      color object with this line segment. To safely change only this
      line segment's color, use {@link #addColors}.

      @param model  {@link Model} object that this {@code LineSegment} is part of
      @param color  new {@link Color} object for this {@code LineSegment}
   */
   public void changeColor(Model model, Color color)
   {
      model.colorList.set(this.cIndex[0], color);
      model.colorList.set(this.cIndex[1], color);
   }


   /**
      Give this {@code LineSegment} the given {@code Color}s at its two endpoints.
      <p>
      Warning: This method replaces the color objects this llne segment
      is using in the model's color list. So calling this method will
      also change the color of any other line segment that shares a
      color object with this line segment. To safely change only this
      line segment's colors, use {@link #addColors}.

      @param model   {@link Model} object that this {@code LineSegment} is part of
      @param color0  new {@link Color} object for 1st endpoint of this {@code LineSegment}
      @param color1  new {@link Color} object for 2nd endpoint of this {@code LineSegment}
   */
   public void changeColors(Model model, Color color0, Color color1)
   {
      model.colorList.set(this.cIndex[0], color0);
      model.colorList.set(this.cIndex[1], color1);
   }


   /**
      For debugging.

      @return {@link String} representation of this {@code LineSegment} object
   */
   @Override
   public String toString()
   {
      return "Line Segment: ([" + vIndex[0] + ", " + vIndex[1] + "], [" + cIndex[0] + ", " + cIndex[1] + "])\n";
   }
}
