/*

*/

package renderer.models;
import  renderer.scene.*;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;

/**
   This is a library of static methods that
   add color shading to a {@link Model}.
*/
public class ModelShading
{
   /**
      Set each {@link Color} in the {@link Model}'s color list
      to the same {@link Color}.

      @param model  {@link Model} whose color list is being manipulated
      @param c      {@link Color} for all of this model's {@link Vertex} objects
   */
   public static void setColor(Model model, Color c)
   {
      if (model.colorList.isEmpty())
      {
         for (int i = 0; i < model.vertexList.size(); i++)
         {
            model.colorList.add(c);
         }
      }
      else
      {
         for (int i = 0; i < model.colorList.size(); i++)
         {
            model.colorList.set(i, c);
         }
      }
   }


   /**
      Set each {@link Color} in the {@link Model}'s color list
      to the same random {@link Color}.

      @param model  {@link Model} whose color list is being manipulated
   */
   public static void setRandomColor(Model model)
   {
      Random generator = new Random();
      float r = generator.nextFloat();
      float g = generator.nextFloat();
      float b = generator.nextFloat();
      Color c = new Color(r, g, b);
      setColor(model, c);
   }


   /**
      Set each {@link Color} in the {@link Model}'s color list
      to a different random {@link Color}.

      @param model  {@link Model} whose color list is being manipulated
   */
   public static void setRandomColors(Model model)
   {
      if (model.colorList.isEmpty())
      {
         setRandomVertexColors(model);
      }
      else
      {
         Random generator = new Random();
         for (int i = 0; i < model.colorList.size(); i++)
         {
            float r = generator.nextFloat();
            float g = generator.nextFloat();
            float b = generator.nextFloat();
            Color c = new Color(r, g, b);
            model.colorList.set(i, c);
         }
      }
   }


   /**
      Set each {@link Vertex} in the {@link Model}
      to a different random {@link Color}.
      <p>
      NOTE: This will destroy whatever "color structure"
      the model might possess.

      @param model  {@link Model} whose color list is being manipulated
   */
   public static void setRandomVertexColors(Model model)
   {
      model.colorList = new ArrayList<Color>();
      Random generator = new Random();
      for (int i = 0; i < model.vertexList.size(); i++)
      {
         float r = generator.nextFloat();
         float g = generator.nextFloat();
         float b = generator.nextFloat();
         Color c = new Color(r, g, b);
         model.colorList.add(c);
      }
      for (LineSegment ls : model.lineSegmentList)
      {
         ls.cIndex[0] = ls.vIndex[0];
         ls.cIndex[1] = ls.vIndex[1];
      }
   }


   /**
      Set each {@link LineSegment} in the {@link Model}
      to a different (uniform) random {@link Color}.
      <p>
      NOTE: This will destroy whatever "color structure"
      the model might possess.

      @param model  {@link Model} whose color list is being manipulated
   */
   public static void setRandomLineSegmentColors(Model model)
   {
      model.colorList = new ArrayList<>();
      Random generator = new Random();
      for (LineSegment ls : model.lineSegmentList)
      {
         float r = generator.nextFloat();
         float g = generator.nextFloat();
         float b = generator.nextFloat();
         Color c = new Color(r, g, b);
         ls.addColor(model, c);
      }
   }


   /**
      Set each {@link LineSegment} in the {@link Model}
      to a different random {@link Color} at each endpoint.
      <p>
      NOTE: This will destroy whatever "color structure"
      the model might possess.

      @param model  {@link Model} whose color list is being manipulated
   */
   public static void setRainbowLineSegmentColors(Model model)
   {
      model.colorList = new ArrayList<>();
      Random generator = new Random();
      for (LineSegment ls : model.lineSegmentList)
      {
         float r1 = generator.nextFloat();
         float g1 = generator.nextFloat();
         float b1 = generator.nextFloat();
         Color c1 = new Color(r1, g1, b1);
         float r2 = generator.nextFloat();
         float g2 = generator.nextFloat();
         float b2 = generator.nextFloat();
         Color c2 = new Color(r2, g2, b2);
         ls.addColors(model, c1, c2);
      }
   }
}
