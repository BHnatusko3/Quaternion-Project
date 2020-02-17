/*

*/

package renderer.models;
import  renderer.scene.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.regex.*;
import java.util.ArrayList;

/**
<p>
   A simple demonstration of loading and drawing a basic OBJ file.
<p>
   A basic OBJ file is a text file that contains three kinds of lines:
   lines that begin with the character {@code 'v'}, lines that begin
   with the character {@code 'f'}, and lines that begin with the
   character {@code '#'}.
<p>
   A line in an OBJ file that begins with {@code '#'} is a comment line
   and can be ignored.
<p>
   A line in an OBJ file that begins with {@code 'v'} is a line that
   describes a vertex in 3-dimensional space. The {@code 'v'} will always
   be followed on the line by three doubles, the {@code x}, {@code y},
   and {@code z} coordinates of the vertex.
<p>
   A line in an OBJ file that begins with {@code 'f'} is a line that
   describes a "face". The {@code 'f'} will be followed on the line by
   a sequence of positive integers. The integers are the indices of the
   vertices that make up the face. The "index" of a vertex is the order
   in which the vertex was listed in the OBJ file. So a line like this
<pre>{@code
      f  2  4  1
}</pre>
   would represent a triangle made up of the 2nd vertex read from the file,
   the 4th vertex read from the file, and the 1st vertex read from the file.
   And a line like this
<pre>{@code
      f  2  4  3  5
}</pre>
   would represent a quadrilateral made up of the 2nd vertex read from the file,
   the 4th vertex read from the file, the 3rd vertex read from the file, and
   the 5th vertex read from the file.
<p>
   See <a href="https://en.wikipedia.org/wiki/Wavefront_.obj_file" target="_top">
                https://en.wikipedia.org/wiki/Wavefront_.obj_file</a>
*/
public class ObjSimpleModel extends Model
{
   /**
      Create a wireframe model from the contents of an OBJ file.

      @param objFile  {@link File} object for the OBJ data file
   */
   public ObjSimpleModel(File objFile)
   {
      super("OBJ Model");

      // Open the OBJ file.
      String objName = null;
      FileInputStream fis = null;
      try
      {
         objName = objFile.getCanonicalPath();
         fis = new FileInputStream( objFile );
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace(System.err);
         System.err.printf("ERROR! Could not find OBJ file: %s\n", objName);
         System.exit(-1);
      }
      catch (IOException e)
      {
         e.printStackTrace(System.err);
         System.err.printf("ERROR! Could not open OBJ file: %s\n", objName);
         System.exit(-1);
      }

      this.name = objName;

      // Get the geometry from the OBJ file.
      try
      {
         // Pattern for parsing lines that start with "f"
         Pattern p = Pattern.compile("^(\\d*)[/]?(\\d*)[/]?(\\d*)");

         Scanner scanner = new Scanner(fis);
         while ( scanner.hasNext() )
         {
            String token = scanner.next();
            if ( token.startsWith("#")
              || token.startsWith("vt")
              || token.startsWith("vn")
              || token.startsWith("s")
              || token.startsWith("g")
              || token.startsWith("o")
              || token.startsWith("usemtl")
              || token.startsWith("mtllib") )
            {
               scanner.nextLine(); // skip over these lines
            }
            else if ( token.startsWith("v") )
            {
               double x = scanner.nextDouble();
               double y = scanner.nextDouble();
               double z = scanner.nextDouble();
               Vertex v = new Vertex(x, y, z);
               this.addVertex( v );
            }// parse vertex
            else if ( token.startsWith("f") )
            {
               // tokenize the rest of the line
               String restOfLine = scanner.nextLine();
               Scanner scanner2 = new Scanner( restOfLine );
               // parse three vertices and make two line segments
               int[] v = new int[3];
               for (int i = 0; i < 3; i++)
               {
                  // parse a "v/vt/vn" group
                  String faceGroup = scanner2.next();
                  Matcher m = p.matcher( faceGroup );
                  if ( m.find() )
                  {
                     v[i] = Integer.parseInt( m.group(1) );
                     String vt = m.group(2);  // don't need
                     String vn = m.group(3);  // don't need
                  }
                  else
                     System.err.println("Error: bad face: " + faceGroup);
               }
               addLineSegment(new LineSegment( v[0]-1, v[1]-1 ));
               addLineSegment(new LineSegment( v[1]-1, v[2]-1 ));

               // parse another vertex (if there is one) and make a line segment
               while (scanner2.hasNext())
               {
                  v[1] = v[2];
                  String faceGroup = scanner2.next();
                  Matcher m = p.matcher( faceGroup );
                  if ( m.find() )
                  {
                     v[2] = Integer.parseInt( m.group(1) );
                     String vt = m.group(2);  // don't need
                     String vn = m.group(3);  // don't need
                  }
                  else
                     System.err.println("Error: bad face: " + faceGroup);

                  addLineSegment(new LineSegment( v[1]-1, v[2]-1 ));
               }
               // close the line loop around this face
               addLineSegment(new LineSegment( v[2]-1, v[0]-1 ));
            }// parse face
         }// parse one line
         fis.close();
      }
      catch (Exception e)
      {
         e.printStackTrace(System.err);
         System.err.printf("ERROR! Could not read OBJ file: %s\n", objName);
         System.exit(-1);
      }
   }
}//ObjSimpleModel
