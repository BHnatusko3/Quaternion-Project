/*

*/

import renderer.scene.*;

/**
   This file defines the "basic model" that will be used
   in the example programs.
*/
public class Model_1 extends Model
{
   public Model_1()
   {
      super();

      // Create the model's geometry.
      Vertex v0 = new Vertex( 1.0,  1.0, 0.0);  // a square
      Vertex v1 = new Vertex(-1.0,  1.0, 0.0);
      Vertex v2 = new Vertex(-1.0, -1.0, 0.0);
      Vertex v3 = new Vertex( 1.0, -1.0, 0.0);

      addVertex(v0, v1, v2, v3);
      addLineSegment(new LineSegment(0, 1),
                     new LineSegment(1, 2),
                     new LineSegment(2, 3),
                     new LineSegment(3, 0));

      Vertex v4 = new Vertex( 2.0,  2.0, 0.0);  // another square
      Vertex v5 = new Vertex(-2.0,  2.0, 0.0);
      Vertex v6 = new Vertex(-2.0, -2.0, 0.0);
      Vertex v7 = new Vertex( 2.0, -2.0, 0.0);

      addVertex(v4, v5, v6, v7);
      addLineSegment(new LineSegment(4, 5),
                     new LineSegment(5, 6),
                     new LineSegment(6, 7),
                     new LineSegment(7, 4));

      // two more line segments
      Vertex v8 = new Vertex(-2.0,  1.0, 0.0);
      Vertex v9 = new Vertex( 1.0, -2.0, 0.0);

      addVertex(v8, v9);
      addLineSegment(new LineSegment(0, 4),
                     new LineSegment(8, 9));
   }
}
