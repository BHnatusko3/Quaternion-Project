import renderer.quaternions.*;
import java.util.Scanner; 
public class Spot3DTester
{

   public static void main(String[] args)
   {
     Scanner in = new Scanner(System.in);
     System.out.println("Provide a.x");
     double aX = in.nextDouble();
     System.out.println("Provide a.y");
     double aY = in.nextDouble();
     System.out.println("Provide a.z");
     double aZ = in.nextDouble();
     
     System.out.println("Provide b.x");
     double bX = in.nextDouble();
     System.out.println("Provide b.y");
     double bY = in.nextDouble();
     System.out.println("Provide b.z");
     double bZ = in.nextDouble();
     
     Spot3D a = new Spot3D(aX,aY,aZ);
     Spot3D b = new Spot3D(bX,bY,bZ);
     
     System.out.println("Spot a: " + a);
     System.out.println("Spot b: " + b);
     Spot3D cross = Spot3D.crossProduct(a,b);
     System.out.println("Cross product: " + cross);
     
     a.normalize();
     b.normalize();
     System.out.println("Spot a normalized: " + a);
     System.out.println("Spot b normalized: " + b);
     Spot3D crossN = Spot3D.crossProduct(a,b);
     System.out.println("Cross product of normalized: " +  crossN);
     cross.normalize();
     System.out.println("Normalized cross product: " + cross);
     crossN.normalize();
     System.out.println("Normalized cross product of normalized: " +  crossN);
     
     
   }
}


