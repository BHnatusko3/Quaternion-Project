/* Tests the slerp class with two quaternions
 * made from Standard Input.
 */ 
/* Three quaternions are created from Standard Input.
 * A slerp is made from the first to the second, then
 * continued to the third.
*/
import renderer.quaternions.*;
import renderer.scene.*;
import java.util.Arrays;
import java.util.Scanner;
public class Slerp_Tester_4
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
      System.out.print("Enter value oA.");
      double oA = in.nextDouble();
      System.out.print("Enter value oB.");
      double oB = in.nextDouble();
      System.out.print("Enter value oC.");
      double oC = in.nextDouble();
      System.out.print("Enter value oD.");
      double oD = in.nextDouble();
   
      Quaternion q0 = new Quaternion(mA, mB, mC, mD);
      Quaternion q1 = new Quaternion(nA, nB, nC, nD);
      Quaternion q2 = new Quaternion(oA, oB, oC, oD);
      Slerp s1 = new Slerp(q0, q1, 5);
      Slerp s2 = new Slerp(q1, q2, 5);
      System.out.println("Slerp between quaterinons m and n: ");
      System.out.println(s1);
      System.out.println("Slerp between quaternions n and o: ");
      System.out.println(s2);
      s1.continueTo(q2,5);
      System.out.println("Slerp mn continued to o: ");
       System.out.println(s1);

      
   }



}