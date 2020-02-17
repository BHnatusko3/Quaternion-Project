/*
Course: CS 42000
      Name: Bruno Hnatusko III
      Email: bhnatusk@pnw.edu
     
      A class that represents a quaternion.
*/


public class Quaternion
{
   private double a;
   private double b;
   private double c;
   private double d;
   
   public Quaternion()
   {
      this.a = 0;
      this.b = 0;
      this.c = 0;
      this.d = 0;
   }
   
   public Quaternion(double w, double x, double y, double z)
   {
      this.a = w;
      this.b = x;
      this.c = y;
      this.d = z;
   }
   
   public Quaternion conjugate()
   {
      return new Quaternion(a,-b,-c,-d);
   }
   
   public static Quaternion add(Quaternion O, Quaternion P)
   {
      return new Quaternion(O.a + P.a, O.b + P.b, O.c + P.c, O.d + P.d);
   }
   
   public Quaternion plus(Quaternion other)
   {
      return Quaternion.add(this,other);
   }
   
   public static Quaternion subtract(Quaternion O, Quaternion P)
   {
      return new Quaternion(O.a - P.a, O.b - P.b, O.c - P.c, O.d - P.d);
   }
   
   public Quaternion minus(Quaternion other)
   {
      return Quaternion.subtract(this,other);
   }
   
   public static Quaternion mult(Quaternion O, Quaternion P)
   {
      double newA = O.a * P.a - O.b * P.b - O.c * P.c - O.d * P.d;
      double newB = O.a * P.b + O.b * P.a + O.c * P.d - O.d * P.c;
      double newC = O.a * P.c - O.b * P.d + O.c * P.a + O.d * P.b; 
      double newD = O.a * P.d + O.b * P.c - O.c * P.b + O.d * P.a;
      return new Quaternion (newA, newB, newC, newD);
   }

   public Quaternion times(Quaternion other)
   {
      return Quaternion.mult(this, other);
   }
   
   public Quaternion unitQuaternion()
   {
      double m = this.magnitude();
      return new Quaternion(a/m, b/m, c/m, d/m);
   }
   
   public Quaternion inverse()
   {
      double m2 = Math.pow(this.magnitude(),2);
      return new Quaternion(a/m2,-b/m2,-c/m2,-d/m2);
   }
   
   public Matrix toMatrix()
   {
   
   }
   
   public double magnitude()
   {
      return (Math.sqrt(a*a + b*b + c*c + d*d));
   }
   
   public String toString()
   {
      String s = "";
      double[] coefficients = {a,b,c,d};
      
      s += equationTerm(a, 0, true);
      
      int a = 1;
      for (a = a; a < 4 && s.length() == 0; a++)
      {
         s += equationTerm(coefficients[a],a,true);
      }
      for (a = a; a < 4 && s.length() > 0; a++)
      {
         s += operatorString(coefficients[a]);
         s += equationTerm(coefficients[a],a,false);
      }
      if (s.length() > 0) {return s;}
      else {return "0";}
   }
   
   public String operatorString(double n)
   {
      if (n > 0) {return " + ";}
      else if (n < 0) {return " - ";}
      else {return "";}
   }
   
   String[] variables = {"","i","j","k"};
   public String equationTerm(double n, int termNo, boolean showSign)
   {
      if (n == 0){return "";}
      
      String sign = "";
      if (n < 0 && showSign) {sign = "-";}    
      
      
           
      return sign + String.format("%.3f", Math.abs(n)) + variables[termNo];
   }
}