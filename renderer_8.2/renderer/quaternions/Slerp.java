/*
Course: CS 42000
      Name: Bruno Hnatusko III
      Email: bhnatusk@pnw.edu
     
      A class that represents quaternion interpolation.
*/
package renderer.quaternions;

public class Slerp
{
   private Quaternion[] qList;
   private int steps;
   
   public Slerp(Quaternion O, Quaternion P, int s)
   {
      //Less than 2 steps is not even a rotation, let alone interpolation.
      if (s < 2) 
      {
         steps = 0;
         qList = new Quaternion[0];
         return;
      }
      
      steps = s;
      qList = new Quaternion[steps]; 
      
      //O and P are normalized to simplify the math.
      O.normalize();
      P.normalize();
      
      //O is the start and P is the end.
      qList[0] = O;
      qList[steps - 1] = P;
      
      System.out.println("Quaternion O: " + O);
      System.out.println("Quaternion P: " + P);
      double dot = Quaternion.dotProduct(O,P);
      //Ensures the path taken is the shorter of the two between the points.
      if (dot < 0.0f) 
      {
         O = O.scale(-1);
         dot = -dot;
      }
      
      //Edge case where dot product is about 1
      if (dot > 0.9995)
      {
         for (int i = 1; i < steps - 1; i++)
         {
            double t = (double) i/(steps-1);
            Quaternion qI = Quaternion.add(O,P.minus(O).scale(t));
            qI.normalize();
            qList[i] = qI;
         }
         return;
      }
      
      //Most cases
      double theta_0 = Math.acos(dot);
      System.out.println("Theta_0: " + Math.toDegrees(theta_0));
      double sin_theta_0 = Math.sin(theta_0);
      
      for (int i = 1; i < steps - 1; i++)
      {
         double t = (double) i/(steps-1);
         double theta = theta_0 * t;
         System.out.println("Theta: " + Math.toDegrees(theta));
         double sin_theta = Math.sin(theta);
         
         double s0 = Math.sin(theta_0 - theta)/sin_theta_0;
         double s1 = sin_theta/sin_theta_0;
         
         Quaternion qI = Quaternion.add(O.scale(s0),P.scale(s1));
         qI.normalize();
         qList[i] = qI;
      }    
   }
   
   public Quaternion get(int i)
   {
      if (i >= 0 && i < qList.length) {return qList[i];}
      return new Quaternion();
   }
   
   public int getSteps()
   {return steps;}
}