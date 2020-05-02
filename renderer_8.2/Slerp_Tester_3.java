/* Tests the slerp class with two quaternions
 * made from Standard Input.
 */ 
import renderer.quaternions.*;
import renderer.scene.*;
import java.util.Arrays;
import java.util.Scanner;
public class Slerp_Tester_3
{
   
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      System.out.print("Enter value mA.");
      double mA = in.nextDouble();
      System.out.print("Enter value mB.");
      double mB = in.nextDouble();
      System.out.print("Enter value mC.");
      double mC = in.nextDouble();
      System.out.print("Enter value mD.");
      double mD = in.nextDouble();
      System.out.print("Enter value nA.");
      double nA = in.nextDouble();
      System.out.print("Enter value nB.");
      double nB = in.nextDouble();
      System.out.print("Enter value nC.");
      double nC = in.nextDouble();
      System.out.print("Enter value nD.");
      double nD = in.nextDouble();
   
      Quaternion q0 = new Quaternion(mA, mB, mC, mD);
      Quaternion q1 = new Quaternion(nA, nB, nC, nD);
   
      Slerp s = new Slerp(q0, q1, 10);
      System.out.println(s);
      
   }



}