/*

*/

import renderer.scene.*;

/**
   Two dimensional letter P.
*/
public class P extends Model
{
   /**
      Two dimensional letter P.
   */
   public P()
   {
      super();

      // Create vertices.
      Vertex v0 = new Vertex(0.00, 0.00, 0.0);
      Vertex v1 = new Vertex(0.00, 1.00, 0.0);
      Vertex v2 = new Vertex(0.75, 1.00, 0.0);
      Vertex v3 = new Vertex(1.00, 0.8,  0.0);
      Vertex v4 = new Vertex(1.00, 0.6,  0.0);
      Vertex v5 = new Vertex(0.75, 0.4,  0.0);
      Vertex v6 = new Vertex(0.25, 0.4,  0.0);
      Vertex v7 = new Vertex(0.25, 0.0,  0.0);

      Vertex v8  = new Vertex(0.25, 0.8,  0.0);
      Vertex v9  = new Vertex(0.75, 0.8,  0.0);
      Vertex v10 = new Vertex(0.75, 0.6,  0.0);
      Vertex v11 = new Vertex(0.25, 0.6,  0.0);

      addVertex(v0, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11);

      // Create line segments.
      addLineSegment(new LineSegment(0, 1),
                     new LineSegment(1, 2),
                     new LineSegment(2, 3),
                     new LineSegment(3, 4),
                     new LineSegment(4, 5),
                     new LineSegment(5, 6),
                     new LineSegment(6, 7),
                     new LineSegment(7, 0));

      addLineSegment(new LineSegment(8,  9),
                     new LineSegment(9,  10),
                     new LineSegment(10, 11),
                     new LineSegment(11, 8));
   }
}
