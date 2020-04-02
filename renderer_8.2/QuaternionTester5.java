import renderer.quaternions.*;
import java.util.Scanner; 
public class QuaternionTester5
{

   public static void main(String[] args)
   {
     Scanner in = new Scanner(System.in);
     System.out.println("Provide x.");
     double x = in.nextDouble();
     System.out.println("Provide y.");
     double y = in.nextDouble();
     System.out.println("Provide z.");
     double z = in.nextDouble();
     System.out.println("Provide angle in degrees.");
     double angle = in.nextDouble();
     Spot3D spot = new Spot3D(x,y,z);
     
     System.out.println("Axis: " + spot);
     System.out.println("Angle: " + angle);
     
     Quaternion q = Quaternion.fromAxisAngle(spot,angle);
     System.out.println("Quaternion: " + q);
     
     double[] angles = q.toEulerAnglesDegXYZ();
     System.out.println("Angles: " + angles[0] + ", " + angles[1] + ", " + angles[2]);
     
     
   }
}
