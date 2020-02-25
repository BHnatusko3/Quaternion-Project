import renderer.quaternions.*;
public class QuaternionTester
{

   public static void main(String[] args)
   {
      System.out.println(new Quaternion(0,0,0,0).toString());
      System.out.println("Step 1.0 done");
      System.out.println(new Quaternion(1,0,0,0).toString());
      System.out.println(new Quaternion(0,1,0,0).toString());
      System.out.println(new Quaternion(0,0,1,0).toString());
      System.out.println(new Quaternion(0,0,0,1).toString());
      System.out.println("Step 1.1 done");
      System.out.println(new Quaternion(1,1,0,0).toString());
      System.out.println(new Quaternion(1,0,1,0).toString());
      System.out.println(new Quaternion(1,0,0,1).toString());
      System.out.println(new Quaternion(0,1,1,0).toString());
      System.out.println(new Quaternion(0,1,0,1).toString());
      System.out.println(new Quaternion(0,0,1,1).toString());
      System.out.println("Step 1.2 done");
      System.out.println(new Quaternion(1,1,1,0).toString());
      System.out.println(new Quaternion(1,0,1,1).toString());
      System.out.println(new Quaternion(1,1,0,1).toString());
      System.out.println(new Quaternion(0,1,1,1).toString());
      System.out.println("Step 1.3 done");
      System.out.println(new Quaternion(1,1,1,1).toString());
      System.out.println("Step 1.4 done");
      System.out.println(new Quaternion(-1,0,0,0).toString());
      System.out.println(new Quaternion(0,-1,0,0).toString());
      System.out.println(new Quaternion(0,0,-1,0).toString());
      System.out.println(new Quaternion(0,0,0,-1).toString());
      System.out.println("Step 2.1 done");
      System.out.println(new Quaternion(-1,-1,0,0).toString());
      System.out.println(new Quaternion(-1,0,-1,0).toString());
      System.out.println(new Quaternion(-1,0,0,-1).toString());
      System.out.println(new Quaternion(0,-1,-1,0).toString());
      System.out.println(new Quaternion(0,-1,0,-1).toString());
      System.out.println(new Quaternion(0,0,-1,-1).toString());
      System.out.println("Step 2.2 done");
      System.out.println(new Quaternion(-1,-1,-1,0).toString());
      System.out.println(new Quaternion(-1,-1,0,-1).toString());
      System.out.println(new Quaternion(-1,0,-1,-1).toString());
      System.out.println(new Quaternion(0,-1,-1,-1).toString());
      System.out.println("Step 2.3 done");
      System.out.println(new Quaternion(-1,-1,-1,-1).toString());
      System.out.println("Step 2.4 done");


   }
}


