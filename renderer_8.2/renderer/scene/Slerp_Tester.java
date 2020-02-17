
package renderer.scene;
import java.util.Arrays;
public class Slerp_Tester
{
   
   public static void main(String[] args)
   {
      Matrix m0 = Matrix.identity();
      Matrix m1 = Matrix.identity().mult(Matrix.rotateZ(90));
   
      Quaternion q0 = Quaternion.fromRotationMatrix(m0);
      Quaternion q1 = Quaternion.fromRotationMatrix(m1);
   
      Slerp s = new Slerp(q0, q1, 10);
      for (int i = 0; i < s.getSteps(); i++)
      {
         System.out.println(Arrays.toString(s.get(i).toEulerAnglesDegXYZ())); 
      }
      
   System.out.println(Quaternion.fromRotationMatrix(Matrix.rotateZ(90)));
    System.out.println(Matrix.rotateZ(90));
   }



}