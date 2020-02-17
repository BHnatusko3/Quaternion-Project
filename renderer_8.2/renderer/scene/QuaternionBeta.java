/*
Course: CS 42000
      Name: Bruno Hnatusko III
      Email: bhnatusk@pnw.edu
     
      A class that represents a quaternion.
*/
package renderer.scene;

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

   public Quaternion scale(double s)
   {
      return new Quaternion(a*s,b*s,c*s,d*s);
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
   
   public static Quaternion fromRotationMatrix(Matrix m)
   {
      double m11 = m.get(1,1);
      double m12 = m.get(1,2);
      double m13 = m.get(1,3);
      
      double m21 = m.get(2,1);
      double m22 = m.get(2,2);
      double m23 = m.get(2,3);
      
      double m31 = m.get(3,1);
      double m32 = m.get(3,2);
      double m33 = m.get(3,3);
    
      
      double t;
      Quaternion q;
      
      if (m33 < 0)
      {
         if (m11 > m22)
         {
            System.out.println("A");
            t = 1 + m11 - m22 - m33;
            q = new Quaternion (t,m12 + m21,m31 + m13, m23-m32);
         }
         else
         {
            System.out.println("B");
            t = 1 - m11 + m22 - m33;
            q = new Quaternion (m12 + m21,t,m23 + m32, m31-m13);
         }
      }
      else
      {
         
         if(m11 < -m22)
         {
            System.out.println("C");
            t = 1 - m11 - m22 + m33;
            q = new Quaternion (m31 + m13,m23 + m32,t, m12-m21);  
         }
         else
         {
            System.out.println("D");
            t = 1 + m11 + m22 + m33;
            q = new Quaternion (m23 - m32,m31 - m13,m12 - m21,t);
         }
      }
      
      return q.scale(0.5/Math.sqrt(t));     
   }
   
   
   public static Quaternion fromRotationMatrix2(Matrix m)
   {
      double m11 = m.get(1,1);
      double m12 = m.get(1,2);
      double m13 = m.get(1,3);
      
      double m21 = m.get(2,1);
      double m22 = m.get(2,2);
      double m23 = m.get(2,3);
      
      double m31 = m.get(3,1);
      double m32 = m.get(3,2);
      double m33 = m.get(3,3);
    
      double t = m11 + m22 + m33;
      double r = Math.sqrt(1 + t);
      
      double newA = r/2;
      double newB = Math.copySign(0.5 * Math.sqrt(1 + m11 - m22 - m33),m32 - m23);
      double newC = Math.copySign(0.5 * Math.sqrt(1 - m11 + m22 - m33),m13 - m31);
      double newD = Math.copySign(0.5 * Math.sqrt(1 - m11 - m22 + m33),m21 - m12);
      return new Quaternion(newA, newB, newC, newD);
   }
   
    public static Quaternion fromRotationMatrix3(Matrix m)
   {
      double m11 = m.get(1,1);
      double m12 = m.get(1,2);
      double m13 = m.get(1,3);
      
      double m21 = m.get(2,1);
      double m22 = m.get(2,2);
      double m23 = m.get(2,3);
      
      double m31 = m.get(3,1);
      double m32 = m.get(3,2);
      double m33 = m.get(3,3);
    
      double t = m11 + m22 + m33;
      double r = Math.sqrt(1 + m11 - m22 - m33);
      double s = 0.5/r;
         
      double newA = s * (m32 - m23);
      double newB = 0.5*r;
      double newC = s * (m12 - m21);
      double newD = s * (m31 - m13);
      return new Quaternion(newA, newB, newC, newD);
   }
    
   public Matrix toRotationMatrix()
   {
      double s = Math.pow(this.magnitude(),-2);
      double twoS = 2*s;
      
      double b2 = b*b;
      double c2 = c*c;
      double d2 = d*d;
      
      double ab = a*b;
      double ac = a*c;
      double ad = a*d;
      double bc = b*c;
      double bd = b*d;
      double cd = c*d;
      
      //Column Vectors
      Vector v1 = new Vector(1 - twoS * (c2 + d2), twoS * (bc + ad), twoS * (bd - ac), 0);
      Vector v2 = new Vector(twoS * (bc - ad), 1 - twoS * (b2 + d2), twoS * (cd + ab), 0);
      Vector v3 = new Vector(twoS * (bd + ac), twoS * (cd - ab), 1 - twoS * (b2 + c2), 0);
      Vector v4 = new Vector(0, 0, 0, 1);
       
      return Matrix.build(v1,v2,v3,v4);
   }
   
   //Potentially more efficient, but very slightly less accurate.
    public Matrix toRotationMatrix2()
   {
      if (this.magnitude() != 1) {return this.unitQuaternion().toRotationMatrix();}
      //double s = Math.pow(this.magnitude(),-2);
      //double twoS = 2*s;
      
      double b2 = b*b;
      double c2 = c*c;
      double d2 = d*d;
      
      double ab = a*b;
      double ac = a*c;
      double ad = a*d;
      double bc = b*c;
      double bd = b*d;
      double cd = c*d;
      
      //Column Vectors
      Vector v1 = new Vector(1 - 2 * (c2 + d2), 2 * (bc + ad), 2 * (bd - ac), 0);
      Vector v2 = new Vector(2 * (bc - ad), 1 - 2 * (b2 + d2), 2 * (cd + ab), 0);
      Vector v3 = new Vector(2 * (bd + ac), 2 * (cd - ab), 1 - 2 * (b2 + c2), 0);
      Vector v4 = new Vector(0, 0, 0, 1);
       
      return Matrix.build(v1,v2,v3,v4);
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