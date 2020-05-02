//Tests the slerp class with a 90-degree Z rotation.
import renderer.quaternions.*;
import renderer.scene.*;
import java.util.Arrays;
public class Slerp_Tester
{
   
   public static void main(String[] args)
   {
      //Starts with matrices.
      Matrix m0 = Matrix.identity(); //No rotation
      Matrix m1 = Matrix.identity().mult(Matrix.rotateZ(90)); //90-degree Z-rotation
      
      //Convert to quaternions.
      Quaternion q0 = Quaternion.fromRotationMatrix(m0);
      Quaternion q1 = Quaternion.fromRotationMatrix(m1);
      
      //I originally had the following two prints at the end, but I moved them here.
      //Prints quaternion used for the rotation.
      System.out.println(q1);
      //Prints rotation matrix used to form the previous quaternion.
      System.out.println(m1);
      
      //Forms slerp between q0 and q1.
      Slerp s = new Slerp(q0, q1, 10);
      //Print slerp rotations.
      for (int i = 0; i < s.getSteps(); i++)
      {
         System.out.println(Arrays.toString(s.get(i).toEulerAnglesDegXYZ())); 
      }

   }



}