/* Tests the slerp class with one quaternion 
 * made from Standard Input.
 */ 
import renderer.quaternions.*;
import renderer.scene.*;
import java.util.Arrays;
import java.util.Scanner;
public class Slerp_Tester_2
{
   
   public static void main(String[] args)
   {
      Matrix m = Matrix.identity();
      //Get Quaternion data
      Scanner in = new Scanner(System.in);
      System.out.print("Enter value nA.");
      double mA = in.nextDouble();
      System.out.print("Enter value nB.");
      double mB = in.nextDouble();
      System.out.print("Enter value nC.");
      double mC = in.nextDouble();
      System.out.print("Enter value nD.");
      double mD = in.nextDouble();
      //Form quaternions
      Quaternion q0 = Quaternion.fromRotationMatrix(m);
      Quaternion q1 = new Quaternion(mA, mB, mC, mD);
      //Form 10-step slerp from quaternions
      Slerp s = new Slerp(q0, q1, 10);
      //Print slerp rotation angles
      for (int i = 0; i < s.getSteps(); i++)
      {
         System.out.println(Arrays.toString(s.get(i).toEulerAnglesDegXYZ())); 
      }
      
   }



}