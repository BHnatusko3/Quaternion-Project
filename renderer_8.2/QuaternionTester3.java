import renderer.quaternions.*;
import renderer.scene.*;
public class QuaternionTester3
{

   public static void main(String[] args)
   {
      if (args.length < 8) 
      {
         System.out.println("Not enough arguments!"); 
         return;
      }
      double mA = Double.valueOf(args[0]);
      double mB = Double.valueOf(args[1]);
      double mC = Double.valueOf(args[2]);
      double mD = Double.valueOf(args[3]);
      double nA = Double.valueOf(args[4]);
      double nB = Double.valueOf(args[5]);
      double nC = Double.valueOf(args[6]);
      double nD = Double.valueOf(args[7]);
      Quaternion m = new Quaternion(mA,mB,mC,mD);
      Quaternion n = new Quaternion(nA,nB,nC,nD);
      System.out.println("Quaternion m: " + m);
      System.out.println("Quaternion n: " + n);
      System.out.println("n + m:        " + Quaternion.add(n,m));
      System.out.println("n - m:        " + Quaternion.subtract(n,m));
      System.out.println("m - n:        " + Quaternion.subtract(m,n));
      System.out.println("m * n:        " + Quaternion.mult(m,n));
      System.out.println("n * m:        " + Quaternion.mult(n,m));
      
      System.out.println("m * n: unit quaternion:                             " + Quaternion.mult(m,n).unitQuaternion());
      System.out.println("m * n: to matrices, multiplied, then to quaternion: " + Quaternion.fromRotationMatrix(m.toRotationMatrix().times(n.toRotationMatrix())).unitQuaternion());
   }
}
