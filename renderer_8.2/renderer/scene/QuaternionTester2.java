package renderer.scene;
public class QuaternionTester2
{

   public static void main(String[] args)
   {
      Quaternion q1 = new Quaternion(1,1,0,0);
      Quaternion q2 = new Quaternion(2,0,1,0);
      Quaternion q3 = new Quaternion(3,0,0,1);
      System.out.println("Addition.");
      System.out.println("q1 + q2     : " + Quaternion.add(q1,q2));
      System.out.println("q1 + q2 + q3: " + q1.plus(q2).plus(q3));
      System.out.println("Subtraction.");
      System.out.println("q1 - q3       : " + Quaternion.subtract(q3,q2));
      System.out.println("q3 - q2 - q1  : " + q3.minus(q2).minus(q1));
      System.out.println("q3 - (q2 - q1): " + q3.minus(q2.minus(q1)));
      System.out.println("Multiplication");
      System.out.println(Quaternion.mult(q1,q2));
      System.out.println(Quaternion.mult(q2,q1));
      System.out.println("To rotation matrices");
      System.out.println(q1.toRotationMatrix());
      System.out.println(Quaternion.mult(q2,q1).toRotationMatrix());
      System.out.println("Testing the matrix get function");
      Matrix m = Matrix.build(
                            new Vector(1.1,2.1,3.1,4.1),
                            new Vector(1.2,2.2,3.2,4.2),
                            new Vector(1.3,2.3,3.3,4.3),
                            new Vector(1.4,2.4,3.4,4.4)
      );
      System.out.println(m);
      System.out.println(m.get(2,3));
      System.out.println(m.get(4,1));
      System.out.println("Conversion from matrix");      
      System.out.println("q3 unit: " + q3.unitQuaternion());
      System.out.println("q3 to matrix and back: " + Quaternion.fromRotationMatrix(q3.toRotationMatrix()));
      System.out.println("Conversion from matrix: sign differences");   
      Quaternion q4 = new Quaternion(-1,1,0,0);
      Quaternion q5 = new Quaternion(1,-1,0,0);
      System.out.println("q4: " + q4);
      System.out.println("q5: " + q5);
      System.out.println("q4 unit: " + q4.unitQuaternion());
      System.out.println("q5 unit: " + q5.unitQuaternion());
      System.out.println("q4 to matrix and back: " + Quaternion.fromRotationMatrix(new Quaternion(-1,1,0,0).toRotationMatrix()));
      System.out.println("q5 to matrix and back: " + Quaternion.fromRotationMatrix(new Quaternion(1,-1,0,0).toRotationMatrix()));
   }
}


