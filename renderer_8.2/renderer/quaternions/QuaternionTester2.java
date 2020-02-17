public class QuaternionTester2
{

   public static void main(String[] args)
   {
      Quaternion q1 = new Quaternion(1,1,0,0);
      Quaternion q2 = new Quaternion(2,0,1,0);
      Quaternion q3 = new Quaternion(3,0,0,1);
      System.out.println("Addition.");
      System.out.println(Quaternion.add(q1,q2));
      System.out.println(q1.plus(q2).plus(q3));
      System.out.println("Subtraction.");
      System.out.println(Quaternion.subtract(q3,q2));
      System.out.println(q3.minus(q2).minus(q1));
      System.out.println(q3.minus(q2.minus(q1)));
      System.out.println("Multiplication");
      System.out.println(Quaternion.mult(q1,q2));
      System.out.println(Quaternion.mult(q2,q1));
   }
}


