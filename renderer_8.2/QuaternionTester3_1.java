/* Tests the original Quaternion functions with custom quaternions
 * Made from standard input.
 */
import renderer.quaternions.*;
import renderer.scene.*;
import java.util.Scanner;
public class QuaternionTester3_1
{

   public static void main(String[] args)
   {
     
      Scanner in = new Scanner(System.in);
      //First Quaternion data
      System.out.print("Enter value mA.");
      double mA = in.nextDouble();
      System.out.print("Enter value mB.");
      double mB = in.nextDouble();
      System.out.print("Enter value mC.");
      double mC = in.nextDouble();
      System.out.print("Enter value mD.");
      double mD = in.nextDouble();
      //Second Quaternion data
      System.out.print("Enter value nA.");
      double nA = in.nextDouble();
      System.out.print("Enter value mB.");
      double nB = in.nextDouble();
      System.out.print("Enter value mC.");
      double nC = in.nextDouble();
      System.out.print("Enter value mD.");
      double nD = in.nextDouble();
      //Construct Quaternions
      Quaternion m = new Quaternion(mA,mB,mC,mD);
      Quaternion n = new Quaternion(nA,nB,nC,nD);
      //Print quaternions
      System.out.println("Quaternion m: " + m);
      System.out.println("Quaternion n: " + n);
      //Test basic functions
      System.out.println("n + m:        " + Quaternion.add(n,m));
      System.out.println("n - m:        " + Quaternion.subtract(n,m));
      System.out.println("m - n:        " + Quaternion.subtract(m,n));
      System.out.println("m * n:        " + Quaternion.mult(m,n));
      System.out.println("n * m:        " + Quaternion.mult(n,m));
       /* Compares quaternion multiplication and matrix multiplication. 
       * Sometimes, signs will be reversed.
       */     
      System.out.println("m * n: unit quaternion:                             " + Quaternion.mult(m,n).unitQuaternion());
      System.out.println("m * n: to matrices, multiplied, then to quaternion: " + Quaternion.fromRotationMatrix(m.toRotationMatrix().times(n.toRotationMatrix())).unitQuaternion());
   }
}
