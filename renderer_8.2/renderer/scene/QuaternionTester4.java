package renderer.scene;
import java.util.Arrays;
public class QuaternionTester4
{

   public static void main(String[] args)
   {
      System.out.println("");
      System.out.println("Comparing matrix multiplication with quaternion multiplication");
      Quaternion q1 = Quaternion.fromRotationMatrix(Matrix.rotateX(90).times(Matrix.rotateZ(90)));
      Quaternion q2 =  Quaternion.rotateX(90).times(Quaternion.rotateZ(90));
      System.out.println("Quaternion derived from multipled matrices: " + q1);
      System.out.println("Quaternion product:                         " + q2);
      
      System.out.println("Comparing X->Y->Z with fromEulerAngles");
      Quaternion q3 = Quaternion.fromRotationMatrix(Matrix.rotateZ(90).times(Matrix.rotateY(90)).times(Matrix.rotateX(90)));
      Quaternion q4 = Quaternion.fromEulerAnglesXYZ(90,90,90);
      System.out.println("Quaternion from X times Y times Z matrices: " + q3);
      System.out.println("Quaternuon from Euler Angles XYZ:           " + q4);
   }
}
